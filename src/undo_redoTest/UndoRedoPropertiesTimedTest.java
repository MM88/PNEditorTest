 package undo_redoTest;


import static org.junit.Assert.*;

import java.awt.Point;
import java.beans.PropertyVetoException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import framework.undoredo.HistoryException;

import pNeditor.PNeditorDocTemplate;
import pNeditor.PNeditorDocument;
import pNeditor.PNeditorPlugin;
import pNeditor.PNeditorView;
import petriNetDomain.FeaturePropertyAdapter;
import petriNetDomain.TimedTransitionFeature;
import petriNetDomain.TimedTransitionFeature.LTFProperty;
import petriNetDomain.Transition;
import pnEditorApp.PNeditorApplication;

/**
 * This class tests that:
 * the edit of a property of a Timed Transition Feature is undoable
 * if the edit of a property of a Timed Transition Feature is undone it is redoable
 * @author Benedetta
 *
 */


public class UndoRedoPropertiesTimedTest {
	
	private static PNeditorPlugin myPlugin;
	private static PNeditorDocument myDoc;
	private static PNeditorApplication mockedApp;
	
	
	@Before
	public  void setUp() throws Exception {
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
	
	@Test
	public void testUndoRedoEFToneTransition() {	
		Point position = new Point (0,0);
		Transition t = new Transition("transition0",
				position);
		TimedTransitionFeature myTimed = new TimedTransitionFeature(mockedApp);
		assertNotNull(myTimed);
		TimedTransitionFeature.EFTProperty myEft = myTimed.new EFTProperty();
		assertNotNull(myEft);
		t.addFeature(myTimed);
		Double dflEft = (double) 0;
		Double oldName = myTimed.getEFT();
		//check the default value
		assertEquals(dflEft, oldName);
		myTimed.setLFT(5.8);  // to not generate errors
		String newName = "3.2";
		//write the new value
		myDoc.getSelectionModel().select(t, true);
		FeaturePropertyAdapter fPa = new FeaturePropertyAdapter(mockedApp);
		fPa.addProperty(myEft);
		try {
			fPa.write((Object)newName);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		Double expectedValue = Double.parseDouble(newName);
		assertEquals(expectedValue, myTimed.getEFT());	
		try {
			myDoc.getHistoryManager().undo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		assertEquals(oldName,myTimed.getEFT());
		// redo test
		try {
			myDoc.getHistoryManager().redo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		assertEquals(expectedValue, myTimed.getEFT());
	}
	
	@Test
	public void testUndoRedoLTFOneTransition() {	
		Point position = new Point (0,0);
		Transition t = new Transition("transition0",
				position);
		TimedTransitionFeature myTimed = new TimedTransitionFeature(mockedApp);
		assertNotNull(myTimed);
		LTFProperty myLtf = myTimed.new LTFProperty();
		assertNotNull(myLtf);
		t.addFeature(myTimed);
		Double dflLtf = (double) 0;
		Double oldName = myTimed.getEFT();
		//check the default value
		assertEquals(dflLtf, oldName);
		String newName = "3.2";
		//write the new value
		myDoc.getSelectionModel().select(t, true);
		FeaturePropertyAdapter fPa = new FeaturePropertyAdapter(mockedApp);
		fPa.addProperty(myLtf);
		try {
			fPa.write((Object)newName);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		Double expectedValue = Double.parseDouble(newName);
		assertEquals(expectedValue, myTimed.getLFT());	
		try {
			myDoc.getHistoryManager().undo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		assertEquals(oldName,myTimed.getLFT());
		// redo test
		try {
			myDoc.getHistoryManager().redo(null);
		} catch (HistoryException e) {
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
		TimedTransitionFeature myTimed = new TimedTransitionFeature(mockedApp);
		assertNotNull(myTimed);
		TimedTransitionFeature myTimed1 = new TimedTransitionFeature(mockedApp);
		assertNotNull(myTimed1);
		TimedTransitionFeature.EFTProperty myEft = myTimed.new EFTProperty();
		assertNotNull(myEft);
		TimedTransitionFeature.EFTProperty myEft1 = myTimed1.new EFTProperty();
		assertNotNull(myEft1);
		t.addFeature(myTimed);
		t1.addFeature(myTimed1);
		Double oldName = myTimed.getEFT();
		Double oldName1 = myTimed1.getEFT();
		myTimed.setLFT(5.8);  // to not generate errors
		myTimed1.setLFT(6.2);
		myDoc.getSelectionModel().select(t, true);
		myDoc.getSelectionModel().select(t1, true);
		String newName = "3.2";
		//write the new value
		FeaturePropertyAdapter fPa = new FeaturePropertyAdapter(mockedApp);
		fPa.addProperty(myEft);
		fPa.addProperty(myEft1);
		try {
			fPa.write((Object)newName);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		Double expectedValue = Double.parseDouble(newName);
		assertEquals(expectedValue, myTimed.getEFT());
		assertEquals(expectedValue, myTimed1.getEFT());
		try {
			myDoc.getHistoryManager().undo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		assertEquals(oldName,myTimed.getEFT());
		assertEquals(oldName1,myTimed1.getEFT());
		// redo test
		try {
			myDoc.getHistoryManager().redo(null);
		} catch (HistoryException e) {
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
		TimedTransitionFeature myTimed = new TimedTransitionFeature(mockedApp);
		assertNotNull(myTimed);
		TimedTransitionFeature myTimed1 = new TimedTransitionFeature(mockedApp);
		assertNotNull(myTimed1);
		LTFProperty myLtf = myTimed.new LTFProperty();
		assertNotNull(myLtf);
		LTFProperty myLtf1 = myTimed1.new LTFProperty();
		assertNotNull(myLtf1);
		t.addFeature(myTimed);
		t1.addFeature(myTimed1);
		Double oldName = myTimed.getLFT();
		Double oldName1 = myTimed1.getLFT();
		myDoc.getSelectionModel().select(t, true);
		myDoc.getSelectionModel().select(t1, true);
		String newName = "3.2";
		//write the new value
		FeaturePropertyAdapter fPa = new FeaturePropertyAdapter(mockedApp);
		fPa.addProperty(myLtf);
		fPa.addProperty(myLtf1);
		try {
			fPa.write((Object)newName);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		Double expectedValue = Double.parseDouble(newName);
		assertEquals(expectedValue, myTimed.getLFT());
		assertEquals(expectedValue, myTimed1.getLFT());
		try {
			myDoc.getHistoryManager().undo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		assertEquals(oldName,myTimed.getLFT());
		assertEquals(oldName1,myTimed1.getLFT());
		// redo test
		try {
			myDoc.getHistoryManager().redo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		assertEquals(expectedValue, myTimed.getLFT());
		assertEquals(expectedValue, myTimed1.getLFT());
	}
	
	

}
