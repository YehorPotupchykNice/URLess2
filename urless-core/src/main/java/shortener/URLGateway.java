package shortener;

import java.util.List;

public interface URLGateway {
    ShortenedURL create(String url, String id);

    ShortenedURL getById(String id);

    List<ShortenedURL> getAll();
}
