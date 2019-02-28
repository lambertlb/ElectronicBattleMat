package per.lambert.ebattleMat;

import per.lambert.ebattleMat.client.ElectronicBattleMatTest;
import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;
import junit.framework.TestSuite;

public class ElectronicBattleMatSuite extends GWTTestSuite {
	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for ElectronicBattleMat");
		suite.addTestSuite(ElectronicBattleMatTest.class);
		return suite;
	}
}
