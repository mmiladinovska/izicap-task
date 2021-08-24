package com.example.izicaptask.sirene_integration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:/sirene_mapping.properties")
@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "")
public class SireneResponseFieldMapping {

    /**
     * Mapping of CompanyInformation fields to Establishment fields of the Sirene API json response using JsonPath
     */
    private final Map<String, String> sireneMapping = new ConcurrentHashMap<>();
}




