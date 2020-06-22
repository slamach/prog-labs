package common.data;

/**
 * Enumeration with marine category constants.
 */
public enum AstartesCategory {
    DREADNOUGHT,
    ASSAULT,
    TACTICAL,
    CHAPLAIN,
    APOTHECARY;

    /**
     * Generates a beautiful list of enum string values.
     *
     * @return String with all enum values splitted by comma.
     */
    public static String nameList() {
        String nameList = "";
        for (AstartesCategory category : values()) {
            nameList += category.name() + ", ";
        }
        return nameList.substring(0, nameList.length() - 2);
    }
}
