package core;

import utility.ThingInterface;

public class Boat implements ThingInterface {
    private String name;

    public Boat() {
        name = "Лодка";
    }

    public Boat(String name) {
        this.name = name;
    }

    public String semicircle() {
        return this.toString() + " стала описывать полукруг, направляясь к берегу.";
    }

    public String crash() {
        return this.toString() + " врезалась в камыши под деревьями.";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Лодка '" + name + "'";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Boat) {
            return name.equals(((Boat) obj).getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
