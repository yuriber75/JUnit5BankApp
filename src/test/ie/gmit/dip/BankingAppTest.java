package test.ie.gmit.dip;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.Timeout;

import main.ie.gmit.dip.BankingApp;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.TimeUnit;

public class BankingAppTest {

    private BankingApp bankingApp;
   
    /*
     * @Before https://junit.org/junit5/docs/5.0.2/api/org/junit/jupiter/api/BeforeAll.html
     * All methods are only executed once for a given test class.
     * Printing a starting message.
     */   
    /**
     * It prints a message indicating that the tests for the BankingApp class are starting.
     */
    @BeforeAll
    static void setupClass() {
        System.out.println("class BankingAppTest -> Starting tests...");
    }

    
    /*
     * @BeforeEach https://junit.org/junit5/docs/5.0.2/api/org/junit/jupiter/api/BeforeEach.html
     * is used to signal that the annotated method should be executed before each @Test method in the current test class.
     */
    /**
     * Sets up for each test creating a new Object
     * 
     * Each test starts with a new has-a Relationship, new Object 
     */
    @BeforeEach
    void setUp() {
        bankingApp = new BankingApp();
    }
    
    /**
     * Test testSuccessfulAddAccount
     * -Verify: correct account creation, balance account and balance bank. 
     *
     * -Time  : Test has 3 seconds to be completed
     *
     * -Steps : 1) Account for "Alice" is created with balance 1000
     *          2) Check that account "Alice" has balance 1000
     *          3) Check that bank has balance 1000
     * 
     * -Result: Account created, 1000 in Alice's account , 1000 in bank's account
     */   
    @Test
    @Timeout(value = 3, unit = TimeUnit.MILLISECONDS) //as per lesson HDipASDWeek10b
    void testSuccessfulAddAccount() {
        bankingApp.addAccount("Alice", 1000);
        assertEquals(1000, bankingApp.getBalance("Alice"),0.001);
        assertEquals(1000, bankingApp.getTotalDeposits(),0.001);
    }

    
    /**
     * Test testAddAccountWithEmptyName
     * -Verify: behaviour during creation with no name
     *
     * -Time  : Test has 3 seconds to be completed
     *
     * -Steps : 1) Check if creation account with no name
     *             throws IllegalArgumentException
     *          2) Check equality message "Account holder name cannot be empty."
     * 
     * -Result: Exception thrown and message verified
     */  
    @Test
    @Timeout(value = 3, unit = TimeUnit.MILLISECONDS) 
    void testAddAccountWithEmptyName() {    	
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            bankingApp.addAccount("", 1000);
        });
        assertEquals("Account holder name cannot be null or empty.", ex.getMessage());
        System.out.println("Exception --> Test: " + ex.getMessage());
    }
    
    
    /**
     * Test testAddAccountWithEmptyName
     * -Verify: behaviour during creation with no name
     *
     * -Time  : Test has 3 seconds to be completed
     *
     * -Steps : 1) Check if creation account with no name
     *             throws IllegalArgumentException
     *          2) Check equality message "Account holder name cannot be null"
     * 
     * -Result: Exception thrown and message verified
     */     
    @Test
    @Timeout(value = 3, unit = TimeUnit.MILLISECONDS) 
    void testAddAccountWithNullName() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            bankingApp.addAccount(null, 1000);
        });
        assertEquals("Account holder name cannot be null or empty.", ex.getMessage());
        System.out.println("Exception --> Test: " + ex.getMessage());
    }

    /**
     * Test testAddAccountWithNonPositiveDeposit
     * -Verify: behaviour during creation with negative deposit
     *
     * -Time  : Test has 3 seconds to be completed
     *
     * -Steps : 1) Check if creation account with negative deposit
     *             throws IllegalArgumentException
     *          2) Check equality message "Initial deposit must be greater than zero."
     * 
     * -Result: Exception thrown and message verified
     */ 
    @Test
    @Timeout(value = 3, unit = TimeUnit.MILLISECONDS) 
    void testAddAccountWithNonPositiveDeposit() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            bankingApp.addAccount("Bob", -500);
        });
        assertEquals("Initial deposit must be greater than zero.", ex.getMessage());
        System.out.println("Exception --> Test: " + ex.getMessage());
    }

    /**
     * Test testDeposit
     * -Verify: behaviour during deposit in existing account
     *
     * -Steps : 1) Initialisation of a new account for Alice set with 500 balance
     *          2) Deposit 200 in Alice's account
     *          3) Check if deposit return true, for successful operation
     *          4) Check total Alice's balance equality to 700 (500+200)
     *          5) Check total bank (700)
     * 
     * -Result: True for successful deposit, Alice's 700, Bank 700 
     */ 
    @Test
    void testDeposit() {
        bankingApp.addAccount("Alice", 500);
        boolean success = bankingApp.deposit("Alice", 200);
        assertTrue(success);
        assertEquals(700, bankingApp.getBalance("Alice"),0.001);
        assertEquals(700, bankingApp.getTotalDeposits(),0.001);
    }


    /**
     * Test testDepositNoName
     * -Verify: behaviour during attempt of deposit in non existing account
     *
     * -Steps : 1) Deposit 200 in not existing account
     *          2) Check if deposit return false, deposit not done
     * 
     * -Result: False since account does not exist 
     */ 
    @Test
    void testDepositNoName() {
        boolean success = bankingApp.deposit("No_name", 200);
        assertFalse(success);
    }
    
    /**
     * Test testDepositNegativeAmount
     * -Verify: behaviour during attempt of deposit in non existing account
     *
     * -Steps : 1) Initialisation of a new account for Alice set with 500 balance
     * 		    2) Attempt to deposit -200 
     *          3) Check if deposit return false, deposit not done
     * 
     * -Result: False since amount is negative 
     */ 
    @Test
    void testDepositNegativeAmount() {
        bankingApp.addAccount("Alice", 500);
        boolean fail = bankingApp.deposit("Alice", -200);
        assertFalse(fail);
    }
    

    /**
     * Test testWithdrawSuccess
     * -Verify: verify that a possible withdrawn is reflected in the balance
     *
     * -Steps : 1) Initialisation of a new account for Alice set with 1000 balance
     *          2) Withdrawn 300 in Alice's account
     *          3) Check total Alice's balance equality to 700 (1000-300)
     *          4) Check total bank (700)
     * 
     * -Result: the bank and Alice's balances are updated to 700 
     */
    @Test
    void testWithdrawSuccess() {
        bankingApp.addAccount("Alice", 1000);
        bankingApp.withdraw("Alice", 300);
        assertEquals(700, bankingApp.getBalance("Alice"),0.001);
        assertEquals(700, bankingApp.getTotalDeposits(),0.001);
    }
  
    
    /**
     * Test testWithdrawInvalidAmount
     * -Verify: verify that in not possible withdrawn a negative amount
     *
     * -Steps : 1) Initialisation of a new account for Alice set with 1000 balance
     *          2) Withdrawn -100 from Alice's account
     *          3) Check if method throws IllegalArgumentException
     *          4) Check equality of message with "Withdrawal amount must be greater than zero."
     * 
     * -Result: message should be equal that it means that exception works
     */    
    @Test
    void testWithdrawInvalidAmount() {
        bankingApp.addAccount("Alice", 1000);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            bankingApp.withdraw("Alice", -100);
        });
        assertEquals("Withdrawal amount must be greater than zero.", ex.getMessage());
        System.out.println("Exception --> Test: " + ex.getMessage());
    }

    
    /**
     * Test testWithdrawInvalidAmount
     * -Verify: verify that in not possible withdrawn from a non existing account
     *
     * -Steps : 1) Attempt to withdrawn 100 from "NoName" account
     *          2) Check if method throws IllegalArgumentException
     *          3) Check equality of message with "Account holder not found: NoName"
     * 
     * -Result: message should be equal that it means that exception works
     */ 
    @Test
    void testWithdrawNoNameAccount() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            bankingApp.withdraw("NoName", 100);
        });
        assertEquals("Account holder not found: NoName", ex.getMessage());
        System.out.println("Exception --> Test: " + ex.getMessage());
    }

    
    /**
     * Test testWithdrawInsufficientFunds
     * -Verify: verify that trying withdrawn more than balance create an exception
     *
     * -Steps : 1) Initialisation of a new account for Alice set with 100 balance
     *          2) Attempt withdrawn 200 in Alice's account
     *          3) Check if method throws IllegalArgumentException
     *          4) Check equality of message with "Insufficient funds for withdrawal."
     * 
     * -Result: message should be equal that it means that exception works
     */     
    @Test
    void testWithdrawInsufficientFunds() {
        bankingApp.addAccount("Alice", 100);
        Exception ex = assertThrows(IllegalStateException.class, () -> {
            bankingApp.withdraw("Alice", 200);
        });
        assertEquals("Insufficient funds for withdrawal.", ex.getMessage());
        System.out.println("Exception --> Test: " + ex.getMessage());
    }


    /**
     * Test testApproveLoan
     * -Verify: verify that a loan approved reflect in both Alice's loan balance and bank's balance
     *
     * -Steps : 1) Initialisation of a new account for Alice set with 1000 balance
     *          2) Attempt approval loan 500 in Alice's account
     *          3) Loan approval return True and assertTrue
     *          4) Check if Alice's loan balance is now 500
     *          5) Check if bank's balance is now 500
     * 
     * -Result: loan approved, equality new Alice's balance to 500, reduction bank balance o 500
     */
    @Test
    void testApproveLoan() {
        bankingApp.addAccount("Alice", 1000);
        boolean success = bankingApp.approveLoan("Alice", 500);
        assertTrue(success);
        assertEquals(500, bankingApp.getLoan("Alice"),0.001);
        assertEquals(500, bankingApp.getTotalDeposits(),0.001);
    }

    /**
     * Test testFailLoanExceedsTotalDeposits
     * -Verify: verify that a loan is not approved and Alice's loan balance stay at 0
     *
     * -Steps : 1) Initialisation of a new account for Alice set with 500 balance
     *          2) Attempt approval loan 1000 in Alice's account
     *          3) Loan denied return False and assertFalse
     *          4) Check if Alice's loan balance stays 0
     * 
     * -Result: loan denied and Alice's loan balance stays at 0
     */
    @Test
    void testFailLoanExceedsTotalDeposits() {
        bankingApp.addAccount("Alice", 500);
        boolean fail = bankingApp.approveLoan("Alice", 1000);
        assertFalse(fail);
        assertEquals(0, bankingApp.getLoan("Alice"),0.001);
    }

    /**
     * Test testFailLoanExceedsTotalDeposits
     * -Verify: verify that a loan is deny because there is no account
     *
     * -Steps : 1) Attempt approval loan 1000 in NoName account
     *          2) Loan denied return False and assertFalse
     * 
     * -Result: loan denied because account does not exist
     */
    @Test
    void testFailLoanNoAccount() {
        boolean fail = bankingApp.approveLoan("NoName", 1000);
        assertFalse(fail);
    }
    
    
    /**
     * Test testRepayLoan
     * -Verify: verify that a loan repay is successful reflect on Alice's loan balance and bank's balance
     *
     * -Steps : 1) Initialisation of a new account for Alice set with 1000 balance
     *          2) Approval loan 500 in Alice's loan balance
     *          3) Repay 200 of the loan
     *          4) Repay approved return true and assertTrue 
     *          5) Alice's loan balance should be 300 (500-200)
     *          6) Bank's balance should be 700 (1000-300)
     * 
     * -Result: Alice's loan balance should decrease (300) and bank deposit increase (700) 
     */
    @Test
    void testRepayLoan() {
        bankingApp.addAccount("Alice", 1000);
        bankingApp.approveLoan("Alice", 500);
        boolean success = bankingApp.repayLoan("Alice", 200);
        assertTrue(success);
        assertEquals(300, bankingApp.getLoan("Alice"),0.001);
        assertEquals(700, bankingApp.getTotalDeposits(),0.001);
    }


    /**
     * Test testRepayLoanExceedsLoanAmount
     * -Verify: Test invalid condition where trying to repay more than than loan balance
     *
     * -Steps : 1) Initialisation of a new account for Alice set with 1000 balance
     *          2) Approval loan 500 in Alice's loan balance
     *          3) Repay 600 of the loan
     *          4) Repay deny return false and assertFalse
     *          5) Alice's loan balance is unchanged 500
     * 
     * -Result: Alice's loan balance remain unchanged because repay is not approved
     */
    @Test
    void testRepayLoanExceedsLoanAmount() {
        bankingApp.addAccount("Alice", 1000);
        bankingApp.approveLoan("Alice", 500);
        boolean fail = bankingApp.repayLoan("Alice", 600);
        assertFalse(fail);
        assertEquals(500, bankingApp.getLoan("Alice"),0.001);
    }
 
    
    /**
     * Test testRepayNoName
     * -Verify: Test invalid condition where trying to repay account do not exist
     *
     * -Steps : 1) Repay -100 of the loan
     *          2) Repay deny return false and assertFalse
     * 
     * -Result: repay fail due to no account
     */
    @Test
    void testRepayLoanNoAccount() {
        boolean fail = bankingApp.repayLoan("", -100);
        assertFalse(fail);
    }

    
    /**
     * Test testRepayLoanNegativeAmount
     * -Verify: Test invalid condition where trying to repay more than than loan balance
     *
     * -Steps : 1) Initialisation of a new account for Alice set with 1000 balance
     *          2) Approval loan 500 in Alice's loan balance
     *          3) Repay -100 of the loan
     *          4) Repay deny return false and assertFalse
     *          5) Alice's loan balance is unchanged 500
     * 
     * -Result: Alice's loan balance remain unchanged because repay is not approved
     */
    @Test
    void testRepayLoanNegativeAmount() {
        bankingApp.addAccount("Alice", 1000);
        bankingApp.approveLoan("Alice", 500);
        boolean fail = bankingApp.repayLoan("Alice", -100);
        assertFalse(fail);
        assertEquals(500, bankingApp.getLoan("Alice"),0.001);
    }    
    
    /**
     * Test testGetBalanceNonexistentAccount
     * -Verify: method return "null" if account does not exist 
     *
     * -Steps : 1) try to get the balance from account NoAccount 
     *          2) Assert that result is null
     * 
     * -Result: method should return null because NoAccount does not exist
     */
    @Test
    void testGetBalanceNoAccount() {
        assertNull(bankingApp.getBalance("NoAccount"));
    }

    
    /**
     * Test testGetLoanNoAccount
     * -Verify: method return "null" if account does not exist 
     *
     * -Steps : 1) try to get a loan from account NoAccount 
     *          2) Assert that result is null
     * 
     * -Result: method should return null because NoAccount does not exist
     */
    @Test
    void testGetLoanNoAccount() {
        assertNull(bankingApp.getLoan("NoAccount"));
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
        System.out.println("class BankingAppTest -> End tests!");
        System.out.println("");
    }
}

