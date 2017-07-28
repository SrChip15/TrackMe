package com.example.android.trackme;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class HabitListAdapter extends ArrayAdapter<Habit> {

	private Context mContext;
	private List<Habit> mHabitsList;
	private LayoutInflater mInflater;

	public HabitListAdapter(Context context, List<Habit> objects) {
		super(context, 0, objects);
		this.mContext = context;
		this.mHabitsList = objects;
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mHabitsList.size();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			// No views in the recycle view pool
			// Inflate view for first time view render
			convertView = mInflater.inflate(R.layout.item_habit, parent, false);
		}

		// Setup listener
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Get clicked habit from list
				Habit clickedHabit = mHabitsList.get(position);

				// Create intent to send to editor
				Intent intent = new Intent(mContext, EditorActivity.class);

				// Send data packet
				intent.putExtra("triggerType", "existingHabit");
				intent.putExtra("habitDesc", clickedHabit.getHabitDescription());
				intent.putExtra("daysCompleted", String.valueOf(clickedHabit.getHabitDaysCompleted()));

				// Start editor activity
				mContext.startActivity(intent);
			}
		});

		// Get current item
		Habit currentHabitItem = mHabitsList.get(position);

		// Get TextView to display description of habit
		TextView habitDescTextView = (TextView) convertView.findViewById(R.id.habit_desc_text);
		habitDescTextView.setText(currentHabitItem.getHabitDescription());

		// Get TextView to display number of days the habit was completed
		TextView daysCompletedTextView = (TextView) convertView.findViewById(R.id.habit_days_text);
		String daysCompleted = String.valueOf(currentHabitItem.getHabitDaysCompleted());
		daysCompletedTextView.setText(daysCompleted);

		// Return view
		return convertView;
	}


}
