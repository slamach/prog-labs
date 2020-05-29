package core;

import utility.ThingInterface;

public class Valleys implements ThingInterface {
    private String name;
    private boolean washed = true;

    public Valleys() {
        name = "Долины";
    }

    public Valleys(String name) {
        this.name = name;
    }

    public Valleys(String name, boolean washed) {
        this.name = name;
        this.washed = washed;
    }

    public String turnIntoSea() {
        return this.toString() + " превратились в море.";
    }

    public String gotoSun() {
        return this.toString() + " медленно выползали на солнечный свет.";
    }

    public boolean isWashed() {
        return washed;
    }

    public void setWashed(boolean washed) {
        this.washed = washed;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String ending = washed ? "(вымыты штормом)" : "(не вымыты штормом)";
        return "Долины '" + name + "' " + ending;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Valleys) {
            return name.equals(((Valleys) obj).getName()) && washed == ((Valleys) obj).isWashed();
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (washed) return name.hashCode() + name.length();
        return name.hashCode();
    }
}
