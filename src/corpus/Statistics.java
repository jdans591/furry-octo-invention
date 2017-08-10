package corpus;

import java.util.Arrays;

/**
 * This class helps with statistical calculations such as finding mean, median,
 * and the standard deviation. The data has to be in a double array format.
 *
 * @author Dhanasit
 */
public class Statistics {

    double[] data; // The data to perform calculations on
    int size;

    /**
     * Initialise the Statistics instance with the double array data field.
     *
     * @param data the double array data field to set
     */
    public Statistics(double[] data) {
        this.data = data;
        size = data.length;
    }

    public void setData(double[] data) {
        this.data = data;
    }

    /**
     * Get the mean of the data field of this class
     *
     * @return the mean of the double array data field.
     */
    public double getMean() {
        double sum = 0.0;
        for (double a : data) {
            sum += a;
        }
        return sum / size;
    }

    /**
     * Get the variance of the data field of this class
     *
     * @return the variance of the double array data field.
     */
    public double getVariance() {
        double mean = getMean();
        double temp = 0;
        for (double a : data) {
            temp += (a - mean) * (a - mean);
        }
        return temp / (size - 1);
    }

    /**
     * Get the standard deviation of the data field of this class.
     *
     * @return the standard deviation of the double array data field.
     */
    public double getStdDev() {
        return Math.sqrt(getVariance());
    }

    /**
     * Get the median of the data field of this class.
     *
     * @return the median of the double array data field.
     */
    public double getMedian() {
        Arrays.sort(data);

        if (data.length % 2 == 0) {
            return (data[(data.length / 2) - 1] + data[data.length / 2]) / 2.0;
        }
        return data[data.length / 2];
    }
}
