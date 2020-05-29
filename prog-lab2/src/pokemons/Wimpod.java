package pokemons;

import moves.*;
import ru.ifmo.se.pokemon.*;

public class Wimpod extends Pokemon {
    public Wimpod(String name, int level) {
        super(name, level);
        setStats(25, 35, 40, 20, 30, 80);
        setType(Type.BUG, Type.WATER);
        setMove(new Facade(), new Waterfall(), new DoubleTeam());
    }
}
