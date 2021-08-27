package com.example.izicaptask.sirene_integration;

import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

@Configuration
@RequiredArgsConstructor
public class SireneWebClient {

    private final SireneProperties sireneProperties;

    @Bean
    public WebClient webClientSirene() throws SSLException {

        var sslContext = SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();

        HttpClient httpClient = HttpClient
                .create()
                .secure(t -> t.sslContext(sslContext) )
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, sireneProperties.getTimeout())
                .doOnConnected(connection -> {
                    connection
                            .addHandlerLast(new ReadTimeoutHandler(sireneProperties.getTimeout(), TimeUnit.MILLISECONDS));
                    connection
                            .addHandlerLast(new WriteTimeoutHandler(sireneProperties.getTimeout(), TimeUnit.MILLISECONDS));
                });

        return WebClient
                .builder()
                .baseUrl(sireneProperties.getUrl())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}