package ling572;

import java.util.Set;

public class EuclideanDistanceMetric implements DistanceMetricMethod {

	@Override
	public double distance(Instance lhs, Instance rhs) {
		double sum = 0.0;
		
		Set<String> featureList = lhs.getFeatures().keySet();
		
		for (String feature : featureList) {
			int lhsVal = lhs.getFeatureValue(feature);
			int rhsVal = rhs.getFeatureValue(feature);
			
			sum += Math.pow((lhsVal - rhsVal), 2);
			
		}
		return Math.sqrt(sum);

	}

}
