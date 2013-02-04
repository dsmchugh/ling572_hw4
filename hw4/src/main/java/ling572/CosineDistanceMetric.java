package ling572;

import java.util.*;

public class CosineDistanceMetric implements DistanceMetricMethod {

	@Override
	public double distance(Instance lhs, Instance rhs, Set<String> featureList) {
		int num = 0;
		int lhsDen = 0;
		int rhsDen = 0;
		
		Set<String> lrUnion = new HashSet<String>();
		lrUnion.addAll(lhs.getFeatures().keySet());
		lrUnion.addAll(rhs.getFeatures().keySet());
		
		for (String feature : lrUnion) {
			if (!featureList.contains(feature))
				continue;
			
			int lhsVal = lhs.getFeatureValue(feature);
			int rhsVal = rhs.getFeatureValue(feature);
			
			num += lhsVal * rhsVal;
			lhsDen += lhsVal * lhsVal;
			rhsDen += rhsVal * rhsVal;
		}
		
		double distance = (double)num / (Math.sqrt(lhsDen) * Math.sqrt(rhsDen));
		
		if (Double.isNaN(distance))
			distance = 0;
		
		return distance;
	}

	@Override
	public Map<Instance, Double> nearest(Map<Instance, Double> distances, int kVal) {
		return KnnModel.getHighestValues(distances, kVal, true);
	}

}
