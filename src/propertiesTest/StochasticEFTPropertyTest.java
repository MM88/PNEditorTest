package propertiesTest;

import static org.junit.Assert.*;

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
import petriNetDomain.StochasticTransitionFeature;
import petriNetDomain.TimedTransitionFeature;
import petriNetDomain.Transition;
import petriNetDomain.TimedTransitionFeature.EFTProperty;
import pnEditorApp.PNeditorApplication;

public class StochasticEFTPropertyTest {

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
		StochasticTransitionFeature myStoc = new StochasticTransitionFeature();
		assertNotNull(myStoc);
		t.addFeature(myStoc);
		StochasticTransitionFeature.EFTProperty myEft = myStoc.new EFTProperty(mockedApp);
		assertNotNull(myEft);
		String expectedName = "EFT Stochastic";
		assertEquals(expectedName, myEft.getName());
		String expectedCategory = "Stochastic";
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
		StochasticTransitionFeature myStoc = new StochasticTransitionFeature();
		assertNotNull(myStoc);
		t.addFeature(myStoc);
		StochasticTransitionFeature.EFTProperty myEft = myStoc.new EFTProperty(mockedApp);
		assertNotNull(myEft);
		Double expectedEft = myStoc.getEFT();
		assertEquals(expectedEft, myEft.readValue());
	}

	@Test
	public void testWriteValue() throws PropertyVetoException {
		StochasticTransitionFeature myStoc = new StochasticTransitionFeature();
		assertNotNull(myStoc);
		t.addFeature(myStoc);
		StochasticTransitionFeature.EFTProperty myEft = myStoc.new EFTProperty(mockedApp);
		assertNotNull(myEft);
		myStoc.setLFT(4.5);
		String newValue = "3.4";
		myEft.writeValue(newValue);
		assertEquals(3.4, myEft.readValue());
	}

	@Test
	public void testGetErrorMessage() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetName() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDisplayName() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetDisplayName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetType() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetType() {
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
	public void testGetCategory() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetCategory() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetEditorClass() {
		fail("Not yet implemented");
	}

}
