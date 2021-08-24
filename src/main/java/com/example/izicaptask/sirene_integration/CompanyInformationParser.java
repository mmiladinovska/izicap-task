package com.example.izicaptask.sirene_integration;

import com.example.izicaptask.model.CompanyInformation;
import com.example.izicaptask.model.CompanyInformationFromSirene;
import com.example.izicaptask.model.CompanyInformationMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyInformationParser {

    private final CompanyInformationMapper companyInformationMapper;
    public static final String DATA_MAPPING = "$.etablissement";

    public CompanyInformation parseCompanyInformationFromSireneJsonResponse(String sireneJsonResponse) {
        LOGGER.debug("Retrieving company information from sirene API");
        var documentContext = JsonPath.parse(sireneJsonResponse);
        String siret = documentContext.read("$.etablissement.siret");
        Integer id = documentContext.read("$.etablissement.id");
        String nic = documentContext.read("$.etablissement.nic");
        String fullAddress = documentContext.read("$.etablissement.geo_adresse");
        String creationDate = documentContext.read("$.etablissement.created_at");
        String lastName = String.valueOf(Optional.ofNullable(documentContext.read(
        "$.etablissement.unite_legale.nom")).orElse(""));
        String usualFirstName = String.valueOf(Optional.ofNullable(documentContext.read(
                "$.etablissement.unite_legale.prenom_usuel")).orElse(""));
        String firstName1 = String.valueOf(Optional.ofNullable(documentContext.read(
                "$.etablissement.unite_legale.prenom_1")).orElse(""));
        String firstName2 = String.valueOf(Optional.ofNullable(documentContext.read(
                "$.etablissement.unite_legale.prenom_2")).orElse(""));
        String firstName3 = String.valueOf(Optional.ofNullable(documentContext.read("" +
                "$.etablissement.unite_legale.prenom_3")).orElse(""));
        String firstName4 = String.valueOf(Optional.ofNullable(documentContext.read(
                "$.etablissement.unite_legale.prenom_4")).orElse(""));
        String companyName = documentContext.read("$.etablissement.nomenclature_activite_principale");
        String tvaNumber = documentContext.read("$.etablissement.unite_legale.numero_tva_intra");
        String updateDate = documentContext.read("$.etablissement.updated_at");
        LOGGER.debug("company information fields returned from Sirene API");


        CompanyInformationFromSirene companyInformationFromSirene =
                CompanyInformationFromSirene.builder()
                        .siret(siret)
                        .id(Integer.valueOf(id))
                        .nic(nic)
                        .fullAddress(fullAddress)
                        .creationDate(creationDate)
                        .lastName(lastName)
                        .usualFirstName(usualFirstName)
                        .firstName1(firstName1)
                        .firstName2(firstName2)
                        .firstName3(firstName3)
                        .firstName4(firstName4)
                        .tvaNumber(tvaNumber)
                        .companyName(companyName)
                        .updateDate(updateDate)
                        .build();
        return companyInformationMapper.toEntity(companyInformationFromSirene);
    }
}