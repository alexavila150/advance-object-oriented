import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.FileHandler;

public class RunBank {
	public static void main(String[] args){
		//Welcome message
		System.out.println("Welcome to DisneyBank");
		
		//Ask user for file
		System.out.print("Enter file name with information: ");
		Scanner scnr = new Scanner(System.in);
		File infoFile = new File(System.getProperty("user.dir") + "\\" + scnr.nextLine());
		
		//Read file
		try {
			Scanner fileScnr = new Scanner(infoFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
