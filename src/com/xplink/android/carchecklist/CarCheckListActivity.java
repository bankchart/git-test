package com.xplink.android.carchecklist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import org.apache.http.util.LangUtils;

import com.xplink.android.carchecklist.R;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import android.R.bool;
import android.R.layout;
import android.R.string;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.sip.SipAudioCall.Listener;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.opengl.Visibility;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.InputFilter.LengthFilter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseBooleanArray;

import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;
import android.widget.ViewFlipper;

import com.google.ads.*;

@SuppressWarnings("unused")
@SuppressLint({ "SetJavaScriptEnabled", "CutPasteId", "CommitPrefEdits",
		"DrawAllocation" })
public class CarCheckListActivity extends Activity implements AnimationListener {

	private int powerWeight, engineWeight, exteriorWeight, interiorWeight,
			documentWeight;
	private static final int Integer = 0;
	int CheckDocumentTotal, CheckPowerTotal, CheckEngineTotal,
			CheckExteriorTotal, CheckInteriorTotal, PercenDocument,
			PercenPower, PercenEngine, PercenExterior, PercenInterior,
			PercenRatio, Checknum, powerseekbarValue, engineseekbarValue,
			exteriorseekbarValue, interiorseekbarValue, documentseekbarValue,
			sumPriority, PowerPriority, EnginePriority, ExteriorPriority,
			InteriorPriority, DocumentPriority, documentprogressValue;
	int width, height, screenwidth, screenheight;
	Animation testanimation;
	ImageView image, headdocument, headpower, headengine, headexterior,
			headinterior, headsetting;
	WebView browser;
	private AdView adView;
	// String admonId = "a151ef84c7b0a98";
	String admonId = "ca-app-pub-9957173043484675/3220197247";
	ImageButton btnPower, btnEngine, btnExterior, btnInterior, btnDocument,
			btnSetting;
	Button button;
	ProgressBar PowerProgress, EngineProgress, ExteriorProgress,
			InteriorProgress, DocumentProgress, RatioProgress;
	TextView percenpower, percenengine, percenexterior, perceninterior,
			percendocument, Ratiotext;
	SeekBar seekbar;
	private float from;
	private float to;
	Typeface type;
	CheckBox chkgift;

	private DBCarCheckList db = new DBCarCheckList(this);

	private SQLiteDatabase sqliteDB;

	private Intent intent;
	private Bundle store;

	private List<Map> listValue;

	/*
	 * private void checkBug(String pointerName) { // CHECK //
	 * ------------------
	 * ------------------------------------------------------------------
	 * Checknumcheckbox(); CheckPowerTotal =
	 * getCheckedNumFromShared("CheckPowerTotal"); CheckEngineTotal =
	 * getCheckedNumFromShared("CheckEngineTotal"); CheckExteriorTotal =
	 * getCheckedNumFromShared("CheckExteriorTotal"); CheckInteriorTotal =
	 * getCheckedNumFromShared("CheckInteriorTotal"); CheckDocumentTotal =
	 * getCheckedNumFromShared("CheckDocumentTotal");
	 * 
	 * // checking : dispaly value..... Log.i("CheckNumTotal", pointerName +
	 * " : " + CheckPowerTotal); Log.i("CheckNumTotal", "CheckEngineTotal : " +
	 * CheckEngineTotal); Log.i("CheckNumTotal", "CheckExteriorTotal : " +
	 * CheckExteriorTotal); Log.i("CheckNumTotal", "CheckInteriorTotal : " +
	 * CheckInteriorTotal); Log.i("CheckNumTotal", "CheckDocumentTotal : " +
	 * CheckDocumentTotal);
	 * 
	 * // CHECK //
	 * --------------------------------------------------------------
	 * ---------------------- }
	 */

	private void deleteAllData() {
		sqliteDB = db.getWritableDatabase();
		String deleteSql = "delete from " + db.TABLE_NAME;
		sqliteDB.execSQL(deleteSql);

		sqliteDB = db.getReadableDatabase();
		String querySql = "select * from " + db.TABLE_NAME;
		int count = sqliteDB.rawQuery(querySql, null).getCount();

		Log.i("count", "after clear : " + count);

		sqliteDB.close();
		db.close();
	}

