package T2;

import static org.junit.Assert.*;

import java.awt.Point;
import java.beans.PropertyVetoException;

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
 * when editing a property of any kind the dockbar must check if 
 * the value is valid, and if not forbid the change
 * @author Michaela
 *
 */
public class T2_5 {

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
	public void test() {
		final double DELTA = 1e-15;
		double expected;
		double actual;
		PNeditorApplication app = mock(PNeditorApplication.class);
		when(app.getActiveDocument()).thenReturn(doc);		
		
		Transition t1 = new Transition("transition1", new Point(0,0));
		t1.addFeature(new TimedTransitionFeature(app));
		
		Property[] properties;
		doc.addTransitionToPetriNet(t1, null);
		doc.getSelectionModel().select(t1, true);
		pDock.activate(doc);
		pDock.createSheet();
		properties = pDock.getSheet().getProperties();
		
		//try to change the EFT property with a value grater than LFT value
		for (int i=0; i<properties.length;i++){
			if (properties[i].getDisplayName().equalsIgnoreCase("EFT")){
				properties[i].setValue("10");
			}
		}
		 expected = 0;
		 actual = 5;
		 for (PNelement pn: doc.getNodes()){
			 if (pn.getName().equalsIgnoreCase("transition1"))
				for ( IFeatureProperty p : ((IFeaturizable)pn).getFeature("Timed Transition").getProperties() ){ 
					if (p.getDisplayName().equalsIgnoreCase("EFT")){
					actual = (Double) p.readValue();}
				}
			}
		
		assertEquals(expected, actual, DELTA);
					
		doc.getSelectionModel().clearSelection();
		
	   //try to change the LFT property with a value smaller than EFT value
		
		doc.getSelectionModel().select(t1, true);
		pDock.activate(doc);
		pDock.createSheet();
		properties = pDock.getSheet().getProperties();
		//set two values that doesn't generate error
		for (int i=0; i<properties.length;i++){
			if (properties[i].getDisplayName().equalsIgnoreCase("LFT")){
				properties[i].setValue("10");
			}		
		}
		for (int i=0; i<properties.length;i++){
			if (properties[i].getDisplayName().equalsIgnoreCase("EFT")){
				properties[i].setValue("5");
			}			
		}
		 expected = 10;
		 actual = 0;
		
		 //try to lower the LFT value
		for (int i=0; i<properties.length;i++){
			if (properties[i].getDisplayName().equalsIgnoreCase("LFT")){			
				properties[i].setValue("4");
			}		
		}
		 for (PNelement pn: doc.getNodes()){
				for ( IFeatureProperty p : ((IFeaturizable)pn).getFeature("Timed Transition").getProperties() ){ 
					if (p.getDisplayName().equalsIgnoreCase("LFT")){
					actual = (Double) p.readValue();}
				}
			}
		
		 assertEquals(expected, actual, DELTA);
		 
		 doc.getSelectionModel().clearSelection();
		 
		 //try to give two transitions the same name 
		 Transition t2 = new Transition("transition2", new Point(10,10));
		 doc.addTransitionToPetriNet(t2, null);
		 doc.getSelectionModel().select(t1, true);
		 doc.getSelectionModel().select(t2, true);
		 
		 pDock.activate(doc);
		 pDock.createSheet();
		 
		 properties = pDock.getSheet().getProperties();
		 for (int i=0; i<properties.length;i++){
				if (properties[i].getDisplayName().equalsIgnoreCase("name")){
					if (properties[i].getValue().toString().equalsIgnoreCase("transition2")){
						properties[i].setValue("transition1");
					}					
				}
		}
		 assertEquals(t2.getName(), "transition2");
		 
		 doc.getSelectionModel().clearSelection();
		 
		 //try to give to LFT a string value
		 
		 t2.addFeature(new TimedTransitionFeature(app));
		 doc.getSelectionModel().select(t2, true);
		 pDock.activate(doc);
		 pDock.createSheet();
		 
		 properties = pDock.getSheet().getProperties();			
		
		for (int i=0; i<properties.length;i++){
			if (properties[i].getDisplayName().equalsIgnoreCase("LFT")){
				properties[i].setValue("ten");
			}
		}
		
		expected = 0;
	    actual = 10; 	    
	   
		for ( IFeatureProperty p : t2.getFeature("Timed Transition").getProperties() ){ 
			if (p.getDisplayName().equalsIgnoreCase("LFT")){
			actual = (Double) p.readValue();}
		}
		
		assertEquals(expected, actual, DELTA);
		 
		 doc.getSelectionModel().clearSelection();
		 
		 //try to add a resource that already exists
		 
		 doc.addResource("cpu");
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

		expected = 1;
		actual = 0;
		for (String r: doc.getResources())
			if (r.equalsIgnoreCase("cpu"))
				actual++;
				
		assertEquals(expected, actual,DELTA);
	    
		doc.getSelectionModel().clearSelection();
	
	}

}
