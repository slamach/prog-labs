package commands;

import exceptions.WrongAmountOfElementsException;
import utility.Console;

/**
 * Command 'execute_script'. Executes scripts from a file. Ectually only checks argument and prints messages.
 */
public class ExecuteScriptCommand extends AbstractCommand {
    public ExecuteScriptCommand() {
        super("execute_script <file_name>", "исполнить скрипт из указанного файла");
    }

    /**
     * Executes the command, but partially.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (argument.isEmpty()) throw new WrongAmountOfElementsException();
            Console.println("Выполняю скрипт '" + argument + "'...");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            Console.println("Использование: '" + getName() + "'");
        }
        return false;
    }
}
