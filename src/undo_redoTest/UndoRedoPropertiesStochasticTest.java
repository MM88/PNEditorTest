package undo_redoTest;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.Point;
import java.beans.PropertyVetoException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import pNeditor.PNeditorDocTemplate;
import pNeditor.PNeditorDocument;
import pNeditor.PNeditorPlugin;
import pNeditor.PNeditorView;
import petriNetDomain.FeaturePropertyAdapter;
import petriNetDomain.StochasticTransitionFeature;
import petriNetDomain.StochasticTransitionFeature.FdtProperty;
import petriNetDomain.StochasticTransitionFeature.LFTProperty;
import petriNetDomain.TimedTransitionFeature;
import petriNetDomain.Transition;
import petriNetDomain.TimedTransitionFeature.EFTProperty;
import petriNetDomain.TimedTransitionFeature.LTFProperty;
import pnEditorApp.PNeditorApplication;
import framework.undoredo.HistoryException;

public class UndoRedoPropertiesStochasticTest {

	private static PNeditorPlugin myPlugin;
	private static PNeditorDocument myDoc;
	private static PNeditorApplication mockedApp;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		myPlugin = new PNeditorPlugin();
		myPlugin.initClipboard();
		myDoc = new PNeditorDocument();
		assertNotNull(myDoc);
		PNeditorDocTemplate dt = new PNeditorDocTemplate(myPlugin);
		myDoc.setDocTemplate(dt);
		PNeditorView view = new PNeditorView();
		view.setDocument(myDoc);
		view.initializeView(null, myDoc);
		mockedApp = Mockito.mock(PNeditorApplication.class);	
		Mockito.when(mockedApp.getActiveDocument()).thenReturn(myDoc);
		
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
	public void testUndoRedoEFToneTransition() {	
		Point position = new Point (0,0);
		Transition t = new Transition("transition0",
				position);
		StochasticTransitionFeature myStoc = new StochasticTransitionFeature();
		assertNotNull(myStoc);
		StochasticTransitionFeature.EFTProperty myEft = myStoc.new EFTProperty(mockedApp);
		assertNotNull(myEft);
		t.addFeature(myStoc);
		Double dflEft = (double) 0;
		Double oldName = myStoc.getEFT();
		//controllo il valore di default
		assertEquals(dflEft, oldName);
		myStoc.setLFT(5.8);  // per non generare errori
		String newName = "3.2";
		//scrivo il nuovo valore
		myDoc.getSelectionModel().select(t, true);
		FeaturePropertyAdapter fPa = new FeaturePropertyAdapter(mockedApp);
		fPa.addProperty(myEft);
		try {
			fPa.write((Object)newName);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		Double expectedValue = Double.parseDouble(newName);
		assertEquals(expectedValue, myStoc.getEFT());	
		try {
			myDoc.getHistoryManager().undo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		assertEquals(oldName,myStoc.getEFT());
		// redo test
		try {
			myDoc.getHistoryManager().redo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		assertEquals(expectedValue, myStoc.getEFT());
	}
	
	@Test
	public void testUndoRedoLTFOneTransition() {	
		Point position = new Point (0,0);
		Transition t = new Transition("transition0",
				position);
		StochasticTransitionFeature myStoc = new StochasticTransitionFeature();
		assertNotNull(myStoc);
		LFTProperty myLtf = myStoc.new LFTProperty(mockedApp);
		assertNotNull(myLtf);
		t.addFeature(myStoc);
		Double dflLtf = (double) 0;
		Double oldName = myStoc.getEFT();
		//controllo il valore di default
		assertEquals(dflLtf, oldName);
		String newName = "3.2";
		//scrivo il nuovo valore
		myDoc.getSelectionModel().select(t, true);
		FeaturePropertyAdapter fPa = new FeaturePropertyAdapter(mockedApp);
		fPa.addProperty(myLtf);
		try {
			fPa.write((Object)newName);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		Double expectedValue = Double.parseDouble(newName);
		assertEquals(expectedValue, myStoc.getLFT());	
		try {
			myDoc.getHistoryManager().undo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		assertEquals(oldName,myStoc.getLFT());
		// redo test
		try {
			myDoc.getHistoryManager().redo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		assertEquals(expectedValue, myStoc.getLFT());
	}
	
	@Test
	public void testUndoRedoEFTtwoTransition() {	
		Point position = new Point (0,0);
		Point p1 = new Point (10,10);
		Transition t = new Transition("transition0",
				position);
		Transition t1 = new Transition("transition1",
				p1);
		StochasticTransitionFeature myStoc = new StochasticTransitionFeature();
		assertNotNull(myStoc);
		StochasticTransitionFeature myStoc1 = new StochasticTransitionFeature();
		assertNotNull(myStoc1);
		StochasticTransitionFeature.EFTProperty myEft = myStoc.new EFTProperty(mockedApp);
		assertNotNull(myEft);
		StochasticTransitionFeature.EFTProperty myEft1 = myStoc1.new EFTProperty(mockedApp);
		assertNotNull(myEft1);
		t.addFeature(myStoc);
		t1.addFeature(myStoc1);
		Double oldName = myStoc.getEFT();
		Double oldName1 = myStoc1.getEFT();
		myStoc.setLFT(5.8);  // per non generare errori
		myStoc1.setLFT(6.2);
		myDoc.getSelectionModel().select(t, true);
		myDoc.getSelectionModel().select(t1, true);
		String newName = "3.2";
		//scrivo il nuovo valore
		FeaturePropertyAdapter fPa = new FeaturePropertyAdapter(mockedApp);
		fPa.addProperty(myEft);
		fPa.addProperty(myEft1);
		try {
			fPa.write((Object)newName);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		Double expectedValue = Double.parseDouble(newName);
		assertEquals(expectedValue, myStoc.getEFT());
		assertEquals(expectedValue, myStoc1.getEFT());
		try {
			myDoc.getHistoryManager().undo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		assertEquals(oldName,myStoc.getEFT());
		assertEquals(oldName1,myStoc1.getEFT());
		// redo test
		try {
			myDoc.getHistoryManager().redo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		assertEquals(expectedValue, myStoc.getEFT());
		assertEquals(expectedValue, myStoc1.getEFT());
	}
	
	
	@Test
	public void testUndoRedoLFTtwoTransition() {	
		Point position = new Point (0,0);
		Point p1 = new Point (10,10);
		Transition t = new Transition("transition0",
				position);
		Transition t1 = new Transition("transition1",
				p1);
		StochasticTransitionFeature myStoc = new StochasticTransitionFeature();
		assertNotNull(myStoc);
		StochasticTransitionFeature myStoc1 = new StochasticTransitionFeature();
		assertNotNull(myStoc1);
		StochasticTransitionFeature.LFTProperty myLtf = myStoc.new LFTProperty(mockedApp);
		assertNotNull(myLtf);
		StochasticTransitionFeature.LFTProperty myLtf1 = myStoc1.new LFTProperty(mockedApp);
		assertNotNull(myLtf1);
		t.addFeature(myStoc);
		t1.addFeature(myStoc1);
		Double oldName = myStoc.getLFT();
		Double oldName1 = myStoc1.getLFT();
		myDoc.getSelectionModel().select(t, true);
		myDoc.getSelectionModel().select(t1, true);
		String newName = "3.2";
		//scrivo il nuovo valore
		FeaturePropertyAdapter fPa = new FeaturePropertyAdapter(mockedApp);
		fPa.addProperty(myLtf);
		fPa.addProperty(myLtf1);
		try {
			fPa.write((Object)newName);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		Double expectedValue = Double.parseDouble(newName);
		assertEquals(expectedValue, myStoc.getLFT());
		assertEquals(expectedValue, myStoc1.getLFT());
		try {
			myDoc.getHistoryManager().undo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		assertEquals(oldName,myStoc.getLFT());
		assertEquals(oldName1,myStoc1.getLFT());
		// redo test
		try {
			myDoc.getHistoryManager().redo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		assertEquals(expectedValue, myStoc.getLFT());
		assertEquals(expectedValue, myStoc1.getLFT());
	}

	@Test
	public void testUndoRedoFdtOneTransition() {	
		Point position = new Point (0,0);
		Transition t = new Transition("transition0", position);
		StochasticTransitionFeature myStoc = new StochasticTransitionFeature();
		assertNotNull(myStoc);
		FdtProperty myFdt = myStoc.new FdtProperty(mockedApp);
		assertNotNull(myFdt);
		t.addFeature(myStoc);
		String dflFdt = "";
		String oldValue = myStoc.getFdt();
		//controllo il valore di default
		assertEquals(dflFdt, oldValue);
		String expectedValue = "expolinomial";
		//scrivo il nuovo valore
		myDoc.getSelectionModel().select(t, true);
		FeaturePropertyAdapter fPa = new FeaturePropertyAdapter(mockedApp);
		fPa.addProperty(myFdt);
		try {
			fPa.write((Object)expectedValue);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		assertEquals(expectedValue, myStoc.getFdt());	
		try {
			myDoc.getHistoryManager().undo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		assertEquals(oldValue,myStoc.getFdt());
		// redo test
		try {
			myDoc.getHistoryManager().redo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		assertEquals(expectedValue, myStoc.getFdt());
	}
	
	@Test
	public void testUndoRedoFdtTwoTransition() {	
		Point position = new Point (0,0);
		Point p1 = new Point (10,10);
		Transition t = new Transition("transition0", position);
		Transition t1 = new Transition("transition1", p1);
		StochasticTransitionFeature myStoc = new StochasticTransitionFeature();
		assertNotNull(myStoc);
		StochasticTransitionFeature myStoc1 = new StochasticTransitionFeature();
		assertNotNull(myStoc1);
		FdtProperty myFdt = myStoc.new FdtProperty(mockedApp);
		assertNotNull(myFdt);
		FdtProperty myFdt1 = myStoc1.new FdtProperty(mockedApp);
		assertNotNull(myFdt1);
		t.addFeature(myStoc);
		t1.addFeature(myStoc1);
		String oldValue = myStoc.getFdt();
		String oldValue1 = myStoc1.getFdt();
		myDoc.getSelectionModel().select(t, true);
		myDoc.getSelectionModel().select(t1, true);
		String expectedValue = "expolinomial";
		//scrivo il nuovo valore
		FeaturePropertyAdapter fPa = new FeaturePropertyAdapter(mockedApp);
		fPa.addProperty(myFdt);
		fPa.addProperty(myFdt1);
		try {
			fPa.write((Object)expectedValue);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		assertEquals(expectedValue, myStoc.getFdt());
		assertEquals(expectedValue, myStoc1.getFdt());
		try {
			myDoc.getHistoryManager().undo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		assertEquals(oldValue,myStoc.getFdt());
		assertEquals(oldValue1,myStoc1.getFdt());
		// redo test
		try {
			myDoc.getHistoryManager().redo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		assertEquals(expectedValue, myStoc.getFdt());
		assertEquals(expectedValue, myStoc1.getFdt());
	}
	
	
	
	
	
	
	
	
	
}
