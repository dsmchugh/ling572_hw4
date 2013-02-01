package ling572;
import java.io.*;

public class ModelOutput implements AutoCloseable {
	private static final String SEPARATOR = "\t";
	private BufferedWriter writer;
	
	public ModelOutput(String modelFileName) throws IOException {
		this.writer = new BufferedWriter(new FileWriter(modelFileName));
	}
	
	public void printPriorProbHeader() throws IOException {
		writer.write("%%%%% prior prob P(c) %%%%%"); 
		writer.newLine();
	}
	
	public void printPriorProb(String classLabel, double priorProbability) throws IOException {
		writer.write(classLabel);
		writer.write(SEPARATOR);
		writer.write(Double.toString(priorProbability));
		writer.write(SEPARATOR);
		writer.write(Double.toString(Math.log10(priorProbability))); 
		writer.newLine();
	}
	
	public void printCondProbHeader() throws IOException {
		writer.write("%%%%% conditional prob P(f|c) %%%%%");
		writer.newLine();
	}
	
	public void printCondProbPriorHeader(String classLabel) throws IOException{
		writer.write("%%%%% conditional prob P(f|c) c=");
		writer.write(classLabel);
		writer.write(" %%%%%");
		writer.newLine();
	}
	
	public void printCondProb(String featureName, String classLabel, double condProb) throws IOException {
		writer.write(featureName);
		writer.write(SEPARATOR);
		writer.write(classLabel);
		writer.write(SEPARATOR);
		writer.write(Double.toString(condProb));
		writer.write(SEPARATOR);
		writer.write(Double.toString(Math.log10(condProb)));
		writer.newLine();
	}

	@Override
	public void close() throws IOException {
		writer.close();
	}
}