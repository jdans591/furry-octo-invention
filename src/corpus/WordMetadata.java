/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corpus;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Dhanasit
 */
public class WordMetadata {
    
    private String name;
    private String language;
    private String PoS;
    private HashMap<String, Integer> frequencies = new HashMap(); // A map of frequencies of this word for each category/topic.
    private HashMap<String, Integer> rankings = new HashMap();
    
    
    
    public WordMetadata() {
        
    }
    public WordMetadata(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getFrequencies(String category) {
        return this.frequencies.get(category);
    }
    public Integer getRankings(String category) {
        return  this.rankings.get(category);
    }
    public void setFrequencies(String category, int value) {
        this.frequencies.put(category, value);
    }
    public void setRankings(String category, int value) {
        this.rankings.put(category, value);
    }
    
    
    
}
