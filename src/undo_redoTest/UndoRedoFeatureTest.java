package undo_redoTest;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import framework.undoredo.HistoryComposite;
import framework.undoredo.HistoryException;
import framework.undoredo.IHistoryComposite;

import pNeditor.PNeditorDocTemplate;
import pNeditor.PNeditorDocument;
import pNeditor.PNeditorPlugin;
import petriNetDomain.IFeature;
import petriNetDomain.PNelement;
import petriNetDomain.TimedTransitionFeature;
import petriNetDomain.Transition;


public class UndoRedoFeatureTest {

	private static PNeditorPlugin myPlugin;
	private static PNeditorDocument myDoc;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		myPlugin = new PNeditorPlugin();
		myPlugin.initClipboard();
		myDoc = new PNeditorDocument();
		assertNotNull(myDoc);
		PNeditorDocTemplate dt = new PNeditorDocTemplate(myPlugin);
		myDoc.setDocTemplate(dt);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	@Test
	public void testUndoCreationOneTimedFeature() {
		Point position = new Point (0,0);
		Transition t = new Transition(new String("transition0"),
				position);
		Set<IFeature> expectedFeatures = t.getFeatures();
		assertEquals(expectedFeatures.size(), 0);
		IFeature ft = new TimedTransitionFeature();
		myDoc.addFeatureToNode(ft, t, myDoc.getHistoryManager());
		Set<IFeature> transitionFeatures = t.getFeatures();
		assertEquals(transitionFeatures.size(), 1);
		try {
			myDoc.getHistoryManager().undo(null);
		} catch (HistoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Set<IFeature> actualFeatures = t.getFeatures();
		assertEquals(expectedFeatures.size(), actualFeatures.size());
	}
	
	@Test
	public void testUndoCreationTwoTimedFeatures() {
		Point p0 = new Point (0,0);
		Point p1 = new Point (10,10);
		Transition t = new Transition(new String("transition0"),
				p0);
		Transition t1 = new Transition(new String("transition1"),
				p1);
		Set<IFeature> expFt = t.getFeatures();
		assertEquals(expFt.size(), 0);
		Set<IFeature> expFt1 = t1.getFeatures();
		assertEquals(expFt1.size(), 0);
		ArrayList<PNelement> pnel = new ArrayList<PNelement>();
		pnel.add(t);
		pnel.add(t1);
		IHistoryComposite hc = new HistoryComposite("crea");
		for (PNelement pn : pnel ){
			IFeature ft = new TimedTransitionFeature();
			myDoc.addFeatureToNode(ft, pn, hc);
		}
		myDoc.getHistoryManager().addMemento(hc);
		Set<IFeature> tFeatures = t.getFeatures();
		Set<IFeature> t1Features = t1.getFeatures();
		assertEquals(tFeatures.size(), 1);
		assertEquals(t1Features.size(), 1);
		try {
			myDoc.getHistoryManager().undo(null);
		} catch (HistoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Set<IFeature> actualFeatures = t.getFeatures();
		Set<IFeature> actualFeaturest1 = t1.getFeatures();
		assertEquals(expFt.size(), actualFeatures.size());
		assertEquals(expFt1.size(), actualFeaturest1.size());
	}
	
	@Test
	public void testRedoCreationTwoTimedFeatures(){
		Point p0 = new Point (0,0);
		Point p1 = new Point (10,10);
		Transition t = new Transition(new String("transition0"),
				p0);
		Transition t1 = new Transition(new String("transition1"),
				p1);
		Set<IFeature> initFt = t.getFeatures();
		assertEquals(initFt.size(), 0);
		Set<IFeature> initFt1 = t1.getFeatures();
		assertEquals(initFt1.size(), 0);
		ArrayList<PNelement> pnel = new ArrayList<PNelement>();
		pnel.add(t);
		pnel.add(t1);
		IHistoryComposite hc = new HistoryComposite("crea");
		for (PNelement pn : pnel ){
			IFeature ft = new TimedTransitionFeature();
			myDoc.addFeatureToNode(ft, pn, hc);
		}
		myDoc.getHistoryManager().addMemento(hc);
		Set<IFeature> expFt = t.getFeatures();
		Set<IFeature> expFt1 = t1.getFeatures();
		assertEquals(expFt.size(), 1);
		assertEquals(expFt1.size(), 1);
		try {
			myDoc.getHistoryManager().undo(null);
		} catch (HistoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			myDoc.getHistoryManager().redo(null);
		} catch (HistoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Set<IFeature> actualFeatures = t.getFeatures();
		Set<IFeature> actualFeaturest1 = t1.getFeatures();
		assertEquals(expFt.size(), actualFeatures.size());
		assertEquals(expFt1.size(), actualFeaturest1.size());
	}
	
	@Test
	public void testUndoRemovalOneTimedFeature() {
		Point position = new Point (0,0);
		Transition t = new Transition(new String("transition0"),
				position);
		Set<IFeature> initFeatures = t.getFeatures();
		assertEquals(initFeatures.size(), 0);
		IFeature ft = new TimedTransitionFeature();
		t.addFeature(ft);
		Set<IFeature> expectedFeatures = t.getFeatures();
		assertEquals(expectedFeatures.size(), 1);
		//rimuovo la feature dalla transizione
		myDoc.removeFeatureFromNode(ft, t, myDoc.getHistoryManager());
		assertEquals(t.getFeatures().size(), 0);
		//undo
		try {
			myDoc.getHistoryManager().undo(null);
		} catch (HistoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//mi aspetto che ora gli elementi siano gli stessi di prima della rimozione
		Set<IFeature> actualFeatures = t.getFeatures();
		assertEquals(expectedFeatures.size(), actualFeatures.size());
	}
	
	@Test
	public void testRedoRemovalOneTimedFeature() {
		Point position = new Point (0,0);
		Transition t = new Transition(new String("transition0"),
				position);
		Set<IFeature> initFeatures = t.getFeatures();
		assertEquals(initFeatures.size(), 0);
		IFeature ft = new TimedTransitionFeature();
		t.addFeature(ft);
		assertEquals(t.getFeatures().size(), 1);
		//rimuovo la feature dalla transizione
		myDoc.removeFeatureFromNode(ft, t, myDoc.getHistoryManager());
		Set<IFeature> expectedFeatures = t.getFeatures();
		assertEquals(expectedFeatures.size(), 0);
		try {
			myDoc.getHistoryManager().undo(null);
		} catch (HistoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			myDoc.getHistoryManager().redo(null);
		} catch (HistoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Set<IFeature> actualFeatures = t.getFeatures();
		assertEquals(expectedFeatures.size(), actualFeatures.size());	
	}
	
	
	
}
