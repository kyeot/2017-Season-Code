package org.usfirst.frc.team2783.tools;

import java.util.ArrayList;

public class MovingAverage {
	
	private Integer numRecordsToAverage;
	private ArrayList<Double> storedValues;
		
	public MovingAverage() {
		this(10);
	}

	public MovingAverage(Integer numRecordsToAverage) {
		this.numRecordsToAverage = numRecordsToAverage;
		storedValues = new ArrayList<Double>();
	}

	public Double addValue(Double value) {
		// Add the value to the array
		storedValues.add(value);
		
		// If the array now has too many entries, delete the oldest one
		if (storedValues.size() > numRecordsToAverage) {
			storedValues.remove(0);
		}
		
		// Return the average calculated with the new numbers
		return getAverage();
	}
	
	public Double getAverage() {
		// If the list of stored values isn't empty
		if (!storedValues.isEmpty()) {
			// Sum all of the entries in the storedValues array
			Double sum = 0.0;
			for (Double value : storedValues) {
				sum += value;
			}
			
			// Return an average value
			return sum / storedValues.size();
		} else {
			// Return null if there are no entries in storedValues
			return null;
		}
	}
	
	public void clearValues() {
		// If the list of stored value's isn't empty
		if (!storedValues.isEmpty()) {
			// Clear the backlog 
			storedValues.clear();
		}
	}
}