/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spam.filtering;

import IndonesianNLP.IndonesianSentenceFormalization;
import IndonesianNLP.IndonesianSentenceTokenizer;
import IndonesianNLP.IndonesianStemmer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.Instances;

/**
 *
 * @author zulva dan icha
 */
public class SpamFiltering {

    protected IndonesianSentenceFormalization formalizer;
    protected IndonesianStemmer stemmer;
    protected IndonesianSentenceTokenizer tokenizer;
    
    public SpamFiltering() {
        formalizer = new IndonesianSentenceFormalization();
        stemmer = new IndonesianStemmer();
        tokenizer = new IndonesianSentenceTokenizer();
        
        formalizer.initStopword();
    }
    
    public ArrayList preProcess(ArrayList listOfText) {
        ArrayList processed = new ArrayList();
        for (int i = 0; i < listOfText.size(); i++) {
            // Normalize Sentences
            String text = formalizer.normalizeSentence(listOfText.get(i).toString());
            
//Replace All Non Alphabetic
            text = prosesNonAlfabet (text.toLowerCase());
                  
            //Tokenizer
            ArrayList<String> words = tokenizer.tokenizeSentence(text);
             
            // Stemming
            StringBuilder result = new StringBuilder();
            //String[] words = text.split(" ");
            for (String word : words) {
                if (word.length() > 1) {
                    word = stemmer.stem(word);
                    word = formalizer.formalizeWord(word);
                    result.append(word + " ");
                }
            }
            
            //Stop Word
            String finalize = formalizer.deleteStopword(result.toString()); 
            processed.add(finalize);
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
    
    public ArrayList<String> generateFeatures(ArrayList<String> spam, ArrayList<String> notSpam) throws FileNotFoundException, IOException, Exception{
        Map<String,Integer[]> counter = new HashMap<>();
        ArrayList<String> result = new ArrayList<>();
        
        for(int i=0; i<spam.size(); i++) {
            ArrayList<String> token = tokenizer.tokenizeSentence(spam.get(i));           
            
            for(int j=0; j< token.size(); j++) {
                if(!counter.containsKey(token.get(j))) {
                    Integer[] temp = {1,0}; 
                    counter.put(token.get(j),temp);
                } else {
                    Integer[] temp = counter.get(token.get(j));
                    temp[0]++;
                    counter.put(token.get(j), temp);
                }
            }
        }
        
        for(int i=0; i<notSpam.size(); i++) {
            ArrayList<String> token = tokenizer.tokenizeSentence(notSpam.get(i));
            
            for(int j=0; j< token.size(); j++) {
                if(!counter.containsKey(token.get(j))) {
                    Integer[] temp = {0,1}; 
                    counter.put(token.get(j),temp);
                } else {
                    Integer[] temp = counter.get(token.get(j));
                    temp[1]++;
                    counter.put(token.get(j), temp);
                }
            }
        }
        
        result.addAll(counter.keySet());        
        
//        for (int i=0; i< 20; i++){
//            String key = result.get(i);
//            System.out.println(key + " " + counter.get(key)[0] + " " + counter.get(key)[1]);
//        }
        
        return result;
    }
    
    public ArrayList<String> featureSelection(String arffFile) throws FileNotFoundException, IOException, Exception {
        
        BufferedReader reader = new BufferedReader(new FileReader(arffFile));
        Instances data = new Instances(reader);
        reader.close();
        
        data.setClassIndex(data.numAttributes() - 1);
        AttributeSelection selector = new AttributeSelection();
        InfoGainAttributeEval evaluator = new InfoGainAttributeEval();
        Ranker ranker = new Ranker();
        ranker.setNumToSelect(Math.min(1000, data.numAttributes() - 1));
        selector.setEvaluator(evaluator);
        selector.setSearch(ranker);
        selector.SelectAttributes(data);
        
        int selectedAttr[] = selector.selectedAttributes();
        ArrayList<String> result = new ArrayList<>();
        for (int i=0; i<selectedAttr.length-1; i++) {
            result.add(data.attribute(selectedAttr[i]).name());
        }
        
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
