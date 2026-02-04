package exceptions;

import lombok.Getter;

@Getter
public class FailedToCreateURLException extends RuntimeException {
    private final String url;

    public FailedToCreateURLException(String url) {
        super("Failed to create URL " + url);
        this.url = url;
    }
}
