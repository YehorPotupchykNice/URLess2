package interactors;

import shortener.ShortenedURLCollection;
import shortener.URLCollectionGateway;
import usecases.CollectionShortenerUseCase;

import java.util.List;
import java.util.Optional;

public class CollectionShortenerInteractor implements CollectionShortenerUseCase {

    private final URLCollectionGateway  urlCollectionGateway;

    public CollectionShortenerInteractor(URLCollectionGateway urlCollectionGateway) {
        this.urlCollectionGateway = urlCollectionGateway;
    }

    @Override
    public Optional<ShortenedURLCollection> getById(String id) {
        return Optional.ofNullable(urlCollectionGateway.getById(id));
    }

    @Override
    public ShortenedURLCollection create(List<String> url) {
        return null;
    }
}
