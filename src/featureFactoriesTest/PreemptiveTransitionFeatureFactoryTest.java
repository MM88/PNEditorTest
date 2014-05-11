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

import FeatureFactories.PreemptiveTransitionFeatureFactory;

import pNeditor.PNeditorDocTemplate;
import pNeditor.PNeditorDocument;
import pNeditor.PNeditorPlugin;
import pNeditor.PNeditorView;
import petriNetDomain.*;


public class PreemptiveTransitionFeatureFactoryTest {

	private static PreemptiveTransitionFeatureFactory testObj;
	@BeforeClass
	public static void setUp() throws Exception {		
		PNeditorPlugin plugin = new PNeditorPlugin();
		plugin.initClipboard();
		PNeditorDocument doc = new PNeditorDocument();
		assertNotNull(doc);
		PNeditorDocTemplate dt = new PNeditorDocTemplate(plugin);
		doc.setDocTemplate(dt);
		PNeditorView view = new PNeditorView();
		view.setDocument(doc);
		view.initializeView(null, doc);
		
		testObj = new PreemptiveTransitionFeatureFactory();
	}

	@AfterClass
	public static void tearDown() throws Exception {
		System.gc();
	}

	@Test
	public void testGetName() {
	
		String actualName = "Preemptive Transition";
		assertEquals(testObj.getName(), actualName);
	}

//  problema perche passa da FApplication.getActivedocument
//	@Test
//	public void testCreateFeature() {
//		
//		IFeature f=testObj.createFeature();
//		assertNotNull(f);
//	}

	@Test
	public void testGetDependencies() {
		String [] actualDependencies = {"Timed Transition"};
		assertArrayEquals(testObj.getDependencies(), actualDependencies);
	}
//
	@Test
	public void testHasDependencies() {
		assertTrue(testObj.hasDependencies());
	}
//
	@Test
	public void testIsDependent() {
		String trueDependence = "Timed Transition";
		String falseDependence = "Stochastic Transition";
		boolean actual;
		actual = testObj.isDependent(trueDependence);
		assertTrue(actual);
		actual = testObj.isDependent(falseDependence);
		assertFalse(actual);		
	}
	
	@SuppressWarnings("null")
	@Test
	public void testIsAppliableToAll() {	
		
		Collection<PNelement> elements = new ArrayList<PNelement>();
		Point p;
		p = new Point(0,0);
		PNelement transition1 = new Transition("transition1", p);
		elements.add(transition1);
		p = new Point(5,5);
		PNelement transition2 = new Transition("transition2", p);
		elements.add(transition2);
		assertTrue(testObj.isAppliableToAll(elements));
		p= new Point(10,10);
		PNelement place1 = new Place("place1", p);
		elements.add(place1);
		assertFalse(testObj.isAppliableToAll(elements));
	
	}

}
