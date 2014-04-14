package edu.wpi.cs.team9.planningpoker.view;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import edu.wpi.cs.team9.planningpoker.Config;
import edu.wpi.cs.team9.planningpoker.R;
import edu.wpi.cs.team9.planningpoker.controller.CurrentUserController;
import edu.wpi.cs.team9.planningpoker.model.Estimate;
import edu.wpi.cs.team9.planningpoker.model.GameRequirementModel;
import edu.wpi.cs.team9.planningpoker.view.Card.CardListener;

public class RequirementFragment extends Fragment implements CardListener {
	
	private static final String TAG = RequirementFragment.class.getSimpleName();
	
	private static final int COLUMNS = 4;
	
	private static final float[] deck = {0.5f, 1, 2, 3, 5, 10, 20, 50, 100};
		
	private static final TableLayout.LayoutParams rowLP = new TableLayout.LayoutParams(
	        TableLayout.LayoutParams.MATCH_PARENT,
	        TableLayout.LayoutParams.MATCH_PARENT,
	        1.0f);
	
	private static final TableRow.LayoutParams cellLP = new TableRow.LayoutParams(
	        TableRow.LayoutParams.MATCH_PARENT,
	        TableRow.LayoutParams.MATCH_PARENT,
	        1.0f);
	
	private RequirementFragmentListener listener;
	
	private GameRequirementModel requirement;
	
	private TextView nameView;
	private TextView descView;
	private TableLayout table;
	private Button submit;
	
	private TextView estimateLabel;
	
	private boolean selectMultiple = true;
	
	private ArrayList<Card> cards;

	public RequirementFragment() {
	}
	
	public void setRequirement(GameRequirementModel req){
		this.requirement = req;		
	}
	
	public void setListener(RequirementFragmentListener rfl){
		listener = rfl;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {		
		cards = new ArrayList<Card>();
		
		View root = inflater.inflate(R.layout.fragment_requirement, null);
					
		nameView = (TextView)root.findViewById(R.id.req_name);
		descView = (TextView)root.findViewById(R.id.description);			
		estimateLabel = (TextView)root.findViewById(R.id.label);
		
		Estimate oldEstimate = getRecordedEstimate();
		if(oldEstimate == null){
			estimateLabel.setText("You have not estimated yet");
		} else {
			estimateLabel.setText("Your previous estimate was "+Card.cardNumberFormat.format(oldEstimate.getEstimate()));
		}
		
		nameView.setText(requirement.getName());
		descView.setText(requirement.getDescription());
		
		submit = (Button)root.findViewById(R.id.submitButton);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String userName = Config.getUserName(getActivity());
				
				Estimate oldEstimate = getRecordedEstimate();
				
				Estimate newEstimate = new Estimate(CurrentUserController.getInstance().findUser(userName), getEstimate(), getSelectedCards()); 
				
				if(oldEstimate == null){
					requirement.addEstimate(newEstimate);
				} else {
					requirement.UpdateEstimate(oldEstimate, newEstimate);
				}
				
				if(listener != null){
					listener.updated(requirement);
				}
			}
		});
		
		table = (TableLayout)root.findViewById(R.id.buttonLayout);
		setUpCardTable();

		Log.d(TAG, "onCreateView()");
		return root;
	}
	
	private void setUpCardTable(){
		cards.clear();
		
		TableRow[] rows = new TableRow[deck.length/COLUMNS + 1];
		for(int i = 0; i < rows.length; i++){
			rows[i] = new TableRow(getActivity());
		}
		
		for(int i = 0; i < deck.length; i++){
			Card card = new Card(getActivity(), deck[i], this);
			cards.add(card);
			rows[i/COLUMNS].addView(card.getView(), cellLP);
		}		
		
		for(TableRow row:rows){
			while(row.getChildCount() < COLUMNS){
				row.addView(new TextView(getActivity()), cellLP);
			}
			table.addView(row, rowLP);
		}
	}

	@Override
	public void cardSelected(Card card) {
		if(!selectMultiple){
			for(Card c:cards){
				if(c != card){
					c.setCardSelected(false);
				}
			}
		}	
						
		for(Card c:cards){
			if(c.isCardSelected()){
				submit.setBackgroundResource(R.drawable.bg_card_button);
				submit.setEnabled(true);	
				return;
			} else {
				submit.setBackgroundResource(R.drawable.bg_card_disable);
				submit.setEnabled(false);	
			}
		}
		
	}
	
	public float getEstimate(){
		float estimate = 0;
		for(Card c:cards){
			if(c.isCardSelected()){
				estimate += c.getValue();				
			}
		}
		return estimate;
	}
	
	public Estimate getRecordedEstimate(){
		String userName = Config.getUserName(getActivity());
		Estimate oldEstimate = null;
		for(Estimate e:requirement.getEstimates()){
			if(e.getUsername().equals(userName)){
				oldEstimate = e;
			}
		}
		return oldEstimate;
	}
	
	public ArrayList<Integer> getSelectedCards(){
		ArrayList<Integer> selected = new ArrayList<Integer>();
		for(Card c:cards){
			if(c.isCardSelected()){
				selected.add((int)c.getValue());
			}
		}
		return selected;
	}
	
	public interface RequirementFragmentListener{
		public void updated(GameRequirementModel requirement);
	}
}

