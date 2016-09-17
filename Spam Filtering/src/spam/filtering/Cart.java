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
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.AttributeSelectedClassifier;

/**
 *
 * @author TOSHIBA PC
 */
public class Cart {
    
    protected SimpleCart tree;
    protected Instances dataTrain;
    protected Instances dataTest;
    
    public Cart(String fileTrain, String fileTest) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileTrain));
            dataTrain = new Instances(reader);
            reader.close();
            dataTrain.setClassIndex(dataTrain.numAttributes() - 1);
            
            reader = new BufferedReader(new FileReader(fileTest));
            dataTest = new Instances(reader);
            reader.close();
            dataTest.setClassIndex(dataTest.numAttributes() - 1);

            // build tree
            tree = new SimpleCart();    
            tree.buildClassifier(dataTrain);
	} catch (FileNotFoundException e) {
	} catch (Exception e) {
	}
    }
    
    public void printTree() {
        System.out.println(tree.toString());
    }
    
    public void classify() throws Exception {
        Classifier cls = new SimpleCart();
        cls.buildClassifier(dataTrain);
        Evaluation eval = new Evaluation(dataTrain);
        eval.evaluateModel(cls, dataTest);
        
        System.out.println(eval.toSummaryString("\nSummary\n======\n", false));   
        System.out.println(eval.toClassDetailsString("\nStatistic\n======\n"));
        System.out.println(eval.toMatrixString("\nConfusion Matrix\n======\n"));
    }
}
