package commands;

import java.time.LocalDateTime;

import data.SpaceMarine;
import exceptions.IncorrectInputInScriptException;
import exceptions.WrongAmountOfElementsException;
import utility.CollectionManager;
import utility.Console;
import utility.MarineAsker;

/**
 * Command 'add'. Adds a new element to collection.
 */
public class AddCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    private MarineAsker marineAsker;

    public AddCommand(CollectionManager collectionManager, MarineAsker marineAsker) {
        super("add {element}", "добавить новый элемент в коллекцию");
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
            collectionManager.addToCollection(new SpaceMarine(
                collectionManager.generateNextId(),
                marineAsker.askName(),
                marineAsker.askCoordinates(),
                LocalDateTime.now(),
                marineAsker.askHealth(),
                marineAsker.askCategory(),
                marineAsker.askWeaponType(),
                marineAsker.askMeleeWeapon(),
                marineAsker.askChapter()
            ));
            Console.println("Солдат успешно добавлен!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            Console.println("Использование: '" + getName() + "'");
        } catch (IncorrectInputInScriptException exception) {}
        return false;
    }
}
