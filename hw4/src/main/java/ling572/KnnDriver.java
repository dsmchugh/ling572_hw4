package ling572;

import java.io.*;
import ling572.KnnModel.*;

public class KnnDriver {
	private File trainingData;
	private File testData;
	private int kVal;
	private DistanceMetricMethod similarityFunc;
	private File sysOutput;
	
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
		
		KnnModel model = new KnnModel(driver.kVal);
	}
	
	private void parseArgs(String[] args) {

		if (args.length != 5)
			exit("Error: usage KnnDriver [training_data] [test_data] [k_val] [similarity_func] [sys_output]");

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
			exit("Error: invalid similarity_func value. 1 = Euclidian 2 = Cosine");
		}
		
		try {
			this.sysOutput = new File(args[4]);
		} catch (Exception e) {
			exit("Error: invalid sys_output file");
		}

	}
	
	private static void exit(String errorText) {
		System.out.println(errorText);
		System.exit(1);
	}

}
