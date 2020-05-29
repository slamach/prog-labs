package commands;

import java.time.LocalDateTime;

import data.SpaceMarine;
import exceptions.CollectionIsEmptyException;
import exceptions.IncorrectInputInScriptException;
import exceptions.MarineNotFoundException;
import exceptions.WrongAmountOfElementsException;
import utility.CollectionManager;
import utility.Console;
import utility.MarineAsker;

/**
 * Command 'remove_greater'. Removes elements greater than user entered.
 */
public class RemoveGreaterCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    private MarineAsker marineAsker;

    public RemoveGreaterCommand(CollectionManager collectionManager, MarineAsker marineAsker) {
        super("remove_greater {element}", "удалить из коллекции все элементы, превышающие заданный");
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
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            SpaceMarine marineToFind = new SpaceMarine(
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
            SpaceMarine marineFromCollection = collectionManager.getByValue(marineToFind);
            if (marineFromCollection == null) throw new MarineNotFoundException();
            collectionManager.removeGreater(marineFromCollection);
            Console.println("Солдаты успешно удалены!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            Console.println("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            Console.printerror("Коллекция пуста!");
        } catch (MarineNotFoundException exception) {
            Console.printerror("Солдата с такими характеристиками в коллекции нет!");
        } catch (IncorrectInputInScriptException exception) {}
        return false;
    }
}
