package com.handsomezhou.mobileassistant.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MobileAssistantService extends Service {
	private static final String TAG="MobileAssistantService";
	public static final String ACTION_MOBILE_ASSISTANT_SERVICE="com.handsomezhou.mobileassistant.service.MOBILE_ASSISTANT_SERVICE";
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "MobileAssistantService onCreate()");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG, "MobileAssistantService onStartCommand()");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "MobileAssistantService onDestroy()");
		super.onDestroy();
		
		startService();
	}
	
	private void startService(){
		Intent intent=new Intent();
		intent.setAction(ACTION_MOBILE_ASSISTANT_SERVICE);
		startService(intent);
	}

}
