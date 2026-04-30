package org.example;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.example.Main.scanner;


public class TransactionService {

    //MY METHODS

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
                    runReports(transactions);
                    break;
//                case "H":
//                    // should i throw the home menu into a method as well and just call it on
//                    //the MAIN?
//                    return;
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
    public static void displayEntries(List<Transaction> transactions){
        //lists append to end, looping from the end for earlier results
        for (int i = transactions.size()-1; i >=0; i--){
            //get object at that index
            Transaction transaction = transactions.get(i);

            //formatting
            System.out.print(transaction.toString());
        }
    }
    public static void filterDeposits(List<Transaction> transactions) {
        for (int i = transactions.size() - 1; i >= 0; i--) {
            //get object at that index
            Transaction transaction = transactions.get(i);
            //if there is an amount
            if (transaction.getAmount() > 0){
                System.out.println(transaction);
            }
        }
    }
    public static void filterPayments(List<Transaction> transactions) {
        for (int i = transactions.size() - 1; i >= 0; i--) {
            //get object at that index
            Transaction transaction = transactions.get(i);
            //if there is an amount
            if (transaction.getAmount() < 0){
                System.out.println(transaction);
            }
        }
    }

    public static void runReports(List<Transaction> transactions){
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        System.out.println("\n==== Reports Menu ====");
        System.out.println("1.) Month to date inquiries");
        System.out.println("2.) Previous month inquiries");
        System.out.println("3.) Year to date inquiries");
        System.out.println("4.) Previous year inquiries");
        System.out.println("5.) Search by company name:");
        System.out.println("Back to Ledger Menu(0)");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice){
            case 1:
                monthToDate(transactions);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 0:
                break;
            default:
                System.out.println("Please choose a number 0 - 5 to continue");
                break;
        }

    }
    public static void monthToDate(List<Transaction> transactions){
        LocalDate today = LocalDate.now();
        for (int i = transactions.size() - 1; i >= 0; i--) {
            //get object at that index
            Transaction transaction = transactions.get(i);

            if (today.getMonth() == transaction.getDate().getMonth() &&
            today.getYear() == transaction.getDate().getYear()){
                System.out.println(transaction);
            }
        }
    }
    public static void prevMonth(List<Transaction> transactions){
        //today's date, can be compared to current month and year
        LocalDate today = LocalDate.now();
        //return month as an integer, easier to compare
        int currentMonth = today.getMonthValue();
        //current year
        int currentYear = today.getYear();
        //previous Year
        int prevMonth = currentMonth - 1;
        //CHATGPT
        int year = currentYear;

        //if currentMonth is 1(Jan) then prevMonth should be 12(Dec)
        if(currentMonth==1){
            prevMonth = 12;
            //year should go back one
            year=currentYear -1;
        }
        for (int i = transactions.size() - 1; i >= 0; i--) {
            //get object at that index
            Transaction transaction = transactions.get(i);

            //if transaction month = prevMonth
            //&& transaction year = year
            if (transaction.getDate().getMonthValue() == prevMonth &&
            transaction.getDate().getYear() == year){
                System.out.println(transaction);
            }

        }
    }

    public static void yearToDate(List<Transaction> transactions){
        LocalDate today = LocalDate.now();
        for (int i = transactions.size() - 1; i >= 0; i--) {
            //get object at that index
            Transaction transaction = transactions.get(i);
            if (today.getYear() == transaction.getDate().getYear() &&
                    //transaction date is not after today (IN THE FUTURE)
            !transaction.getDate().isAfter(today)){
                System.out.println(transaction);
            }
        }
    }
    public static void prevYear(List<Transaction> transactions){
        //logic/variables for previous year
        int prevYear = LocalDate.now().getYear() - 1;
        for (int i = transactions.size() - 1; i >= 0; i--) {
            //get object at that index
            Transaction transaction = transactions.get(i);
            if (transaction.getDate().getYear()== prevYear){
                System.out.println(transaction);
            }
        }
    }
    public static void searchByVendor(List<Transaction> transactions, Scanner scanner){
        //USER input
        System.out.println("Enter the vendor name: ");
        String vendor = scanner.nextLine();

        for (int i = transactions.size() - 1; i >= 0; i--) {
            //get object at that index
            Transaction transaction = transactions.get(i);
        }
        //vendor
    }


}
