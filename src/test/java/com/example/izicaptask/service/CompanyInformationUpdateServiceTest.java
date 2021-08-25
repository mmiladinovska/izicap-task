package com.example.izicaptask.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.example.izicaptask.model.CompanyInformation;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CompanyInformationUpdateServiceTest {

    @Autowired
    private CompanyInformationUpdateService companyInformationUpdateService;

    private final String CSV_FILE_PATH = "companyInformation.csv";

    @Test
    void companyInformationCSVshouldBeCreated() throws IOException {
        Set<String> sirets = Set.of("41339442000090", "31302979500017");
        companyInformationUpdateService.createOrUpdateCompanyInformationDatabase(sirets);
        List<CompanyInformation> csvCompanies = readCompaniesFromCsv();
        sirets.forEach(siret -> {
                    assertTrue(csvCompanies.stream().anyMatch(c -> c.getSiret().equals(siret)));
                    assertTrue(csvCompanies.stream().anyMatch(c -> !c.getTvaNumber().isBlank()));
                    assertTrue(csvCompanies.stream().anyMatch(c -> !c.getId().toString().isBlank()));
                    assertTrue(csvCompanies.stream().anyMatch(c -> !c.getNic().isBlank()));
                    assertTrue(csvCompanies.stream().anyMatch(c -> !c.getCreationDate().isBlank()));
                }
        );
    }

    @Test
    void companyInformationCSVshouldBeUpdated() throws IOException {
        String siret1 = "31302979500017";
        Set<String> testSiretList = Set.of(siret1);
        companyInformationUpdateService.createOrUpdateCompanyInformationDatabase(testSiretList);
        var siret2 = "41339442000033";
        testSiretList = Set.of(siret2);
        companyInformationUpdateService.createOrUpdateCompanyInformationDatabase(testSiretList);
        List<CompanyInformation> csvCompanies = readCompaniesFromCsv();

        assertEquals(1, csvCompanies.stream().filter(c -> c.getSiret().equals(siret1)).count());
        assertEquals(1, csvCompanies.stream().filter(c -> c.getSiret().equals(siret2)).count());
    }

    private List<CompanyInformation> readCompaniesFromCsv() throws FileNotFoundException {
        return
                new CsvToBeanBuilder<CompanyInformation>(new FileReader(CSV_FILE_PATH))
                        .withType(CompanyInformation.class)
                        .build()
                        .stream().collect(Collectors.toList());
    }

}