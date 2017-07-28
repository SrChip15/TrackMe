package com.example.android.trackme.data;

import android.provider.BaseColumns;

public final class HabitContract {
	// To prevent someone from accidentally instantiating the contract class,
	// make the constructor private.
	private HabitContract() {}

	/* Inner class that defines the table contents */
	public static class HabitEntry implements BaseColumns {
		public static final String TABLE_NAME = "habits";
		public static final String _ID = BaseColumns._ID;
		public static final String COLUMN_HABIT_DESC = "Description";
		public static final String COLUMN_COMPLETED_DAYS = "Completed Days";
	}
}
