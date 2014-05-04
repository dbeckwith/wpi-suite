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
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

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
        if (NotificationServer.instance == null) {
            NotificationServer.instance = new NotificationServer();
        }
        return NotificationServer.instance;
    }
    
    private ServerSocket serverSocket = null;
    private final List<Socket> clientSockets;
    
    /**
     * Constructor
     */
    private NotificationServer() {
        try {
            serverSocket = new ServerSocket(NotificationServer.PORT);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        clientSockets = Collections.synchronizedList(new ArrayList<Socket>());
    }
    
    @Override
    public void run() {
        Logger.getGlobal().info("Notification server started");
        while (true) {
            try {
                Socket newClient = serverSocket.accept();
                clientSockets.add(newClient);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Notifies all waiting clients of an update,
     * then closes their connection
     */
    public void sendUpdateNotification() {
        //copy waiting clients, then clear list
        final Socket[] clients = clientSockets.toArray(new Socket[] {});
        clientSockets.clear();
        
        for (Socket s : clients) {
            try {
                s.getOutputStream().write(0); //notify each client by sending one byte
                s.close(); //close connection
            }
            catch (IOException e) {
                System.out.print("");
            }
        }
    }
}
