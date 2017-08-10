package corpus;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents the information contained within a word. This class
 * functionalities will be superceded by the WordMetadata class later on.
 *
 * @author Dhanasit
 */
public class WordInformation implements Comparable<WordInformation> {

    protected String name;

    protected int frequency;

    protected ArrayList<WordInformation> tags;

    protected ArrayList<WordInformation> prerequisites;

    protected double specificity;

    /**
     * Default constructor.
     */
    public WordInformation() {

    }

    /**
     * Initialization of the WordInformation class with a given input word name.
     *
     * @param name
     */
    public WordInformation(String name) {
        this.name = name;
        this.frequency = 1;
    }

    /**
     * Compare between two WordInformation objects to see which one is
     * 'greater'. A WordInformation object is defined to be equal if they share
     * the same name. Otherwise the WordInformation object with the greater
     * frequency is deemed to be 'greater'.
     *
     * @param t
     * @return
     */
    @Override
    public int compareTo(WordInformation t) {

        if (this.name.equals(t.name)) {
            return 0;

        } else if (this.frequency < t.frequency) {
            return 1;
        } else if (this.frequency > t.frequency) {
            return -1;
        }

        return 0;

    }

    /**
     * Overrides the equals method. Compares two WordInformation objects to see
     * if they are equal. Two WordInformation objects are defined to be equal if
     * they share the same name, ignoring cases. Otherwise they are compared
     * lexicographically alphabetically.
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof WordInformation) {
            WordInformation word = (WordInformation) o;
            return this.name.equalsIgnoreCase(((WordInformation) o).name);
        }
        return false;
    }

    /**
     * Overridden hashCode method.
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.name);
        hash = 73 * hash + this.frequency;
        hash = 73 * hash + Objects.hashCode(this.tags);
        hash = 73 * hash + Objects.hashCode(this.prerequisites);
        hash = 73 * hash + (int) (Double.doubleToLongBits(this.specificity) ^ (Double.doubleToLongBits(this.specificity) >>> 32));
        return hash;
    }

}
