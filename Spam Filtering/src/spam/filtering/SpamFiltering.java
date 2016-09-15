/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spam.filtering;

import IndonesianNLP.IndonesianSentenceFormalization;
import IndonesianNLP.IndonesianSentenceTokenizer;
import IndonesianNLP.IndonesianStemmer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author mochamadtry
 */
public class SpamFiltering {

    protected IndonesianSentenceFormalization formalizer;
    protected IndonesianStemmer stemmer;
    protected IndonesianSentenceTokenizer tokenizer;
    
    public SpamFiltering() {
        formalizer = new IndonesianSentenceFormalization();
        stemmer = new IndonesianStemmer();
        tokenizer = new IndonesianSentenceTokenizer();
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
    
    public ArrayList<String> generateFeatures(ArrayList<String> sentences){
        Map<String,Integer> counter = new HashMap<String, Integer>();
        ArrayList<String> result = new ArrayList<String>();
        
        for(int i=0; i<sentences.size(); i++) {
            ArrayList<String> token = tokenizer.tokenizeSentence(sentences.get(i));
            for(int j=0; j< token.size(); j++) {
                if(!counter.containsKey(token.get(j))) {
                    counter.put(token.get(j),1);
                } else {
                    int count = counter.get(token.get(j)) + 1;
                    counter.put(token.get(j), count);
                }
            }
        }

        result.addAll(counter.keySet());
        return result;
    }

    public void writeToArff(ArrayList<String> attribute, ArrayList spam, ArrayList notSpam, String filename) {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                file.createNewFile();
            }

            java.io.FileWriter fw = new java.io.FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("@relation spam\n\n");
            for (int i = 0; i < attribute.size(); i++) {
                bw.write("@attribute " + (String) attribute.get(i) + " {0,1}\n");
            }
            bw.write("@attribute class {spam,notspam}\n\n@data\n\n");
            for (int i = 0; i < spam.size(); i++) {
                for (int j = 0; j < attribute.size(); j++) {
                    if (((String)spam.get(i)).contains((String)attribute.get(j))) {
                        bw.write("1,");
                    } else {
                        bw.write("0,");
                    }
                }
                bw.write("spam\n");
            }
            for (int i = 0; i < notSpam.size(); i++) {
                for (int j = 0; j < attribute.size(); j++) {
                    if (((String)notSpam.get(i)).contains((String)attribute.get(j))) {
                        bw.write("1,");
                    } else {
                        bw.write("0,");
                    }
                }
                bw.write("notspam\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
