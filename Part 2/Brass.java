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
 * 21012014, 15 Jun 2022 1:23:08 pm
 */

public class Brass extends MusicalInstrument{
	
	private double weight;

	public Brass(String id, String name, String category, LocalDate latestServiceDate, double weight) {
		super(id, name, category, latestServiceDate);
		this.weight = weight;
	}
	
	public double getWeight() {
		return weight;
	}
}
