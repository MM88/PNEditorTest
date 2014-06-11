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
import petriNetDomain.FeaturePropertyAdapter;
import petriNetDomain.PreemptiveTransitionFeature;
import petriNetDomain.Transition;
import petriNetDomain.PreemptiveTransitionFeature.ResourcePriorityProperty;
import petriNetDomain.PreemptiveTransitionFeature.ResourcesProperty;
import pnEditorApp.PNeditorApplication;

/**
 * This class tests the basic functioning of the class {@link ResourcePriorityProperty}
 * @author Benedetta
 *
 */

public class ResourcePriorityPropertyTest {

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
	public void testResourcePriorityProperty() throws PropertyVetoException {
		PreemptiveTransitionFeature myPree = new PreemptiveTransitionFeature(mockedApp);
		assertNotNull(myPree);
		ResourcesProperty myRp = (ResourcesProperty) myPree.getProperties().get(0);
		t.addFeature(myPree);		
		String expName = "cpu";
		FeaturePropertyAdapter fPa = new FeaturePropertyAdapter(mockedApp);
		fPa.addProperty(myRp);
		fPa.write((Object)expName);
		ResourcePriorityProperty myRpp = (ResourcePriorityProperty) myPree.getProperties().get(myPree.getProperties().size() -1);
		assertEquals(expName, myRpp.getName());
		String expCategory = "Preemptive";
		assertEquals(expCategory, myRpp.getCategory());
		assertEquals(expName, myRpp.getDisplayName());
		Class expectedType = String.class;
		assertEquals(expectedType, myRpp.getType());
		String expectedError = null;
		assertEquals(expectedError, myRpp.getErrorMessage());
	}

	@Test
	public void testWriteReadValue() throws PropertyVetoException {
		PreemptiveTransitionFeature myPree = new PreemptiveTransitionFeature(mockedApp);
		assertNotNull(myPree);
		ResourcesProperty myRp = (ResourcesProperty) myPree.getProperties().get(0);
		t.addFeature(myPree);		
		String expName = "cpu";
		FeaturePropertyAdapter fPa = new FeaturePropertyAdapter(mockedApp);
		fPa.addProperty(myRp);
		fPa.write((Object)expName);
		ResourcePriorityProperty myRpp = (ResourcePriorityProperty) myPree.getProperties().get(myPree.getProperties().size() -1);
		assertNotNull(myRpp);
		String expValue = "3";
		myRpp.writeValue(expValue);
		assertEquals(3, myRpp.readValue());		
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
