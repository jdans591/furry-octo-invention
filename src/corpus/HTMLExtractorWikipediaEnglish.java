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
public class HTMLExtractorWikipediaEnglish extends HTMLExtractor {

    private HashSet<String> links;

    private final String baseURL = "https://en.wikipedia.org/wiki/";
    protected static final double MAX_DEPTH = 2;
    private static final double MAX_PAGE = 100;

    private final int pageNumber = 0;

    protected String modifier = "comments";

    public HTMLExtractorWikipediaEnglish() {
        links = new HashSet<>();
    }

    @Override
    public Elements selectElement(Document doc) {
        Elements element = doc.select("p");
        return element;
    }

    /**
     * Get links inside the Wiki, whose pages exists.
     */
    @Override
    public void selectLink(Element page, double currentDepth, String baseURL) {
        String linkName = page.attr("abs:href");
        if (linkName.contains("https://en.wikipedia.org/wiki/") && !linkName.contains("#")
                && !linkName.contains("redlink=1") && linkName.length() - linkName.replace(":", "").length() <= 1) {
            currentDepth++;
            extractLinks(page.attr("abs:href"), currentDepth + HTMLExtractor.MAX_DEPTH - MAX_DEPTH);
        }
    }

}
