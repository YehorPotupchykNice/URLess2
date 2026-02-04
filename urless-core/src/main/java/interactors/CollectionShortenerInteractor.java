package interactors;

import shortener.ShortenedURLCollection;
import usecases.CollectionShortenerUseCase;

import java.util.List;
import java.util.Optional;

public class CollectionShortenerInteractor implements CollectionShortenerUseCase {
    @Override
    public Optional<ShortenedURLCollection> getById(String id) {
        return Optional.empty();
    }

    @Override
    public ShortenedURLCollection create(List<String> url) {
        return null;
    }
}
