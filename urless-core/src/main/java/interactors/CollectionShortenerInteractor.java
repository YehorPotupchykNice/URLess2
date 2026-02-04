package interactors;

import generator.CollectionIdGenerator;
import shortener.ShortenedURLCollection;
import shortener.URLCollectionGateway;
import usecases.CollectionShortenerUseCase;

import java.util.List;
import java.util.Optional;

public class CollectionShortenerInteractor implements CollectionShortenerUseCase {

    private final URLCollectionGateway  urlCollectionGateway;
    private final CollectionIdGenerator idGenerator;

    public CollectionShortenerInteractor(URLCollectionGateway urlCollectionGateway, CollectionIdGenerator idGenerator) {
        this.urlCollectionGateway = urlCollectionGateway;
        this.idGenerator = idGenerator;
    }

    @Override
    public Optional<ShortenedURLCollection> getById(String id) {
        return Optional.ofNullable(urlCollectionGateway.getById(id));
    }

    @Override
    public ShortenedURLCollection create(List<String> urls) {
        return urlCollectionGateway.create(urls, idGenerator.generate());
    }
}
