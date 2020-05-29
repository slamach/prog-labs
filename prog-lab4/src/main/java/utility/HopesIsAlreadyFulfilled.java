package utility;

public class HopesIsAlreadyFulfilled extends RuntimeException {
    @Override
    public String toString() {
        return "Мечты уже и так преисполнены!";
    }
}
