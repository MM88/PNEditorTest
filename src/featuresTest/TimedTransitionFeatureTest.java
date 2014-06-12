package featuresTest;

import static org.junit.Assert.*;

import org.junit.Test;

import petriNetDomain.TimedTransitionFeature;

/**
 * This class tests the basic functioning of the class {@link TimedTransitionFeature}
 * @author Benedetta
 *
 */

public class TimedTransitionFeatureTest {

	@Test
	public void testTimedTransitionFeature() {
		TimedTransitionFeature myTimed = new TimedTransitionFeature(null);
		assertNotNull(myTimed);
		String expectedName = "Timed Transition";
		assertEquals(expectedName, myTimed.getName());
		assertEquals(2,myTimed.getProperties().size());
	}
	
	@Test
	public void testGetText() {
		TimedTransitionFeature myTimed = new TimedTransitionFeature(null);
		Double expectedEFT = (double) 1.2;
		Double expectedLFT = (double) 1.5;
		myTimed.setEFT(expectedEFT);
		myTimed.setLFT(expectedLFT);
		String expectedText = "["+ expectedEFT + "," + expectedLFT + "]";
		assertEquals(expectedText, myTimed.getText());
	}

	@Test
	public void testSetGetEFT() {
		TimedTransitionFeature myTimed = new TimedTransitionFeature(null);
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
		TimedTransitionFeature myTimed = new TimedTransitionFeature(null);
		assertNotNull(myTimed);
		Double expectedLFT = (double) 0;
		assertEquals(expectedLFT, myTimed.getLFT());
		expectedLFT = (double) 7.5;
		myTimed.setLFT(expectedLFT);
		Double actualLFT = myTimed.getLFT();
		assertEquals(expectedLFT, actualLFT);
	}


}
