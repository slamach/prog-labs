package server.commands;

import common.data.SpaceMarine;
import common.exceptions.CollectionIsEmptyException;
import common.exceptions.MarineNotFoundException;
import common.exceptions.WrongAmountOfElementsException;
import common.interaction.MarineRaw;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

import java.time.LocalDateTime;

/**
 * Command 'remove_greater'. Removes elements greater than user entered.
 */
public class RemoveGreaterCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public RemoveGreaterCommand(CollectionManager collectionManager) {
        super("remove_greater", "{element}", "удалить из коллекции все элементы, превышающие заданный");
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
            if (!stringArgument.isEmpty() || objectArgument == null) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            MarineRaw marineRaw = (MarineRaw) objectArgument;
            SpaceMarine marineToFind = new SpaceMarine(
                    collectionManager.generateNextId(),
                    marineRaw.getName(),
                    marineRaw.getCoordinates(),
                    LocalDateTime.now(),
                    marineRaw.getHealth(),
                    marineRaw.getCategory(),
                    marineRaw.getWeaponType(),
                    marineRaw.getMeleeWeapon(),
                    marineRaw.getChapter()
            );
            SpaceMarine marineFromCollection = collectionManager.getByValue(marineToFind);
            if (marineFromCollection == null) throw new MarineNotFoundException();
            collectionManager.removeGreater(marineFromCollection);
            ResponseOutputer.appendln("Солдаты успешно удалены!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Использование: '" + getName() + " " + getUsage() + "'");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appenderror("Коллекция пуста!");
        } catch (MarineNotFoundException exception) {
            ResponseOutputer.appenderror("Солдата с такими характеристиками в коллекции нет!");
        } catch (ClassCastException exception) {
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        }
        return false;
    }
}
