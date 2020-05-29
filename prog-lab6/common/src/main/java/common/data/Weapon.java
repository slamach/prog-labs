package common.data;

/**
 * Enumeration with marine weapon constants.
 */
public enum Weapon {
    HEAVY_BOLTGUN,
    BOLT_PISTOL,
    GRAV_GUN;

    /**
     * Generates a beautiful list of enum string values.
     *
     * @return String with all enum values splitted by comma.
     */
    public static String nameList() {
        String nameList = "";
        for (Weapon weaponType : values()) {
            nameList += weaponType.name() + ", ";
        }
        return nameList.substring(0, nameList.length() - 2);
    }
}
