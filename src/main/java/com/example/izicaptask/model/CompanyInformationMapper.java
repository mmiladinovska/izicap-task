package com.example.izicaptask.model;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyInformationMapper {

    /**
     * Converts CompanyInformationFromSirene to CompanyInformation entity model.
     *
     * @param dto of CompanyInfromationFromSirene to be converted
     * @return entity of CompanyInformation
     */
    public CompanyInformation toEntity(CompanyInformationFromSirene dto) {
        return CompanyInformation.builder()
                .siret(dto.getSiret())
                .id(dto.getId())
                .nic(dto.getNic())
                .fullAddress(dto.getFullAddress())
                .creationDate(LocalDateTime.parse(dto.getCreationDate(), DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .fullName(getFullName(dto))
                .tvaNumber(dto.getTvaNumber())
                .companyName(dto.getCompanyName())
                .updateDate(LocalDateTime.parse(dto.getUpdateDate(), DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .build();
    }

    /**
     * Converts list of companyInformationFromSirene dto to List of companyInformation entity.
     *
     * @param dtoList of companyInformationFromSirene to be converted
     * @return list of companyInformation
     */
    public List<CompanyInformation> toEntities(List<CompanyInformationFromSirene> dtoList) {
        final ArrayList<CompanyInformation> companyInformationList = new ArrayList<>();
        dtoList.forEach(companyInformationFromSirene ->
                companyInformationList.add(toEntity(companyInformationFromSirene)));
        return companyInformationList;
    }

    /**
     * Converts the last name and the first names from the Sirene Response to fullName
     *
     * @param companyInformationFromSirene dto
     * @return full name of the company official
     */
    String getFullName(CompanyInformationFromSirene companyInformationFromSirene) {
        if (companyInformationFromSirene == null) {
            return null;
        }
        return companyInformationFromSirene.getLastName() + " "
                + companyInformationFromSirene.getUsualFirstName() + " "
                + companyInformationFromSirene.getFirstName1() + " "
                + companyInformationFromSirene.getFirstName2() + " "
                + companyInformationFromSirene.getFirstName3() + " "
                + companyInformationFromSirene.getFirstName4();
    }
}
