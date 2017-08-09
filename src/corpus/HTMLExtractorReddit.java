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
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 *
 * @author Dhanasit
 */
public class HTMLExtractorReddit extends HTMLExtractor {

    private HashSet<String> links;

    private final String globalURL = "https://www.reddit.com/r/travel/";
    protected static final double MAX_DEPTH = 2;
    private static final double MAX_PAGE = 10;

    private final int pageNumber = 0;

    protected String modifier = "comments";

    public HTMLExtractorReddit() {
        links = new HashSet<String>();
    }

    public Elements selectElement(Document doc) {
        Elements element = doc.select("div.md > p:first-child");
        return element;
    }

    /**
     * Select valid links from Reddit to pull the text content from.
     */
    public void selectLink(Element page, double currentDepth, String baseURL) {
        if (page.attr("abs:href").contains(baseURL) && !page.attr("abs:href").contains("#")
                && !page.attr("abs:href").equals(baseURL)) {

            if (page.attr("abs:href").contains(modifier) && false) {
                currentDepth++;
                extractLinks(page.attr("abs:href"), currentDepth + super.MAX_DEPTH - MAX_DEPTH);
            } else if (page.attr("abs:href").contains("?count")) {
                currentDepth = currentDepth + MAX_DEPTH / MAX_PAGE;
                extractLinks(page.attr("abs:href"), currentDepth + super.MAX_DEPTH - MAX_DEPTH);

            }

        }

    }

}
