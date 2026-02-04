package org.example;

import generator.CollectionIdGenerator;
import generator.IdGenerator;
import generator.RandomCollectionIdGenerator;
import generator.SHA1Generator;
import interactors.CollectionShortenerInteractor;
import interactors.ShortenerInteractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shortener.MockURLCollectionGateway;
import shortener.MockURLGateway;
import shortener.URLCollectionGateway;
import shortener.URLGateway;
import usecases.CollectionShortenerUseCase;
import usecases.ShortenerUseCase;

@Configuration
public class ServiceConfig {

    @Bean
    public URLGateway urlGateway() {
        return new MockURLGateway();
    }

    @Bean
    public IdGenerator idGenerator() {
        return new SHA1Generator();
    }

    @Bean
    public ShortenerUseCase shortener(URLGateway urlGateway, IdGenerator idGenerator) {
        return new ShortenerInteractor(urlGateway, idGenerator);
    }

    @Bean
    public CollectionIdGenerator collectionIdGenerator() {
        return new RandomCollectionIdGenerator();
    }

    @Bean
    CollectionShortenerUseCase collectionShortener(URLCollectionGateway gateway, CollectionIdGenerator idGenerator) {
        return new CollectionShortenerInteractor(gateway, idGenerator);
    }

    @Bean
    public URLCollectionGateway urlCollectionGateway(ShortenerUseCase shortenerUseCase) {
        return new MockURLCollectionGateway(shortenerUseCase);
    }
}
