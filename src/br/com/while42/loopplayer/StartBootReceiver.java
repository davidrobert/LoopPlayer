package br.com.while42.loopplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StartBootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			
			Intent main = new Intent(context, MainActivity.class);
			main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(main);
		}
	}
	
}
