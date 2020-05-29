package commands;

import java.time.LocalDateTime;

import data.SpaceMarine;
import exceptions.IncorrectInputInScriptException;
import exceptions.WrongAmountOfElementsException;
import utility.CollectionManager;
import utility.Console;
import utility.MarineAsker;

/**
 * Command 'add_if_min'. Adds a new element to collection if it's less than the minimal one.
 */
public class AddIfMinCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    private MarineAsker marineAsker;

    public AddIfMinCommand(CollectionManager collectionManager, MarineAsker marineAsker) {
        super("add_if_min {element}", "добавить новый элемент, если его значение меньше, чем у наименьшего");
        this.collectionManager = collectionManager;
        this.marineAsker = marineAsker;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
            SpaceMarine marineToAdd = new SpaceMarine(
                collectionManager.generateNextId(),
                marineAsker.askName(),
                marineAsker.askCoordinates(),
                LocalDateTime.now(),
                marineAsker.askHealth(),
                marineAsker.askCategory(),
                marineAsker.askWeaponType(),
                marineAsker.askMeleeWeapon(),
                marineAsker.askChapter()
            );
            if (collectionManager.collectionSize() == 0 || marineToAdd.compareTo(collectionManager.getFirst()) < 0) {
                collectionManager.addToCollection(marineToAdd);
                Console.println("Солдат успешно добавлен!");
                return true;
            } else Console.printerror("Значение солдата больше, чем значение наименьшего из солдат!");
        } catch (WrongAmountOfElementsException exception) {
            Console.println("Использование: '" + getName() + "'");
        } catch (IncorrectInputInScriptException exception) {}
        return false;
    }
}
