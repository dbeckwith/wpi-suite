package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import java.util.Date;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/**
 * Represents a planning poker game
 * 
 * @author Akshay
 * 
 */
public class GameModel extends AbstractModel {
    
	public static enum GameStatus {
		PENDING("Pending"), COMPLETE("Complete");
		
		public String name;
		
		GameStatus(String stat){
			name = stat;
		}		
	};

	
    public static enum GameType {
        LIVE, DISTRIBUTED
    };
    
    private Requirement[] requirements;
    private Date endDate;
    private GameType type;
    private GameStatus status;
    
    /**
     * Constructor
     * 
     * @param req
     *            The requirement for this game
     * @param end
     *            End time for this game
     * @param type
     *            What type of game this is
     * @param finished
     *            whether or not the game has been manually ended
     */
    public GameModel(Requirement[] req, Date end, GameType type, GameStatus stat) {
        requirements = req;
        endDate = end;
        this.type = type;
        status = stat;
    }
    
    /**
     * 
     * @return The Requirement for this game
     */
    public Requirement[] getRequirements() {
        return requirements;
    }
    
    /**
     * 
     * @return The end time for this game
     */
    public Date getEndTime() {
        return endDate;
    }
    
    /**
     * Returns which type of game this is
     * 
     * @return Either TYPE_LIVE or TYPE_DISTRIBUTED
     */
    public GameType getType() {
        return type;
    }
    
    /**
     * Manually set the game to ended
     * 
     * @param fin
     */
    public void setEnded(boolean fin) {
        status = fin?GameStatus.COMPLETE:GameStatus.PENDING;
    }
    
    public boolean isEnded() {
        return (status == GameStatus.COMPLETE)
                || (endDate.before(new Date(System.currentTimeMillis())));
    }

	@Override
	public void delete() {
		
	}

	@Override
	public Boolean identify(Object arg0) {
		return null;
	}

	@Override
	public void save() {
		
	}

	@Override
	public String toJSON() {
		return new Gson().toJson(this, GameModel.class);
	}
    
}
