package suite;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import T1.T1_1;
import T1.T1_2;


@RunWith(Suite.class)
@SuiteClasses({T1_1.class,T1_2.class})
public class AllTests {
	
}
