package T1;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pNeditor.IFeatureFactory;
import pNeditor.PNeditorDocument;
import pNeditor.PNeditorPlugin;
import petriNetDomain.IFeature;
import petriNetDomain.TimedTransitionFeature;
import petriNetDomain.Transition;

public class T1_4 {

	private PNeditorDocument doc;
	private PNeditorPlugin plugin;
	
	@BeforeClass
	public void setUp() throws Exception {
	}

	@AfterClass
	public void tearDown() throws Exception {
	}


	@Test
	public void test() {
		Point p;
		IFeature f;
		p = new Point (0,0);
		Transition t = new Transition("t1", p);	
		doc.getSelectionModel().select(t, true);
		ArrayList<IFeatureFactory> factories = plugin.getFeatureFactory();
		for (IFeatureFactory ff: factories){
			f= ff.createFeature();
			t.addFeature(f);
		}
		//ci sarà un modo migliore per non fare il fof? magari va implementato un metodino veloce per restituire la feature giusta
		for (IFeatureFactory ff: factories){
			if (ff.getName().equalsIgnoreCase("Preemptive Transition")){
				assertFalse(doc.deleteFeatures(ff, factories));
			}
		}
		for (IFeatureFactory ff: factories){
			if (ff.getName().equalsIgnoreCase("Stochastic Transition")){
				assertTrue(doc.deleteFeatures(ff, factories));
			}
		}
		//provo a levarne una che non ha, per il code coverage
		for (IFeatureFactory ff: factories){
			if (ff.getName().equalsIgnoreCase("Stochastic Transition")){
				assertTrue(doc.deleteFeatures(ff, factories));
			}
		}
	}

}
