package per.lambert.ebattleMat;

import per.lambert.ebattleMat.client.ElectronicBattleMatTest;
import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Unit test suite.
 * @author LLambert
 *
 */
public class ElectronicBattleMatSuite extends GWTTestSuite {
	/**
	 * Suite of tests.
	 * @return suite of tests
	 */
	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for ElectronicBattleMat");
		suite.addTestSuite(ElectronicBattleMatTest.class);
		return suite;
	}
}
