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
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 *
 * @author TOSHIBA PC
 */
public class Cart {
    
    protected SimpleCart tree;
    
    public Cart(String arffFile) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(arffFile));
            Instances data = new Instances(reader);
            reader.close();
            data.setClassIndex(data.numAttributes() - 1);

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
    
}
