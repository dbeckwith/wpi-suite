/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * This handles all requests for games
 * 
 * @author Brett Ammeson
 * 
 */
public class GetGamesRequestObserver implements RequestObserver {
    
    private GetGamesController controller;
    
    /**
     * Constructs the observer given a GetGamesController
     * 
     * @param controller
     *            the controller used to retrieve games
     */
    public GetGamesRequestObserver(GetGamesController controller) {
        this.controller = controller;
    }
    
    /**
     * Parse the games out of the response body and pass them to the controller
     * 
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
     */
    @Override
    public void responseSuccess(IRequest iReq) {
        // Convert the JSON array of games to a Games object array
        GameModel games[] = GameModel.fromJSONArray(iReq.getResponse()
                .getBody());
        
        // Pass these Games to the controller
        controller.receivedGames(games);
    }
    
    /**
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
     */
    @Override
    public void responseError(IRequest iReq) {
        fail(iReq, null);
    }
    
    /**
     * Put an error games in the PostBoardPanel if the request fails.
     * 
     * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest,
     *      java.lang.Exception)
     */
    @Override
    public void fail(IRequest iReq, Exception exception) {
        // TODO: Needs to be updated with a GameModel indicating an error
        GameModel[] errorGames = { new GameModel() };
        controller.receivedGames(errorGames);
    }
}
