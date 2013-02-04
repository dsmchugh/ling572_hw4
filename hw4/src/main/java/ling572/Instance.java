package ling572;
import java.io.*;
import java.util.*;

public class Instance {

	private String label;
	private Map<String,Integer> features;
	private int size;
	private int id;
	
	public Instance(String label, Map<String,Integer> buildFrom) {
		this.features = new HashMap<String,Integer>();
		for (String key : buildFrom.keySet()) {
			this.features.put(key, buildFrom.get(key));
		}
		this.size = 0;
	}
	
	public Instance(String label, int id) {
		this.label = label;
		this.features = new HashMap<String,Integer>();
		this.size = 0;
		this.id = id;
	}
	
	public void addFeature(String feature, int value) {
		this.features.put(feature, value);
		this.size += value;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public boolean hasFeature(String feature) {
		return this.features.containsKey(feature);
	}
	
	public int getFeatureValue(String feature) {
		Integer value = this.features.get(feature);		
		return value == null ? 0 : value;
	}
	
	public Map<String,Integer> getFeatures() {
		return this.features;
	}
	
	public int getTotalFeatureCount() {
		return this.size;
	}
	
	public int getVocabularySize() {
		return this.features.size();
	}
	
	public void removeFeature(String feature) {
		int featureCount = this.features.get(feature);
		this.size -= featureCount;
		this.features.remove(feature);
	}
	
	public static List<Instance> indexInstances(InputStream inputStream) throws IOException {
		List<Instance> instances = new ArrayList<Instance>();

		int j = 0;
		
		// line formatted as: label feature1:value1 feature2:value2 ..."
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String line;
		while ((line = reader.readLine()) != null) {
			String[] splitLine = line.split("\\s");
			
			String label = splitLine[0];
			Instance instance = new Instance(label, j);
			
			for (int i = 1; i < splitLine.length; i++) {
				String[] featureSplit = splitLine[i].split(":");
				String feature = featureSplit[0];
				
				Integer value = null;
				
				try {
					value = Integer.parseInt(featureSplit[1]);
				} catch (NumberFormatException e) {
					System.out.println("Error: cannot convert " + splitLine[i] + " value to integer.");
					continue;
				} catch (ArrayIndexOutOfBoundsException e) {
					//	ignore. extra space in file
					continue;
				}
				
				instance.addFeature(feature, value);
			}
			
			instances.add(instance);
			j++;
		}
	
		reader.close();
		
		return instances;
		
	}
	
	public String toString() {
		return Integer.toString(this.getId());
	}
	
	public static List<Instance> indexInstances(File dataFile) throws IOException {
		InputStream inputStream = new FileInputStream(dataFile);
		
		return indexInstances(inputStream);
	}
}