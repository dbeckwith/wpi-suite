/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckListModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.DeckModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 *
 */
public class GetDecksController {
    private GetDecksRequestObserver observer;
    private static GetDecksController instance;
    
    /**
     * Returns a controller for getting decks
     */
    private GetDecksController() {
        observer = new GetDecksRequestObserver(this);
    }
    
    /**
     * Returns the GetDecksController instance
     * 
     * @return
     */
    public static GetDecksController getInstance() {
        if (GetDecksController.instance == null) {
            GetDecksController.instance = new GetDecksController();
        }
        return GetDecksController.instance;
    }
    
    /**
     * Retrieves all decks
     */
    public void retrieveDecks() {
        // GET reads existing objects
        final Request request = Network.getInstance().makeRequest(
                "planningpoker/deck", HttpMethod.GET);
        request.addObserver(observer);
        request.send();
    }
    
    public void receivedDecks(DeckModel[] decks) {
        DeckListModel.getInstance().emptyModel();
        if (decks != null) {
            for (DeckModel deck : decks) {
                DeckListModel.getInstance().addDeck(deck);
            }
        }
    }
}
