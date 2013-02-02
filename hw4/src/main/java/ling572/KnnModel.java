package ling572;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class KnnModel {
	private int kVal;
	private List<Instance> trainInstances;
		
	
	public KnnModel(int kVal) {
		this.setKVal(kVal);
	}
	
	public void train(List<Instance> trainInstances) {
		this.trainInstances = trainInstances;
	}
	
	public void test(DistanceMetricMethod method, List<Instance> testInstances, File sysOutputFile) throws IOException {
		int i = 0;
		int correctCount = 0;
		SysOutput output = new SysOutput(sysOutputFile);
		
		for (Instance testInstance : testInstances) {
			Map<Instance,Double> distances = new HashMap<Instance,Double>();
			Set<String> classes = new HashSet<String>();
			
			for (Instance trainInstance : this.trainInstances) {
				double distance = this.getDistance(method, trainInstance, testInstance);	
				distances.put(trainInstance, distance);
				classes.add(trainInstance.getLabel());
			}
			
			Map<Instance,Double> neighbors = this.getNearest(method, distances);
			
			Frequency<String> classCounts = new Frequency<String>();
			
			for (Map.Entry<Instance,Double> neighbor : neighbors.entrySet()) {
				classCounts.count(neighbor.getKey().getLabel());
			}
			
			Map<String,Integer> sortedCounts = classCounts.getCountsSortedByValueDesc();
			
			Map<String,Double> classProbabilities = new HashMap<String,Double>();
			
			for (String classLabel : classes) {
				classProbabilities.put(classLabel, 0d);
			}
			
			double highProb = 0;
			boolean isCorrect = false;
			
			for (Map.Entry<String,Integer> count : classCounts.getCounts().entrySet()) {
				String classLabel = count.getKey();
				int classCount = count.getValue();
				
				double probability = (double)classCount/(double)this.kVal;
				classProbabilities.put(classLabel,probability);
				
				if (probability >= highProb) {
					highProb = probability;
					isCorrect = classLabel.equals(testInstance.getLabel()) ? true : false;
				}
			}
			
			output.printClassProbabilities(i, classProbabilities, testInstance.getLabel());			
			
			i++;
			if (isCorrect)
				correctCount++;
			
		}
		
		output.close();
		
		//	temporary accuracy output
		System.out.println("Accuracy: " + (double)correctCount/(double)i+1);
	}

	private double getDistance(DistanceMetricMethod method, Instance lhs, Instance rhs) {
		return method.distance(lhs, rhs);
	}
	
	private Map<Instance,Double> getNearest(DistanceMetricMethod method, Map<Instance,Double> distances) {
		return method.nearest(distances, this.kVal);
	}
	
	public int getKVal() {
		return this.kVal;
	}
	
	public void setKVal(int kVal) {
		this.kVal = kVal;
	}
	
	public static <T> Map<T,Double> getHighestValues(Map<T,Double> distances, int n, final boolean descending) {
		List<Entry<T,Double>> list = new LinkedList<Entry<T,Double>>(distances.entrySet());
		
		Collections.sort(list, new Comparator<Entry<T,Double>>(){
			public int compare(Entry<T,Double> x, Entry<T,Double> y) {
				int c;
				c = (x.getValue()).compareTo(y.getValue());
				
				if (descending)
					c = -c;
				
				if (c==0)
					c=(x.getKey().toString()).compareTo(y.getKey().toString());
				
				return c;
			}
		});
			
		Map<T,Double> sortedTypes = new LinkedHashMap<T,Double>();
				
		int j=0;
		
		for(Iterator<Entry<T,Double>> i = list.iterator(); i.hasNext();) {		
			if (j >= n) 
				break;
			
			Entry<T,Double> entry = i.next();
			sortedTypes.put(entry.getKey(), entry.getValue());
			j++;
		}
		
		return sortedTypes;
	}
}
