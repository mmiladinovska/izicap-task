package com.example.izicaptask.service;

import com.example.izicaptask.sirene_integration.CompanyInformationParser;
import com.example.izicaptask.sirene_integration.SireneApiIntegrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyInformationUpdateService {

    private final SireneApiIntegrationService sireneApiIntegrationService;
    private final CompanyInformationParser companyInformationParser;
    private static final String SIRET_FIELD = "siret";
    private static final String ID_FIELD = "id";
    private static final String NIC_FIELD = "nic";
    private static final String FULL_ADDRESS_FIELD = "fullAddress";
    private static final String CREATION_DATE_FIELD = "creationDate";
    private static final String FULLNAME_FIELD = "fullName";
    private static final String TVA_NUMBER_FIELD = "tvaNumber";
    private static final String COMPANY_NAME_FIELD = "companyName";
    private static final String UPDATE_DATE_FILED = "updateDate";
    private static final String CSV_FILE_PATH = "companyInformation.csv";
    private static final String CSV_OLD_FILE_PATH = "companyInformationOld.csv";
    private static final String TEMPORARY_CSV_PATH = "temporary.csv";


    private Optional<String> findCompanyInformationBySiret(String siret) throws IOException {
        try (Reader csvReader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH))) {
            CSVParser csvParser = new CSVParser(csvReader, CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim());
            for (CSVRecord csvRecord : csvParser) {
                if (csvRecord.get("SIRET_FIELD").equals(siret))
                    return Optional.of(csvRecord.toString());
            }
            return Optional.empty();
        }
    }

    public void createOrUpdateCompanyInformationDatabase(List<String> siretList) throws IOException {
        ArrayList<String> changedRecords = new ArrayList<>();

        try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(CSV_FILE_PATH), CSVFormat.DEFAULT.withHeader(
                SIRET_FIELD, ID_FIELD, NIC_FIELD, FULL_ADDRESS_FIELD, CREATION_DATE_FIELD, FULLNAME_FIELD,
                TVA_NUMBER_FIELD, COMPANY_NAME_FIELD, UPDATE_DATE_FILED));
             CSVPrinter tempCsvPrinter = new CSVPrinter(new FileWriter(TEMPORARY_CSV_PATH), CSVFormat.DEFAULT.withHeader(
                     SIRET_FIELD, ID_FIELD, NIC_FIELD, FULL_ADDRESS_FIELD, CREATION_DATE_FIELD, FULLNAME_FIELD,
                     TVA_NUMBER_FIELD, COMPANY_NAME_FIELD, UPDATE_DATE_FILED));
             Reader csvReader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH));
             CSVParser csvParser = new CSVParser(csvReader, CSVFormat.DEFAULT
                     .withFirstRecordAsHeader()
                     .withIgnoreHeaderCase()
                     .withTrim());
        ) {

            siretList.forEach(
                    s -> {
                        final var indexOfUpdateDate = sireneApiIntegrationService.getCompanyInformation(s).indexOf(UPDATE_DATE_FILED);
                        String[] data = sireneApiIntegrationService.getCompanyInformation(s).split(",");
                        Predicate<String> predicate = field -> !field.equals(data[indexOfUpdateDate]);
                        try {
                            Optional<String> companyInformation = findCompanyInformationBySiret(s);
                            if (companyInformation.isPresent() && companyInformation.stream().anyMatch(predicate)) {
                                changedRecords.add(String.valueOf(companyInformation));

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );
            if (changedRecords.isEmpty()) {
                siretList.forEach(
                        s -> {
                            try {
                                csvPrinter.print(companyInformationParser.parseCompanyInformationFromSireneJsonResponse(
                                        sireneApiIntegrationService.getCompanyInformation(s)));
                                csvPrinter.print("\n");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                );
            }
            else {
                for (String changed : changedRecords) {
                    for (CSVRecord csvRecord : csvParser) {
                        try {
                            if (changed.contains(csvRecord.get("SIRET_FIELD"))) {
                                tempCsvPrinter.print(changed);
                                tempCsvPrinter.print("\n");
                            } else {
                                tempCsvPrinter.print(csvRecord);
                                tempCsvPrinter.print("\n");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                File file = new File(CSV_FILE_PATH);
                File oldFile = new File(CSV_OLD_FILE_PATH);
                File tempFile = new File(TEMPORARY_CSV_PATH);
                boolean deleteOldFile = false;

                LOGGER.info("Renaming the original file to {} file after writing the records", "companyInformatiol_old.csv");
                try {
                    boolean renameOriginal = file.renameTo(oldFile);
                    if (renameOriginal) {
                        boolean renameTempFile = tempFile.renameTo(file);
                        if (renameTempFile) {
                            deleteOldFile = oldFile.delete();
                        }
                    }
                    if (deleteOldFile) LOGGER.info("Old file successfully deleted, new records are stored in {}",
                            CSV_FILE_PATH);
                    else LOGGER.error("There was a problem with deleting the old file.");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}