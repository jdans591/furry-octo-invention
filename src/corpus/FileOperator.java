package corpus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 * Deals with operations relating to file input/output.
 *
 * @author Dhanasit
 */
public class FileOperator {

    private int test = 0;

    /**
     * Default constructor
     */
    public FileOperator() {

    }

    /**
     * Combine files into a single file name.
     *
     * @param tags the list of tags the file must contain
     * @param fileName the output file name
     */
    public void combineFiles(String[] tags, String fileName) {

        //Definitions of variables. These include arrayListFiles which is the list of relevant file names to combine.
        //Readers and writers are also defined here.
        ArrayList<File> arrayListFiles = listFiles(tags);

        FileReader fReader;
        BufferedReader bReader;
        FileWriter fWriter = null;
        BufferedWriter bWriter = null;
        //Define String line, which is the current line text pulled from buffered reader.
        //Define String array components, which are each component of information from the line. The default split is by tab character.
        String line;
        String[] components;
        //Define counter, which is the current lineNumber.
        //Define flag, which means whether the wordMetadata read from the frequency list is already present in the local list.
        int counter;
        int counters = 0;
        boolean flag;
        //Define wordMetadatas, which is a list of all the wordMetadata that we want to write to a single file later.
        ArrayList<WordMetadata> wordMetadatas = new ArrayList<>();
       
        //For each file in the relevant file list...
        for (File file : arrayListFiles) {
            try {
                counter = 1; //counter reset for each file.
                //Instantiate readers.
                fReader = new FileReader(file);
                bReader = new BufferedReader(fReader);
                //Read a line from a file and check if it's null.
                while ((line = bReader.readLine()) != null) {
                    flag = false; //By default
                    components = line.split("\t"); //Split along tabs, components[0] should be the word, components[1] should be the frequency..
                    //components' length should be 1 as there should be multiple components in a single line (e.g. word name, frequency).
                    if (components.length == 1) {
                        break;
                    }
                    //From how the file name is named, the category name should be the 2nd to last word in the file name, excluding the .txt file extension.
                    String category = file.getName().split("_")[file.getName().split("_").length - 3];
                    //components[0] should be the word name.
                    WordMetadata WM = new WordMetadata(components[0], "english");
                    //components[1] should be the frequency of the word of that particular topic/file.
                    int frequency = Integer.parseInt(components[1]);
                    //Record the frequencies and rankings of the wordMetadata by category.
                    WM.setFrequency(category, frequency);

                    WM.setRanking(category, counter);
                    
                    if(WM.getFrequency(category) < 5) {
                        continue;
                    }

                    //Check if the wordMetadatas contain the temporary WM wordmetadata.
                    for (WordMetadata wordMetadata : wordMetadatas) {

                        if (wordMetadata.equals(WM)) {
                            if (wordMetadata.getFrequency(category) != null && wordMetadata.getRanking(category) != null) {
                                wordMetadata.setFrequency(category, wordMetadata.getFrequency(category) + frequency);
                                wordMetadata.setRanking(category, wordMetadata.getRanking(category));
                            } else {
                                wordMetadata.setFrequency(category, frequency);
                                wordMetadata.setRanking(category, counter);
                            }

                            //System.out.println(counter);
                            flag = true;
                            break;
                        }

                    }

        //If the list of wordMetadata doesn't contain the wordMetadata read from the line, then add to the list.
                    if (flag == false) {
                        wordMetadatas.add(WM);
                    }

                    counter++; //Increment counter (see definition of counter at top of method).
                } //end read 1 line.
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FileOperator.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FileOperator.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Generated wordMetadata for a file.");

            counters++;
        }
        for (WordMetadata wordMetadata : wordMetadatas) {
            //System.out.println(wordMetadata.toString());
        }
        //Here we should have an array list of wordMetadata ready to be written to a file.

        this.writeWordMetadatasToFile(wordMetadatas, fileName);
    }

    /**
     * List relevant files according to the tag and root directory (working
     * directory). A relevant file must have all the tag string from the tags
     * input.
     *
     * @param tags.
     * @return a list of relevant file.
     */
    public ArrayList<File> listFiles(String[] tags) {
        String currentDirectory = System.getProperty("user.dir"); //Get the current directory of the system.
        File root = new File(currentDirectory);
        boolean flag; // Define flag to be whether a file contains every single tag.

        //Get a collection of all the txt files in the current directory.
        ArrayList<File> arrayListFiles = new ArrayList<>();
        Collection<File> files = FileUtils.listFiles(root, "txt".split("  "), true);
        //For each file, check whether it contains all the tags. These are the relevant files.
        for (File file : files) {
            flag = true; // Assume the file is valid until it's proven to be guilty.
            for (String tag : tags) {
                if (!file.getName().contains(tag)) {
                    flag = false;
                    break; // go to next file
                } else {

                }
            }
            if (flag == true) { // If a file contains every single tag, then add the file.
                //Add the file to the list of relevant files.
                arrayListFiles.add(file);
            }

        }
        return arrayListFiles; //return the relevant files.

    }

    /**
     * Write the array list of wordMetadata to a file. Each wordMetadata should
     * occupy one line, with each field separated by a tab character. There
     * should be a space between the category name and its corresponding
     * frequency or ranking.
     *
     * @param wordMetadatas
     * @param fileName the file name (including extension) of the file to write
     * to.
     */
    protected void writeWordMetadatasToFile(ArrayList<WordMetadata> wordMetadatas, String fileName) {

        String line; //Define a string line to represent the string representation of a wordMetadata.

        String currentDirectory = System.getProperty("user.dir");
        File root = new File(currentDirectory + File.separator + fileName);

        try {
            FileWriter fWriter;
            BufferedWriter bWriter;

            fWriter = new FileWriter(root, true); //Default is to append string.
            bWriter = new BufferedWriter(fWriter);

            //For each wordMetadata, convert to a string representation, and write its content to the file.
            for (WordMetadata wordMetadata : wordMetadatas) {
                line = wordMetadata.toString();
                bWriter.write(line);
                bWriter.newLine();
            }

            bWriter.close();
            fWriter.close();

        } catch (IOException ex) {
            Logger.getLogger(FileOperator.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

}
