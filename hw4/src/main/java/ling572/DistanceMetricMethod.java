package ling572;

import java.util.*;

public interface DistanceMetricMethod  {
	
	double distance(Instance lhs, Instance rhs);
	Map<Instance,Double> nearest(Map<Instance,Double> distances, int kVal);
}
