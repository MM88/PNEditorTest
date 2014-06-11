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
import petriNetDomain.TimedTransitionFeature.LTFProperty;
import petriNetDomain.Transition;
import pnEditorApp.PNeditorApplication;

/**
 * This class tests the basic functioning of the class {@link LTFProperty} of the class {@link TimedTransitionFeature}
 * @author Benedetta
 *
 */

public class TimedLTFPropertyTest {

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
	public void testLTFProperty() {
		TimedTransitionFeature myTimed = new TimedTransitionFeature();
		assertNotNull(myTimed);
		t.addFeature(myTimed);
		TimedTransitionFeature.LTFProperty myLft = myTimed.new LTFProperty(mockedApp);
		assertNotNull(myLft);
		String expectedName = "LFT Timed";
		assertEquals(expectedName, myLft.getName());
		String expectedCategory = "Timed";
		assertEquals(expectedCategory, myLft.getCategory());
		String expectedDisplayName = "LFT";
		assertEquals(expectedDisplayName, myLft.getDisplayName());
		Class expectedType = String.class;
		assertEquals(expectedType, myLft.getType());
		String expectedError = "";
		assertEquals(expectedError , myLft.getErrorMessage());
		assertTrue(myLft.isEditable());
	}

	@Test
	public void testReadValue() {
		TimedTransitionFeature myTimed = new TimedTransitionFeature();
		assertNotNull(myTimed);
		t.addFeature(myTimed);
		TimedTransitionFeature.LTFProperty myLft = myTimed.new LTFProperty(mockedApp);
		assertNotNull(myLft);
		Double expectedEft = myTimed.getLFT();
		assertEquals(expectedEft, myLft.readValue());
		expectedEft = 3.2;
		myTimed.setLFT(expectedEft);
		assertEquals(expectedEft, myLft.readValue());
	}

	@Test
	public void testWriteValue() throws PropertyVetoException {
		TimedTransitionFeature myTimed = new TimedTransitionFeature();
		assertNotNull(myTimed);
		t.addFeature(myTimed);
		TimedTransitionFeature.LTFProperty myLft = myTimed.new LTFProperty(mockedApp);
		assertNotNull(myLft);
		String newValue = "3.4";
		myLft.writeValue(newValue);
		assertEquals(3.4, myLft.readValue());
	}

	@Test
	public void testGetErrorMessage() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetGetName() {
		fail("Not yet implemented");
	}

	

	@Test
	public void testSetGetDisplayName() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetDisplayName() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetGetType() {
		fail("Not yet implemented");
	}

	

	@Test
	public void testIsEditable() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetEditable() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetGetCategory() {
		fail("Not yet implemented");
	}



	@Test
	public void testGetEditorClass() {
		fail("Not yet implemented");
	}

}
