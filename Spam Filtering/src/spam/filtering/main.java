/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spam.filtering;

import java.util.ArrayList;
import IndonesianNLP.IndonesianSentenceFormalization;
import IndonesianNLP.IndonesianStemmer;

/**
 *
 * @author TOSHIBA PC
 */
public class main {
    public static void main(String[] args) {        
        FileReaderCSV reader = new FileReaderCSV();
        ArrayList spam = reader.readFile("traindata/spam.csv");
        ArrayList notSpam = reader.readFile("traindata/notSpam.csv");

        System.out.println("BEFORE");
        
        System.out.println(spam.get(7));
        
        // Preprocess
        IndonesianSentenceFormalization formalizer = new IndonesianSentenceFormalization();
        IndonesianStemmer stemmer = new IndonesianStemmer();
        
        for (int i = 0; i < spam.size(); i++) {
            // Formalization
            String text = formalizer.normalizeSentence(spam.get(i).toString());
            
            //Stop Word
            formalizer.initStopword();
            text = formalizer.deleteStopword(text);
            
            // Stemming
            StringBuilder result = new StringBuilder();
            String[] words = text.split(" ");
            for (String word : words) {
                if (word.length() > 1) result.append(stemmer.stem(word + " "));
            }
            
            spam.set(i, result.toString());
        }
        for (int i = 0; i < notSpam.size(); i++) {
            // Formalization
            String text = formalizer.normalizeSentence(notSpam.get(i).toString());
            
            //Stop Word
            formalizer.initStopword();
            text = formalizer.deleteStopword(text);
            
            // Stemming
            StringBuilder result = new StringBuilder();
            String[] words = text.split(" ");
            for (String word : words) {
                if (word.length() > 1) result.append(stemmer.stem(word + " "));
            }
            
            notSpam.set(i, result.toString());
        }
        
        System.out.println("AFTER");
        System.out.println(spam.get(7));
        
        reader.writeFile(spam, "PreprocessSpam.csv");
        reader.writeFile(notSpam, "PreprocessNotSpam.csv");
    }
}
