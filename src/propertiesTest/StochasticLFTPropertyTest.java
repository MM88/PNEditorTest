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
import petriNetDomain.StochasticTransitionFeature;
import petriNetDomain.Transition;
import petriNetDomain.StochasticTransitionFeature.LFTProperty;
import pnEditorApp.PNeditorApplication;


/**
 * This class tests the basic functioning of the class {@link LFTProperty} of the class {@link StochasticTransitionFeature}
 * @author Benedetta
 *
 */

public class StochasticLFTPropertyTest {

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
		StochasticTransitionFeature myStoc = new StochasticTransitionFeature(mockedApp);
		assertNotNull(myStoc);
		t.addFeature(myStoc);
		LFTProperty myLft = myStoc.new LFTProperty();
		assertNotNull(myLft);
		String expectedName = "LFT Stochastic";
		assertEquals(expectedName, myLft.getName());
		String expectedCategory = "Stochastic";
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
		StochasticTransitionFeature myStoc = new StochasticTransitionFeature(mockedApp);
		assertNotNull(myStoc);
		t.addFeature(myStoc);
		LFTProperty myLft = myStoc.new LFTProperty();
		assertNotNull(myLft);
		Double expectedEft = myStoc.getLFT();
		assertEquals(expectedEft, myLft.readValue());
		expectedEft = 3.2;
		myStoc.setLFT(expectedEft);
		assertEquals(expectedEft, myLft.readValue());
	}

	@Test
	public void testWriteValue() throws PropertyVetoException {
		StochasticTransitionFeature myStoc = new StochasticTransitionFeature(mockedApp);
		assertNotNull(myStoc);
		t.addFeature(myStoc);
		LFTProperty myLft = myStoc.new LFTProperty();
		assertNotNull(myLft);
		String newValue = "3.4";
		myLft.writeValue(newValue);
		assertEquals(3.4, myLft.readValue());
	}

	@Test
	public void testGetErrorMessage() {
		StochasticTransitionFeature myStoc = new StochasticTransitionFeature(mockedApp);
		assertNotNull(myStoc);
		t.addFeature(myStoc);
		LFTProperty myLft = myStoc.new LFTProperty();
		assertNotNull(myLft);
		assertEquals("",myLft.getErrorMessage());
	}

	@Test
	public void testSetGetName() {
		StochasticTransitionFeature myStoc = new StochasticTransitionFeature(mockedApp);
		assertNotNull(myStoc);
		t.addFeature(myStoc);
		LFTProperty myLft = myStoc.new LFTProperty();
		String expName = "LFT Stochastic";
		assertEquals(expName, myLft.getName());
		expName = "LFT";
		myLft.setName(expName);
		String actName = myLft.getName();
		assertEquals(expName, actName);
	}

	@Test
	public void testSetGetDisplayName() {
		StochasticTransitionFeature myStoc = new StochasticTransitionFeature(mockedApp);
		assertNotNull(myStoc);
		t.addFeature(myStoc);
		LFTProperty myLft = myStoc.new LFTProperty();
		String expDisplayName = "LFT";
		assertEquals(expDisplayName, myLft.getDisplayName());
		expDisplayName = "LFT Stochastic";
		myLft.setDisplayName(expDisplayName);
		String actName = myLft.getDisplayName();
		assertEquals(expDisplayName, actName);
	}

	@Test
	public void testSetGetType() {
		StochasticTransitionFeature myStoc = new StochasticTransitionFeature(mockedApp);
		assertNotNull(myStoc);
		t.addFeature(myStoc);
		LFTProperty myLft = myStoc.new LFTProperty();
		Class expType = String.class;
		assertEquals(expType, myLft.getType());
		expType = Integer.class;
		myLft.setType(Integer.class);
		Class actType = myLft.getType();
		assertEquals(expType, actType);
	}

	@Test
	public void testIsEditable() {
		StochasticTransitionFeature myStoc = new StochasticTransitionFeature(mockedApp);
		assertNotNull(myStoc);
		t.addFeature(myStoc);
		LFTProperty myLft = myStoc.new LFTProperty();
		assertTrue(myLft.isEditable());
	}

	@Test
	public void testSetEditable() {
		StochasticTransitionFeature myStoc = new StochasticTransitionFeature(mockedApp);
		assertNotNull(myStoc);
		t.addFeature(myStoc);
		LFTProperty myLft = myStoc.new LFTProperty();
		assertTrue(myLft.isEditable());
		myLft.setEditable(false);
		assertFalse(myLft.isEditable());
	}

}
