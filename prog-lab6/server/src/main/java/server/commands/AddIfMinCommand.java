package server.commands;

import common.data.SpaceMarine;
import common.exceptions.WrongAmountOfElementsException;
import common.interaction.MarineRaw;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

import java.time.LocalDateTime;

/**
 * Command 'add_if_min'. Adds a new element to collection if it's less than the minimal one.
 */
public class AddIfMinCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public AddIfMinCommand(CollectionManager collectionManager) {
        super("add_if_min", "{element}", "добавить новый элемент, если его значение меньше, чем у наименьшего");
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
            MarineRaw marineRaw = (MarineRaw) objectArgument;
            SpaceMarine marineToAdd = new SpaceMarine(
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
            if (collectionManager.collectionSize() == 0 || marineToAdd.compareTo(collectionManager.getFirst()) < 0) {
                collectionManager.addToCollection(marineToAdd);
                ResponseOutputer.appendln("Солдат успешно добавлен!");
                return true;
            } else ResponseOutputer.appenderror("Значение солдата больше, чем значение наименьшего из солдат!");
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Использование: '" + getName() + " " + getUsage() + "'");
        } catch (ClassCastException exception) {
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        }
        return false;
    }
}
