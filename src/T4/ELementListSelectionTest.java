package T4;


import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import framework.tool.viewer.IContentProvider;

import pNeditor.ElementListDockBar;
import pNeditor.PNeditorDocTemplate;
import pNeditor.PNeditorDocument;
import pNeditor.PNeditorPlugin;
import pNeditor.PNeditorView;
import petriNetDomain.PNelement;
import petriNetDomain.Place;
import petriNetDomain.Transition;

/**
 * This class tests that:
 *  when one (or more) element is selected in the document it appears selected on the element list and viceversa;
 *  when an element is removed from the document it is removed from the element list;
 *  when the name of an element is changed on the document it changes on the element list as well.
 * @author Benedetta
 *
 */

public class ELementListSelectionTest {

	private static ElementListDockBar myEldock = null;
	private static PNeditorDocument myDoc;
	private static PNeditorPlugin myPlugin;

	@Before
	public void setUp() throws Exception {
		
		myPlugin = new PNeditorPlugin();
		myPlugin.initClipboard();
		myDoc = new PNeditorDocument();
		PNeditorDocTemplate dt = new PNeditorDocTemplate(myPlugin);
		myDoc.setDocTemplate(dt);
		PNeditorView view = new PNeditorView();
		view.setDocument(myDoc);
		view.initializeView(null, myDoc);
		myEldock = new ElementListDockBar();
	}
	
	@Test
	public void selectionDocTest(){
		Point p = new Point(0,0);
		Place place = new Place("place0", p);
		Point p0 = new Point(10,10);
		Transition t0 = new Transition("transizion0", p0);
		myDoc.addPlaceToPetriNet(place, null);
		myDoc.addTransitionToPetriNet(t0, null);
		myDoc.getSelectionModel().select(t0, true);
		myEldock.activate(myDoc);
		IContentProvider cp = myEldock.getViewer().getContentProvider();
		int nEl = cp.getChildrenCount(myDoc);
		assertEquals(2, nEl);
		myEldock.updateView(myDoc, null);
		myEldock.getSelectionListener().valueChanged(null);
		int selectedEl = myEldock.getViewer().getSelectedValues().length;
		assertEquals(1, selectedEl);
		assertEquals(Transition.class,myEldock.getViewer().getSelectedValue().getClass());
		//more elements 
		Point p1 = new Point(20,20);
		Transition t1 = new Transition("transizion1", p1);
		myDoc.addTransitionToPetriNet(t1, null);
		myDoc.getSelectionModel().select(t1, true);
		myEldock.activate(myDoc);
		myEldock.updateView(myDoc, null);
		myEldock.getSelectionListener().valueChanged(null);
		cp = myEldock.getViewer().getContentProvider();
		nEl = cp.getChildrenCount(myDoc);
		assertEquals(3, nEl);
		selectedEl = myEldock.getViewer().getSelectedValues().length;
		assertEquals(2, selectedEl);
	}
	
	@Test
	public void selectionDockTest(){
		Point p = new Point(0,0);
		Place place = new Place("place0", p);
		Point p0 = new Point(10,10);
		Transition t0 = new Transition("transizion0", p0);
		myDoc.addPlaceToPetriNet(place, null);
		myDoc.addTransitionToPetriNet(t0, null);
		myEldock.activate(myDoc);
		myEldock.updateView(myDoc, null);
		myEldock.getViewer().getSelectionModel().setSelectionInterval(0, 0);
		assertEquals(1,myDoc.getSelectionModel().getSelectedItems().size());
	}
	
	@Test
	public void deletedTest(){
		
		Point p = new Point(0,0);
		Place place = new Place("place0", p);
		Point p0 = new Point(10,10);
		Transition t0 = new Transition("transizion0", p0);
		myDoc.addPlaceToPetriNet(place, null);
		myDoc.addTransitionToPetriNet(t0, null);
		myEldock.activate(myDoc);
		myEldock.updateView(myDoc, null);
		IContentProvider cp = myEldock.getViewer().getContentProvider();
		int nEl = cp.getChildrenCount(myDoc);
		assertEquals(2, nEl);
		Collection<PNelement> toDelete = new ArrayList<PNelement>();
		toDelete.add(t0);
		myDoc.deleteItemsImpl(toDelete,null);
		int actualEl = myDoc.getPlacesAndTransitions().size();
		assertEquals(1, actualEl);
		myEldock.activate(myDoc);
		myEldock.updateView(myDoc, null);
		cp = myEldock.getViewer().getContentProvider();
		nEl = cp.getChildrenCount(myDoc);
		assertEquals(1, nEl);
		Object child = cp.getChild(myDoc, 0);
		assertEquals(Place.class, child.getClass());
	}
	
	@Test
	public void changeNameTest(){
		Point p = new Point(0,0);
		Place place = new Place("place0", p);
		myDoc.addPlaceToPetriNet(place, null);
		myDoc.getSelectionModel().select(place, true);
		myEldock.activate(myDoc);
		myEldock.updateView(myDoc, null);
		myEldock.getSelectionListener().valueChanged(null);
		Place elPlace = (Place) myEldock.getViewer().getSelectedValue();
		assertEquals("place0", elPlace.getName());
		myDoc.rename(null, place, "place0", "p", null);
		myEldock.updateView(myDoc, null);
		myEldock.getSelectionListener().valueChanged(null);
		elPlace = (Place) myEldock.getViewer().getSelectedValue();
		assertEquals("p", elPlace.getName());
	}
	

}
