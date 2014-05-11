package T1;

import static org.junit.Assert.*;

import java.awt.Point;
import java.awt.Robot;
import java.util.concurrent.TimeUnit;

import javax.swing.JComponent;

import org.fest.swing.core.BasicRobot;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.launcher.ApplicationLauncher;
import org.fest.swing.timing.Pause;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import framework.FApplication;
import framework.exception.ApplicationException;
import framework.plugin.FPluginApplication;
import framework.plugin.IPluginDescriptor;
import framework.plugin.internal.PluginManager;
import framework.tool.viewer.FListViewer;
import framework.tool.viewer.ILabelProvider;
import framework.ui.FMainFrameWnd;


import pNeditor.FeaturesDockBar;
import pNeditor.PNeditorDocTemplate;
import pNeditor.PNeditorDocument;
import pNeditor.PNeditorPlugin;
import pNeditor.PNeditorView;
import petriNetDomain.PNelement;
import petriNetDomain.Place;
import petriNetDomain.Transition;
import pnEditorApp.Main;
import pnEditorApp.MainFrame;
import pnEditorApp.PNeditorApplication;
import FeatureFactories.PreemptiveTransitionFeatureFactory;
import static org.mockito.Mockito.*;


public class T1_2 {
	
	private PNeditorDocument doc;
	private PNeditorPlugin plugin; 
	private PNeditorView view;
	private static Robot robot;
	@BeforeClass
	public static void setUp() throws Exception {		
//		String[] args = {};
//		pnEditorApp.Main.main(args);
		
		 Robot robot =  (Robot) BasicRobot.robotWithCurrentAwtHierarchy();
	      PNeditorApplication app = new PNeditorApplication();
	      app.run();
	     // robot.mouseMove(50, 50);
	    
	}

	@AfterClass
	public static void tearDown() throws Exception {
		System.gc();
	}

	@Test
	public void test() throws ApplicationException {
		PNeditorDocument doc = mock(PNeditorDocument.class);
		Transition t = doc.createTransitionAt(new Point(0,0));
		doc.getSelectionModel().select(t,true);
		
//		PNeditorDocument doc = (PNeditorDocument) FApplication.getApplication().getActiveDocument();
//		Point p;
//		p = new Point (0,0);
//		doc.createTransitionAt(p);
		
//		FListViewer viewer = docBar.getViewer();
//		ILabelProvider lp= viewer.getLabelProvider();
//		System.out.print(lp.toString());
		
	}

}
