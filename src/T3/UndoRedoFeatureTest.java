package T3;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import FeatureFactories.PreemptiveTransitionFeatureFactory;
import FeatureFactories.StochasticTransitionFeatureFactory;
import FeatureFactories.TimedTransitionFeatureFactory;

import framework.undoredo.HistoryComposite;
import framework.undoredo.HistoryException;
import framework.undoredo.IHistoryComposite;

import pNeditor.IFeatureFactory;
import pNeditor.PNeditorDocTemplate;
import pNeditor.PNeditorDocument;
import pNeditor.PNeditorPlugin;
import petriNetDomain.IFeature;
import petriNetDomain.PNelement;
import petriNetDomain.PreemptiveTransitionFeature;
import petriNetDomain.TimedTransitionFeature;
import petriNetDomain.Transition;
import pnEditorApp.PNeditorApplication;

/**
 * This class tests that:
 *  the creation of a feature is undoable
 *  if the creation of a feature is undone it is redoable
 *  the removal of a feature is undoable
 *  if the removal of a feature is undone it is redoable
 * @author Benedetta
 *
 */

public class UndoRedoFeatureTest {

	private static PNeditorPlugin myPlugin;
	private static PNeditorDocument myDoc;
	private static PNeditorApplication mockedApp;
	

	@Before
	public void setUp() throws Exception {
		myPlugin = new PNeditorPlugin();
		myPlugin.initClipboard();
		myDoc = new PNeditorDocument();
		assertNotNull(myDoc);
		PNeditorDocTemplate dt = new PNeditorDocTemplate(myPlugin);
		myDoc.setDocTemplate(dt);
		mockedApp = Mockito.mock(PNeditorApplication.class);	
		Mockito.when(mockedApp.getActiveDocument()).thenReturn(myDoc);	
	}
	
	
	@Test
	public void testUndoRedoCreationOneTimedFeature() {
		Point position = new Point (0,0);
		Transition t = new Transition(new String("transition0"),
				position);
		Set<IFeature> expectedFeatures = t.getFeatures();
		assertEquals(expectedFeatures.size(), 0);
		IFeature ft = new TimedTransitionFeature(null);
		myDoc.addFeatureToNode(ft, t, myDoc.getHistoryManager());
		Set<IFeature> transitionFeatures = t.getFeatures();
		int expSize = 1;
		assertEquals(expSize, transitionFeatures.size());
		//undoTest
		try {
			myDoc.getHistoryManager().undo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		Set<IFeature> actualFeatures = t.getFeatures();
		assertEquals(expectedFeatures.size(), actualFeatures.size());
		//redoTest
		try {
			myDoc.getHistoryManager().redo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		actualFeatures = t.getFeatures();
		assertEquals(transitionFeatures.size(), actualFeatures.size());
	}
	
	@Test
	public void testUndoRedoCreationOnePreemtiveFeature() {
		Point position = new Point (0,0);
		Transition t = new Transition("transition0",
				position);
		Set<IFeature> expectedFeatures = t.getFeatures();
		int expSize = 0;
		assertEquals(expSize, expectedFeatures.size());
		myDoc.getSelectionModel().select(t, true);
		IFeature myPree = new PreemptiveTransitionFeature(mockedApp);	
		ArrayList<IFeatureFactory> factories = new ArrayList<IFeatureFactory>();
		IFeatureFactory ptff = mock(PreemptiveTransitionFeatureFactory.class);
		when(ptff.createFeature()).thenReturn(myPree);
		IFeatureFactory ttff = new TimedTransitionFeatureFactory();
		IFeatureFactory stff = new StochasticTransitionFeatureFactory();
		factories.add(stff);
		factories.add(ttff);
		factories.add(ptff);
		String[] dependencies = {"Timed Transition"};
		when(ptff.getName()).thenReturn("Preemptive Transition");
		when(ptff.getDependencies()).thenReturn(dependencies);
		when(ptff.hasDependencies()).thenReturn(true);
		IHistoryComposite hc = new HistoryComposite("crea");
		myDoc.createFeature(ptff, factories, t, hc);
		myDoc.getHistoryManager().addMemento(hc);
		Set<IFeature> transitionFeatures = t.getFeatures();
		expSize = 2;
		assertEquals(expSize, transitionFeatures.size());
		IFeature timed = t.getFeature("Timed Transition");
		assertNotNull(timed);
		IFeature pree = t.getFeature("Preemptive Transition");
		assertNotNull(pree);
		//undoTest
		try {
			myDoc.getHistoryManager().undo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		Set<IFeature> actualFeatures = t.getFeatures();
		assertEquals(expectedFeatures.size(), actualFeatures.size());
		// redo test
		try {
			myDoc.getHistoryManager().redo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		actualFeatures = t.getFeatures();
		assertEquals(transitionFeatures.size(), actualFeatures.size());
	}
	
	@Test
	public void testUndoRedoCreationTwoTimedFeatures() {
		Point p0 = new Point (0,0);
		Point p1 = new Point (10,10);
		Transition t = new Transition(new String("transition0"),
				p0);
		Transition t1 = new Transition(new String("transition1"),
				p1);
		Set<IFeature> expFt = t.getFeatures();
		int expSize = 0;
		assertEquals(expSize, expFt.size());
		Set<IFeature> expFt1 = t1.getFeatures();
		assertEquals(expSize, expFt1.size());
		ArrayList<PNelement> pnel = new ArrayList<PNelement>();
		pnel.add(t);
		pnel.add(t1);
		IHistoryComposite hc = new HistoryComposite("crea");
		for (PNelement pn : pnel ){
			IFeature ft = new TimedTransitionFeature(null);
			myDoc.addFeatureToNode(ft, pn, hc);
		}
		myDoc.getHistoryManager().addMemento(hc);
		Set<IFeature> tFeatures = t.getFeatures();
		Set<IFeature> t1Features = t1.getFeatures();
		expSize = 1;
		assertEquals(expSize, tFeatures.size());
		assertEquals(expSize, t1Features.size());
		//undoTest
		try {
			myDoc.getHistoryManager().undo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		Set<IFeature> actualFeatures = t.getFeatures();
		Set<IFeature> actualFeaturest1 = t1.getFeatures();
		assertEquals(expFt.size(), actualFeatures.size());
		assertEquals(expFt1.size(), actualFeaturest1.size());
		//redoTest
		try {
			myDoc.getHistoryManager().redo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		actualFeatures = t.getFeatures();
		actualFeaturest1 = t1.getFeatures();
		assertEquals(tFeatures.size(), actualFeatures.size());
		assertEquals(t1Features.size(), actualFeaturest1.size());
	}
	
	@Test
	public void testUndoRedoRemovalOneTimedFeature() {
		Point position = new Point (0,0);
		Transition t = new Transition(new String("transition0"),
				position);
		Set<IFeature> initFeatures = t.getFeatures();
		int expSize = 0;
		assertEquals(expSize, initFeatures.size());
		IFeature ft = new TimedTransitionFeature(null);
		t.addFeature(ft);
		Set<IFeature> expectedFeatures = t.getFeatures();
		expSize = 1;
		assertEquals(expSize, expectedFeatures.size());
		myDoc.removeFeatureFromNode(ft, t, myDoc.getHistoryManager());
		Set<IFeature> tFeatures = t.getFeatures();
		expSize = 0;
		assertEquals(expSize, tFeatures.size());
		//undoTest
		try {
			myDoc.getHistoryManager().undo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		Set<IFeature> actualFeatures = t.getFeatures();
		assertEquals(expectedFeatures.size(), actualFeatures.size());
		//redoTest
		try {
			myDoc.getHistoryManager().redo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		actualFeatures = t.getFeatures();
		assertEquals(tFeatures.size(), actualFeatures.size());	
	}
	
	@Test
	public void testUndoRedoRemovalOnePreemtiveFeature() {
		Point position = new Point (0,0);
		Transition t = new Transition("transition0",
				position);
		Set<IFeature> transitionFeatures = t.getFeatures();
		int expSize = 0;
		assertEquals(expSize, transitionFeatures.size());
		myDoc.getSelectionModel().select(t, true);
		IFeature myPree = new PreemptiveTransitionFeature(mockedApp);	
		ArrayList<IFeatureFactory> factories = new ArrayList<IFeatureFactory>();
		IFeatureFactory ptff = mock(PreemptiveTransitionFeatureFactory.class);
		when(ptff.createFeature()).thenReturn(myPree);
		IFeatureFactory ttff = new TimedTransitionFeatureFactory();
		IFeatureFactory stff = new StochasticTransitionFeatureFactory();
		factories.add(stff);
		factories.add(ttff);
		factories.add(ptff);
		String[] dependencies = {"Timed Transition"};
		when(ptff.getName()).thenReturn("Preemptive Transition");
		when(ptff.getDependencies()).thenReturn(dependencies);
		when(ptff.hasDependencies()).thenReturn(true);
		IHistoryComposite hc = new HistoryComposite("crea");
		myDoc.createFeature(ptff, factories, t, hc);
		myDoc.getHistoryManager().addMemento(hc);
		Set<IFeature> expectedFeatures = t.getFeatures();
		expSize = 2;
		assertEquals(expSize, expectedFeatures.size());
		IFeature timed = t.getFeature("Timed Transition");
		assertNotNull(timed);
		IFeature pree = t.getFeature("Preemptive Transition");
		assertNotNull(pree);
		myDoc.removeFeatureFromNode(myPree, t, myDoc.getHistoryManager());
		Set<IFeature> tFeatures = t.getFeatures();
		expSize = 1;
		assertEquals(expSize, tFeatures.size());
		timed = t.getFeature("Timed Transition");
		assertNotNull(timed);
		//undoTest
		try {
			myDoc.getHistoryManager().undo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		Set<IFeature> actualFeatures = t.getFeatures();
		assertEquals(expectedFeatures.size(), actualFeatures.size());
		// redo test
		try {
			myDoc.getHistoryManager().redo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		actualFeatures = t.getFeatures();
		assertEquals(tFeatures.size(), actualFeatures.size());
	}
	
	
	
}
