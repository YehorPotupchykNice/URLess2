package usecases;

import shortener.ShortenedURL;

import java.util.Optional;

public interface ShortenerUseCase {
    Optional<ShortenedURL> getById(String id);

    ShortenedURL create(String url);
}
