package client.utility;

import client.controllers.tools.ObservableResourceFactory;

import java.text.MessageFormat;
import java.util.MissingResourceException;

/**
 * Class for outputting something.
 */
public class Outputer {
    private static ObservableResourceFactory resourceFactory;

    private static String tryResource(String str, String arg) {
        try {
            if (haveResourceFactory()) throw new NullPointerException();
            if (arg == null) return resourceFactory.getResources().getString(str);
            else {
                MessageFormat messageFormat = new MessageFormat(resourceFactory.getResources().getString(str));
                Object[] args = {arg};
                return messageFormat.format(args);
            }
        } catch (MissingResourceException | NullPointerException exception) {
            return str;
        }
    }

    /**
     * Prints toOut.toString() to Console.
     *
     * @param toOut Object to print
     * @param arg   Args for output.
     */
    public static void print(String toOut, String arg) {
        System.out.print(tryResource(toOut, arg));
    }

    /**
     * Prints toOut.toString() to Console.
     *
     * @param toOut Object to print
     */
    public static void print(String toOut) {
        print(toOut, null);
    }

    /**
     * Prints toOut.toString() + \n to Console.
     *
     * @param toOut Object to print
     * @param arg   Args for output.
     */
    public static void println(String toOut, String arg) {
        System.out.println(tryResource(toOut, arg));
    }

    /**
     * Prints toOut.toString() + \n to Console.
     *
     * @param toOut Object to print
     */
    public static void println(String toOut) {
        println(toOut, null);
    }

    /**
     * Prints \n to Console.
     */
    public static void println() {
        System.out.println();
    }

    /**
     * Prints error to Console.
     *
     * @param toOut Error to print
     * @param arg   Args for output.
     */
    public static void printerror(String toOut, String arg) {
        System.out.println("error: " + tryResource(toOut, arg));
    }

    /**
     * Prints error: toOut.toString() to Console.
     *
     * @param toOut Error to print
     */
    public static void printerror(String toOut) {
        printerror(toOut, null);
    }

    public static void setResourceFactory(ObservableResourceFactory resourceFactory) {
        Outputer.resourceFactory = resourceFactory;
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
