package org.example;

import generator.IdGenerator;
import generator.SHA1Generator;
import interactors.ShortenerInteractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shortener.MockURLGateway;
import shortener.URLGateway;
import usecases.ShortenerUseCase;

@Configuration
public class ServiceConfig {

    @Bean
    public URLGateway urlGateway() {
        return new MockURLGateway();
    }

    @Bean
    public IdGenerator idGenerator() {
        return new  SHA1Generator();
    }

    @Bean
    public ShortenerUseCase shortener(URLGateway urlGateway, IdGenerator  idGenerator) {
        return new ShortenerInteractor(urlGateway, idGenerator);
    }
}
