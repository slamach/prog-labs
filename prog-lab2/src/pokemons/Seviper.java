package pokemons;

import moves.*;
import ru.ifmo.se.pokemon.*;

public class Seviper extends Pokemon {
    public Seviper(String name, int level) {
        super(name, level);
        setStats(73, 100, 60, 100, 60, 65);
        setType(Type.POISON);
        setMove(new PoisonTail(), new Swagger(), new Rest(), new DarkPulse());
    }
}
