package T2;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Event;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;

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
import pNeditor.PropertyDescriptorAdapter;
import petriNetDomain.PNelement;
import petriNetDomain.Transition;

/**
 * when the name of an element is modified on the properties
 *  dockbar it has to change also in the document
 * @author Michaela
 *
 */
public class T2_2 {

	
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
	public void test() throws AWTException {
		
		Property[] properties;
		Transition t1 = new Transition("transition1", new Point(0,0));
		
		doc.addTransitionToPetriNet(t1, null);
		doc.getSelectionModel().select(t1, true);
		
		pDock.activate(doc);
		pDock.createSheet();
		properties = pDock.getSheet().getProperties();
		  
		for (int i=0; i<properties.length;i++){
			if (properties[i].getDisplayName().equalsIgnoreCase("name")){
				properties[i].setValue("newName");
			}
		}
		
		assertEquals(t1.getName(), "newName");
	}

}
