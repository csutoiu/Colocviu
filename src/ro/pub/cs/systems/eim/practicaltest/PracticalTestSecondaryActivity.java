package ro.pub.cs.systems.eim.practicaltest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PracticalTestSecondaryActivity extends Activity {

	public Button okBtn, cancelBtn;
	public EditText text;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practical_test_secondary);
		
		okBtn = (Button)findViewById(R.id.ok);
		okBtn.setOnClickListener(new clickListener());
		
		cancelBtn = (Button)findViewById(R.id.cancel);
		cancelBtn.setOnClickListener(new clickListener());
		
		text = (EditText)findViewById(R.id.editText1);
		
		Intent intentFromParent = getIntent();
		if(intentFromParent!=null &&
				intentFromParent.getExtras().containsKey("SUM_KEY")) {
			int sum = intentFromParent.getIntExtra("SUM_KEY", 0);
			text.setText(String.valueOf(sum));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.practical_test_secondary, menu);
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
	
	private class clickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int id = ((Button)v).getId();
			switch (id) {
			case R.id.ok:
				setResult(RESULT_OK, null);
				break;
			case R.id.cancel:
				setResult(RESULT_CANCELED, null);
				break;
			default:
				break;
			}
			finish();
		}	
	}
}
