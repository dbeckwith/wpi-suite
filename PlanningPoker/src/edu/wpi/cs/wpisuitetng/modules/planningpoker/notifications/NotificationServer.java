package edu.wpi.cs.wpisuitetng.modules.planningpoker.notifications;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class NotificationServer extends Thread {
	
	public static final int PORT = 9797;
	
	private static NotificationServer instance;
	
	public static NotificationServer getInstance() {
		if(instance == null){
			instance = new NotificationServer();
		}
		return instance;
	}
	
	private ServerSocket serverSocket;
	private ArrayList<Socket> clientSockets;
	
	private NotificationServer(){	
		try {
			serverSocket = new ServerSocket(PORT);
			System.out.println("NS socket constructed");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		clientSockets = new ArrayList<Socket>();
	}
	
	public void run(){	
		System.out.println("NS server started");
		while(true){
			try {
				Socket newClient = serverSocket.accept();
				clientSockets.add(newClient);
				System.out.println("NS client :" + newClient.getInetAddress().toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public void sendUpdateNotification(){
		System.out.println("NS send update");
		Socket[] clients = clientSockets.toArray(new Socket[]{});
		clientSockets.clear();
		
		for(Socket s:clients){
			try {
				s.getOutputStream().write(0);
				s.close();
				
				System.out.println("\tNS notified "+s.getInetAddress().toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
