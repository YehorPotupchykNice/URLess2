package generator;

import java.util.Set;

public interface IdGenerator {
    String generate(String url, Set<String> collisions);
}
