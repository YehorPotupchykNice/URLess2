package shortener;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ShortenedURLCollection {
    private final String id;
    private final List<ShortenedURL> urls;
}
