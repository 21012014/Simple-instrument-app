/**
 * 
 */
package graded_assignment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


/**
 * I declare that this code was written by me.
 * I will not copy or allow others to copy my code.
 * I understand that copying code is considered as plagiarism.
 *
 * 21012014, 15 Jun 2022 1:20:43 pm
 */

public abstract class MusicalInstrument implements ServiceReminder{
	
	private String id;
	private String name;
	private String category;
	private LocalDate latestServiceDate;
	private String reminder;
	
	public MusicalInstrument(String id, String name, String category, LocalDate latestServiceDate) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.latestServiceDate = latestServiceDate;
		reminder = "";
	}
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCategory() {
		return category;
	}
	
	public LocalDate getLatestServiceDate() {
			return latestServiceDate;
	}
	
	public String getReminder() {
		check();
		return reminder;
	}
	
	private void check() {
		if (this.latestServiceDate != null) {
			long diff = ChronoUnit.MONTHS.between(this.latestServiceDate, LocalDate.now());
			if (diff > 6) {
				this.reminder = "URGENT : Please send instrument for service/repair";
			} 						
		}
	}
		
}