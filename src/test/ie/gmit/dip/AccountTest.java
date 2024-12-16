package test.ie.gmit.dip;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import main.ie.gmit.dip.Account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.TestInfo;

/**
 * AccountTest Class
 * verify all methods such as Deposit, 
 * Withdraw, Get and Loan
 * 
 * This is a JUnit5 test class.
 */

class AccountTest {

    private Account account;
   
    /*
     * @Before https://junit.org/junit5/docs/5.0.2/api/org/junit/jupiter/api/BeforeAll.html
     * All methods are only executed once for a given test class.
     * 
     * Need to be static because instance are not created yet
     * It runs only once
     */
    /**
     * It prints a message indicating that the tests for the AccountTest class are starting.
     */
    @BeforeAll
    static void setupClass() {
        System.out.println("class AccountTest -> Starting tests...");
    }

    
    /*
     * @BeforeEach https://junit.org/junit5/docs/5.0.2/api/org/junit/jupiter/api/BeforeEach.html
     * is used to signal that the annotated method should be executed before each @Test method in the current test class.
     */
    /**
     * Sets up for each test creating a new Object
     * 
     * Each test starts with a new account, a new instance ("Alice", 1000)
     */
    @BeforeEach
    void setup() {
        account = new Account("Alice", 1000);
    }

    
    /*
     * assertEquals(double expected, double actual)
     * Asserts that two doubles or floats are equal to within a positive delta.
     * Equality imposed by this method is consistent with Double.equals(Object) and Double.compare(double, double).
     * https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/api/Assertions.html#assertEquals-double-double-java.lang.String-
     */
    /**
     * Test Deposit
     * -Verify: deposit amount is correctly added to balance. 
     *
     * -Steps : 1) Deposit 200 to account with 1000 of balance
     *          2) Check that the new balance is 1200
     * 
     * -Result: balance should be verified and equal
     */
    @Test
    void testDeposit(){
        account.deposit(200);
        assertEquals(1200, account.getBalance(),0.001);
    }

    
    /*
     * @ParameterizedTest is used to signal that the annotated method is a parameterized test method.
     * https://junit.org/junit5/docs/5.0.2/api/org/junit/jupiter/params/ParameterizedTest.html
     * @ValueSource is an ArgumentsSource which provides access to an array of literal values of primitive types.
     * https://junit.org/junit5/docs/5.0.2/api/org/junit/jupiter/params/provider/ValueSource.html
     */ 
    /**
     * Test Deposit with invalid amount
     * -Verify: deposit should be rejected if <= 0 and >= 1 million
     *
     * -Steps : test values 0, -100 and 1 million
     * 
     * -Result: method should return false for all the tests
     * 
     * @param test values provided by @ValueSource
     */
    @ParameterizedTest
    @ValueSource(doubles = {0, -100, 1000000})
    void testInvalidDepositAmounts(double amount) {
        assertFalse(account.deposit(amount));
    }     

    
    /*
     * assertTrue(boolean condition)
     * Asserts that the supplied condition is true.
     * https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/api/Assertions.html#assertTrue-boolean-
     */    
    /**
     * Test Withdraw - amount request less than balance
     * -Verify: Withdraw amount is correctly subtract from balance. 
     *
     * -Steps : 1) Withdraw 500 to account with 1000 of balance
     *             check if method return true
     *          2) Check that the new balance is 500 (1000-500)
     * 
     * -Result: true and correct balance
     */
    @Test
    void Withdraw() {
        assertTrue(account.withdraw(500));
        assertEquals(500, account.getBalance(),0.001);
    }

    
    /*
     * assertFalse(boolean condition)
     * Asserts that the supplied condition is false.
     * https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/api/Assertions.html#assertFalse-boolean-
     */
    /**
     * Test Withdraw - amount request more than balance
     * -Verify: Withdraw amount is rejected 
     *
     * -Steps : 1) Attempt to withdraw 1500 to account with 1000 of balance
     *             check if method return false
     *          2) Check that balance is 1000 (unchanged)
     * 
     * -Result: true and correct balance
     */    
    @Test
    void testWithdrawFail() {
        assertFalse(account.withdraw(1500));
        assertEquals(1000, account.getBalance(),0.001);
    }

    
    /*
     * assertEquals(double expected, double actual)
     * Asserts that two doubles or floats are equal to within a positive delta.
     * Equality imposed by this method is consistent with Double.equals(Object) and Double.compare(double, double).
     * https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/api/Assertions.html#assertEquals-double-double-java.lang.String-
     */
    /**
     * Test ApproveLoan
     * -Verify: loan amount added to loan balance. 
     *
     * -Steps : 1) Loan 500 to account with loan balance 0
     * 
     * -Result: balance of loan should be 500
     */
    @Test
    void testApproveLoan() {
        account.approveLoan(500);
        assertEquals(500, account.getLoan(),0.001);
    }
    
