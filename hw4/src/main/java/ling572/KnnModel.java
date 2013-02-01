package ling572;

import java.util.*;

public class KnnModel {
	int kVal;
		
	public KnnModel(int kVal) {
		this.setKVal(kVal);
	}
	
	public void train(List<Instance> instances) {
		
	}
	
	public void test(DistanceMetricMethod method, List<Instance> instances) {
		//	loop through all instances and call getDistance.
	}

	public double getDistance(DistanceMetricMethod method, Instance lhs, Instance rhs) {
		return method.distance(lhs, rhs);
	}
	
	public int getKVal() {
		return this.kVal;
	}
	
	public void setKVal(int kVal) {
		this.kVal = kVal;
	}
}
