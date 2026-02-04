package exceptions;

import lombok.Getter;

@Getter
public class URLAlreadyExists extends RuntimeException {
    private final String url;
    private final String id;

    public URLAlreadyExists(String url, String id) {
        super("URL " + url + " already exists");
        this.url = url;
        this.id = id;
    }
}
