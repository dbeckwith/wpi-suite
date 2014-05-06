/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.team9.planningpoker;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * A utility to access some global configuration values
 * @author akshay
 *
 */
public class Config {
	
	/**
	 * 
	 * @param ctx the context of this application
	 * @return the username of the currently loged in user
	 */
	public static String getUserName(Context ctx){
		//get user from preferences
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		return prefs.getString("username", "");		
	}

}
