package T1;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import FeatureFactories.PreemptiveTransitionFeatureFactory;
import FeatureFactories.StochasticTransitionFeatureFactory;
import FeatureFactories.TimedTransitionFeatureFactory;

import framework.tool.viewer.IContentProvider;
import pNeditor.FeaturesDockBar;
import pNeditor.IFeatureFactory;
import pNeditor.PNeditorDocTemplate;
import pNeditor.PNeditorDocument;
import pNeditor.PNeditorPlugin;
import pNeditor.PNeditorView;

import petriNetDomain.Place;
import petriNetDomain.TimedTransitionFeature;
import petriNetDomain.Transition;

/**
 * when an element of the list is selected the features dockbar must show all and only
 *  the features that can be applied to that element
 * @author Michaela
 */
public class T1_1 {
	
	private static FeaturesDockBar fDock;
	private static PNeditorDocument doc;
	private static PNeditorPlugin plugin;
	
	private static ArrayList<IFeatureFactory> expecteds;
	private static ArrayList<IFeatureFactory> actuals;
	private IContentProvider cp;
	private Transition t1;
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
		//serve per inizializzare la roba che serve per creare i posti
		PNeditorView view = new PNeditorView();
		view.setDocument(doc);
		view.initializeView(null, doc);
		
		fDock = new FeaturesDockBar(plugin.getFeatureFactory());
		expecteds = new ArrayList<IFeatureFactory>();
		actuals = new ArrayList<IFeatureFactory>();
	}

	@Before
	public void before(){

		t1 = new Transition("transition1", new Point(0,0));	
		doc.addTransitionToPetriNet(t1, null);
	}
	
	@After
	public void after(){
		expecteds.clear();
		actuals.clear();	
		doc.getSelectionModel().clearSelection();	
	}
	
	@AfterClass
	public static void tearDown() {
    }


	@Test
	public void test1() {		
		
		//check it gives all the factories if the element is a Tansition without features
			
	
		doc.getSelectionModel().select(t1,true);
		fDock.activate(doc);
		
		expecteds = plugin.getFeatureFactory();	
		
		cp = fDock.getViewer().getContentProvider();	
		for (int i=0; i<cp.getChildrenCount(doc); i++){
			if (!(fDock.getViewer().isElementChecked(cp.getChild(doc, i)))){
					actuals.add((IFeatureFactory) cp.getChild(doc, i));
			}
		} 
		
		assertEquals(expecteds.size(), actuals.size());
		assertTrue(actuals.containsAll(expecteds));	
		
	}
	
	@Test
	public void test2(){
		
		//check it gives only Stochastic and Preemptive if the element is a Tansition with Timed feature
		
		t1.addFeature(new TimedTransitionFeature(null));
		doc.getSelectionModel().select(t1,true);
		fDock.activate(doc);		
	
		for (IFeatureFactory ff: plugin.getFeatureFactory()){
			if (!(ff instanceof TimedTransitionFeatureFactory)){
				expecteds.add(ff);				
			}
		}
		
		cp = fDock.getViewer().getContentProvider();	
		for (int i=0; i<cp.getChildrenCount(doc); i++){
			if (!(fDock.getViewer().isElementChecked(cp.getChild(doc, i)))){
					actuals.add((IFeatureFactory) cp.getChild(doc, i));
			}
		} 
		
		assertEquals(expecteds.size(), actuals.size());
		assertTrue(actuals.containsAll(expecteds));			
						
	}
	
	@Test
	public void test3(){
		
		
		//check that doesnt gives factories if the element selected is a place
		
		Place p1 = new Place("place1", new Point(50,50));	
		doc.addPlaceToPetriNet(p1, null);
		doc.getSelectionModel().select(p1, true);
		doc.getSelectionModel().select(t1, true);
		fDock.activate(doc);		
		
		cp = fDock.getViewer().getContentProvider();	
		for (int i=0; i<cp.getChildrenCount(doc); i++){
			if (!(fDock.getViewer().isElementChecked(cp.getChild(doc, i)))){
					actuals.add((IFeatureFactory) cp.getChild(doc, i));
			}
		} 
		
		
		assertEquals(expecteds.isEmpty(), actuals.isEmpty());
	
	}

}
