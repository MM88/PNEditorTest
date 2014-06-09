package dockBarsTest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.l2fprod.common.propertysheet.Property;

import framework.tool.viewer.IContentProvider;

import pNeditor.ElementListDockBar;
import pNeditor.FeaturesDockBar;
import pNeditor.IFeatureFactory;
import pNeditor.PNeditorDocTemplate;
import pNeditor.PNeditorDocument;
import pNeditor.PNeditorPlugin;
import pNeditor.PNeditorView;
import pNeditor.PropertiesDockBar;
import petriNetDomain.FeaturePropertyAdapter;
import petriNetDomain.IFeature;
import petriNetDomain.IFeatureProperty;
import petriNetDomain.PreemptiveTransitionFeature;
import petriNetDomain.StochasticTransitionFeature;
import petriNetDomain.TimedTransitionFeature;
import petriNetDomain.Transition;
import pnEditorApp.PNeditorApplication;

/**
 * This class tests the basic functioning of the class {@link PropertiesDockBar}
 * @author Michaela
 *
 */
public class PropertiesDockBarTest {

	private static PropertiesDockBar testObj = null;
	private static PNeditorDocument doc;
	private static PNeditorPlugin plugin;
	
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
		
		testObj = new PropertiesDockBar();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * the basic functioning being tested is whether the properties list contains the general
	 * property and the features properties if present 
	 * 
	 * then its done a stress test based on the number of element in the net
	 */
	@Test
	public void test() {
		
		ArrayList<String> expecteds = new ArrayList<String>();
		ArrayList<String> actuals = new ArrayList<String>();
		Property[] properties;
		
		Transition t1 = new Transition("transition1", new Point(0,0));
		
		doc.addTransitionToPetriNet(t1, null);
		doc.getSelectionModel().select(t1,true);
		
		testObj.activate(doc);
		testObj.createSheet();
		
		properties = testObj.getSheet().getProperties();
		
		expecteds.add("name");
		for (int i=0; i<properties.length;i++){
			actuals.add(properties[i].getDisplayName());
		}
		assertEquals(expecteds.size(), actuals.size());
		assertTrue(actuals.containsAll(expecteds));	
		doc.getSelectionModel().clearSelection();
		
		//with a feature
		t1.addFeature(new TimedTransitionFeature(null));
		t1.addFeature(new StochasticTransitionFeature());

		doc.getSelectionModel().select(t1,true);
		testObj.activate(doc);
		testObj.createSheet();	
		
		for (IFeature f: t1.getFeatures()){
			for (IFeatureProperty fp: f.getProperties() ){
				expecteds.add(fp.getDisplayName());
			}
		}	
		
	    properties = testObj.getSheet().getProperties();
		actuals.clear();
		for (int i=0; i<properties.length;i++){
			actuals.add(properties[i].getDisplayName());
		}
		assertEquals(expecteds.size(), actuals.size());
		assertTrue(actuals.containsAll(expecteds));		
		doc.getSelectionModel().clearSelection();
		
		//with a preemptive feature
		
		PNeditorApplication app = mock(PNeditorApplication.class);
		when(app.getActiveDocument()).thenReturn(doc);	
		expecteds.clear();
		actuals.clear();
		doc.addResource("cpu");
		Transition t2 = new Transition("transition2", new Point(10,10));
		t2.addFeature(new PreemptiveTransitionFeature(app));
		expecteds.add("name");
		for (IFeature f: t2.getFeatures()){
			for (IFeatureProperty fp: f.getProperties() ){
				expecteds.add(fp.getDisplayName());
			}
		}	

		doc.getSelectionModel().select(t2,true);
		testObj.activate(doc);
		testObj.createSheet();	
		
		 properties = testObj.getSheet().getProperties();
			
		for (int i=0; i<properties.length;i++){
			actuals.add(properties[i].getDisplayName());
		}
		assertEquals(expecteds.size(), actuals.size());
		assertTrue(actuals.containsAll(expecteds));		
		doc.getSelectionModel().clearSelection();
		
		//stress test
		
		expecteds.clear();
		actuals.clear();
		for (int i=0; i<150; i++){
			Transition t = new Transition("t"+i, new Point (i,i));
			t.addFeature(new TimedTransitionFeature(null));
			expecteds.add("name");
			doc.addTransitionToPetriNet(t, null);		
			doc.getSelectionModel().select(t, true);
		}
		expecteds.add("EFT");
		expecteds.add("LFT");
		testObj.activate(doc);
		testObj.createSheet();
		
		properties = testObj.getSheet().getProperties();
		
		for (int i=0; i<properties.length;i++){
			actuals.add(properties[i].getDisplayName());
		}
		
		assertEquals(expecteds.size(), actuals.size());
		assertTrue(actuals.containsAll(expecteds));	
		doc.getSelectionModel().clearSelection();
	}

}
