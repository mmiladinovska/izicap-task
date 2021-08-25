package com.example.izicaptask.service;

import com.example.izicaptask.model.CompanyInformation;
import com.example.izicaptask.sirene_integration.SireneApiIntegrationService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyInformationUpdateService {

    private final SireneApiIntegrationService sireneApiIntegrationService;
    private static final String CSV_FILE_PATH = "companyInformation.csv";
    private static final String TEMPORARY_CSV_PATH = "temporary.csv";

    /**
     * Extracts data from external service and maintains the data in a local CSV file.
     * The method is allowed to be called from one thread only due to maintaining the data in a single CSV file.
     * @param sirets a set of siret numbers
     * @throws IOException
     */
    @Synchronized
    public void createOrUpdateCompanyInformationDatabase(Set<String> sirets) throws IOException {
        HashSet<String> processedSirets = new HashSet<>(sirets);
        try (FileWriter fileWriter = new FileWriter(TEMPORARY_CSV_PATH)) {
            StatefulBeanToCsv<CompanyInformation> writer = getCsvWriter(fileWriter);
            // check for previous siret values in the csv file if they should be updated
            copyPreviousCompanies(processedSirets, writer);
            // new siret values (not present in the previous CSV file)
            writeAllCompanies(processedSirets, writer);
        }
        copyTemporaryCsvToCompaniesCsv();
    }

    private void writeAllCompanies(Set<String> sirets, StatefulBeanToCsv<CompanyInformation> writer) {
        sirets.forEach(siret ->
                writeCompanyToCsvFile(writer, sireneApiIntegrationService.getCompany(siret))
        );
    }

    private void copyPreviousCompanies(Set<String> sirets, StatefulBeanToCsv<CompanyInformation> writer)
            throws IOException {

        try (FileReader fileReader = getFileReader()) {
            getCsvReader(fileReader)
                    .forEach(csvCompany -> {
                        CompanyInformation companyToWrite = sirets.contains(csvCompany.getSiret())
                                ? sireneApiIntegrationService.getCompany(csvCompany.getSiret())
                                : csvCompany;
                        writeCompanyToCsvFile(writer, companyToWrite);
                        sirets.remove(csvCompany.getSiret());
                    });
        }
    }

    private void copyTemporaryCsvToCompaniesCsv() throws IOException {
        Files.move(Paths.get(TEMPORARY_CSV_PATH), Paths.get(CSV_FILE_PATH), StandardCopyOption.REPLACE_EXISTING);
    }

    private StatefulBeanToCsv<CompanyInformation> getCsvWriter(FileWriter fileWriter) {
        return
                new StatefulBeanToCsvBuilder<CompanyInformation>(fileWriter)
                        .build();
    }

    private CsvToBean<CompanyInformation> getCsvReader(Reader reader) {
        return new CsvToBeanBuilder<CompanyInformation>(reader)
                .withType(CompanyInformation.class)
                .build();
    }

    private FileReader getFileReader() throws IOException {
        File csvFile = new File(CSV_FILE_PATH);
        if (!csvFile.exists()) {
            csvFile.createNewFile();
        }
        return new FileReader(csvFile);
    }

    private void writeCompanyToCsvFile(StatefulBeanToCsv<CompanyInformation> writer,
                                       CompanyInformation company) {
        try {
            writer.write(company);
            LOGGER.info("Writing to CSV: siret: {}", company.getSiret());
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            LOGGER.error("Error writing company to CSV file: siret: {}", company.getSiret(), e);
        }
    }
}