package com.example.android.trackme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

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
		if (mPrimaryView.getText() == "") {
			mPrimaryView.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.empty_view_dim));
			mPrimaryView.setGravity(Gravity.CENTER);
			mPrimaryView.setText("You can do this. Add habits to get started.");
		}
	}
}
