package com.xplink.android.fragment;

import com.xplink.android.carchecklist.R;

import android.app.Fragment; 
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RecordCheckList extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.record_activity, container, false);
		return v;
	}
}