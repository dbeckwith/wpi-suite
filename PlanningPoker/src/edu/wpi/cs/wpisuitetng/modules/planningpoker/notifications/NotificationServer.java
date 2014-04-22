/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.notifications;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * A server that can notify clients when there is an update
 * 
 * @author Team 9
 * @version 1.0
 */
public class NotificationServer extends Thread {
	
	public static final int PORT = 9797;
	
	private static NotificationServer instance = null;
	
    /**
     * @return The instance of the NotificationServer or creates one if it does
     *         not exist.
     */
	public static NotificationServer getInstance() {
		if(instance == null){
			instance = new NotificationServer();
		}
		return instance;
	}
	
	private ServerSocket serverSocket = null;
	private final ArrayList<Socket> clientSockets;
	
	/**
	 * Constructor
	 */
	private NotificationServer(){
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
		}
		clientSockets = new ArrayList<Socket>();
	}
	
	@Override
	public void run(){
		System.out.println("Notification server started");
		while(true){
			try {
				Socket newClient = serverSocket.accept();
				clientSockets.add(newClient);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Notifies all waiting clients of an update, 
	 * then closes their connection
	 */
	public void sendUpdateNotification(){
		//copy waiting clients, then clear list
		final Socket[] clients = clientSockets.toArray(new Socket[]{});
		clientSockets.clear();
		
		for(Socket s:clients){
			try {
				s.getOutputStream().write(0); //notify each client by sending one byte
				s.close(); //close connection
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
