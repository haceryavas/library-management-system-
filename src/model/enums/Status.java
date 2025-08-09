package model.enums;

/**
 * Represents the availability status of a book.
 */
public enum Status {
    AVAILABLE("Available"),
    UNAVAILABLE("Not Available");

    private final String displayNext;

    Status(String displayNext) {
        this.displayNext = displayNext;
    }

    @Override
    public String toString() {
        return displayNext;
    }
}
