package featureFactoriesTest;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pNeditor.PNeditorDocTemplate;
import pNeditor.PNeditorDocument;
import pNeditor.PNeditorPlugin;
import pNeditor.PNeditorView;
import petriNetDomain.IFeature;
import petriNetDomain.PNelement;
import petriNetDomain.Place;
import petriNetDomain.Transition;

import FeatureFactories.PreemptiveTransitionFeatureFactory;
import FeatureFactories.StochasticTransitionFeatureFactory;
/**
 * This class tests the basic functioning of the class {@link StochasticTransitionFeatureFactory}
 * @author Michaela
 *
 */
public class StochasticTransitionFeatureFactoryTest {

	private static StochasticTransitionFeatureFactory testObj;
	private static PNeditorDocument doc;
	private static PNeditorPlugin plugin;
	/**
	 * set up the main classes needed in order to create a document 
	 * and add elements to it and perform operations on them
	 *
	 * and instantiate an instance of the object to test
	 */
	@BeforeClass
	public static void setUp() throws Exception {
		plugin = new PNeditorPlugin();
		plugin.initClipboard();
		doc = new PNeditorDocument();
		PNeditorDocTemplate dt = new PNeditorDocTemplate(plugin);
		doc.setDocTemplate(dt);
		PNeditorView view = new PNeditorView();
		view.setDocument(doc);
		view.initializeView(null, doc);
		
	
		testObj = new StochasticTransitionFeatureFactory();
	}

	@AfterClass
	public static void tearDown() throws Exception {
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
		
		String falseDependence;
		boolean actual;
		falseDependence = "Timed Transition";
		actual = testObj.isDependent(falseDependence);
		assertFalse(actual);
		falseDependence = "Preemptive Transition";				
		actual = testObj.isDependent(falseDependence);
		assertFalse(actual);
	}

	@Test
	public void testIsAppliableToAll() {
		
		Collection<PNelement> elements = new ArrayList<PNelement>();
		PNelement transition1 = new Transition("transition1", new Point(0,0));
		elements.add(transition1);
		PNelement transition2 = new Transition("transition2", new Point(10,10));
		elements.add(transition2);
		
		assertTrue(testObj.isAppliableToAll(elements));
		
		PNelement place1 = new Place("place1", new Point(20,20));
		elements.add(place1);
		
		assertFalse(testObj.isAppliableToAll(elements));
	}
}
