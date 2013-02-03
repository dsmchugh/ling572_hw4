package ling572;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ChiSquareModel {
	
	private Map<String,Frequency<String>> classFeatureCounters = new HashMap<String,Frequency<String>>();
	private Frequency<String> totalFeatureCounter = new Frequency<String>();
	private Frequency<String> classCounter = new Frequency<String>();
	
	private List<Instance> trainInstances;
	
	private Frequency<String> getFeatureCounterForClass(String classLabel) {
		Frequency<String> classFeatureCounter = this.classFeatureCounters.get(classLabel);
		if (classFeatureCounter == null) {
			classFeatureCounter = new Frequency<String>();
			this.classFeatureCounters.put(classLabel, classFeatureCounter);
		}
		return classFeatureCounter;
	}

 	
	public void train(List<Instance> trainInstances) {
		this.trainInstances = trainInstances;
		for (Instance inst : this.trainInstances) {
			classCounter.count(inst.getLabel());
			Frequency<String> classFeatureCounter = getFeatureCounterForClass(inst.getLabel());
			for (String featureLabel : inst.getFeatures().keySet()) {
				classFeatureCounter.count(featureLabel);
				totalFeatureCounter.count(featureLabel);
			}
		}
	}

	public void computeChiSquareValues() {
		
		LinkedHashMap<String,Double> chiSquareScores = new LinkedHashMap<String,Double>();
		
		// quick reference to classes in fixed order (if Java let us do an array of generics this would be easier)
		String[] classLabels = new String[classCounter.getCounts().keySet().size()];
		int index = 0;
		for (String s : classCounter.getCounts().keySet()) {  classLabels[index++] = s; }		
		int nClasses = classLabels.length;
		
		for (String feature : totalFeatureCounter.getCounts().keySet()) {
			int counts[][] = new int[2][nClasses];
			int n_feature = totalFeatureCounter.get(feature);
			for (int c=0; c < nClasses; c++) {
				int cn_feature = (classFeatureCounters.get(classLabels[c]).get(feature));
				counts[1][c] = cn_feature;
				counts[0][c] = n_feature - cn_feature;
				
			}
			double chi_square = computeChiSquare(counts); 
			chiSquareScores.put(feature, chi_square);
		}
		List<Entry<String,Double>> scoreList = new LinkedList<Entry<String, Double>>(chiSquareScores.entrySet());
		Collections.sort(scoreList, Collections.reverseOrder(new Comparator<Entry<String,Double>>(){ 
			public int compare(Entry<String,Double> x, Entry<String,Double> y) {
			return x.getValue().compareTo(y.getValue());
		}
		}));
		
		for (Entry<String,Double> score : scoreList) {
			System.out.println(score.getKey() + "\t" + score.getValue() + "\t" + totalFeatureCounter.get(score.getKey()));
		}

		
	}


	private double computeChiSquare(int[][] counts) {
		int n_rows = counts.length;
		int n_cols = counts[0].length;
		
		// sums
        double[] row_sum = new double[n_rows];
        double[] col_sum = new double[n_cols];
        double total = 0.0;
        for (int i = 0; i < n_rows; i++) {
            for (int j = 0; j < n_cols; j++) {
                row_sum[i] += counts[i][j];
                col_sum[j] += counts[i][j];
                total += counts[i][j];
            }
        }

        // computation
        double sum_of_squares = 0.0;
        double expected = 0.0;
        for (int i = 0; i < n_rows; i++) {
            for (int j = 0; j < n_cols; j++) {
                expected = (row_sum[i] * col_sum[j]) / total;
                double d = (counts[i][j] - expected);
                sum_of_squares += (d * d) / expected;
            }
        }
        return sum_of_squares;
	}

}
