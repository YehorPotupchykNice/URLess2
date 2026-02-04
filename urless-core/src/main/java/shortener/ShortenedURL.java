package shortener;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ShortenedURL {
    private final String id;
    private final String url;
}
