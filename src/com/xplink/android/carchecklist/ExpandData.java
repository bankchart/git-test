package com.xplink.android.carchecklist;

import java.util.ArrayList; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

public class ExpandData {
	private Map<String, Integer> settingsMap;
	private Map<String, Boolean> checklistsMap;
	private String settings;
	private String checklists;
	private String[] tmpSplit;
	
	public List<Map> filterData(String data) {
		data = data.substring(0, data.length() - 1);
		//println("all >> " + data);
		String[] tmp = data.split("&");

		checklists = tmp[0];
		settings = tmp[1];
		// jdk 1.5up
		settingsMap = new HashMap<String, Integer>();
		checklistsMap = new HashMap<String, Boolean>();

		// get settings
		tmpSplit = settings.split("\\|");
		//println("settings >> " + settings);
		for (int i = 0; i < tmpSplit.length; i++) {
			String[] settingValues = tmpSplit[i].split("\\-");
			if (i == 0) { // power
				
				//println("0 >> " + settingValues[0]);
				//println("1 >> " + settingValues[1]);
				settingsMap.put(settingValues[0],
						Integer.parseInt(settingValues[1]));
			} else if (i == 1) { // engine
				settingsMap
						.put(settingValues[0], Integer.parseInt(settingValues[1]));
			} else if (i == 2) { // exterior
				settingsMap
						.put(settingValues[0], Integer.parseInt(settingValues[1]));
			} else if (i == 3) { // interior
				settingsMap
						.put(settingValues[0], Integer.parseInt(settingValues[1]));
			} else { // document
				settingsMap
						.put(settingValues[0], Integer.parseInt(settingValues[1]));
			}
		}

		// get checklist

		tmpSplit = checklists.split("\\|");
	
		//for(String n : tmpSplit){
		//	println(n);
		//}
		
		for (int i = 0; i < tmpSplit.length; i++) {
			if(tmpSplit[i].equals("null"))
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
		
		//displayMap(store.get(0), store.get(1));
		return store;
	}
	
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
	
	public void print(String data){
		Log.i("print", data);
	}
	
	public void println(String data){
		Log.i("println", data);
	}
}
