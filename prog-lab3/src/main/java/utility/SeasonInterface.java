package utility;

public interface SeasonInterface extends ThingInterface {
    SeasonType getType();
    boolean isLengthy();
    void setLengthy(boolean lengthy);
}
