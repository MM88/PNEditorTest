package suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import T0_2.PreemptiveTransitionFeatureFactoryTest;
import T0_2.StochasticTransitionFeatureFactoryTest;
import T0_2.TimedTransitionFeatureFactoryTest;
import T0_3.TransitionTest;
import T0_5_6_7.ElementListDockBarTest;
import T0_5_6_7.FeaturesDockBarTest;
import T0_5_6_7.PropertiesDockBarTest;
import T1.T1_1;
import T1.T1_2;
import T1.T1_3;
import T1.T1_4;
import T2.T2_1;
import T2.T2_2;
import T2.T2_3;
import T2.T2_4;
import T2.T2_5;


@RunWith(Suite.class)
@SuiteClasses({ ElementListDockBarTest.class, FeaturesDockBarTest.class,
	PreemptiveTransitionFeatureFactoryTest.class, StochasticTransitionFeatureFactoryTest.class, TimedTransitionFeatureFactoryTest.class,
	TransitionTest.class,
	PropertiesDockBarTest.class,T1_1.class, T1_2.class , T1_3.class , T1_4.class,
	T2_1.class , T2_2.class , T2_3.class , T2_4.class , T2_5.class })
public class AllTestsM {

}
