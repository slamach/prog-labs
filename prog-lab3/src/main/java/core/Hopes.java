package core;

import utility.ThingInterface;

public class Hopes implements ThingInterface {
    private String name;
    private boolean fulfilled;

    public Hopes() {
        name = "Надежды";
        fulfilled = false;
        joinStory();
    }

    public Hopes(String name) {
        this.name = name;
        fulfilled = false;
        joinStory();
    }

    private void joinStory() {
        System.out.println("Надежды '" + name + "' присоединились к истории.");
    }

    public void fulfill() {
        if (!fulfilled) {
            fulfilled = true;
            System.out.println("Надежды '" + name + "' исполнены!");
        } else {
            System.out.println("Надежды '" + name + "' уже и так исполнены!");
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
        String ending;
        if (fulfilled) {
            ending = "исполнены";
        } else {
            ending = "не исполнены";
        }
        return "Надежды '" + name + "', " + ending;
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
