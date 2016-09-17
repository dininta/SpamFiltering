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
            System.out.println(text);
            //Replace All Non Alphabetic
            //text = text.replaceAll("[^a-zA-Z\\s]", " ");
            text = prosesNonAlfabet (text.toLowerCase());
            
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
    
    public String prosesNonAlfabet (String _text){
        String text = _text;
        boolean match;
        if (match = text.matches("(.*)rek(.*)|(.*)rekening(.*)|(.*)transfer(.*)")) 
        {
            text = text.replaceAll("[\\d]{8,}", "noRek");
        }
        else if(match = text.matches("(.*)hp(.*)|(.*)handphone(.*)|(.*)tlp(.*)|(.*)telepon(.*)|(.*)telp(.*)|(.*)hub(.*)|(.*)hubungi(.*)")) {
            text = text.replaceAll("[\\d]{4,20}", "noHP");
        }
        text = text.replaceAll("08\\d{6,20}", "noHP");
        text = text.replaceAll("[^a-zA-Z\\s]", " ");
        return text;
    }

}
