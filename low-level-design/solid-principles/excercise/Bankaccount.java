// ============================================================
// Exercise 01 — Bank Account (Encapsulation)
// OOP Concept: Encapsulation
// Goal: private data + controlled public access only
// ============================================================

//package low-level-design.solid-principles.excercise;
import java.util.ArrayList;
public class Bankaccount {
    private String accountNumber;
    private double balance;
    private String accountHolderName;
    public static final double MINIMUM_BALANCE = 100.0;
    private ArrayList<String> transactionHistory = new ArrayList<>();
    public Bankaccount(String accountNumber, String accountHolderName, double initialDeposit) {
         if (initialDeposit < MINIMUM_BALANCE) {
            throw new IllegalArgumentException("Initial deposit must be at least " + MINIMUM_BALANCE);
        }
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialDeposit;
        transactionHistory.add("Account created with initial deposit of $" + initialDeposit);
    }
    public String getAccountNumber() {
        String last4=accountNumber.substring(accountNumber.length() - 4);
        return "**** **** **** " + last4;
    }
    public String getAccountHolderName() {
        return accountHolderName;
    }
    public double getBalance() {
        return balance;
    }
    public void deposit(double amount){
        if(amount<0){
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance+=amount;
        System.out.println("Deposited: " + amount);
        transactionHistory.add("Deposited: " + amount);
    }
    public void withdraw (double amount){
        if(amount<0){
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if(balance-amount<MINIMUM_BALANCE){
            throw new IllegalArgumentException("Insufficient funds. Minimum balance must be maintained.");
        }
        balance-=amount;
        System.out.println("Withdrew: " + amount);
        transactionHistory.add("Withdrew: " + amount);
    }
    public void transfer(Bankaccount targetaccount,double amount){
        if(amount<0){
            throw new IllegalArgumentException("Transfer amount must be positive");
        }
        if(this.balance - amount < MINIMUM_BALANCE){
            throw new IllegalArgumentException("Insufficient funds. Minimum balance must be maintained.");
        }
        this.withdraw(amount);
        targetaccount.deposit(amount);
    }
    public void printAccountDetails(){
        System.out.println("Account Number: " + getAccountNumber());
        System.out.println("Account Holder: " + getAccountHolderName());
        System.out.println("Balance: $" + getBalance());
    }
    public void printTransactionHistory(){
        System.out.println("Transaction History:");
        for(String transaction : transactionHistory){
            System.out.println(transaction);
        }
    }
    class savingsAccount extends Bankaccount{
        private double interestRate;
        public savingsAccount(String accountNumber, String accountHolderName, double initialDeposit, double interestRate) {
            super(accountNumber, accountHolderName, initialDeposit);
            this.interestRate = interestRate;
        }
        public void applyInterest(){
            double interest = getBalance() * interestRate / 100;
            deposit(interest);
            System.out.println("Applied interest: " + interest);
        }
    }
    public static void main(String[] args) {
        Bankaccount account1 = new Bankaccount("1234567890", "John Doe", 500.0);
        Bankaccount account2 = new Bankaccount("1234567891", "pie Doe", 1000.0);
      savingsAccount savingsAccount1 = account1.new savingsAccount("1234567892", "John Doe", 500.0, 5.0);
        account1.printAccountDetails();
        account1.deposit(200.0);
        account1.withdraw(150.0);
        account1.transfer(account2, 100.0);
        account1.printTransactionHistory();
        account1.printAccountDetails();
        account2.printAccountDetails();

    }
}
