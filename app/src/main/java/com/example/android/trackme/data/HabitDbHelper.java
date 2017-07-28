package com.example.android.trackme.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.trackme.data.HabitContract.HabitEntry;

public class HabitDbHelper extends SQLiteOpenHelper {
	// If you change the database schema, you must increment the database version.
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "habit tracker";

	/** SQL string to create table */
	private static final String SQL_CREATE_ENTRIES =
			"CREATE TABLE " + HabitEntry.TABLE_NAME + " (" +
					HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					HabitEntry.COLUMN_HABIT_DESC + " TEXT NOT NULL, " +
					HabitEntry.COLUMN_COMPLETED_DAYS + " INTEGER NOT NULL DEFAULT 0)";

	/** SQL string to delete table */
	private static final String SQL_DELETE_ENTRIES =
			"DROP TABLE IF EXISTS " + HabitEntry.TABLE_NAME;

	/**
	 * Create new DB Helper
	 */
	public HabitDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * Create new table using SQL table create string
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
	}

	/**
	 * Upgrade to new database when new database is created
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Nothing for now since only one database version exists
	}
}
