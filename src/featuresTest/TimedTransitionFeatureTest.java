package featuresTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.hamcrest.*;





import petriNetDomain.PreemptiveTransitionFeature;
import petriNetDomain.TimedTransitionFeature;

/**
 * This class tests the basic functioning of the class {@link TimedTransitionFeature}
 * @author Benedetta
 *
 */

public class TimedTransitionFeatureTest {

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
	public void testTimedTransitionFeature() {
		TimedTransitionFeature myTimed = new TimedTransitionFeature();
		assertNotNull(myTimed);
		String expectedName = "Timed Transition";
		assertEquals(expectedName, myTimed.getName());
		assertEquals(2,myTimed.getProperties().size());
	}
	
	@Test
	public void testGetText() {
		TimedTransitionFeature myTimed = new TimedTransitionFeature();
		Double expectedEFT = (double) 1.2;
		Double expectedLFT = (double) 1.5;
		myTimed.setEFT(expectedEFT);
		myTimed.setLFT(expectedLFT);
		String expectedText = "["+ expectedEFT + "," + expectedLFT + "]";
		assertEquals(expectedText, myTimed.getText());
	}

	@Test
	public void testSetGetEFT() {
		TimedTransitionFeature myTimed = new TimedTransitionFeature();
		assertNotNull(myTimed);
		Double expectedEFT = (double) 0;
		assertEquals(expectedEFT, myTimed.getEFT());
		expectedEFT = (double) 3.5;
		myTimed.setEFT(expectedEFT);
		Double actualEFT = myTimed.getEFT();
		assertEquals(expectedEFT, actualEFT);
	}

	@Test
	public void testSetGetLFT() {
		TimedTransitionFeature myTimed = new TimedTransitionFeature();
		assertNotNull(myTimed);
		Double expectedLFT = (double) 0;
		assertEquals(expectedLFT, myTimed.getLFT());
		expectedLFT = (double) 7.5;
		myTimed.setLFT(expectedLFT);
		Double actualLFT = myTimed.getLFT();
		assertEquals(expectedLFT, actualLFT);
	}


}
