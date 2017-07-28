package com.example.android.trackme;

public class Habit {

	private String mHabitDescription;
	private int mHabitDaysCompleted;

	public Habit(String habitDescription, int daysCompleted) {
		this.mHabitDescription = habitDescription;
		this.mHabitDaysCompleted = daysCompleted;
	}

	public String getHabitDescription() {
		return mHabitDescription;
	}

	public int getHabitDaysCompleted() {
		return mHabitDaysCompleted;
	}
}
