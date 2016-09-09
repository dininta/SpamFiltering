import weka.core.Instance;
import weka.core.Instances;
import weka.core.DenseInstance;
import weka.classifiers.trees.SimpleCart;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class SpamFiltering {

	public static void main(String[] args)
	{
		try {
			BufferedReader reader = new BufferedReader(new FileReader("test.arff"));
			Instances data = new Instances(reader);
			reader.close();
			data.setClassIndex(data.numAttributes() - 1);

			// build tree
			SimpleCart cart = new SimpleCart();
			cart.buildClassifier(data);
			System.out.println(cart.toString());

			// create an instance
            Instance newInstance = new DenseInstance(data.numAttributes());
            newInstance.setDataset(data);
            newInstance.setValue(0, "vhigh");
            newInstance.setValue(1, "vhigh");
            newInstance.setValue(2, "2");
            newInstance.setValue(3, "2");
            newInstance.setValue(4, "small");
            newInstance.setValue(5, "high");
			System.out.println("New Instance: " + newInstance.toString());

			// classify newInstance
			double[] result = cart.distributionForInstance(newInstance);
			System.out.println("Class probabilities: " + Arrays.toString(result));

		} catch (FileNotFoundException e) {
		} catch (Exception e) {
		}
	}

}