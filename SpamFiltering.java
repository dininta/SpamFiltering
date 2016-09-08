import weka.core.Instances;
import weka.classifiers.trees.SimpleCart;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class SpamFiltering {

	public static void main(String[] args)
	{
		try {
			BufferedReader reader = new BufferedReader(new FileReader("weather.nominal.arff"));
			Instances data = new Instances(reader);
			reader.close();
			data.setClassIndex(data.numAttributes() - 1);

			SimpleCart test = new SimpleCart();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}

}