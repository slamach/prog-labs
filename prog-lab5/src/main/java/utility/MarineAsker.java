package utility;

import java.util.NoSuchElementException;
import java.util.Scanner;

import data.AstartesCategory;
import data.Chapter;
import data.Coordinates;
import data.MeleeWeapon;
import data.Weapon;
import exceptions.IncorrectInputInScriptException;
import exceptions.MustBeNotEmptyException;
import exceptions.NotInDeclaredLimitsException;
import run.App;

/**
 * Asks a user a marine's value.
 */
public class MarineAsker {
    private final int MAX_Y = 262;
    private final double MIN_HEALTH = 0;
    private final long MIN_MARINES = 1;
    private final long MAX_MARINES = 1000;

    private Scanner userScanner;
    private boolean fileMode;
    
    public MarineAsker(Scanner userScanner) {
        this.userScanner = userScanner;
        fileMode = false;
    }

    /**
     * Sets a scanner to scan user input.
     * @param userScanner Scanner to set.
     */
    public void setUserScanner(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    /**
     * @return Scanner, which uses for user input.
     */
    public Scanner getUserScanner() {
        return userScanner;
    }

    /**
     * Sets marine asker mode to 'File Mode'.
     */
    public void setFileMode() {
        fileMode = true;
    }

    /**
     * Sets marine asker mode to 'User Mode'.
     */
    public void setUserMode() {
        fileMode = false;
    }

    /**
     * Asks a user the marine's name.
     * @return Marine's name.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public String askName() throws IncorrectInputInScriptException {
        String name;
        while (true) {
            try {
                Console.println("Введите имя:");
                Console.print(App.PS2);
                name = userScanner.nextLine().trim();
                if (fileMode) Console.println(name);
                if (name.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Имя не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (MustBeNotEmptyException exception) {
                Console.printerror("Имя не может быть пустым!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return name;
    }

    /**
     * Asks a user the marine's X coordinate.
     * @return Marine's X coordinate.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public double askX() throws IncorrectInputInScriptException {
        String strX;
        double x;
        while (true) {
            try {
                Console.println("Введите координату X:");
                Console.print(App.PS2);
                strX = userScanner.nextLine().trim();
                if (fileMode) Console.println(strX);
                x = Double.parseDouble(strX);
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Координата X не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                Console.printerror("Координата X должна быть представлена числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return x;
    }

    /**
     * Asks a user the marine's Y coordinate.
     * @return Marine's Y coordinate.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public Float askY() throws IncorrectInputInScriptException {
        String strY;
        Float y;
        while (true) {
            try {
                Console.println("Введите координату Y < " + (MAX_Y+1) + ":");
                Console.print(App.PS2);
                strY = userScanner.nextLine().trim();
                if (fileMode) Console.println(strY);
                y = Float.parseFloat(strY);
                if (y > MAX_Y) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Координата Y не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException exception) {
                Console.printerror("Координата Y не может превышать " + MAX_Y + "!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                Console.printerror("Координата Y должна быть представлена числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return y;
    }

    /**
     * Asks a user the marine's coordinates.
     * @return Marine's coordinates.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public Coordinates askCoordinates() throws IncorrectInputInScriptException {
        double x;
        Float y;
        x = askX();
        y = askY();
        return new Coordinates(x, y);
    }

    /**
     * Asks a user the marine's health.
     * @return Marine's health.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public double askHealth() throws IncorrectInputInScriptException {
        String strHealth;
        double health;
        while (true) {
            try {
                Console.println("Введите здоровье:");
                Console.print(App.PS2);
                strHealth = userScanner.nextLine().trim();
                if (fileMode) Console.println(strHealth);
                health = Double.parseDouble(strHealth);
                if (health < MIN_HEALTH) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Здоровье не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException exception) {
                Console.printerror("Здоровье должно быть больше нуля!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                Console.printerror("Здоровье должно быть представлено числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return health;
    }

    /**
     * Asks a user the marine's category.
     * @return Marine's category.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public AstartesCategory askCategory() throws IncorrectInputInScriptException {
        String strCategory;
        AstartesCategory category;
        while (true) {
            try {
                Console.println("Список категорий - " + AstartesCategory.nameList());
                Console.println("Введите категорию:");
                Console.print(App.PS2);
                strCategory = userScanner.nextLine().trim();
                if (fileMode) Console.println(strCategory);
                category = AstartesCategory.valueOf(strCategory.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Категория не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                Console.printerror("Категории нет в списке!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return category;
    }

    /**
     * Asks a user the marine's weapon type.
     * @return Marine's weapon type.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public Weapon askWeaponType() throws IncorrectInputInScriptException {
        String strWeaponType;
        Weapon weaponType;
        while (true) {
            try {
                Console.println("Список оружия дальнего боя - " + Weapon.nameList());
                Console.println("Введите оружие дальнего боя:");
                Console.print(App.PS2);
                strWeaponType = userScanner.nextLine().trim();
                if (fileMode) Console.println(strWeaponType);
                weaponType = Weapon.valueOf(strWeaponType.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Оружие не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                Console.printerror("Оружия нет в списке!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return weaponType;
    }

    /**
     * Asks a user the marine's melee weapon.
     * @return Marine's melee weapon.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public MeleeWeapon askMeleeWeapon() throws IncorrectInputInScriptException {
        String strMeleeWeapon;
        MeleeWeapon meleeWeapon;
        while (true) {
            try {
                Console.println("Список оружия ближнего боя - " + MeleeWeapon.nameList());
                Console.println("Введите оружие ближнего боя:");
                Console.print(App.PS2);
                strMeleeWeapon = userScanner.nextLine().trim();
                if (fileMode) Console.println(strMeleeWeapon);
                meleeWeapon = MeleeWeapon.valueOf(strMeleeWeapon.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Оружие не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception) {
                Console.printerror("Оружия нет в списке!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return meleeWeapon;
    }

    /**
     * Asks a user the marine chapter's name.
     * @return Chapter's name.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public String askChapterName() throws IncorrectInputInScriptException {
        String chapterName;
        while (true) {
            try {
                Console.println("Введите имя ордена:");
                Console.print(App.PS2);
                chapterName = userScanner.nextLine().trim();
                if (fileMode) Console.println(chapterName);
                if (chapterName.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Имя ордена не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (MustBeNotEmptyException exception) {
                Console.printerror("Имя ордена не может быть пустым!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return chapterName;
    }

    /**
     * Asks a user the marine chapter's number of soldiers.
     * @return Number of soldiers.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public long askChapterMarinesCount() throws IncorrectInputInScriptException {
        String strMarinesCount;
        long marinesCount;
        while (true) {
            try {
                Console.println("Введите количество солдат в ордене < " + (MAX_MARINES+1) + ":");
                Console.print(App.PS2);
                strMarinesCount = userScanner.nextLine().trim();
                if (fileMode) Console.println(strMarinesCount);
                marinesCount = Long.parseLong(strMarinesCount);
                if (marinesCount < MIN_MARINES || marinesCount > MAX_MARINES) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Количество солдат в ордене не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException exception) {
                Console.printerror("Количество солдат в ордене должно быть положительным и не превышать " + MAX_MARINES + "!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                Console.printerror("Количество солдат в ордене должно быть представлено числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return marinesCount;
    }

    /**
     * Asks a user the marine's chapter.
     * @return Marine's chapter.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public Chapter askChapter() throws IncorrectInputInScriptException {
        String name;
        long marinesCount;
        name = askChapterName();
        marinesCount = askChapterMarinesCount();
        return new Chapter(name, marinesCount);
    }

    /**
     * Asks a user a question.
     * @return Answer (true/false).
     * @param question A question.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public boolean askQuestion(String question) throws IncorrectInputInScriptException {
        String finalQuestion = question + " (+/-):";
        String answer;
        while (true) {
            try {
                Console.println(finalQuestion);
                Console.print(App.PS2);
                answer = userScanner.nextLine().trim();
                if (fileMode) Console.println(answer);
                if (!answer.equals("+") && !answer.equals("-")) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                Console.printerror("Ответ не распознан!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException exception) {
                Console.printerror("Ответ должен быть представлен знаками '+' или '-'!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return (answer.equals("+")) ? true : false;
    }

    @Override
    public String toString() {
        return "MarineAsker (вспомогательный класс для запросов пользователю)";
    }
}
