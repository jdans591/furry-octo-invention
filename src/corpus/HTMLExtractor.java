/*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
 */
package corpus;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Dhanasit
 */
public class HTMLExtractor {

    protected HashSet<String> links;
    protected ArrayList<String> listString;

    protected String globalURL = "https://www.reddit.com/r/travel/";
    protected static final int MAX_DEPTH = 2;
    protected static final double MAX_PAGE = 3;
    protected static final String LINK_PREFIX = "link_of_";
    
    protected FileOperator fileOperator = new FileOperator();

    protected String modifier = "comments";

    protected int counter = 0;

    public HTMLExtractor() {
        links = new HashSet<>();
    }

    /**
     * Extracts the links for data pulling later. The method is based on the
     * baseURL, to prevent the scraper from pulling from other random web sites
     * and currentDepth, used for recursive calling. currentDepth defines the
     * depth of the links to search.
     *
     * The links are stored in a HashSet of Strings.
     *
     * @param baseURL
     * @param currentDepth
     */
    public void extractLinks(String baseURL, double currentDepth) {

        //If link is not already in the list, add it
        if (!links.contains(baseURL) && currentDepth < MAX_DEPTH) {
            try {
                if (links.add(baseURL)) {
                    if(baseURL.contains("http://dictionary.cambridge.org/dictionary/english/")) {
                        
                        return;
                    }
                    System.out.println(baseURL); //list of links added to the list
                }

                //Connect and select only links (a href tags).
                Document document = Jsoup.connect(baseURL).get();
                Elements linksOnPage = document.select("a[href]");

                //For each link in a page, select valid ones.
                linksOnPage.forEach((page) -> {
                    //Implement according to website being searched. (specific implementation).
                    selectLink(page, currentDepth, baseURL);
                    //end specific implementation
                });
            } catch (IOException e) {

            }
        }

    }

    /**
     * Converts the links stored as HashSet of Strings, to a text file. Each
     * link is stored in a single line in the text file.
     *
     * @param fileName
     */
    public void writeLinksToFile(String fileName) {
        File file = new File(LINK_PREFIX + fileName);

      

        //Start iterating through the hashset (in no particular order).
        Iterator<String> iterator = links.iterator();
        try {
            //Connect and set up writers.

            try (FileWriter fWriter = new FileWriter(fileName, true)) {

                FileWriter linkWriter = new FileWriter(LINK_PREFIX + fileName, true);
                // Write the list of links into a file. Format is (link_of_"baseName").txt
                try (BufferedWriter bfLinkWriter = new BufferedWriter(linkWriter)) {
                    // Write the list of links into a file. Format is (link_of_"baseName").txt
                    while (iterator.hasNext()) {
                        writeStringToFile(iterator.next(), bfLinkWriter);
                    }
                    links = new HashSet<>();
                }
            }
        } catch (IOException ex) {

        }

    }

    /**
     * Use the links pulled from extractLinks to pull data into text files.
     *
     * @param baseURL
     * @param fileName
     */
    public void writeToFile(String baseURL, String fileName) {

        //Start iterating through the hashset (in no particular order).
        Iterator<String> iterator = links.iterator();
        try {
            //Connect and set up writers.
            Document doc;
            try (FileWriter fWriter = new FileWriter(fileName, true); BufferedWriter bfWriter = new BufferedWriter(fWriter)) {

                Iterator<String> iterator2 = links.iterator();

                //Iterate through each link. Pull out the text content, and put them into a file called "baseName".txt
                while (iterator2.hasNext()) {
                    counter++;
                    String currentURL = iterator2.next();
                    doc = Jsoup.connect(currentURL).get();

                    //Specific implementation (select only relevant text to pull).
                    Elements elements = selectElement(doc);
                    //End specific implementation

                    // Write the relevant texts to a line. Each element is separated by double tab characters.
                    for (Element el : elements) {
                        String text = el.text();
                        bfWriter.write(text);
                        bfWriter.write("\t\t");
                    }

                    bfWriter.newLine();

                }

            }

        } catch (IOException ex) {
            Logger.getLogger(HTMLExtractorReddit.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Method to override
     *
     * @param doc
     * @return
     */
    public Elements selectElement(Document doc) {
        Elements element = doc.select("html");
        return element;
    }

    /**
     * Method to override
     *
     * @param page
     * @param currentDepth
     * @param baseURL
     */
    public void selectLink(Element page, double currentDepth, String baseURL) {
        if (this.getClass().getSimpleName().contains("Reddit")) {
            selectLink(page, currentDepth, baseURL);
        }
    }

    public void clearLinks() {
        links.clear();
    }

    public HashSet<String> getLinks() {
        return links;
    }

    /**
     * Write the input string into a line of a file. (Helper method).
     *
     * @param string
     * @param bfWriter
     */
    protected void writeStringToFile(String string, BufferedWriter bfWriter) {
        try {
            bfWriter.write(string);
            bfWriter.newLine();
        } catch (IOException ex) {
            Logger.getLogger(HTMLExtractor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
