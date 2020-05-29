package server.commands;

import common.data.*;
import common.exceptions.CollectionIsEmptyException;
import common.exceptions.MarineNotFoundException;
import common.exceptions.WrongAmountOfElementsException;
import common.interaction.MarineRaw;
import server.utility.CollectionManager;
import server.utility.ResponseOutputer;

import java.time.LocalDateTime;

/**
 * Command 'update'. Updates the information about selected marine.
 */
public class UpdateCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public UpdateCommand(CollectionManager collectionManager) {
        super("update", "<ID> {element}", "обновить значение элемента коллекции по ID");
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
            if (stringArgument.isEmpty() || objectArgument == null) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();

            Long id = Long.parseLong(stringArgument);
            if (id <= 0) throw new NumberFormatException();
            SpaceMarine oldMarine = collectionManager.getById(id);
            if (oldMarine == null) throw new MarineNotFoundException();

            MarineRaw marineRaw = (MarineRaw) objectArgument;
            String name = marineRaw.getName() == null ? oldMarine.getName() : marineRaw.getName();
            Coordinates coordinates = marineRaw.getCoordinates() == null ? oldMarine.getCoordinates() : marineRaw.getCoordinates();
            LocalDateTime creationDate = oldMarine.getCreationDate();
            double health = marineRaw.getHealth() == -1 ? oldMarine.getHealth() : marineRaw.getHealth();
            AstartesCategory category = marineRaw.getCategory() == null ? oldMarine.getCategory() : marineRaw.getCategory();
            Weapon weaponType = marineRaw.getWeaponType() == null ? oldMarine.getWeaponType() : marineRaw.getWeaponType();
            MeleeWeapon meleeWeapon = marineRaw.getMeleeWeapon() == null ? oldMarine.getMeleeWeapon() : marineRaw.getMeleeWeapon();
            Chapter chapter = marineRaw.getChapter() == null ? oldMarine.getChapter() : marineRaw.getChapter();

            collectionManager.removeFromCollection(oldMarine);
            collectionManager.addToCollection(new SpaceMarine(
                    id,
                    name,
                    coordinates,
                    creationDate,
                    health,
                    category,
                    weaponType,
                    meleeWeapon,
                    chapter
            ));
            ResponseOutputer.appendln("Солдат успешно изменен!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Использование: '" + getName() + " " + getUsage() + "'");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appenderror("Коллекция пуста!");
        } catch (NumberFormatException exception) {
            ResponseOutputer.appenderror("ID должен быть представлен положительным числом!");
        } catch (MarineNotFoundException exception) {
            ResponseOutputer.appenderror("Солдата с таким ID в коллекции нет!");
        } catch (ClassCastException exception) {
            ResponseOutputer.appenderror("Переданный клиентом объект неверен!");
        }
        return false;
    }
}
