/**
 * 
 */
package graded_assignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

/**
 * I declare that this code was written by me.
 * I will not copy or allow others to copy my code.
 * I understand that copying code is considered as plagiarism.
 *
 * 21012014, 15 Jul 2022 12:05:22 am
 */

public class OrchestraA {

	private Connection conn;
	private Statement statement;
	private ResultSet rs;
	
	public static void main(String[] args) {
			OrchestraA orc = new OrchestraA();
		    orc.start();
	}
	
	public void start() {

		
		String connectionString = "jdbc:mysql://localhost:3306/instruments?serverTimezone=UTC";
		String userid = "root";
		String password = "";
		
		try {
			this.conn = DriverManager.getConnection(connectionString, userid, password);
			this.statement = conn.createStatement();
		
		int option = 0;
			while (option != 3) {
				menu();
				option = Helper.readInt("Enter choice > ");
			
				if (option == 1) {
					viewAll();
				} else if (option == 2) {
					viewAllByCategory();
				} else if (option == 3) {
					rs.close();
					statement.close();
					conn.close();
					System.out.println("Goodbye!");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	
	
	private void menu() {
	    Helper.line(150,"=");
	    System.out.println("MUSICAL INSTRUMENTS MANAGEMENT APP \n");
	    System.out.println("  1. View All Instruments \n  2. View Instruments by Category \n  3. Quit \n");
	  }

	private void viewAll() {    // Prints out everything directly from SQL
		String out = String.format("%-10s %-20s %-20s %-10s %-10s %-20s %-10s \n", "ID", "Name" , "Category", "Reed", "Weight", "Num of Strings", "Service date");
		try {
			this.rs = statement.executeQuery("SELECT * FROM instrument INNER JOIN service_date ON instrument.ID = service_date.ID");
			
			while (rs.next()) { 
				String id = rs.getString("ID");
				String name = rs.getString("Name");  
				String cat = rs.getString("Category");
				int reed = rs.getInt("ReedInstrument");
				double weight = rs.getDouble("Weight");
				int numString = rs.getInt("NumStrings");
				LocalDate dateS = rs.getDate("DateServiced").toLocalDate();
				out += String.format("%-10s %-20s %-20s %-10d %-10.2f %-20d %-10s \n", id, name, cat, reed, weight, numString, dateS);
				
			}
			System.out.println(out);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void viewAllByCategory() {
		System.out.println("  1. Woodwind \n  2. String \n  3. Brass \n");
		int search = Helper.readInt("Select number option > ");
		
		String out = "";
		
		if (search == 1) {
			out += String.format("%-10s %-20s %-20s %-10s %-10s \n", "ID", "Name" , "Category", "Is Reed", "Service date");
		} else if (search == 2) {
			out += String.format("%-10s %-20s %-20s %-10s %-10s \n", "ID", "Name" , "Category", "Num of Strings", "Service date");
		} else if (search == 3) {
			String.format("%-10s %-20s %-20s %-10s %-10s \n", "ID", "Name" , "Category", "Weight", "Service date");	
		}
		
		try {
			this.rs = statement.executeQuery("SELECT * FROM instrument INNER JOIN service_date ON instrument.ID = service_date.ID");
			
			while (rs.next()) { 
				String id = rs.getString("ID");
				String name = rs.getString("Name");  
				String cat = rs.getString("Category");
				int reed = rs.getInt("ReedInstrument");
				double weight = rs.getDouble("Weight");
				int numString = rs.getInt("NumStrings");
				LocalDate dateS = rs.getDate("DateServiced").toLocalDate();
				if (search == 1 && cat.equals("Woodwind")) {
					if (reed == 0) {
						out += String.format("%-10s %-20s %-20s %-10s %-10s \n", id, name, cat, false, dateS);						
					} else {
						out += String.format("%-10s %-20s %-20s %-10s %-10s \n", id, name, cat, true, dateS);
					}
				} else if (search == 2 && cat.equals("String")) {
					out += String.format("%-10s %-20s %-20s %-20d %-10s \n", id, name, cat, numString, dateS);
				} else if (search == 3 && cat.equals("Brass")) {
					out += String.format("%-10s %-20s %-20s %-10.2f %-10s \n", id, name, cat, weight, dateS);					
				}
			}
			System.out.println(out);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}