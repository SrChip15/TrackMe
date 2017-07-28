package com.example.android.trackme;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.trackme.data.HabitContract.HabitEntry;
import com.example.android.trackme.data.HabitDbHelper;

public class EditorActivity extends AppCompatActivity {

	private EditText mHabitDesc;
	private EditText mHabitDays;
	private Button mHabitDaysIncrement;
	private Button mHabitDaysDecrement;
	private Button mDeleteHabit;
	private HabitDbHelper mDbHelper;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editor);

		// Find all relevant views that we will need to read user input from
		mHabitDesc = (EditText) findViewById(R.id.edit_habit_desc);
		mHabitDays = (EditText) findViewById(R.id.days_from_db_text);
		mHabitDaysIncrement = (Button) findViewById(R.id.plus_button);
		mHabitDaysDecrement = (Button) findViewById(R.id.minus_button);
		mDeleteHabit = (Button) findViewById(R.id.delete_button);

		// Setup access to db
		mDbHelper = new HabitDbHelper(EditorActivity.this);
	}

	public void saveHabit(View view) {
		// Get writable instance of the database
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		// Get user inputs
		String habitDescription = mHabitDesc.getText().toString().trim();
		int habitDaysCompleted = Integer.parseInt(mHabitDays.getText().toString().trim());

		// Values
		ContentValues values = new ContentValues();
		values.put(HabitEntry.COLUMN_HABIT_DESC, habitDescription);
		values.put(HabitEntry.COLUMN_COMPLETED_DAYS, habitDaysCompleted);

		// Insert record into database
		long rowId = db.insert(HabitEntry.TABLE_NAME, null, values);

		// Check on success of recored insertion into table
		if (rowId == -1) {
			Toast.makeText
					(
							EditorActivity.this,
							"Error creating new habit",
							Toast.LENGTH_SHORT
					)
					.show();
		} else {
			Toast.makeText
					(
							EditorActivity.this,
							"New habit created at row number " + rowId,
							Toast.LENGTH_SHORT
					)
					.show();

		}

		// Intent to home
		Intent intent = new Intent(EditorActivity.this, MainActivity.class);
		startActivity(intent);

	}

	public void decrementHabitDays(View view) {
		// Get the habit days completed pre-fetched from the database
		int habitDays = Integer.parseInt(mHabitDays.getText().toString().trim());

		// Increment habit days completed by 1
		habitDays--;

		// Set revised habit days completed as user input
		mHabitDays.setText(String.valueOf(habitDays));
	}

	public void incrementHabitDays(View view) {
		// Get the habit days completed pre-fetched from the database
		int habitDays = Integer.parseInt(mHabitDays.getText().toString().trim());

		// Increment habit days completed by 1
		habitDays++;

		// Set revised habit days completed as user input
		mHabitDays.setText(String.valueOf(habitDays));
	}
}
