package core;

import utility.NoHopesForSummer;
import utility.SeasonAbstract;
import utility.SeasonType;

public class Summer extends SeasonAbstract {
    private String name;
    private final SeasonType TYPE = SeasonType.SUMMER;

    public Summer() {
        super(true);
        name = "Лето";
    }

    public Summer(String name) {
        super(true);
        this.name = name;
    }

    public Summer(String name, boolean lengthy) {
        super(lengthy);
        this.name = name;
    }

    public String fulfillHopes(Hopes obj) throws NoHopesForSummer {
        if (obj instanceof Hopes) {
            obj.fulfill();
            return this.toString() + " сулило исполнение всех надежд '" + obj.getName() + "'...";
        }
        throw new NoHopesForSummer();
    }

    @Override
    public SeasonType getType() {
        return TYPE;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        if (isLengthy()) return "Долгое лето '" + name + "'";
        return "Лето '" + name + "'";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Summer) {
            return name.equals(((Summer) obj).getName()) && isLengthy() == ((Summer) obj).isLengthy();
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (isLengthy()) return name.hashCode() + name.length();
        return name.hashCode();
    }
}
