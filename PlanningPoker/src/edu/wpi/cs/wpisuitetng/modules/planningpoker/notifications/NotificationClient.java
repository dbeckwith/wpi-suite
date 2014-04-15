/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.notifications;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetGamesController;
import edu.wpi.cs.wpisuitetng.network.Network;

/**
 * A client that continually listens for updates from the server
 * @author Akshay
 *
 */
public class NotificationClient extends Thread {
		
	private static NotificationClient instance = null;
	
    /**
     * @return the instance of the NotificationClient or creates one if it does
     *         not exist.
     */
	public static NotificationClient getInstance() {
		if(instance == null){
			instance = new NotificationClient();
		}
		return instance;
	}
	
	private InetAddress serverAddress;
	
	/**
	 * Constructor
	 */
	private NotificationClient(){
		try {
			//get ip address of the server
			serverAddress = InetAddress.getByName(new URL(Network.getInstance().getDefaultNetworkConfiguration().getApiUrl()).getHost());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		System.out.println("Notification client started");
		while(true){
			try {	
				Socket server = new Socket(serverAddress, NotificationServer.PORT); //open a connection to the notification server
				server.getInputStream().read(); //wait for the one-byte ping
				server.close(); //close the connection
				GetGamesController.getInstance().retrieveGames(); //update the games
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}
}