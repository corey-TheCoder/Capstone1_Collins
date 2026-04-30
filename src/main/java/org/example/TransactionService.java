package org.example;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;



public class TransactionService {

    //MY METHODS

    //input/scanner
    public static void ledgerScreen(List<Transaction> transactions, Scanner scanner){
        //ledger menu
        while (true){
            System.out.println("\n==== Ledger Menu ====");
            System.out.println("A.) Display all entries");
            System.out.println("D.) Display deposits");
            System.out.println("P.) Display due payments");
            System.out.println("R.) Display Reports");
            System.out.println("H.) Exit to HOME SCREEN");
            System.out.print("Enter a letter to continue: ");
            String choice = scanner.nextLine().trim().toUpperCase();

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
                    //returning to previous page
                    return;
                default:
                    System.out.println("Please select from the menu options above");
            }

        }
    }
    //input/scanner
    public static void addDeposit(List<Transaction> transactions, Scanner scanner) {
        System.out.println("\n==== Deposit Screen ====");

        //logging local date and time
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now().withNano(0);

        //prompting desc
        String description;
        while(true){
            System.out.print("Enter a description: ");
            description = scanner.nextLine().trim();

            //defense
            //if input is not blank, accept
            if (!description.isEmpty()){
                break;
            }else
            {
                System.out.println("\nThis field cannot be blank, try again");
            }
        }

        //prompting vendor
        String vendor;
        while(true){
            System.out.print("Enter the vendor name: ");
            vendor = scanner.nextLine().trim();

            //if input is not blank
            if (!vendor.isEmpty()){
                //accept
                break;
            }
            else {
                System.out.println("\nThis field cannot be blank, try again");
            }
        }

        double amount;
        while (true) {

            //prompting amount
            System.out.print("\nEnter amount: ");

            try {
                //attempt to parse
                amount = Double.parseDouble(scanner.nextLine().trim());

                //positive numbers
                if (amount > 0) {
                    break;
                } else {
                    System.out.println("\nAmount should be greater than 0.");
                }
            } catch (NumberFormatException ex) {
                System.out.println("\nInvalid input, enter numbers only");
            }
        }

            //creating transaction
            Transaction transaction = new Transaction(date, time, description, vendor, amount);
            //adding and writing to file
            saveTransaction(transactions, transaction);
            //confirmation
            System.out.println("\nDeposit successful!");
        }

        //input/scanner
    public static void makePayment(List<Transaction> transactions, Scanner scanner){
        System.out.println("\n==== Payment Screen ====");
        //logging local date and time
        LocalDate date = LocalDate.now();
        //readibility
        LocalTime time = LocalTime.now().withNano(0);

        //prompting desc
        String description;
        while(true){
            System.out.print("\nEnter a description: ");
            description = scanner.nextLine().trim();

            //defense
            //if input is not blank, accept
            if (!description.isEmpty()){
                break;
            }else
            {
                System.out.println("\nThis field cannot be blank, try again");
            }
        }

        //prompting vendor
        String vendor;
        while(true){
            System.out.print("\nEnter the vendor name: ");
            vendor = scanner.nextLine().trim();

            //if input is not blank
            if (!vendor.isEmpty()){
                //accept
                break;
            }
            else {
                System.out.println("\nThis field cannot be blank, try again");
            }
        }

        double amount;
        while (true) {

            //prompting amount
            System.out.print("\nEnter amount: ");

            try {
                //attempt to parse
                amount = Double.parseDouble(scanner.nextLine().trim());

                //positive numbers
                if (amount > 0) {
                    break;
                } else {
                    System.out.println("\nAmount should be greater than 0.");
                }
            } catch (NumberFormatException ex) {
                System.out.println("\nInvalid input, enter numbers only");
            }
        }
        //payments must be negative
        amount = -(amount);
        //creating transaction
        Transaction transaction = new Transaction(date, time, description, vendor, amount);
        //adding and writing to file
        saveTransaction(transactions, transaction);
        System.out.println("\nPayment successful");
    }
    // no input/ just list
    public static void displayEntries(List<Transaction> transactions){
        System.out.println("\n==== Transactions ====");
        //lists append to end, looping from the end for earlier results
        for (int i = transactions.size()-1; i >=0; i--){
            //get object at that index
            Transaction transaction = transactions.get(i);

            //formatting
            System.out.println(transaction.toString());
        }
    }
    //no input just list
    public static void filterDeposits(List<Transaction> transactions) {
        System.out.println("\n==== Deposits ====");
        for (int i = transactions.size() - 1; i >= 0; i--) {
            //get object at that index
            Transaction transaction = transactions.get(i);
            //if there is an amount
            if (transaction.getAmount() > 0){
                System.out.println(transaction);
            }
        }
    }
    //no input just list
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

    //input/scanner
    public static void runReports(List<Transaction> transactions, Scanner scanner){


        while(true) {
            System.out.println("\n==== Reports Menu ====");
            System.out.println("1.) Month to date inquiries");
            System.out.println("2.) Previous month inquiries");
            System.out.println("3.) Year to date inquiries");
            System.out.println("4.) Previous year inquiries");
            System.out.println("5.) Search by vendor: ");
            System.out.println("0.) Back to Ledger Menu");


            int choice;
            //DEFENSE
            while (true) {
                System.out.print("Enter a number to continue: ");
                try {
                    //.trim() removes any unnecessary spaces before after entries
                    choice = Integer.parseInt(scanner.nextLine().trim());

                    //defense
                    if (choice >= 0 && choice <= 5)
                        break;
                    else {
                        System.out.println("\nPlease enter a number from 0 - 5.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\nInvalid input. Number from 0 - 5 only.");
                }
            }


            switch (choice) {
                case 1:
                    monthToDate(transactions);
                    break;
                case 2:
                    prevMonth(transactions);
                    break;
                case 3:
                    yearToDate(transactions);
                    break;
                case 4:
                    prevYear(transactions);
                    break;
                case 5:
                    searchByVendor(transactions, scanner);
                    break;
                case 0:
                    //returning to previous screen
                    return;
                default:
                    System.out.println("Please choose a number 0 - 5 to continue");
                    break;
            }
        }

    }
    //no input just list
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
    //no input just list
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
    //no input just list
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


        //defensive variable
        boolean isFound = false;
        //USER input
        System.out.print("Enter the vendor name: ");
        String vendor = scanner.nextLine().trim();

        for (int i = transactions.size() - 1; i >= 0; i--) {
            //get object at that index
            Transaction transaction = transactions.get(i);

            //vendor
            if (transaction.getVendor().equalsIgnoreCase(vendor)){
                System.out.println(transaction);
                //when vendor is found, change status to true for IF
                isFound = true;
            }
        }
        if (!isFound){
            System.out.println("No transactions under that name.");
        }

    }
    public static void saveTransaction(List<Transaction> transactions, Transaction transaction){
        transactions.add(transaction);
        FileManager.writeTransaction(transaction);
    }


}
