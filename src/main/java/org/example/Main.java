package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        while (true){
            System.out.println("\n==== Home Menu ====");
            System.out.println("D.) to add a deposit");
            System.out.println("P.) to make a payment(debt)");
            System.out.println("L.) to display the Ledger screen");
            System.out.println("X.) to exit");
            System.out.print("Enter a letter to continue: ");
            String choice = scanner.nextLine().trim().toUpperCase();

            switch(choice){
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
                    break;
                default:
                    System.out.println("Please select from the menu options above");
            }
        }
    }
}