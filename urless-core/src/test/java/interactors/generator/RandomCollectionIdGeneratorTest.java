package interactors.generator;

import generator.RandomCollectionIdGenerator;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import static org.junit.jupiter.api.Assertions.*;

public class RandomCollectionIdGeneratorTest {
    @Test
    public void testMinimalRandomness()
    {
        var generator = new RandomCollectionIdGenerator();
        var ids = new HashSet<String>();
        for (int i = 0; i < 100; i++) {
            ids.add(generator.generate());
        }
        assertEquals(100,ids.size());

    }
}
