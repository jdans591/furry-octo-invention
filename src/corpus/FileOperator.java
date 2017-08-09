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
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *Deals with operations relating to file input/output.
 * @author Dhanasit
 */
public class FileOperator {
    
    
    
    public FileOperator() {
        
    }
    /**
     * Combine files into a single file name. The file is
     *
     * @param tag the list of tags the file must contain
     * @param fileName the output file name
     */
    public void combineFiles(String[] tags, String fileName) {

        ArrayList<File> arrayListFiles = listFiles(tags);
        
        FileReader fReader = null;
        BufferedReader bReader = null;
        FileWriter fWriter = null;
        BufferedWriter bWriter = null;
        String line = "";
        String[] components;
        int counter = 1;
        boolean flag = false;
        ArrayList<WordMetadata> wordMetadatas = new ArrayList<>();
        
        for(File file : arrayListFiles) {
            try {
                counter = 1; //counter reset for each file.
                fReader = new FileReader(file);
                bReader = new BufferedReader(fReader);
                while((line = bReader.readLine()) != null) {
                    flag = false;
                    components = line.split("\t"); //Split along tabs, components[0] should be the word, components[1] should be the frequency..
                    if(components.length == 1) {
                        break;
                    }
                    String category = file.getName().split("_")[file.getName().split("_").length - 3];
                    WordMetadata WM = new WordMetadata(components[0], "english");
                    int frequency = Integer.parseInt(components[1]);
                    WM.setFrequencies(category, frequency);
                    WM.setRankings(category, counter);
                    
                  
                    // Iterate through the wordMetadatas, checking if the generated WM pulled from the bufferedReader is in it. (equal name and language).
                    for(WordMetadata wordMetadata : wordMetadatas) {
                        if(wordMetadata.equals(WM)) { //If the metadata already exists then add to the frequency
                            wordMetadata.setFrequencies(category, frequency);
                            wordMetadata.setRankings(category, counter);
                            flag = true;
                            break;
                        }
                        
                    }
                    //If the wordMetadatas doesn't contain the current WM.
                    if(flag == false) {
                        wordMetadatas.add(WM);
                    }
                    
                    
                    
                    
                    counter++;
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FileOperator.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FileOperator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    /**
     * List relevant files according to the tag and root directory (working
     * directory).
     *
     * @param tags.
     * @return a list of relevant file.
     */
    public ArrayList<File> listFiles(String[] tags) {
        String currentDirectory = System.getProperty("user.dir");
        File root = new File(currentDirectory);

        ArrayList<File> arrayListFiles = new ArrayList<>();
        Collection<File> files = FileUtils.listFiles(root, "txt".split("  "), true);
        for (File file : files) {
            for (String tag : tags) {
                if (!file.getName().contains(tag)) {
                    break; // go to next file
                } else {

                }
            }

            arrayListFiles.add(file);

        }
        return arrayListFiles;

    }
    
    public void writeWordMetadatasToFile(ArrayList<WordMetadata> wordMetadatas) {
        
        String currentDirectory = System.getProperty("user.dir");
        File root = new File(currentDirectory);
        
        
        try {
            FileWriter fWriter = null;
            BufferedWriter bWriter = null;
            
            fWriter = new FileWriter(root);
            bWriter = new BufferedWriter(fWriter);
            
            
        } catch (IOException ex) {
            Logger.getLogger(FileOperator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
