package ling572;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;

public class CosineDistanceMetricTest {

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
		
	@Test
	public void testDistance() {
		CosineDistanceMetric tester = new CosineDistanceMetric();
		assertEquals("Result", 1, tester.distance(testInstance, testInstance, featureList), .00001);
		assertEquals("Result", 0.896458305, tester.distance(testInstance, test2Instance, featureList), .0000000001);
		assertNotSame("Result", 1, tester.distance(testInstance, test2Instance, featureList));
		assertTrue(tester.distance(testInstance,test2Instance, featureList) < tester.distance(testInstance, test3Instance, featureList));
	}
	
	@Test
	public void testNonIsolengthVectors() {
		CosineDistanceMetric tester = new CosineDistanceMetric();
		// |lhs| > |rhs|
		assertEquals("Result", 0.321073, tester.distance(test4Instance, test5Instance, featureList), .000001);
		// |lhs| < |rhs|
		assertEquals("Result", 0.731058, tester.distance(test5Instance, test4Instance, featureList), .000001);
	}
	
}