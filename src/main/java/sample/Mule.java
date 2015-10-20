package sample;

/**
 * Created by AlexandraLink on 10/5/15.
 */
public class Mule {

    public enum Configuration {ENERGY, FOOD, ORE};

    private Configuration type = Configuration.ENERGY;

    public Mule(Configuration type) {
        this.type = type;
    }

    public Mule() { this.type = Configuration.ENERGY; }

    public void setType(Configuration type) {
        this.type = type;
    }

    public Configuration getType() {
        return type;
    }
}
