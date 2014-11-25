package com.xplink.android.carchecklist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.util.Log;

public class ExpandData {

	private Map<String, Integer> settingsMap;
	private Map<String, Boolean> checklistsMap;
	private String settings;
	private String checklists;
	private String[] tmpSplit;
	private final int ENGINE = 11;
	private final int POWER = 28;
	private final int EXTERIOR = 9;
	private final int INTERIOR = 15;
	private final int DOCUMENT = 9;
	private final int PERCENT = 100;
	private double enginePart;
	private double powerPart;
	private double exteriorPart;
	private double interiorPart;
	private double documentPart;

	private double engineCri;
	private double powerCri;
	private double exteriorCri;
	private double interiorCri;
	private double documentCri;

	private double percentPower = 0, percentEngin = 0, percentExterior = 0;
	private double percentInterior = 0, percentDocument = 0, percentRatio = 0;

	public int[] getPercentAllList() {
		double percentPower = 0, percentEngine = 0, percentExterior = 0;
		double percentInterior = 0, percentDocument = 0, percentRatio = 0;
		// calculator
		int numPower = 0, numEngine = 0, numExterior = 0, numInterior = 0, numDocument = 0;

		enginePart = PERCENT / ENGINE;
		powerPart = PERCENT / POWER;
		exteriorPart = PERCENT / EXTERIOR;
		interiorPart = PERCENT / INTERIOR;
		documentPart = PERCENT / DOCUMENT;

		engineCri = PERCENT - enginePart;
		powerCri = PERCENT - powerPart;
		exteriorCri = PERCENT - exteriorPart;
		interiorCri = PERCENT - interiorPart;
		documentCri = PERCENT - documentPart;

		for (Map.Entry<String, Boolean> entry : checklistsMap.entrySet()) {
			if (!"null".equals(entry.getValue())) {
				String[] tmp = entry.getKey().split("\\_");
				if ("power".equals(tmp[0])) {
					if (entry.getValue())
						numPower++;
				} else if ("engine".equals(tmp[0])) {
					if (entry.getValue())
						numEngine++;
				} else if ("outside".equals(tmp[0])) {
					if (entry.getValue())
						numExterior++;
				} else if ("inside".equals(tmp[0])) {
					if (entry.getValue())
						numInterior++;
				} else {
					if (entry.getValue())
						numDocument++;
				}
			}
		}
		percentPower = numPower * 100 / POWER;
		percentEngine = numEngine * 100 / ENGINE;
		percentExterior = numExterior * 100 / EXTERIOR;
		percentInterior = numInterior * 100 / INTERIOR;
		percentDocument = numDocument * 100 / DOCUMENT;

		println("percentPower : " + numPower);
		println("percentEngine : " + numEngine);
		println("percentExterior : " + numExterior);
		println("percentInterior : " + numInterior);
		println("percentDocument : " + numDocument);

		
		int percents[] = new int[] { (int) percentPower, (int) percentEngine,
				(int) percentExterior, (int) percentInterior,
				(int) percentDocument, (int) percentRatio };

		for (int n : percents) {
			println("allpercent : " + n);
		}

		return percents;
	}

	public List<Map> filterData(String data) {
		data = data.substring(0, data.length() - 1);
		// println("all >> " + data);
		String[] tmp = data.split("&");

		checklists = tmp[0];
		settings = tmp[1];
		// jdk 1.5up
		settingsMap = new HashMap<String, Integer>();
		checklistsMap = new HashMap<String, Boolean>();

		// get settings
		tmpSplit = settings.split("\\|");
		// println("settings >> " + settings);
		for (int i = 0; i < tmpSplit.length; i++) {
			String[] settingValues = tmpSplit[i].split("\\-");
			if (i == 0) { // power

				// println("0 >> " + settingValues[0]);
				// println("1 >> " + settingValues[1]);
				settingsMap.put(settingValues[0],
						Integer.parseInt(settingValues[1]));
			} else if (i == 1) { // engine
				settingsMap.put(settingValues[0],
						Integer.parseInt(settingValues[1]));
			} else if (i == 2) { // exterior
				settingsMap.put(settingValues[0],
						Integer.parseInt(settingValues[1]));
			} else if (i == 3) { // interior
				settingsMap.put(settingValues[0],
						Integer.parseInt(settingValues[1]));
			} else { // document
				settingsMap.put(settingValues[0],
						Integer.parseInt(settingValues[1]));
			}
		}

		// get checklist

		tmpSplit = checklists.split("\\|");

		// for(String n : tmpSplit){
		// println(n);
		// }

		for (int i = 0; i < tmpSplit.length; i++) {
			if (tmpSplit[i].equals("null"))
				continue;
			String checklistValues[] = tmpSplit[i].split("\\,");

			for (int j = 0; j < checklistValues.length; j++) {

				String checklistTmp[] = checklistValues[j].split("\\-");

				if (i == 0) { // power
					checklistsMap.put(checklistTmp[0],
							"t".equals(checklistTmp[1]) ? true : false);
				} else if (i == 1) { // engine
					checklistsMap.put(checklistTmp[0],
							"t".equals(checklistTmp[1]) ? true : false);
				} else if (i == 2) { // exterior
					checklistsMap.put(checklistTmp[0],
							"t".equals(checklistTmp[1]) ? true : false);
				} else if (i == 3) { // interior
					checklistsMap.put(checklistTmp[0],
							"t".equals(checklistTmp[1]) ? true : false);
				} else { // document
					checklistsMap.put(checklistTmp[0],
							"t".equals(checklistTmp[1]) ? true : false);
				}

			}
		}
		List<Map> store = new ArrayList<Map>();
		store.add(checklistsMap);
		store.add(settingsMap);

		// displayMap(store.get(0), store.get(1));
		return store;
	}

	//public 
	
	public void displayMap(Map<String, Boolean> mapList,
			Map<String, Integer> mapSetting) {

		for (Map.Entry<String, Boolean> entry : mapList.entrySet()) {
			println(entry.getKey() + " : " + entry.getValue());
		}
		println("------------------------");
		for (Map.Entry<String, Integer> entry : mapSetting.entrySet()) {
			println(entry.getKey() + " : " + entry.getValue());
		}

	}

	public void print(String data) {
		Log.i("print", data);
	}

	public void println(String data) {
		Log.i("println", data);
	}
}
