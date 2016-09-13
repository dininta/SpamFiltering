/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spam.filtering;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 *
 * @author mochamadtry
 */
public class FileReaderCSV {
    
    public static void main(String[] args) {
        // TODO code application logic here
        String csvFile = "G:\\Kuliah\\7th Semester\\NLP\\Spam Filtering\\src\\spam\\filtering\\notSpam.csv";
        BufferedReader br = null;
        String line;
        ArrayList features = new ArrayList();

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String data = line;
                features.add(line);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //System.out.println(Arrays.toString(features));
        System.out.println(features.get(3));
        
    }
    
    
}
