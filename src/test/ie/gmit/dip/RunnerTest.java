package test.ie.gmit.dip;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.ie.gmit.dip.BankingApp;
import main.ie.gmit.dip.Runner;

class RunnerTest {

    @Test
    void testRunBankingApp() {

        BankingApp bank = new BankingApp();
        Runner runner = new Runner();

        runner.runBankingApp(bank);
        
        BankingApp bankTest = new BankingApp();
        runBankingAppTest(bankTest);
    }
    
    public void runBankingAppTest(BankingApp bankTest) {    
	    // Add accounts
	    bankTest.addAccount("AliceTest", 1000);
	    bankTest.addAccount("BobTest", 500);

	    System.out.println("Testing Runner Mock Method");
	    
	    // Test deposits
	    assertTrue(bankTest.deposit("AliceTest", 200));
	    System.out.println("Deposit: Test passed");
	    assertEquals(1200, bankTest.getBalance("AliceTest"));
	    System.out.println("Balance after deposit: Test passed");

	    // Test withdrawals
	    assertTrue(bankTest.withdraw("BobTest", 300));
	    System.out.println("Withdraw: Test passed");
	    assertEquals(200, bankTest.getBalance("BobTest"));
	    System.out.println("Balance after withdraw: Test passed");	    
	    
	    // Test loan approval
	    assertTrue(bankTest.approveLoan("AliceTest", 400));
	    System.out.println("Approving a loan: Test passed");
	    assertEquals(400, bankTest.getLoan("AliceTest"));
	    System.out.println("Balance loan: Test passed");	    
	    
	    // Test loan repayment
	    assertTrue(bankTest.repayLoan("AliceTest", 200));
	    System.out.println("Repaying loan: Test passed");
	    assertEquals(200, bankTest.getLoan("AliceTest"));
	    System.out.println("Balance remaining loan: Test passed");		    

	    // Check total deposits in the bank
	    assertEquals(1200, bankTest.getTotalDeposits());
	    System.out.println("Total deposits in the bank: Test passed");
	    System.out.println("");             	    
	    
}
    
}