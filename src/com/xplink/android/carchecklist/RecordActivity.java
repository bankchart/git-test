package com.xplink.android.carchecklist;

import java.sql.PreparedStatement; 

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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
	private EditText nameRecordEdit;
	private Typeface type;
	private Button saveBtn, cancelBtn;
	private RelativeLayout recordMainLayout, recordLayout;
	private TableLayout saveForm;

	private DBCarCheckList db;
	private SQLiteDatabase sqliteDB;

	public boolean isDuplicate(){
		
		//sqliteDB = openOrCreateDatabase("history", MODE_PRIVATE, null);
		String username = nameRecordEdit.getText().toString();
		username = username.trim();
		String sql = "select * from " + db.TABLE_NAME + " where " + db.COL_USERNAME + " = '" + username + "'";
		Cursor next = sqliteDB.rawQuery(sql, null);
		next.moveToFirst();
		int countRow = next.getCount();
		if(countRow == 0)
			return false;
		return true;
	}
	
	public String[] getKeyChecklist(){
		
		return null;
	}
	
	public void save() {
		
		final AlertDialog.Builder adb = new AlertDialog.Builder(this);
		final AlertDialog.Builder subAdb = new AlertDialog.Builder(this);
		adb.setTitle("Confirm dialog");
		adb.setMessage("Do you save your name ?");
		adb.setNegativeButton("Cancel", null);
		adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
			public void onClick(DialogInterface dialog, int arg1) {
				SharedPreferences shared = getSharedPreferences("mysettings", Context.MODE_PRIVATE);
				
				// ok wait serialize checklist data
				sqliteDB = db.getWritableDatabase();
				String username = nameRecordEdit.getText().toString();
				username = (username.length() == 0) ? "empty" : username;
				// INSERT
				String powerWeight = "power-" + shared.getInt("Powerbar", 0);
				String engineWeight = "engine-" + shared.getInt("Enginebar", 0);
				String exteriorWeight = "exterior-" + shared.getInt("Exteriorbar", 0);
				String interiorWeight = "interior-" + shared.getInt("Interiorbar", 0);
				String documentWeight = "document-" + shared.getInt("Documentbar", 0);
				
				String allWeight = powerWeight + "|" + engineWeight + "|" + exteriorWeight + 
						"|" + interiorWeight + "|" + documentWeight + "|";
				Bundle intent = getIntent().getExtras();
				
				String powerChecklist = intent.getString("power");
				String engineChecklist = intent.getString("engine");
				String exteriorChecklist = intent.getString("exterior");
				String interiorChecklist = intent.getString("interior");
				String documentChecklist = intent.getString("document");
				
				String allChecklist = powerChecklist + "|" + engineChecklist + "|" + exteriorChecklist + "|" +
									interiorChecklist + "|" + documentChecklist;
				
				String all = allChecklist + "**" + allWeight;
				Log.i("display_inserted", "inserted : " + all);
				
				String insertSql = "insert into " + db.TABLE_NAME + "(" + db.COL_USERNAME + ", " + 
				db.COL_CHECKLIST + ") VALUES ( '" + username + "', '" + all + "')";
				sqliteDB.execSQL(insertSql);		
				Log.i("writable", "completed.");
				sqliteDB.close();
				db.close();
				sqliteDB = db.getReadableDatabase();
				String querySql = "select * from " + db.TABLE_NAME;
				Cursor x = sqliteDB.rawQuery(querySql, null);
				x.moveToFirst();
				Log.i("cnt_table", "count of table : " + x.getCount());		
				
				while(x != null){
					String id = x.getString(0);
					String username2 = x.getString(1);
					String data = x.getString(2);
					String display = "id : " + id + ", username : " + username2 + ", data : " + data;
					Log.i("result", display);
					if(x.isLast())
						break;
					x.moveToNext();
				}
				x.close();
				sqliteDB.close();
				db.close();
				
				backToCheckList();
			}
		});
		adb.show();
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record_activity);

		db = new DBCarCheckList(this);
		sqliteDB = db.getWritableDatabase();

		type = Typeface.createFromAsset(getAssets(), "Circular.ttf");
		titleRecordLabel = (TextView) findViewById(R.id.titleRecordLabel);
		nameRecordLabel = (TextView) findViewById(R.id.nameRecordLabel);

		nameRecordEdit = (EditText) findViewById(R.id.nameRecordEdit);
		
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
				// backToCheckList();
				EditText nameRecordEdit = (EditText) findViewById(R.id.nameRecordEdit);
				String name = nameRecordEdit.getText().toString();
				// String sql = "INSERT INTO " + db.getDatabaseName() + " (" +
				// db.COL_USERNAME +
				// ", " + db.COL_CHECKLIST + ") VALUES('" + name + "', '" + +
				// "')";

				// readable carchecklist database
				/*
				 * SQLiteDatabase mydb = openOrCreateDatabase("carchecklist",
				 * MODE_PRIVATE, null); Cursor next =
				 * mydb.rawQuery("select * from history", null);
				 * next.moveToFirst(); String index1 = next.getString(1); String
				 * index2 = next.getString(2);
				 * 
				 * Log.i("index1", "index 1 : " + index1); Log.i("idnex2",
				 * "index 2 : " + index2);
				 * 
				 * mydb.close();
				 */

				save();
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
		SharedPreferences shared = getSharedPreferences("mysettings", MODE_PRIVATE);
		Editor editor = shared.edit();
		editor.clear();
		editor.commit();
		startActivity(i);
	}
	@Override
	public void onBackPressed(){
		SharedPreferences shared = getSharedPreferences("mysettings", Context.MODE_PRIVATE);
		Editor editor = shared.edit();
		editor.clear();
		editor.commit();
		finish();
	}

}
