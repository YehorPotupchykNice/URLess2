package interactors.interactors;

import interactors.CollectionShortenerInteractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CollectionShortenerInteractorTest {
    static CollectionShortenerInteractor interactor;

    @BeforeEach
    public void setup() {
        interactor = new CollectionShortenerInteractor();
    }

    @Test
    public void testNonExistent() {
        var actual = interactor.getById("non-existent");

        assertEquals(Optional.empty(), actual);
    }
}
