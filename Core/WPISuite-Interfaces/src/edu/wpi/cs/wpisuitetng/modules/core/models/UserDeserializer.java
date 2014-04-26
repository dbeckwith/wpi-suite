/*******************************************************************************
 * Copyright (c) 2012 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    twack
 *******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.core.models;

import java.lang.reflect.Type;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Set;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import edu.wpi.cs.wpisuitetng.modules.core.models.User.Carrier;

/**
 * A custom deserializer class for the GSON JSON library. The password field
 * should not be exposed after inflation, so it is set to null.
 * 
 * @author twack
 * 
 */
public class UserDeserializer implements JsonDeserializer<User> {

	private static final Logger logger = Logger
			.getLogger(UserDeserializer.class.getName());

	@Override
	public User deserialize(JsonElement userElement, Type userType,
			JsonDeserializationContext context) throws JsonParseException {
		JsonObject deflated = userElement.getAsJsonObject();

		if (!deflated.has("username")) {
			throw new JsonParseException(
					"The serialized User did not contain the required username field.");
		}

		// for all other attributes: instantiate as null, fill in if given.

		int idNum = 0;
		String username = deflated.get("username").getAsString();
		String name = null;
		String email = null;
		String password = null;
		boolean emailNotify = false;
		boolean smsNotify = false;
		Carrier carrier = null;
		String phoneNumber = null;
		

		if (deflated.has("idNum")
				&& !deflated.get("idNum").getAsString().equals("")) {
			try {
				idNum = deflated.get("idNum").getAsInt();
			} catch (java.lang.NumberFormatException e) {
				logger.log(Level.FINER,
						"User transmitted with String in iDnum field");
			}
		}

		if (deflated.has("password") && !isValueEmpty(deflated, "password")) {
			password = deflated.get("password").getAsString();
		}

		if (deflated.has("name") && !isValueEmpty(deflated, "name")) {
			name = deflated.get("name").getAsString();
		}

		if (deflated.has("email") && !isValueEmpty(deflated, "email")) {
			email = deflated.get("email").getAsString();
		}

		if (deflated.has("emailNotify")
				&& !isValueEmpty(deflated, "emailNotify")) {
			emailNotify = deflated.get("emailNotify").getAsBoolean();
		}

		if (deflated.has("smsNotify") && !isValueEmpty(deflated, "smsNotify")) {
			smsNotify = deflated.get("smsNotify").getAsBoolean();
		}
		
        if (deflated.has("carrier") && !isValueEmpty(deflated, "carrier")) {
            carrier = Carrier.valueOf(Carrier.class, deflated.get("carrier").getAsString());
        }
        
		User inflated = new User(name, username, email, password, idNum);
		inflated.setNotifyByEmail(emailNotify);
		inflated.setNotifyBySMS(smsNotify);

		if (deflated.has("role") && !isValueEmpty(deflated, "role")) {
			Role r = Role.valueOf(deflated.get("role").getAsString());
			inflated.setRole(r);
		} else {
			inflated.setRole(null);
		}
		return inflated;
	}

	/**
	 * A helper method for checking if a value associated with a key is empty.
	 * 
	 * @param deflated
	 *            the deflated JsonObject to check in
	 * @param key
	 *            the key associated with the value to look for
	 * @return true if the value is empty, otherwise false
	 */
	private boolean isValueEmpty(JsonObject deflated, String key) {
		return deflated.get(key).getAsString().equals("");
	}

	/**
	 * This static utility method takes a JSON string and attempts to retrieve a
	 * password field from it.
	 * 
	 * @param serializedUser
	 *            a JSON string containing a password
	 * @return the password field parsed.
	 */
	public static String parsePassword(String serializedUser) {
		if (!serializedUser.contains("password")) {
			throw new JsonParseException(
					"The given JSON string did not contain a password field.");
		}

		int fieldStartIndex = serializedUser.indexOf("password");
		int separator = serializedUser.indexOf(':', fieldStartIndex);
		int startIndex = serializedUser.indexOf('"', separator) + 1;
		int endIndex = serializedUser.indexOf('"', startIndex);

		String password = serializedUser.substring(startIndex, endIndex);

		return password;
	}

}
