/**
 * 
 */
package graded_assignment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


/**
 * I declare that this code was written by me.
 * I will not copy or allow others to copy my code.
 * I understand that copying code is considered as plagiarism.
 *
 * 21012014, 15 Jun 2022 1:29:51 pm
 */

public class OrchestraB {
	
	private ArrayList<MusicalInstrument> instruments = new ArrayList<MusicalInstrument>();
	
	public static void main(String[] args) {
		
		OrchestraB orc = new OrchestraB();
	    orc.start();
	  }

	public void start() {

		
		String connectionString = "jdbc:mysql://localhost:3306/instruments?serverTimezone=UTC";
		String userid = "root";
		String password = "";
		
		DBUtil.init(connectionString, userid, password);
		
		
		int option = 0;
		
		load();
		while (option != 3) {
			menu();
			option = Helper.readInt("Enter choice > ");
		
			if (option == 1) {
				viewAll();
			} else if (option == 2) {
				viewAllByCategory();
			} else if (option == 3) {
			DBUtil.close();
			
			System.out.println("Goodbye!");
			}
		}
	}
	  
	  private void menu() {
	    Helper.line(150,"=");
	    System.out.println("MUSICAL INSTRUMENTS MANAGEMENT APP \n");
	    System.out.println("  1. View All Instruments \n  2. View Instruments by Category \n  3. Quit \n");
	  }
	  
	  private void viewAll() {
		  Helper.line(150, "-");
		  String out = "";
		  System.out.printf("%-10s %-15s %-16s %-15s %18s %20s \n", "ID", "INSTRUMENT", "CATEGORY", "INFO", "LAST SERVICED DATE", "MESSAGE");
		  Helper.line(150, "-");
		  for (MusicalInstrument i : instruments) {
			  if (i instanceof Strings) {
				  Strings s = (Strings)i;
				  out += String.format("%-10s %-15s %-16s %-15s %18s    %s \n", i.getId(), i.getName(), i.getCategory(), s.getNumOfStrings() + " Stringed", s.getLatestServiceDate(), i.getReminder());
			  } else if (i instanceof Woodwind) {
				  Woodwind w = (Woodwind)i;
				  if (w.isReed()) {
					  out += String.format("%-10s %-15s %-16s %-15s %18s    %s \n", i.getId(), i.getName(), i.getCategory(), "Is a reed", w.getLatestServiceDate(), i.getReminder());
				  } else {
					  out += String.format("%-10s %-15s %-16s %-15s %18s    %s \n", i.getId(), i.getName(), i.getCategory(), "Not a reed", w.getLatestServiceDate(), i.getReminder());
				  }
			  } else if (i instanceof Brass) {
				  Brass b = (Brass)i;
				  out += String.format("%-10s %-15s %-16s %-15s %18s    %s \n", i.getId(), i.getName(), i.getCategory(), b.getWeight() + " kg", b.getLatestServiceDate(), i.getReminder());
			  } 
		  } System.out.println(out);
	  }
	  	
	 
	  
	  private void viewAllByCategory() {
		  
		  int search = 0;
		  boolean print = true;
		  
		  while (search != 4) {
			  Helper.line(150, "-");
			  System.out.println("GIVEN CATEGORIES");
			  Helper.line(150, "-");
			  System.out.println("  1. Woodwind \n  2. String \n  3. Brass \n  4. Return to main menu \n");
			  search = Helper.readInt("Select number option > ");
			  String out = "";
			  for (MusicalInstrument i : instruments) {
				  if (i instanceof Strings && search == 2) {
					  Strings s = (Strings)i;
					  out += String.format("%-10s %-15s %-16s %-15s %18s    %s \n", i.getId(), i.getName(), i.getCategory(), s.getNumOfStrings() + " Stringed", s.getLatestServiceDate(), i.getReminder());
				  
				  } else if (i instanceof Woodwind && search == 1) {
					  Woodwind w = (Woodwind)i;
						  if (w.isReed()) {
							  out += String.format("%-10s %-15s %-16s %-15s %18s    %s \n", i.getId(), i.getName(), i.getCategory(), "Is a reed", w.getLatestServiceDate(), i.getReminder());
						  } else {
							  out += String.format("%-10s %-15s %-16s %-15s %18s    %s \n", i.getId(), i.getName(), i.getCategory(), "Not a reed", w.getLatestServiceDate(), i.getReminder());
						  }	
				  } else if (i instanceof Brass && search == 3) {
					  Brass b = (Brass)i;
				 	  out += String.format("%-10s %-15s %-16s %-15s %18s    %s \n", i.getId(), i.getName(), i.getCategory(), b.getWeight() + " kg", b.getLatestServiceDate(), i.getReminder());

				  } else if (search == 4) {
					  System.out.println("\n  <<< **BACK** <<<");
					  print = false;
					  break;
				  }
				  
			  }  if (print) {
				  Helper.line(150, "-");
				  System.out.printf("%-10s %-15s %-16s %-15s %18s %20s \n", "ID", "INSTRUMENT", "CATEGORY", "INFO", "LATEST SERVICED DATE", "MESSAGE");
				  Helper.line(150, "-");
				  System.out.println(out);				  
			  }
		  }	  
	  }
	  
	  private void load() {
		  try {
				String sql = "SELECT * FROM instrument INNER JOIN service_date ON instrument.ID = service_date.ID WHERE DateServiced in ((SELECT MAX(DateServiced) from service_date GROUP BY ID))";
				ResultSet rs = DBUtil.getTable(sql);
				
				while (rs.next()) { 
					String id = rs.getString("ID");
					String name = rs.getString("Name");  
					String cat = rs.getString("Category");
					int reed = rs.getInt("ReedInstrument");
					double weight = rs.getDouble("Weight");
					int numString = rs.getInt("NumStrings");
					LocalDate dateS = rs.getDate("DateServiced").toLocalDate();
					
					
					if (cat.equals("Woodwind")) {
						if (reed == 1) {
							instruments.add(new Woodwind(id, name, cat, dateS, true));													
						} else {
							instruments.add(new Woodwind(id, name, cat, dateS, false));
						}
					} else if (cat.equals("String")) {
						instruments.add(new Strings(id, name, cat, dateS, numString));						
					} else if (cat.equals("Brass")) {
						instruments.add(new Brass(id, name, cat, dateS, weight));						
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	  }

}