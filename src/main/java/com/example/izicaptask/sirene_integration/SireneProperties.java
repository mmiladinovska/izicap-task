package com.example.izicaptask.sirene_integration;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "sirene")
@Configuration
public class SireneProperties {

    private String url;
    private Integer timeout;
}

