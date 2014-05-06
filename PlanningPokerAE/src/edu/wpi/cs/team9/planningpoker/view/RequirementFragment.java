/*******************************************************************************
 * Copyright (c) 2012-2014 -- WPI Suite
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package edu.wpi.cs.team9.planningpoker.view;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.Service;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import edu.wpi.cs.team9.planningpoker.Config;
import edu.wpi.cs.team9.planningpoker.R;
import edu.wpi.cs.team9.planningpoker.controller.CurrentUserController;
import edu.wpi.cs.team9.planningpoker.model.DeckModel;
import edu.wpi.cs.team9.planningpoker.model.Estimate;
import edu.wpi.cs.team9.planningpoker.model.GameModel;
import edu.wpi.cs.team9.planningpoker.model.GameRequirementModel;
import edu.wpi.cs.team9.planningpoker.view.Card.CardListener;

/**
 * Allows a user to vote on requirements using cards or input
 * @author akshay 
 */
public class RequirementFragment extends Fragment implements CardListener {
	
	private static final String TAG = RequirementFragment.class.getSimpleName();
	
	private static final int COLUMNS = 4;
			
	private static final TableLayout.LayoutParams rowLP = new TableLayout.LayoutParams(
	        TableLayout.LayoutParams.MATCH_PARENT,
	        TableLayout.LayoutParams.MATCH_PARENT,
	        1.0f);
	
	private static final TableRow.LayoutParams cellLP = new TableRow.LayoutParams(
	        TableRow.LayoutParams.MATCH_PARENT,
	        TableRow.LayoutParams.MATCH_PARENT,
	        1.0f);
	
	private RequirementFragmentListener listener;
	
	private GameModel game;
	private GameRequirementModel requirement;
	
	private TextView nameView;
	private TextView descView;
	private TableLayout table;
	private Button submit;
	
	private TextView estimateLabel;
		
	private DeckModel deck;
	private EditText numberInput;
	
	private ArrayList<Card> cards;
	
	/**
	 * Empty constructor
	 */
	public RequirementFragment() {
	}
	
	/**
	 * sets the game of the requirement
	 * @param g
	 */
	public void setGame(GameModel g){
		game = g;
	}
	
	/**
	 * set the requirement being voted on
	 * @param req
	 */
	public void setRequirement(GameRequirementModel req){
		this.requirement = req;		
	}
	
	/**
	 * sets the deck being used
	 * @param dm the deckmodel
	 */
	public void setDeck(DeckModel dm){
		this.deck = dm;
	}
	
	/**
	 * sets the vote submission listener
	 * @param rfl
	 */
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
			estimateLabel.setText("Your current estimate is "+Card.cardNumberFormat.format(oldEstimate.getEstimate()));
		}
		
		nameView.setText(requirement.getName());
		descView.setText(requirement.getDescription());
		
		submit = (Button)root.findViewById(R.id.submitButton);
		//submit the vote estimate
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
				
				estimateLabel.setText("Your current estimate is "+Card.cardNumberFormat.format(getEstimate()));
				
				if(listener != null){
					listener.updated(requirement);
				}
			}
		});
		
		table = (TableLayout)root.findViewById(R.id.buttonLayout);
		if(game.isStarted()){
			if(game.getDeck().isNone()){
				setUpInputCard();
			} else {
				setUpCardTable();
			}
		} else {
			estimateLabel.setText("This game has not started yet.");
		}

		Log.d(TAG, "onCreateView()");
		return root;
	}
	
	/**
	 * sets up the grid of card in the deck
	 */
	private void setUpCardTable(){
		cards.clear();
		
		TableRow[] rows = new TableRow[deck.getCards().size()/COLUMNS + 1];
		for(int i = 0; i < rows.length; i++){
			rows[i] = new TableRow(getActivity());
		}
		
		for(int i = 0; i < deck.getCards().size(); i++){
			Card card = new Card(getActivity(), deck.getCards().get(i), this);
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
	
	/**
	 * sets up a number input for games without a deck
	 */
	private void setUpInputCard(){
		LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		View inputView = inflater.inflate(R.layout.card_spinner, null);
		
		numberInput = (EditText)inputView.findViewById(R.id.picker);
		if(game.getDeck().getMaxEstimate() != DeckModel.NO_LIMIT){
			numberInput.setHint("Max: "+Card.cardNumberFormat.format(game.getDeck().getMaxEstimate()));
		}
		numberInput.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}
			
			@Override
			public void afterTextChanged(Editable s) {
				float max = (game.getDeck().getMaxEstimate() == DeckModel.NO_LIMIT)?
						Float.MAX_VALUE:game.getDeck().getMaxEstimate();
				try{
					float newValue = Float.parseFloat(s.toString());	
					newValue = Math.max(0, Math.min(max, newValue));
					String newStr = Card.cardNumberFormat.format(newValue);
					if(!newStr.equals(s.toString())){
						s.replace(0, s.length(), newStr);
					}
					setSubmitEnabled(true);
				}catch(Exception e){}
				
			}
		});
		
		Estimate old = getRecordedEstimate();
		if(old != null){
			numberInput.setText(Card.cardNumberFormat.format(old.getEstimate()));
		}
		
		TableRow row = new TableRow(getActivity());
		row.addView(inputView, cellLP);
		table.addView(row, rowLP);
	}

	@Override
	public void cardSelected(Card card) {
		if(!deck.canAllowsMultipleSelection()){
			for(Card c:cards){
				if(c != card){
					c.setCardSelected(false);
				}
			}
		}	
		

		estimateLabel.setText("Total: "+Card.cardNumberFormat.format(getEstimate()));
		
		for(Card c:cards){
			if(c.isCardSelected()){
				setSubmitEnabled(true);
				return;
			} 
		}
		
		
		setSubmitEnabled(false);
		
	}
	
	/**
	 * sets the state of the submit button
	 * @param enabled
	 */
	private void setSubmitEnabled(boolean enabled){
		if(enabled){
			submit.setBackgroundResource(R.drawable.bg_card_button);
			submit.setEnabled(true);
		} else {
			submit.setBackgroundResource(R.drawable.bg_card_disable);
			submit.setEnabled(false);	
		}
	}
	
	/**
	 * computes the selected estimate
	 * @return
	 */
	public float getEstimate(){
		float estimate = 0;
		if(game.getDeck().isNone()){
			estimate = Float.parseFloat(numberInput.getText().toString());
		} else {
			for(Card c:cards){
				if(c.isCardSelected()){
					estimate += c.getValue();				
				}
			}
		}
		return estimate;
	}
	
	/**
	 * gets the estimate stored in the server for this user
	 * @return
	 */
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
	
	/**
	 * returns a list of selected card indices
	 * @return list of indices
	 */
	public ArrayList<Integer> getSelectedCards(){
		ArrayList<Integer> selected = new ArrayList<Integer>();
		for(Card c:cards){
			if(c.isCardSelected()){
				selected.add(cards.indexOf(c));
			}
		}
		return selected;
	}
	
	/**
	 * gets called when a vote is submitted
	 * @author akshay
	 *
	 */
	public interface RequirementFragmentListener{
		public void updated(GameRequirementModel requirement);
	}
}

