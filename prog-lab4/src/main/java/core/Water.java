package core;

import utility.ThingInterface;

public class Water implements ThingInterface {
    private String name;
    private boolean raised = true;

    public Water() {
        name = "Вода";
    }

    public Water(String name) {
        this.name = name;
    }

    public Water(String name, boolean raised) {
        this.name = name;
        this.raised = raised;
    }

    public String becomeFalling() {
        return this.toString() + " стала спадать.";
    }

    public boolean isRaised() {
        return raised;
    }

    public void setRaised(boolean raised) {
        this.raised = raised;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String ending = raised ? "(поднята)" : "(не поднята)";
        return "Вода '" + name + "' " + ending;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Water) {
            return name.equals(((Water) obj).getName()) && raised == ((Water) obj).isRaised();
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (raised) return name.hashCode() + name.length();
        return name.hashCode();
    }
}
