/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spam.filtering;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
/**
 *
 * @author mochamadtry
 */
public class FileReaderCSV {

    public ArrayList readFile(String csvFile) {    
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
        
        return features;
    }
    
    public void writeFile(ArrayList content, String filename){
        try {
                File file = new File(filename);

                // if file doesnt exists, then create it
                if (!file.exists()) {
                        file.createNewFile();
                }

                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                for(int i=0; i<content.size(); i++){
                    bw.write((String) content.get(i));
                    bw.write("\n");
                }

                bw.close();

        } catch (IOException e) {
                e.printStackTrace();
        }
    }
            
}
