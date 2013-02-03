package ling572;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class KnnModel {
	private int kVal;
	private List<Instance> trainInstances;
	private Set<String> featureList = new HashSet<String>();	
	
	public KnnModel(int kVal) {
		this.setKVal(kVal);
	}
	
	public void train(List<Instance> trainInstances) {
		this.trainInstances = trainInstances;
	}
	
	public void filterByFeatureList(File featureFile) {

			// line formatted as: feature chi_square doc_count
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(featureFile));
			
				String line;
				while ((line = reader.readLine()) != null) {
					String[] splitLine = line.split("\\s");
				
					String feature = splitLine[0];
					if (feature != null) {
						featureList.add(feature);
					}
				} 
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}	
			
			for (String feature : featureList) {
				for (Instance instance : trainInstances) {
					if (instance.hasFeature(feature)) instance.removeFeature(feature);	
				}
			}
	}
	
	public void test(DistanceMetricMethod method, List<Instance> testInstances, File sysOutputFile, String dataType) throws IOException {
		int i = 0;
		Map<String, Frequency<String>> confusionMatrix = new HashMap<String, Frequency<String>>();
		
		SysOutput output = new SysOutput(sysOutputFile);
		output.printHeader(dataType);
		
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
						
			Map<String,Double> classProbabilities = new HashMap<String,Double>();
			
			for (String classLabel : classes) {
				classProbabilities.put(classLabel, 0d);
			}
			
			double highProb = 0;
			String highProbLabel = "";
			
			for (Map.Entry<String,Integer> count : classCounts.getCounts().entrySet()) {
				String classLabel = count.getKey();
				int classCount = count.getValue();
				
				double probability = (double)classCount/(double)this.kVal;
				classProbabilities.put(classLabel,probability);
				
				if (probability >= highProb) {
					highProb = probability;
					highProbLabel = classLabel;
				}
			}
			
			output.printClassProbabilities(i, classProbabilities, testInstance.getLabel());			
			
			i++;
			
			Frequency<String> labelCounts = confusionMatrix.get(testInstance.getLabel());
			if (labelCounts == null)
				labelCounts = new Frequency<String>();
			
			labelCounts.count(highProbLabel);
			
			confusionMatrix.put(testInstance.getLabel(), labelCounts);
		}
		
		output.close();
		
		printConfusionMatrix(confusionMatrix, dataType);
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
	
	private static void printConfusionMatrix(Map<String, Frequency<String>> confusionMatrix, String testType) {
		int totalCount = 0;
		int totalCorrect = 0;
		
		Map<String,Frequency<String>> sorted = new TreeMap<String, Frequency<String>>(confusionMatrix);
		
		System.out.println("Confusion matrix for the "+ testType + " data:");
		System.out.println("row is the truth, column is the system output");
		System.out.println();
		
		System.out.print("\t\t\t");
		for (String label : sorted.keySet()) {
			System.out.print(label + " ");
		}
		
		System.out.println();
		
		
		
		for (String goldLabel : sorted.keySet()) {
			System.out.print(goldLabel + " ");
			Frequency<String> counts = confusionMatrix.get(goldLabel);
			
			for (String classLabel : sorted.keySet()) {
				Integer count = counts.getCounts().get(classLabel);
				
				if (count == null)
					count = 0;
				
				System.out.print(count + " ");
				totalCount += count;
				
				if (goldLabel.equals(classLabel))
					totalCorrect += count;
			}
			
			System.out.println();
		}
		
		System.out.println(" " + testType + " accuracy=" + (double)totalCorrect/(double)totalCount);
		System.out.println();
		System.out.println();
		System.out.println();
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
