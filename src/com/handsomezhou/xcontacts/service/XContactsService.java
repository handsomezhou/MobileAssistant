package com.handsomezhou.xcontacts.service;

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

import com.handsomezhou.xcontacts.helper.ContactsHelper;
import com.handsomezhou.xcontacts.listener.CustomPhoneStateListener;

public class XContactsService extends Service {
	private static final String TAG=XContactsService.class.getSimpleName();
	public static final String ACTION_X_CONTACTS_SERVICE="com.handsomezhou.xcontacts.service.ACTION_X_CONTACTS_SERVICE";
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
		Log.i(TAG, " onCreate()");
		
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(TAG, " onStartCommand()");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, " onDestroy()");
		super.onDestroy();
		unregisterContentObserver();
		startService();
		
	}
	
	private void startService(){
		Intent intent=new Intent();
		intent.setAction(ACTION_X_CONTACTS_SERVICE);
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
					ContactsHelper.setContactsChanged(true);
					//ContactsHelper.getInstance().startLoadContacts(); //Reparsed contacts when contacts change
					Log.i(TAG, "Contacts onChange"+ContactsHelper.isContactsChanged());
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
