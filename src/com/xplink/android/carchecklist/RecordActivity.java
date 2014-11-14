package com.xplink.android.carchecklist;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class RecordActivity extends Activity {

	private AdView adView;
	// String admonId = "a151ef84c7b0a98";
	String admonId = "ca-app-pub-9957173043484675/3220197247";

	private TextView titleRecordLabel, nameRecordLabel;
	private Typeface type;
	private Button saveBtn, cancelBtn;
	private RelativeLayout recordMainLayout, recordLayout;
	private TableLayout saveForm;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record_activity);

		type = Typeface.createFromAsset(getAssets(), "Circular.ttf");
		titleRecordLabel = (TextView) findViewById(R.id.titleRecordLabel);
		nameRecordLabel = (TextView) findViewById(R.id.nameRecordLabel);

		titleRecordLabel.setTypeface(type);
		nameRecordLabel.setTypeface(type);

		saveBtn = (Button) findViewById(R.id.saveRecord);
		cancelBtn = (Button) findViewById(R.id.cancelRecord);

		recordMainLayout = (RelativeLayout) findViewById(R.id.recordMainLayout);
		recordLayout = (RelativeLayout) findViewById(R.id.recordLayout);
		saveForm = (TableLayout) findViewById(R.id.saveForm);

		saveBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				backToCheckList();
			}
		});

		cancelBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				backToCheckList();
			}
		});

		recordMainLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				backToCheckList();
			}
		});
		recordLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		saveForm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		// addMob
		LinearLayout layout = (LinearLayout) findViewById(R.id.admobInRecord);
		adView = new AdView(getApplicationContext());
		adView.setAdSize(AdSize.LEADERBOARD);
		adView.setAdUnitId(admonId);
		// Add the adView to it
		layout.addView(adView);
		// Initiate a generic request to load it with an ad
		AdRequest.Builder adRequestBuilder = new AdRequest.Builder();

		adView.loadAd(adRequestBuilder.build());

	}

	public void backToCheckList() {
		Intent i = new Intent(getApplicationContext(),
				CarCheckListActivity.class);
		finish();
		startActivity(i);
	}

}
