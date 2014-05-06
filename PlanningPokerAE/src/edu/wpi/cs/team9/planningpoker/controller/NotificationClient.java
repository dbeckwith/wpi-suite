/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.team9.planningpoker.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

import android.util.Log;
import edu.wpi.cs.wpisuitetng.network.Network;

/**
 * A client that continually listens for updates from the server
 * 
 * @author Team 9
 * @version 1.0
 */
public class NotificationClient extends Thread {
	
	private static final String TAG = NotificationClient.class.getSimpleName();
    
    private static NotificationClient instance = null;
    
    /**
     * @return the instance of the NotificationClient or creates one if it does
     *         not exist.
     */
    public static NotificationClient getInstance() {
        if (NotificationClient.instance == null) {
            NotificationClient.instance = new NotificationClient();
        }
        return NotificationClient.instance;
    }
    
    private InetAddress serverAddress = null;
    
    /**Logger.getGlobal().info
     * Constructor
     */
    private NotificationClient() {
        super("Long Polling Notification Client");
        try {
            //get ip address of the server
            serverAddress = InetAddress.getByName(new URL(Network.getInstance()
                    .getDefaultNetworkConfiguration().getApiUrl()).getHost());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        Log.d(TAG, "Notification client started");
        while (true) {
            try {
                //open a connection to the notification server
                Socket server = new Socket(serverAddress, 9797);
                server.getInputStream().read(); //wait for the one-byte ping
                server.close(); //close the connection
                GetGamesController.getInstance().requestGames(); //update the games
            }
            catch (IOException e) {
                Log.e(TAG, 
                        "IO Exception: " + e.getMessage() + " caused by:\n" + e.getCause());
            }
        }
    }
}
