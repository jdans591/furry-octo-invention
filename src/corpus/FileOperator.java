/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corpus;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
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

        FileUtils.listFiles(directory, tags, true)
        
        FileReader fReader = new FileReader();
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
    
}
