package com.example.android.trackme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.android.trackme.data.HabitContract.HabitEntry;
import com.example.android.trackme.data.HabitDbHelper;

public class MainActivity extends AppCompatActivity {

	private HabitDbHelper mDbHelper;
	private TextView mPrimaryView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Primary text view hookup
		mPrimaryView = (TextView) findViewById(R.id.primary_view);

		// Setup FAB to open EditorActivity
		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, EditorActivity.class);
				startActivity(intent);
			}
		});

		// To access our database, we instantiate our subclass of SQLiteOpenHelper
		// and pass the context, which is the current activity.
		mDbHelper = new HabitDbHelper(MainActivity.this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		displayDatabaseInfo();
		/*if (mPrimaryView.getText() == "") {
			mPrimaryView.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.empty_view_dim));
			mPrimaryView.setGravity(Gravity.CENTER);
			mPrimaryView.setText("You can do this. Add habits to get started.");
		}*/

	}

	private void displayDatabaseInfo() {
		// Get readable instance of the database
		SQLiteDatabase db = mDbHelper.getReadableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
				HabitEntry.COLUMN_HABIT_DESC,
				HabitEntry.COLUMN_COMPLETED_DAYS
		};

		Cursor cursor = db.query(
				HabitEntry.TABLE_NAME,                     // The table to query
				projection,                                // The columns to return
				null,                                      // The columns for the WHERE clause
				null,                                      // The values for the WHERE clause
				null,                                      // don't group the rows
				null,                                      // don't filter by row groups
				null                                       // The sort order
		);

		try {
			mPrimaryView.setText("The habit table contains " + cursor.getCount() + " habits\n\n");

			// Create table headers
			mPrimaryView.append(HabitEntry.COLUMN_HABIT_DESC + " - " +
					HabitEntry.COLUMN_COMPLETED_DAYS + "\n");

			// Get column index
			int descColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_DESC);
			int daysCompletedColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_COMPLETED_DAYS);

			// Iterate over rows and extract information
			while (cursor.moveToNext()) {

				String currentHabitDesc = cursor.getString(descColumnIndex);
				int currentHabitDaysCompleted = cursor.getInt(daysCompletedColumnIndex);

				// Display extracted information in primary view
				mPrimaryView.append("\n" + currentHabitDesc + " - " +
						currentHabitDaysCompleted);

			}
		} finally {
			// Close the cursor and release resources associated with the cursor
			cursor.close();
		}
	}
}
