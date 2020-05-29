package core;

import utility.ThingInterface;

public class Birds implements ThingInterface {
    private String name;

    public Birds() {
        name = "Птицы";
    }

    public Birds(String name) {
        this.name = name;
    }

    public String findNest() {
        return this.toString() + " отыскивали свои старые насиженные гнезда.";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Птицы '" + name + "'";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Birds) {
            return name.equals(((Birds) obj).getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
