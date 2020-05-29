package core;

import utility.ThingInterface;

public class Snusmumrik implements ThingInterface {

    // TODO: вынести трубку в отдельный вложенный класс

    private String name;

    public Snusmumrik() {
        name = "Снусмумрик";
    }

    public Snusmumrik(String name) {
        this.name = name;
    }

    public String hideInNight() {
        return this.toString() + " исчез в ночи.";
    }

    public String oar(boolean silent) {
        String ending = silent ? ", не произнося ни слова." : ".";
        return this.toString() + " грёб" + ending;
    }

    public String oar() {
        return oar(true);
    }

    public String seatOnShit() {
        return this.toString() + " сел на корму.";
    }

    public String laugh(boolean lightly) {
        String ifLaugh = lightly ? " тихонько" : "";
        return this.toString() + ifLaugh + " рассмеялся.";
    }

    public String laugh() {
        return laugh(true);
    }

    public String startFillingPipe() {
        return this.toString() + " начал набивать трубку.";
    }

    public String cleanPipe(boolean carefully) {
        String ifCarefully = carefully ? " осторожно" : "";
        return this.toString() + ifCarefully + " выбил пепел из трубки.";
    }

    public String cleanPipe() {
        return cleanPipe(true);
    }

    public String getDown() {
        return this.toString() + " наклонился.";
    }

    public String lookOverReeds() {
        return this.toString() + " выглянул из-за камышей.";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Снусмумрик '" + name + "'";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Snusmumrik) {
            return name.equals(((Snusmumrik) obj).getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
