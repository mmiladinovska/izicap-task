package com.example.izicaptask.sirene_integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.izicaptask.model.CompanyInformation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SireneApiIntegrationTest {

    @Autowired
    private SireneApiIntegrationService sireneApiIntegrationService;

    @Test
    void getCompanyInformationForGivenSiret() {
        final var siret = "31302979500017";
        CompanyInformation companyInformation = sireneApiIntegrationService.getCompany(siret);
        assertEquals(siret, companyInformation.getSiret());
    }
}