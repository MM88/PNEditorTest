package T1;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pNeditor.PNeditorDocument;
import pNeditor.PNeditorPlugin;
import petriNetDomain.Place;
import petriNetDomain.Transition;

public class T1_5 {

	private PNeditorDocument doc;
	private PNeditorPlugin plugin;
	
	@BeforeClass
	public void setUp() throws Exception {
	}

	@AfterClass
	public void tearDown() throws Exception {
	}
	@Test
	public void test() {
		Point p;
		p = new Point (0,0);
		Transition t1 = new Transition("t1", p);
		p = new Point(5,5);
		Transition t2 = new Transition("t2",p);
		p= new Point(10,10);
		Place p1 = new Place("p1",p);		
		doc.getSelectionModel().select(t1, true);
		doc.getSelectionModel().select(p1, true);
	}

}
