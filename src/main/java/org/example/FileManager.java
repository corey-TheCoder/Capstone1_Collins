package org.example;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    //load transactions method
    public static List<Transaction> loadTransactions(){
        //empty list
        List<Transaction> transactions = new ArrayList<>();

        try{
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/transactions.csv"));
            String line;

            while((line = reader.readLine())!= null){
                //splitting at the pipe symbol
                String[] parts = line.split("\\|");

                LocalDate date = LocalDate.parse(parts[0]);
                LocalTime time = LocalTime.parse(parts[1]);
                String description = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);

                //creating an object
                Transaction transaction = new Transaction(date, time, description, vendor, amount);
                //add to list
                transactions.add(transaction);

            }
            reader.close();

        }catch (IOException e){
            System.out.println("Error reading file");
        }

        return transactions;
    }
    public static void writeTransaction(Transaction transaction) {
        try {
            File file = new File("src/main/resources/transactions.csv");
            FileWriter fileWriter = new FileWriter(file, true);
            if (file.length() > 0) {
                fileWriter.write(System.lineSeparator());
            }

            fileWriter.write(String.format("%s|%s|%s|%s|%f", transaction.getDate(), transaction.getTime(), transaction.getDescription(),
                    transaction.getVendor(), transaction.getAmount()));

            fileWriter.close();
        }catch(IOException ex) {
            System.out.println("Error writing to file");
        }
    }

}
