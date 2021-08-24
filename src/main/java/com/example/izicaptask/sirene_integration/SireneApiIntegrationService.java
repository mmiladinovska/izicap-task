package com.example.izicaptask.sirene_integration;

import static com.example.izicaptask.utils.ErrorMessages.errorCallingSireneApiErrorMessage;
import static com.example.izicaptask.sirene_integration.SireneEndpoints.COMPANYINFORMATION_BY_SIRET_ENDPOINT;

import java.util.function.Function;

import com.example.izicaptask.model.CompanyInformation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class SireneApiIntegrationService {

    private static final String ESTABLISHMENT_BY_SIRET_PATH = COMPANYINFORMATION_BY_SIRET_ENDPOINT;

    private final WebClient webClientSirene;

    public String getCompanyInformation(String siret) {
        return webClientSirene.get()
                .uri(ESTABLISHMENT_BY_SIRET_PATH + "/" + siret)
                .retrieve()
                .onStatus(HttpStatus::isError, errorResponse())
                .bodyToMono(String.class)
                .block();
    }


    private Function<ClientResponse, Mono<? extends Throwable>> errorResponse() {
        return response ->
                response.bodyToMono(CompanyInformation.class)
                        .single()
                        .flatMap(e -> Mono.error(
                                        new SireneIntegrationException(
                                                errorCallingSireneApiErrorMessage(
                                                        "company information", "establishment by Siret")
                                        )
                                )
                        );
    }
}



