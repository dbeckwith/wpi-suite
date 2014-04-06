package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * A class used to parse an array of users out of a JSON string.
 * 
 * @author Sam Carlberg
 * 
 */
public class UserArrayParser {
    
    /**
     * Parses an array of Users out of a JSON string retrieved from the server.
     * 
     * @param json
     *        the JSON string to parse
     * @return
     */
    public static User[] parse(String json) {
        json = json.replace("[", "").replace("]", "");
        String[] entries = json.split(",");
        User[] users = new User[entries.length];
        
        for (int i = 0; i < entries.length; i++) {
            String s = entries[i];
            s = s.replace(" ", "");
            entries[i] = s;
            users[i] = User.fromJSON(s);
        }
        
        return users;
    }
    
}
