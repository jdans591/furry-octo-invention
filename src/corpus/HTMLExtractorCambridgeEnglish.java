/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corpus;

import java.util.HashSet;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This class extends the HTMLExtractor class. It is a specific implementation
 * of the HTMLExtractor class. It can scrape the text content of the English
 * Wikipedia web site.
 *
 * @author Dhanasit
 */
public class HTMLExtractorCambridgeEnglish extends HTMLExtractor {

    private HashSet<String> links;

    private final String baseURL = "http://dictionary.cambridge.org/dictionary/english/";
    protected static final int MAX_DEPTH = 1;
    private static final double MAX_PAGE = 100;

    private final int pageNumber = 0;

    protected String modifier = "comments";

    /**
     * Default constructor
     */
    public HTMLExtractorCambridgeEnglish() {
        links = new HashSet<>();
    }

    /**
     * This method selects the text to scrape from, using the English Wikipedia
     * HTML formatting.
     *
     * @return
     */
    @Override
    public Elements selectElement(Document doc) {
        Elements element = doc.select(".eg");
        return element;
    }

    /**
     * Get links inside the Wikipedia, whose pages exists.
     */
    @Override
    public void selectLink(Element page, double currentDepth, String baseURL) {
        //Do nothing.
        currentDepth++;
        extractLinks(baseURL, currentDepth + 1);
        
    }

}
