package server.commands;

import common.exceptions.DatabaseHandlingException;
import common.exceptions.UserAlreadyExists;
import common.exceptions.WrongAmountOfElementsException;
import common.interaction.User;
import server.utility.DatabaseUserManager;
import server.utility.ResponseOutputer;

/**
 * Command 'register'. Allows the user to register.
 */
public class RegisterCommand extends AbstractCommand {
    private DatabaseUserManager databaseUserManager;

    public RegisterCommand(DatabaseUserManager databaseUserManager) {
        super("register", "", "~internal command~");
        this.databaseUserManager = databaseUserManager;
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
            if (!databaseUserManager.insertUser(user)) throw new UserAlreadyExists();
            ResponseOutputer.appendln("UserRegistered");
            ResponseOutputer.appendargs(user.getUsername());
            return true;
        } catch (WrongAmountOfElementsException exception) {
            ResponseOutputer.appendln("Using");
            ResponseOutputer.appendargs(getName() + " " + getUsage() + "'");
        } catch (ClassCastException exception) {
            ResponseOutputer.appenderror("ClientObjectException");
        } catch (DatabaseHandlingException exception) {
            ResponseOutputer.appenderror("DatabaseHandlingException");
        } catch (UserAlreadyExists exception) {
            ResponseOutputer.appendln("UserExistsException");
            ResponseOutputer.appendargs(user.getUsername());
        }
        return false;
    }
}
