package server.utility;

/**
 * A class for generating responses to a client.
 */
public class ResponseOutputer {
    private static StringBuilder stringBuilder = new StringBuilder();

    /**
     * Append object to out to the end of string.
     *
     * @param toOut Object to Out.
     */
    public static void append(Object toOut) {
        stringBuilder.append(toOut);
    }

    /**
     * Append line break to the end of string.
     */
    public static void appendln() {
        stringBuilder.append("\n");
    }

    /**
     * Append object to out and line break to the end of string.
     *
     * @param toOut Object to Out.
     */
    public static void appendln(Object toOut) {
        stringBuilder.append(toOut + "\n");
    }

    /**
     * Append error description and line break to the end of string.
     *
     * @param toOut Error description.
     */
    public static void appenderror(Object toOut) {
        stringBuilder.append("error: " + toOut + "\n");
    }

    /**
     * Append a table with two elements to the end of the line.
     *
     * @param element1 First element for the table.
     * @param element2 Second element for the table.
     */
    public static void appendtable(Object element1, Object element2) {
        stringBuilder.append(String.format("%-37s%-1s%n", element1, element2));
    }

    /**
     * Takes a constructed string.
     *
     * @return Сonstructed string.
     */
    public static String getString() {
        return stringBuilder.toString();
    }

    /**
     * Takes a constructed string and clears the buffer.
     *
     * @return Сonstructed string.
     */
    public static String getAndClear() {
        String toReturn = stringBuilder.toString();
        stringBuilder.delete(0, stringBuilder.length());
        return toReturn;
    }

    /**
     * Сlears the buffer.
     */
    public static void clear() {
        stringBuilder.delete(0, stringBuilder.length());
    }
}
