package core;

import utility.ThingInterface;

public class Trees implements ThingInterface {
    private String name;


    private class Crowns {
        public String wave() {
            return "Макушки деревьев '" + name + "' качаются."; 
        }
    }

    private class Branches {
        public String spread() {
            return "Ветки деревьев '" + name + "' расправились";
        } 
    }

    Crowns crowns = new Crowns();
    Branches branches = new Branches();


    public Trees() {
        name = "Деревья";
    }

    public Trees(String name) {
        this.name = name;
    }

    public String waveCrowns() {
        return crowns.wave();
    }

    public String spreadBranches() {
        return branches.spread();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Деревья '" + name + "'";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Trees) {
            return name.equals(((Trees) obj).getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
