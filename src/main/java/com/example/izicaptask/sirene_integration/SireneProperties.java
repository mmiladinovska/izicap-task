package com.example.izicaptask.sirene_integration;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@Configuration
public class SireneProperties {

    private String url;
    private Integer timeout;
}

