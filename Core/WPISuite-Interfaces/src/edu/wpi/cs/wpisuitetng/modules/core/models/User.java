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
 *    mpdelladonna
 *******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.core.models;

import com.google.gson.*;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
/**
 * The Data Model representation of a User. Implements
 * 	database interaction and serializing.
 * @author mdelladonna, twack, bgaffey
 */

public class User extends AbstractModel
{

	private String name;
	private String username;
	private String email;
	private int idNum;
	private Role role;
	private boolean notifyByEmail = false;
	private boolean notifyBySMS = false;
	private Carrier carrier;
	private String phoneNumber;
	
	transient private String password; // excluded from serialization, still stored.
	
	public enum Carrier {
	    THREE_RIVER_WIRELESS("sms.3rivers.net"),
	    ACS_WIRELESS("paging.acswireless.com"),
	    ALLTEL("message.alltel.com"),
        ATT("txt.att.net"),
        BLUE_SKY_FROG("blueskyfrog.com"),
        BLUEGRASS_CELLULAR("sms.bluecell.com"),
        BOOST_MOBILE("myboostmobile.com"),
        BPL_MOBILE("bplmobile.com"),
        CAROLINA_WEST_WIRELESS("cwwsms.com"),
        CELLULAR_ONE("mobile.celloneusa.com"),
        CELLULAR_SOUTH("csouth1.com"),
        CENTENNIAL_WIRELESS("cwemail.com"),
        CENTURYTEL("messaging.centurytel.net"),
        CLEARNET("msg.clearnet.com"),
        COMCAST("comcastpcs.textmsg.com"),
        CORR_WIRELESS_COMMUNICATIONS("corrwireless.net"),
        DOBSON("mobile.dobson.net"),
        EDGE_WIRELESS("sms.edgewireless.com"),
        FIDO("fido.ca"),
        GOLDEN_TELECOM("sms.goldentele.com"),
        HELIO("messaging.sprintpcs.com"),
        HOUSTON_CELLULAR("text.houstoncellular.net"),
        IDEA_CELLULAR("ideacellular.net"),
        ILLINOIS_VALLEY_CELLULAR("ivctext.com"),
        INLAND_CELLULAR_TELEPHONE("inlandlink.com"),
        MCI("pagemci.com"),
        METROCALL("page.metrocall.com"),
        METROCALL2WAY("my2way.com"),
        METRO_PCS("mymetropcs.com"),
        MICROCELL("fido.ca"),
        MIDWEST_WIRELESS("clearlydigital.com"),
        MOBILCOMM("mobilecomm.net"),
        MTS("text.mtsmobility.com"),
        NEXTEL("messaging.nextel.com"),
        ONLINEBEEP("onlinebeep.net"),
        PCS_ONE("pcsone.net"),
        PRESIDENTS_CHOICE("txt.bell.ca"),
        PUBLIC_SERVICE_CELLULAR("sms.pscel.com"),
        QWEST("qwestmp.com"),
        SATELLINK("satellink.net"),
        SOLO_MOBILE("txt.bell.ca"),
        SOUTHWESTERN_BELL("email.swbw.com"),
        SPRINT("messaging.sprintpcs.com"),
        SUMCOM("tms.suncom.com"),
        SUREWEST_COMMUNICATIONS("mobile.surewest.com"),
        T_MOBILE("tmomail.net"),
        TELUS("msg.telus.com"),
        TRACFONE("txt.att.net"),
        TRITON("tms.suncom.com"),
        UNICEL("utext.com"),
        UNKNOWN(""),
        US_CELLULAR("email.uscc.net"),
        US_WEST("uswestdatamail.com"),
        VERIZON("vtext.com"),
        VIRGIN_MOBILE("vmobl.com"),
        VIRGIN_MOBILE_CA("vmobile.ca"),
        WEST_CENTRAL_WIRELESS("sms.wcc.net"),
        WESTERN_WIRELESS("cellularonewest.com");

	    private final String url;
	    
	    private Carrier(String url) {
	        this.url = url;
	    }
	    
	    public String getURL() {
	        return url;
	    }
	}
	
	/**
	 * The primary constructor for a User
	 * @param name	User's full name
	 * @param username	User's username (nickname)
	 * @param idNum	User's ID number
	 */
	public User(String name, String username, String password, int idNum)
	{
		this.name = name;
		this.username = username;
		this.password = password;
		this.idNum = idNum;
		this.role = Role.USER;
		this.email = null;
		this.phoneNumber = null;
		this.carrier = Carrier.UNKNOWN;
	}
	
	public User(String name, String username, String email, String password, int idNum) {
	    this.name = name;
        this.username = username;
        this.password = password;
        this.idNum = idNum;
        this.role = Role.USER;
        this.email = email;
        this.phoneNumber = null;
        this.carrier = Carrier.UNKNOWN;
	}
	
