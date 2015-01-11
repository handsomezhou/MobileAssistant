package com.handsomezhou.mobileassistant.broadcast;

import com.handsomezhou.mobileassistant.service.MobileAssistantService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BootReceiver extends BroadcastReceiver {
	private static final String TAG="BootReceiver";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		Intent MobileAssistantServiceIntent=new Intent(context,MobileAssistantService.class);
		intent.setAction(MobileAssistantService.ACTION_MOBILE_ASSISTANT_SERVICE);
		context.startService(MobileAssistantServiceIntent);
		Toast.makeText(context,"BootReceiver" ,Toast.LENGTH_LONG).show();
	}

}
