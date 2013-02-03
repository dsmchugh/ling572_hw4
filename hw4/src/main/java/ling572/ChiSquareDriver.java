package ling572;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ChiSquareDriver {

	private File trainingData;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ChiSquareDriver driver = new ChiSquareDriver();
		driver.parseArgs(args);
		
		
		try {
			List<Instance> trainInstances = Instance.indexInstances(driver.trainingData);
			
			ChiSquareModel model = new ChiSquareModel();
			model.train(trainInstances);
			model.computeChiSquareValues();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private void parseArgs(String[] args) {

		if (args.length != 1)
			exit("Error: usage ChiSquareDriver [training_data]");

		try {
			this.trainingData = new File(args[0]);
		} catch (Exception e){
			exit("Error: invalid training_data file");
		}
		
	}
	
	private static void exit(String errorText) {
		System.out.println(errorText);
		System.exit(1);
	}

}
