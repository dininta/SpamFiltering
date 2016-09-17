/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spam.filtering;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author TOSHIBA PC
 */
public class main {
    public static void main(String[] args) throws Exception {        
        FileReaderCSV reader = new FileReaderCSV();
        ArrayList spam = reader.readFile("traindata/spam.csv");
        ArrayList notSpam = reader.readFile("traindata/notSpam.csv");
        ArrayList<String> token = new ArrayList<String>();
        
        // Preprocess
        SpamFiltering spamFiltering = new SpamFiltering();
        spam = spamFiltering.preProcess(spam);
        notSpam = spamFiltering.preProcess(notSpam);
        token = spamFiltering.generateFeatures(spam, notSpam);
        
        reader.writeFile(spam, "PreprocessSpam.csv");
        reader.writeFile(notSpam, "PreprocessNotSpam.csv");
        reader.writeFile(token, "Token.csv");
        
        spamFiltering.writeToArff(token, spam, notSpam, "Spam.arff");
        
        // Build tree and classify text
        Cart cart = new Cart("Spam.arff");
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("Enter text: ");
            String text = input.nextLine();
            cart.classify(text);
        }
    }
}
