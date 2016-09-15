/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spam.filtering;

import IndonesianNLP.IndonesianSentenceFormalization;
import IndonesianNLP.IndonesianStemmer;
import java.util.ArrayList;

/**
 *
 * @author mochamadtry
 */
public class SpamFiltering {

    protected IndonesianSentenceFormalization formalizer;
    protected IndonesianStemmer stemmer;
    
    public SpamFiltering() {
        formalizer = new IndonesianSentenceFormalization();
        stemmer = new IndonesianStemmer();
    }
    
    public ArrayList preProcess(ArrayList listOfText) {
        ArrayList processed = new ArrayList();
        for (int i = 0; i < listOfText.size(); i++) {
            // Formalization
            String text = formalizer.normalizeSentence(listOfText.get(i).toString());
            
            //Replace All Non Alphabetic
            text = text.replaceAll("[^a-zA-Z\\s]", " ");
            
            //Stop Word
            formalizer.initStopword();
            text = formalizer.deleteStopword(text);
                  
            // Stemming
            StringBuilder result = new StringBuilder();
            String[] words = text.split(" ");
            for (String word : words) {
                if (word.length() > 1) result.append(stemmer.stem(word + " "));
            }
            
            processed.add(result.toString());
        }
        return processed;
    }
    
}
