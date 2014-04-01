/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This handles game creation requests
 * 
 * @author Brett Ammeson
 * 
 */
public class AddGameController {
	private static AddGameController instance;
	private AddGameRequestObserver observer;

	/**
	 * Construct an AddGameController for the given model, view pair
	 */
	public AddGameController() {
		setObserver(new AddGameRequestObserver(this));
	}

	/**
	 * 
	 * @return the instance of the AddGameController or creates one if it does
	 *         not exist.
	 */
	public static AddGameController getInstance() {
		if (AddGameController.instance == null) {
			AddGameController.instance = new AddGameController();
		}

		return AddGameController.instance;
	}

	/**
	 * This method adds a game to the server.
	 * 
	 * @param newGame
	 *            is the game to be added to the server.
	 */
	public void addGame(GameModel newGame) {
		final Request request = Network.getInstance().makeRequest(
				"planningpoker/game", HttpMethod.PUT); // PUT ==
																		// create
		request.setBody(newGame.toJSON()); // put the new game in the body of
											// the request
		request.addObserver(observer); // add an observer to process the
										// response
		request.send();
	}

	/**
	 * @return the observer
	 */
	public AddGameRequestObserver getObserver() {
		return observer;
	}

	/**
	 * @param observer
	 *            the observer to set
	 */
	public void setObserver(AddGameRequestObserver observer) {
		this.observer = observer;
	}
}