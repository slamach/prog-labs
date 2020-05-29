package core;

import utility.ThingInterface;
import utility.HopesIsAlreadyFulfilled;

public class Hopes implements ThingInterface {
    private String name;
    private boolean fulfilled = false;

    public Hopes() {
        name = "Надежды";
    }

    public Hopes(String name) {
        this.name = name;
    }

    public Hopes(String name, boolean fulfilled) {
        this.name = name;
        this.fulfilled = fulfilled;
    }

    public void fulfill() {
        if (fulfilled) {
            throw new HopesIsAlreadyFulfilled();
        } else {
            fulfilled = true;
        }
    }

    public boolean isFulfilled() {
        return fulfilled;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String ending = fulfilled ? "(исполнены)" : "(не исполнены)";
        return "Надежды '" + name + "' " + ending;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Hopes) {
            return name.equals(((Hopes) obj).getName()) && fulfilled == ((Hopes) obj).isFulfilled();
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (fulfilled) return name.hashCode() + name.length();
        return name.hashCode();
    }
}
