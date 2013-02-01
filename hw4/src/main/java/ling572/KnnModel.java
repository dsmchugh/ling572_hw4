package ling572;

import java.util.*;

public class KnnModel {
	int kVal;
	
	public enum SimilarityFunction {
		Cosine, 
		Euclidian;
	}
	
	public KnnModel(int kVal) {
		this.setKVal(kVal);
	}
	
	public void train(List<Instance> instances) {
		
	}
	
	public void test(List<Instance> instances) {
		
	}
	
	public int getKVal() {
		return this.kVal;
	}
	
	public void setKVal(int kVal) {
		this.kVal = kVal;
	}
}
