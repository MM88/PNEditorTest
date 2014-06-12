package suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import propertiesTest.FdtPropertyTest;
import propertiesTest.ResourcePriorityPropertyTest;
import propertiesTest.ResourcesPropertyTest;
import propertiesTest.StochasticEFTPropertyTest;
import propertiesTest.StochasticLFTPropertyTest;
import propertiesTest.TimedEFTPropertyTest;
import propertiesTest.TimedLTFPropertyTest;
//import undo_redoTest.UndoRedoFeatureTest;
import undo_redoTest.UndoRedoPropertiesPnElementTest;
import undo_redoTest.UndoRedoPropertiesPreemptiveTest;
import undo_redoTest.UndoRedoPropertiesStochasticTest;
import undo_redoTest.UndoRedoPropertiesTimedTest;


import elementListTest.ELementListSelectionTest;
import featuresTest.PreemptiveTransitionFeatureTest;
import featuresTest.StochasticTransitionFeatureTest;
import featuresTest.TimedTransitionFeatureTest;

@RunWith(Suite.class)
@SuiteClasses({ELementListSelectionTest.class,
	PreemptiveTransitionFeatureTest.class, StochasticTransitionFeatureTest.class, TimedTransitionFeatureTest.class,
	FdtPropertyTest.class, ResourcePriorityPropertyTest.class, ResourcesPropertyTest.class, StochasticEFTPropertyTest.class, StochasticLFTPropertyTest.class, TimedEFTPropertyTest.class, TimedLTFPropertyTest.class,
	//UndoRedoFeatureTest.class, 
	UndoRedoPropertiesPnElementTest.class, UndoRedoPropertiesPreemptiveTest.class, UndoRedoPropertiesStochasticTest.class, UndoRedoPropertiesTimedTest.class})
public class AllTestsB {

}
