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
import petriNetDomain.StochasticTransitionFeature.FdtProperty;
import petriNetDomain.Transition;
import pnEditorApp.PNeditorApplication;

/**
 * This class tests the basic functioning of the class {@link FdtProperty}
 * @author Benedetta
 *
 */

public class FdtPropertyTest {

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
	public void testFdtProperty() {
		StochasticTransitionFeature myStoc = new StochasticTransitionFeature(mockedApp);
		assertNotNull(myStoc);
		t.addFeature(myStoc);
		StochasticTransitionFeature.FdtProperty myFdt = myStoc.new FdtProperty();
		assertNotNull(myFdt);
		String expectedName = "Fdt Stochastic";
		assertEquals(expectedName, myFdt.getName());
		String expectedCategory = "Stochastic";
		assertEquals(expectedCategory, myFdt.getCategory());
		String expectedDisplayName = "Fdt";
		assertEquals(expectedDisplayName, myFdt.getDisplayName());
		Class expectedType = String.class;
		assertEquals(expectedType, myFdt.getType());
		String expectedError = null;
		assertEquals(expectedError, myFdt.getErrorMessage());
	}

	@Test
	public void testReadValue() {
		StochasticTransitionFeature myStoc = new StochasticTransitionFeature(mockedApp);
		assertNotNull(myStoc);
		t.addFeature(myStoc);
		StochasticTransitionFeature.FdtProperty myFdt = myStoc.new FdtProperty();
		assertNotNull(myFdt);
		String expectedFdt = myStoc.getFdt();
		assertEquals(expectedFdt, myFdt.readValue());
		expectedFdt = "uniform";
		myStoc.setFdt(expectedFdt);
		assertEquals(expectedFdt, myFdt.readValue());
	}

	@Test
	public void testWriteValue() throws PropertyVetoException {
		StochasticTransitionFeature myStoc = new StochasticTransitionFeature(mockedApp);
		assertNotNull(myStoc);
		t.addFeature(myStoc);
		StochasticTransitionFeature.FdtProperty myFdt = myStoc.new FdtProperty();
		assertNotNull(myFdt);
		String newValue = "uniform";
		myFdt.writeValue(newValue);
		assertEquals(newValue, myFdt.readValue());
	}

	@Test
	public void testGetEditorClass() {
		fail("Not yet implemented");
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
	public void testGetCategory() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetCategory() {
		fail("Not yet implemented");
	}

}
