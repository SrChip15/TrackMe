package com.example.android.trackme;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.trackme.data.HabitContract.HabitEntry;
import com.example.android.trackme.data.HabitDbHelper;

public class EditorActivity extends AppCompatActivity {

	/** EditText field to enter description of habit */
	private EditText mHabitDesc;

	/** EditText field to number of days the habit was completed */
	private EditText mHabitDays;

	/** Database helper that will provide us access to the database */
	private HabitDbHelper mDbHelper;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Setup UI
		setContentView(R.layout.activity_editor);

		// Find all relevant views that we will need to read user input from
		mHabitDesc = findViewById(R.id.edit_habit_desc);
		mHabitDays = findViewById(R.id.days_from_db_text);

		// Setup access to database
		mDbHelper = new HabitDbHelper(EditorActivity.this);

		// Find activity trigger type
		String qualifier = getIntent().getStringExtra("triggerType");

		// Check for existing habit qualifier
		if (qualifier != null && qualifier.equals("existingHabit")) {
			// Existing habit click has triggered the activity
			// Get habit data from intent and set text
			mHabitDesc.setText(getIntent().getStringExtra("habitDesc"));
			mHabitDays.setText(getIntent().getStringExtra("daysCompleted"));
		}
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

		// Check on success of record insertion into table
		if (rowId == -1) {
			// Error while inserting record
			Toast.makeText
					(
							EditorActivity.this,
							"Error creating new habit",
							Toast.LENGTH_SHORT
					)
					.show();
		} else {
			// Record was inserted successfully
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

	public void deleteHabit(View view) {
		// Find activity trigger type
		String qualifier = getIntent().getStringExtra("triggerType");
		String targetHabit = "";

		// Check for existing habit qualifier
		if (qualifier != null && qualifier.equals("existingHabit")) {
			// Existing habit click has triggered the activity
			targetHabit = (getIntent().getStringExtra("habitDesc"));
		}

		// Get writable instance of the database to delete the habit
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		// Define 'where' part of query
		String selection = HabitEntry.COLUMN_HABIT_DESC + " LIKE ?";

		// Specify arguments in placeholder order.
		String[] selectionArgs = { targetHabit };

		// Issue SQL statement.
		db.delete(HabitEntry.TABLE_NAME, selection, selectionArgs);

		// Intent to home
		Intent intent = new Intent(EditorActivity.this, MainActivity.class);
		startActivity(intent);

	}
}
