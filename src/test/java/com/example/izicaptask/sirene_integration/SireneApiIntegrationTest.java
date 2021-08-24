package com.example.izicaptask.sirene_integration;

import com.example.izicaptask.model.CompanyInformation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SireneApiIntegrationTest {

    @Autowired
    private SireneApiIntegrationService sireneApiIntegrationService;

    @Test
    void getCompanyInformationForGivenSiret() {
        final var siret = "31302979500017";
        String companyInformation = sireneApiIntegrationService.getCompanyInformation(siret);
        assertTrue(companyInformation.contains("siret\":\"31302979500017"));
    }
}