package suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import T0_1.PreemptiveTransitionFeatureTest;
import T0_1.StochasticTransitionFeatureTest;
import T0_1.TimedTransitionFeatureTest;
import T0_4.FdtPropertyTest;
import T0_4.ResourcePriorityPropertyTest;
import T0_4.ResourcesPropertyTest;
import T0_4.StochasticEFTPropertyTest;
import T0_4.StochasticLFTPropertyTest;
import T0_4.TimedEFTPropertyTest;
import T0_4.TimedLTFPropertyTest;
import T3.UndoRedoPropertiesPnElementTest;
import T3.UndoRedoPropertiesPreemptiveTest;
import T3.UndoRedoPropertiesStochasticTest;
import T3.UndoRedoPropertiesTimedTest;
import T4.ELementListSelectionTest;




@RunWith(Suite.class)
@SuiteClasses({ELementListSelectionTest.class,
	PreemptiveTransitionFeatureTest.class, StochasticTransitionFeatureTest.class, TimedTransitionFeatureTest.class,
	FdtPropertyTest.class, ResourcePriorityPropertyTest.class, ResourcesPropertyTest.class, StochasticEFTPropertyTest.class, StochasticLFTPropertyTest.class, TimedEFTPropertyTest.class, TimedLTFPropertyTest.class,
	//UndoRedoFeatureTest.class, 
	UndoRedoPropertiesPnElementTest.class, UndoRedoPropertiesPreemptiveTest.class, UndoRedoPropertiesStochasticTest.class, UndoRedoPropertiesTimedTest.class})
public class AllTestsB {

}
