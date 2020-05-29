package moves;

import ru.ifmo.se.pokemon.*;

public class WakeUpSlap extends PhysicalMove {
    public WakeUpSlap() {
        super(Type.FIGHTING, 70, 100);
    }

    @Override
    protected void applyOppDamage(Pokemon def, double damage) {
        Status p_stat = def.getCondition();
        if (p_stat.equals(Status.SLEEP)) {
            Effect e = new Effect().condition(Status.NORMAL);
            def.setCondition(e);
            super.applyOppDamage(def, damage*2);
        }
        else {
            super.applyOppDamage(def, damage);
        }
    }
    
    @Override
    protected String describe() {
        return "использует Wake-Up Slap";
    }
}
