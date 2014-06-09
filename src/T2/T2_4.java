package T2;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Point;
import java.beans.PropertyDescriptor;
import java.beans.PropertyVetoException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pNeditor.PNeditorDocTemplate;
import pNeditor.PNeditorDocument;
import pNeditor.PNeditorPlugin;
import pNeditor.PNeditorView;
import pNeditor.PropertiesDockBar;
import petriNetDomain.FeaturePropertyAdapter;
import petriNetDomain.IFeatureProperty;
import petriNetDomain.Place;
import petriNetDomain.StochasticTransitionFeature;
import petriNetDomain.TimedTransitionFeature;
import petriNetDomain.Transition;
import pnEditorApp.PNeditorApplication;

import com.l2fprod.common.propertysheet.Property;

/**
 * when more than one element is selected the properties dockbar must show the general 
 * properties of the selected elements and only the common features properties
 * @author Michaela
 *
 */
public class T2_4 {

	private static PropertiesDockBar pDock;
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
		
		pDock = new PropertiesDockBar();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}


	@Test
	public void test() throws PropertyVetoException {
		final double DELTA = 1e-15;
		PNeditorApplication app = mock(PNeditorApplication.class);
		when(app.getActiveDocument()).thenReturn(doc);
		ArrayList<String> expecteds = new ArrayList<String>();
		ArrayList<String> actuals = new ArrayList<String>();
		Property[] properties;
		
		//the elements selected are two transition with the same features
		
		Transition t1 = new Transition("transition1", new Point(0,0));
		doc.addTransitionToPetriNet(t1, null);
		t1.addFeature(new TimedTransitionFeature(app));
		t1.addFeature(new StochasticTransitionFeature());		
		
		Transition t2 = new Transition("transition2", new Point(20,20));
		doc.addTransitionToPetriNet(t2, null);
		t2.addFeature(new TimedTransitionFeature(app));
		t2.addFeature(new StochasticTransitionFeature());
		doc.getSelectionModel().select(t1, true);
		doc.getSelectionModel().select(t2, true);
		
		expecteds.add("name");
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
		expecteds.clear();
		actuals.clear();	
		doc.getSelectionModel().clearSelection();
		
		//the elements selected are a transition with features and a place
		
		Place p1 = new Place("place1", new Point(10,10));		
	    doc.addPlaceToPetriNet(p1, null);
	    doc.getSelectionModel().select(p1, true);
	    doc.getSelectionModel().select(t1, true);
	    expecteds.add("name");
		expecteds.add("name");
		pDock.activate(doc);
		pDock.createSheet();
		
		properties = pDock.getSheet().getProperties();
		
		for (int i=0; i<properties.length;i++){
			actuals.add(properties[i].getDisplayName());
		}
		
		assertEquals(expecteds.size(), actuals.size());
		assertTrue(actuals.containsAll(expecteds));	
		expecteds.clear();
		actuals.clear();	
		doc.getSelectionModel().clearSelection();
		
		//if two elements have the same value of a property it is shown in the dockbar 
		
		for (IFeatureProperty fp : t1.getFeature("Timed Transition").getProperties()){
			if(fp.getDisplayName().equalsIgnoreCase("LFT")){
				fp.writeValue("10");
			}
		}
		for (IFeatureProperty fp : t2.getFeature("Timed Transition").getProperties()){
			if(fp.getDisplayName().equalsIgnoreCase("LFT")){
				fp.writeValue("10");
			}
		}
		double expected = 10 ;
		double actual = 0;
		t1.removeFeature("Stochastic Transition");
		t2.removeFeature("Stochastic Transition");
		doc.getSelectionModel().select(t1, true);
		doc.getSelectionModel().select(t2, true);
		
		pDock.activate(doc);
		pDock.createSheet();
		
		properties = pDock.getSheet().getProperties();
		
		for (int i=0; i<properties.length;i++){
			if (properties[i].getDisplayName().equalsIgnoreCase("LFT"))
			{
				actual = (Double) properties[i].getValue();
			}			
		}
		assertEquals(expected, actual, DELTA);
		doc.getSelectionModel().clearSelection();
	}

}
