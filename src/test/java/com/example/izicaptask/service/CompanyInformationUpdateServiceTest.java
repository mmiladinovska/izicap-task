package com.example.izicaptask.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CompanyInformationUpdateServiceTest {

    @Autowired
    private CompanyInformationUpdateService companyInformationUpdateService;
    private final String CSV_FILE_PATH = "./companyInformation.csv";

    @Test
    void CompanyInformationCSVshouldBeCreated() throws IOException {
        ArrayList<String>testSiretList = new ArrayList();
        testSiretList.add("31302979500017");
        testSiretList.add("41339442000033");
        testSiretList.add("41339442000090");

        companyInformationUpdateService.createOrUpdateCompanyInformationDatabase(testSiretList);
        File tmpDir = new File("companyInformation.csv");
        boolean exists = tmpDir.exists();
        assertTrue(exists);
    }

}