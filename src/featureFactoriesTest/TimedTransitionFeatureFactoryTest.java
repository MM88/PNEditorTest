package featureFactoriesTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import petriNetDomain.IFeature;

import FeatureFactories.TimedTransitionFeatureFactory;

public class TimedTransitionFeatureFactoryTest {

	private static TimedTransitionFeatureFactory testObj;
	@Before
	public void setUp() throws Exception {
		testObj = new TimedTransitionFeatureFactory();
	}

	@After
	public void tearDown() throws Exception {
		System.gc();
	}

	@Test
	public void testGetName() {
		String actualName = "Timed Transition";
		assertEquals(testObj.getName(), actualName);
	}

	@Test
	public void testCreateFeature() {
		IFeature f=testObj.createFeature();
		assertNotNull(f);
	}	
	@Test
	public void testGetDependencies() {
		String [] actualDependencies = null;
		assertArrayEquals(testObj.getDependencies(), actualDependencies);
	}

	@Test
	public void testHasDependencies() {
		assertFalse(testObj.hasDependencies());
	}

	@Test
	public void testIsDependent() {
		String trueDependence = "Stochastic Transition";
		String falseDependence = "Preemptive Transition";
		boolean actual;
		actual = testObj.isDependent(trueDependence);
		assertFalse(actual);
		actual = testObj.isDependent(falseDependence);
		assertFalse(actual);
	}

//	@Test
//	public void testIsAppliableToAll() {
//		fail("Not yet implemented");
//	}

}
