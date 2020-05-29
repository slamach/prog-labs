package core;

public class BrokenTrees extends Trees {
    public BrokenTrees() {
        super();
    }

    public BrokenTrees(String name) {
        super(name);
    }

    public String haste() {
        return this.toString() + " торопились выпустить новые побеги.";
    }

    @Override
    public String toString() {
        return "Сломанные деревья '" + super.getName() + "'";
    }
}
