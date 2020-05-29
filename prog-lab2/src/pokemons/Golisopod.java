package pokemons;

import moves.*;

public class Golisopod extends Wimpod {
    public Golisopod(String name, int level) {
        super(name, level);
        setStats(75, 125, 140, 60, 90, 40);
        addMove(new Liquidation());
    }
}
