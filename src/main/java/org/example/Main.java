package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.example.TransactionService.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    //giving access to other methods (scope)
    static Scanner scanner = new Scanner(System.in);
    static List<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {

        //load csv info before running program
        transactions = FileManager.loadTransactions();


        //HOMEMENU
        while (true) {
            System.out.println(CYAN + "=========================================" + RESET);
            System.out.println(CYAN + "\t\tWELCOME TO THE LEDGER APP" + RESET);
            System.out.println(CYAN + "========================================" + RESET);
            System.out.println(CYAN + "\n=====================" + RESET);
            System.out.println(CYAN + "     HOME MENU    " + RESET);
            System.out.println(CYAN + "=====================" + RESET);
            System.out.println("\nD.) to add a deposit");
            System.out.println("\nP.) to make a payment");
            System.out.println("\nL.) to display the Ledger screen");
            System.out.println("\nX.) to exit");
            System.out.println("----------------------------");


            String choice;
            while (true) {
                System.out.print("\nEnter a letter to continue: ");
                choice = scanner.nextLine().trim().toUpperCase();

                if (choice.equals("D") || choice.equals("P")
                        || choice.equals("L") || choice.equals("X")) {
                    break;
                } else {
                    System.out.println(RED + "Invalid entry, try again" + RESET);
                }
            }
                //process choice
                switch (choice) {
                    case "D":
                        TransactionService.addDeposit(transactions, scanner);
                        break;
                    case "P":
                        TransactionService.makePayment(transactions, scanner);
                        break;
                    case "L":
                        TransactionService.ledgerScreen(transactions, scanner);
                        break;
                    case "X":
                        System.out.println("Thank you for banking with us!");
                        System.exit(0);
                }

        }
    }
}