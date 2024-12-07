package test.ie.gmit.dip;

import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SelectClasses;

/**
 * 2024-25: 8873 -- AGILE SOFTWARE DEVELOPMENT
 * 
 * Test suite for bank application. Assignment One (JUnit)
 * Student : G00439350@atu.ie
 * 
 * Verify with JUnit5 all the class in this project  
 * Testing 1) RunnerTest related to Runner class
 * 		   1) AccountTest related to Account class
 *         2) BankingAppTest related to BankingApp class
 * 
 * Running this suite execute all the test together
 * 
 * 
 */

@Suite
@SelectClasses({
	RunnerTest.class,
	AccountTest.class,
	BankingAppTest.class
})
class TestSuite {

}