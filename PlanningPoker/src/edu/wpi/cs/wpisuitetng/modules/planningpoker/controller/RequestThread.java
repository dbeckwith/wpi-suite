package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * A class for requesting users from the server.
 * 
 * @author Sam Carlberg
 */
public class RequestThread extends Thread {
    
    private final AbstractUserController controller;
    
    /**
     * Creates a new thread that notifies the given controller.
     * @param controller
     */
    public RequestThread(AbstractUserController controller) {
        this.controller = controller;
    }
    
    @Override
    public void run() {
        try {
            requestUsers();
        }
        catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * Sends the request.
     */
    private void requestUsers() throws InterruptedException {
        synchronized (controller) {            
            final Request request = Network.getInstance().makeRequest(
                    "core/user", HttpMethod.GET);
            request.addObserver(controller.observer);
            System.out.println("Sending request for user...");
            request.send();
            controller.notifyAll();
        }
    }
    
}
