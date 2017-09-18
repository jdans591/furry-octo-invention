package corpus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

/**
 * This class represents the WordMetadata, i.e. the word and all the relevant
 * information the word contains. This also includes frequencies and rankings of
 * the word for each topic. When information about a word is needed, the
 * information should be contained in this class.
 *
 * @author Dhanasit
 */
public class WordMetadata {

    private String name;
    private String language;
    private String PoS;
    private double specificity;
    private HashMap<String, Integer> frequencies = new HashMap(); // A map of frequencies of this word for each category/topic.
    private HashMap<String, Integer> rankings = new HashMap(); // A map of the rankings of this word for each category topic.

    /**
     * Default constructor
     */
    public WordMetadata() {

    }

    /**
     * Constructor to set the word name
     *
     * @param name
     */
    public WordMetadata(String name) {
        this.name = name;
    }

    /**
     * Constructor to set the word name and the language the word is in
     *
     * @param name
     * @param language
     */
    public WordMetadata(String name, String language) {
        this.name = name;
        this.language = language;
    }

    /**
     * Set the word specificity rating for this wordMetadata
     *
     * @param specificity
     */
    protected void setSpecificity(double specificity) {
        this.specificity = specificity;
    }

    /**
     * Get the word specificity rating for this wordMetadata
     *
     * @return
     */
    public double getSpecificity() {
        return this.specificity;
    }

    /**
     * Get all the categories present in the frequencies map.
     *
     * @return An array list of strings that contain the categories in the
     * frequencies field map.
     */
    public ArrayList<String> getFrequencyCategories() {
        ArrayList<String> result = new ArrayList<>(); //Define result to be the array list of strings that contains the categories in the frequencies field map.

        //Iterate through each category in the frequencies field map.
        frequencies.keySet().forEach((category) -> {
            result.add(category);
        });

        return result;
    }

    /**
     * Get all the categories present in the rankings map.
     *
     * @return An array list of strings that contain the categories in the
     * rankings field map.
     */
    public ArrayList<String> getRankingCategories() {
        ArrayList<String> result = new ArrayList<>(); // Define result to be the array list of strings that contains the categories in the rankings field map.

        //Iterate through each category in the rankings field map.
        rankings.keySet().forEach((category) -> {
            result.add(category);
        });

        return result;
    }

    /**
     * Get all the frequencies in the frequencies field map.
     *
     * @return
     */
    public ArrayList<Integer> getFrequencies() {
        ArrayList<Integer> result = new ArrayList<>(); // Define result to be the array list of integer that contains all the frequencies for each category in the 
        //frequencies field map.
        //Iterate through each frequency in the frequencies field map.
        frequencies.values().forEach((frequency) -> {
            result.add(frequency);
        });

        return result;
    }

    /**
     * Get all the rankings in the rankings field map.
     *
     * @return
     */
    public ArrayList<Integer> getRankings() {
        ArrayList<Integer> result = new ArrayList<>(); // Define result to be the array list of integer that contains all the rankings for each category in the 
        //rankings field map.
        //Iterate through each ranking in the rankings field map.
        rankings.values().forEach((ranking) -> {
            result.add(ranking);
        });

        return result;
    }

    /**
     * Return the name (the spelling of the word)
     *
     * @return . the lexicographical spelling of the word.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name of the word.
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the frequency of the word from the input category (topic).
     *
     * @param category
     * @return The frequency of the word
     */
    public Integer getFrequency(String category) {
        return this.frequencies.get(category);
    }

    /**
     * Get the ranking of the word from the input category (topic). The ranking
     * is the rank of the word's popularity for a particular input
     * topic/category.
     *
     * @param category
     * @return The ranking of the word.
     */
    public Integer getRanking(String category) {
        return this.rankings.get(category);
    }

    /**
     * Set the frequency of the word for an input category and value.
     *
     * @param category
     * @param value
     */
    public void setFrequency(String category, int value) {
        Integer temp = value;
        this.frequencies.put(category, temp);
    }

