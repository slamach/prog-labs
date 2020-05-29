package moves;

import ru.ifmo.se.pokemon.*;

public class Flamethrower extends SpecialMove {
    public Flamethrower() {
        super(Type.FIRE, 90, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        if (Math.random() < 0.1) {
            Effect.burn(p);
        }
    }
    
    @Override
    protected String describe() {
        return "использует Flamethrower";
    }
}
