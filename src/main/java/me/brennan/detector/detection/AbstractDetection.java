package me.brennan.detector.detection;

/**
 * @author Brennan
 * @since 12/8/2020
 **/
public abstract class AbstractDetection implements Detection {
    private final String name;

    public AbstractDetection(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
