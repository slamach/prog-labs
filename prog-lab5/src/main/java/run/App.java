package run;

import java.util.Scanner;

import commands.AddCommand;
import commands.AddIfMinCommand;
import commands.ClearCommand;
import commands.ExecuteScriptCommand;
import commands.ExitCommand;
import commands.FilterByWeaponTypeCommand;
import commands.HelpCommand;
import commands.HistoryCommand;
import commands.InfoCommand;
import commands.MaxByMeleeWeaponCommand;
import commands.RemoveByIdCommand;
import commands.RemoveGreaterCommand;
import commands.SaveCommand;
import commands.ShowCommand;
import commands.SumOfHealthCommand;
import commands.UpdateCommand;
import utility.CollectionManager;
import utility.CommandManager;
import utility.Console;
import utility.FileManager;
import utility.MarineAsker;

// TODO: RemoveGreater - повторы (?)
// TODO: Заменить возвращаемые объекты на клоны

/**
 * Main application class. Creates all instances and runs the program.
 * @author Sviridov Dmitry and Orlov Egor
 */
public class App {
    public static final String PS1 = "$ ";
    public static final String PS2 = "> ";

    public static void main(String[] args) {
        try (Scanner userScanner = new Scanner(System.in)) {
            final String envVariable = "LABA";

            MarineAsker marineAsker = new MarineAsker(userScanner);
            FileManager fileManager = new FileManager(envVariable);
            CollectionManager collectionManager = new CollectionManager(fileManager);
            CommandManager commandManager = new CommandManager(
                new HelpCommand(),
                new InfoCommand(collectionManager),
                new ShowCommand(collectionManager),
                new AddCommand(collectionManager, marineAsker),
                new UpdateCommand(collectionManager, marineAsker),
                new RemoveByIdCommand(collectionManager),
                new ClearCommand(collectionManager),
                new SaveCommand(collectionManager),
                new ExitCommand(),
                new ExecuteScriptCommand(),
                new AddIfMinCommand(collectionManager, marineAsker),
                new RemoveGreaterCommand(collectionManager, marineAsker),
                new HistoryCommand(),
                new SumOfHealthCommand(collectionManager),
                new MaxByMeleeWeaponCommand(collectionManager),
                new FilterByWeaponTypeCommand(collectionManager)
            );
            Console console = new Console(commandManager, userScanner, marineAsker);

            console.interactiveMode();
        }
    }
}
