package usecases;

import shortener.ShortenedURLCollection;

import java.util.List;
import java.util.Optional;

public interface CollectionShortenerUseCase {
    Optional<ShortenedURLCollection> getById(String id);

    ShortenedURLCollection create(List<String> url);
}
