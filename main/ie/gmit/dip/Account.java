package main.ie.gmit.dip;

//  Represents a single bank account with account holder name, balance, and loan amount
public class Account {
    private String accountHolder; // Name of the account holder
    private double balance;       // Current account balance
    private double loan;          // Outstanding loan amount

    // Constructor to create a new account
    public Account(String accountHolder, double balance) {
        this.accountHolder = accountHolder;
        this.balance = balance;
        this.loan = 0;
    }

    // Getter for the account holder's name
    public String getAccountHolder() {
        return accountHolder;
    }

    // Getter for the account balance
    public double getBalance() {
        return balance;
    }

    // Getter for the loan amount
    public double getLoan() {
        return loan;
    }

    // Method to deposit money into the account
    public boolean deposit(double amount) {
        if (amount <= 0 || amount >= 1_000_000) {
            return false; 
        }
        balance += amount;
        return true;
    }

    // Method to withdraw money from the account (only if balance is sufficient)
    public boolean withdraw(double amount) {
        if (amount > balance) return false; // Insufficient funds
        balance -= amount;
        return true;
    }

    /*
     * Method to approve a loan for the account (only if loan less than amount)
     * 
     * A banker is a fellow who lends you his umbrella when the sun is shining, 
     * but wants it back the minute it begins to rain. -Mark Twain 
     */ 
    public void approveLoan(double amount) {
    	loan += (amount > 0) ? amount : 0;
    }

    // Method to repay a part of the loan (only if amount <= loan)
    public boolean repayLoan(double amount) {
        if (amount <= 0 || amount > loan) {
            return false;  
        }
        loan -= amount;
        return true; 
    }
}

