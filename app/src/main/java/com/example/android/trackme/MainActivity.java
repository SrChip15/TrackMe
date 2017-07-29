package com.example.android.trackme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.trackme.data.HabitContract.HabitEntry;
import com.example.android.trackme.data.HabitDbHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	/**
	 * Database helper that will provide us access to the database
	 */
	private HabitDbHelper mDbHelper;

	/**
	 * List of habits
	 */
	private List<Habit> mHabitsList;

	/** Adapter for holding the habits data set */
	private HabitListAdapter mAdapter;

	private TextView mDescHeader;

	private TextView mDaysHeader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Setup UI
		setContentView(R.layout.activity_main);

		// Empty text view hookup
		TextView mEmptyView = (TextView) findViewById(R.id.empty_view);
		// Set text for empty view
		mEmptyView.setText(R.string.empty_view_text);
		// Position text
		mEmptyView.setGravity(Gravity.CENTER);
		// Dim the background color
		mEmptyView.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.empty_view_dim));

		// To access our database, we instantiate our subclass of SQLiteOpenHelper
		// and pass the context, which is the current activity.
		mDbHelper = new HabitDbHelper(MainActivity.this);

		// Initialize list of habits
		mHabitsList = new ArrayList<>();
		makeHabitsList();

		// Setup FAB to open EditorActivity
		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, EditorActivity.class);
				startActivity(intent);
			}
		});

		// Get the list view
		ListView listView = (ListView) findViewById(R.id.list);

		// Initialize adapter
		mAdapter = new HabitListAdapter(MainActivity.this, mHabitsList);

		// Remove headers if empty state
		mDescHeader= (TextView) findViewById(R.id.list_header_desc_text);
		mDaysHeader= (TextView) findViewById(R.id.list_header_days_text);
		if (mAdapter.isEmpty()) {
			removeHeaders();
		}

		// Set adapter for list view
		listView.setAdapter(mAdapter);

		// Set empty view when there are no habits
		listView.setEmptyView(mEmptyView);
	}

	/**
	 * Fetch all data from database and return the data as a cursor
	 */
	Cursor readFromDatabase() {
		// Get readable instance of the database
		SQLiteDatabase db = mDbHelper.getReadableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
				HabitEntry.COLUMN_HABIT_DESC,
				HabitEntry.COLUMN_COMPLETED_DAYS
		};

		// Perform a query on the habits table
		Cursor cursor = db.query(
				HabitEntry.TABLE_NAME,                     // The table to query
				projection,                                // The columns to return
				null,                                      // The columns for the WHERE clause
				null,                                      // The values for the WHERE clause
				null,                                      // don't group the rows
				null,                                      // don't filter by row groups
				null                                       // The sort order
		);

		// Return fetched cursor
		return cursor;
	}

	/**
	 * Make a list of all habits present in the database
	 */
	private void makeHabitsList() {
		Cursor cursor = readFromDatabase();
		try {
			// Get column index
			int descColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_DESC);
			int daysCompletedColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_COMPLETED_DAYS);

			// Iterate over rows and extract information
			while (cursor.moveToNext()) {

				String currentHabitDesc = cursor.getString(descColumnIndex);
				int currentHabitDaysCompleted = cursor.getInt(daysCompletedColumnIndex);

				// Add habit to list
				mHabitsList.add(new Habit(currentHabitDesc, currentHabitDaysCompleted));
			}
		} finally {
			// Close the cursor and release resources associated with the cursor
			cursor.close();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_delete_all_habits:
				deleteAll();
				mAdapter.flush();
				removeHeaders();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void deleteAll() {
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		// Delete the all contents of table
		db.delete(HabitEntry.TABLE_NAME, null, null);
	}

	private void removeHeaders() {
		// Remove headers
		mDescHeader.setVisibility(View.GONE);
		mDaysHeader.setVisibility(View.GONE);
	}
}
