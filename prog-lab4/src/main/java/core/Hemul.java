package core;

import utility.ThingInterface;

public class Hemul implements ThingInterface {
    private String name;
    private boolean seen;


    public static class PoliceWhistle implements ThingInterface {
        private String name;

        public PoliceWhistle() {
            name = "Полицейский свисток";
        }

        public PoliceWhistle(String name) {
            this.name = name;
        }

        public String cutNight() {
            return this.toString() + " тревожно разрезал ночь.";
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Полицейский свисток '" + name + "' ";
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj instanceof PoliceWhistle) {
                return name.equals(((PoliceWhistle) obj).getName());
            }
            return false;
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }
    

    public Hemul() {
        name = "Хемуль";
    }

    public Hemul(String name) {
        this.name = name;
    }

    public String getAwayFromStage() {
        return this.toString() + " вырался с вращающейся сцены.";
    }

    public String escapeFromBadGuys() {
        class BadGuys {};

        BadGuys forestGuys = new BadGuys() {
            @Override
            public String toString() {
                return "'Лесные малыши'";
            }
        };
        BadGuys spectators = new BadGuys() {
            public String voice() {
                return this.toString() + " кричат 'Ура!'.";
            }

            public String throwFlowers(ThingInterface obj) {
                return this.toString() + " осыпают цветами " + obj + ".";
            }
            
            @Override
            public String toString() {
                return "'Зрители'";
            }
        };

        return this.toString() + " высвободился от " + forestGuys + " и " + spectators + ".";
    }

    public String seatIntoBoat(boolean angry) {
        String ifAngry = angry ? ", ругаясь," : "";
        return this.toString() + ifAngry + " уселся в лодку.";
    }

    public String seatIntoBoat() {
        return seatIntoBoat(false);
    }

    public String catchIt(ThingInterface obj) {
        return this.toString() + " пустился вдогонку за " + obj + ".";
    }

    public String getLate() {
        return this.toString() + " опоздал.";
    }

    public String goForward() {
        return this.toString() + " уверенно держал курс вперед.";
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Хемуль '" + name + "' ";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Hemul) {
            return name.equals(((Hemul) obj).getName()) && seen == ((Hemul) obj).isSeen();
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (seen) return name.hashCode() + name.length();
        return name.hashCode();
    }
}