    /**
     * Test RefuseLoan
     * -Verify: loan amount added to loan balance. 
     *
     * -Steps : 1) Loan -500 to account with loan balance 0
     * 
     * -Result: balance of loan should be 0
     */
    @Test
    void testRefuseLoan() {
        account.approveLoan(-500);
        assertEquals(0, account.getLoan(),0.001);
    }

    
    /**
     * Test RepayLoanSuccess
     * -Verify: after repaying loan amount, loan balance is updated. 
     *
     * -Steps : 1) A new loan of 500 is added to the balance
     *          2) Check if repayLoan return true
     *          3) Verify that new loan balance is updated to 300
     *             500 initial balance - 200 repayLoan  
     * 
     * -Result: balance of loan should be updated to 300
     */
    @Test
    void testRepayLoanSuccess() {
        account.approveLoan(500);
        assertTrue(account.repayLoan(200));
        assertEquals(300, account.getLoan(),0.001);
    }

    
    /**
     * Test RepayLoanFail
     * -Verify: check loan balance is unchanged if amount is bigger than loan. 
     *
     * -Steps : 1) A new loan of 500 is added to the balance
     *          2) Check if repayLoan return false
     *          3) Verify that new loan balance is unchanged (500) 
     * 
     * -Result: balance of loan should be unchanged as set at the beginning
     */
    @Test
    void testRepayLoanFail() {
        account.approveLoan(500);
        assertFalse(account.repayLoan(600));
        assertEquals(500, account.getLoan(),0.001);
    }

    
    /**
     * Test RepayLoanNegativeValue
     * -Verify: check loan balance is unchanged if amount is negative
     *
     * -Steps : 1) A new loan of 500 is added to the balance
     *          2) Check if repayLoan return false with negative amount
     *          3) Verify that new loan balance is unchanged (500) 
     * 
     * -Result: balance of loan should be unchanged as set at the beginning
     */
    @Test
    void testRepayNegativeValue() {
        account.approveLoan(500);
        assertFalse(account.repayLoan(-100));
        assertEquals(500, account.getLoan(),0.001);
    }
    
    
    /*
     * assertEquals(Object expected, Object actual)
     * Asserts that two Object (String) are equal.
     * Asserts that expected and actual are equal. If null are equals.
     * https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/api/Assertions.html#assertEquals-java.lang.Object-java.lang.Object-
     */
    /**
     * Test getAccountHolder
     * -Verify: account name is correctly returned 
     *
     * -Steps : 1) Create a new Account with name "Alice"
     *          2) Retrieve the name from the method
     *          3) Verify equality
     * 
     * -Result: name returned should be "Alice"
     */    
    @Test
    void testGetAccountHolder() {
        assertEquals("Alice", account.getAccountHolder());
    }

    
    /**
     * Test getBalance
     * -Verify: account name is correctly returned 
     *
     * -Steps : 1) Create a new Account with balance 1000
     *          2) Retrieve the Account balance
     *          3) Verify equality
     * 
     * -Result: should return correct balance 1000
     */  
    @Test
    void testGetBalance() {
        assertEquals(1000, account.getBalance(),0.001);
    }

   
    /**
     * Test testGetLoan
     * -Verify: account name is correctly returned 
     *
     * -Steps : 1) Create a new Account, value of Loan is 0 at start
     *          2) Retrieve the loan amount
     *          3) Verify equality between 0 and retrieved
     * 
     * -Result: should return the initial 0 value
     */     
    @Test
    void testGetLoan() {
        assertEquals(0, account.getLoan(),0.001);
    }

    
    /*
     * @AfterEach is used to signal that the annotated method should be executed after each 
     * @Test method in the current test class.
     * https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/api/AfterEach.html
     * 
     * TestInfo is used to inject information about the current test or container into to 
     * @Test, @BeforeEach, @AfterEach, @BeforeAll, and @AfterAll methods.
     * https://junit.org/junit5/docs/5.0.0/api/org/junit/jupiter/api/TestInfo.html
     */
    /**
     * Logs a message after each test
     * 
     * @param testInfo -> provides information about the current test
     * 
     * It helps to track the testing process
     */
    @AfterEach
    void logTestCompletion(TestInfo testInfo) {
        System.out.println("Test " + testInfo.getDisplayName() + " finished.");
    }

    
    /*
     * @AfterAll methods must have a void return type, must not be private, and must be static by default. 
     * Consequently, @AfterAll methods are not supported in @Nested test classes or as interface default 
     * methods unless the test class is annotated with @TestInstance(Lifecycle.PER_CLASS).
     * 
     * @AfterAll may be used as a meta-annotation in order to create a custom composed annotation that 
     * inherits the semantics of @AfterAll.
     * https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/api/AfterAll.html
     */
    /**
     * Logs a message in console when all the test are performed
     * It is executed only once
     */
    @AfterAll
    static void logClassTestCompletion() {
        System.out.println("class AccountTest -> End tests!");
        System.out.println("");
    }
}