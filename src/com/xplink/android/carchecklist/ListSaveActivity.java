package com.xplink.android.carchecklist;

import com.google.android.gms.ads.AdRequest; 
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ListSaveActivity extends Activity {
	private AdView adView;
	// String admonId = "a151ef84c7b0a98";
	String admonId = "ca-app-pub-9957173043484675/3220197247";
	private String[] list;
	private DBCarCheckList db;
	private SQLiteDatabase sqliteDB;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listsave_activity);
		list = getUsernames();
		ArrayAdapter adapter = new ArrayAdapter(this,
				R.layout.text_for_listview, list);
		ListView listView = (ListView) findViewById(R.id.list);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			    // When clicked, show a toast with the TextView text
				// position will return index of listview
				//Toast.makeText(getApplicationContext(), "" + position, Toast.LENGTH_SHORT).show(); 
				SharedPreferences shared = getSharedPreferences("mysettings", Context.MODE_PRIVATE);
				Editor edit = shared.edit();
				edit.putBoolean("stateGetList", true);
				edit.putInt("indexList", position);
				edit.commit();
				
				Log.i("stateGetList", "stateGetList >>> " + shared.getBoolean("stateGetList", false));
				Log.i("indexList", "indexList >>> " + shared.getInt("indexList", -1));
				Intent i = new Intent(getApplicationContext(), CarCheckListActivity.class);
				startActivity(i);
				finish();
			}
		});

		Button btnBack = (Button) findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
		
				Intent i = new Intent(getApplicationContext(), CarCheckListActivity.class);
				startActivity(i);
				finish();
			}
		});

		RelativeLayout listMainLayout = (RelativeLayout) findViewById(R.id.listMainLayout);
		RelativeLayout listSaveLayout = (RelativeLayout) findViewById(R.id.listSaveLayout);
		listMainLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent(getApplicationContext(), CarCheckListActivity.class);
				startActivity(i);
				finish();
			}
		});
		listSaveLayout.setOnClickListener(new OnClickListener() {
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

	private String[] getUsernames() {
		// query data into listview
		String list[];
		db = new DBCarCheckList(this);
		sqliteDB = db.getReadableDatabase();
		String sql = "SELECT * FROM " + db.TABLE_NAME;
		Cursor cursor = sqliteDB.rawQuery(sql, null);
		cursor.moveToFirst();

		if (cursor.getCount() == 0)
			list = new String[] { "Empty" };
		else
			list = new String[cursor.getCount()];

		int index = 0;
		while (cursor != null) {
			String id = cursor.getString(0);
			String username = cursor.getString(1);
			String data = cursor.getString(2);
			String display = "id : " + id + ", username : " + username
					+ ", data : " + data;
			Log.i("result", display);
			list[index] = (index + 1) + ". " + username;
			index++;
			if (cursor.isLast())
				break;
			cursor.moveToNext();
		}
		cursor.close();
		sqliteDB.close();
		db.close();
		return list;
	}
}
