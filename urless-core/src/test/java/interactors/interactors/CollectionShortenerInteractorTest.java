package interactors.interactors;

import generator.SHA1Generator;
import interactors.CollectionShortenerInteractor;
import interactors.ShortenerInteractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shortener.MockURLCollectionGateway;
import shortener.MockURLGateway;
import shortener.ShortenedURL;
import shortener.URLCollectionGateway;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CollectionShortenerInteractorTest {
    static CollectionShortenerInteractor interactor;
    static URLCollectionGateway urlCollectionGateway;

    @BeforeEach
    public void setup() {
        var shortenerInteractor = new ShortenerInteractor(new MockURLGateway(), new SHA1Generator());
        urlCollectionGateway = new MockURLCollectionGateway(shortenerInteractor);
        interactor = new CollectionShortenerInteractor(urlCollectionGateway);
    }

    @Test
    public void testNonExistent() {
        var actual = interactor.getById("non-existent");

        assertEquals(Optional.empty(), actual);
    }

    @Test
    public void testExistent() {
        var urls = Arrays.asList("https://example.com/1", "https://example.com/2");
        urlCollectionGateway.create(urls, "asdfg");

        var actual = interactor.getById("asdfg").get();

        assertNotNull(actual);
        assertEquals("asdfg", actual.getId());
        assertTrue(actual.getUrls().stream().map(ShortenedURL::getUrl).toList().containsAll(urls));
    }
}
