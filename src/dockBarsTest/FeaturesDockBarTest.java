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
import framework.tool.viewer.ILabelProvider;

import pNeditor.ElementListDockBar;
import pNeditor.FeaturesDockBar;
import pNeditor.IFeatureFactory;
import pNeditor.PNeditorDocTemplate;
import pNeditor.PNeditorDocument;
import pNeditor.PNeditorPlugin;
import pNeditor.PNeditorView;
import petriNetDomain.IFeaturizable;
import petriNetDomain.PNelement;
import petriNetDomain.TimedTransitionFeature;
import petriNetDomain.Transition;
import FeatureFactories.PreemptiveTransitionFeatureFactory;
import FeatureFactories.TimedTransitionFeatureFactory;

/**
 * This class tests the basic functioning of the class {@link FeaturesDockBar}
 * @author Michaela
 *
 */
public class FeaturesDockBarTest {

	private static FeaturesDockBar testObj = null;
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
		
		testObj = new FeaturesDockBar(plugin.getFeatureFactory());
		
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * the basic functioning being tested is whether the features list corresponds exactly to
	 * the list of feature applicable to the element selected
	 * 
	 * and if an element has a feature the corresponding factory must appear checked
	 */
	@Test
	public void test() {
		
		Transition t1 = new Transition("transition1", new Point(0,0));
		
		doc.addTransitionToPetriNet(t1, null);
		doc.getSelectionModel().select(t1,true);
		testObj.activate(doc);
		ArrayList<IFeatureFactory> expecteds = plugin.getFeatureFactory();		
		IContentProvider actuals = testObj.getViewer().getContentProvider();
	
		for (int i=0; i<actuals.getChildrenCount(doc); i++){
			assertEquals(actuals.getChild(doc, i).getClass(), expecteds.get(i).getClass());		
		} 
		
		//with feature
		t1.addFeature(new TimedTransitionFeature(null));
		doc.getSelectionModel().select(t1,true);
		testObj.activate(doc);
		for (int i=0; i<actuals.getChildrenCount(doc); i++){
			assertEquals(actuals.getChild(doc, i).getClass(), expecteds.get(i).getClass());	
			if ( actuals.getChild(doc,i).getClass().equals(TimedTransitionFeatureFactory.class))
			{
				assertTrue(testObj.getViewer().isElementChecked(actuals.getChild(doc,i)));
			}
		} 
	}

}
