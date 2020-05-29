package core;

import utility.SeasonAbstract;
import utility.SeasonType;

public class Summer extends SeasonAbstract {
    private String name;
    private final SeasonType TYPE = SeasonType.SUMMER;

    public Summer() {
        super(true);
        name = "Лето";
        joinStory();
    }

    public Summer(String name) {
        super(true);
        this.name = name;
        joinStory();
    }

    public Summer(String name, boolean lengthy) {
        super(lengthy);
        this.name = name;
        joinStory();
    }

    private void joinStory() {
        if (isLengthy()) {
            System.out.println("Долгое лето '" + name + "' присоединилось к истории.");
        } else {
            System.out.println("Лето '" + name + "' присоединилось к истории.");
        }
    }

    public void fulfillHopes(Hopes obj) {
        if (obj instanceof Hopes) {
            System.out.println("Лето '" + name + "' сулило исполнение всех надежд '" + obj.getName() + "'...");
            obj.fulfill();
        }
        System.out.println("Лето '" + name + "' сулило исполнение всех надежд, но их не оказалось рядом...");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public SeasonType getType() {
        return TYPE;
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
