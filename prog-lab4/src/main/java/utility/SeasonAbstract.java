package utility;

public abstract class SeasonAbstract implements SeasonInterface {
    private boolean lengthy;
    
    public SeasonAbstract(boolean lengthy) {
        this.lengthy = lengthy;
    }

    @Override
    public boolean isLengthy() {
        return lengthy;
    }

    @Override
    public void setLengthy(boolean lengthy) {
        this.lengthy = lengthy;
    }
}
