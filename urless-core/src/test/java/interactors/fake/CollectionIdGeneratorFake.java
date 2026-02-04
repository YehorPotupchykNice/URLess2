package interactors.fake;

import generator.CollectionIdGenerator;

public class CollectionIdGeneratorFake implements CollectionIdGenerator {
    public String generate() {
        return "42";
    }
}
