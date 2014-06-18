package T0_3;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pNeditor.FeaturesDockBar;
import petriNetDomain.IFeature;
import petriNetDomain.PNelement;
import petriNetDomain.PreemptiveTransitionFeature;
import petriNetDomain.StochasticTransitionFeature;
import petriNetDomain.TimedTransitionFeature;
import petriNetDomain.Transition;
/**
 * This class tests the functioning of the class {@link Transition} as an implementation of {@link IFeaturizable}
 * @author Michaela
 *
 */
public class TransitionTest {

	private Transition testObj;
	
	@Before
	public void setUp() throws Exception {
		Point p = new Point(0,0);
		testObj = new Transition("transition1", p);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddGetFeature() {
		
		Set<IFeature> expecteds = new HashSet<IFeature>();
		IFeature f= new TimedTransitionFeature(null);
		expecteds.add(f);
		
		Set<IFeature> features = testObj.getFeatures();
		assertTrue(features.isEmpty());
		
		testObj.addFeature(f);
		
		Set<IFeature> actuals = testObj.getFeatures();
		assertEquals(expecteds.size(), actuals.size());
		assertTrue(actuals.containsAll(expecteds));
		
		IFeature actual = testObj.getFeature("Timed Transition");
		assertEquals(f, actual);
	}


	@Test
	public void testHasFeature() {
		
		IFeature f= new TimedTransitionFeature(null);
		testObj.addFeature(f);
		boolean actual;
		
		actual = testObj.hasFeature("Timed Transition");
		assertTrue(actual); 
		
		actual = testObj.hasFeature("Preemptive Transition");
		assertFalse(actual);
		
	}


	@Test
	public void testRemoveFeature() {
		
		IFeature f= new TimedTransitionFeature(null);
		Set<IFeature> features = testObj.getFeatures();
		assertTrue(features.isEmpty());
		
		testObj.addFeature(f);
		assertTrue(testObj.hasFeature(f.getName()));
		
		testObj.removeFeature("Timed Transition");
		assertFalse(testObj.hasFeature(f.getName()));
	}

	@Test
	public void testRemoveAllFeature() {
		
		IFeature tf= new TimedTransitionFeature(null);
		IFeature sf= new StochasticTransitionFeature(null);
		testObj.addFeature(tf);
		testObj.addFeature(sf);
		
		assertTrue(testObj.hasFeature(tf.getName()));
		assertTrue(testObj.hasFeature(sf.getName()));
		
		testObj.removeAllFeature();
		Set<IFeature> features = testObj.getFeatures();
		assertTrue(features.isEmpty());
	}

}
