package featuresTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



import petriNetDomain.StochasticTransitionFeature;
import pnEditorApp.Main;

public class StochasticTransitionFeatureTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testStochasticTransitionFeature() {
		StochasticTransitionFeature myStoc = new StochasticTransitionFeature();
		assertNotNull(myStoc);
		String expectedName = "Stochastic Transition";
		assertEquals(expectedName, myStoc.getName());
		assertEquals(3,myStoc.getProperties().size());
	}

	@Test
	public void testGetText() {
		StochasticTransitionFeature myStoc = new StochasticTransitionFeature();
		String expectedFdt = "exponential";  // can add the other cases
		myStoc.setFdt(expectedFdt);
		Double expectedEFT = (double) 3.5;
		Double expectedLFT = (double) 7.5;
		myStoc.setEFT(expectedEFT);
		myStoc.setLFT(expectedLFT);
		String expectedText = "["+ expectedEFT + "," + expectedLFT + "]" + "Exp";
		assertEquals(expectedText, myStoc.getText());
	}
	
	@Test
	public void testSetGetFdt() {
		StochasticTransitionFeature myStoc = new StochasticTransitionFeature();
		assertNotNull(myStoc);
		assertEquals("", myStoc.getFdt());
		String [] expectedFdt = new String[] { "exponential", "uniform",
		"expolinomial" };
		myStoc.setFdt(expectedFdt[0]);
		String actualFdt = myStoc.getFdt();
		assertEquals("exponential", actualFdt);
		myStoc.setFdt(expectedFdt[1]);
		actualFdt = myStoc.getFdt();
		assertEquals("uniform", actualFdt);
		myStoc.setFdt(expectedFdt[2]);
		actualFdt = myStoc.getFdt();
		assertEquals("expolinomial", actualFdt);
	}

	@Test
	public void testSetGetEFT() {
		StochasticTransitionFeature myStoc = new StochasticTransitionFeature();
		assertNotNull(myStoc);
		Double expectedEFT = (double) 0;
		assertEquals(expectedEFT, myStoc.getEFT());
		expectedEFT = (double) 3.5;
		myStoc.setEFT(expectedEFT);
		Double actualEFT = myStoc.getEFT();
		assertEquals(expectedEFT, actualEFT);
	}

	@Test
	public void testSetGetLFT() {
		StochasticTransitionFeature myStoc = new StochasticTransitionFeature();
		assertNotNull(myStoc);
		Double expectedLFT = (double) 0;
		assertEquals(expectedLFT, myStoc.getLFT());
		expectedLFT = (double) 7.5;
		myStoc.setLFT(expectedLFT);
		Double actualLFT = myStoc.getLFT();
		assertEquals(expectedLFT, actualLFT);
	}


}
