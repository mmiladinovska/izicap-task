package com.example.izicaptask.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CompanyInformation {

    @ApiModelProperty(notes = "siret of the company")
    private String siret;

    @ApiModelProperty(notes = "id of the company")
    private Integer id;

    @ApiModelProperty(notes = "nic of the company")
    private String nic;

    @ApiModelProperty(notes = "full address of the company")
    private String fullAddress;

    @ApiModelProperty(notes = "creation date of the company information")
    private LocalDateTime creationDate;

    @ApiModelProperty(notes = "full name of the company")
    private String fullName;

    @ApiModelProperty(notes = "tva number of the company")
    private String tvaNumber;

    @ApiModelProperty(notes = "name of the company")
    private String companyName;

    @ApiModelProperty(notes = "last updated date of the company information")
    private LocalDateTime updateDate;

    public String toString() {
        return siret + ","
                + id + ","
                + nic + ","
                + fullAddress + ","
                + creationDate + ","
                + fullName + ","
                + tvaNumber + ","
                + companyName + ","
                + updateDate;
    }
}