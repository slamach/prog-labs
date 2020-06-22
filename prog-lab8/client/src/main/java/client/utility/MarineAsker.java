package client.utility;

import client.App;
import common.data.*;
import common.exceptions.IncorrectInputInScriptException;
import common.exceptions.MustBeNotEmptyException;
import common.exceptions.NotInDeclaredLimitsException;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Asks user a marine's value.
 */
public class MarineAsker {
    private Scanner userScanner;

    public MarineAsker(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    /**
     * Asks a user the marine's name.
     *
     * @return Marine's name.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public String askName() throws IncorrectInputInScriptException {
        String name;
        try {
            Outputer.println("EnterName");
            Outputer.print(App.PS2);
            name = userScanner.nextLine().trim();
            Outputer.println(name);
            if (name.equals("")) throw new MustBeNotEmptyException();
            return name;
        } catch (NoSuchElementException exception) {
            Outputer.printerror("NameNotIdentifiedException");
        } catch (MustBeNotEmptyException exception) {
            Outputer.printerror("NameEmptyException");
        } catch (IllegalStateException exception) {
            Outputer.printerror("UnexpectedException");
            OutputerUI.error("UnexpectedException");
            System.exit(0);
        }
        throw new IncorrectInputInScriptException();
    }

    /**
     * Asks a user the marine's X coordinate.
     *
     * @return Marine's X coordinate.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public double askX() throws IncorrectInputInScriptException {
        String strX;
        double x;
        try {
            Outputer.println("EnterX");
            Outputer.print(App.PS2);
            strX = userScanner.nextLine().trim();
            Outputer.println(strX);
            x = Double.parseDouble(strX);
            return x;
        } catch (NoSuchElementException exception) {
            Outputer.printerror("XNotIdentifiedException");
        } catch (NumberFormatException exception) {
            Outputer.printerror("XMustBeNumberException");
        } catch (NullPointerException | IllegalStateException exception) {
            Outputer.printerror("UnexpectedException");
            OutputerUI.error("UnexpectedException");
            System.exit(0);
        }
        throw new IncorrectInputInScriptException();
    }

    /**
     * Asks a user the marine's Y coordinate.
     *
     * @return Marine's Y coordinate.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public Float askY() throws IncorrectInputInScriptException {
        String strY;
        Float y;
        try {
            Outputer.println("EnterY", String.valueOf(SpaceMarine.MAX_Y + 1));
            Outputer.print(App.PS2);
            strY = userScanner.nextLine().trim();
            Outputer.println(strY);
            y = Float.parseFloat(strY);
            if (y > SpaceMarine.MAX_Y) throw new NotInDeclaredLimitsException();
            return y;
        } catch (NoSuchElementException exception) {
            Outputer.printerror("YNotIdentifiedException");
        } catch (NotInDeclaredLimitsException exception) {
            Outputer.printerror("YMustBeLessException", String.valueOf(SpaceMarine.MAX_Y));
        } catch (NumberFormatException exception) {
            Outputer.printerror("YMustBeNumberException");
        } catch (NullPointerException | IllegalStateException exception) {
            Outputer.printerror("UnexpectedException");
            OutputerUI.error("UnexpectedException");
            System.exit(0);
        }
        throw new IncorrectInputInScriptException();
    }

    /**
     * Asks a user the marine's coordinates.
     *
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
     *
     * @return Marine's health.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public double askHealth() throws IncorrectInputInScriptException {
        String strHealth;
        double health;
        try {
            Outputer.println("EnterHealth");
            Outputer.print(App.PS2);
            strHealth = userScanner.nextLine().trim();
            Outputer.println(strHealth);
            health = Double.parseDouble(strHealth);
            if (health <= SpaceMarine.MIN_HEALTH) throw new NotInDeclaredLimitsException();
            return health;
        } catch (NoSuchElementException exception) {
            Outputer.printerror("HealthNotIdentifiedException");
        } catch (NotInDeclaredLimitsException exception) {
            Outputer.printerror("HealthMustBeNumberException");
        } catch (NumberFormatException exception) {
            Outputer.printerror("HealthMustBeMoreZero");
        } catch (NullPointerException | IllegalStateException exception) {
            Outputer.printerror("UnexpectedException");
            OutputerUI.error("UnexpectedException");
            System.exit(0);
        }
        throw new IncorrectInputInScriptException();
    }

    /**
     * Asks a user the marine's category.
     *
     * @return Marine's category.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public AstartesCategory askCategory() throws IncorrectInputInScriptException {
        String strCategory;
        AstartesCategory category;
        try {
            Outputer.println("CategoryList", AstartesCategory.nameList());
            Outputer.println("EnterCategory");
            Outputer.print(App.PS2);
            strCategory = userScanner.nextLine().trim();
            Outputer.println(strCategory);
            category = AstartesCategory.valueOf(strCategory.toUpperCase());
            return category;
        } catch (NoSuchElementException exception) {
            Outputer.printerror("CategoryNotIndentifiedException");
        } catch (IllegalArgumentException exception) {
            Outputer.printerror("NoSuchCategory");
        } catch (IllegalStateException exception) {
            Outputer.printerror("UnexpectedException");
            OutputerUI.error("UnexpectedException");
            System.exit(0);
        }
        throw new IncorrectInputInScriptException();
    }

    /**
     * Asks a user the marine's weapon type.
     *
     * @return Marine's weapon type.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public Weapon askWeaponType() throws IncorrectInputInScriptException {
        String strWeaponType;
        Weapon weaponType;
        try {
            Outputer.println("WeaponList", Weapon.nameList());
            Outputer.println("EnterWeapon");
            Outputer.print(App.PS2);
            strWeaponType = userScanner.nextLine().trim();
            Outputer.println(strWeaponType);
            weaponType = Weapon.valueOf(strWeaponType.toUpperCase());
            return weaponType;
        } catch (NoSuchElementException exception) {
            Outputer.printerror("WeaponNotIdentifiedException");
        } catch (IllegalArgumentException exception) {
            Outputer.printerror("NoSuchWeapon");
        } catch (IllegalStateException exception) {
            Outputer.printerror("UnexpectedException");
            OutputerUI.error("UnexpectedException");
            System.exit(0);
        }
        throw new IncorrectInputInScriptException();
    }

    /**
     * Asks a user the marine's melee weapon.
     *
     * @return Marine's melee weapon.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public MeleeWeapon askMeleeWeapon() throws IncorrectInputInScriptException {
        String strMeleeWeapon;
        MeleeWeapon meleeWeapon;
        try {
            Outputer.println("MeleeWeaponList", MeleeWeapon.nameList());
            Outputer.println("EnterMeleeWeapon");
            Outputer.print(App.PS2);
            strMeleeWeapon = userScanner.nextLine().trim();
            Outputer.println(strMeleeWeapon);
            meleeWeapon = MeleeWeapon.valueOf(strMeleeWeapon.toUpperCase());
            return meleeWeapon;
        } catch (NoSuchElementException exception) {
            Outputer.printerror("WeaponNotIdentifiedException");
        } catch (IllegalArgumentException exception) {
            Outputer.printerror("NoSuchWeapon");
        } catch (IllegalStateException exception) {
            Outputer.printerror("UnexpectedException");
            OutputerUI.error("UnexpectedException");
            System.exit(0);
        }
        throw new IncorrectInputInScriptException();
    }

    /**
     * Asks a user the marine chapter's name.
     *
     * @return Chapter's name.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public String askChapterName() throws IncorrectInputInScriptException {
        String chapterName;
        try {
            Outputer.println("EnterChapterName");
            Outputer.print(App.PS2);
            chapterName = userScanner.nextLine().trim();
            Outputer.println(chapterName);
            if (chapterName.equals("")) throw new MustBeNotEmptyException();
            return chapterName;
        } catch (NoSuchElementException exception) {
            Outputer.printerror("ChapterNameNotIdentifiedException");
        } catch (MustBeNotEmptyException exception) {
            Outputer.printerror("ChapterNameMustBeNotEmptyException");
        } catch (IllegalStateException exception) {
            Outputer.printerror("UnexpectedException");
            OutputerUI.error("UnexpectedException");
            System.exit(0);
        }
        throw new IncorrectInputInScriptException();
    }

    /**
     * Asks a user the marine chapter's number of soldiers.
     *
     * @return Number of soldiers.
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public long askChapterMarinesCount() throws IncorrectInputInScriptException {
        String strMarinesCount;
        long marinesCount;
        try {
            Outputer.println("EnterChapterMarinesCount", String.valueOf(SpaceMarine.MAX_MARINES + 1));
            Outputer.print(App.PS2);
            strMarinesCount = userScanner.nextLine().trim();
            Outputer.println(strMarinesCount);
            marinesCount = Long.parseLong(strMarinesCount);
            if (marinesCount < SpaceMarine.MIN_MARINES || marinesCount > SpaceMarine.MAX_MARINES)
                throw new NotInDeclaredLimitsException();
            return marinesCount;
        } catch (NoSuchElementException exception) {
            Outputer.printerror("ChapterMarinesCountNotIdentifiedException");
        } catch (NotInDeclaredLimitsException exception) {
            Outputer.printerror("ChapterMarinesCountMustBeLessException", String.valueOf(SpaceMarine.MAX_MARINES));
        } catch (NumberFormatException exception) {
            Outputer.printerror("ChapterMarinesCountMustBeNumberException");
        } catch (NullPointerException | IllegalStateException exception) {
            Outputer.printerror("UnexpectedException");
            OutputerUI.error("UnexpectedException");
            System.exit(0);
        }
        throw new IncorrectInputInScriptException();
    }

    /**
     * Asks a user the marine's chapter.
     *
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
     *
     * @param question A question.
     * @return Answer (true/false).
     * @throws IncorrectInputInScriptException If script is running and something goes wrong.
     */
    public boolean askQuestion(String question) throws IncorrectInputInScriptException {
        String answer;
        try {
            Outputer.print(question);
            Outputer.println(" (+/-):");
            Outputer.print(App.PS2);
            answer = userScanner.nextLine().trim();
            Outputer.println(answer);
            if (!answer.equals("+") && !answer.equals("-")) throw new NotInDeclaredLimitsException();
            return answer.equals("+");
        } catch (NoSuchElementException exception) {
            Outputer.printerror("AnswerNotIndentifiedException");
        } catch (NotInDeclaredLimitsException exception) {
            Outputer.printerror("AnswerLimitsException");
        } catch (IllegalStateException exception) {
            Outputer.printerror("UnexpectedException");
            OutputerUI.error("UnexpectedException");
            System.exit(0);
        }
        throw new IncorrectInputInScriptException();
    }
}
