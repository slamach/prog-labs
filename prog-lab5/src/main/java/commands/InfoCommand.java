package commands;

import java.time.LocalDateTime;

import exceptions.WrongAmountOfElementsException;
import utility.CollectionManager;
import utility.Console;

/**
 * Command 'info'. Prints information about the collection.
 */
public class InfoCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        super("info", "вывести информацию о коллекции");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
            LocalDateTime lastInitTime = collectionManager.getLastInitTime();
            String lastInitTimeString = (lastInitTime == null) ? "в данной сессии инициализации еще не происходило" :
                                        lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();
            
            LocalDateTime lastSaveTime = collectionManager.getLastSaveTime();
            String lastSaveTimeString = (lastSaveTime == null) ? "в данной сессии сохранения еще не происходило" :
                                        lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();

            Console.println("Сведения о коллекции:");
            Console.println(" Тип: " + collectionManager.collectionType());
            Console.println(" Количество элементов: " + collectionManager.collectionSize());
            Console.println(" Дата последнего сохранения: " + lastSaveTimeString);
            Console.println(" Дата последней инициализации: " + lastInitTimeString);
            return true;
        } catch (WrongAmountOfElementsException exception) {
            Console.println("Использование: '" + getName() + "'");
        }
        return false;
    }
}
