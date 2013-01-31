import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Document {

	private String label;
	private Map<String,Integer> features;
	private int size;
	
	public Document(String label) {
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
	
	public static List<Document> indexDocuments(File dataFile) {
		List<Document> documents = new ArrayList<Document>();

		// line formatted as: label feature1:value1 feature2:value2 ..."
		try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] splitLine = line.split("\\s");
				
				String label = splitLine[0];
				Document document = new Document(label);
				
				for (int i = 1; i < splitLine.length; i++) {
					String[] featureSplit = splitLine[i].split(":");
					String feature = featureSplit[0];
					int value = Integer.parseInt(featureSplit[1]);							
					document.addFeature(feature, value);
				}
				
				documents.add(document);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return documents;
	}
}
