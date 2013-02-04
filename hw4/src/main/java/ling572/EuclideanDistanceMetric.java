package ling572;

import java.util.*;

public class EuclideanDistanceMetric implements DistanceMetricMethod {

	@Override
	public double distance(Instance lhs, Instance rhs, Set<String> featureList) {
		double sum = 0.0;
		
		Set<String> lrUnion = new HashSet<String>();
		lrUnion.addAll(lhs.getFeatures().keySet());
		lrUnion.addAll(rhs.getFeatures().keySet());
		
		for (String feature : lrUnion) {
			if (!featureList.contains(feature))
				continue;
			
			int lhsVal = lhs.getFeatureValue(feature);
			int rhsVal = rhs.getFeatureValue(feature);
			
			sum += Math.pow((lhsVal - rhsVal), 2);
			
		}
		return Math.sqrt(sum);

	}

	@Override
	public Map<Instance, Double> nearest(Map<Instance, Double> distances, int kVal) {
		return KnnModel.getHighestValues(distances, kVal, false);
	}
}