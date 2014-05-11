package T1;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pnEditorApp.Main;

public class T1_1 {

	@BeforeClass
	public static void setUp() throws Exception {
		String [] args=null;
		Main.main(args);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
