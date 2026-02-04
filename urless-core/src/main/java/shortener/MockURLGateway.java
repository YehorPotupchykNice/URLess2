package shortener;

import exceptions.URLAlreadyExists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockURLGateway implements URLGateway {

    Map<String, ShortenedURL> shortenedURLs = new HashMap<>();

    @Override
    public ShortenedURL create(String url, String id) {
        if (shortenedURLs.containsKey(id)){
            throw new URLAlreadyExists(url, id);
        }

        var su = new ShortenedURL (id, url);
        shortenedURLs.put(id, su);
        return su;
    }

    @Override
    public ShortenedURL getById(String id) {
        return shortenedURLs.get(id);
    }

    @Override
    public List<ShortenedURL> getAll() {
        return shortenedURLs.values().stream().toList();
    }
}
