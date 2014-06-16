package propertiesTest;

import static org.junit.Assert.*;

import java.awt.Point;
import java.beans.PropertyVetoException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import pNeditor.PNeditorDocTemplate;
import pNeditor.PNeditorDocument;
import pNeditor.PNeditorPlugin;
import petriNetDomain.TimedTransitionFeature;
import petriNetDomain.TimedTransitionFeature.EFTProperty;
import petriNetDomain.Transition;
import pnEditorApp.PNeditorApplication;

/**
 * This class tests the basic functioning of the class {@link EFTProperty} of the class {@link TimedTransitionFeature}
 * @author Benedetta
 *
 */

public class TimedEFTPropertyTest {

	private static PNeditorPlugin myPlugin;
	private static PNeditorDocument myDoc;
	private static PNeditorApplication mockedApp;
	private static Transition t;

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
		Point position = new Point (0,0);
		t = new Transition("transition0",
				position);
		myDoc.getSelectionModel().select(t, true);
	}

	@Test
	public void testEFTProperty() {
		TimedTransitionFeature myTimed = new TimedTransitionFeature(mockedApp);
		assertNotNull(myTimed);
		t.addFeature(myTimed);
		TimedTransitionFeature.EFTProperty myEft = myTimed.new EFTProperty();
		assertNotNull(myEft);
		String expectedName = "EFT Timed";
		assertEquals(expectedName, myEft.getName());
		String expectedCategory = "Timed";
		assertEquals(expectedCategory, myEft.getCategory());
		String expectedDisplayName = "EFT";
		assertEquals(expectedDisplayName, myEft.getDisplayName());
		Class expectedType = String.class;
		assertEquals(expectedType, myEft.getType());
		String expectedError = "";
		assertEquals(expectedError , myEft.getErrorMessage());
	}

	@Test
	public void testReadValue() {
		TimedTransitionFeature myTimed = new TimedTransitionFeature(mockedApp);
		assertNotNull(myTimed);
		t.addFeature(myTimed);
		TimedTransitionFeature.EFTProperty myEft = myTimed.new EFTProperty();
		assertNotNull(myEft);
		Double expectedEft = myTimed.getEFT();
		assertEquals(expectedEft, myEft.readValue());
		expectedEft = 3.2;
		myTimed.setEFT(expectedEft);
		assertEquals(expectedEft, myEft.readValue());
	}
	
	@Test
	public void testWriteValue() throws PropertyVetoException {
		TimedTransitionFeature myTimed = new TimedTransitionFeature(mockedApp);
		assertNotNull(myTimed);
		t.addFeature(myTimed);
		TimedTransitionFeature.EFTProperty myEft = myTimed.new EFTProperty();
		assertNotNull(myEft);
		myTimed.setLFT(4.5);
		String newValue = "3.4";
		myEft.writeValue(newValue);
		assertEquals(3.4, myEft.readValue());
	}

	@Test
	public void testGetErrorMessage() {
		TimedTransitionFeature myTimed = new TimedTransitionFeature(mockedApp);
		assertNotNull(myTimed);
		t.addFeature(myTimed);
		TimedTransitionFeature.EFTProperty myEft = myTimed.new EFTProperty();
		assertNotNull(myEft);
		assertEquals("",myEft.getErrorMessage());
	}

	@Test
	public void testSetGetName() {
		TimedTransitionFeature myTimed = new TimedTransitionFeature(mockedApp);
		assertNotNull(myTimed);
		t.addFeature(myTimed);
		TimedTransitionFeature.EFTProperty myEft = myTimed.new EFTProperty();
		assertNotNull(myEft);
		String expName = "EFT Timed";
		assertEquals(expName, myEft.getName());
		expName = "EFT";
		myEft.setName(expName);
		String actName = myEft.getName();
		assertEquals(expName, actName);
	}

	@Test
	public void testSetGetDisplayName() {
		TimedTransitionFeature myTimed = new TimedTransitionFeature(mockedApp);
		assertNotNull(myTimed);
		t.addFeature(myTimed);
		TimedTransitionFeature.EFTProperty myEft = myTimed.new EFTProperty();
		assertNotNull(myEft);
		String expDisplayName = "EFT";
		assertEquals(expDisplayName, myEft.getDisplayName());
		expDisplayName = "EFT Timed";
		myEft.setDisplayName(expDisplayName);
		String actName = myEft.getDisplayName();
		assertEquals(expDisplayName, actName);
	}

	@Test
	public void testSetGetType() {
		TimedTransitionFeature myTimed = new TimedTransitionFeature(mockedApp);
		assertNotNull(myTimed);
		t.addFeature(myTimed);
		TimedTransitionFeature.EFTProperty myEft = myTimed.new EFTProperty();
		assertNotNull(myEft);
		Class expType = String.class;
		assertEquals(expType, myEft.getType());
		expType = Integer.class;
		myEft.setType(Integer.class);
		Class actType = myEft.getType();
		assertEquals(expType, actType);
	}

	@Test
	public void testIsEditable() {
		TimedTransitionFeature myTimed = new TimedTransitionFeature(mockedApp);
		assertNotNull(myTimed);
		t.addFeature(myTimed);
		TimedTransitionFeature.EFTProperty myEft = myTimed.new EFTProperty();
		assertNotNull(myEft);
		assertTrue(myEft.isEditable());
	}

	@Test
	public void testSetEditable() {
		TimedTransitionFeature myTimed = new TimedTransitionFeature(mockedApp);
		assertNotNull(myTimed);
		t.addFeature(myTimed);
		TimedTransitionFeature.EFTProperty myEft = myTimed.new EFTProperty();
		assertNotNull(myEft);
		assertTrue(myEft.isEditable());
		myEft.setEditable(false);
		assertFalse(myEft.isEditable());
	}
}
