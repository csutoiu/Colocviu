package ro.pub.cs.systems.eim.practicaltest;

import android.R.bool;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTestMainActivity extends Activity {
	public int firstValue = 0;
	public int secondValue = 0;
	
	EditText firstText, secondText;
	Button firstBtn, secondBtn, navigateBtn;
	
	boolean serviceStatus = false;
	
	private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
	private IntentFilter intentFilter = new IntentFilter();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practical_test_main);
		
		firstText = (EditText)findViewById(R.id.editText1);
		firstText.setText(String.valueOf(firstValue));
		secondText = (EditText)findViewById(R.id.editText2);
		secondText.setText(String.valueOf(secondValue));
		
		firstBtn = (Button)findViewById(R.id.button2);
		firstBtn.setOnClickListener(new clickListener());
		
		secondBtn = (Button)findViewById(R.id.button3);
		secondBtn.setOnClickListener(new clickListener());
		
		navigateBtn = (Button)findViewById(R.id.button1);
		navigateBtn.setOnClickListener(new clickListener());
		
		intentFilter.addAction("ACTION_INTENT");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.practical_test_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onDestroy() {
		Intent intent = new Intent(this, CustomService.class);
		stopService(intent);
		super.onDestroy();
	}
	
	@Override
	public void onResume() {
		registerReceiver(messageBroadcastReceiver, intentFilter);
		super.onResume();
	}
	
	@Override
	public void onPause() {
		unregisterReceiver(messageBroadcastReceiver);
		super.onPause();
	}
	@Override
	public void onSaveInstanceState(Bundle savedInstance) {
		super.onSaveInstanceState(savedInstance);
		
		savedInstance.putInt("FIRST_NUMBER", firstValue);
		savedInstance.putInt("SECOND_NUMBER", secondValue);
	}
	
	// Nu se apeleaza cand se apasa Back pentru ca trece prin onPause(), onStop()
	// si onDestroy(), se presupune ca userul nu doreste salvarea informatiilor
	@Override
	public void onRestoreInstanceState(Bundle savedInstance) {
		if(savedInstance != null) {
			firstText.setText(String.valueOf(savedInstance.get("FIRST_NUMBER")));
			secondText.setText(String.valueOf(savedInstance.get("SECOND_NUMBER")));
		}
	}
	
	@Override
	  protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
	    if (requestCode == 100) {
	      Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
	    }
	  }
	
	private class MessageBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d("[Message]", intent.getStringExtra("message"));
			Toast.makeText(getApplicationContext(),intent.getStringExtra("message") , 
					   Toast.LENGTH_LONG).show();
			
		}
	}
	
	
	private class clickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int id = ((Button)v).getId();
			
			//handler to start a service
			if(id == R.id.button2 || id == R.id.button3) {
				int leftNumberOfClicks = Integer.parseInt(firstText.getText().toString());
				int rightNumberOfClicks = Integer.parseInt(secondText.getText().toString());
					
				if (leftNumberOfClicks + rightNumberOfClicks > 5
		        && !serviceStatus) {
					Intent intent = new Intent(getApplicationContext(), CustomService.class);
					intent.putExtra("firstNumber", leftNumberOfClicks);
					intent.putExtra("secondNumber", rightNumberOfClicks);
					getApplicationContext().startService(intent);
					serviceStatus = true;
				}
			}
			
			switch (id) {
			case R.id.button1:
				Intent intent = new Intent(getApplicationContext(), PracticalTestSecondaryActivity.class);
				int sum = firstValue + secondValue;
				intent.putExtra("SUM_KEY", sum);
				startActivityForResult(intent, 100);
				break;
			case R.id.button2:
				firstValue++;
				firstText.setText(String.valueOf(firstValue));
				break;
			case R.id.button3:
				secondValue++;
				secondText.setText(String.valueOf(secondValue));
				break;
			default:
				break;
			}
		}	
	}
}
