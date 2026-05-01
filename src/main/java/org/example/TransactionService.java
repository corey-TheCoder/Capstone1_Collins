package org.example;
import java.sql.SQLOutput;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;



public class TransactionService {
    //my colors
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String CYAN = "\u001B[36m";

    //MY METHODS

    //input/scanner
    public static void ledgerScreen(List<Transaction> transactions, Scanner scanner){
        //ledger menu
        //title
        System.out.println(CYAN + "\n=====================" + RESET);
        System.out.println(CYAN + "     Ledger Menu     " + RESET);
        System.out.println(CYAN + "=====================" + RESET);
        while (true){

            System.out.println("A.) Display all entries");
            System.out.println("D.) Display deposits");
            System.out.println("P.) Display due payments");
            System.out.println("R.) Display Reports");
            System.out.println("H.) Exit to HOME SCREEN");
            System.out.println("---------------------------");
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
                    System.out.println(RED + "Please select from the menu options above" + RESET);
            }

        }
    }
    //input/scanner
    public static void addDeposit(List<Transaction> transactions, Scanner scanner) {
        System.out.println(CYAN + "\n===================" + RESET);
        System.out.println(CYAN + "   MAKE A DEPOSIT   " + RESET);
        System.out.println(CYAN + "===================" + RESET);
        //logging local date and time
        LocalDate date = LocalDate.now();
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
            System.out.print("Enter amount: ");

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
              System.out.println(GREEN + "\n----------------------" + RESET);
            System.out.println(GREEN + "  DEPOSIT SUCCESSFUL!  " + RESET);
            System.out.println(GREEN + "-----------------------" + RESET);
        }

        //input/scanner
    public static void makePayment(List<Transaction> transactions, Scanner scanner){
        System.out.println(CYAN + "\n======================" + RESET);
        System.out.println(CYAN + "    MAKE A PAYMENT   " + RESET);
        System.out.println(CYAN + "=====================" + RESET);
        //logging local date and time
        LocalDate date = LocalDate.now();
        //readability
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
                System.out.println(RED + "\nThis field cannot be blank, try again" + RESET);
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
                System.out.println(RED + "\nThis field cannot be blank, try again" + RESET);
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
                    System.out.println(RED +"\nAmount should be greater than 0." + RESET);
                }
            } catch (NumberFormatException ex) {
                System.out.println(RED + "\nInvalid input, enter numbers only" + RESET);
            }
        }
        //payments must be negative
        amount = -(amount);
        //creating transaction
        Transaction transaction = new Transaction(date, time, description, vendor, amount);
        //adding and writing to file
        saveTransaction(transactions, transaction);
        System.out.println(GREEN + "\n----------------------" + RESET);
        System.out.println(GREEN + "  PAYMENT SUCCESSFUL!" + RESET);
        System.out.println(GREEN +  "----------------------" + RESET);
    }
    // no input/ just list
    public static void displayEntries(List<Transaction> transactions){
        System.out.println(CYAN + "\n================" + RESET);
        System.out.println(CYAN + "   TRANSACTIONS " + RESET);
        System.out.println(CYAN + "==================" + RESET);
        //lists append to end, looping from the end for earlier results
        for (int i = transactions.size()-1; i >=0; i--){
            //get object at that index
            Transaction transaction = transactions.get(i);

            //COLOR CODING
            if (transaction.getAmount() < 0){
                System.out.println(RED + transaction + RESET);
            }
            else {
                System.out.println(GREEN + transaction + RESET);
            }
        }
    }
    //no input just list
    public static void filterDeposits(List<Transaction> transactions) {
        System.out.println(CYAN + "\n=====================" + RESET);
        System.out.println(CYAN + "      DEPOSITS     " + RESET);
        System.out.println(CYAN + "=======================" + RESET);
        for (int i = transactions.size() - 1; i >= 0; i--) {
            //get object at that index
            Transaction transaction = transactions.get(i);
            //if there is an amount
            if (transaction.getAmount() > 0){
                System.out.println(GREEN + transaction + RESET);
            }
        }
    }
    //no input just list
    public static void filterPayments(List<Transaction> transactions) {
        System.out.println(CYAN + "\n===================" + RESET);
        System.out.println(CYAN + "      PAYMENTS       " + RESET);
        System.out.println(CYAN + "=====================" + RESET);
        for (int i = transactions.size() - 1; i >= 0; i--) {
            //get object at that index
            Transaction transaction = transactions.get(i);
            //if there is an amount
            if (transaction.getAmount() < 0){
                System.out.println(RED + transaction + RESET);
            }
        }
    }

    //input/scanner
    public static void runReports(List<Transaction> transactions, Scanner scanner){


        while(true) {
            System.out.println(CYAN + "\n=====================" + RESET);
            System.out.println(CYAN + "     Reports Menu    " + RESET);
            System.out.println(CYAN + "=====================" + RESET);
            System.out.println("1.) Month to date inquiries");
            System.out.println("2.) Previous month inquiries");
            System.out.println("3.) Year to date inquiries");
            System.out.println("4.) Previous year inquiries");
            System.out.println("5.) Search by vendor: ");
            System.out.println("0.) Back to Ledger Menu");


            int choice;
            //DEFENSE
            while (true) {
                System.out.println("----------------------------");
                System.out.print("Enter a number to continue: ");
                try {
                    //.trim() removes any unnecessary spaces before after entries
                    choice = Integer.parseInt(scanner.nextLine().trim());

                    //defense
                    if (choice >= 0 && choice <= 5)
                        break;
                    else {
                        System.out.println(RED + "\nPlease enter a number from 0 - 5." + RESET);
                    }
                } catch (NumberFormatException e) {
                    System.out.println(RED + "\nInvalid input. Number from 0 - 5 only." + RESET);
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
                    System.out.println(RED + "Please choose a number 0 - 5 to continue" + RESET);
                    break;
            }
        }

    }
    //no input just list
    public static void monthToDate(List<Transaction> transactions){
        LocalDate today = LocalDate.now();
        System.out.println(CYAN + "\n=====================" + RESET);
        System.out.println(CYAN + "    MONTH TO DATE    " + RESET);
        System.out.println(CYAN + "=====================" + RESET);
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
        System.out.println(CYAN + "\n=====================" + RESET);
        System.out.println(CYAN + "  PREVIOUS MONTH    " + RESET);
        System.out.println(CYAN + "=====================" + RESET);
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
        System.out.println(CYAN + "\n=====================" + RESET);
        System.out.println(CYAN + "    YEAR TO DATE    " + RESET);
        System.out.println(CYAN + "=====================" + RESET);
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
        System.out.println(CYAN + "\n=====================" + RESET);
        System.out.println(CYAN + "    PREVIOUS YEAR    " + RESET);
        System.out.println(CYAN + "=====================" + RESET);
        for (int i = transactions.size() - 1; i >= 0; i--) {
            //get object at that index
            Transaction transaction = transactions.get(i);
            if (transaction.getDate().getYear()== prevYear){
                System.out.println(transaction);
            }
        }
    }
    public static void searchByVendor(List<Transaction> transactions, Scanner scanner) {

        while (true) {
            //defensive variable
            boolean isFound = false;
            System.out.println(CYAN + "\n=====================" + RESET);
            System.out.println(CYAN + "   SEARCH BY VENDOR  " + RESET);
            System.out.println(CYAN + "=====================" + RESET);
            //USER input
            System.out.print("Enter the vendor name: ");
            String vendor = scanner.nextLine().trim();

            for (int i = transactions.size() - 1; i >= 0; i--) {
                //get object at that index
                Transaction transaction = transactions.get(i);

                //vendor
                if (transaction.getVendor().equalsIgnoreCase(vendor)) {
                    System.out.println(transaction);
                    //when vendor is found, change status to true for IF
                    isFound = true;
                }
            }
            if (isFound) {
                break;
            }
            else {
                System.out.println(RED + "\nNo transactions under that name. Try again." + RESET);
            }

        }
    }
    public static void saveTransaction(List<Transaction> transactions, Transaction transaction){
        transactions.add(transaction);
        FileManager.writeTransaction(transaction);
    }


}
