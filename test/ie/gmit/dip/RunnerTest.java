package test.ie.gmit.dip;

import org.junit.jupiter.api.Test;

import main.ie.gmit.dip.BankingApp;
import main.ie.gmit.dip.Runner;

class RunnerTest {

    @Test
    void testRunBankingApp() {

        BankingApp bank = new BankingApp();
        Runner runner = new Runner();

        runner.runBankingApp(bank);
    }
}