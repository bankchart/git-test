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
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ListSaveActivity extends Activity {
	private AdView adView;
	// String admonId = "a151ef84c7b0a98";
	String admonId = "ca-app-pub-9957173043484675/3220197247";
	private String[] list;
	private DBCarCheckList db;
	private SQLiteDatabase sqliteDB;
	
	//store
	private int checkNumPowerTotal;
	private int checkNumEngineTotal;
	private int checkNumExteriorTotal;
	private int checkNumInteriorTotal;
	private int checkNumDocumentTotal;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listsave_activity);
		checkBug("begin onCreate");
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
				
				edit.putInt("CheckPowerTotal", checkNumPowerTotal);
				edit.putInt("CheckEngineTotal", checkNumEngineTotal);
				edit.putInt("CheckExteriorTotal", checkNumExteriorTotal);
				edit.putInt("CheckInteriorTotal", checkNumInteriorTotal);
				edit.putInt("CheckDocumentTotal", checkNumDocumentTotal);
				edit.commit();
				
				Log.i("stateGetList", "stateGetList >>> " + shared.getBoolean("stateGetList", false));
				Log.i("indexList", "indexList >>> " + shared.getInt("indexList", -1));
				Intent i = new Intent(getApplicationContext(), CarCheckListActivity.class);
				startActivity(i);
				finish();
			}
		});
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
				return false;
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
		adRequestBuilder.addTestDevice("9F5DF3C9768A51CB506B68902F766B40");
		adView.loadAd(adRequestBuilder.build());
		
		checkBug("last onCreate");
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
	private void checkBug(String pointerName){
		SharedPreferences shared = getSharedPreferences("mysettings", Context.MODE_PRIVATE);
		int tmp1 = shared.getInt("CheckPowerTotal", 0);
		checkNumPowerTotal = tmp1;
		int tmp2 = shared.getInt("CheckEngineTotal", 0);
		checkNumEngineTotal = tmp2;
		int tmp3 = shared.getInt("CheckExteriorTotal", 0);
		checkNumExteriorTotal = tmp3;
		int tmp4 = shared.getInt("CheckInteriorTotal", 0);
		checkNumInteriorTotal = tmp4;
		int tmp5 = shared.getInt("CheckDocumentTotal", 0);
		checkNumDocumentTotal = tmp5;
		println(pointerName + " - CheckPowerTotal : " + tmp1);
		println("CheckEngineTotal : " + tmp2);
		println("CheckExteriorTotal : " + tmp3);
		println("CheckInteriorTotal : " + tmp4);
		println("CheckDocumentTotal : " + tmp5);
	}	
	private void println(String str){
		Log.i("CheckNumTotal", str);
	}
	
}
