package server.commands;

import common.data.SpaceMarine;
import common.exceptions.CollectionIsEmptyException;
import common.exceptions.MarineNotFoundException;
import common.exceptions.WrongAmountOfElementsException;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

/**
 * Command 'remove_by_id'. Removes the element by its ID.
 */
public class RemoveByIdCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove_by_id", "<ID>", "удалить элемент из коллекции по ID");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     *
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            Long id = Long.parseLong(stringArgument);
            SpaceMarine marineToRemove = collectionManager.getById(id);
            if (marineToRemove == null) throw new MarineNotFoundException();
            collectionManager.removeFromCollection(marineToRemove);
            ResponseOutputer.appendln("Солдат успешно удален!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Использование: '" + getName() + " " + getUsage() + "'");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appenderror("Коллекция пуста!");
        } catch (NumberFormatException exception) {
            ResponseOutputer.appenderror("ID должен быть представлен числом!");
        } catch (MarineNotFoundException exception) {
            ResponseOutputer.appenderror("Солдата с таким ID в коллекции нет!");
        }
        return false;
    }
}
