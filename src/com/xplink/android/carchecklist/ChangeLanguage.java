package com.xplink.android.carchecklist;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class ChangeLanguage extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.language); 	

		final int power = getIntent().getExtras().getInt("power");
		final int engine = getIntent().getExtras().getInt("engine");
		final int exterior = getIntent().getExtras().getInt("exterior");
		final int interior = getIntent().getExtras().getInt("interior");
		final int document = getIntent().getExtras().getInt("document");
		
		final int numpower = getIntent().getExtras().getInt("numpower");
		final int numengine = getIntent().getExtras().getInt("numengine");
		final int numexterior = getIntent().getExtras().getInt("numexterior");
		final int numinterior = getIntent().getExtras().getInt("numinterior");
		final int numdocument = getIntent().getExtras().getInt("numdocument");
		
	
		    Log.d("progress","" + power);

		ImageButton th = (ImageButton) findViewById(R.id.th);
		th.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if (v.getId() == R.id.th) {
					
					getString(R.string.power);
					getString(R.string.power_air);
					getString(R.string.power_antiFoggyBack);
					getString(R.string.power_antiFoggySide);
					getString(R.string.power_cabinSeatLight);
					getString(R.string.power_carStereo);
					getString(R.string.power_centralLock);
					getString(R.string.power_clock);
					getString(R.string.power_dashBoardLight);
					getString(R.string.power_dim);
					getString(R.string.power_electronicWindow);
					getString(R.string.power_headLight);
					getString(R.string.power_highBeam);
					getString(R.string.power_horn);
					getString(R.string.power_rainSensor);
					getString(R.string.power_remoteKey);
					getString(R.string.power_sideDoorLight);
					getString(R.string.power_sideMirror);
					getString(R.string.power_steeringWheelSet);
					getString(R.string.power_steeringWheelTest);
					getString(R.string.power_thermometer);
					getString(R.string.power_thirdBrakeLight);
					getString(R.string.power_transmissionPosition);
					getString(R.string.power_turnSignal);
					getString(R.string.power_warnDoor);
					getString(R.string.power_warnHandBrake);
					getString(R.string.power_warnSeatBelt);
					getString(R.string.power_wipe);
					
					getString(R.string.engine_backHood);
					getString(R.string.engine_belt);
					getString(R.string.engine_brakeOil);
					getString(R.string.engine_engineOil);
					getString(R.string.engine_gear);
					getString(R.string.engine_hood);
					getString(R.string.engine_liquidLevel);		
					getString(R.string.engine_soundIn);
					getString(R.string.engine_soundOut);
					getString(R.string.engine_underEngine);
					getString(R.string.engine_waterCoolant);
					
					getString(R.string.outside_color);
					getString(R.string.outside_doorHood);
					getString(R.string.outside_jack);
					getString(R.string.outside_light);
					getString(R.string.outside_seal);
					getString(R.string.outside_tirePart);		
					getString(R.string.outside_tires);
					getString(R.string.outside_window);
					getString(R.string.outside_wrench);
					
					
					getString(R.string.inside_accelerator);
					getString(R.string.inside_backDoor);
					getString(R.string.inside_brakePedal);
					getString(R.string.inside_carpet);
					getString(R.string.inside_console);
					getString(R.string.inside_curtain);
					getString(R.string.inside_dashBoard);		
					getString(R.string.inside_handBrake);
					getString(R.string.inside_lidOil);
					getString(R.string.inside_seat);
					getString(R.string.inside_seatBelt);
					getString(R.string.inside_sideDoors);		
					getString(R.string.inside_steeringWheel);
					getString(R.string.inside_transmission);
					
					getString(R.string.doc_actTaxLabel);
					getString(R.string.doc_bill);
					getString(R.string.doc_carManual);
					getString(R.string.doc_carPartPaper);
					getString(R.string.doc_gift);
					getString(R.string.doc_insurance);
					getString(R.string.doc_licenseManual);
					getString(R.string.doc_licensePlate);
					getString(R.string.doc_licenseRegister);
					
					getString(R.string.setting);
					getString(R.string.priority);
					getString(R.string.low);
					getString(R.string.high);
					getString(R.string.textexterior);
					getString(R.string.textinterior);
					getString(R.string.textpower);
					getString(R.string.textengine);
					getString(R.string.textdocument);
					getString(R.string.save);
					getString(R.string.reset);
					getString(R.string.change_language);
					
				}

				setLocaleth("th");

				 Intent nextScreen = new Intent(getApplicationContext(),CarCheckListActivity.class);
				 nextScreen.putExtra("power", power);
				 nextScreen.putExtra("engine", engine);
				 nextScreen.putExtra("exterior", exterior);
				 nextScreen.putExtra("interior", interior);
				 nextScreen.putExtra("document", document);
				 
				 nextScreen.putExtra("numpower", numpower);
				 nextScreen.putExtra("numengine", numengine);
				 nextScreen.putExtra("numexterior", numexterior);
				 nextScreen.putExtra("numinterior", numinterior);
				 nextScreen.putExtra("numdocument", numdocument);
				 
		        startActivity(nextScreen);
		        finish();
		        
		}
	});
		
		ImageButton ch = (ImageButton) findViewById(R.id.ch);
		ch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if (v.getId() == R.id.ch) {
					
					getString(R.string.power);
					getString(R.string.power_air);
					getString(R.string.power_antiFoggyBack);
					getString(R.string.power_antiFoggySide);
					getString(R.string.power_cabinSeatLight);
					getString(R.string.power_carStereo);
					getString(R.string.power_centralLock);
					getString(R.string.power_clock);
					getString(R.string.power_dashBoardLight);
					getString(R.string.power_dim);
					getString(R.string.power_electronicWindow);
					getString(R.string.power_headLight);
					getString(R.string.power_highBeam);
					getString(R.string.power_horn);
					getString(R.string.power_rainSensor);
					getString(R.string.power_remoteKey);
					getString(R.string.power_sideDoorLight);
					getString(R.string.power_sideMirror);
					getString(R.string.power_steeringWheelSet);
					getString(R.string.power_steeringWheelTest);
					getString(R.string.power_thermometer);
					getString(R.string.power_thirdBrakeLight);
					getString(R.string.power_transmissionPosition);
					getString(R.string.power_turnSignal);
					getString(R.string.power_warnDoor);
					getString(R.string.power_warnHandBrake);
					getString(R.string.power_warnSeatBelt);
					getString(R.string.power_wipe);
					
					getString(R.string.engine_backHood);
					getString(R.string.engine_belt);
					getString(R.string.engine_brakeOil);
					getString(R.string.engine_engineOil);
					getString(R.string.engine_gear);
					getString(R.string.engine_hood);
					getString(R.string.engine_liquidLevel);		
					getString(R.string.engine_soundIn);
					getString(R.string.engine_soundOut);
					getString(R.string.engine_underEngine);
					getString(R.string.engine_waterCoolant);
					
					getString(R.string.outside_color);
					getString(R.string.outside_doorHood);
					getString(R.string.outside_jack);
					getString(R.string.outside_light);
					getString(R.string.outside_seal);
					getString(R.string.outside_tirePart);		
					getString(R.string.outside_tires);
					getString(R.string.outside_window);
					getString(R.string.outside_wrench);
					
					
					getString(R.string.inside_accelerator);
					getString(R.string.inside_backDoor);
					getString(R.string.inside_brakePedal);
					getString(R.string.inside_carpet);
					getString(R.string.inside_console);
					getString(R.string.inside_curtain);
					getString(R.string.inside_dashBoard);		
					getString(R.string.inside_handBrake);
					getString(R.string.inside_lidOil);
					getString(R.string.inside_seat);
					getString(R.string.inside_seatBelt);
					getString(R.string.inside_sideDoors);		
					getString(R.string.inside_steeringWheel);
					getString(R.string.inside_transmission);
					
					getString(R.string.doc_actTaxLabel);
					getString(R.string.doc_bill);
					getString(R.string.doc_carManual);
					getString(R.string.doc_carPartPaper);
					getString(R.string.doc_gift);
					getString(R.string.doc_insurance);
					getString(R.string.doc_licenseManual);
					getString(R.string.doc_licensePlate);
					getString(R.string.doc_licenseRegister);
					
					getString(R.string.setting);
					getString(R.string.priority);
					getString(R.string.low);
					getString(R.string.high);
					getString(R.string.textexterior);
					getString(R.string.textinterior);
					getString(R.string.textpower);
					getString(R.string.textengine);
					getString(R.string.textdocument);
					getString(R.string.save);
					getString(R.string.reset);
					getString(R.string.change_language);
					
				}
				
				setLocalech("ch");
				
				 Intent nextScreen = new Intent(getApplicationContext(),CarCheckListActivity.class);
				 nextScreen.putExtra("power", power);
				 nextScreen.putExtra("engine", engine);
				 nextScreen.putExtra("exterior", exterior);
				 nextScreen.putExtra("interior", interior);
				 nextScreen.putExtra("document", document);
				 
				 nextScreen.putExtra("numpower", numpower);
				 nextScreen.putExtra("numengine", numengine);
				 nextScreen.putExtra("numexterior", numexterior);
				 nextScreen.putExtra("numinterior", numinterior);
				 nextScreen.putExtra("numdocument", numdocument);
				 
				 startActivity(nextScreen);
			     finish();
				 
			}
	});
		
		ImageButton en = (ImageButton) findViewById(R.id.en_us);
		en.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 if (v.getId() == R.id.en_us) {
			        getString(R.string.back);
				 }
				 
					setLocaleen("en");
					
					 Intent nextScreen = new Intent(getApplicationContext(),CarCheckListActivity.class);
					 nextScreen.putExtra("power", power);
					 nextScreen.putExtra("engine", engine);
					 nextScreen.putExtra("exterior", exterior);
					 nextScreen.putExtra("interior", interior);
					 nextScreen.putExtra("document", document);
					 
					 nextScreen.putExtra("numpower", numpower);
					 nextScreen.putExtra("numengine", numengine);
					 nextScreen.putExtra("numexterior", numexterior);
					 nextScreen.putExtra("numinterior", numinterior);
					 nextScreen.putExtra("numdocument", numdocument);
					 
					 startActivity(nextScreen);
				     finish();
					 
			}

		});
		
}


	public void setLocaleth(String lang) {
		
        Locale myLocale = new Locale("th");
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
//        Intent intent = new Intent(this, CarCheckListActivity.class);
//        intent.putExtra("power2", val);
//        startActivity(intent);
//        finish();
    }
	
	public void setLocalech(String lang) {
		
        Locale myLocale = new Locale("ch");
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        
    }
	
	public void setLocaleen(String lang) {
		
        Locale myLocale = new Locale("en");
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

    }


}
