package featureFactoriesTest;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import petriNetDomain.IFeature;
import petriNetDomain.PNelement;
import petriNetDomain.Place;
import petriNetDomain.Transition;

import FeatureFactories.StochasticTransitionFeatureFactory;

public class StochasticTransitionFeatureFactoryTest {

	private static StochasticTransitionFeatureFactory testObj;
	@Before
	public void setUp() throws Exception {
		testObj = new StochasticTransitionFeatureFactory();
	}

	@After
	public void tearDown() throws Exception {
		System.gc();
	}

	@Test
	public void testGetName() {
		String actualName = "Stochastic Transition";
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
		String trueDependence = "Timed Transition";
		String falseDependence = "Preemptive Transition";
		boolean actual;
		actual = testObj.isDependent(trueDependence);
		assertFalse(actual);
		actual = testObj.isDependent(falseDependence);
		assertFalse(actual);	
	}

//	@SuppressWarnings("null")
//	@Test
//	public void testIsAppliableToAll() {
//		Collection<PNelement> elements = new ArrayList<PNelement>();
//		Point p;
//		p = new Point(0,0);
//		PNelement transition1 = new Transition("transition1", p);
//		elements.add(transition1);
//		p = new Point(5,5);
//		PNelement transition2 = new Transition("transition2", p);
//		elements.add(transition2);
//		assertTrue(testObj.isAppliableToAll(elements));
//		p= new Point(10,10);
//		PNelement place1 = new Place("place1", p);
//		elements.add(place1);
////		assertFalse(testObj.isAppliableToAll(elements));
//	
//	}
}
