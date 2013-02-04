package ling572;

import java.io.*;
import java.util.*;

public class KnnDriver {
	private File trainingData;
	private File testData;
	private int kVal;
	private DistanceMetricMethod similarityFunc;
	private File sysOutput;
	private File featureList;
	
	/**
	 * training_data 
	 * test_data 
	 * k_val
	 * similarity_func
	 * sys_output
	 */
	public static void main(String[] args) {
		KnnDriver driver = new KnnDriver();
		driver.parseArgs(args);
		
		try {
			List<Instance> trainInstances = Instance.indexInstances(driver.trainingData);
			List<Instance> testInstances = Instance.indexInstances(driver.testData);
			
			KnnModel model = new KnnModel(driver.kVal);
			model.train(trainInstances);
			if (driver.featureList != null) model.filterByFeatureList(driver.featureList);
			model.test(driver.similarityFunc, testInstances, driver.sysOutput, "test");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private void parseArgs(String[] args) {

		if (args.length < 5 || args.length > 6)
			exit("Error: usage KnnDriver [training_data] [test_data] [k_val] [similarity_func] [sys_output] ([feature_list])");

		try {
			this.trainingData = new File(args[0]);
		} catch (Exception e){
			exit("Error: invalid training_data file");
		}
		
		try {
			this.testData= new File(args[1]);
		} catch (Exception e) {
			exit("Error: invalid test_data file");
		}
				
		try {
			this.kVal = Integer.parseInt(args[2]);
		} catch (Exception e) {
			exit("Error: invalid k_val integer");
		}
		
		try {
			int similarityFuncCode = Integer.parseInt(args[3]);
			switch (similarityFuncCode) {
				case 1:
					this.similarityFunc = new EuclideanDistanceMetric();
					break; 
				case 2:
					this.similarityFunc = new CosineDistanceMetric();
					break;
				default:
					throw new Exception();
			}
		} catch (Exception e) {
			exit("Error: invalid similarity_func value. 1 = Euclidean 2 = Cosine");
		}
		
		try {
			this.sysOutput = new File(args[4]);

			//	clear existing output data
			sysOutput.delete();
		} catch (Exception e) {
			exit("Error: invalid sys_output file");
		}
		
		if (args.length == 6) {
			try {
				this.featureList = new File(args[5]);
			} catch(Exception e) {
				exit("Error: invalid feature_list file");
			}
		}

	}

	private static void exit(String errorText) {
		System.out.println(errorText);
		System.exit(1);
	}

}