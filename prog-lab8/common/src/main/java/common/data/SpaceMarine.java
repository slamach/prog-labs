package common.data;

import common.interaction.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Main character. Is stored in the collection.
 */
public class SpaceMarine implements Comparable<SpaceMarine>, Serializable {
    public static final int MAX_Y = 262;
    public static final double MIN_HEALTH = 0;
    public static final long MIN_MARINES = 1;
    public static final long MAX_MARINES = 1000;

    private Long id;
    private String name;
    private Coordinates coordinates;
    private LocalDateTime creationDate;
    private double health;
    private AstartesCategory category;
    private Weapon weaponType;
    private MeleeWeapon meleeWeapon;
    private Chapter chapter;
    private User owner;

    public SpaceMarine(Long id, String name, Coordinates coordinates, LocalDateTime creationDate, double health,
                       AstartesCategory category, Weapon weaponType, MeleeWeapon meleeWeapon, Chapter chapter,
                       User owner) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.health = health;
        this.category = category;
        this.weaponType = weaponType;
        this.meleeWeapon = meleeWeapon;
        this.chapter = chapter;
        this.owner = owner;
    }

    /**
     * @return ID of the marine.
     */
    public Long getId() {
        return id;
    }

    /**
     * @return Name of the marine.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Coordinates of the marine.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * @return Creation date of the marine.
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * @return Health of the marine.
     */
    public double getHealth() {
        return health;
    }

    /**
     * @return Category of the marine.
     */
    public AstartesCategory getCategory() {
        return category;
    }

    /**
     * @return Weapon type of the marine.
     */
    public Weapon getWeaponType() {
        return weaponType;
    }

    /**
     * @return Melee weapon of the marine.
     */
    public MeleeWeapon getMeleeWeapon() {
        return meleeWeapon;
    }

    /**
     * @return Chapter of the marine.
     */
    public Chapter getChapter() {
        return chapter;
    }

    /**
     * @return Owner of the marine.
     */
    public User getOwner() {
        return owner;
    }

    @Override
    public int compareTo(SpaceMarine marineObj) {
        return id.compareTo(marineObj.getId());
    }

    @Override
    public String toString() {
        String info = "";
        info += "Солдат №" + id;
        info += " [" + owner.getUsername() + " " + creationDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) +
                " в " + creationDate.format(DateTimeFormatter.ofPattern("HH:mm")) + "]";
        info += "\n Имя: " + name;
        info += "\n Местоположение: " + coordinates;
        info += "\n Здоровье: " + health;
        info += "\n Категория: " + category;
        info += "\n Дальнее оружие: " + weaponType;
        info += "\n Ближнее оружие: " + meleeWeapon;
        info += "\n Орден: " + chapter;
        return info;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + coordinates.hashCode() + (int) health + category.hashCode() + weaponType.hashCode() +
                meleeWeapon.hashCode() + chapter.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof SpaceMarine) {
            SpaceMarine marineObj = (SpaceMarine) obj;
            return name.equals(marineObj.getName()) && coordinates.equals(marineObj.getCoordinates()) &&
                    (health == marineObj.getHealth()) && (category == marineObj.getCategory()) &&
                    (weaponType == marineObj.getWeaponType()) && (meleeWeapon == marineObj.getMeleeWeapon()) &&
                    chapter.equals(marineObj.getChapter());
        }
        return false;
    }
}
