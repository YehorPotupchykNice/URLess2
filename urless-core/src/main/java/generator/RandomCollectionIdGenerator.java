package generator;

import java.util.Base64;
import java.util.Random;

public class RandomCollectionIdGenerator implements CollectionIdGenerator {

    private final Random random = new Random();

    @Override
    public String generate() {
        var bytes = new byte[4];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().encodeToString(bytes).substring(0, 5);
    }
}
