/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spam.filtering;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.DenseInstance;
import weka.classifiers.trees.SimpleCart;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.GreedyStepwise;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.AttributeSelectedClassifier;

/**
 *
 * @author TOSHIBA PC
 */
public class Cart {
    
    protected SimpleCart tree;
    protected Instances data;
    
    public Cart(String arffFile) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(arffFile));
            data = new Instances(reader);
            reader.close();
            data.setClassIndex(data.numAttributes() - 1);
            System.out.println(data.numAttributes());

            // build tree
            tree = new SimpleCart();    
            tree.buildClassifier(data);
	} catch (FileNotFoundException e) {
	} catch (Exception e) {
	}
    }
    
    public void printTree() {
        System.out.println(tree.toString());
    }
    
    public void classify(String text) throws Exception {
        Instance newInstance = new DenseInstance(data.numAttributes());
        newInstance.setDataset(data);
        for (int i = 0; i < data.numAttributes()-1; i++) {
            if (text.contains(data.attribute(i).name()))
                newInstance.setValue(i, 1);
            else
                newInstance.setValue(i, 0);
        }
        
        double[] result = tree.distributionForInstance(newInstance);
        System.out.println("Class probabilities:");
        System.out.println("Spam: " + result[0]);
        System.out.println("Not spam: " + result[1]);
    }
    
}