	private void isSaveCheckBox() {
		// power_headLight
		// Toast.makeText(getApplicationContext(), (String)data.result,
		// Toast.LENGTH_LONG).show();
		// Toast.makeText(getApplicationContext(), "inSaveCheckBox",
		// Toast.LENGTH_LONG);
		SharedPreferences shared = getSharedPreferences("mysettings",
				Context.MODE_PRIVATE);
		Log.i("isSaveCheckBox",
				"isSaveCheckBox : "
						+ shared.getBoolean("power_headLight", false));
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) { // end 480
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// deleteAllData();
		// ************************************************************************************
		listValue = restoreCheckList();
		if (listValue != null) {
			SharedPreferences restoreShared = getSharedPreferences(
					"mysettings", Context.MODE_PRIVATE);
			Editor edit = restoreShared.edit();

			Map<String, Boolean> mapList = listValue.get(0);
			Map<String, Integer> mapSetting = listValue.get(1);
			int countChecknum = 0;
			for (Map.Entry<String, Boolean> entry : mapList.entrySet()) {
				
				String[] tmp = entry.getKey().split("\\_");
				
				String key = tmp[0];
				
				if ("interior".equals(key)) {
					if (entry.getValue())
						getTotalInterior(true);
				} else if ("power".equals(key)) {
					if (entry.getValue())
						getTotalPower(true);
				} else if ("engine".equals(key)) {
					if (entry.getValue())
						getTotalEngine(true);
				} else if ("exterior".equals(key)) {
					if (entry.getValue())
						getTotalExterior(true);
				} else {
					if (entry.getValue())
						getTotalExterior(true);
				}
				edit.putBoolean(entry.getKey(), entry.getValue());
				// println(entry.getKey() + " : " + entry.getValue());
			}
			Checknum = countChecknum;
			edit.commit();
			for (Map.Entry<String, Integer> entry : mapSetting.entrySet()) {

				if ("interior".equals(entry.getKey())) {
					edit.putInt("Interiorbar", entry.getValue());
				} else if ("power".equals(entry.getKey())) {
					edit.putInt("Powerbar", entry.getValue());
				} else if ("engine".equals(entry.getKey())) {
					edit.putInt("Enginebar", entry.getValue());
				} else if ("exterior".equals(entry.getKey())) {
					edit.putInt("Exteriorbar", entry.getValue());
				} else {
					edit.putInt("Documentbar", entry.getValue());
				}
				// Log.i("checkSettingsName", "checkSettingsName : " +
				// entry.getKey());
			}
			edit.commit();

			/*
			 * ProgressBar PowerProgress, EngineProgress, ExteriorProgress,
			 * InteriorProgress, DocumentProgress, RatioProgress; TextView
			 * percenpower, percenengine, percenexterior, perceninterior,
			 * percendocument, Ratiotext;
			 */
		}
		// ************************************************************************************
		//isSaveCheckBox();
		store = new Bundle();
		db = new DBCarCheckList(this);

		intent = new Intent(getBaseContext(), RecordActivity.class);

		// getSettingShared();

		Log.i("dbcarchecklist", "create object DBCarCheckList");

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		float height = metrics.heightPixels;
		float width = metrics.widthPixels;

		Log.d("height", "" + height);
		Log.d("width", "" + width);

		int left195 = (int) ((width / 100) * 15.3);
		int left200 = (int) ((width / 100) * 16.25);
		int leftt200 = (int) ((width / 100) * 17);
		int left230 = (int) ((width / 100) * 18);
		int left475 = (int) ((width / 100) * 37.1);
		int left480 = (int) ((width / 100) * 38);
		int left495 = (int) ((width / 100) * 38.7);
		int left500 = (int) ((width / 100) * 40.5);
		int left510 = (int) ((width / 100) * 39.7);
		int left530 = (int) ((width / 100) * 41.2);
		int left865 = (int) ((width / 100) * 67.6);
		int left870 = (int) ((width / 100) * 69.5);
		int leftt870 = (int) ((width / 100) * 68);
		int leftt900 = (int) ((width / 100) * 70);
		int left950 = (int) ((width / 100) * 75);
		int left1150 = (int) ((width / 100) * 93);
		int left1180 = (int) ((width / 100) * 92);

		int top10 = (int) ((height / 100) * 3);
		int top20 = (int) ((height / 100) * 6.5);
		int top40 = (int) ((height / 100) * 5);
		int top95 = (int) ((height / 100) * 12);
		int top100 = (int) ((height / 100) * 12.5);
		int top110 = (int) ((height / 100) * 16.5);
		int top130 = (int) ((height / 100) * 19);
		int top135 = (int) ((height / 100) * 17.5);
		int top225 = (int) ((height / 100) * 28.8);
		int top390 = (int) ((height / 100) * 53);
		int top410 = (int) ((height / 100) * 51.5);
		int top480 = (int) ((height / 100) * 64.5);
		int top500 = (int) ((height / 100) * 63);
		int top505 = (int) ((height / 100) * 63.8);
		int top595 = (int) ((height / 100) * 75);
		int top610 = (int) ((height / 100) * 76);

		Intent intent = getIntent();
		PercenPower = intent.getIntExtra("power", PercenPower);
		PercenEngine = intent.getIntExtra("engine", PercenEngine);
		PercenExterior = intent.getIntExtra("exterior", PercenExterior);
		PercenInterior = intent.getIntExtra("interior", PercenInterior);
		PercenDocument = intent.getIntExtra("document", PercenDocument);

		CheckPowerTotal = intent.getIntExtra("numpower", CheckPowerTotal);
		CheckEngineTotal = intent.getIntExtra("numengine", CheckEngineTotal);
		CheckExteriorTotal = intent.getIntExtra("numexterior",
				CheckExteriorTotal);
		CheckInteriorTotal = intent.getIntExtra("numinterior",
				CheckInteriorTotal);
		CheckDocumentTotal = intent.getIntExtra("numdocument",
				CheckDocumentTotal);

		Log.d("percen", "" + PercenPower);

		type = Typeface.createFromAsset(getAssets(), "Circular.ttf");

		MyCustomPanel view = new MyCustomPanel(this);

		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(1200, 800);
		params.width = 1200;
		params.height = 800;

		addContentView(view, params);

		RelativeLayout.LayoutParams imgpower = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		imgpower.setMargins(left480, top20, 0, 0);
		RelativeLayout.LayoutParams bdpower = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		bdpower.setMargins(left475, top40, 0, 0);
		RelativeLayout.LayoutParams txtpower = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		txtpower.setMargins(left510, top135, 0, 0);

		ImageView borderpower = (ImageView) findViewById(R.id.powerborder);
		borderpower.setLayoutParams(bdpower);
		percenpower = (TextView) findViewById(R.id.percenpower);
		percenpower.setLayoutParams(txtpower);
		percenpower.setTypeface(type);
		percenpower.setText("" + PercenPower + "%");
		PowerProgress = (ProgressBar) findViewById(R.id.PowerProgressbar);
		PowerProgress.setMax(100);
		PowerProgress.setProgress(PercenPower);
		headpower = (ImageView) findViewById(R.id.headpower);
		btnPower = (ImageButton) findViewById(R.id.battery_button);
		btnPower.setLayoutParams(imgpower);
		btnPower.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				// startAnimation
				SlidePowerLayout();
			}
		});

		RelativeLayout.LayoutParams imgengine = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		imgengine.setMargins(left200, top110, 0, 0);
		RelativeLayout.LayoutParams bdengine = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		bdengine.setMargins(left195, top110, 0, 0);
		RelativeLayout.LayoutParams txtengine = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		txtengine.setMargins(left230, top225, 0, 0);

		ImageView borderengine = (ImageView) findViewById(R.id.engineborder);
		borderengine.setLayoutParams(bdengine);
		percenengine = (TextView) findViewById(R.id.percenengine);
		percenengine.setLayoutParams(txtengine);
		percenengine.setTypeface(type);
		percenengine.setText("" + PercenEngine + "%");
		EngineProgress = (ProgressBar) findViewById(R.id.EngineProgressbar);
		EngineProgress.setMax(100);
		EngineProgress.setProgress(PercenEngine);
		headengine = (ImageView) findViewById(R.id.headengine);
		btnEngine = (ImageButton) findViewById(R.id.engine_button);
		btnEngine.setLayoutParams(imgengine);
		btnEngine.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// startAnimation
				SlideEngineLayout();
			}
		});

		RelativeLayout.LayoutParams imgexterior = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		imgexterior.setMargins(leftt200, top390, 0, 0);
		RelativeLayout.LayoutParams bdexterior = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		bdexterior.setMargins(left195, top410, 0, 0);
		RelativeLayout.LayoutParams txtexterior = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		txtexterior.setMargins(left230, top505, 0, 0);

		ImageView borderexterior = (ImageView) findViewById(R.id.exteriorborder);
		borderexterior.setLayoutParams(bdexterior);
		percenexterior = (TextView) findViewById(R.id.percenexterior);
		percenexterior.setLayoutParams(txtexterior);
		percenexterior.setTypeface(type);
		percenexterior.setText("" + PercenExterior + "%");
		ExteriorProgress = (ProgressBar) findViewById(R.id.ExteriorProgressbar);
		ExteriorProgress.setMax(100);
		ExteriorProgress.setProgress(PercenExterior);
		headexterior = (ImageView) findViewById(R.id.headexterior);
		btnExterior = (ImageButton) findViewById(R.id.outside_button);
		btnExterior.setLayoutParams(imgexterior);

		btnExterior.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// startAnimation
				SlideExteriorLayout();
			}
		});

		RelativeLayout.LayoutParams imginterior = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		imginterior.setMargins(left500, top480, 0, 0);
		RelativeLayout.LayoutParams bdinterior = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		bdinterior.setMargins(left495, top500, 0, 0);
		RelativeLayout.LayoutParams txtinterior = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		txtinterior.setMargins(left530, top595, 0, 0);

		ImageView borderinterior = (ImageView) findViewById(R.id.interiorborder);
		borderinterior.setLayoutParams(bdinterior);
		perceninterior = (TextView) findViewById(R.id.perceninterior);
		perceninterior.setLayoutParams(txtinterior);
		perceninterior.setTypeface(type);
		perceninterior.setText("" + PercenInterior + "%");
		InteriorProgress = (ProgressBar) findViewById(R.id.InteriorProgressbar);
		InteriorProgress.setMax(100);
		InteriorProgress.setProgress(PercenInterior);
		headinterior = (ImageView) findViewById(R.id.headinterior);
		btnInterior = (ImageButton) findViewById(R.id.inside_button);
		btnInterior.setLayoutParams(imginterior);
		btnInterior.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// startAnimation
				SlideInteriorLayout();
			}
		});

		RelativeLayout.LayoutParams imgdocument = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		imgdocument.setMargins(left870, top480, 0, 0);
		RelativeLayout.LayoutParams bddocument = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		bddocument.setMargins(left865, top500, 0, 0);
		RelativeLayout.LayoutParams txtdocument = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		txtdocument.setMargins(leftt900, top595, 0, 0);
		RelativeLayout.LayoutParams progdocument = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		progdocument.setMargins(leftt870, top610, 0, 0);

		ImageView borderdocument = (ImageView) findViewById(R.id.documentborder);
		borderdocument.setLayoutParams(bddocument);
		percendocument = (TextView) findViewById(R.id.percendocument);
		percendocument.setLayoutParams(txtdocument);
		percendocument.setTypeface(type);
		percendocument.setText("" + PercenDocument + "%");
		DocumentProgress = (ProgressBar) findViewById(R.id.DocumentProgressbar);
		DocumentProgress.setMax(100);
		DocumentProgress.setProgress(PercenDocument);
		headdocument = (ImageView) findViewById(R.id.headdocument);
		btnDocument = (ImageButton) findViewById(R.id.document_button);
		btnDocument.setLayoutParams(imgdocument);
		btnDocument.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				SlideDocumentLayout();

			}
		});

		RelativeLayout.LayoutParams imgsetting = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		imgsetting.setMargins(left1180, top10, 0, 0);
		RelativeLayout.LayoutParams txtratio = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		txtratio.setMargins(left950, top95, 0, 0);
		RelativeLayout.LayoutParams ratioprog = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		ratioprog.setMargins(left1150, top100, 0, 0);

		Ratiotext = (TextView) findViewById(R.id.ratiotext);
		Ratiotext.setLayoutParams(txtratio);
		RatioProgress = (ProgressBar) findViewById(R.id.ratio);
		RatioProgress.setLayoutParams(ratioprog);
		RatioProgress.setMax(100);
		headsetting = (ImageView) findViewById(R.id.headsetting);
		btnSetting = (ImageButton) findViewById(R.id.setting_button);
		btnSetting.setLayoutParams(imgsetting);
		btnSetting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				SlideSettingLayout();

			}
		});

		// addMob
		LinearLayout layout = (LinearLayout) findViewById(R.id.admob);
		adView = new AdView(getApplicationContext());
		adView.setAdSize(AdSize.LEADERBOARD);
		adView.setAdUnitId(admonId);
		// Add the adView to it
		layout.addView(adView);
		// Initiate a generic request to load it with an ad
		AdRequest.Builder adRequestBuilder = new AdRequest.Builder();

		adView.loadAd(adRequestBuilder.build());
		// adView.loadAd(new AdRequest.Builder().build());
		SharedPreferences shared = getSharedPreferences("mysettings",
				Context.MODE_PRIVATE);
		Log.i("checksum",
				"before call CheckRatio : " + shared.getInt("checknum", 0));
		CheckRatio();
	}

	private List<Map> restoreCheckList() {
		// check getListMem

		List<Map> tmpList = null;
		SharedPreferences shared = getSharedPreferences("mysettings",
				Context.MODE_PRIVATE);
		String check = "empty";
		boolean stateGetList = shared.getBoolean("stateGetList", false);
		int indexList = shared.getInt("indexList", -1);

		if (stateGetList) {
			Log.i("inCheck_satetGetList", "inCheck_stateGetList");
			DBCarCheckList dbList = new DBCarCheckList(this);
			SQLiteDatabase sqliteList = dbList.getReadableDatabase();
			String sqlList = "SELECT * FROM " + dbList.TABLE_NAME;
			Cursor cursor = sqliteList.rawQuery(sqlList, null);
			cursor.moveToFirst();
			int numRow = cursor.getCount();
			String[] idList = new String[numRow];
			int i = 0;

			int id = -1;
			String username = "empty";
			String data = "empty";

			while (cursor != null) {
				idList[i] = cursor.getString(0);
				i++;
				if (cursor.isLast())
					break;
				cursor.moveToNext();
			}

			// restore from save list
			String iD = idList[indexList];
			sqlList = "SELECT * FROM " + dbList.TABLE_NAME + " WHERE id=" + iD;

			Cursor cursor2 = sqliteList.rawQuery(sqlList, null);
			cursor2.moveToFirst();
			ExpandData ex = new ExpandData();
			tmpList = ex.filterData(cursor2.getString(2));
			ex.displayMap(tmpList.get(0), tmpList.get(1));
			check = " , data : " + cursor2.getString(2);

			cursor2.close();
			sqliteList.close();
			dbList.close();

			int[] test = ex.getPercentAllList();
			// IT'S WORK FOR TEST
			// ****************************************************************
			PercenPower = test[0];
			PercenEngine = test[1];
			PercenExterior = test[2];
			PercenInterior = test[3];
			PercenDocument = test[4];
			// IT'S WORK FOR TEST
			// ****************************************************************
		} else {
			Log.i("inCheck_satetGetList", "out stateGetList");
		}
		Log.i("stateGetList2", "stateGetList >--->>" + stateGetList + check);
		Log.i("indexList", "getIndexList >>>>>>> " + indexList);
		if (stateGetList) {
			Editor edit = shared.edit();
			edit.clear();
			edit.commit();
			Checknumcheckbox();
			edit.putInt("checknum", Checknum);
			edit.commit();
		}
		return tmpList;
	}

	private void SlidePowerLayout() {

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		float height = metrics.heightPixels;
		float width = metrics.widthPixels;

		int left500 = (int) ((width / 100) * 39);
		int top200 = (int) ((width / 100) * 15.7);

		final SharedPreferences settings = getSharedPreferences("mysettings", 0);
		final SharedPreferences.Editor editor = settings.edit();
		final Dialog powerdialog = new Dialog(CarCheckListActivity.this,
				R.style.backgrounddialog);
		powerdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		powerdialog.setContentView(R.layout.powerdialoglayout);
		powerdialog.getWindow().getAttributes().windowAnimations = R.style.PowerDialogAnimation;
		powerdialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		// make everything around Dialog brightness than default
		WindowManager.LayoutParams lp = powerdialog.getWindow().getAttributes();
		lp.dimAmount = 0f;

		final CheckBox chkpower_headLight = (CheckBox) powerdialog.getWindow()
				.findViewById(R.id.power_headLight);
		final CheckBox chkpower_dim = (CheckBox) powerdialog.getWindow()
				.findViewById(R.id.power_dim);
		final CheckBox chkpower_highBeam = (CheckBox) powerdialog.getWindow()
				.findViewById(R.id.power_highBeam);
		final CheckBox chkpower_dashBoardLight = (CheckBox) powerdialog
				.getWindow().findViewById(R.id.power_dashBoardLight);
		final CheckBox chkpower_cabinSeatLight = (CheckBox) powerdialog
				.getWindow().findViewById(R.id.power_cabinSeatLight);
		final CheckBox chkpower_sideDoorLight = (CheckBox) powerdialog
				.getWindow().findViewById(R.id.power_sideDoorLight);
		final CheckBox chkpower_turnSignal = (CheckBox) powerdialog.getWindow()
				.findViewById(R.id.power_turnSignal);
		final CheckBox chkpower_air = (CheckBox) powerdialog.getWindow()
				.findViewById(R.id.power_air);
		final CheckBox chkpower_thermometer = (CheckBox) powerdialog
				.getWindow().findViewById(R.id.power_thermometer);
		final CheckBox chkpower_horn = (CheckBox) powerdialog.getWindow()
				.findViewById(R.id.power_horn);
		final CheckBox chkpower_wipe = (CheckBox) powerdialog.getWindow()
				.findViewById(R.id.power_wipe);
		final CheckBox chkpower_rainSensor = (CheckBox) powerdialog.getWindow()
				.findViewById(R.id.power_rainSensor);
		final CheckBox chkpower_thirdBrakeLight = (CheckBox) powerdialog
				.getWindow().findViewById(R.id.power_thirdBrakeLight);
		final CheckBox chkpower_antiFoggyBack = (CheckBox) powerdialog
				.getWindow().findViewById(R.id.power_antiFoggyBack);
		final CheckBox chkpower_antiFoggySide = (CheckBox) powerdialog
				.getWindow().findViewById(R.id.power_antiFoggySide);
		final CheckBox chkpower_steeringWheelTest = (CheckBox) powerdialog
				.getWindow().findViewById(R.id.power_steeringWheelTest);
		final CheckBox chkpower_steeringWheelSet = (CheckBox) powerdialog
				.getWindow().findViewById(R.id.power_steeringWheelSet);
		final CheckBox chkpower_carStereo = (CheckBox) powerdialog.getWindow()
				.findViewById(R.id.power_carStereo);
		final CheckBox chkpower_electronicWindow = (CheckBox) powerdialog
				.getWindow().findViewById(R.id.power_electronicWindow);
		final CheckBox chkpower_sideMirror = (CheckBox) powerdialog.getWindow()
				.findViewById(R.id.power_sideMirror);
		final CheckBox chkpower_warnDoor = (CheckBox) powerdialog.getWindow()
				.findViewById(R.id.power_warnDoor);
		final CheckBox chkpower_warnSeatBelt = (CheckBox) powerdialog
				.getWindow().findViewById(R.id.power_warnSeatBelt);
		final CheckBox chkpower_warnHandBrake = (CheckBox) powerdialog
				.getWindow().findViewById(R.id.power_warnHandBrake);
		final CheckBox chkpower_clock = (CheckBox) powerdialog.getWindow()
				.findViewById(R.id.power_clock);
		final CheckBox chkpower_remoteKey = (CheckBox) powerdialog.getWindow()
				.findViewById(R.id.power_remoteKey);
		final CheckBox chkpower_centralLock = (CheckBox) powerdialog
				.getWindow().findViewById(R.id.power_centralLock);
		final CheckBox chkpower_transmissionPosition = (CheckBox) powerdialog
				.getWindow().findViewById(R.id.power_transmissionPosition);

		// change font
		chkpower_headLight.setTypeface(type);
		chkpower_dim.setTypeface(type);
		chkpower_highBeam.setTypeface(type);
		chkpower_dashBoardLight.setTypeface(type);
		chkpower_cabinSeatLight.setTypeface(type);
		chkpower_sideDoorLight.setTypeface(type);
		chkpower_turnSignal.setTypeface(type);
		chkpower_air.setTypeface(type);
		chkpower_thermometer.setTypeface(type);
		chkpower_horn.setTypeface(type);
		chkpower_wipe.setTypeface(type);
		chkpower_rainSensor.setTypeface(type);
		chkpower_thirdBrakeLight.setTypeface(type);
		chkpower_antiFoggyBack.setTypeface(type);
		chkpower_antiFoggySide.setTypeface(type);
		chkpower_steeringWheelTest.setTypeface(type);
		chkpower_steeringWheelSet.setTypeface(type);
		chkpower_carStereo.setTypeface(type);
		chkpower_electronicWindow.setTypeface(type);
		chkpower_sideMirror.setTypeface(type);
		chkpower_warnDoor.setTypeface(type);
		chkpower_warnSeatBelt.setTypeface(type);
		chkpower_warnHandBrake.setTypeface(type);
		chkpower_clock.setTypeface(type);
		chkpower_remoteKey.setTypeface(type);
		chkpower_centralLock.setTypeface(type);
		chkpower_transmissionPosition.setTypeface(type);

		powerdialog.setCanceledOnTouchOutside(true);
		powerdialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				headpower.setVisibility(ImageView.VISIBLE);
				TranslateAnimation slideoutheadpower = new TranslateAnimation(
						0, 0, 200, 800);
				slideoutheadpower.setDuration(500);
				slideoutheadpower.setFillAfter(true);
				headpower.startAnimation(slideoutheadpower);

				Map<String, Boolean> mp = new HashMap<String, Boolean>();

				mp.put("power_headLight", chkpower_headLight.isChecked());
				mp.put("power_dim", chkpower_dim.isChecked());
				mp.put("power_highBeam", chkpower_highBeam.isChecked());
				mp.put("power_dashBoardLight",
						chkpower_dashBoardLight.isChecked());
				mp.put("power_cabinSeatLight",
						chkpower_cabinSeatLight.isChecked());
				mp.put("power_sideDoorLight",
						chkpower_sideDoorLight.isChecked());
				mp.put("power_turnSignal", chkpower_turnSignal.isChecked());
				mp.put("power_air", chkpower_air.isChecked());
				mp.put("power_thermometer", chkpower_thermometer.isChecked());
				mp.put("power_horn", chkpower_horn.isChecked());
				mp.put("power_wipe", chkpower_wipe.isChecked());
				mp.put("power_rainSensor", chkpower_rainSensor.isChecked());
				mp.put("power_thirdBrakeLight",
						chkpower_thirdBrakeLight.isChecked());
				mp.put("power_antiFoggyBack",
						chkpower_antiFoggyBack.isChecked());
				mp.put("power_antiFoggySide",
						chkpower_antiFoggySide.isChecked());
				mp.put("power_steeringWheelTest",
						chkpower_steeringWheelTest.isChecked());
				mp.put("power_steeringWheelSet",
						chkpower_steeringWheelSet.isChecked());
				mp.put("power_carStereo", chkpower_carStereo.isChecked());
				mp.put("power_electronicWindow",
						chkpower_electronicWindow.isChecked());
				mp.put("power_sideMirror", chkpower_sideMirror.isChecked());
				mp.put("power_warnDoor", chkpower_warnDoor.isChecked());
				mp.put("power_warnSeatBelt", chkpower_warnSeatBelt.isChecked());
				mp.put("power_warnHandBrake",
						chkpower_warnHandBrake.isChecked());
				mp.put("power_clock", chkpower_clock.isChecked());
				mp.put("power_remoteKey", chkpower_remoteKey.isChecked());
				mp.put("power_centralLock", chkpower_centralLock.isChecked());
				mp.put("power_transmissionPosition",
						chkpower_transmissionPosition.isChecked());

				filterStore("power", mp);
				save(mp);

			}
		});

		TextView power = (TextView) powerdialog.getWindow().findViewById(
				R.id.Power);
		power.setTypeface(type);
		Button powerback = (Button) powerdialog.getWindow().findViewById(
				R.id.Powerback);
		powerback.setTypeface(type);
		powerback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				powerdialog.dismiss();

				headpower.setVisibility(ImageView.VISIBLE);
				TranslateAnimation slideoutheadpower = new TranslateAnimation(
						0, 0, 200, 800);
				slideoutheadpower.setDuration(500);
				slideoutheadpower.setFillAfter(true);
				headpower.startAnimation(slideoutheadpower);

				Map<String, Boolean> mp = new HashMap<String, Boolean>();

				mp.put("power_headLight", chkpower_headLight.isChecked());
				mp.put("power_dim", chkpower_dim.isChecked());
				mp.put("power_highBeam", chkpower_highBeam.isChecked());
				mp.put("power_dashBoardLight",
						chkpower_dashBoardLight.isChecked());
				mp.put("power_cabinSeatLight",
						chkpower_cabinSeatLight.isChecked());
				mp.put("power_sideDoorLight",
						chkpower_sideDoorLight.isChecked());
				mp.put("power_turnSignal", chkpower_turnSignal.isChecked());
				mp.put("power_air", chkpower_air.isChecked());
				mp.put("power_thermometer", chkpower_thermometer.isChecked());
				mp.put("power_horn", chkpower_horn.isChecked());
				mp.put("power_wipe", chkpower_wipe.isChecked());
				mp.put("power_rainSensor", chkpower_rainSensor.isChecked());
				mp.put("power_thirdBrakeLight",
						chkpower_thirdBrakeLight.isChecked());
				mp.put("power_antiFoggyBack",
						chkpower_antiFoggyBack.isChecked());
				mp.put("power_antiFoggySide",
						chkpower_antiFoggySide.isChecked());
				mp.put("power_steeringWheelTest",
						chkpower_steeringWheelTest.isChecked());
				mp.put("power_steeringWheelSet",
						chkpower_steeringWheelSet.isChecked());
				mp.put("power_carStereo", chkpower_carStereo.isChecked());
				mp.put("power_electronicWindow",
						chkpower_electronicWindow.isChecked());
				mp.put("power_sideMirror", chkpower_sideMirror.isChecked());
				mp.put("power_warnDoor", chkpower_warnDoor.isChecked());
				mp.put("power_warnSeatBelt", chkpower_warnSeatBelt.isChecked());
				mp.put("power_warnHandBrake",
						chkpower_warnHandBrake.isChecked());
				mp.put("power_clock", chkpower_clock.isChecked());
				mp.put("power_remoteKey", chkpower_remoteKey.isChecked());
				mp.put("power_centralLock", chkpower_centralLock.isChecked());
				mp.put("power_transmissionPosition",
						chkpower_transmissionPosition.isChecked());

				filterStore("power", mp);
				save(mp);

			}
		});

		chkpower_headLight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalPower(increment);
				} else {
					increment = false;
					getTotalPower(increment);
				}
				PowerProgress.setProgress(PercenPower);
				percenpower.setText("" + PercenPower + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkpower_dim.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalPower(increment);
				} else {
					increment = false;
					getTotalPower(increment);
				}
				PowerProgress.setProgress(PercenPower);
				percenpower.setText("" + PercenPower + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkpower_highBeam.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalPower(increment);
				} else {
					increment = false;
					getTotalPower(increment);
				}
				PowerProgress.setProgress(PercenPower);
				percenpower.setText("" + PercenPower + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkpower_dashBoardLight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalPower(increment);
				} else {
					increment = false;
					getTotalPower(increment);
				}
				PowerProgress.setProgress(PercenPower);
				percenpower.setText("" + PercenPower + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkpower_cabinSeatLight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalPower(increment);
				} else {
					increment = false;
					getTotalPower(increment);
				}
				PowerProgress.setProgress(PercenPower);
				percenpower.setText("" + PercenPower + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkpower_sideDoorLight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalPower(increment);
				} else {
					increment = false;
					getTotalPower(increment);
				}
				PowerProgress.setProgress(PercenPower);
				percenpower.setText("" + PercenPower + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkpower_turnSignal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalPower(increment);
				} else {
					increment = false;
					getTotalPower(increment);
				}
				PowerProgress.setProgress(PercenPower);
				percenpower.setText("" + PercenPower + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkpower_air.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalPower(increment);
				} else {
					increment = false;
					getTotalPower(increment);
				}
				PowerProgress.setProgress(PercenPower);
				percenpower.setText("" + PercenPower + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkpower_thermometer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalPower(increment);
				} else {
					increment = false;
					getTotalPower(increment);
				}
				PowerProgress.setProgress(PercenPower);
				percenpower.setText("" + PercenPower + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkpower_horn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalPower(increment);
				} else {
					increment = false;
					getTotalPower(increment);
				}
				PowerProgress.setProgress(PercenPower);
				percenpower.setText("" + PercenPower + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkpower_wipe.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalPower(increment);
				} else {
					increment = false;
					getTotalPower(increment);
				}
				PowerProgress.setProgress(PercenPower);
				percenpower.setText("" + PercenPower + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkpower_rainSensor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalPower(increment);
				} else {
					increment = false;
					getTotalPower(increment);
				}
				PowerProgress.setProgress(PercenPower);
				percenpower.setText("" + PercenPower + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkpower_thirdBrakeLight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalPower(increment);
				} else {
					increment = false;
					getTotalPower(increment);
				}
				PowerProgress.setProgress(PercenPower);
				percenpower.setText("" + PercenPower + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkpower_antiFoggyBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalPower(increment);
				} else {
					increment = false;
					getTotalPower(increment);
				}
				PowerProgress.setProgress(PercenPower);
				percenpower.setText("" + PercenPower + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkpower_antiFoggySide.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalPower(increment);
				} else {
					increment = false;
					getTotalPower(increment);
				}
				PowerProgress.setProgress(PercenPower);
				percenpower.setText("" + PercenPower + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkpower_steeringWheelTest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalPower(increment);
				} else {
					increment = false;
					getTotalPower(increment);
				}
				PowerProgress.setProgress(PercenPower);
				percenpower.setText("" + PercenPower + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkpower_steeringWheelSet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalPower(increment);
				} else {
					increment = false;
					getTotalPower(increment);
				}
				PowerProgress.setProgress(PercenPower);
				percenpower.setText("" + PercenPower + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkpower_carStereo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalPower(increment);
				} else {
					increment = false;
					getTotalPower(increment);
				}
				PowerProgress.setProgress(PercenPower);
				percenpower.setText("" + PercenPower + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkpower_electronicWindow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalPower(increment);
				} else {
					increment = false;
					getTotalPower(increment);
				}
				PowerProgress.setProgress(PercenPower);
				percenpower.setText("" + PercenPower + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkpower_sideMirror.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalPower(increment);
				} else {
					increment = false;
					getTotalPower(increment);
				}
				PowerProgress.setProgress(PercenPower);
				percenpower.setText("" + PercenPower + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkpower_warnDoor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalPower(increment);
				} else {
					increment = false;
					getTotalPower(increment);
				}
				PowerProgress.setProgress(PercenPower);
				percenpower.setText("" + PercenPower + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkpower_warnSeatBelt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalPower(increment);
				} else {
					increment = false;
					getTotalPower(increment);
				}
				PowerProgress.setProgress(PercenPower);
				percenpower.setText("" + PercenPower + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkpower_warnHandBrake.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalPower(increment);
				} else {
					increment = false;
					getTotalPower(increment);
				}
				PowerProgress.setProgress(PercenPower);
				percenpower.setText("" + PercenPower + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkpower_clock.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalPower(increment);
				} else {
					increment = false;
					getTotalPower(increment);
				}
				PowerProgress.setProgress(PercenPower);
				percenpower.setText("" + PercenPower + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkpower_remoteKey.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalPower(increment);
				} else {
					increment = false;
					getTotalPower(increment);
				}
				PowerProgress.setProgress(PercenPower);
				percenpower.setText("" + PercenPower + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkpower_centralLock.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalPower(increment);
				} else {
					increment = false;
					getTotalPower(increment);
				}
				PowerProgress.setProgress(PercenPower);
				percenpower.setText("" + PercenPower + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkpower_transmissionPosition.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalPower(increment);
				} else {
					increment = false;
					getTotalPower(increment);
				}
				PowerProgress.setProgress(PercenPower);
				percenpower.setText("" + PercenPower + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		headpower.setVisibility(ImageView.VISIBLE);
		TranslateAnimation slideheadpower = new TranslateAnimation(0, 0, 800,
				200);
		slideheadpower.setDuration(300);
		slideheadpower.setFillAfter(true);
		headpower.startAnimation(slideheadpower);

		powerdialog.show();
		WindowManager.LayoutParams params = powerdialog.getWindow()
				.getAttributes();
		params.y = top200;
		params.x = left500;
		params.gravity = Gravity.TOP | Gravity.LEFT;
		powerdialog.getWindow().setAttributes(params);

		isSaveCheckBox();

		chkpower_headLight.setChecked(load("power_headLight"));
		chkpower_dim.setChecked(load("power_dim"));
		chkpower_highBeam.setChecked(load("power_highBeam"));
		chkpower_dashBoardLight.setChecked(load("power_dashBoardLight"));
		chkpower_cabinSeatLight.setChecked(load("power_cabinSeatLight"));
		chkpower_sideDoorLight.setChecked(load("power_sideDoorLight"));
		chkpower_turnSignal.setChecked(load("power_turnSignal"));
		chkpower_air.setChecked(load("power_air"));
		chkpower_thermometer.setChecked(load("power_thermometer"));
		chkpower_horn.setChecked(load("power_horn"));
		chkpower_wipe.setChecked(load("power_wipe"));
		chkpower_rainSensor.setChecked(load("power_rainSensor"));
		chkpower_thirdBrakeLight.setChecked(load("power_thirdBrakeLight"));
		chkpower_antiFoggyBack.setChecked(load("power_antiFoggyBack"));
		chkpower_antiFoggySide.setChecked(load("power_antiFoggySide"));
		chkpower_steeringWheelTest.setChecked(load("power_steeringWheelTest"));
		chkpower_steeringWheelSet.setChecked(load("power_steeringWheelSet"));
		chkpower_carStereo.setChecked(load("power_carStereo"));
		chkpower_electronicWindow.setChecked(load("power_electronicWindow"));
		chkpower_sideMirror.setChecked(load("power_sideMirror"));
		chkpower_warnDoor.setChecked(load("power_warnDoor"));
		chkpower_warnSeatBelt.setChecked(load("power_warnSeatBelt"));
		chkpower_warnHandBrake.setChecked(load("power_warnHandBrake"));
		chkpower_clock.setChecked(load("power_clock"));
		chkpower_remoteKey.setChecked(load("power_remoteKey"));
		chkpower_centralLock.setChecked(load("power_centralLock"));
		chkpower_transmissionPosition
				.setChecked(load("power_transmissionPosition"));

	}

	private void SlideEngineLayout() {

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		float height = metrics.heightPixels;
		float width = metrics.widthPixels;

		int left840 = (int) ((width / 100) * 65.625);

		final SharedPreferences settings = getSharedPreferences("mysettings", 0);
		final SharedPreferences.Editor editor = settings.edit();
		final Dialog enginedialog = new Dialog(CarCheckListActivity.this,
				R.style.backgrounddialog);
		enginedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		enginedialog.setContentView(R.layout.enginedialoglayout);
		enginedialog.getWindow().getAttributes().windowAnimations = R.style.EngineDialogAnimation;
		enginedialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		// make everything around Dialog brightness than default
		WindowManager.LayoutParams lp = enginedialog.getWindow()
				.getAttributes();
		lp.dimAmount = 0f;

		final CheckBox chkengine_hood = (CheckBox) enginedialog.getWindow()
				.findViewById(R.id.engine_hood);
		final CheckBox chkengine_backHood = (CheckBox) enginedialog.getWindow()
				.findViewById(R.id.engine_backHood);
		final CheckBox chkengine_underEngine = (CheckBox) enginedialog
				.getWindow().findViewById(R.id.engine_underEngine);
		final CheckBox chkengine_brakeOil = (CheckBox) enginedialog.getWindow()
				.findViewById(R.id.engine_brakeOil);
		final CheckBox chkengine_engineOil = (CheckBox) enginedialog
				.getWindow().findViewById(R.id.engine_engineOil);
		final CheckBox chkengine_waterCoolant = (CheckBox) enginedialog
				.getWindow().findViewById(R.id.engine_waterCoolant);
		final CheckBox chkengine_belt = (CheckBox) enginedialog.getWindow()
				.findViewById(R.id.engine_belt);
		final CheckBox chkengine_gear = (CheckBox) enginedialog.getWindow()
				.findViewById(R.id.engine_gear);
		final CheckBox chkengine_liquidLevel = (CheckBox) enginedialog
				.getWindow().findViewById(R.id.engine_liquidLevel);
		final CheckBox chkengine_soundOut = (CheckBox) enginedialog.getWindow()
				.findViewById(R.id.engine_soundOut);
		final CheckBox chkengine_soundIn = (CheckBox) enginedialog.getWindow()
				.findViewById(R.id.engine_soundIn);

		// Change font
		chkengine_hood.setTypeface(type);
		chkengine_backHood.setTypeface(type);
		chkengine_underEngine.setTypeface(type);
		chkengine_brakeOil.setTypeface(type);
		chkengine_engineOil.setTypeface(type);
		chkengine_waterCoolant.setTypeface(type);
		chkengine_belt.setTypeface(type);
		chkengine_gear.setTypeface(type);
		chkengine_liquidLevel.setTypeface(type);
		chkengine_soundOut.setTypeface(type);
		chkengine_soundIn.setTypeface(type);

		enginedialog.setCanceledOnTouchOutside(true);
		enginedialog
				.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						headengine.setVisibility(ImageView.VISIBLE);
						TranslateAnimation slideoutheadengine = new TranslateAnimation(
								0, 0, 490, -500);
						slideoutheadengine.setDuration(300);
						slideoutheadengine.setFillAfter(true);
						headengine.startAnimation(slideoutheadengine);

						Map<String, Boolean> mp = new HashMap<String, Boolean>();

						mp.put("engine_hood", chkengine_hood.isChecked());
						mp.put("engine_backHood",
								chkengine_backHood.isChecked());
						mp.put("engine_underEngine",
								chkengine_underEngine.isChecked());
						mp.put("engine_brakeOil",
								chkengine_brakeOil.isChecked());
						mp.put("engine_engineOil",
								chkengine_engineOil.isChecked());
						mp.put("engine_waterCoolant",
								chkengine_waterCoolant.isChecked());
						mp.put("engine_belt", chkengine_belt.isChecked());
						mp.put("engine_gear", chkengine_gear.isChecked());
						mp.put("engine_liquidLevel",
								chkengine_liquidLevel.isChecked());
						mp.put("engine_soundOut",
								chkengine_soundOut.isChecked());
						mp.put("engine_soundIn", chkengine_soundIn.isChecked());

						filterStore("engine", mp);
						save(mp);

					}
				});

		TextView engine = (TextView) enginedialog.getWindow().findViewById(
				R.id.Engine);
		engine.setTypeface(type);
		Button engineback = (Button) enginedialog.getWindow().findViewById(
				R.id.Engineback);
		engineback.setTypeface(type);
		engineback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				enginedialog.dismiss();

				headengine.setVisibility(ImageView.VISIBLE);
				TranslateAnimation slideoutheadengine = new TranslateAnimation(
						0, 0, 490, -500);
				slideoutheadengine.setDuration(300);
				slideoutheadengine.setFillAfter(true);
				headengine.startAnimation(slideoutheadengine);

				Map<String, Boolean> mp = new HashMap<String, Boolean>();

				mp.put("engine_hood", chkengine_hood.isChecked());
				mp.put("engine_backHood", chkengine_backHood.isChecked());
				mp.put("engine_underEngine", chkengine_underEngine.isChecked());
				mp.put("engine_brakeOil", chkengine_brakeOil.isChecked());
				mp.put("engine_engineOil", chkengine_engineOil.isChecked());
				mp.put("engine_waterCoolant",
						chkengine_waterCoolant.isChecked());
				mp.put("engine_belt", chkengine_belt.isChecked());
				mp.put("engine_gear", chkengine_gear.isChecked());
				mp.put("engine_liquidLevel", chkengine_liquidLevel.isChecked());
				mp.put("engine_soundOut", chkengine_soundOut.isChecked());
				mp.put("engine_soundIn", chkengine_soundIn.isChecked());

				filterStore("engine", mp);
				save(mp);

			}
		});

		chkengine_hood.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalEngine(increment);
				} else {
					increment = false;
					getTotalEngine(increment);
				}
				EngineProgress.setProgress(PercenEngine);
				percenengine.setText("" + PercenEngine + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkengine_backHood.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalEngine(increment);
				} else {
					increment = false;
					getTotalEngine(increment);
				}
				EngineProgress.setProgress(PercenEngine);
				percenengine.setText("" + PercenEngine + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkengine_underEngine.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalEngine(increment);
				} else {
					increment = false;
					getTotalEngine(increment);
				}
				EngineProgress.setProgress(PercenEngine);
				percenengine.setText("" + PercenEngine + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkengine_brakeOil.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalEngine(increment);
				} else {
					increment = false;
					getTotalEngine(increment);
				}
				EngineProgress.setProgress(PercenEngine);
				percenengine.setText("" + PercenEngine + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkengine_engineOil.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalEngine(increment);
				} else {
					increment = false;
					getTotalEngine(increment);
				}
				EngineProgress.setProgress(PercenEngine);
				percenengine.setText("" + PercenEngine + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkengine_waterCoolant.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalEngine(increment);
				} else {
					increment = false;
					getTotalEngine(increment);
				}
				EngineProgress.setProgress(PercenEngine);
				percenengine.setText("" + PercenEngine + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkengine_belt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalEngine(increment);
				} else {
					increment = false;
					getTotalEngine(increment);
				}
				EngineProgress.setProgress(PercenEngine);
				percenengine.setText("" + PercenEngine + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkengine_gear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalEngine(increment);
				} else {
					increment = false;
					getTotalEngine(increment);
				}
				EngineProgress.setProgress(PercenEngine);
				percenengine.setText("" + PercenEngine + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkengine_liquidLevel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalEngine(increment);
				} else {
					increment = false;
					getTotalEngine(increment);
				}
				EngineProgress.setProgress(PercenEngine);
				percenengine.setText("" + PercenEngine + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkengine_soundOut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalEngine(increment);
				} else {
					increment = false;
					getTotalEngine(increment);
				}
				EngineProgress.setProgress(PercenEngine);
				percenengine.setText("" + PercenEngine + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkengine_soundIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalEngine(increment);
				} else {
					increment = false;
					getTotalEngine(increment);
				}
				EngineProgress.setProgress(PercenEngine);
				percenengine.setText("" + PercenEngine + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		headengine.setVisibility(ImageView.VISIBLE);
		TranslateAnimation slideheadengine = new TranslateAnimation(0, 0, 0,
				490);
		slideheadengine.setDuration(300);
		slideheadengine.setFillAfter(true);
		headengine.startAnimation(slideheadengine);

		enginedialog.show();

		WindowManager.LayoutParams params = enginedialog.getWindow()
				.getAttributes();
		params.y = 1;
		params.x = left840;
		params.gravity = Gravity.TOP | Gravity.LEFT;
		enginedialog.getWindow().setAttributes(params);

		chkengine_hood.setChecked(load("engine_hood"));
		chkengine_backHood.setChecked(load("engine_backHood"));
		chkengine_underEngine.setChecked(load("engine_underEngine"));
		chkengine_brakeOil.setChecked(load("engine_brakeOil"));
		chkengine_engineOil.setChecked(load("engine_engineOil"));
		chkengine_waterCoolant.setChecked(load("engine_waterCoolant"));
		chkengine_belt.setChecked(load("engine_belt"));
		chkengine_gear.setChecked(load("engine_gear"));
		chkengine_liquidLevel.setChecked(load("engine_liquidLevel"));
		chkengine_soundOut.setChecked(load("engine_soundOut"));
		chkengine_soundIn.setChecked(load("engine_soundIn"));

	}

	private void SlideExteriorLayout() {
		final SharedPreferences settings = getSharedPreferences("mysettings", 0);
		final SharedPreferences.Editor editor = settings.edit();
		final Dialog exteriordialog = new Dialog(CarCheckListActivity.this,
				R.style.backgrounddialog);
		exteriordialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		exteriordialog.setContentView(R.layout.exteriordialoglayout);
		exteriordialog.getWindow().getAttributes().windowAnimations = R.style.ExteriorDialogAnimation;
		exteriordialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		// make everything around Dialog brightness than default
		WindowManager.LayoutParams lp = exteriordialog.getWindow()
				.getAttributes();
		lp.dimAmount = 0f;

		final CheckBox chkoutside_color = (CheckBox) exteriordialog.getWindow()
				.findViewById(R.id.outside_color);
		final CheckBox chkoutside_window = (CheckBox) exteriordialog
				.getWindow().findViewById(R.id.outside_window);
		final CheckBox chkoutside_doorHood = (CheckBox) exteriordialog
				.getWindow().findViewById(R.id.outside_doorHood);
		final CheckBox chkoutside_jack = (CheckBox) exteriordialog.getWindow()
				.findViewById(R.id.outside_jack);
		final CheckBox chkoutside_wrench = (CheckBox) exteriordialog
				.getWindow().findViewById(R.id.outside_wrench);
		final CheckBox chkoutside_tires = (CheckBox) exteriordialog.getWindow()
				.findViewById(R.id.outside_tires);
		final CheckBox chkoutside_light = (CheckBox) exteriordialog.getWindow()
				.findViewById(R.id.outside_light);
		final CheckBox chkoutside_seal = (CheckBox) exteriordialog.getWindow()
				.findViewById(R.id.outside_seal);
		final CheckBox chkoutside_tirePart = (CheckBox) exteriordialog
				.getWindow().findViewById(R.id.outside_tirePart);

		// Change font
		chkoutside_color.setTypeface(type);
		chkoutside_window.setTypeface(type);
		chkoutside_doorHood.setTypeface(type);
		chkoutside_jack.setTypeface(type);
		chkoutside_wrench.setTypeface(type);
		chkoutside_tires.setTypeface(type);
		chkoutside_light.setTypeface(type);
		chkoutside_seal.setTypeface(type);
		chkoutside_tirePart.setTypeface(type);

		exteriordialog.setCanceledOnTouchOutside(true);
		exteriordialog
				.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						headexterior.setVisibility(ImageView.VISIBLE);
						TranslateAnimation slideoutheadexterior = new TranslateAnimation(
								0, 0, 380, -400);
						slideoutheadexterior.setDuration(500);
						slideoutheadexterior.setFillAfter(true);
						headexterior.startAnimation(slideoutheadexterior);

						Map<String, Boolean> mp = new HashMap<String, Boolean>();

						mp.put("outside_color", chkoutside_color.isChecked());
						mp.put("outside_window", chkoutside_window.isChecked());
						mp.put("outside_doorHood",
								chkoutside_doorHood.isChecked());
						mp.put("outside_jack", chkoutside_jack.isChecked());
						mp.put("outside_wrench", chkoutside_wrench.isChecked());
						mp.put("outside_tires", chkoutside_tires.isChecked());
						mp.put("outside_light", chkoutside_light.isChecked());
						mp.put("outside_seal", chkoutside_seal.isChecked());
						mp.put("outside_tirePart",
								chkoutside_tirePart.isChecked());

						filterStore("exterior", mp);
						save(mp);

					}
				});

		TextView exterior = (TextView) exteriordialog.getWindow().findViewById(
				R.id.Exterior);
		exterior.setTypeface(type);
		Button exteriorback = (Button) exteriordialog.getWindow().findViewById(
				R.id.Exteriorback);
		exteriorback.setTypeface(type);
		exteriorback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				exteriordialog.dismiss();

				headexterior.setVisibility(ImageView.VISIBLE);
				TranslateAnimation slideoutheadexterior = new TranslateAnimation(
						0, 0, 380, -400);
				slideoutheadexterior.setDuration(500);
				slideoutheadexterior.setFillAfter(true);
				headexterior.startAnimation(slideoutheadexterior);

				Map<String, Boolean> mp = new HashMap<String, Boolean>();

				mp.put("outside_color", chkoutside_color.isChecked());
				mp.put("outside_window", chkoutside_window.isChecked());
				mp.put("outside_doorHood", chkoutside_doorHood.isChecked());
				mp.put("outside_jack", chkoutside_jack.isChecked());
				mp.put("outside_wrench", chkoutside_wrench.isChecked());
				mp.put("outside_tires", chkoutside_tires.isChecked());
				mp.put("outside_light", chkoutside_light.isChecked());
				mp.put("outside_seal", chkoutside_seal.isChecked());
				mp.put("outside_tirePart", chkoutside_tirePart.isChecked());

				filterStore("exterior", mp);
				save(mp);

			}
		});

		chkoutside_color.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalExterior(increment);
				} else {
					increment = false;
					getTotalExterior(increment);
				}
				ExteriorProgress.setProgress(PercenExterior);
				percenexterior.setText("" + PercenExterior + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkoutside_window.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalExterior(increment);
				} else {
					increment = false;
					getTotalExterior(increment);
				}
				ExteriorProgress.setProgress(PercenExterior);
				percenexterior.setText("" + PercenExterior + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkoutside_doorHood.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalExterior(increment);
				} else {
					increment = false;
					getTotalExterior(increment);
				}
				ExteriorProgress.setProgress(PercenExterior);
				percenexterior.setText("" + PercenExterior + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkoutside_jack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalExterior(increment);
				} else {
					increment = false;
					getTotalExterior(increment);
				}
				ExteriorProgress.setProgress(PercenExterior);
				percenexterior.setText("" + PercenExterior + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkoutside_wrench.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalExterior(increment);
				} else {
					increment = false;
					getTotalExterior(increment);
				}
				ExteriorProgress.setProgress(PercenExterior);
				percenexterior.setText("" + PercenExterior + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkoutside_tires.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalExterior(increment);
				} else {
					increment = false;
					getTotalExterior(increment);
				}
				ExteriorProgress.setProgress(PercenExterior);
				percenexterior.setText("" + PercenExterior + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkoutside_light.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalExterior(increment);
				} else {
					increment = false;
					getTotalExterior(increment);
				}
				ExteriorProgress.setProgress(PercenExterior);
				percenexterior.setText("" + PercenExterior + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkoutside_seal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalExterior(increment);
				} else {
					increment = false;
					getTotalExterior(increment);
				}
				ExteriorProgress.setProgress(PercenExterior);
				percenexterior.setText("" + PercenExterior + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkoutside_tirePart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalExterior(increment);
				} else {
					increment = false;
					getTotalExterior(increment);
				}
				ExteriorProgress.setProgress(PercenExterior);
				percenexterior.setText("" + PercenExterior + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		headexterior.setVisibility(ImageView.VISIBLE);
		TranslateAnimation slideheadexterior = new TranslateAnimation(0, 0, 0,
				380);
		slideheadexterior.setDuration(500);
		slideheadexterior.setFillAfter(true);
		headexterior.startAnimation(slideheadexterior);

		exteriordialog.show();

		WindowManager.LayoutParams params = exteriordialog.getWindow()
				.getAttributes();
		params.y = 0;
		params.x = 60;
		params.gravity = Gravity.TOP | Gravity.LEFT;
		exteriordialog.getWindow().setAttributes(params);

		chkoutside_color.setChecked(load("outside_color"));
		chkoutside_window.setChecked(load("outside_window"));
		chkoutside_doorHood.setChecked(load("outside_doorHood"));
		chkoutside_jack.setChecked(load("outside_jack"));
		chkoutside_wrench.setChecked(load("outside_wrench"));
		chkoutside_tires.setChecked(load("outside_tires"));
		chkoutside_light.setChecked(load("outside_light"));
		chkoutside_seal.setChecked(load("outside_seal"));
		chkoutside_tirePart.setChecked(load("outside_tirePart"));

	}

	private void SlideInteriorLayout() {
		final SharedPreferences settings = getSharedPreferences("mysettings", 0);
		final SharedPreferences.Editor editor = settings.edit();
		final Dialog interiordialog = new Dialog(CarCheckListActivity.this,
				R.style.backgrounddialog);
		interiordialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		interiordialog.setContentView(R.layout.interiordialoglayout);
		interiordialog.getWindow().getAttributes().windowAnimations = R.style.InteriorDialogAnimation;
		interiordialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		// make everything around Dialog brightness than default
		WindowManager.LayoutParams lp = interiordialog.getWindow()
				.getAttributes();
		lp.dimAmount = 0f;

		final CheckBox chkinside_seat = (CheckBox) interiordialog.getWindow()
				.findViewById(R.id.inside_seat);
		final CheckBox chkinside_console = (CheckBox) interiordialog
				.getWindow().findViewById(R.id.inside_console);
		final CheckBox chkinside_steeringWheel = (CheckBox) interiordialog
				.getWindow().findViewById(R.id.inside_steeringWheel);
		final CheckBox chkinside_transmission = (CheckBox) interiordialog
				.getWindow().findViewById(R.id.inside_transmission);
		final CheckBox chkinside_sideDoors = (CheckBox) interiordialog
				.getWindow().findViewById(R.id.inside_sideDoors);
		final CheckBox chkinside_dashBoard = (CheckBox) interiordialog
				.getWindow().findViewById(R.id.inside_dashBoard);
		final CheckBox chkinside_carpet = (CheckBox) interiordialog.getWindow()
				.findViewById(R.id.inside_carpet);
		final CheckBox chkinside_curtain = (CheckBox) interiordialog
				.getWindow().findViewById(R.id.inside_curtain);
		final CheckBox chkinside_seatBelt = (CheckBox) interiordialog
				.getWindow().findViewById(R.id.inside_seatBelt);
		final CheckBox chkinside_backDoor = (CheckBox) interiordialog
				.getWindow().findViewById(R.id.inside_backDoor);
		final CheckBox chkinside_lidOil = (CheckBox) interiordialog.getWindow()
				.findViewById(R.id.inside_lidOil);
		final CheckBox chkinside_setSeat = (CheckBox) interiordialog
				.getWindow().findViewById(R.id.inside_setSeat);
		final CheckBox chkinside_brakePedal = (CheckBox) interiordialog
				.getWindow().findViewById(R.id.inside_brakePedal);
		final CheckBox chkinside_accelerator = (CheckBox) interiordialog
				.getWindow().findViewById(R.id.inside_accelerator);
		final CheckBox chkinside_handBrake = (CheckBox) interiordialog
				.getWindow().findViewById(R.id.inside_handBrake);

		// Change font
		chkinside_seat.setTypeface(type);
		chkinside_console.setTypeface(type);
		chkinside_steeringWheel.setTypeface(type);
		chkinside_transmission.setTypeface(type);
		chkinside_sideDoors.setTypeface(type);
		chkinside_dashBoard.setTypeface(type);
		chkinside_carpet.setTypeface(type);
		chkinside_curtain.setTypeface(type);
		chkinside_seatBelt.setTypeface(type);
		chkinside_backDoor.setTypeface(type);
		chkinside_lidOil.setTypeface(type);
		chkinside_setSeat.setTypeface(type);
		chkinside_brakePedal.setTypeface(type);
		chkinside_accelerator.setTypeface(type);
		chkinside_handBrake.setTypeface(type);

		interiordialog.setCanceledOnTouchOutside(true);
		interiordialog
				.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						headinterior.setVisibility(ImageView.VISIBLE);
						TranslateAnimation slideoutheadinterior = new TranslateAnimation(
								0, 0, 180, 800);
						slideoutheadinterior.setDuration(500);
						slideoutheadinterior.setFillAfter(true);
						headinterior.startAnimation(slideoutheadinterior);

						Map<String, Boolean> mp = new HashMap<String, Boolean>();

						mp.put("inside_seat", chkinside_seat.isChecked());
						mp.put("inside_console", chkinside_console.isChecked());
						mp.put("inside_steeringWheel",
								chkinside_steeringWheel.isChecked());
						mp.put("inside_transmission",
								chkinside_transmission.isChecked());
						mp.put("inside_sideDoors",
								chkinside_sideDoors.isChecked());
						mp.put("inside_dashBoard",
								chkinside_dashBoard.isChecked());
						mp.put("inside_carpet", chkinside_carpet.isChecked());
						mp.put("inside_curtain", chkinside_curtain.isChecked());
						mp.put("inside_seatBelt",
								chkinside_seatBelt.isChecked());
						mp.put("inside_backDoor",
								chkinside_backDoor.isChecked());
						mp.put("inside_lidOil", chkinside_lidOil.isChecked());
						mp.put("inside_setSeat", chkinside_setSeat.isChecked());
						mp.put("inside_brakePedal",
								chkinside_brakePedal.isChecked());
						mp.put("inside_accelerator",
								chkinside_accelerator.isChecked());
						mp.put("inside_handBrake",
								chkinside_handBrake.isChecked());

						filterStore("interior", mp);
						save(mp);
					}
				});
		TextView interior = (TextView) interiordialog.getWindow().findViewById(
				R.id.Interior);
		interior.setTypeface(type);
		Button interiorback = (Button) interiordialog.getWindow().findViewById(
				R.id.Interiorback);
		interiorback.setTypeface(type);
		interiorback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				interiordialog.dismiss();

				headinterior.setVisibility(ImageView.VISIBLE);
				TranslateAnimation slideoutheadinterior = new TranslateAnimation(
						0, 0, 180, 800);
				slideoutheadinterior.setDuration(500);
				slideoutheadinterior.setFillAfter(true);
				headinterior.startAnimation(slideoutheadinterior);

				Map<String, Boolean> mp = new HashMap<String, Boolean>();

				mp.put("inside_seat", chkinside_seat.isChecked());
				mp.put("inside_console", chkinside_console.isChecked());
				mp.put("inside_steeringWheel",
						chkinside_steeringWheel.isChecked());
				mp.put("inside_transmission",
						chkinside_transmission.isChecked());
				mp.put("inside_sideDoors", chkinside_sideDoors.isChecked());
				mp.put("inside_dashBoard", chkinside_dashBoard.isChecked());
				mp.put("inside_carpet", chkinside_carpet.isChecked());
				mp.put("inside_curtain", chkinside_curtain.isChecked());
				mp.put("inside_seatBelt", chkinside_seatBelt.isChecked());
				mp.put("inside_backDoor", chkinside_backDoor.isChecked());
				mp.put("inside_lidOil", chkinside_lidOil.isChecked());
				mp.put("inside_setSeat", chkinside_setSeat.isChecked());
				mp.put("inside_brakePedal", chkinside_brakePedal.isChecked());
				mp.put("inside_accelerator", chkinside_accelerator.isChecked());
				mp.put("inside_handBrake", chkinside_handBrake.isChecked());

				filterStore("interior", mp);
				save(mp);

			}
		});

		chkinside_seat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalInterior(increment);
				} else {
					increment = false;
					getTotalInterior(increment);
				}
				InteriorProgress.setProgress(PercenInterior);
				perceninterior.setText("" + PercenInterior + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkinside_console.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalInterior(increment);
				} else {
					increment = false;
					getTotalInterior(increment);
				}
				InteriorProgress.setProgress(PercenInterior);
				perceninterior.setText("" + PercenInterior + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkinside_steeringWheel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalInterior(increment);
				} else {
					increment = false;
					getTotalInterior(increment);
				}
				InteriorProgress.setProgress(PercenInterior);
				perceninterior.setText("" + PercenInterior + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkinside_transmission.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalInterior(increment);
				} else {
					increment = false;
					getTotalInterior(increment);
				}
				InteriorProgress.setProgress(PercenInterior);
				perceninterior.setText("" + PercenInterior + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkinside_sideDoors.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalInterior(increment);
				} else {
					increment = false;
					getTotalInterior(increment);
				}
				InteriorProgress.setProgress(PercenInterior);
				perceninterior.setText("" + PercenInterior + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkinside_dashBoard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalInterior(increment);
				} else {
					increment = false;
					getTotalInterior(increment);
				}
				InteriorProgress.setProgress(PercenInterior);
				perceninterior.setText("" + PercenInterior + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkinside_carpet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalInterior(increment);
				} else {
					increment = false;
					getTotalInterior(increment);
				}
				InteriorProgress.setProgress(PercenInterior);
				perceninterior.setText("" + PercenInterior + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkinside_curtain.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalInterior(increment);
				} else {
					increment = false;
					getTotalInterior(increment);
				}
				InteriorProgress.setProgress(PercenInterior);
				perceninterior.setText("" + PercenInterior + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkinside_seatBelt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalInterior(increment);
				} else {
					increment = false;
					getTotalInterior(increment);
				}
				InteriorProgress.setProgress(PercenInterior);
				perceninterior.setText("" + PercenInterior + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkinside_backDoor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalInterior(increment);
				} else {
					increment = false;
					getTotalInterior(increment);
				}
				InteriorProgress.setProgress(PercenInterior);
				perceninterior.setText("" + PercenInterior + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkinside_lidOil.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalInterior(increment);
				} else {
					increment = false;
					getTotalInterior(increment);
				}
				InteriorProgress.setProgress(PercenInterior);
				perceninterior.setText("" + PercenInterior + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkinside_setSeat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalInterior(increment);
				} else {
					increment = false;
					getTotalInterior(increment);
				}
				InteriorProgress.setProgress(PercenInterior);
				perceninterior.setText("" + PercenInterior + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkinside_brakePedal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalInterior(increment);
				} else {
					increment = false;
					getTotalInterior(increment);
				}
				InteriorProgress.setProgress(PercenInterior);
				perceninterior.setText("" + PercenInterior + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkinside_accelerator.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalInterior(increment);
				} else {
					increment = false;
					getTotalInterior(increment);
				}
				InteriorProgress.setProgress(PercenInterior);
				perceninterior.setText("" + PercenInterior + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkinside_handBrake.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalInterior(increment);
				} else {
					increment = false;
					getTotalInterior(increment);
				}
				InteriorProgress.setProgress(PercenInterior);
				perceninterior.setText("" + PercenInterior + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		headinterior.setVisibility(ImageView.VISIBLE);
		TranslateAnimation slideheadinterior = new TranslateAnimation(0, 0,
				800, 180);
		slideheadinterior.setDuration(500);
		slideheadinterior.setFillAfter(true);
		headinterior.startAnimation(slideheadinterior);

		interiordialog.show();

		WindowManager.LayoutParams params = interiordialog.getWindow()
				.getAttributes();
		params.y = 180;
		params.x = 60;
		params.gravity = Gravity.TOP | Gravity.LEFT;
		interiordialog.getWindow().setAttributes(params);

		chkinside_seat.setChecked(load("inside_seat"));
		chkinside_console.setChecked(load("inside_console"));
		chkinside_steeringWheel.setChecked(load("inside_steeringWheel"));
		chkinside_transmission.setChecked(load("inside_transmission"));
		chkinside_sideDoors.setChecked(load("inside_sideDoors"));
		chkinside_dashBoard.setChecked(load("inside_dashBoard"));
		chkinside_carpet.setChecked(load("inside_carpet"));
		chkinside_curtain.setChecked(load("inside_curtain"));
		chkinside_seatBelt.setChecked(load("inside_seatBelt"));
		chkinside_backDoor.setChecked(load("inside_backDoor"));
		chkinside_lidOil.setChecked(load("inside_lidOil"));
		chkinside_setSeat.setChecked(load("inside_setSeat"));
		chkinside_brakePedal.setChecked(load("inside_brakePedal"));
		chkinside_accelerator.setChecked(load("inside_accelerator"));
		chkinside_handBrake.setChecked(load("inside_handBrake"));

	}

	private void SlideDocumentLayout() {
		final SharedPreferences settings = getSharedPreferences("mysettings", 0);
		final SharedPreferences.Editor editor = settings.edit();
		final Dialog documentdialog = new Dialog(CarCheckListActivity.this,
				R.style.backgrounddialog);
		documentdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		documentdialog.setContentView(R.layout.documentdialoglayout);
		documentdialog.getWindow().getAttributes().windowAnimations = R.style.DocumentDialogAnimation;
		documentdialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		// make everything around Dialog brightness than default
		WindowManager.LayoutParams lp = documentdialog.getWindow()
				.getAttributes();
		lp.dimAmount = 0f;

		final CheckBox chkinsurance = (CheckBox) documentdialog.getWindow()
				.findViewById(R.id.doc_insurance);
		final CheckBox chkactTaxLabel = (CheckBox) documentdialog.getWindow()
				.findViewById(R.id.doc_actTaxLabel);
		final CheckBox chkbill = (CheckBox) documentdialog.getWindow()
				.findViewById(R.id.doc_bill);
		final CheckBox chklicensePlate = (CheckBox) documentdialog.getWindow()
				.findViewById(R.id.doc_licensePlate);
		final CheckBox chklicenseManual = (CheckBox) documentdialog.getWindow()
				.findViewById(R.id.doc_licenseManual);
		final CheckBox chkcarPartPaper = (CheckBox) documentdialog.getWindow()
				.findViewById(R.id.doc_carPartPaper);
		final CheckBox chkcarManual = (CheckBox) documentdialog.getWindow()
				.findViewById(R.id.doc_carManual);
		final CheckBox chklicenseRegister = (CheckBox) documentdialog
				.getWindow().findViewById(R.id.doc_licenseRegister);
		final CheckBox chkgift = (CheckBox) documentdialog.getWindow()
				.findViewById(R.id.doc_gift);

		// Change font
		chkinsurance.setTypeface(type);
		chkactTaxLabel.setTypeface(type);
		chkbill.setTypeface(type);
		chklicensePlate.setTypeface(type);
		chklicenseManual.setTypeface(type);
		chkcarPartPaper.setTypeface(type);
		chkcarManual.setTypeface(type);
		chklicenseRegister.setTypeface(type);
		chkgift.setTypeface(type);

		documentdialog.setCanceledOnTouchOutside(true);
		documentdialog
				.setOnCancelListener(new DialogInterface.OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {

						headdocument.setVisibility(ImageView.VISIBLE);
						TranslateAnimation slideoutheaddocument = new TranslateAnimation(
								0, 0, 350, 800);
						slideoutheaddocument.setDuration(500);
						slideoutheaddocument.setFillAfter(true);
						headdocument.startAnimation(slideoutheaddocument);

						Map<String, Boolean> mp = new HashMap<String, Boolean>();

						mp.put("doc_insurance", chkinsurance.isChecked());
						mp.put("doc_actTaxLabel", chkactTaxLabel.isChecked());
						mp.put("doc_bill", chkbill.isChecked());
						mp.put("doc_licensePlate", chklicensePlate.isChecked());
						mp.put("doc_licenseManual",
								chklicenseManual.isChecked());
						mp.put("doc_carPartPaper", chkcarPartPaper.isChecked());
						mp.put("doc_carManual", chkcarManual.isChecked());
						mp.put("doc_licenseRegister",
								chklicenseRegister.isChecked());
						mp.put("doc_gift", chkgift.isChecked());

						filterStore("document", mp);
						save(mp);

					}
				});

		TextView document = (TextView) documentdialog.getWindow().findViewById(
				R.id.Document);
		document.setTypeface(type);
		Button documentback = (Button) documentdialog.getWindow().findViewById(
				R.id.Documentback);
		documentback.setTypeface(type);
		documentback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				documentdialog.dismiss();

				headdocument.setVisibility(ImageView.VISIBLE);
				TranslateAnimation slideoutheaddocument = new TranslateAnimation(
						0, 0, 350, 800);
				slideoutheaddocument.setDuration(500);
				slideoutheaddocument.setFillAfter(true);
				headdocument.startAnimation(slideoutheaddocument);

				Map<String, Boolean> mp = new HashMap<String, Boolean>();

				mp.put("doc_insurance", chkinsurance.isChecked());
				mp.put("doc_actTaxLabel", chkactTaxLabel.isChecked());
				mp.put("doc_bill", chkbill.isChecked());
				mp.put("doc_licensePlate", chklicensePlate.isChecked());
				mp.put("doc_licenseManual", chklicenseManual.isChecked());
				mp.put("doc_carPartPaper", chkcarPartPaper.isChecked());
				mp.put("doc_carManual", chkcarManual.isChecked());
				mp.put("doc_licenseRegister", chklicenseRegister.isChecked());
				mp.put("doc_gift", chkgift.isChecked());

				filterStore("document", mp);
				save(mp);

			}

		});

		chkinsurance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalDocument(increment);
				} else {
					increment = false;
					getTotalDocument(increment);
				}
				DocumentProgress.setProgress(PercenDocument);
				percendocument.setText("" + PercenDocument + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkactTaxLabel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalDocument(increment);
				} else {
					increment = false;
					getTotalDocument(increment);
				}
				DocumentProgress.setProgress(PercenDocument);
				percendocument.setText("" + PercenDocument + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkbill.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalDocument(increment);
				} else {
					increment = false;
					getTotalDocument(increment);
				}
				DocumentProgress.setProgress(PercenDocument);
				percendocument.setText("" + PercenDocument + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chklicensePlate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalDocument(increment);
				} else {
					increment = false;
					getTotalDocument(increment);
				}
				DocumentProgress.setProgress(PercenDocument);
				percendocument.setText("" + PercenDocument + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chklicenseManual.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalDocument(increment);
				} else {
					increment = false;
					getTotalDocument(increment);
				}
				DocumentProgress.setProgress(PercenDocument);
				percendocument.setText("" + PercenDocument + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkcarPartPaper.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalDocument(increment);
				} else {
					increment = false;
					getTotalDocument(increment);
				}
				DocumentProgress.setProgress(PercenDocument);
				percendocument.setText("" + PercenDocument + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkcarManual.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalDocument(increment);
				} else {
					increment = false;
					getTotalDocument(increment);
				}
				DocumentProgress.setProgress(PercenDocument);
				percendocument.setText("" + PercenDocument + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chklicenseRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalDocument(increment);
				} else {
					increment = false;
					getTotalDocument(increment);
				}
				DocumentProgress.setProgress(PercenDocument);
				percendocument.setText("" + PercenDocument + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		chkgift.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean increment = true;
				if (((CheckBox) v).isChecked()) {
					getTotalDocument(increment);
				} else {
					increment = false;
					getTotalDocument(increment);
				}
				DocumentProgress.setProgress(PercenDocument);
				percendocument.setText("" + PercenDocument + "%");
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		headdocument.setVisibility(ImageView.VISIBLE);
		TranslateAnimation slideheaddocument = new TranslateAnimation(0, 0,
				800, 350);
		slideheaddocument.setDuration(500);
		slideheaddocument.setFillAfter(true);
		headdocument.startAnimation(slideheaddocument);

		documentdialog.show();

		WindowManager.LayoutParams params = documentdialog.getWindow()
				.getAttributes();
		params.y = 350;
		params.x = 60;
		params.gravity = Gravity.TOP | Gravity.LEFT;
		documentdialog.getWindow().setAttributes(params);

		chkinsurance.setChecked(load("doc_insurance"));
		chkactTaxLabel.setChecked(load("doc_actTaxLabel"));
		chkbill.setChecked(load("doc_bill"));
		chklicensePlate.setChecked(load("doc_licensePlate"));
		chklicenseManual.setChecked(load("doc_licenseManual"));
		chkcarPartPaper.setChecked(load("doc_carPartPaper"));
		chkcarManual.setChecked(load("doc_carManual"));
		chklicenseRegister.setChecked(load("doc_licenseRegister"));
		chkgift.setChecked(load("doc_gift"));

	}

	// A LOT OF THE BIG BUGGGGGGGGGGG
	// *************************************************************
	private void SlideSettingLayout() {

		SharedPreferences mSharedPrefs = getSharedPreferences("mysettings",
				Context.MODE_PRIVATE);

		// Bundle seek = getIntent().getExtras();
		final Dialog settingdialog = new Dialog(CarCheckListActivity.this,
				R.style.backgrounddialog);
		settingdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		settingdialog.setContentView(R.layout.settingdialoglayout);

		final SeekBar powerseekbar = (SeekBar) settingdialog.getWindow()
				.findViewById(R.id.Powerbar);
		final SeekBar engineseekbar = (SeekBar) settingdialog.getWindow()
				.findViewById(R.id.Enginebar);
		final SeekBar exteriorseekbar = (SeekBar) settingdialog.getWindow()
				.findViewById(R.id.Exteriorbar);
		// exteriorseekbar.setIndeterminate(false);
		final SeekBar interiorseekbar = (SeekBar) settingdialog.getWindow()
				.findViewById(R.id.Interiorbar);
		final SeekBar documentseekbar = (SeekBar) settingdialog.getWindow()
				.findViewById(R.id.Documentbar);
		// seekbar.putInt("Powerbar", powerseekbarValue).commit();
		// if(seek != null){
		Log.i("insettings",
				"seekbar : power-> " + mSharedPrefs.getInt("Powerbar", 0));
		Log.i("insettings",
				"seekbar : engine->" + mSharedPrefs.getInt("Enginebar", 0));
		Log.i("insettings",
				"seekbar : exterior->" + mSharedPrefs.getInt("Exteriorbar", 0));
		Log.i("insettings",
				"seekbar : interior->" + mSharedPrefs.getInt("Interiorbar", 0));
		Log.i("insettings",
				"seekbar : document->" + mSharedPrefs.getInt("Documentbar", 0));
		powerseekbar.setProgress(mSharedPrefs.getInt("Powerbar", 0));
		engineseekbar.setProgress(mSharedPrefs.getInt("Enginebar", 0));
		exteriorseekbar.setProgress(mSharedPrefs.getInt("Exteriorbar", 0));
		interiorseekbar.setProgress(mSharedPrefs.getInt("Interiorbar", 0));
		documentseekbar.setProgress(mSharedPrefs.getInt("Documentbar", 0));

		/*
		 * for(Map.Entry<String, Integer> entry : mapSetting.entrySet()){
		 * if("interior".equals(entry.getKey())) edit.putInt("Interiorbar",
		 * entry.getValue()); else if("power".equals(entry.getKey()))
		 * edit.putInt("Powerbar", entry.getValue()); else
		 * if("engine".equals(entry.getKey())) edit.putInt("Enginebar",
		 * entry.getValue()); else if("exterior".equals(entry.getKey()))
		 * edit.putInt("Exteriorbar", entry.getValue()); else
		 * edit.putInt("Documentbar", entry.getValue());
		 * 
		 * //Log.i("checkSettingsName", "checkSettingsName : " +
		 * entry.getKey()); }
		 */

		// ****************************************************check exist
		// current setting

		int powerBar = mSharedPrefs.getInt("Powerbar", 0);
		int engineBar = mSharedPrefs.getInt("Enginebar", 0);
		int exteriorBar = mSharedPrefs.getInt("Exteriorbar", 0);
		int interiorBar = mSharedPrefs.getInt("Interiorbar", 0);
		int documentBar = mSharedPrefs.getInt("Documentbar", 0);

		Log.i("power_setting", "*****in setting******power setting : "
				+ powerBar);
		Log.i("engine_setting", "engine setting : " + engineBar);
		Log.i("exterior_setting", "exterior setting : " + exteriorBar);
		Log.i("interior_setting", "interior setting : " + interiorBar);
		Log.i("document_setting", "document setting : " + documentBar);

		// }
		TextView setting = (TextView) settingdialog.getWindow().findViewById(
				R.id.Setting);
		TextView priority = (TextView) settingdialog.getWindow().findViewById(
				R.id.Priority);
		TextView low = (TextView) settingdialog.getWindow().findViewById(
				R.id.Low);
		TextView high = (TextView) settingdialog.getWindow().findViewById(
				R.id.High);
		TextView textexterior = (TextView) settingdialog.getWindow()
				.findViewById(R.id.textexterior);
		TextView textinterior = (TextView) settingdialog.getWindow()
				.findViewById(R.id.textinterior);
		TextView textpower = (TextView) settingdialog.getWindow().findViewById(
				R.id.textpower);
		TextView textengine = (TextView) settingdialog.getWindow()
				.findViewById(R.id.textengine);
		TextView textdocument = (TextView) settingdialog.getWindow()
				.findViewById(R.id.textdocument);
		TextView one = (TextView) settingdialog.getWindow().findViewById(
				R.id.one);
		TextView two = (TextView) settingdialog.getWindow().findViewById(
				R.id.two);
		TextView three = (TextView) settingdialog.getWindow().findViewById(
				R.id.three);
		TextView four = (TextView) settingdialog.getWindow().findViewById(
				R.id.four);
		TextView five = (TextView) settingdialog.getWindow().findViewById(
				R.id.five);

		setting.setTypeface(type);
		priority.setTypeface(type);
		low.setTypeface(type);
		high.setTypeface(type);
		textexterior.setTypeface(type);
		textinterior.setTypeface(type);
		textpower.setTypeface(type);
		textengine.setTypeface(type);
		textdocument.setTypeface(type);
		one.setTypeface(type);
		two.setTypeface(type);
		three.setTypeface(type);
		four.setTypeface(type);
		five.setTypeface(type);

		settingdialog.getWindow().getAttributes().windowAnimations = R.style.SettingDialogAnimation;
		settingdialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		// make everything around Dialog brightness than default
		WindowManager.LayoutParams lp = settingdialog.getWindow()
				.getAttributes();
		lp.dimAmount = 0f;
		settingdialog.setCanceledOnTouchOutside(true);
		settingdialog
				.setOnCancelListener(new DialogInterface.OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {

						headsetting.setVisibility(ImageView.VISIBLE);
						TranslateAnimation slideoutheadsetting = new TranslateAnimation(
								0, 0, 0, -468);
						slideoutheadsetting.setDuration(500);
						slideoutheadsetting.setFillAfter(true);
						headsetting.startAnimation(slideoutheadsetting);

					}
				});

		Button btnsave = (Button) settingdialog.getWindow().findViewById(
				R.id.save);
		btnsave.setTypeface(type);
		btnsave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				settingdialog.dismiss();

				headsetting.setVisibility(ImageView.VISIBLE);
				TranslateAnimation slideoutheadsetting = new TranslateAnimation(
						0, 0, 0, -468);
				slideoutheadsetting.setDuration(500);
				slideoutheadsetting.setFillAfter(true);
				headsetting.startAnimation(slideoutheadsetting);

				powerseekbarValue = powerseekbar.getProgress();
				engineseekbarValue = engineseekbar.getProgress();
				exteriorseekbarValue = exteriorseekbar.getProgress();
				interiorseekbarValue = interiorseekbar.getProgress();
				documentseekbarValue = documentseekbar.getProgress();

				Priority();

				SharedPreferences mSharedPrefs = getSharedPreferences(
						"mysettings", Context.MODE_PRIVATE);
				Editor seekbar = mSharedPrefs.edit();
				seekbar.putInt("Powerbar", powerseekbarValue);
				seekbar.putInt("Enginebar", engineseekbarValue);
				seekbar.putInt("Exteriorbar", exteriorseekbarValue);
				seekbar.putInt("Interiorbar", interiorseekbarValue);
				seekbar.putInt("Documentbar", documentseekbarValue);
				seekbar.commit();

				/*
				 * int powerBar = shared2.getInt("Powerbar", 0); int engineBar =
				 * shared2.getInt("Enginebar", 0); int exteriorBar =
				 * shared2.getInt("Exteriorbar", 0); int interiorBar =
				 * shared2.getInt("Interiorbar", 0); int documentBar =
				 * shared2.getInt("Documentbar", 0);
				 */
				RatioProgress.setProgress(PercenRatio);
				Ratiotext.setText("Rating of the Vehicle.   " + PercenRatio
						+ "  %");
				CheckRatio();
			}
		});

		Button btnreset = (Button) settingdialog.getWindow().findViewById(
				R.id.reset);
		btnreset.setTypeface(type);
		btnreset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				AlertDialog alertDialog = new AlertDialog.Builder(
						CarCheckListActivity.this)
						.setTitle("Reset")
						.setMessage("Do you really want to clear data?")
						.setPositiveButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										// do nothing
									}
								})
						.setNegativeButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										Intent intent = getIntent();
										SharedPreferences sharedPreferences = getSharedPreferences(
												"mysettings",
												Context.MODE_PRIVATE);
										sharedPreferences.edit().clear()
												.commit();
										
										/*int CheckDocumentTotal, CheckPowerTotal, CheckEngineTotal,
										CheckExteriorTotal, CheckInteriorTotal, PercenDocument,
										PercenPower, PercenEngine, PercenExterior, PercenInterior,
										PercenRatio, Checknum, powerseekbarValue, engineseekbarValue,
										exteriorseekbarValue, interiorseekbarValue, documentseekbarValue,
										sumPriority, PowerPriority, EnginePriority, ExteriorPriority,
										InteriorPriority, DocumentPriority, documentprogressValue;*/
										
										/*Log.i("checkreset", "BEFORE CLEAR -> CheckDocumentTotal : " + CheckDocumentTotal);
										Log.i("checkreset", "CheckPowerTotal : " + CheckPowerTotal);
										Log.i("checkreset", "CheckEngineTotal : " + CheckEngineTotal);
										Log.i("checkreset", "CheckExteriorTotal : " + CheckExteriorTotal);
										Log.i("checkreset", "CheckInteriorTotal : " + CheckInteriorTotal);
										Log.i("checkreset", "PercenDocument : " + PercenDocument);
										Log.i("checkreset", "PercenPower : " + PercenPower);
										Log.i("checkreset", "PercenEngine : " + PercenEngine);
										Log.i("checkreset", "PercenExterior : " + PercenExterior);
										Log.i("checkreset", "PercenInterior : " + PercenInterior);
										Log.i("checkreset", "PercenRatio : " + PercenRatio);
										Log.i("checkreset", "Checknum : " + Checknum);
										Log.i("checkreset", "powerseekbarValue : " + powerseekbarValue);
										Log.i("checkreset", "engineseekbarValue : " + engineseekbarValue);
										Log.i("checkreset", "interiorseekbarValue : " + interiorseekbarValue);
										Log.i("checkreset", "exteriorseekbarValue : " + exteriorseekbarValue);
										Log.i("checkreset", "documentseekbarValue : " + documentseekbarValue);
										Log.i("checkreset", "sumPriority : " + sumPriority);
										Log.i("checkreset", "PowerPriority : " + PowerPriority);
										Log.i("checkreset", "EnginePriority : " + EnginePriority);
										Log.i("checkreset", "ExteriorPriority : " + ExteriorPriority);
										Log.i("checkreset", "InteriorPriority : " + InteriorPriority);
										Log.i("checkreset", "DocumentPriority : " + DocumentPriority);*/
										
										
										getIntent().removeExtra("power");
										getIntent().removeExtra("engine");
										getIntent().removeExtra("exterior");
										getIntent().removeExtra("interior");
										getIntent().removeExtra("document");
										finish();
										
										startActivity(intent);
										
									}
								}).show();
			}
		});

		Button btnChangeLanguage = (Button) settingdialog.getWindow()
				.findViewById(R.id.change_language);
		btnChangeLanguage.setTypeface(type);
		btnChangeLanguage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent myIntent = new Intent(CarCheckListActivity.this,
						ChangeLanguage.class);

				// th
				myIntent.putExtra("power", PercenPower);
				myIntent.putExtra("engine", PercenEngine);
				myIntent.putExtra("exterior", PercenExterior);
				myIntent.putExtra("interior", PercenInterior);
				myIntent.putExtra("document", PercenDocument);
				myIntent.putExtra("numpower", CheckPowerTotal);
				myIntent.putExtra("numengine", CheckEngineTotal);
				myIntent.putExtra("numexterior", CheckExteriorTotal);
				myIntent.putExtra("numinterior", CheckInteriorTotal);
				myIntent.putExtra("numdocument", CheckDocumentTotal);

				SharedPreferences shared = getSharedPreferences("mysettings",
						Context.MODE_PRIVATE);
				Editor editor = shared.edit();
				int powerBar = shared.getInt("Powerbar", 0);
				int engineBar = shared.getInt("Enginebar", 0);
				int exteriorBar = shared.getInt("Exteriorbar", 0);
				int interiorBar = shared.getInt("Interiorbar", 0);
				int documentBar = shared.getInt("Documentbar", 0);

				Log.i("power_setting", "power setting : " + powerBar);
				Log.i("engine_setting", "engine setting : " + engineBar);
				Log.i("exterior_setting", "exterior setting : " + exteriorBar);
				Log.i("interior_setting", "interior setting : " + interiorBar);
				Log.i("document_setting", "document setting : " + documentBar);

				Log.i("checkInList",
						"Check in List : " + shared.getInt("checknum", 0));
				CarCheckListActivity.this.startActivity(myIntent);
				finish();
			}
		});

		Button btnRecord = (Button) settingdialog.getWindow().findViewById(
				R.id.record);
		btnRecord.setTypeface(type);

		btnRecord.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("record", "record onClick");
				final AlertDialog.Builder adb = new AlertDialog.Builder(
						getApplicationContext());
				adb.setTitle("Warning Dialog");
				adb.setMessage("you must select more than one checklist.");
				adb.setPositiveButton("Ok", null);

				SharedPreferences shared = getSharedPreferences("mysettings",
						MODE_PRIVATE);

				powerWeight = shared.getInt("Powerbar", 0);
				engineWeight = shared.getInt("Enginebar", 0);
				exteriorWeight = shared.getInt("Exteriorbar", 0);
				interiorWeight = shared.getInt("Interiorbar", 0);
				documentWeight = shared.getInt("Documentbar", 0);

				String display = "before record activity >> " + powerWeight
						+ "|" + engineWeight + "|" + exteriorWeight + "|"
						+ interiorWeight + "|" + documentWeight;
				Log.i("display", display);

				CarCheckListActivity.this.startActivity(intent);
				finish();
			}
		});

		Button btnList = (Button) settingdialog.getWindow().findViewById(
				R.id.list);
		btnList.setTypeface(type);

		btnList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Checknumcheckbox();

				Intent listSaving = new Intent(getApplicationContext(),
						ListSaveActivity.class);
				startActivity(listSaving);
				finish();
			}
		});

		headsetting.setVisibility(ImageView.VISIBLE);
		TranslateAnimation slideheadsetting = new TranslateAnimation(0, 0,
				-468, 0);
		slideheadsetting.setDuration(500);
		slideheadsetting.setFillAfter(true);
		headsetting.startAnimation(slideheadsetting);

		settingdialog.show();

		WindowManager.LayoutParams params = settingdialog.getWindow()
				.getAttributes();
		params.y = 0;
		params.x = 60;
		params.gravity = Gravity.TOP | Gravity.LEFT;
		settingdialog.getWindow().setAttributes(params);

		powerseekbarValue = mSharedPrefs.getInt("Powerbar", 0);
		engineseekbarValue = mSharedPrefs.getInt("Enginebar", 0);
		exteriorseekbarValue = mSharedPrefs.getInt("Exteriorbar", 0);
		interiorseekbarValue = mSharedPrefs.getInt("Interiorbar", 0);
		documentseekbarValue = mSharedPrefs.getInt("Documentbar", 0);

		PowerPriority = powerseekbarValue + 1;
		EnginePriority = engineseekbarValue + 1;
		ExteriorPriority = exteriorseekbarValue + 1;
		InteriorPriority = interiorseekbarValue + 1;
		DocumentPriority = documentseekbarValue + 1;

		/*
		 * powerseekbar.setProgress(mSharedPrefs.getInt("Powerbar", 0));
		 * engineseekbar.setProgress(mSharedPrefs.getInt("Enginebar", 0));
		 * exteriorseekbar.setProgress(mSharedPrefs.getInt("Exteriorbar", 0));
		 * interiorseekbar.setProgress(mSharedPrefs.getInt("Interior", 0));
		 * documentseekbar.setProgress(mSharedPrefs.getInt("Documentbar", 0));
		 */

		powerseekbar.setProgress(powerseekbarValue);
		engineseekbar.setProgress(engineseekbarValue);
		exteriorseekbar.setProgress(exteriorseekbarValue);
		interiorseekbar.setProgress(interiorseekbarValue);
		documentseekbar.setProgress(documentseekbarValue);

	}

	private void save(Map<String, Boolean> mp) {
		SharedPreferences sharedPreferences = getSharedPreferences(
				"mysettings", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		for (Map.Entry<String, Boolean> entry : mp.entrySet()) {
			editor.putBoolean(entry.getKey(), entry.getValue());

			// Log.d("Key", "" + entry.getKey());

			editor.commit();

		}
	}

	private boolean load(String checkboxName) {
		SharedPreferences sharedPreferences = getSharedPreferences(
				"mysettings", Context.MODE_PRIVATE);
		return sharedPreferences.getBoolean(checkboxName, false);
	}

	private void DisplayToast(String msg) {
		Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
	}

	public void getTotalDocument(boolean increment) {

		if (increment)
			CheckDocumentTotal = CheckDocumentTotal + 1;

		else
			CheckDocumentTotal = CheckDocumentTotal - 1;

		Log.d("check total = ", "" + CheckDocumentTotal);

		PercenDocument();
		Checknumcheckbox();

	}

	public void PercenDocument() {
		PercenDocument = (CheckDocumentTotal * 100) / 9;
		Log.d("check persen = ", "" + PercenDocument);
		Priority();
	}

	public void getTotalInterior(boolean increment) {

		if (increment)
			CheckInteriorTotal = CheckInteriorTotal + 1;

		else
			CheckInteriorTotal = CheckInteriorTotal - 1;

		Log.d("check total = ", "" + CheckInteriorTotal);

		PercenInterior();
		Checknumcheckbox();

	}

	public void PercenInterior() {
		PercenInterior = (CheckInteriorTotal * 100) / 15;
		Log.d("check persen = ", "" + PercenInterior);
		Priority();
	}

	public void getTotalExterior(boolean increment) {

		if (increment)
			CheckExteriorTotal = CheckExteriorTotal + 1;

		else
			CheckExteriorTotal = CheckExteriorTotal - 1;

		Log.d("check total = ", "" + CheckExteriorTotal);

		PercenExterior();
		Checknumcheckbox();

	}

	public void PercenExterior() {
		PercenExterior = (CheckExteriorTotal * 100) / 9;
		Log.d("check persen = ", "" + PercenExterior);
		Priority();
	}

	public void getTotalEngine(boolean increment) {

		if (increment)
			CheckEngineTotal = CheckEngineTotal + 1;

		else
			CheckEngineTotal = CheckEngineTotal - 1;

		Log.d("check total = ", "" + CheckEngineTotal);

		PercenEngine();
		Checknumcheckbox();

	}

	public void PercenEngine() {
		PercenEngine = (CheckEngineTotal * 100) / 11;
		Log.d("check persen = ", "" + PercenEngine);
		Priority();
	}

	public void getTotalPower(boolean increment) {

		if (increment)
			CheckPowerTotal = CheckPowerTotal + 1;

		else
			CheckPowerTotal = CheckPowerTotal - 1;

		// Log.d("check total = ","" + CheckPowerTotal);

		PercenPower();
		Checknumcheckbox();

	}

	public void PercenPower() {
		PercenPower = (CheckPowerTotal * 100) / 27;
		// Log.d("check percen = ","" + PercenPower);
		Priority();
	}

	public void Checknumcheckbox() {
		Checknum = CheckPowerTotal + CheckEngineTotal + CheckExteriorTotal
				+ CheckInteriorTotal + CheckDocumentTotal;
		SharedPreferences shared = getSharedPreferences("mysettings",
				Context.MODE_PRIVATE);
		Editor editor = shared.edit();
		editor.putInt("checknum", Checknum);
		editor.commit();
	}

	public void Priority() {
		int sumpower = powerseekbarValue + 1;
		int sumengine = engineseekbarValue + 1;
		int sumexterior = exteriorseekbarValue + 1;
		int suminterior = interiorseekbarValue + 1;
		int sumdocument = documentseekbarValue + 1;

		sumPriority = sumpower + sumengine + sumexterior + suminterior
				+ sumdocument;

		PowerPriority = sumpower * PercenPower;
		EnginePriority = sumengine * PercenEngine;
		ExteriorPriority = sumexterior * PercenExterior;
		InteriorPriority = suminterior * PercenInterior;
		DocumentPriority = sumdocument * PercenDocument;

		PercenRatio();

	}

	public void PercenRatio() {

		PercenRatio = (PowerPriority + EnginePriority + ExteriorPriority
				+ InteriorPriority + DocumentPriority)
				/ sumPriority;
		SharedPreferences shared = getSharedPreferences("mysettings",
				Context.MODE_PRIVATE);
		Editor editor = shared.edit();
		editor.putInt("percenRatio", PercenRatio);
		editor.commit();
		Log.d("persenRatio", "" + PercenRatio);

	}

	public void CheckRatio() {
		Log.i("checknum", "in checknum : " + Checknum);
		SharedPreferences shared = getSharedPreferences("mysettings",
				Context.MODE_PRIVATE);
		Checknum = shared.getInt("checknum", 0);
		PercenRatio = shared.getInt("percenRatio", PercenRatio);
		Log.i("Checknum", "Checknum in CHECKRATIO : " + Checknum);
		Log.i("PercenRatio", "PercenRatio >>>> " + PercenRatio);
		if (Checknum > 0) {
			Ratiotext
					.setText("Rating of the Vehicle.   " + PercenRatio + "  %");
			RatioProgress.setProgress(PercenRatio);
			Ratiotext.setVisibility(TextView.VISIBLE);
			RatioProgress.setVisibility(ProgressBar.VISIBLE);
		} else {
			Ratiotext.setVisibility(TextView.INVISIBLE);
			RatioProgress.setVisibility(ProgressBar.INVISIBLE);
		}
	}

	public void getprogressValue() {
		DocumentProgress.setProgress(documentprogressValue);
	}

	// // addMob
	// LinearLayout layout = (LinearLayout) findViewById(R.id.admob);
	// AdView adView = new AdView(this, AdSize.BANNER, admonId);
	// // Add the adView to it
	// layout.addView(adView);
	// // Initiate a generic request to load it with an ad
	// AdRequest request = new AdRequest();
	// adView.loadAd(request);
	//
	// DisplayMetrics displayMetrics = new DisplayMetrics();
	// WindowManager wm = (WindowManager)
	// getApplicationContext().getSystemService(Context.WINDOW_SERVICE); // the
	// results will be higher than using the activity context object or the
	// getWindowManager() shortcut
	// wm.getDefaultDisplay().getMetrics(displayMetrics);
	// int screenWidth = displayMetrics.widthPixels;
	// int screenHeight = displayMetrics.heightPixels;
	//
	// Log.d("screenWidth", "" + screenWidth); // 1280
	// Log.d("screenHeight", "" + screenHeight); // 800
	//
	// browser = (WebView) findViewById(R.id.webkit);
	//
	// WebSettings webSettings = browser.getSettings();
	// webSettings.setJavaScriptEnabled(true);

	// browser.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

	// CookieManager.setAcceptFileSchemeCookies(true);
	//
	// CookieManager cookieManager = CookieManager.getInstance();
	// cookieManager.setAcceptCookie(true); cookieManager.acceptCookie();
	//
	// browser.setWebViewClient(new WebViewClient() {
	//
	// public void onPageStarted(WebView view, String url, Bitmap favicon) {
	// super.onPageStarted(view, url, favicon);
	// // check if you are on the right URL and parse it
	// }
	//
	// });
	//
	// if (screenWidth >= 1024 && screenHeight >= 600) {
	// browser.setInitialScale(100);
	//
	// } else {
	// browser.setInitialScale(80);
	// }
	//
	// browser.loadUrl("file:///android_asset/splashPage.html");

	// AdView adView = new AdView(this, AdSize.BANNER,
	// getString(R.string.admob));
	// // Add the adView to it
	// layout.addView(adView);
	// // Initiate a generic request to load it with an ad
	// AdRequest request = new AdRequest();
	// adView.loadAd(request);

	// }

	private CharSequence Double() {
		// TODO Auto-generated method stub
		return null;
	}

	public void onAnimationEnd(Animation animation) {
	}

	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub

	}

	public void menuToggle(int motionin, int motionout, int fragment) {

		/*
		 * final Dialog settingdialog = new Dialog(CarCheckListActivity.this,
		 * R.style.backgrounddialog); settingdialog.dismiss();
		 */

		new Dialog(CarCheckListActivity.this, R.style.backgrounddialog)
				.dismiss();

		headsetting.setVisibility(ImageView.VISIBLE);
		TranslateAnimation slideoutheadsetting = new TranslateAnimation(0, 0,
				0, -468);
		slideoutheadsetting.setDuration(500);
		slideoutheadsetting.setFillAfter(true);
		headsetting.startAnimation(slideoutheadsetting);

		int prefer = getSharedPreferences("mysettings", MODE_PRIVATE).getInt(
				"already", 1);
		if (prefer == 1) {
			getSharedPreferences("mysettings", MODE_PRIVATE).edit()
					.putInt("already", 0).commit();

			FragmentTransaction ft = getFragmentManager().beginTransaction()
					.setCustomAnimations(motionin, motionout);
			FragmentManager fm = getFragmentManager();
			Fragment fmTarget = fm.findFragmentById(fragment);
			ft.show(fmTarget);
			ft.commit();
		} else {
			getSharedPreferences("mysettings", MODE_PRIVATE).edit()
					.putInt("already", 1).commit();
			FragmentTransaction ft = getFragmentManager().beginTransaction()
					.setCustomAnimations(motionin, motionout);
			FragmentManager fm = getFragmentManager();
			final Fragment fmTarget = fm.findFragmentById(fragment);
			ft.hide(fmTarget);
			ft.commit();
		}
	}

	private class MyCustomPanel extends View {

		public MyCustomPanel(Context context) {
			super(context);
		}

		public void draw(Canvas canvas) {

			DisplayMetrics metrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(metrics);

			float height = metrics.heightPixels;
			float width = metrics.widthPixels;

			int left305 = (int) ((width / 100) * 23.8);
			int left380 = (int) ((width / 100) * 29.6);
			int left480 = (int) ((width / 100) * 37.5);
			int left580 = (int) ((width / 100) * 45.3);
			int left585 = (int) ((width / 100) * 45.7);
			int left605 = (int) ((width / 100) * 47.2);
			int left650 = (int) ((width / 100) * 50.7);
			int left700 = (int) ((width / 100) * 54.6);
			int left800 = (int) ((width / 100) * 62.5);
			int left850 = (int) ((width / 100) * 66.4);

			int top110 = (int) ((height / 100) * 13.75);
			int top200 = (int) ((height / 100) * 25);
			int top210 = (int) ((height / 100) * 26.25);
			int top330 = (int) ((height / 100) * 41.25);
			int top370 = (int) ((height / 100) * 46.25);
			int top410 = (int) ((height / 100) * 51.25);
			int top480 = (int) ((height / 100) * 60);
			int top570 = (int) ((height / 100) * 71.25);

			Paint paint = new Paint();
			paint.setStrokeWidth(1);

			paint.setColor(Color.rgb(255, 155, 0));
			canvas.drawLine(left585, top110, left650, top110, paint);
			canvas.drawLine(left650, top110, left800, top210, paint);

			paint.setColor(Color.GRAY);
			canvas.drawLine(left305, top200, left380, top200, paint);
			canvas.drawLine(left380, top200, left480, top330, paint);

			paint.setColor(Color.rgb(0, 125, 255));
			canvas.drawLine(left305, top480, left580, top480, paint);
			canvas.drawLine(left580, top480, left650, top410, paint);

			paint.setColor(Color.RED);
			canvas.drawLine(left605, top570, left700, top570, paint);
			canvas.drawLine(left700, top570, left850, top370, paint);

		}
	}

	public void filterStore(String menuName, Map<String, Boolean> mp) {
		SharedPreferences sharedPreferences = getSharedPreferences(
				"mysettings", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		String tmp = "";
		Log.i("filter", "infileter : " + menuName);
		for (Map.Entry<String, Boolean> entry : mp.entrySet()) {
			editor.putBoolean(entry.getKey(), entry.getValue());

			// Log.d("Key", "" + entry.getKey());

			editor.commit();
			String result = (entry.getValue()) ? "t" : "f";
			tmp += entry.getKey() + "-" + result + ",";
		}
		final Dialog settingdialog = new Dialog(CarCheckListActivity.this,
				R.style.backgrounddialog);
		settingdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		settingdialog.setContentView(R.layout.settingdialoglayout);

		final SeekBar powerSeekbar = (SeekBar) settingdialog.getWindow()
				.findViewById(R.id.Powerbar);
		final SeekBar engineSeekbar = (SeekBar) settingdialog.getWindow()
				.findViewById(R.id.Enginebar);
		final SeekBar exteriorSeekbar = (SeekBar) settingdialog.getWindow()
				.findViewById(R.id.Exteriorbar);
		final SeekBar interiorSeekbar = (SeekBar) settingdialog.getWindow()
				.findViewById(R.id.Interiorbar);
		final SeekBar documentSeekbar = (SeekBar) settingdialog.getWindow()
				.findViewById(R.id.Documentbar);
		tmp = tmp.substring(0, tmp.length() - 1);

		// Log.i("store", menuName + " : " + tmp);
		editor.putString(menuName, tmp);
		Log.i("checknum", "CHECKNUM IN FILTERSTORE : " + Checknum);
		editor.putInt("checknum", Checknum);
		editor.putInt("percentRatio", PercenRatio);
		intent.putExtra(menuName, tmp);
		editor.commit();
		String checklistInMem = sharedPreferences.getString(menuName, "");
		Log.i("inmem", "*** " + checklistInMem);
		editor.putInt("checknum", Checknum);
		editor.commit();
		// CHECK
		// ------------------------------------------------------------------------------------
		// checkBug("ON CANCEL ");
	}

	// CHECK
	// ------------------------------------------------------------------------------------
	@Override
	public void onBackPressed() {

		SharedPreferences shared = getSharedPreferences("mysettings",
				Context.MODE_PRIVATE);

		String tmp1 = shared.getString("power", "");
		String tmp2 = shared.getString("engine", "");
		String tmp3 = shared.getString("exterior", "");
		String tmp4 = shared.getString("interior", "");
		String tmp5 = shared.getString("document", "");

		String tmp = tmp1 + "|" + tmp2 + "|" + tmp3 + "|" + tmp4 + "|" + tmp5;
		Log.i("alldata", "alldata >> " + tmp);

		Editor editor = shared.edit();
		editor.clear();
		editor.commit();
		finish();
	}

	public void getSettingShared() {
		SharedPreferences mSharedPrefs = getSharedPreferences("mysettings",
				Context.MODE_PRIVATE);
		final Dialog settingdialog = new Dialog(CarCheckListActivity.this,
				R.style.backgrounddialog);
		settingdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		settingdialog.setContentView(R.layout.settingdialoglayout);
		final SeekBar powerseekbar = (SeekBar) settingdialog.getWindow()
				.findViewById(R.id.Powerbar);
		final SeekBar engineseekbar = (SeekBar) settingdialog.getWindow()
				.findViewById(R.id.Enginebar);
		final SeekBar exteriorseekbar = (SeekBar) settingdialog.getWindow()
				.findViewById(R.id.Exteriorbar);
		final SeekBar interiorseekbar = (SeekBar) settingdialog.getWindow()
				.findViewById(R.id.Interiorbar);
		final SeekBar documentseekbar = (SeekBar) settingdialog.getWindow()
				.findViewById(R.id.Documentbar);
		// seekbar.putInt("Powerbar", powerseekbarValue).commit();
		// powerseekbar.setProgress(4);
		Log.i("seek", "setProgress(4)");
		engineseekbar.setProgress(mSharedPrefs.getInt("Enginebar", 0));
		exteriorseekbar.setProgress(mSharedPrefs.getInt("Exteriorbar", 0));
		interiorseekbar.setProgress(mSharedPrefs.getInt("Interiorbar", 0));
		documentseekbar.setProgress(mSharedPrefs.getInt("Documentbar", 0));
	}

	private void assignChecked2Shared(String key, int value) {
		SharedPreferences shared = getSharedPreferences("mysettings",
				Context.MODE_PRIVATE);
		Editor editor = shared.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	private int getCheckedNumFromShared(String key) {
		SharedPreferences shared = getSharedPreferences("mysettings",
				Context.MODE_PRIVATE);
		return shared.getInt(key, 0);
	}

	/*
	 * @Override public void onResume() { super.onResume();
	 * 
	 * // Resume the AdView. adView.resume(); }
	 * 
	 * @Override public void onPause() { // Pause the AdView. adView.pause();
	 * 
	 * super.onPause(); }
	 * 
	 * @Override public void onDestroy() { // Destroy the AdView.
	 * adView.destroy();
	 * 
	 * super.onDestroy(); }
	 */

}
