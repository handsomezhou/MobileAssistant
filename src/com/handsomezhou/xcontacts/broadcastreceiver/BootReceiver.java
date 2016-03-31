package com.handsomezhou.xcontacts.broadcastreceiver;

import com.handsomezhou.xcontacts.service.XContactsService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BootReceiver extends BroadcastReceiver {
	private static final String TAG="BootReceiver";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		Intent MobileAssistantServiceIntent=new Intent(context,XContactsService.class);
		intent.setAction(XContactsService.ACTION_X_CONTACTS_SERVICE);
		context.startService(MobileAssistantServiceIntent);
		Toast.makeText(context,"BootReceiver" ,Toast.LENGTH_LONG).show();
	}

}
