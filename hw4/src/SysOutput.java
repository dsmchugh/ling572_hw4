import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class SysOutput implements AutoCloseable {
	private static final String SEPARATOR = " ";
	private BufferedWriter writer;
	
	public SysOutput(String sysOutputFileName) throws IOException {
		this.writer = new BufferedWriter(new FileWriter(sysOutputFileName));
	}
	
	public void printHeader(String dataType) throws IOException {
		writer.write("%%%%% ");
		writer.write(dataType);
		writer.write(" data:");
		writer.newLine();
	}
	
	public void printClassProbabilities(int index, Map<String,Double> classProbabilities) throws IOException {
		writer.write("array:");
		writer.write(Integer.toString(index));
		writer.write(SEPARATOR);
		
		sortMapByValueDesc(classProbabilities);
		
		for(Map.Entry<String,Double> entry: classProbabilities.entrySet()) {
			this.printClassProbability(entry.getKey(), entry.getValue());
		}
		writer.newLine();
	}
	
	public void newDataType() throws IOException {
		writer.newLine();
		writer.newLine();
		writer.newLine();
	}
	
	public void close() throws IOException {
		this.writer.close();
	}
	
	private void printClassProbability(String classLabel, double probability) throws IOException{
		writer.write(classLabel);
		writer.write(SEPARATOR);
		writer.write(Double.toString(probability));
		writer.write(SEPARATOR);
	}
	
	private static <T> void sortMapByValueDesc(Map<T,Double> map) {
		List<Entry<T,Double>> list = new LinkedList<Entry<T, Double>>(map.entrySet());
		
		Collections.sort(list, new Comparator<Entry<T,Double>>(){
			public int compare(Entry<T,Double> x, Entry<T,Double> y) {
				int c;
				//	return negative (to reverse)
				c = -(x.getValue()).compareTo(y.getValue());
				
				if (c==0)
					c=(x.getKey().toString()).compareTo(y.getKey().toString());
				
				return c;
			}
		});
			
		map = new LinkedHashMap<T,Double>();
			
		int j=0;
		
		for(Iterator<Entry<T, Double>> i = list.iterator(); i.hasNext();) {			
			Entry<T, Double> entry = i.next();
			map.put(entry.getKey(), entry.getValue());
			j++;
		}
	}
}
