package undo_redoTest;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import framework.undoredo.HistoryException;

import pNeditor.PNeditorDocTemplate;
import pNeditor.PNeditorDocument;
import pNeditor.PNeditorPlugin;
import petriNetDomain.Place;
import petriNetDomain.Transition;

/**
 * This class tests that:
 * the edit of a property of a Pnelement is undoable
 * if the edit of a property of a Pnelement is undone it is redoable
 * @author Benedetta
 *
 */

public class UndoRedoPropertiesPnElementTest {

	private static PNeditorPlugin myPlugin;
	private static PNeditorDocument myDoc;
	

	@Before
	public void setUp() throws Exception {
		myPlugin = new PNeditorPlugin();
		myPlugin.initClipboard();
		myDoc = new PNeditorDocument();
		assertNotNull(myDoc);
		PNeditorDocTemplate dt = new PNeditorDocTemplate(myPlugin);
		myDoc.setDocTemplate(dt);
	}
	
	@Test
	public void UndoRedoName(){
		Point position = new Point (0,0);
		Transition t = new Transition("transition0", position);
		myDoc.getSelectionModel().select(t, true);
		String uExpName = t.getName();
		String rExpName = "t0";
		myDoc.rename(null, t, uExpName, rExpName, myDoc.getHistoryManager());
		try {
			myDoc.getHistoryManager().undo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		assertEquals(uExpName, t.getName());
		//redoTest
		try {
			myDoc.getHistoryManager().redo(null);
		} catch (HistoryException e) {
			e.printStackTrace();
		}
		assertEquals(rExpName, t.getName());
	}
	
}
