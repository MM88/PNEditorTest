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
import petriNetDomain.PreemptiveTransitionFeature.ResourcesProperty;
import pnEditorApp.PNeditorApplication;


/**
 * This class tests the basic functioning of the class {@link ResourcesProperty} of the class {@link PreemptiveTransitionFeature}
 * @author Benedetta
 *
 */

public class ResourcesPropertyTest {
	
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
	public void testResourceProperty() throws PropertyVetoException {
		PreemptiveTransitionFeature myPree = new PreemptiveTransitionFeature(mockedApp);
		assertNotNull(myPree);
		ResourcesProperty myRp = (ResourcesProperty) myPree.getProperties().get(0);
		t.addFeature(myPree);
		String expCategory = "Preemptive";
		assertEquals(expCategory, myRp.getCategory());
		String expDisplayName = "Add Resource";
		assertEquals(expDisplayName, myRp.getDisplayName());
		assertTrue(myRp.isEditable());
		Class expectedType = String.class;
		assertEquals(expectedType, myRp.getType());
		String expName = "Resources Preemptive";
		assertEquals(expName, myRp.getName());
	}

	@Test
	public void testReadValue() {
		PreemptiveTransitionFeature myPree = new PreemptiveTransitionFeature(mockedApp);
		assertNotNull(myPree);
		ResourcesProperty myRp = (ResourcesProperty) myPree.getProperties().get(0);
		assertNull(myRp.readValue());
	}

	@Test
	public void testWriteValue() throws PropertyVetoException {
		PreemptiveTransitionFeature myPree = new PreemptiveTransitionFeature(mockedApp);
		assertNotNull(myPree);
		t.addFeature(myPree);
		assertEquals(0, myPree.getResources().size());
		ResourcesProperty myRp = (ResourcesProperty) myPree.getProperties().get(0);
		FeaturePropertyAdapter fPa = new FeaturePropertyAdapter(mockedApp);
		fPa.addProperty(myRp);
		String expName = "cpu";
		fPa.write(expName);
		assertEquals(1, myDoc.getResources().size());
		assertEquals(1, myPree.getResources().size());
		
	}

	@Test
	public void testGetErrorMessage() {
		PreemptiveTransitionFeature myPree = new PreemptiveTransitionFeature(mockedApp);
		assertNotNull(myPree);
		ResourcesProperty myRp = (ResourcesProperty) myPree.getProperties().get(0);
		assertNull(myRp.getErrorMessage());
	}

	@Test
	public void testSetGetName() {
		PreemptiveTransitionFeature myPree = new PreemptiveTransitionFeature(mockedApp);
		assertNotNull(myPree);
		ResourcesProperty myRp = (ResourcesProperty) myPree.getProperties().get(0);
		String expName = "Resources Preemptive";
		assertEquals(expName, myRp.getName());
		expName = "Resources";
		myRp.setName(expName);
		String actName = myRp.getName();
		assertEquals(expName, actName);
	}

	@Test
	public void testSetGetDisplayName() {
		PreemptiveTransitionFeature myPree = new PreemptiveTransitionFeature(mockedApp);
		assertNotNull(myPree);
		ResourcesProperty myRp = (ResourcesProperty) myPree.getProperties().get(0);
		String expDisplayName = "Add Resource";
		assertEquals(expDisplayName, myRp.getDisplayName());
		expDisplayName = "Add";
		myRp.setDisplayName(expDisplayName);
		String actName = myRp.getDisplayName();
		assertEquals(expDisplayName, actName);
	}

	@Test
	public void testSetGetType() {
		PreemptiveTransitionFeature myPree = new PreemptiveTransitionFeature(mockedApp);
		assertNotNull(myPree);
		ResourcesProperty myRp = (ResourcesProperty) myPree.getProperties().get(0);
		Class expType = String.class;
		assertEquals(expType, myRp.getType());
		expType = Integer.class;
		myRp.setType(Integer.class);
		Class actType = myRp.getType();
		assertEquals(expType, actType);
	}

	@Test
	public void testIsEditable() {
		PreemptiveTransitionFeature myPree = new PreemptiveTransitionFeature(mockedApp);
		assertNotNull(myPree);
		ResourcesProperty myRp = (ResourcesProperty) myPree.getProperties().get(0);
		assertTrue(myRp.isEditable());
	}

	@Test
	public void testSetEditable() {
		PreemptiveTransitionFeature myPree = new PreemptiveTransitionFeature(mockedApp);
		assertNotNull(myPree);
		ResourcesProperty myRp = (ResourcesProperty) myPree.getProperties().get(0);
		assertTrue(myRp.isEditable());
		myRp.setEditable(false);
		assertFalse(myRp.isEditable());
	}
}
