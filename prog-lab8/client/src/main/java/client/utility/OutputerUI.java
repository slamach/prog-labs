package client.utility;

import client.controllers.tools.ObservableResourceFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.text.MessageFormat;
import java.util.MissingResourceException;

/**
 * Class for outputting something to user.
 */
public class OutputerUI {
    private static final String INFO_TITLE = "Collection Keeper";
    private static final String ERROR_TITLE = "Collection Keeper";

    private static ObservableResourceFactory resourceFactory;

    /**
     * Forms a message.
     *
     * @param title   Title of message.
     * @param toOut   Message.
     * @param args    Arguments.
     * @param msgType Message type.
     */
    private static void msg(String title, String toOut, String[] args, AlertType msgType) {
        Alert alert = new Alert(msgType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(tryResource(toOut, args));
        alert.showAndWait();
    }

    /**
     * Trys resource.
     *
     * @param str  String.
     * @param args Arguments.
     */
    private static String tryResource(String str, String[] args) {
        try {
            if (haveResourceFactory()) throw new NullPointerException();
            if (args == null) return resourceFactory.getResources().getString(str);
            MessageFormat messageFormat = new MessageFormat(resourceFactory.getResources().getString(str));
            return messageFormat.format(args);
        } catch (MissingResourceException | NullPointerException exception) {
            return str;
        }
    }


    /**
     * Prints info message.
     *
     * @param toOut Message.
     * @param args  Arguments.
     */
    public static void info(String toOut, String[] args) {
        msg(INFO_TITLE, toOut, args, AlertType.INFORMATION);
    }

    /**
     * Prints info message.
     *
     * @param toOut Message.
     */
    public static void info(String toOut) {
        info(toOut, null);
    }

    /**
     * Error message.
     *
     * @param toOut Error.
     * @param args  Arguments.
     */
    public static void error(String toOut, String[] args) {
        msg(ERROR_TITLE, toOut, args, AlertType.ERROR);
    }

    /**
     * Error message.
     *
     * @param toOut Error.
     */
    public static void error(String toOut) {
        error(toOut, null);
    }

    /**
     * Prints error if object starts with 'error: ' or info in other case.
     *
     * @param toOut Message to print.
     * @param args  Arguments.
     */
    public static void tryError(String toOut, String[] args) {
        if (toOut.startsWith("error: "))
            msg(ERROR_TITLE, toOut.substring(7), args, AlertType.ERROR);
        else msg(INFO_TITLE, toOut, args, AlertType.INFORMATION);
    }

    /**
     * Prints error if object starts with 'error: ' or info in other case.
     *
     * @param toOut Message to print.
     */
    public static void tryError(String toOut) {
        tryError(toOut, null);
    }

    public static void setResourceFactory(ObservableResourceFactory resourceFactory) {
        OutputerUI.resourceFactory = resourceFactory;
    }

    /**
     * Checking to resource factory.
     *
     * @return False if heave and true if haven't
     */
    public static boolean haveResourceFactory() {
        return resourceFactory == null;
    }
}
