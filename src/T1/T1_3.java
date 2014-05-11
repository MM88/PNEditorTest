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
import pNeditor.PNeditorDocTemplate;
import pNeditor.PNeditorDocument;
import pNeditor.PNeditorPlugin;
import petriNetDomain.IFeature;
import petriNetDomain.PreemptiveTransitionFeature;
import petriNetDomain.Transition;

public class T1_3 {

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
	p = new Point (0,0);
	Transition t = new Transition("t1", p);	
	doc.getSelectionModel().select(t, true);
	//creo la preemptive
	ArrayList<IFeatureFactory> factories = plugin.getFeatureFactory();
	for (IFeatureFactory ff: factories){
		if (ff.getName().equalsIgnoreCase("Preemptive Transition"))
			doc.createFeatures(ff, factories);
	}
	//controllo che abbia la timed
	assertTrue(t.hasFeature("Timed Transition"));
	
	//provo a dare di nuovo la preemptive per il code coverage
	for (IFeatureFactory ff: factories){
		if (ff.getName().equalsIgnoreCase("Preemptive Transition"))
			doc.createFeatures(ff, factories);
	}
	//rimuovo solo la preemptive e la rimetto sempre per il code coverage, ora la timed dovrebbe gia esserci
	t.removeFeature("Preemptive Transition");
	assertTrue(t.hasFeature("Timed Transition"));
	for (IFeatureFactory ff: factories){
		if (ff.getName().equalsIgnoreCase("Preemptive Transition"))
			doc.createFeatures(ff, factories);
	}
	}

}
