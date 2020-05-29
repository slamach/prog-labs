package core;

import utility.ThingInterface;

public class Shores implements ThingInterface {
    private String name;
    private boolean hurry = false;
    private boolean washed = true;

    public Shores() {
        name = "Берега";
    }

    public Shores(String name) {
        this.name = name;
    }

    public Shores(String name, boolean hurry, boolean washed) {
        this.name = name;
        this.hurry = hurry;
        this.washed = washed;
    }
    
    public String turnIntoLine() {
        return this.toString() + " превратились в темную полоску.";
    }

    public String gotoSun() {
        return this.toString() + " медленно выползали на солнечный свет.";
    }

    public String becomeImpressed() {
        return this.toString() + " под впечатлением...";
    }

    public String becomeImpressed(String[] impressions) {
        String result = this.toString() + " под впечатлением ";
        for (int i=0; i<impressions.length; i++) {
            if (i != 0) result += ", ";
            result += impressions[i];
        }
        return result + ".";
    }

    public boolean isHurry() {
        return hurry;
    }

    public void setHurry(boolean hurry) {
        this.hurry = hurry;
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
        String ending;
        String wash_add = "";

        if (washed) wash_add = "не ";
        if (hurry) {
            ending = "(спешат, " + wash_add + "вымыты штормом)";
        } else {
            ending = "(не спешат, " + wash_add + "вымыты штормом)";
        }

        return "Берега '" + name + "' " + ending;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Shores) {
            return name.equals(((Shores) obj).getName()) &&
                   hurry == ((Shores) obj).isHurry() &&
                   washed == ((Shores) obj).isWashed();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = name.hashCode();
        if (hurry) hash += name.length();
        if (washed) hash += 69;
        return hash;
    }
}
