package server.commands;

import common.data.SpaceMarine;
import common.exceptions.*;
import common.interaction.MarineRaw;
import common.interaction.User;
import server.utility.CollectionManager;
import server.utility.DatabaseCollectionManager;
import server.utility.ResponseOutputer;

import java.time.LocalDateTime;

/**
 * Command 'remove_greater'. Removes elements greater than user entered.
 */
public class RemoveGreaterCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    private DatabaseCollectionManager databaseCollectionManager;

    public RemoveGreaterCommand(CollectionManager collectionManager, DatabaseCollectionManager databaseCollectionManager) {
        super("remove_greater", "{element}", "remove all elements which is higher than selected");
        this.collectionManager = collectionManager;
        this.databaseCollectionManager = databaseCollectionManager;
    }

    /**
     * Executes the command.
     *
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument, User user) {
        try {
            if (!stringArgument.isEmpty() || objectArgument == null) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            MarineRaw marineRaw = (MarineRaw) objectArgument;
            SpaceMarine marineToFind = new SpaceMarine(
                    0L,
                    marineRaw.getName(),
                    marineRaw.getCoordinates(),
                    LocalDateTime.now(),
                    marineRaw.getHealth(),
                    marineRaw.getCategory(),
                    marineRaw.getWeaponType(),
                    marineRaw.getMeleeWeapon(),
                    marineRaw.getChapter(),
                    user
            );
            SpaceMarine marineFromCollection = collectionManager.getByValue(marineToFind);
            if (marineFromCollection == null) throw new MarineNotFoundException();
            for (SpaceMarine marine : collectionManager.getGreater(marineFromCollection)) {
                if (!marine.getOwner().equals(user)) throw new PermissionDeniedException();
                if (!databaseCollectionManager.checkMarineUserId(marine.getId(), user))
                    throw new ManualDatabaseEditException();
            }
            for (SpaceMarine marine : collectionManager.getGreater(marineFromCollection)) {
                databaseCollectionManager.deleteMarineById(marine.getId());
                collectionManager.removeFromCollection(marine);
            }
            ResponseOutputer.appendln("MarinesWasDeleted");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Using");
            ResponseOutputer.appendargs(getName() + " " + getUsage() + "'");
        } catch (CollectionIsEmptyException exception) {
            ResponseOutputer.appenderror("CollectionIsEmptyException");
        } catch (MarineNotFoundException exception) {
            ResponseOutputer.appenderror("MarineException");
        } catch (ClassCastException exception) {
            ResponseOutputer.appenderror("ClientObjectException");
        } catch (DatabaseHandlingException exception) {
            ResponseOutputer.appenderror("DatabaseHandlingException");
        } catch (PermissionDeniedException exception) {
            ResponseOutputer.appenderror("NoughRightsException");
        } catch (ManualDatabaseEditException exception) {
            ResponseOutputer.appenderror("ManualDatabaseException");
        }
        return false;
    }
}
