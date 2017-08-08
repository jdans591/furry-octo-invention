/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corpus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Dhanasit
 */
public class Main {

    enum categoriesReddit {
        travel, sports, photography
        
    }

    enum categoriesWikipedia {
//        Travel, Wilderness, Hiking, Adventure, Fishing, Camping, Hunting, Beach,
        Sport, Gym, Walking, Physical_fitness, Health, Dance, Football, 
        Music, Book, Film, Writing, Concert, Video_game, Netflix,
        Food, Beer, Eating, Cooking, Alcoholic_drink, Pizza, Coffee, Wine,
//        Photography, 
//        Animal, Dog, Cat, Pet, Family, Joke, God,
//        Nerd, Student, Job, Employment, Career,
//        Tattoo, Smoking,
    }

    enum fileNames {
//        reddit_travel, reddit_sports, reddit_photography,
//        wikipedia_english_Travel, wikipedia_english_Wilderness, wikipedia_english_Hiking, wikipedia_english_Adventure, wikipedia_english_Fishing, 
//        wikipedia_english_Camping, wikipedia_english_Hunting, wikipedia_english_Beach, 
        wikipedia_english_Sport, wikipedia_english_Gym, wikipedia_english_Walking, wikipedia_english_Physical_fitness, wikipedia_english_Health, 
        wikipedia_english_Dance, wikipedia_english_Football, 
        wikipedia_english_

    }

    private static ArrayList<String> listStringReddit = new ArrayList<>();
    private static ArrayList<String> listStringWikipedia = new ArrayList<>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Connection connection = null;
        Statement statement = null;

//        try {
//            Class.forName("org.sqlite.JDBC");
//            connection = DriverManager.getConnection("jdbc:sqlite:test.db");
//            
//            statement = connection.createStatement();
//            String sql = "CREATE TABLE COMPANY " + 
//                            "(ID INT PRIMARY KEY     NOT NULL, " +
//                            " NAME           TEXT    NOT NULL, " +
//                            " AGE            INT     NOT NULL, " +
//                            " ADDRESS        CHAR(50), " +
//                            " SALARY         REAL);";
//            statement.execute(sql);
//            
//            
//            statement.close();
//            connection.close();
//            
//        } catch(Exception e) {
//            System.out.println(e.getMessage());
//        }
        //Make completed lists based on websites used to pull. These are blacklisted topics that won't be used.
//        listStringReddit = makeCompletedList("topics_reddit_completed.txt");
        listStringWikipedia = makeCompletedList("topics_wikipedia_completed.txt");

        // scrape reddit based on input subreddit. Iterate through the enums
//        for (categoriesReddit category : categoriesReddit.values()) {
//
//            if (!listStringReddit.contains(category.name())) {
//
//                scrapeReddit(category.toString());
//            }
//        }

        // scrape wikipedia based on input topic. Iterate through the enums
        for (categoriesWikipedia category : categoriesWikipedia.values()) {
            if (!listStringWikipedia.contains(category.name())) {

                scrapeWikipedia(category.toString());
            }
        }

        ///////////////////////////////////////////////////////// End scrape
        /////////////////////////////////////////////////////Start generating word frequency list
        generateWordList();

        ////////////////////////////////////////////////////End generating word frequency list
        
        ////////////////////////////////////////////////////Start calculating word specifivity
        //calculateWordSpecifivityEnglish();
        ///////////////////////////////////////////////////End calculating word specifivity
    }

    private static void generateWordList() {
        System.out.println("Up to generating word frequency list");
        WordListGenerator wordListGenerator = new WordListGenerator();

        // Generate word frequency lists for all the filenames in the fileNames enum.
        for (categoriesWikipedia category: categoriesWikipedia.values()) {
            

            wordListGenerator.generateWordFrequencyListEnglish("wikipedia_english_" + category.name(), 1);
            System.out.println("Successful?");

        }
    }

    /**
     * Scrape the content of Wikipedia based on the input topic. The program
     * will search through most links and recursively scrape using those links.
     */
    private static void scrapeWikipedia(String topic) {
        HTMLExtractorWikipediaEnglish HTMLewe = new HTMLExtractorWikipediaEnglish();
        String URL = "https://en.wikipedia.org/wiki/" + topic;

        HTMLewe.ExtractURL(URL, 0);
        HTMLewe.writeLinksToFile("wikipedia_english_" + topic + ".txt");
        HTMLewe.writeToFile(URL, "wikipedia_english_" + topic + ".txt");
    }

    /**
     * Scrape the content in Reddit based on the input sub Reddit.
     */
    private static void scrapeReddit(String subReddit) {
        HTMLExtractorReddit HTMLExtractorReddit = new HTMLExtractorReddit();

        String URL = "https://www.reddit.com/r/" + subReddit + "/";

        HTMLExtractorReddit.ExtractURL(URL, 0);
        HTMLExtractorReddit.writeLinksToFile("reddit_" + subReddit + ".txt");
        HTMLExtractorReddit.writeToFile(URL, "reddit_" + subReddit + ".txt");
    }

    /**
     * Convert the topics in the file name into elements in an array list,
     * separated by newlines. These topics are blacklisted from being processed
     * further (as they are already processed)
     */
    private static ArrayList<String> makeCompletedList(String fileName) {

        ArrayList<String> listString = new ArrayList<>();
        try {
            //Read files. Add each line of the file to the listString array list.
            FileReader fReader = new FileReader(fileName);
            BufferedReader bReader = new BufferedReader(fReader);

            try {

                String line;
                while ((line = bReader.readLine()) != null) {
                    listString.add(line);

                }
                bReader.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listString;
    }
    
    private static void calculateWordSpecifivityEnglish(String fileName) {
        
        String currentDirectory = System.getProperty("user.dir");
        File root = new File(currentDirectory);
        File[] fileList = root.listFiles();
        
        for(File file : fileList) { // look for text files which have the relevant file name input.
            if(file.getName().contains(fileName) && file.getName().contains(".txt") && file.getName().contains("frequency_list") && 
                    file.getName().contains("english")) {
                
            }
        }
        String[] stringArray = new String[1];
        stringArray[0] = "txt";
        Collection<File> files = FileUtils.listFiles(root, "txt".split("  "), true);
        
        
        
        FileReader fReader = null;
        BufferedReader bReader = null;
        try {
            for(File file : files) {
                fReader = new FileReader(file.getName());
                bReader = new BufferedReader(fReader);
                
                try {
                    String line = "";
                    while((line = bReader.readLine()) != null) {
                        String[] components = line.split("\t");
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

}
