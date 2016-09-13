/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spam.filtering;

import java.util.ArrayList;
import IndonesianNLP.IndonesianSentenceFormalization; 

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
        System.out.println(spam.get(3));
        
        // Formalize every text
        IndonesianSentenceFormalization formalizer = new IndonesianSentenceFormalization();
        for (int i = 0; i < spam.size(); i++) {
            spam.set(i, formalizer.normalizeSentence(spam.get(i).toString()));
        }
        for (int i = 0; i < notSpam.size(); i++) {
            notSpam.set(i, formalizer.normalizeSentence(notSpam.get(i).toString()));
        }
        
        System.out.println("AFTER NORMALIZATION");
        System.out.println(spam.get(3));
    }
}
