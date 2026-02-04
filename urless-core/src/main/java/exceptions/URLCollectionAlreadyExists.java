package exceptions;

import lombok.Getter;

@Getter
public class URLCollectionAlreadyExists extends RuntimeException {
    private final String id;

    public URLCollectionAlreadyExists(String id) {
        this.id = id;
    }
}
