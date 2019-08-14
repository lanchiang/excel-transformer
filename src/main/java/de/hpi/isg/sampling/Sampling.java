package de.hpi.isg.sampling;

/**
 * @author Lan Jiang
 * @since 2019-08-13
 */
public class Sampling {

    private final int sampleSize;

    private final int totalSize;

    public Sampling(int sampleSize, int totalSize) {
        this.sampleSize = sampleSize;
        this.totalSize = totalSize;
    }

    public boolean shouldSample() {
        double prob = (double) sampleSize / (double) totalSize;
        if (getRandom(0, 1) < prob) {
            return true;
        }
        return false;
    }

    /**
     * @param min - The minimum.
     * @param max - The maximum.
     * @return A random double between these numbers (inclusive the minimum and maximum).
     */
    public double getRandom(double min, double max) {
        return (Math.random() * (max + 1 - min)) + min;
    }
}
