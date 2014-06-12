package T2;

import static org.junit.Assert.*;

import java.awt.Point;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.l2fprod.common.propertysheet.Property;


import pNeditor.IFeatureFactory;
import pNeditor.PNeditorDocTemplate;
import pNeditor.PNeditorDocument;
import pNeditor.PNeditorPlugin;
import pNeditor.PNeditorView;
import pNeditor.PropertiesDockBar;

import petriNetDomain.Place;
import petriNetDomain.StochasticTransitionFeature;
import petriNetDomain.TimedTransitionFeature;
import petriNetDomain.Transition;

/**
 * when one element is selected the properties dockbar must show all
 *  its general properties and features properties if present
 * @author Michaela  
 * 
 */
public class T2_1 {

	private static PropertiesDockBar pDock;
	private static PNeditorDocument doc;
	private static PNeditorPlugin plugin;
	private static ArrayList<String> expecteds;
	private static ArrayList<String> actuals;
	private Property[] properties;
	/**
	 * set up the main classes needed in order to create a document 
	 * and add elements to it and perform operations on them
	 *
	 * and instantiate an instance of the object to test
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		plugin = new PNeditorPlugin();
		plugin.initClipboard();
	    doc = new PNeditorDocument();
		PNeditorDocTemplate dt = new PNeditorDocTemplate(plugin);
		doc.setDocTemplate(dt);
		//serve per inizializzare la roba che serve per creare i posti
		PNeditorView view = new PNeditorView();
		view.setDocument(doc);
		view.initializeView(null, doc);
		
		pDock = new PropertiesDockBar();
		expecteds = new ArrayList<String>();
		actuals = new ArrayList<String>();
	}

	@Before
	public void before(){
		
	}
	
	@After
	public void after(){
		
		expecteds.clear();
		actuals.clear();	
		doc.getSelectionModel().clearSelection();	
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test1() {	
		
		//the element selected is a transition without features
		
		Transition t1 = new Transition("transition1", new Point(0,0));
		doc.addTransitionToPetriNet(t1, null);
		doc.getSelectionModel().select(t1, true);
		pDock.activate(doc);
		pDock.createSheet();
		
		expecteds.add("name");
		properties = pDock.getSheet().getProperties();
			
		for (int i=0; i<properties.length;i++){
			actuals.add(properties[i].getDisplayName());
		}
		assertEquals(expecteds.size(), actuals.size());
		assertTrue(actuals.containsAll(expecteds));		
		
	}
	
	@Test
	public void test2(){
		
		//the element selected is a transition with two features
		
		Transition t1 = new Transition("transition1", new Point(0,0));
		t1.addFeature(new TimedTransitionFeature(null));
		t1.addFeature(new StochasticTransitionFeature(null));
		doc.getSelectionModel().select(t1, true);
		expecteds.add("name");
		expecteds.add("Fdt");
		expecteds.add("EFT");
		expecteds.add("LFT");
		expecteds.add("EFT");
		expecteds.add("LFT");
		
		pDock.activate(doc);
		pDock.createSheet();
		properties = pDock.getSheet().getProperties();
		
		for (int i=0; i<properties.length;i++){
			actuals.add(properties[i].getDisplayName());
		}
		
		assertEquals(expecteds.size(), actuals.size());
		assertTrue(actuals.containsAll(expecteds));	
		
	}
	@Test
	public void test3(){
		
		//the element selected is a place
		
		Place p1 = new Place("place1", new Point(10,10));		
	    doc.addPlaceToPetriNet(p1, null);
	    doc.getSelectionModel().select(p1, true);
	    expecteds.add("name");
		expecteds.add("tokens");
		pDock.activate(doc);
		pDock.createSheet();
		
		properties = pDock.getSheet().getProperties();
		
		for (int i=0; i<properties.length;i++){
			actuals.add(properties[i].getDisplayName());			
		}
		
		assertEquals(expecteds.size(), actuals.size());
		assertTrue(actuals.containsAll(expecteds));			
	}

}
