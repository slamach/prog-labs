package commands;

import exceptions.CollectionIsEmptyException;
import exceptions.WrongAmountOfElementsException;
import utility.CollectionManager;
import utility.Console;

/**
 * Command 'sum_of_health'. Prints the sum of health of all marines.
 */
public class SumOfHealthCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public SumOfHealthCommand(CollectionManager collectionManager) {
        super("sum_of_health", "вывести сумму значений поля health для всех элементов коллекции");
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
            double sum_of_health = collectionManager.getSumOfHealth();
            if (sum_of_health == 0) throw new CollectionIsEmptyException();
            Console.println("Сумма здоровья всех солдат: " + sum_of_health);
            return true;
        } catch (WrongAmountOfElementsException exception) {
            Console.println("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            Console.printerror("Коллекция пуста!");
        }
        return false;
    }
}
