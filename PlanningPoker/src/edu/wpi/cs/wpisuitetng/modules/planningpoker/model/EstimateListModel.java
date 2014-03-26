package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.SimpleListObserver;

/**
 * Stores a list of user's estimate
 * 
 * @author Akshay
 * 
 */
public class EstimateListModel extends AbstractListModel<EstimateModel> {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -3957508701397985775L;

	
	private ArrayList<EstimateModel> estimateList;
    private ArrayList<SimpleListObserver> observers;
    
    public EstimateListModel() {
        estimateList = new ArrayList<>();
        observers = new ArrayList<SimpleListObserver>();
    }
    
    /**
     * Add a SimpleListObserver that is notified when the list of estimates is
     * changed
     * 
     * @param slo
     *            The SimpleListObsrever to add
     */
    public void addListListener(SimpleListObserver slo) {
        if (!observers.contains(slo)) {
            observers.add(slo);
        }
    }
    
    /**
     * Add a user's estimate to the list
     * 
     * @param user
     * @param estoimate
     */
    public void addEstimate(EstimateModel e) {
        estimateList.add(e);
        updated();
    }
    
   
    /**
     * Removes a user's estimate from the list. Doesn't do anything if
     * the estimate is not in the list
     * 
     * @param user
     *            the user to remove
     */
    public void removeEstimate(EstimateModel e) {
        if (estimateList.contains(e)) {
            estimateList.remove(e);
            updated();
        }
    }

    /**
     * @return an array containing all of the estimates
     */
    public ArrayList<EstimateModel> getEstimates() {
        return estimateList;
    }
    
    /**
     * Computes the numerical average of all of the estimates
     * 
     * @return the mean (average)
     */
    public float getMean() {
        float mean = 0;
        for (EstimateModel e:estimateList) {
            mean += e.getEstimate() / (estimateList.size());
        }
        return mean;
    }
    
    /**
     * Computes the median of all of the estimates
     * 
     * @return the median
     */
    public float getMedian() {
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

	@Override
	public int getSize() {
		return estimateList.size();
	}

	@Override
	public EstimateModel getElementAt(int index) {
		return estimateList.get(index);
	}
    
}