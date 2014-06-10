 package undo_redoTest;


import static org.junit.Assert.*;

import java.awt.Point;
import java.beans.PropertyVetoException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.*;

import framework.undoredo.HistoryException;


import pNeditor.PNeditorDocTemplate;
import pNeditor.PNeditorDocument;
import pNeditor.PNeditorPlugin;
import pNeditor.PNeditorView;
import petriNetDomain.FeaturePropertyAdapter;
import petriNetDomain.PNelement;
import petriNetDomain.TimedTransitionFeature;
import petriNetDomain.TimedTransitionFeature.LTFProperty;
import petriNetDomain.Transition;
import pnEditorApp.PNeditorApplication;

public class UndoRedoPropertiesTimedTest {
	
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
		myDoc.getSelectionModel().clearSelection();
	}
	
	@Test
	public void testUndoRedoEFToneTransition() {	
		Point position = new Point (0,0);
		Transition t = new Transition("transition0",
				position);
		TimedTransitionFeature myTimed = new TimedTransitionFeature();
		assertNotNull(myTimed);
		TimedTransitionFeature.EFTProperty myEft = myTimed.new EFTProperty(mockedApp);
		assertNotNull(myEft);
		t.addFeature(myTimed);
		Double dflEft = (double) 0;
		Double oldName = myTimed.getEFT();
		//controllo il valore di default
		assertEquals(dflEft, oldName);
		myTimed.setLFT(5.8);  // per non generare errori
		String newName = "3.2";
		//scrivo il nuovo valore
		myDoc.getSelectionModel().select(t, true);
		FeaturePropertyAdapter fPa = new FeaturePropertyAdapter(mockedApp);
		fPa.addProperty(myEft);
		try {
			fPa.write((Object)newName);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Double expectedValue = Double.parseDouble(newName);
		assertEquals(expectedValue, myTimed.getEFT());	
		try {
			myDoc.getHistoryManager().undo(null);
		} catch (HistoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(oldName,myTimed.getEFT());
		// redo test
		try {
			myDoc.getHistoryManager().redo(null);
		} catch (HistoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(expectedValue, myTimed.getEFT());
	}
	
	@Test
	public void testUndoRedoLTFOneTransition() {	
		Point position = new Point (0,0);
		Transition t = new Transition("transition0",
				position);
		TimedTransitionFeature myTimed = new TimedTransitionFeature();
		assertNotNull(myTimed);
		LTFProperty myLtf = myTimed.new LTFProperty(mockedApp);
		assertNotNull(myLtf);
		t.addFeature(myTimed);
		Double dflLtf = (double) 0;
		Double oldName = myTimed.getEFT();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Double expectedValue = Double.parseDouble(newName);
		assertEquals(expectedValue, myTimed.getLFT());	
		try {
			myDoc.getHistoryManager().undo(null);
		} catch (HistoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(oldName,myTimed.getLFT());
		// redo test
		try {
			myDoc.getHistoryManager().redo(null);
		} catch (HistoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(expectedValue, myTimed.getLFT());
	}
	
	@Test
	public void testUndoRedoEFTtwoTransition() {	
		Point position = new Point (0,0);
		Point p1 = new Point (10,10);
		Transition t = new Transition("transition0",
				position);
		Transition t1 = new Transition("transition1",
				p1);
		TimedTransitionFeature myTimed = new TimedTransitionFeature();
		assertNotNull(myTimed);
		TimedTransitionFeature myTimed1 = new TimedTransitionFeature();
		assertNotNull(myTimed1);
		TimedTransitionFeature.EFTProperty myEft = myTimed.new EFTProperty(mockedApp);
		assertNotNull(myEft);
		TimedTransitionFeature.EFTProperty myEft1 = myTimed1.new EFTProperty(mockedApp);
		assertNotNull(myEft1);
		t.addFeature(myTimed);
		t1.addFeature(myTimed1);
		Double oldName = myTimed.getEFT();
		Double oldName1 = myTimed1.getEFT();
		myTimed.setLFT(5.8);  // per non generare errori
		myTimed1.setLFT(6.2);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Double expectedValue = Double.parseDouble(newName);
		assertEquals(expectedValue, myTimed.getEFT());
		assertEquals(expectedValue, myTimed1.getEFT());
		try {
			myDoc.getHistoryManager().undo(null);
		} catch (HistoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(oldName,myTimed.getEFT());
		assertEquals(oldName1,myTimed1.getEFT());
		// redo test
		try {
			myDoc.getHistoryManager().redo(null);
		} catch (HistoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(expectedValue, myTimed.getEFT());
		assertEquals(expectedValue, myTimed1.getEFT());
	}
	
	
	@Test
	public void testUndoRedoLFTtwoTransition() {	
		Point position = new Point (0,0);
		Point p1 = new Point (10,10);
		Transition t = new Transition("transition0",
				position);
		Transition t1 = new Transition("transition1",
				p1);
		TimedTransitionFeature myTimed = new TimedTransitionFeature();
		assertNotNull(myTimed);
		TimedTransitionFeature myTimed1 = new TimedTransitionFeature();
		assertNotNull(myTimed1);
		LTFProperty myLtf = myTimed.new LTFProperty(mockedApp);
		assertNotNull(myLtf);
		LTFProperty myLtf1 = myTimed1.new LTFProperty(mockedApp);
		assertNotNull(myLtf1);
		t.addFeature(myTimed);
		t1.addFeature(myTimed1);
		Double oldName = myTimed.getLFT();
		Double oldName1 = myTimed1.getLFT();
//		myTimed.setLFT(5.8);  // per non generare errori
//		myTimed1.setLFT(6.2);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Double expectedValue = Double.parseDouble(newName);
		assertEquals(expectedValue, myTimed.getLFT());
		assertEquals(expectedValue, myTimed1.getLFT());
		try {
			myDoc.getHistoryManager().undo(null);
		} catch (HistoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(oldName,myTimed.getLFT());
		assertEquals(oldName1,myTimed1.getLFT());
		// redo test
		try {
			myDoc.getHistoryManager().redo(null);
		} catch (HistoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(expectedValue, myTimed.getLFT());
		assertEquals(expectedValue, myTimed1.getLFT());
	}
	
	

}
