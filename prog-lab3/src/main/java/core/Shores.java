package core;

import utility.ThingInterface;

public class Shores implements ThingInterface {
    private String name;
    private boolean hurry;

    public Shores() {
        name = "Берега";
        hurry = false;
        joinStory();
    }

    public Shores(String name) {
        this.name = name;
        hurry = false;
        joinStory();
    }

    public Shores(String name, boolean hurry) {
        this.name = name;
        this.hurry = hurry;
        joinStory();
    }

    private void joinStory() {
        System.out.print("Берега '" + name + "' присоединились к истории ");
        if (hurry) {
            System.out.println("(они куда-то спешат).");
        } else {
            System.out.println("(им некуда спешить).");
        }
    }
    
    public void turnIntoLine() {
        System.out.println("Берега '" + name + "' превратились в темную полоску.");
    }

    public void becomeImpressed() {
        System.out.println("Берега '" + name + "' под впечатлением...");
    }

    public void becomeImpressed(String[] impressions) {
        int length = impressions.length;
        System.out.print("Берега '" + name + "' под впечатлением ");
        for (int i=0; i<length; i++) {
            if (i != 0) System.out.print(", ");
            System.out.print(impressions[i]);
        }
        System.out.println(".");
    }

    public boolean isHurry() {
        return hurry;
    }

    public void setHurry(boolean hurry) {
        if (hurry != this.hurry) {
            this.hurry = hurry;
            if (hurry) {
                System.out.println("Берега '" + name + "' теперь куда-то спешат.");
            } else {
                System.out.println("Берегам '" + name + "' теперь некуда спешить.");
            }
        } else {
            if (hurry) {
                System.out.println("Берега '" + name + "' уже и так куда-то спешат.");
            } else {
                System.out.println("Берегам '" + name + "' уже и так некуда спешить.");
            }
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Берега '" + name + "'";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Shores) {
            return name.equals(((Shores) obj).getName()) && hurry == ((Shores) obj).isHurry();
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (hurry) return name.hashCode() + name.length();
        return name.hashCode();
    }
}
