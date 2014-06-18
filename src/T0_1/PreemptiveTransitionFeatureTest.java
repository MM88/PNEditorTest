package T0_1;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import pNeditor.PNeditorDocTemplate;
import pNeditor.PNeditorDocument;
import pNeditor.PNeditorPlugin;
import petriNetDomain.IFeatureProperty;
import petriNetDomain.PreemptiveTransitionFeature;
import petriNetDomain.Transition;
import petriNetDomain.PreemptiveTransitionFeature.ResourcePriorityProperty;
import pnEditorApp.PNeditorApplication;

/**
 * This class tests the basic functioning of the class {@link PreemptiveTransitionFeature}
 * @author Benedetta
 *
 */

public class PreemptiveTransitionFeatureTest {

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
	public void testPreemptiveTransitionFeature() {
		PreemptiveTransitionFeature myPree = new PreemptiveTransitionFeature(mockedApp);
		assertNotNull(myPree);
		String expectedName = "Preemptive Transition";
		assertEquals(expectedName, myPree.getName());
		ArrayList<IFeatureProperty> actualProperties = myPree.getProperties();
		int expSize = 1;
		assertEquals(expSize, actualProperties.size());
		Map<String, Integer> resources = myPree.getResources();
		assertNotNull(resources);
	}
	
	@Test
	public void testAddResourceToMap() {
		PreemptiveTransitionFeature myPree = new PreemptiveTransitionFeature(mockedApp);
		assertNotNull(myPree);
		String expName = "cpu"; 
		Integer expPriority = 3;
		myPree.addResourceToMap(expName, expPriority);
		Map<String, Integer> actualResources = myPree.getResources();
		Integer actualPriority = actualResources.get(expName);
		assertEquals(expPriority, actualPriority);
	}
	
	@Test
	public void testGetText() {
		PreemptiveTransitionFeature myPree = new PreemptiveTransitionFeature(mockedApp);
		assertNotNull(myPree);
		assertEquals("", myPree.getText());
		String resourceName = "cpu";
		int resourcePriority = 3;
		myPree.addResourceToMap(resourceName, resourcePriority);
		String expText = "\n {cpu}: 3";
		assertEquals(expText, myPree.getText());
		String resourceName1 = "cpu2";
		int resourcePriority1 = 2;
		myPree.addResourceToMap(resourceName1, resourcePriority1);
		expText = "\n {cpu}: 3...";
		assertEquals(expText, myPree.getText());
	}

	@Test
	public void testAddResources() {
		Point position = new Point (0,0);
		Transition t = new Transition(new String("transition0"),
				position);
		PreemptiveTransitionFeature myPree = new PreemptiveTransitionFeature(mockedApp);
		assertNotNull(myPree);
		t.addFeature(myPree);
		myDoc.getSelectionModel().select(t, true);
		String expResource = "cpu";
		myDoc.addResource(expResource);
		myPree.addResources();
		ArrayList<IFeatureProperty> fp = myPree.getProperties();
		IFeatureProperty actualProperty = fp.get(fp.size() - 1);
		assertEquals(expResource, ((ResourcePriorityProperty)actualProperty).getName());
		String expResource2 = "cpu2";
		myDoc.addResource(expResource2);
		myPree.addResources();
		int expSize = 3;
		assertEquals(expSize, myPree.getProperties().size());
		IFeatureProperty actualProperty2 = fp.get(fp.size() - 1);
		assertEquals(expResource2, ((ResourcePriorityProperty)actualProperty2).getName());
		actualProperty = fp.get(fp.size() - 2);
		assertEquals(expResource, ((ResourcePriorityProperty)actualProperty).getName());
	}

	@Test
	public void testGetResources() {
		PreemptiveTransitionFeature myPree = new PreemptiveTransitionFeature(mockedApp);
		String expName = "cpu";
		Integer expPriority = 3;
		myPree.addResourceToMap(expName, expPriority);
		Integer actualPriority = myPree.getResources().get(expName);
		assertEquals(expPriority, actualPriority);
	}

	@Test
	public void testGetProperties() {
		Point position = new Point (0,0);
		Transition t = new Transition(new String("transition0"),
				position);
		PreemptiveTransitionFeature myPree = new PreemptiveTransitionFeature(mockedApp);	
		assertNotNull(myPree);
		int expSize = 1;
		assertEquals(expSize, myPree.getProperties().size());
		t.addFeature(myPree);
		myDoc.getSelectionModel().select(t, true);
		myDoc.addResource("cpu");
		ArrayList<IFeatureProperty> actualProperties = myPree.getProperties();
		expSize = 2;
		assertEquals(expSize, actualProperties.size());
	}

}
