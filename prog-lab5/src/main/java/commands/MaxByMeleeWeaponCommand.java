package commands;

import exceptions.CollectionIsEmptyException;
import exceptions.WrongAmountOfElementsException;
import utility.CollectionManager;
import utility.Console;

/**
 * Command 'max_by_melee_weapon'. Prints the element of the collection with maximum melee weapon.
 */
public class MaxByMeleeWeaponCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public MaxByMeleeWeaponCommand(CollectionManager collectionManager) {
        super("max_by_melee_weapon", "вывести элемент, значение поля meleeWeapon которого максимально");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
            Console.println(collectionManager.maxByMeleeWeapon());
            return true;
        } catch (WrongAmountOfElementsException exception) {
            Console.println("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            Console.printerror("Коллекция пуста!");
        }
        return true;
    }
}
