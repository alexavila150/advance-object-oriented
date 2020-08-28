import javax.security.auth.callback.CallbackHandler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Scanner;

import static com.sun.corba.se.impl.util.Utility.printStackTrace;

public class Main {
    public static void main(String[] args) {
    
        HashSet<Checking> checkings = new HashSet<>();
        //Read File
        try {
            Scanner fileScnr = new Scanner(
                new File("D:\\UTEP\\2020 Fall\\AOOP\\advance-object-oriented\\Bank\\src\\BankUsers.csv"));
    
            //For every line make a new Checking instance
            fileScnr.nextLine(); //Ignores first line
            while(fileScnr.hasNextLine()){
                String line = fileScnr.nextLine();
                String[] attributes = line.split(",");
                checkings.add(
                    new Checking(
                        attributes[0],
                        attributes[1],
                        Integer.parseInt(attributes[2]),
                        Boolean.parseBoolean(attributes[3]),
                        Boolean.parseBoolean(attributes[4]),
                        Double.parseDouble(attributes[5]),
                        Double.parseDouble(attributes[6].substring(0,attributes[6].length() - 1))
                    )
                );
            }
            
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        
        ATM atm = new ATM(checkings);
        
        while(true){
            atm.optionsMenu();
        }
    }
}
