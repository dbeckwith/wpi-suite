package edu.wpi.cs.team9.planningpoker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import edu.wpi.cs.team9.planningpoker.controller.LoginController;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

public class LoginActivity extends Activity {
	
	private static final String TAG = LoginActivity.class.getSimpleName();

	private EditText usernameText;
	private EditText passwordText;
	private EditText projectText;
	private EditText serverText;
	
	private TextView errorView;
	
	private Button loginButton;
	
	private SharedPreferences prefs;
	
	private TextWatcher formWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if( usernameText.getText().toString().isEmpty()
					|| passwordText.getText().toString().isEmpty()
					|| projectText.getText().toString().isEmpty()
					|| serverText.getText().toString().isEmpty()) {
				loginButton.setEnabled(false);
				loginButton.setBackgroundResource(R.drawable.bg_card_disable);
			} else {
				loginButton.setEnabled(true);
				loginButton.setBackgroundResource(R.drawable.bg_card_button);
			}
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
		
		@Override
		public void afterTextChanged(Editable s) {}
	};
	
	private RequestObserver projectObserver = new RequestObserver() {
		
		@Override
		public void responseSuccess(IRequest iReq) {
			ResponseModel response = ((Request)iReq).getResponse();
			if (response.getStatusCode() == 200) {
				LoginController.projectSelectSuccessful(response);
				Log.d(TAG, "Project Select Success!");
				Intent intent = new Intent(getBaseContext(), GameListActivity.class);
				intent.putExtra("project", projectText.getText().toString());
				startActivity(intent);
				setError("");
				
				prefs.edit()
					.putString("project", projectText.getText().toString())
				.commit();
			} else {
				
				setError(String.format("Error %d: %s", response.getStatusCode(), response.getStatusMessage()));
				Log.e(TAG, "Project Select Failed " + response.getStatusCode());
				
			}
		}
		
		@Override
		public void responseError(IRequest iReq) {
			ResponseModel response = ((Request)iReq).getResponse();
			if (iReq.getResponse().getStatusCode() == 403) {
				setError("Incorrect username, password or project.");
			} else {
				setError(String.format("Error %d: %s", response.getStatusCode(), response.getStatusMessage()));
				Log.e(TAG, "Project Select Failed " + response.getStatusCode());			
			}


			hideProgress();
		}
		
		@Override
		public void fail(IRequest iReq, Exception exception) {
			setError("Unable to complete request: "+exception.getMessage());
			Log.e(TAG, exception.getMessage());			


			hideProgress();
		}
	};
	
	private RequestObserver loginObserver = new RequestObserver() {
		
		@Override
		public void responseSuccess(IRequest iReq) {			
		ResponseModel response = ((Request)iReq).getResponse();
			if (response.getStatusCode() == 200) {
				LoginController.loginSuccessful(response, projectText.getText().toString(), projectObserver);
				setError("");	
								
				prefs.edit()
					.putString("username", usernameText.getText().toString())
					.putString("password", passwordText.getText().toString())
					.putString("server", serverText.getText().toString())
				.commit();
				
			} else {
				setError(String.format("Error %d: %s", response.getStatusCode(), response.getStatusMessage()));
				Log.e(TAG, "Login Failed " + response.getStatusCode()); 
			}


			hideProgress();
		}
		
		@Override
		public void responseError(IRequest iReq) {
			ResponseModel response = ((Request)iReq).getResponse();
			if (iReq.getResponse().getStatusCode() == 403) {
				setError("Incorrect username, password or project.");
				Log.e(TAG, "Incorrect username, password or project.");

			} else {
				setError(String.format("Error %d: %s", response.getStatusCode(), response.getStatusMessage()));
				Log.e(TAG, "Login Failed " + response.getStatusCode());
			}


			hideProgress();
			
		}
		
		@Override
		public void fail(IRequest iReq, Exception exception) {
			setError("Unable to complete request: "+exception.getMessage());
			Log.e(TAG, exception.getMessage());	


			hideProgress();
		}
	};
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		
		setContentView(R.layout.activity_login);

		prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		
		usernameText = (EditText)findViewById(R.id.usernameField);
		passwordText = (EditText)findViewById(R.id.passwordField);
		projectText = (EditText)findViewById(R.id.projectField);
		serverText = (EditText)findViewById(R.id.serverField);
		
		usernameText.setText(prefs.getString("username", ""));
		passwordText.setText(prefs.getString("password", ""));
		projectText.setText(prefs.getString("project", ""));
		serverText.setText(prefs.getString("server", ""));

		usernameText.addTextChangedListener(formWatcher);
		passwordText.addTextChangedListener(formWatcher);
		projectText.addTextChangedListener(formWatcher);
		serverText.addTextChangedListener(formWatcher);
		
		
		errorView = (TextView)findViewById(R.id.error);
		errorView.setText("");
		
		loginButton = (Button)findViewById(R.id.login);
		loginButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				setProgressBarIndeterminateVisibility(true);
				Log.d(TAG, "User: "+usernameText.getText().toString());
				Log.d(TAG, "Pass: "+passwordText.getText().toString());
				Log.d(TAG, "Proj: "+projectText.getText().toString());
				Log.d(TAG, "Serv: "+serverText.getText().toString());
				LoginController.sendLoginRequest(
						usernameText.getText().toString(),
						passwordText.getText().toString(),
						serverText.getText().toString(),
						loginObserver);				
			}
		});
		
		setTitle("PlanningPoker Login");

		formWatcher.onTextChanged("", 0, 0, 0);
		
	}
	
	private void setError(final String error){
		runOnUiThread(new Runnable() {			
			@Override
			public void run() {
				errorView.setText(error);				
			}
		});
	}
	
	
	
	private void hideProgress(){
		runOnUiThread(new Runnable() {			
			@Override
			public void run() {
				setProgressBarIndeterminateVisibility(false);				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
