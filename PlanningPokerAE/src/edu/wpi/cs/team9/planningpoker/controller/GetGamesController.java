package edu.wpi.cs.team9.planningpoker.controller;

import android.util.Log;

import com.google.gson.Gson;

import edu.wpi.cs.team9.planningpoker.model.GameModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class GetGamesController implements RequestObserver {
	
	private static final String TAG = GetGamesController.class.getSimpleName();
	
	private static GetGamesObserver observer;	
	private static GetGamesController instance;
	
	public static GetGamesController getInstance(){
		if(instance == null){
			instance = new GetGamesController();
		}
		return instance;
	}
	
	private GetGamesController(){
		observer = new GetGamesObserver() {			
			@Override
			public void receivedGames(GameModel[] games) {}
		};	
	}
	
	public void requestGames(){
		Request gameRequest = Network.getInstance().makeRequest("planningpoker/game", HttpMethod.GET);
		gameRequest.addObserver(this);
		gameRequest.send();
	}
	
	public void setObserver(GetGamesObserver ob){
		if(ob != null){
			observer = ob;
		}
	}

	@Override
	public void responseSuccess(IRequest iReq) {
		Log.d(TAG, iReq.getResponse().getBody());
        // Convert the JSON array of games to a Games object array
        GameModel games[] = GameModel.fromJSONArray(iReq.getResponse()
                .getBody());
        
        // Pass these Games to the controller
        observer.receivedGames(games);
	}

	@Override
	public void responseError(IRequest iReq) {
		fail(iReq, null);
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		observer.receivedGames(null);
	}
	
	public interface GetGamesObserver{
		public void receivedGames(GameModel[] games);
	}
	
		

}
