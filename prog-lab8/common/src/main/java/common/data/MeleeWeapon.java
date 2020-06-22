package common.data;

/**
 * Enumeration with marine melee weapon constants.
 */
public enum MeleeWeapon {
    CHAIN_SWORD,
    CHAIN_AXE,
    LIGHTING_CLAW,
    POWER_BLADE,
    POWER_FIST;

    /**
     * Generates a beautiful list of enum string values.
     *
     * @return String with all enum values splitted by comma.
     */
    public static String nameList() {
        String nameList = "";
        for (MeleeWeapon meleeWeapon : values()) {
            nameList += meleeWeapon.name() + ", ";
        }
        return nameList.substring(0, nameList.length() - 2);
    }
}
