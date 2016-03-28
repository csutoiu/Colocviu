package ro.pub.cs.systems.eim.practicaltest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class CustomService extends Service {
	private ProcessingThread processingThread = null;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	 @Override
	  public int onStartCommand(Intent intent, int flags, int startId) {
	    int firstNumber = intent.getIntExtra("firstNumber", -1);
	    int secondNumber = intent.getIntExtra("secondNumber", -1);
	    processingThread = new ProcessingThread(this, firstNumber, secondNumber);
	    processingThread.start();
	    return Service.START_REDELIVER_INTENT;
	}

}
