package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.SimpleListObserver;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/**
 * Represents a planning poker game
 * 
 * @author Akshay
 * 
 */
public class GameModel extends AbstractModel {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -2777122181981150898L;

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
    
	private ArrayList<Estimate> estimateList;
    private ArrayList<SimpleListObserver> observers;
    private ArrayList<User> userList;
    
    private int id;
    private String name;
    private String description;
    private Requirement[] requirements;
    private Date endDate;
    private GameType type;
    private GameStatus status;
    
    public GameModel() {
        this.id = -1;
        this.name = null;
        this.description = null;
        this.requirements = null;
        this.endDate = null;
        this.type = null;
        this.status = null;   
        estimateList = null;
        observers = null;
    }

    

    /**
     * Constructor
     * @param id
     * @param name
     * @param requirements
     * @param end
     * @param type
     * @param status
     * @param userList list of users in the game
     */
    public GameModel(int id, String name, String description, Requirement[] requirements, Date end, GameType type, GameStatus status, ArrayList<User> userList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.requirements = requirements;
        this.endDate = end;
        this.type = type;
        this.status = status;
        this.userList = userList;
        estimateList = new ArrayList<>();
        observers = new ArrayList<SimpleListObserver>();
    }
    
    /**
     * Constructor
     * @param requirements
     * @param end
     * @param type
     * @param status
     * @param estimates
     * @param userList list of users in the game
     */
    public GameModel(String name, String description, Requirement[] requirements, Date end, GameType type, GameStatus status, ArrayList<Estimate> estimates, ArrayList<User> userList) {
        this.id = -1;
        this.name = name;
        this.description = description;
        this.requirements = requirements;
        this.endDate = end;
        this.type = type;
        this.status = status;
        this.estimateList = estimates;
        this.userList = userList;
        observers = new ArrayList<SimpleListObserver>();
    }
    
    /**
     * 
     * @return the name of this game
     */
    public String getName() {
        return name;
    }
    
    public void setID(int id) {
        this.id = id;
    }



    /**
     * 
     * @return the name of this game
     */
    public String getDescription() {
        return description;
    }
    
    
    
    /**
     * Add a SimpleListObserver that is notified when the list of estimates is
     * changed
     * 
     * @param slo The SimpleListObsrever to add
     */
    public void addListListener(SimpleListObserver slo) {
        if (!observers.contains(slo)) {
            observers.add(slo);
        }
    }
    
    /**
     * Add a user's estimate to the list 
     * @param e the estimate to add
     */
    public void addEstimate(Estimate e) {
        estimateList.add(e);
        updated();
    }
    
   
    /**
     * Removes a user's estimate from the list. Doesn't do anything if
     * the estimate is not in the list
     * 
     * @param e the estimate to remove
     */
    public void removeEstimate(Estimate e) {
        if (estimateList.contains(e)) {
            estimateList.remove(e);
            updated();
        }
    }

    /**
     * @return an array containing all of the estimates
     */
    public ArrayList<Estimate> getEstimates() {
        return estimateList;
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
        if (estimateList.size() == userList.size()){
        	setEnded(true);
        }
    	else if((endDate.before(new Date(System.currentTimeMillis())))){
            setEnded(true);
        }
        return (status == GameStatus.COMPLETE);
    }
    
    /**
     * Computes the numerical average of all of the estimates
     * 
     * @return the mean (average)
     */
    public float getEstimateMean() {
        float mean = 0;
        for (Estimate e:estimateList) {
            mean += e.getEstimate() / (estimateList.size());
        }
        return mean;
    }
    
    /**
     * Computes the median of all of the estimates
     * 
     * @return the median
     */
    public float getEstimateMedian() {
    	int count = estimateList.size();
        if (estimateList.size() % 2 == 1) {
        	return estimateList.get(count/2).getEstimate();
        } else {
        	return (estimateList.get(count/2).getEstimate() + estimateList.get(count/2 -1).getEstimate())/2;
        }
    }

    /**
     * Notifies all observers when that the list has changed
     */
    private void updated() {
        for (SimpleListObserver observer : observers) {
            observer.listUpdated();
        }
    }
    
	public int getSize() {
		return estimateList.size();
	}
	
	public Estimate getElementAt(int index) {
		return estimateList.get(index);
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toJSON() {
		return new Gson().toJson(this, GameModel.class);
	}

	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}



    public static GameModel fromJSON(String json) {
        final Gson parser = new Gson();
        return parser.fromJson(json, GameModel.class);
    }




    public int getID() {
        return this.id;
    }



    public void copyFrom(GameModel g) {
        this.id = g.id;
        this.name = g.name;
        this.description = g.description;
        this.requirements = g.requirements;
        this.endDate = g.endDate;
        this.type = g.type;
        this.status = g.status; 
        estimateList = g.estimateList;
        observers = g.observers;
    }
    
}
