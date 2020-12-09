package me.brennan.detector;

/**
 * @author Brennan
 * @since 12/8/2020
 **/
public class Flag {
    private final FlagLevel level;
    private final String message, klazz;

    public Flag(FlagLevel level, String message, String klazz) {
        this.level = level;
        this.message = message;
        this.klazz = klazz;
    }

    public String getMessage() {
        return message;
    }

    public FlagLevel getLevel() {
        return level;
    }

    public String getKlazz() {
        return klazz;
    }

    public enum FlagLevel {
        WARN,
        ALERT,
        DETECTED
    }

}
