package br.com.while42.loopplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MountSDCardReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("DEBUG", "Storage: " + intent.getData());

		String action = intent.getAction();
		if (action.equalsIgnoreCase(Intent.ACTION_MEDIA_REMOVED)
				|| action.equalsIgnoreCase(Intent.ACTION_MEDIA_MOUNTED)
				|| action.equalsIgnoreCase(Intent.ACTION_MEDIA_UNMOUNTED)
				|| action.equalsIgnoreCase(Intent.ACTION_MEDIA_BAD_REMOVAL)
				|| action.equalsIgnoreCase(Intent.ACTION_MEDIA_EJECT)) {

			Log.d("DEBUG", "Action: " + action);
		}
	}

}
