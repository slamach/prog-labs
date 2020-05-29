package moves;

import ru.ifmo.se.pokemon.*;

public class DarkPulse extends SpecialMove {
    public DarkPulse() {
        super(Type.DARK, 80, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        if (Math.random() < 0.2) {
            Effect.flinch(p);
        }
    }

    @Override
    protected String describe() {
        return "использует Dark Pulse";
    }
}
