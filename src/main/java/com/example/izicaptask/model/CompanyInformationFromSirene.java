package com.example.izicaptask.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data@SuperBuilder

public class CompanyInformationFromSirene {

    @ApiModelProperty(notes = "siret of the company")
    @JsonProperty(value = "siret")
    private String siret;

    @ApiModelProperty(notes = "id of the company")
    @JsonProperty(value = "id")
    private Integer id;

    @ApiModelProperty(notes = "nic of the company")
    @JsonProperty(value = "nic")
    private String nic;

    @ApiModelProperty(notes = "full address of the company")
    @JsonProperty(value = "fullAddress")
    private String fullAddress;

    @ApiModelProperty(notes = "creation date of the company information")
    @JsonProperty(value = "creationDate")
    private String creationDate;

    @ApiModelProperty(notes = "lastName of the company official")
    @JsonProperty(value = "lastName")
    private String lastName;

    @ApiModelProperty(notes = "usual first name of the company official")
    @JsonProperty(value = "usualFirstName")
    private String usualFirstName;

    @ApiModelProperty(notes = "first first name of the company official")
    @JsonProperty(value = "firstName1")
    private String firstName1;

    @ApiModelProperty(notes = "second first name of the company official")
    @JsonProperty(value = "firstName2")
    private String firstName2;

    @ApiModelProperty(notes = "third first name of the company official")
    @JsonProperty(value = "firstName3")
    private String firstName3;

    @ApiModelProperty(notes = "fourth first name of the company official")
    @JsonProperty(value = "firstName4")
    private String firstName4;

    @ApiModelProperty(notes = "tva number of the company")
    @JsonProperty(value = "tvaNumber")
    private String tvaNumber;

    @ApiModelProperty(notes = "name of the company")
    @JsonProperty(value = "companyName")
    private String companyName;

    @ApiModelProperty(notes = "date of the last update of the company information")
    @JsonProperty(value = "updateDate")
    private String updateDate;

}