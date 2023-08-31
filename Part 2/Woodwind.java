/**
 * 
 */
package graded_assignment;

import java.time.LocalDate;

//import java.time.LocalDateTime;

/**
 * I declare that this code was written by me.
 * I will not copy or allow others to copy my code.
 * I understand that copying code is considered as plagiarism.
 *
 * 21012014, 15 Jun 2022 1:27:34 pm
 */

public class Woodwind extends MusicalInstrument {
	
	private boolean reed;

	public Woodwind(String id, String name, String category, LocalDate latestServiceDate, boolean reed) {
		super(id, name, category, latestServiceDate);
		this.reed = reed;
	}

	public boolean isReed() {
		return reed;
	}

	
}
