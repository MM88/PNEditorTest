package T1;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import FeatureFactories.TimedTransitionFeatureFactory;

import framework.FApplication;
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
import pnEditorApp.MainFrame;
import pnEditorApp.PNeditorApplication;
/**
 * if more than one element are selected the features dockbar must
 *  show only the features appliable to all the selected elements
 * @author Michaela
 */
public class T1_4 {

	private static FeaturesDockBar fDock;
	private static PNeditorDocument doc;
	private static PNeditorPlugin plugin;
	
	private static ArrayList<IFeatureFactory> expecteds;
	private static ArrayList<IFeatureFactory> actuals;
	private IContentProvider cp;
	private Transition t1;
	private Transition t2;
	private Place p1;
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
		t2 = new Transition("transition2", new Point(10,10));	
		doc.addTransitionToPetriNet(t2, null);
		p1 = new Place("place1", new Point(20,20));
		doc.addPlaceToPetriNet(p1, null);
	}
	
	@After
	public void after(){
		
		expecteds.clear();
		actuals.clear();	
		doc.getSelectionModel().clearSelection();	
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
	}
	
	@Test
	public void test1() {		
		
		//with a place and a transition selected there should not be factories shown
		doc.getSelectionModel().select(t1, true);
		doc.getSelectionModel().select(p1, true);
		
		fDock.activate(doc);			
		cp = fDock.getViewer().getContentProvider();	
		
		for (int i=0; i<cp.getChildrenCount(doc); i++){
			if (!(fDock.getViewer().isElementChecked(cp.getChild(doc, i)))){
					actuals.add((IFeatureFactory) cp.getChild(doc, i));
			}
		} 
		assertEquals(expecteds.isEmpty(), actuals.isEmpty());	
			
	}
	
	@Test
	public void test2(){
		
		//with two transitions without features all the factories must be shown
		doc.getSelectionModel().select(t1, true);
		doc.getSelectionModel().select(t2, true);
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
	public void test3(){
		
		//with two transitions, one of the two has a feature, all the factories must be shown
		t1.addFeature(new TimedTransitionFeature(null));
		doc.getSelectionModel().select(t1, true);
		doc.getSelectionModel().select(t2, true);
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
	public void test4(){
		
		//two transitions with the same feature only the other factories must be shown unchecked
		t2.addFeature(new TimedTransitionFeature(null));
		doc.getSelectionModel().select(t1, true);
		doc.getSelectionModel().select(t2, true);
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
	

}
