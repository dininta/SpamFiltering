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
        ArrayList spam = reader.readFile("C:\\Users\\TOSHIBA PC\\Documents\\GitHub\\SpamFiltering\\Spam Filtering\\src\\spam\\filtering\\spam.csv");
        ArrayList notSpam = reader.readFile("C:\\Users\\TOSHIBA PC\\Documents\\GitHub\\SpamFiltering\\Spam Filtering\\src\\spam\\filtering\\notSpam.csv");

        System.out.println("BEFORE");
        System.out.println(spam.get(5));
        
        // Preprocess
        IndonesianSentenceFormalization formalizer = new IndonesianSentenceFormalization();
        IndonesianStemmer stemmer = new IndonesianStemmer();
        
        for (int i = 0; i < spam.size(); i++) {
            // Formalization
            String text = formalizer.normalizeSentence(spam.get(i).toString());
            
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
            
            // Stemming
            StringBuilder result = new StringBuilder();
            String[] words = text.split(" ");
            for (String word : words) {
                if (word.length() > 1) result.append(stemmer.stem(word + " "));
            }
            
            notSpam.set(i, result.toString());
        }
        
        System.out.println("AFTER");
        System.out.println(spam.get(5));
    }
}
