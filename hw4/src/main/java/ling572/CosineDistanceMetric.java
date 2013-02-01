package ling572;

import java.util.*;

public class CosineDistanceMetric implements DistanceMetricMethod {

	@Override
	public double distance(Instance lhs, Instance rhs) {
		int num = 0;
		int lhsDen = 0;
		int rhsDen = 0;
		
		Set<String> featureList = lhs.getFeatures().keySet();
		
		for (String feature : featureList) {
			int lhsVal = lhs.getFeatureValue(feature);
			int rhsVal = rhs.getFeatureValue(feature);
			
			num += lhsVal * rhsVal;
			lhsDen += lhsVal * lhsVal;
			rhsDen += rhsVal * rhsVal;
		}
		
		double distance = (double)num / (Math.sqrt(lhsDen) * Math.sqrt(rhsDen));
		
		return distance;
	}

}
