package featuresTest;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;


import pNeditor.PNeditorDocTemplate;
import pNeditor.PNeditorDocument;
import pNeditor.PNeditorPlugin;
import petriNetDomain.IFeatureProperty;
import petriNetDomain.PreemptiveTransitionFeature;
import petriNetDomain.TimedTransitionFeature;
import petriNetDomain.Transition;
import petriNetDomain.PreemptiveTransitionFeature.ResourcePriorityProperty;
import petriNetDomain.PreemptiveTransitionFeature.ResourcesProperty;
import pnEditorApp.PNeditorApplication;


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
		assertEquals(1, actualProperties.size());
	}
	

	@Test
	public void testAddResourceToMap() {
		Point position = new Point (0,0);
		Transition t = new Transition(new String("transition0"),
				position);
		PreemptiveTransitionFeature myPree = new PreemptiveTransitionFeature(mockedApp);
		assertNotNull(myPree);
		t.addFeature(myPree);
		myDoc.getSelectionModel().select(t, true);
		String expName = "cpu"; 
		Integer expPriority = 3;
		myPree.addResourceToMap(expName, expPriority);
		Map<String, Integer> actualResources = myPree.getResources();
		Integer actualPriority = actualResources.get(expName);
		assertEquals(expPriority, actualPriority);
	}

	

//	@Test
//	public void testGetText() {
//		fail("Not yet implemented");
//	}
//
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
		assertEquals( 3,myPree.getProperties().size());
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
		t.addFeature(myPree);
		myDoc.getSelectionModel().select(t, true);
		ArrayList<IFeatureProperty> expProperties = new ArrayList<IFeatureProperty>();
		PreemptiveTransitionFeature.ResourcesProperty rp = myPree.new ResourcesProperty();
		PreemptiveTransitionFeature.ResourcePriorityProperty rpp = myPree.new ResourcePriorityProperty("cpu");	
		expProperties.add(rp);
		expProperties.add(rpp);
		myDoc.addResource("cpu");
		ArrayList<IFeatureProperty> actualProperties = myPree.getProperties();
		assertEquals(expProperties.size(), actualProperties.size());
	}



}