    /**
     * Set the ranking of the word for an input category and value.
     *
     * @param category
     * @param value
     */
    public void setRanking(String category, int value) {
        Integer temp = value;
        this.rankings.put(category, temp);
    }

    /**
     * Calculate the word specificity from the various word frequency lists. A
     * word's specificity rating is an estimate of how specific a word is to a
     * topic. A higher value indicates that a word is more specific to a topic.
     * A lower value indicates that a word is generally used across many
     * different topics.
     *
     * @return the word specificity value as a double.
     */
    public double calculateSpecificity() {
        ArrayList<Integer> integerList = new ArrayList<>();

        //Convert the values in the rankings to a list of integers.
        rankings.values().forEach((integer) -> {
            integerList.add(integer);
        });

        //Sort the integer list and convert to a double array format ready for analysis.
        Collections.sort(integerList);
        double[] doubleArray = integerList.stream().mapToDouble(i -> i).toArray();

        //Use the Statistics class to get the common statistical measures of central tendencies and standard deviation for analysis later.
        Statistics statistics = new Statistics(doubleArray);
        double median = statistics.getMedian();
        double mean = statistics.getMean();
        double stdDev = statistics.getStdDev();

        this.setSpecificity(stdDev); // Set the specificity rating for this wordMetadata.
        return stdDev;

    }

    /**
     * This method overrides the standard 'equals' method used in Java. It
     * compares the WordMetadata to see if they are equal. 2 WordMetadata are
     * defined to be equal if the lower case of the WordMetadata have the same
     * name AND come from the same language.
     *
     * @param o the object for comparison (hopefully a WordMetadata object).
     * @return whether the 2 objects are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof WordMetadata) {
            WordMetadata wordMetadata = (WordMetadata) o;
            return this.name.equalsIgnoreCase(wordMetadata.name) && this.language.equals(wordMetadata.language);
        }
        return false;
    }

    @Override
    /**
     * This is the overridden hashCode method used in Java.
     */
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.language);
        hash = 97 * hash + Objects.hashCode(this.PoS);
        hash = 97 * hash + Objects.hashCode(this.frequencies);
        hash = 97 * hash + Objects.hashCode(this.rankings);
        return hash;
    }

    /**
     * An overridden method aimed to convert a wordMetadata into a string
     * representation, usually to write to a file later.
     *
     * @return The string representation of the wordMetadata, ready to be
     * written to a file.
     */
    @Override
    public String toString() {
        String result; //Define string result to be the resultant string to be returned.
        String wordName;
        String frequencyString; //Define frequencyString to be the string containing all the frequency categories and values
        String rankingString;
        String temp = "";
        ArrayList<String> frequenciesCategories;
        ArrayList<String> rankingsCategories;

        //We want the name, frequency map, and ranking map.
        wordName = this.getName(); // Name done.

        frequenciesCategories = this.getFrequencyCategories(); //Get the categories in array list of string format (unsorted).
        rankingsCategories = this.getRankingCategories(); //Get the rankings in array list of string format (unsorted).

        Collections.sort(frequenciesCategories); //Sort both lists so that the resulting string is written in alphabetical order.
        Collections.sort(rankingsCategories);

        //For each frequency category, append the temporary string with the category name and the actual frequency, in alphabetical order.
        for (String frequencyCategory : frequenciesCategories) {
            this.getFrequency(frequencyCategory);
            temp = temp + frequencyCategory + " " + this.getFrequency(frequencyCategory).toString() + " ";
        }
        frequencyString = temp.substring(0, temp.length() - 1); // remove the last character of the temporary string (a whitespace).
        temp = ""; // reset the temp string for use in the next loop.

        //For each ranking category, append the rankingString with the category name and the actual ranking, in alphabetical order.
        for (String rankingCategory : rankingsCategories) {
            this.getRanking(rankingCategory);
            temp = temp + rankingCategory + " " + this.getRanking(rankingCategory).toString() + " ";
        }
        rankingString = temp.substring(0, temp.length() - 1); // remove the last character of the temporary string (a whitespace).

        result = wordName + "\t" + frequencyString + "\t" + rankingString + "\t" + this.getSpecificity();

        return result;
    }

}