	public User(String name, String username, String email, String password, int idNum, String phoneNumber, Carrier carrier) {
	        this.name = name;
	        this.username = username;
	        this.password = password;
	        this.idNum = idNum;
	        this.role = Role.USER;
	        this.email = email;
	        this.phoneNumber = phoneNumber;
	        this.carrier = carrier;
	}
	
	@Override
	public boolean equals(Object other) {
		if(other instanceof User)
		{
			if( ((User)other).idNum == this.idNum)
			{
				//things that can be null
				if(this.name != null && !this.name.equals(((User)other).name))
				{
					return false;
				}
				
				if(this.username != null && !this.username.equals(((User)other).username))
				{
					return false;
				}
				
				if(this.email != null && !this.email.equals(((User)other).email))
                {
                    return false;
                }
				
				if(this.password != null && !this.password.equals(((User)other).password))
				{
					return false;
				}
				
				if(this.role != null && !this.role.equals(((User)other).role))
				{
					return false;
				}
				
				if(this.phoneNumber != null && !this.phoneNumber.equals(((User)other).phoneNumber))
				{
				    return false;
				}
				
				if(this.carrier != null && !this.carrier.equals(((User)other).carrier))
				{
				    return false;
				}
				
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Performs password checking logic. Fails if password field is null, which happens
	 * 	when User is deserialized so as to protect the password.
	 * @param pass	the password String to compare
	 * @return	True if the password matches, False otherwise.
	 */
	public boolean matchPassword(String pass)
	{
		return (this.password == null) ? false : password.equals(pass);
	}
	
	/**
	 * Sets password (please encrypt before using this method)
	 * @param pass
	 */
	public void setPassword(String pass)
	{
		this.password = pass;
	}
	
	public String getPassword()
	{
		return this.password;
	}
	
	public String getEmail() 
	{
	    return this.email;
	}
	
	public void setEmail(String email) 
	{
	    this.email = email;
	}
	
	/* Accessors */
	public String getName()
	{
		return name;
	}
	
	public int getIdNum()
	{
		return idNum;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public boolean isNotifyByEmail() {
		return notifyByEmail;
	}
	
	/**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the carrier
     */
    public Carrier getCarrier() {
        return carrier;
    }

    /**
     * @param carrier the carrier to set
     */
    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    public boolean isNotifyBySMS() {
		return notifyBySMS;
	}
	
	/* database interaction */
	public void save()
	{
		return;
	}
	
	public void delete()
	{
		return;
	}
	
	/* Serializing */
	
	/**
	 * Serializes this User model into a JSON string.
	 * 
	 * @return	the JSON representation of this User
	 */
	public String toJSON()
	{
		String json;
		
		Gson gson = new GsonBuilder().registerTypeAdapter(User.class, new UserSerializer()).create();
		
		json = gson.toJson(this, User.class);
		
		return json;	
	}
	
	/**
	 * Static method offering comma-delimited JSON
	 * 	serializing of User lists
	 * @param u	an array of Users
	 * @return	the serialized array of Users
	 */
	public static String toJSON(User[] u)
	{
		String json ="[";
		
		for(User a : u)
		{
			json += a.toJSON() + ", ";
		}
		
		json += "]";
				
		return json;
		
	}
	
	/* Built-in overrides/overloads */
	
	/**
	 * Override of toString() to return a JSON string for now.
	 * 	May override in the future.
	 */
	public String toString()
	{
		return this.toJSON();
	}

	@Override
	public Boolean identify(Object o)
	{
		Boolean b  = false;
		
		if(o instanceof User)
			if(((User) o).username.equalsIgnoreCase(this.username))
				b = true;
		
		if(o instanceof String)
			if(((String) o).equalsIgnoreCase(this.username))
				b = true;
		return b;
	}
	
	/**
	 * Determines if this is equal to another user
	 * @param anotherUser
	 * @return true if this and anotherUser are equal
	 */
	public boolean equals(User anotherUser){
		return this.name.equalsIgnoreCase(anotherUser.getName()) &&
				this.username.equalsIgnoreCase(anotherUser.getUsername()) &&
				this.idNum == anotherUser.getIdNum();
	}
	
	public User setName(String newName){
		this.name = newName;
		return this;
	}
	
	public User setUserName(String newUserName){
		this.username = newUserName;
		return this;
	}
	
	public User setIdNum(int newidNum){
		this.idNum = newidNum;
		return this;
	}
	
	
	public Role getRole()
	{
		return this.role;
	}
	
	public void setRole(Role r)
	{
		this.role = r;
	}
	
	public void setNotifyByEmail(boolean notify) {
		this.notifyByEmail = notify;
	}
	
	public void setNotifyBySMS(boolean notify) {
		this.notifyBySMS = notify;
	}

	
	public static User fromJSON(String json) {
		// build the custom serializer/deserializer
		Gson gson;
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(User.class, new UserDeserializer());

		gson = builder.create();
		
		return gson.fromJson(json, User.class);
	}

	@Override
	public Project getProject() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setProject(Project aProject){
		//Users are not currently Associated with projects directly 
	}
}
