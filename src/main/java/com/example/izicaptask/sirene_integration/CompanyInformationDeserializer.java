package com.example.izicaptask.sirene_integration;

import com.example.izicaptask.model.CompanyInformation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Custom Deserializer for CompanyInformation from Sirene Web Service response
 */
public class CompanyInformationDeserializer extends StdDeserializer<CompanyInformation> {

    private static final List<String> fullNameFields = List.of("nom", "prenom_usuel", "prenom_1", "prenom_2", "prenom_3", "prenom_4");

    protected CompanyInformationDeserializer() {
        super(CompanyInformation.class);
    }

    @Override
    public CompanyInformation deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        CompanyInformation company = new CompanyInformation();
        JsonNode etablissementNode = node.get("etablissement");
        if (etablissementNode != null) {
            company.setSiret(getText(etablissementNode, "siret"));
            company.setNic(getText(etablissementNode, "nic"));
            company.setId(getInteger(etablissementNode, "id"));
            company.setFullAddress(getText(etablissementNode, "geo_adresse"));
            company.setCompanyName(getText(etablissementNode, "nomenclature_activite_principale"));
            company.setCreationDate(getText(etablissementNode, "created_at"));
            company.setUpdateDate(getText(etablissementNode, "updated_at"));


            JsonNode uniteLegaleNode = etablissementNode.get("unite_legale");
            String fullName = fullNameFields.stream().map(field -> getText(uniteLegaleNode, field))
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(" "));
            company.setFullName(fullName);
            company.setTvaNumber(getText(uniteLegaleNode, "numero_tva_intra"));
        }
        return company;
    }

    private String getText(JsonNode node, String fieldName) {
        JsonNode fieldNode = node.get(fieldName);
        return fieldNode != null ? fieldNode.textValue() : null;
    }

    private Integer getInteger(JsonNode node, String fieldName) {
        JsonNode fieldNode = node.get(fieldName);
        return fieldNode != null ? fieldNode.intValue() : null;
    }

}
