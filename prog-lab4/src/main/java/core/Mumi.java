package core;

import utility.ThingInterface;

public class Mumi implements ThingInterface {
    private String name;

    public Mumi() {
        name = "Муми-тролль";
    }

    public Mumi(String name) {
        this.name = name;
    }

    public String watchAt() {
        return this.toString() + " смотрел вдаль...";
    }

    public String watchAt(String[] itemsToWatch) {
        String result = this.toString() + " смотрел на ";
        for (int i=0; i<itemsToWatch.length; i++) {
            if (i != 0) result += ", ";
            result += itemsToWatch[i];
        }
        return result + ".";
    }

    public String thinkAbout(String thoughts) {
        return this.toString() + " подумал: '" + thoughts + "'.";
    }

    public String consider(String thoughts) {
        return this.toString() + " понял: '" + thoughts + "'.";
    }

    public String getOutOfBoat() {
        return this.toString() + " вылез из лодки.";
    }

    public String goShores() {
        return this.toString() + " зашагал берегом назад.";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Муми-тролль '" + name + "'";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Mumi) {
            return name.equals(((Mumi) obj).getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
