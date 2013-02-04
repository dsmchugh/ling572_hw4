package ling572;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class EuclideanDistanceMetricTest {
	
	static Instance testInstance;
	static Instance test2Instance;
	static Instance test3Instance;
	static Instance test4Instance;
	static Instance test5Instance;
	static Instance test6Instance;
	static Set<String> featureList;
	
	{
		testInstance = new Instance("test", 1);
		testInstance.addFeature("test1", 1);
		testInstance.addFeature("test2", 2);
		testInstance.addFeature("test3", 3);
		testInstance.addFeature("test4", 5);		
				
		test2Instance = new Instance("test2", 2);
		test2Instance.addFeature("test1", 20);
		test2Instance.addFeature("test2", 245);
		test2Instance.addFeature("test3", 39);
		test2Instance.addFeature("test4", 509);	
		
		test3Instance = new Instance("test3", 3);
		test3Instance.addFeature("test1", 20000);
		test3Instance.addFeature("test2", 2450000);
		test3Instance.addFeature("test3", 390000);
		test3Instance.addFeature("test4", 5090000);
		
		test4Instance = new Instance("test4", 4);
		test4Instance.addFeature("test1", 19);
		test4Instance.addFeature("test2", 243);
		test4Instance.addFeature("test3", 36);
		test4Instance.addFeature("test4", 504);
		
		test5Instance = new Instance("test5", 5);
		test5Instance.addFeature("test1", 12);
		test5Instance.addFeature("test2", 16);
		test5Instance.addFeature("test3", 17);
		
		test6Instance = new Instance("test6", 6);
		test6Instance.addFeature("test1",16);
		test6Instance.addFeature("test2", 76);
		test6Instance.addFeature("test3", 27);
		test6Instance.addFeature("test4", 9);
		test6Instance.addFeature("test5", 88);
		
		Set<String> featureList = new HashSet<String>();
		featureList.add("test1");
		featureList.add("test2");
		featureList.add("test3");
		featureList.add("test4");
		featureList.add("test5");			
	}
	
	static final double DEFAULT_TOLERANCE = 0.000001;
	
	@Test
	public void testIdentity() {
		DistanceMetricMethod dmm = new EuclideanDistanceMetric();
		assertEquals("Result", 0.0, dmm.distance(testInstance, testInstance, featureList), DEFAULT_TOLERANCE);
		
	}
	
	@Test
	public void testStandard() {
		DistanceMetricMethod dmm = new EuclideanDistanceMetric();
		assertEquals("Result", 561.000891, dmm.distance(testInstance, test2Instance, featureList), DEFAULT_TOLERANCE) ;
	}
	
	@Test
	public void testNonIsolengthVectors() {
		EuclideanDistanceMetric tester = new EuclideanDistanceMetric();
		// |lhs| > |rhs| = missing features are zeroes
		assertEquals("Result", 553.131991, tester.distance(test4Instance, test5Instance, featureList), DEFAULT_TOLERANCE);
		// |lhs| < |rhs| = missing features are ignored
		assertEquals("Result", 227.901294, tester.distance(test5Instance, test4Instance, featureList), DEFAULT_TOLERANCE);
	}

}
