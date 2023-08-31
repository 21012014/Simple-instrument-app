/**
 * 
 */
package graded_assignment;

import java.time.LocalDate;

/**
 * I declare that this code was written by me.
 * I will not copy or allow others to copy my code.
 * I understand that copying code is considered as plagiarism.
 *
 * 21012014, 28 Jun 2022 5:46:40 pm
 */

public class Strings extends MusicalInstrument {
	
	private int numOfStrings;

	public Strings(String id, String name, String category, LocalDate latestServiceDate, int numOfStrings) {
		super(id, name, category, latestServiceDate);
		this.numOfStrings = numOfStrings;
	}

	public int getNumOfStrings() {
		return numOfStrings;
	}

}
