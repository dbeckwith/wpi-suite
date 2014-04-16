package edu.wpi.cs.team9.planningpoker;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Config {
	
	
	public static String getUserName(Context ctx){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		return prefs.getString("username", "");		
	}

}
