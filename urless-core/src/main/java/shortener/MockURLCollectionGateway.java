package shortener;

import interactors.ShortenerInteractor;
import usecases.ShortenerUseCase;

import java.util.HashMap;
import java.util.List;

public class MockURLCollectionGateway implements URLCollectionGateway {
    private final HashMap<String, ShortenedURLCollection> collections = new HashMap<>();
    private final ShortenerUseCase urlShortener;

    public MockURLCollectionGateway(ShortenerUseCase urlShortener) {
        this.urlShortener = urlShortener;
    }

    @Override
    public ShortenedURLCollection create(List<String> urls, String id) {
        var shortenedURLS = urls.stream().map(urlShortener::create).toList();
        var r = new ShortenedURLCollection(id, shortenedURLS);
        collections.put(id, r);
        return r;
    }

    @Override
    public ShortenedURLCollection getById(String id) {
        return collections.get(id);
    }

    @Override
    public List<ShortenedURLCollection> getAll() {
        return collections.values().stream().toList();
    }
}
