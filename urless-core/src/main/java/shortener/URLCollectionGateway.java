package shortener;

import java.util.List;

public interface URLCollectionGateway {
    ShortenedURLCollection create(List<String> urlIds, String id);

    ShortenedURLCollection getById(String id);

    List<ShortenedURLCollection> getAll();
}
