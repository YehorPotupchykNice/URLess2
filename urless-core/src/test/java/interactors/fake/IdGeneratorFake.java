package interactors.fake;

import generator.IdGenerator;
import lombok.Getter;

import java.util.*;

@Getter
public class IdGeneratorFake implements IdGenerator {
    private List<String> ids = new ArrayList<>();
    private int current = 0;
    private Set<String> collisions;

    @Override
    public String generate(String url, Set<String> collisions) {
        var r = ids.get(current);
        current = (current + 1) % ids.size();
        this.collisions = collisions;
        return r;
    }

    public void add(String... ids) {
        this.ids.addAll(Arrays.stream(ids).toList());
    }
}
