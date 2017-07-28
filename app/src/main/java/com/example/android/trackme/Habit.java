package com.example.android.trackme;

/**
 * Data class object to hold habit information
 */
public class Habit {

	/** String to store description of habit */
	private String mHabitDescription;

	/** Integer variable to hold the number of days the habit was completed */
	private int mHabitDaysCompleted;

	/**
	 * Create new habit object
	 */
	public Habit(String habitDescription, int daysCompleted) {
		this.mHabitDescription = habitDescription;
		this.mHabitDaysCompleted = daysCompleted;
	}

	/**
	 * Return the description of the habit
	 */
	public String getHabitDescription() {
		return mHabitDescription;
	}

	/**
	 * Return the number of days the habit was completed
	 */
	public int getHabitDaysCompleted() {
		return mHabitDaysCompleted;
	}
}
