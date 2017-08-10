/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corpus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dhanasit
 */
public class WordListGenerator {

    private String fileName = "";

    private HashSet<WordInformation> wordHashSet;

    private ArrayList<WordInformation> wordInformations;

    private ArrayList<WordInformation> wordList = new ArrayList<>();

    private int counter = 0;

    public WordListGenerator() {

    }

    public WordListGenerator(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Generate a word frequency list based on the input file name.
     */
    public void generateWordFrequencyListEnglish(String fileName, int nGramLength) {
        FileReader fReader = null;
        BufferedReader bReader = null;
        FileWriter fWriter = null;
        BufferedWriter bWriter = null;

        wordList.clear();

        try {//start try catch for readers and writers
            fReader = new FileReader(fileName + ".txt");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WordListGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        bReader = new BufferedReader(fReader);

        try {
            fWriter = new FileWriter(fileName + "_frequency_list" + nGramLength + ".txt", true);
        } catch (IOException ex) {
            Logger.getLogger(WordListGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        bWriter = new BufferedWriter(fWriter);
        //end try catch for readers and writers

        try {
            String line;
            String[] words;
            while ((line = bReader.readLine()) != null) {
                line = line.replace(".", "").replace("!", "").replace("[", " ").replace("]", " ").replace("(", " ").replace(")", " ")
                        .replace("/", " ").replace("?", " ").replace(",", " ").replace(":", " ").replace(";", " ").replace("\"", " ")
                        .replace("$", " ").replace("->", " ").replace(" -", " ").replace("- ", " ");

                words = line.split("\\s+|\\t+");
                for (int i = 0; i < words.length; i++) {
                    String word = "";

                    switch (nGramLength) {
                        case 1:
                            word = new StringBuilder(words[i]).toString();
                            break;
                        case 2:
                            if (i + 1 < words.length) {
                                word = new StringBuilder(words[i]).append(" ").append(words[i + 1]).toString();
                            }
                            break;
                        case 3:
                            if (i + 2 < words.length) {
                                word = new StringBuilder(words[i]).append(" ").append(words[i + 1]).append(" ").append(words[i + 2]).toString();
                            }
                            break;
                        case 4:
                            if (i + 3 < words.length) {
                                word = words[i] + " " + words[i + 1] + " " + words[i + 2] + " " + words[i + 3];
                            }
                            break;
                        case 5:
                            if (i + 4 < words.length) {
                                word = words[i] + " " + words[i + 1] + " " + words[i + 2] + " " + words[i + 3] + " " + words[i + 4];
                            }
                            break;
                    }

                    WordInformation wordInformation = new WordInformation(word);

                    boolean containFlag = false;
                    for (WordInformation wordIterate : wordList) {
                        if (wordIterate.name.equals(wordInformation.name)) {
                            containFlag = true;
                            wordIterate.frequency++;
                            break;
                        }
                    }
                    if (containFlag == false && wordList.size() < 20000) {
                        wordList.add(wordInformation);
                        // System.out.println(wordInformation.name + " : " + wordInformation.frequency + " : " + counter++);
                    }

                }

            }

        } catch (IOException ex) {
            Logger.getLogger(WordListGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        Collections.sort(wordList);

        int sum = 0;
        for (WordInformation wordInformation : wordList) {
            sum = sum + wordInformation.frequency;
        }

        this.writeToFile(bWriter);
        try {

            bWriter.flush();
            bWriter = new BufferedWriter(fWriter);
            bWriter.write(String.valueOf(sum));

            bWriter.close();

        } catch (IOException ex) {
            Logger.getLogger(WordListGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    protected void writeToFile(BufferedWriter bWriter) {
        for (WordInformation word : wordList) {
            try {
                bWriter.write(word.name + "\t" + word.frequency);
                bWriter.newLine();
            } catch (IOException ex) {
                Logger.getLogger(WordListGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        try {
            bWriter.flush();
            //bWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(WordListGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
