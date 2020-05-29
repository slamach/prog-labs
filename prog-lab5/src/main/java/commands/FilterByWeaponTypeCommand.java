package commands;

import data.Weapon;
import exceptions.CollectionIsEmptyException;
import exceptions.WrongAmountOfElementsException;
import utility.CollectionManager;
import utility.Console;

/**
 * Command 'filter_by_weapon_type'. Filters the collection by weapon type.
 */
public class FilterByWeaponTypeCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public FilterByWeaponTypeCommand(CollectionManager collectionManager) {
        super("filter_by_weapon_type <weaponType>", "вывести элементы, значение поля weaponType которых равно заданному");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (argument.isEmpty()) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            Weapon weapon = Weapon.valueOf(argument.toUpperCase());
            String filteredInfo = collectionManager.weaponFilteredInfo(weapon);
            if (!filteredInfo.isEmpty()) {
                Console.println(filteredInfo);
                return true;
            } else Console.println("В коллекции нет солдат с выбранным типом оружия!");
        } catch (WrongAmountOfElementsException exception) {
            Console.println("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            Console.printerror("Коллекция пуста!");
        } catch (IllegalArgumentException exception) {
            Console.printerror("Оружия нет в списке!");
            Console.println("Список оружия дальнего боя - " + Weapon.nameList());
        }
        return false;
    }
}
