package interactors.interactors;

import interactors.CollectionShortenerInteractor;
import interactors.ShortenerInteractor;
import interactors.fake.IdGeneratorFake;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import shortener.MockURLGateway;
import shortener.URLGateway;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ShortenerInteractorTest {

    static URLGateway urlGateway;
    static ShortenerInteractor shortenerInteractor;
    static IdGeneratorFake idGenerator;
    static CollectionShortenerInteractor interactor;

    @BeforeAll
    static void setup() {
        urlGateway = new MockURLGateway();
        idGenerator = new IdGeneratorFake();
        shortenerInteractor = new ShortenerInteractor(urlGateway, idGenerator);

    }

    @Test
    public void testCreate() {
        ShortenerInteractor interactor = new ShortenerInteractor(new MockURLGateway(), new IdGeneratorFake());
    }

    @Test
    public void testNonExistent() {
        var actual = shortenerInteractor.getById("non-existent");

        assertEquals(Optional.empty(), actual);
    }

    @Test
    public void testExistent() {
        urlGateway.create("https://example.com", "qwerty");
        var actual = shortenerInteractor.getById("qwerty").get();
        assertEquals("qwerty", actual.getId());
        assertEquals("https://example.com", actual.getUrl());
    }

    @Test
    public void createAndReturnURL() {
        idGenerator.add("asdfgh");
        var r = shortenerInteractor.create("https://example.com");

        assertEquals("asdfgh", r.getId());
        assertEquals("https://example.com", r.getUrl());
    }

    @Test
    public void returnDifferentIDs() {
        idGenerator.add("zxcvbn", "zxcvbn", "zxcvbn","poiuyt");

        var r1 = shortenerInteractor.create("https://example.com/same");
        var r2 = shortenerInteractor.create("https://example.com/same");

        assertNotEquals(r1.getId(), r2.getId());
        assertEquals("poiuyt", r2.getId());
        assertEquals(1, idGenerator.getCollisions().size());
        assertTrue(idGenerator.getCollisions().contains("zxcvbn"));
    }
}
