package dockBarsTest;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import framework.tool.viewer.IContentProvider;

import pNeditor.ElementListDockBar;
import pNeditor.FeaturesDockBar;
import pNeditor.IFeatureFactory;
import pNeditor.PNeditorDocTemplate;
import pNeditor.PNeditorDocument;
import pNeditor.PNeditorPlugin;
import pNeditor.PNeditorView;
import petriNetDomain.PNelement;
import petriNetDomain.Place;
import petriNetDomain.TimedTransitionFeature;
import petriNetDomain.Transition;

/**
 * This class tests the basic functioning of the class {@link ElementListDockBar}
 * @author Michaela
 *
 */

public class ElementListDockBarTest {

	private static ElementListDockBar testObj = null;
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
		PNeditorView view = new PNeditorView();
		view.setDocument(doc);
		view.initializeView(null, doc);
		
		testObj = new ElementListDockBar();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * the basic functioning being tested is whether the element list corresponds exactly to
	 * the list of element in the document
	 */
	@Test
	public void test() {
	    
		ArrayList<PNelement> expecteds = new ArrayList<PNelement>();
		Transition t1 = new Transition("transition1", new Point(0,0));
		expecteds.add(t1);
		Place p1 = new Place("place1", new Point(50,50));
		expecteds.add(p1);
		//t1.addFeature(new TimedTransitionFeature());		
		doc.addTransitionToPetriNet(t1, null);
		doc.addPlaceToPetriNet(p1, null);
		doc.getSelectionModel().select(expecteds);	
		
		testObj.activate(doc);
	
		IContentProvider cp = testObj.getViewer().getContentProvider();
		
		ArrayList<PNelement> actuals = new ArrayList<PNelement>();
		for (int i=0; i<cp.getChildrenCount(doc); i++){
			actuals.add((PNelement) cp.getChild(doc, i));
		} 		
		assertEquals(expecteds.size(), actuals.size());
		assertTrue(actuals.containsAll(expecteds));
		
	}

}