package server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.commands.*;
import server.utility.CollectionFileManager;
import server.utility.CollectionManager;
import server.utility.CommandManager;
import server.utility.RequestHandler;

/**
 * Main server class. Creates all server instances.
 *
 * @author Sviridov Dmitry and Orlov Egor.
 */
public class App {
    public static final int PORT = 1821;
    public static final int CONNECTION_TIMEOUT = 60 * 1000;
    public static final String ENV_VARIABLE = "LABA";
    public static Logger logger = LogManager.getLogger("ServerLogger");

    public static void main(String[] args) {
        CollectionFileManager collectionFileManager = new CollectionFileManager(ENV_VARIABLE);
        CollectionManager collectionManager = new CollectionManager(collectionFileManager);
        CommandManager commandManager = new CommandManager(
                new HelpCommand(),
                new InfoCommand(collectionManager),
                new ShowCommand(collectionManager),
                new AddCommand(collectionManager),
                new UpdateCommand(collectionManager),
                new RemoveByIdCommand(collectionManager),
                new ClearCommand(collectionManager),
                new SaveCommand(collectionManager),
                new ExitCommand(),
                new ExecuteScriptCommand(),
                new AddIfMinCommand(collectionManager),
                new RemoveGreaterCommand(collectionManager),
                new HistoryCommand(),
                new SumOfHealthCommand(collectionManager),
                new MaxByMeleeWeaponCommand(collectionManager),
                new FilterByWeaponTypeCommand(collectionManager),
                new ServerExitCommand()
        );
        RequestHandler requestHandler = new RequestHandler(commandManager);
        Server server = new Server(PORT, CONNECTION_TIMEOUT, requestHandler);
        server.run();
    }
}
