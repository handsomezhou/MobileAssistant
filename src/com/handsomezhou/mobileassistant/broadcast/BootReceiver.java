package com.handsomezhou.mobileassistant.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BootReceiver extends BroadcastReceiver {
	private static final String TAG="BootReceiver";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context,"BootReceiver" ,Toast.LENGTH_LONG).show();
	}

}
