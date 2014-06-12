package T2;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.l2fprod.common.propertysheet.Property;

import pNeditor.PNeditorDocTemplate;
import pNeditor.PNeditorDocument;
import pNeditor.PNeditorPlugin;
import pNeditor.PNeditorView;
import pNeditor.PropertiesDockBar;
import petriNetDomain.IFeatureProperty;
import petriNetDomain.IFeaturizable;
import petriNetDomain.PNelement;
import petriNetDomain.PreemptiveTransitionFeature;
import petriNetDomain.TimedTransitionFeature;
import petriNetDomain.Transition;
import pnEditorApp.PNeditorApplication;
import static org.mockito.Mockito.*;
/**
 * when a feature's property is modified on the properties
 * dockbar it has to change also in the document
 * @author Michaela
 *
 */
public class T2_3 {
	
	private static PropertiesDockBar pDock;
	private static PNeditorDocument doc;
	private static PNeditorPlugin plugin;
	private Transition t1;
	private Property[] properties;
	private PNeditorApplication app;
	final double DELTA = 1e-15;
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

	@Before 
	public void before(){
		t1 = new Transition("transition1", new Point(0,0));
	    app = mock(PNeditorApplication.class);
		when(app.getActiveDocument()).thenReturn(doc);	
	}
	@After 
	public void after(){
		 doc.getSelectionModel().clearSelection();
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}


	@Test
	public void test1() throws AWTException {
		
		//try to change the LFT property of a transition and check if the value is changed on the document					
		
		t1.addFeature(new TimedTransitionFeature(app));		
		
		doc.addTransitionToPetriNet(t1, null);
		doc.getSelectionModel().select(t1, true);
		pDock.activate(doc);
		pDock.createSheet();
		properties = pDock.getSheet().getProperties();
			
		for (int i=0; i<properties.length;i++){
			if (properties[i].getDisplayName().equalsIgnoreCase("LFT")){
				properties[i].setValue("10");
			}
		}
		double expected = 10;
		double actual = 0;
		for (PNelement pn: doc.getNodes()){
			for ( IFeatureProperty p : ((IFeaturizable)pn).getFeature("Timed Transition").getProperties() ) 
				if (p.getDisplayName().equalsIgnoreCase("LFT")){
				actual = (Double) p.readValue();}
		}
		
	   assertEquals(expected, actual, DELTA);  
		
	}
	
	@Test
	public void test2(){
		
	   //try to add a new resource with the property AddResource of a preemtive transition feature	
		
	   t1.addFeature(new PreemptiveTransitionFeature(app));
	   doc.getSelectionModel().select(t1, true);
		pDock.activate(doc);
		pDock.createSheet();
		properties = pDock.getSheet().getProperties();
			
		for (int i=0; i<properties.length;i++){
			if (properties[i].getDisplayName().equalsIgnoreCase("Add Resource")){
				properties[i].setValue("cpu");
			}
		}
		assertTrue(doc.getResources().contains("cpu"));
	}

}
