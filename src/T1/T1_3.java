package T1;

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
import petriNetDomain.TimedTransitionFeature;
import petriNetDomain.Transition;
/**
 * when removing a feature the dependencies must be checked and if necessary
 *  the removing process must be stopped, with an error to the user
 * @author Michaela
 */
public class T1_3 {

	private static PNeditorDocument doc;
	private static PNeditorPlugin plugin;
	
	/**
	 * set up the main classes needed in order to create a document 
	 * and add elements to it and perform operations on them
	 *
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
	}

	@AfterClass
	public static void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
		TimedTransitionFeatureFactory ttff = new TimedTransitionFeatureFactory();
		Transition t1 = new Transition("transition1", new Point(0,0));
		
		doc.addTransitionToPetriNet(t1, null);
		t1.addFeature(new TimedTransitionFeature(null));
		assertTrue(t1.hasFeature("Timed Transition"));
		doc.getSelectionModel().select(t1, true);
		doc.deleteFeature(ttff, plugin.getFeatureFactory(), null);
		assertFalse(t1.hasFeature("Timed Transition"));
		
		//the preemptive feature is mocked
		
		String[] dependencies = {"Timed Transition"};
		PreemptiveTransitionFeatureFactory ptff = mock(PreemptiveTransitionFeatureFactory.class);
		PreemptiveTransitionFeature ptf = mock(PreemptiveTransitionFeature.class);
		
		//StochasticTransitionFeatureFactory stff = new StochasticTransitionFeatureFactory();
	
		when(ptff.createFeature()).thenReturn(ptf);
		when(ptff.getName()).thenReturn("Preemptive Transition");
		when(ptff.getDependencies()).thenReturn(dependencies);
		when(ptff.hasDependencies()).thenReturn(true);
		when(ptf.getName()).thenReturn("Preemptive Transition");
	
	
		//create the preemptive		
		
		doc.createFeature(ptff, plugin.getFeatureFactory(), t1, null);
		doc.getSelectionModel().select(t1, true);
		
		//try to remove the timed 
		doc.deleteFeature(ttff, plugin.getFeatureFactory(), null);
		assertTrue(t1.hasFeature("Timed Transition"));
	
	}

}
