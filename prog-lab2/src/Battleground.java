import pokemons.*;
import ru.ifmo.se.pokemon.Battle;

class Battleground {
    public static void main(String args[]) {
        Battle b = new Battle();

        Seviper p1 = new Seviper("Батя", 1);
        Wimpod p2 = new Wimpod("Брат", 2);
        Clefairy p3 = new Clefairy("Зять", 2);
        Golisopod p4 = new Golisopod("Дед", 1);
        Cleffa p5 = new Cleffa("Дядя", 3);
        Clefable p6 = new Clefable("Сосед", 1);

        b.addAlly(p1);
        b.addAlly(p2);
        b.addAlly(p3);

        b.addFoe(p4);
        b.addFoe(p5);
        b.addFoe(p6);
        
        b.go();
    }
}
