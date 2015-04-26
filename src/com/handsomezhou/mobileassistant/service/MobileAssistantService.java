package com.handsomezhou.mobileassistant.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.handsomezhou.mobileassistant.helper.ContactsHelper;
import com.handsomezhou.mobileassistant.listener.CustomPhoneStateListener;

public class MobileAssistantService extends Service {
	private static final String TAG="MobileAssistantService";
	public static final String ACTION_MOBILE_ASSISTANT_SERVICE="com.handsomezhou.mobileassistant.service.MOBILE_ASSISTANT_SERVICE";
	private TelephonyManager mTelephonyManager=null;
	private Handler mContactsHandler = new Handler();
	private ContentObserver mContentObserver;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		initListener();
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
		unregisterContentObserver();
		startService();
		
	}
	
	private void startService(){
		Intent intent=new Intent();
		intent.setAction(ACTION_MOBILE_ASSISTANT_SERVICE);
		startService(intent);
	}

	private void initListener(){
		mTelephonyManager=(TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		mTelephonyManager.listen(new CustomPhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);
		registerContentObserver();
				
	}
	
	private void registerContentObserver() {
		if (null == mContentObserver) {
			mContentObserver = new ContentObserver(mContactsHandler) {
				@Override
				public void onChange(boolean selfChange) {
					super.onChange(selfChange);
					ContactsHelper.getInstance().setContactsChanged(true);
					//ContactsHelper.getInstance().startLoadContacts(); //Reparsed contacts when contacts change
					Log.i(TAG, "Contacts onChange");
				}

			};
		}

		getContentResolver().registerContentObserver(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, true,mContentObserver);
		
	}

	private void unregisterContentObserver() {
		if (null != mContentObserver) {
			getContentResolver().unregisterContentObserver(mContentObserver);

		}
	}
	
}
