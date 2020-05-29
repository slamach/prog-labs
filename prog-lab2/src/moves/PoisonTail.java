package moves;

import ru.ifmo.se.pokemon.*;

public class PoisonTail extends PhysicalMove {
    public PoisonTail() {
        super(Type.POISON, 50, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        if (Math.random() < 0.1) {
            Effect.poison(p);
        }
    }

    @Override
    protected double calcCriticalHit(Pokemon att, Pokemon def) {
        if (Math.random() < (att.getStat(Stat.SPEED)/256.0)) {
            return 2.0;
        }
        else {
            return 1.0;
        }
    }
    
    @Override
    protected String describe() {
        return "использует Poison Tail";
    }
}
