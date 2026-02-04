package interactors.generator;

import generator.SHA1Generator;
import interactors.ShortenerInteractor;
import org.junit.jupiter.api.Test;
import shortener.MockURLGateway;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class SHA1GeneratorTests {

    @Test
    public void resolveCollisions() {
        var urlGateway = new MockURLGateway();
        var generator = new SHA1Generator();
        var shortener = new ShortenerInteractor(urlGateway, generator);
        var pattern = Pattern.compile("^[a-zA-Z0-9-=]{6}$").pattern();

        var r1 = shortener.create("https://example.com/sameagain");
        var r2 = shortener.create("https://example.com/sameagain");
        assertNotEquals(r1.getId(), r2.getId());
        assertTrue(r1.getId().matches(pattern));
        assertTrue(r2.getId().matches(pattern));
    }
}
