package edu.wpi.cs.wpisuitetng.modules.planningpoker.notifications;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetGamesController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.UpdateGamesController;
import edu.wpi.cs.wpisuitetng.network.Network;

public class NotificationClient extends Thread {
		
	private static NotificationClient instance;
	
	public static NotificationClient getInstance() {
		if(instance == null){
			instance = new NotificationClient();
		}
		return instance;
	}
	
	private InetAddress serverAddress;
		
	private NotificationClient(){	
		try {
			serverAddress = InetAddress.getByName(new URL(Network.getInstance().getDefaultNetworkConfiguration().getApiUrl()).getHost());
			System.out.println("NS client address : "+ serverAddress.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run(){	
		System.out.println("NS client started");
		while(true){
			try {				
				Socket server = new Socket(serverAddress, NotificationServer.PORT);
				System.out.println("NS client open   "+System.currentTimeMillis());
				server.getInputStream().read();
				System.out.println("NS client update "+System.currentTimeMillis());
				server.close();
				GetGamesController.getInstance().retrieveGames();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
}