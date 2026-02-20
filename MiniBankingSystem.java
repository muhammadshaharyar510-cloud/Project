import java.io.*;
import java.util.*;

// Account class
class Account implements Serializable {

    private int accountNumber;
    private String accountHolderName;
    private double balance;
    private final double MIN_BALANCE = 1000;

    public Account(int accountNumber, String accountHolderName, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;

        if (balance < MIN_BALANCE) {
            System.out.println("Minimum balance is 1000. Setting balance to 1000.");
            this.balance = MIN_BALANCE;
        } else {
            this.balance = balance;
        }
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposit successful!");
    }

    public void withdraw(double amount) {
        if (balance - amount >= MIN_BALANCE) {
            balance -= amount;
            System.out.println("Withdrawal successful!");
        } else {
            System.out.println("Cannot withdraw. Minimum balance must be maintained.");
        }
    }

    public void displayAccount() {
        System.out.println("----------------------------");
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Name           : " + accountHolderName);
        System.out.println("Balance        : " + balance);
        System.out.println("----------------------------");
    }
}

public class MiniBankingSystem {

    static final String FILE_NAME = "accounts.dat";

    // Read all accounts from file
    public static List<Account> readAllAccounts() {
        List<Account> accounts = new ArrayList<>();

        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) return accounts;

            ObjectInputStream input =
                    new ObjectInputStream(new FileInputStream(FILE_NAME));

            accounts = (List<Account>) input.readObject();
            input.close();

        } catch (Exception e) {
            System.out.println("No records found.");
        }

        return accounts;
    }

    // Save all accounts to file
    public static void saveAllAccounts(List<Account> accounts) {
        try {
            ObjectOutputStream output =
                    new ObjectOutputStream(new FileOutputStream(FILE_NAME));

            output.writeObject(accounts);
            output.close();

        } catch (Exception e) {
            System.out.println("Error saving data.");
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        List<Account> accounts = readAllAccounts();

        int choice;

        do {
            System.out.println("\n====== MINI BANKING SYSTEM ======");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Display All Accounts");
            System.out.println("5. Search Account");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter Account Number: ");
                    int accNo = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Initial Balance: ");
                    double balance = sc.nextDouble();

                    Account newAccount = new Account(accNo, name, balance);
                    accounts.add(newAccount);
                    saveAllAccounts(accounts);

                    System.out.println("Account created successfully!");
                    break;

                case 2:
                    System.out.print("Enter Account Number: ");
                    int depositAccNo = sc.nextInt();

                    for (Account acc : accounts) {
                        if (acc.getAccountNumber() == depositAccNo) {
                            System.out.print("Enter amount to deposit: ");
                            double amount = sc.nextDouble();
                            acc.deposit(amount);
                            saveAllAccounts(accounts);
                            break;
                        }
                    }
                    break;

                case 3:
                    System.out.print("Enter Account Number: ");
                    int withdrawAccNo = sc.nextInt();

                    for (Account acc : accounts) {
                        if (acc.getAccountNumber() == withdrawAccNo) {
                            System.out.print("Enter amount to withdraw: ");
                            double amount = sc.nextDouble();
                            acc.withdraw(amount);
                            saveAllAccounts(accounts);
                            break;
                        }
                    }
                    break;

                case 4:
                    for (Account acc : accounts) {
                        acc.displayAccount();
                    }
                    break;

                case 5:
                    System.out.print("Enter Account Number to Search: ");
                    int searchAccNo = sc.nextInt();
                    boolean found = false;

                    for (Account acc : accounts) {
                        if (acc.getAccountNumber() == searchAccNo) {
                            acc.displayAccount();
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("Account not found.");
                    }
                    break;

                case 6:
                    System.out.println("Thank you for using the system!");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 6);

        sc.close();
    }
}