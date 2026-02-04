package interactors;

import exceptions.FailedToCreateURLException;
import exceptions.URLAlreadyExists;
import generator.IdGenerator;
import shortener.ShortenedURL;
import shortener.URLGateway;
import usecases.ShortenerUseCase;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ShortenerInteractor implements ShortenerUseCase {

    private final URLGateway urlGateway;
    private final IdGenerator idGenerator;

    public ShortenerInteractor(URLGateway urlGateway, IdGenerator idGenerator) {
        this.urlGateway = urlGateway;
        this.idGenerator = idGenerator;
    }

    @Override
    public Optional<ShortenedURL> getById(String id) {
        return Optional.ofNullable(urlGateway.getById(id));
    }

    @Override
    public ShortenedURL create(String url) {
        var collision = false;
        Set<String> collisions = new HashSet<>();
        do {
            try {
                var id = idGenerator.generate(url, collisions);
                return urlGateway.create(url, id);
            } catch (URLAlreadyExists e) {
                collision = true;
                collisions.add(e.getId());
            }
        } while (collision);

        throw new FailedToCreateURLException(url);
    }
}
