package edu.wpi.cs.team9.planningpoker.controller;

import java.util.List;

import android.util.Base64;
import android.util.Log;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

public class LoginController {
	
	private static final String TAG = LoginController.class.getSimpleName();
	
	public static void sendLoginRequest(String user, String pass, String url, RequestObserver ob){
		try {
		Network.getInstance().setDefaultNetworkConfiguration(new NetworkConfiguration(url));

		
		// Form the basic auth string
		String basicAuth = "Basic ";
		String credentials = user + ":" + pass;
		basicAuth += Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);

		// Create and send the login request
		Request request = Network.getInstance().makeRequest("login", HttpMethod.POST);
		Log.d(TAG, basicAuth);
		request.addHeader("Authorization", basicAuth);
		request.addObserver(ob);
		request.send();	
		} catch(Exception e){
			ob.fail(null, new Exception("Bad url"));
		}
	}
	
	public static void loginSuccessful(ResponseModel response, String proj, RequestObserver ob) {
		// Save the cookies
		List<String> cookieList = response.getHeaders().get("Set-Cookie");
		String cookieParts[];
		String cookieNameVal[];
		if (cookieList != null) { // if the server returned cookies
			for (String cookie : cookieList) { // for each returned cookie
				cookieParts = cookie.split(";"); // split the cookie
				if (cookieParts.length >= 1) { // if there is at least one part to the cookie
					cookieNameVal = cookieParts[0].split("="); // split the cookie into its name and value
					if (cookieNameVal.length == 2) { // if the split worked, add the cookie to the default NetworkConfiguration
						NetworkConfiguration config = Network.getInstance().getDefaultNetworkConfiguration();
						config.addCookie(cookieNameVal[0], cookieNameVal[1]);
						Network.getInstance().setDefaultNetworkConfiguration(config);
					}
					else {
						Log.e(TAG, "Received unparsable cookie: " + cookie);
					}
				}
				else {
					Log.e(TAG, "Received unparsable cookie: " + cookie);
				}
			}
			
			Log.d(TAG, Network.getInstance().getDefaultNetworkConfiguration().getRequestHeaders().get("cookie").get(0));

			// Select the project
			Request projectSelectRequest = Network.getInstance().makeRequest("login", HttpMethod.PUT);
			projectSelectRequest.addObserver(ob);
			projectSelectRequest.setBody(proj);
			projectSelectRequest.send();
		}
		else {
			Log.d(TAG, "error: no cookies!");
		}
	}
	
	public static void projectSelectSuccessful(ResponseModel response) {
		// Save the cookies
		List<String> cookieList = response.getHeaders().get("Set-Cookie");
		String cookieParts[];
		String cookieNameVal[];
		if (cookieList != null) { // if the server returned cookies
			for (String cookie : cookieList) { // for each returned cookie
				cookieParts = cookie.split(";");
				if (cookieParts.length >= 1) {
					cookieNameVal = cookieParts[0].split("=");
					if (cookieNameVal.length == 2) {
						NetworkConfiguration config = Network.getInstance().getDefaultNetworkConfiguration();
						config.addCookie(cookieNameVal[0], cookieNameVal[1]);
						Network.getInstance().setDefaultNetworkConfiguration(config);
					}
					else {
						System.err.println("Received unparsable cookie: " + cookie);
					}
				}
				else {
					System.err.println("Received unparsable cookie: " + cookie);
				}
			}

			System.out.println(Network.getInstance().getDefaultNetworkConfiguration().getRequestHeaders().get("cookie").get(0));
		}
		else {
			Log.e(TAG, "Unable to select project: no cookies returned.");
		}
	}

}
