package server.commands;

import common.exceptions.CollectionIsEmptyException;
import common.exceptions.WrongAmountOfElementsException;
import common.interaction.User;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

/**
 * Command 'max_by_melee_weapon'. Prints the element of the collection with maximum melee weapon.
 */
public class MaxByMeleeWeaponCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public MaxByMeleeWeaponCommand(CollectionManager collectionManager) {
        super("max_by_melee_weapon", "", "вывести элемент, значение поля meleeWeapon которого максимально");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     *
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument, User user) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            ResponseOutputer.appendln(collectionManager.maxByMeleeWeapon());
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Использование: '" + getName() + " " + getUsage() + "'");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appenderror("Коллекция пуста!");
        }
        return true;
    }
}
