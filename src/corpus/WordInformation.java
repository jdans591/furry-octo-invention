/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corpus;

import java.util.ArrayList;

/**
 *
 * @author Dhanasit
 */
public class WordInformation implements Comparable<WordInformation> {

    protected String name;

    protected int frequency;

    protected ArrayList<WordInformation> tags;

    protected ArrayList<WordInformation> prerequisites;
    
    protected double specifivity;

    public WordInformation() {

    }

    public WordInformation(String name) {
        this.name = name;
        this.frequency = 1;
    }

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

    @Override
    public boolean equals(Object o) {
        if (o instanceof WordInformation) {
            WordInformation word = (WordInformation) o;
            return this.name.equalsIgnoreCase(((WordInformation) o).name);
        }
        return false;
    }

}
