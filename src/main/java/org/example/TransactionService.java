package org.example;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;


public class TransactionService {
    public static void ledgerScreen(List<Transaction> transactions, Scanner scanner){
        //ledger menu
        while (true){
            System.out.println("\n==== Ledger Menu ====");
            System.out.println("A.) Display all entries");
            System.out.println("D.) Display deposits");
            System.out.println("P.) Display due payments");
            System.out.println("R.) Display Reports");
            System.out.println("H.) Exit to HOME SCREEN");
            String choice = scanner.nextLine().toUpperCase();

            switch (choice){
                case "A":
                    displayEntries(transactions);
                    break;
                case "D":
                    filterDeposits(transactions);
                    break;
                case "P":
                    filterPayments(transactions);
                    break;
                case "R":
                    runReports(transactions, scanner);
                    break;
                case "H":
                    // should i throw the home menu into a method as well and just call it on
                    //the MAIN?
                    return;
                default:
                    System.out.println("Please select from the menu options above");
            }

        }
    }
    public static void addDeposit(List<Transaction> transactions, Scanner scanner){
        System.out.println("\n==== Deposit Screen ====");
        //logging local date and time
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        //prompting user for info
        System.out.print("Enter the description:");
        String description = scanner.nextLine();
        System.out.print("Enter the vendor:");
        String vendor = scanner.nextLine();
        System.out.print("Enter amount:");
        double amount = Double.parseDouble(scanner.nextLine());

        //creating transaction
        Transaction transaction = new Transaction(date, time, description, vendor, amount);
        //adding
        transactions.add(transaction);
        //writing to file
        FileManager.writeTransaction(transaction);
        //confirmation
        System.out.println("Deposit added!");
    }
    public static void makePayment(List<Transaction> transactions, Scanner scanner){
        System.out.println("\n=== Payment Screen ===");
        //logging local date and time
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        //prompting user for info
        System.out.print("Enter the description:");
        String description = scanner.nextLine();
        System.out.print("Enter the vendor:");
        String vendor = scanner.nextLine();
        System.out.print("Enter amount:");
        double amount = Double.parseDouble(scanner.nextLine());
        amount = -(amount);
        //creating transaction
        Transaction transaction = new Transaction(date, time, description, vendor, amount);
        //adding
        transactions.add(transaction);
        //writing to file
        FileManager.writeTransaction(transaction);
        //confirmation
        System.out.println("Payment recorded");
    }

}
