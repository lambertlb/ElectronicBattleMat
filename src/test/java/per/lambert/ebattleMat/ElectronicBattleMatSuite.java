/*
 * Copyright (C) 2019 Leon Lambert.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
