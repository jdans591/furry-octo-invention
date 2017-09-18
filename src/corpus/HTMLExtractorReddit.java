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
        links = new HashSet<>();
    }

    @Override
    public Elements selectElement(Document doc) {
        Elements element = doc.select("div.md > p:first-child");
        return element;
    }

    /**
     * Select valid links from Reddit to pull the text content from.
     */
    @Override
    public void selectLink(Element page, double currentDepth, String baseURL) {
        if (page.attr("abs:href").contains(baseURL) && !page.attr("abs:href").contains("#")
                && !page.attr("abs:href").equals(baseURL)) {

            if (page.attr("abs:href").contains(modifier) && false) {
                currentDepth++;
                extractLinks(page.attr("abs:href"), currentDepth + HTMLExtractor.MAX_DEPTH - MAX_DEPTH);
            } else if (page.attr("abs:href").contains("?count")) {
                currentDepth = currentDepth + MAX_DEPTH / MAX_PAGE;
                extractLinks(page.attr("abs:href"), currentDepth + HTMLExtractor.MAX_DEPTH - MAX_DEPTH);

            }

        }

    }

}
