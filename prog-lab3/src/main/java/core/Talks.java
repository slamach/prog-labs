package core;

import utility.ThingInterface;

public class Talks implements ThingInterface {
    private String name;

    public Talks() {
        name = "Разговоры";
        joinStory();
    }

    public Talks(String name) {
        this.name = name;
        joinStory();
    }

    private void joinStory() {
        System.out.println("Разговоры '" + name + "' присоединились к истории.");
    }
    
    public void notNeeded() {
        System.out.println("Разговоры '" + name + "' теперь ни к чему.");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Разговоры '" + name + "'";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Talks) {
            return name.equals(((Talks) obj).getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
