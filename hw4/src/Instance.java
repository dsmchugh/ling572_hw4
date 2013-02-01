import java.io.*;
import java.util.*;

public class Instance {

	private String label;
	private Map<String,Integer> features;
	private int size;
	
	public Instance(String label) {
		this.label = label;
		this.features = new HashMap<String,Integer>();
		this.size = 0;
	}
	
	public void addFeature(String feature, int value) {
		this.features.put(feature, value);
		this.size += value;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public boolean hasFeature(String feature) {
		return this.features.containsKey(feature);
	}
	
	public Integer getFeatureValue(String feature) {
		return this.features.get(feature);
	}
	
	public Map<String,Integer> getFeatures() {
		return this.features;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public void removeFeature(String feature) {
		int featureCount = this.features.get(feature);
		this.size -= featureCount;
		this.features.remove(feature);
	}
	
	public static List<Instance> indexInstances(File dataFile) {
		List<Instance> instances = new ArrayList<Instance>();

		// line formatted as: label feature1:value1 feature2:value2 ..."
		try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] splitLine = line.split("\\s");
				
				String label = splitLine[0];
				Instance instance = new Instance(label);
				
				for (int i = 1; i < splitLine.length; i++) {
					String[] featureSplit = splitLine[i].split(":");
					String feature = featureSplit[0];
					int value = Integer.parseInt(featureSplit[1]);							
					instance.addFeature(feature, value);
				}
				
				instances.add(instance);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return instances;
	}
}