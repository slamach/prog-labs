package core;

import utility.ThingInterface;

public class Boat implements ThingInterface {
    private String name;

    public Boat() {
        name = "Лодка";
        joinStory();
    }

    public Boat(String name) {
        this.name = name;
        joinStory();
    }

    private void joinStory() {
        System.out.println("Лодка '" + name + "' присоединилась к истории.");
    }

    public void semicircle() {
        System.out.println("Лодка '" + name + "' стала описывать полукруг, направляясь к берегу.");
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
