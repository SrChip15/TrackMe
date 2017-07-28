package com.example.android.trackme;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

public class HabitListAdapter extends ArrayAdapter {
	// TODO At the very end
	// private List<>
	public HabitListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
		super(context, resource, objects);
	}
}
