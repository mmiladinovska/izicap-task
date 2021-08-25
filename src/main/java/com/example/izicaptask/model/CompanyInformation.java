package com.example.izicaptask.model;

import com.example.izicaptask.sirene_integration.CompanyInformationDeserializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.opencsv.bean.CsvBindByName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonDeserialize(using = CompanyInformationDeserializer.class)
public class CompanyInformation {

    @ApiModelProperty(notes = "siret of the company")
    @CsvBindByName(column = "siret")
    private String siret;

    @ApiModelProperty(notes = "id of the company")
    @CsvBindByName(column = "id")
    private Integer id;

    @ApiModelProperty(notes = "nic of the company")
    @CsvBindByName(column = "nic")
    private String nic;

    @ApiModelProperty(notes = "full address of the company")
    @CsvBindByName(column = "fullAddress")
    private String fullAddress;

    @ApiModelProperty(notes = "creation date of the company information")
    @CsvBindByName(column = "creationDate")
    private String creationDate;

    @ApiModelProperty(notes = "full name of the company")
    @CsvBindByName(column = "fullName")
    private String fullName;

    @ApiModelProperty(notes = "tva number of the company")
    @CsvBindByName(column = "tvaNumber")
    private String tvaNumber;

    @ApiModelProperty(notes = "name of the company")
    @CsvBindByName(column = "companyName")
    private String companyName;

    @ApiModelProperty(notes = "last updated date of the company information")
    @CsvBindByName(column = "updateDate")
    private String updateDate;
}