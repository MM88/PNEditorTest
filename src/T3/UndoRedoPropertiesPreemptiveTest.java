package T3;


import static org.junit.Assert.*;

import java.awt.Point;
import java.beans.PropertyVetoException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import framework.undoredo.HistoryException;

import pNeditor.PNeditorDocTemplate;
import pNeditor.PNeditorDocument;
import pNeditor.PNeditorPlugin;
import petriNetDomain.FeaturePropertyAdapter;
import petriNetDomain.PreemptiveTransitionFeature;
import petriNetDomain.PreemptiveTransitionFeature.ResourcePriorityProperty;
import petriNetDomain.PreemptiveTransitionFeature.ResourcesProperty;
import petriNetDomain.Transition;
import pnEditorApp.PNeditorApplication;

/**
 * This class tests that:
 * the edit of a property of a Preemptive Transition Feature is undoable
 * if the edit of a property of a Preemptive Transition Feature is undone it is redoable
 * @author Benedetta
 *
 */

public class UndoRedoPropertiesPreemptiveTest {

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
	public void testUndoRedoResourcesProperty() {	
		Point position = new Point (0,0);
		Transition t = new Transition("transition0",
				position);
		myDoc.getSelectionModel().select(t, true);
		PreemptiveTransitionFeature myPree = new PreemptiveTransitionFeature(mockedApp);
		assertNotNull(myPree);
		ResourcesProperty myRp = (ResourcesProperty) myPree.getProperties().get(0);
		assertNotNull(myRp);
		t.addFeature(myPree);
		int uExpSizePree = myPree.getResources().size();
		int uExpSizeMyDoc = myDoc.getResources().size();
		int expSize = 0;
		assertEquals(expSize, uExpSizePree);
		assertEquals(expSize, uExpSizeMyDoc);
		expSize = 1;
		assertEquals(expSize, myPree.getProperties().size());		
		//write the new value
		String newResource = "cpu";
		FeaturePropertyAdapter fPa = new FeaturePropertyAdapter(mockedApp);
		fPa.addProperty(myRp);
		try {
			fPa.write((Object)newResource);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		int rExpSize = myPree.getResources().size();
		int rExpSizeDoc = myDoc.getResources().size();
		expSize = 1;
		assertEquals(expSize, rExpSize);
		assertEquals(expSize, rExpSizeDoc);
		expSize = 2;
		assertEquals(expSize, myPree.getProperties().size());
		try {
			myDoc.getHistoryManager().undo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		expSize = 0;
		assertEquals(expSize, myPree.getResources().size());
		assertEquals(expSize, myDoc.getResources().size());
		expSize = 1;
		assertEquals(expSize, myPree.getProperties().size());
		//redo test
		try {
			myDoc.getHistoryManager().redo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		rExpSize =  myPree.getResources().size();
		rExpSizeDoc =  myDoc.getResources().size();
		expSize = 1;
		assertEquals(expSize, rExpSize);
		assertEquals(expSize, rExpSizeDoc);
		expSize = 2;
		assertEquals(expSize, myPree.getProperties().size());
	}
	
	@Test
	public void testUndoRedoResourcesPropertyTwoTransition() {	
		Point position = new Point (0,0);
		Transition t = new Transition("transition0",
				position);
		Point position1 = new Point (10,10);
		Transition t1 = new Transition("transition1",
				position1);
		myDoc.getSelectionModel().select(t, true);
		myDoc.getSelectionModel().select(t1, true);
		PreemptiveTransitionFeature myPree = new PreemptiveTransitionFeature(mockedApp);
		assertNotNull(myPree);
		PreemptiveTransitionFeature myPree1 = new PreemptiveTransitionFeature(mockedApp);
		assertNotNull(myPree1);
		ResourcesProperty myRp = (ResourcesProperty) myPree.getProperties().get(0);
		assertNotNull(myRp);
		ResourcesProperty myRp1 = (ResourcesProperty) myPree1.getProperties().get(0);
		assertNotNull(myRp);
		assertNotNull(myRp1);
		t.addFeature(myPree);
		t1.addFeature(myPree1);
		int uExpSizePree = myPree.getResources().size();		
		int uExpSizePree1 = myPree1.getResources().size();
		int uExpSizeMyDoc = myDoc.getResources().size();
		int expSize = 0;
		assertEquals(expSize, uExpSizePree);
		assertEquals(expSize, uExpSizePree1);
		assertEquals(expSize, uExpSizeMyDoc);
		expSize = 1;
		assertEquals(expSize, myPree.getProperties().size());
		assertEquals(expSize, myPree1.getProperties().size());
		//write the new value
		String newResource = "cpu";
		FeaturePropertyAdapter fPa = new FeaturePropertyAdapter(mockedApp);
		fPa.addProperty(myRp);
		fPa.addProperty(myRp1);
		try {
			fPa.write((Object)newResource);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		int rExpSize = myPree.getResources().size();
		int rExpSize1 = myPree1.getResources().size();
		int rExpSizeDoc = myDoc.getResources().size();
		assertEquals(expSize, rExpSize);
		assertEquals(expSize, rExpSize1);
		assertEquals(expSize, rExpSizeDoc);
		expSize = 2;
		assertEquals(expSize, myPree.getProperties().size());
		assertEquals(expSize, myPree1.getProperties().size());
		try {
			myDoc.getHistoryManager().undo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		expSize = 0;
		assertEquals(expSize, myPree.getResources().size());
		assertEquals(expSize, myPree1.getResources().size());
		assertEquals(expSize, myDoc.getResources().size());
		expSize = 1;
		assertEquals(expSize, myPree.getProperties().size());
		assertEquals(expSize, myPree1.getProperties().size());
		//redo test
		try {
			myDoc.getHistoryManager().redo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		rExpSize =  myPree.getResources().size();
		rExpSize1 = myPree1.getResources().size();
		rExpSizeDoc =  myDoc.getResources().size();
		assertEquals(expSize, rExpSize);
		assertEquals(expSize, rExpSize1);
		assertEquals(expSize, rExpSizeDoc);
		expSize = 2;
		assertEquals(expSize, myPree.getProperties().size());
		assertEquals(expSize, myPree1.getProperties().size());
		
	}
	
	@Test
	public void testUndoRedoResourcesPriorityProperty() {	
		Point position = new Point (0,0);
		Transition t = new Transition("transition0",
				position);
		myDoc.getSelectionModel().select(t, true);
		PreemptiveTransitionFeature myPree = new PreemptiveTransitionFeature(mockedApp);
		assertNotNull(myPree);
		ResourcesProperty myRp = (ResourcesProperty) myPree.getProperties().get(0);
		t.addFeature(myPree);		
		String newResource = "cpu";
		FeaturePropertyAdapter fPa = new FeaturePropertyAdapter(mockedApp);
		fPa.addProperty(myRp);
		try {
			fPa.write((Object)newResource);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		ResourcePriorityProperty myRpp = (ResourcePriorityProperty) myPree.getProperties().get(myPree.getProperties().size() -1);
		fPa = new FeaturePropertyAdapter(mockedApp);
		fPa.addProperty(myRpp);
		String rexpValue = "3";
		try {
			fPa.write((Object)rexpValue);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		int expValue = 3;
		assertEquals(expValue, myRpp.readValue());
		try {
			myDoc.getHistoryManager().undo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		expValue = 0;
		assertEquals(expValue, myRpp.readValue());
		//redo test
		try {
			myDoc.getHistoryManager().redo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		expValue = 3;
		assertEquals(expValue, myRpp.readValue());
	}
	
	@Test
	public void testUndoRedoResourcesPriorityPropertyTwoTransition() {	
		Point position = new Point (0,0);
		Transition t = new Transition("transition0",
				position);
		Point position1 = new Point (10,10);
		Transition t1 = new Transition("transition1",
				position1);
		myDoc.getSelectionModel().select(t, true);
		myDoc.getSelectionModel().select(t1, true);
		PreemptiveTransitionFeature myPree = new PreemptiveTransitionFeature(mockedApp);
		assertNotNull(myPree);
		PreemptiveTransitionFeature myPree1 = new PreemptiveTransitionFeature(mockedApp);
		assertNotNull(myPree1);
		ResourcesProperty myRp = (ResourcesProperty) myPree.getProperties().get(0);
		ResourcesProperty myRp1 = (ResourcesProperty) myPree1.getProperties().get(0);
		t.addFeature(myPree);	
		t1.addFeature(myPree1);	
		String newResource = "cpu";
		FeaturePropertyAdapter fPa = new FeaturePropertyAdapter(mockedApp);
		fPa.addProperty(myRp);
		fPa.addProperty(myRp1);
		try {
			fPa.write((Object)newResource);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		ResourcePriorityProperty myRpp = (ResourcePriorityProperty) myPree.getProperties().get(myPree.getProperties().size() -1);
		ResourcePriorityProperty myRpp1 = (ResourcePriorityProperty) myPree1.getProperties().get(myPree.getProperties().size() -1);
		fPa = new FeaturePropertyAdapter(mockedApp);
		fPa.addProperty(myRpp);
		fPa.addProperty(myRpp1);
		String rexpValue = "3";
		try {
			fPa.write((Object)rexpValue);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		int expValue = 3;
		assertEquals(expValue, myRpp.readValue());
		assertEquals(expValue, myRpp1.readValue());
		try {
			myDoc.getHistoryManager().undo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		expValue = 0;
		assertEquals(expValue, myRpp.readValue());
		assertEquals(expValue, myRpp1.readValue());
		//redo test
		try {
			myDoc.getHistoryManager().redo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		expValue = 3;
		assertEquals(expValue, myRpp.readValue());
		assertEquals(expValue, myRpp1.readValue());
	}
	
	
	
	

}
