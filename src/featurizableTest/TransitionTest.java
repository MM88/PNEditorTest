package featurizableTest;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import petriNetDomain.IFeature;
import petriNetDomain.PNelement;
import petriNetDomain.PreemptiveTransitionFeature;
import petriNetDomain.TimedTransitionFeature;
import petriNetDomain.Transition;

public class TransitionTest {

	private Transition testObj;
	@Before
	public void setUp() throws Exception {
		Point p = new Point(0,0);
		testObj = new Transition("transition1", p);
	}

	@After
	public void tearDown() throws Exception {
		System.gc();
	}

	@Test
	public void testAddGetFeature() {
		Set<IFeature> expecteds = new HashSet<IFeature>();
		IFeature f= new TimedTransitionFeature();
		expecteds.add(f);
		Set<IFeature> features = testObj.getFeatures();
		assertTrue(features.isEmpty());
		testObj.addFeature(f);
		Set<IFeature> actuals = testObj.getFeatures();
		assertEquals(expecteds, actuals);		
		IFeature actual = testObj.getFeature("Timed Transition");
		assertEquals(f, actual);
	}


	@Test
	public void testHasFeature() {
		IFeature f1= new TimedTransitionFeature();
		testObj.addFeature(f1);
		boolean actual;
		actual = testObj.hasFeature("Timed Transition");
		assertTrue(actual); 
		actual = testObj.hasFeature("Preemptive Transition");
		assertFalse(actual);
		
	}


	@Test
	public void testRemoveFeature() {
		IFeature f= new TimedTransitionFeature();
		Set<IFeature> features = testObj.getFeatures();
		assertTrue(features.isEmpty());
		testObj.addFeature(f);
		assertTrue(testObj.hasFeature(f.getName()));
		testObj.removeFeature("Timed Transition");
		assertFalse(testObj.hasFeature(f.getName()));
	}

	@Test
	public void testRemoveAllFeature() {
		IFeature f= new TimedTransitionFeature();
		testObj.addFeature(f);
		assertTrue(testObj.hasFeature(f.getName()));
		testObj.removeAllFeature();
		Set<IFeature> features = testObj.getFeatures();
		assertTrue(features.isEmpty());
	}

}
