package T0_2;

import static org.junit.Assert.*;

import java.awt.Point;

import java.util.ArrayList;
import java.util.Collection;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import static org.mockito.Mockito.*;

import framework.FApplication;
import framework.FDocument;
import framework.exception.ApplicationException;

import FeatureFactories.PreemptiveTransitionFeatureFactory;

import pNeditor.ElementListDockBar;
import pNeditor.PNeditorDocTemplate;
import pNeditor.PNeditorDocument;
import pNeditor.PNeditorPlugin;
import pNeditor.PNeditorView;
import pNeditor.PropertiesDockBar;
import petriNetDomain.*;
import pnEditorApp.PNeditorApplication;

/**
 * This class tests the basic functioning of the class {@link PreemptiveTransitionFeatureFactory}
 * @author Michaela
 *
 */
public class PreemptiveTransitionFeatureFactoryTest {

	private static PreemptiveTransitionFeatureFactory testObj;
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
		
		testObj = new PreemptiveTransitionFeatureFactory();
	}

	@AfterClass
	public static void tearDown() throws Exception {
	}
	
	@Test
	public void testGetName() {
	
		String actualName = "Preemptive Transition";
		assertEquals(testObj.getName(), actualName);
	}


//	@Test
//	public void testCreateFeature() throws ApplicationException {
//	
//		PNeditorApplication app = mock(PNeditorApplication.class);
//		when(app.getActiveDocument()).thenReturn(doc);
//		
//		Transition t1= new Transition("transition1", new Point(0,0));
//		doc.addTransitionToPetriNet(t1, null);
//		doc.getSelectionModel().select(t1, true);
//		
//		t1.addFeature(new PreemptiveTransitionFeature(app));
//		
//		assertNotNull(t1.getFeature("Preemptive Transition"));
//	}


	@Test
	public void testGetDependencies() {
		String [] actualDependencies = {"Timed Transition"};
		assertArrayEquals(testObj.getDependencies(), actualDependencies);
	}

	@Test
	public void testHasDependencies() {
		assertTrue(testObj.hasDependencies());
	}
	
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
