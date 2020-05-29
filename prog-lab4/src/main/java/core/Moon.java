package core;

import utility.ThingInterface;

public class Moon implements ThingInterface {
    private String name;
    private boolean full = false;

    public Moon() {
        name = "Луна";
    }

    public Moon(String name) {
        this.name = name;
    }

    public Moon(String name, boolean full) {
        this.name = name;
        this.full = full;
    }

    public String rise() {
        return this.toString() + " взошла.";
    }

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String ending = full ? "(полная)" : "(не полная)";
        return "Луна '" + name + "' " + ending;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Moon) {
            return name.equals(((Moon) obj).getName()) && full == ((Moon) obj).isFull();
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (full) return name.hashCode() + name.length();
        return name.hashCode();
    }
}
