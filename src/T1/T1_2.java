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

import pNeditor.IFeatureFactory;
import pNeditor.PNeditorDocTemplate;
import pNeditor.PNeditorDocument;
import pNeditor.PNeditorPlugin;
import pNeditor.PNeditorView;
import petriNetDomain.IFeature;
import petriNetDomain.PreemptiveTransitionFeature;
import petriNetDomain.Transition;
import pnEditorApp.PNeditorApplication;
import static org.mockito.Mockito.*;
/**
 * The creation process must create if necessary all the dependencies of the feature selected
 * @author Michaela
 */
public class T1_2 {

	private static PNeditorDocument doc;
	private static PNeditorPlugin plugin;
	private static ArrayList<IFeatureFactory> factories;
	private PreemptiveTransitionFeatureFactory ptff;
	private Transition t1;
	/**
	 * set up the main classes needed in order to create a document 
	 * and add elements to it and perform operations on them
	 */
	@BeforeClass
	public static void setUp() throws Exception {
		
		plugin = new PNeditorPlugin();
		plugin.initClipboard();
		doc = new PNeditorDocument();
		PNeditorDocTemplate dt = new PNeditorDocTemplate(plugin);
		doc.setDocTemplate(dt);
		PNeditorView view = new PNeditorView();
		view.setDocument(doc);
		view.initializeView(null, doc);
		factories = new ArrayList<IFeatureFactory>();
	}

	@Before
	public void before(){
		
		String[] dependencies = {"Timed Transition"};
		
		ptff = mock(PreemptiveTransitionFeatureFactory.class);
		
		TimedTransitionFeatureFactory ttff = new TimedTransitionFeatureFactory();
		StochasticTransitionFeatureFactory stff = new StochasticTransitionFeatureFactory();
		
		factories.add(stff);
		factories.add(ttff);
		factories.add(ptff);
		
		
		when(ptff.getName()).thenReturn("Preemptive Transition");
		when(ptff.getDependencies()).thenReturn(dependencies);
		when(ptff.hasDependencies()).thenReturn(true);
		
		t1 = new Transition("transition1", new Point(0,0));
		doc.addTransitionToPetriNet(t1, null);
		doc.getSelectionModel().select(t1, true);
	}
	@AfterClass
	public static void tearDown() throws Exception {
	}

	@Test
	public void test() {
	
		//first method: with a mock of the Preemptive feature I check if after the creation process the Timed has been created
		
		PreemptiveTransitionFeature ptf = mock(PreemptiveTransitionFeature.class);
		when(ptff.createFeature()).thenReturn(ptf);
		when(ptf.getName()).thenReturn("Preemptive Transition");
		
		
		doc.createFeature(ptff, factories, t1, null);
		
		assertTrue(t1.hasFeature("Timed Transition"));
		doc.getSelectionModel().clearSelection();
		
		
	}
	
	@Test
	public void test2(){
		
		//second method: mock the application and not the Preemptive feature, but need some refactoring
		
		PNeditorApplication app = mock(PNeditorApplication.class);
		when(app.getActiveDocument()).thenReturn(doc);
		
		IFeature f= new PreemptiveTransitionFeature(app);
		when(ptff.createFeature()).thenReturn(f);
		
		doc.createFeature(ptff, factories, t1, null);
		
		assertTrue(t1.hasFeature("Timed Transition"));
	}

}
