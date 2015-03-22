package com.handsomezhou.mobileassistant.listener;

import com.handsomezhou.mobileassistant.util.CallRecordHelper;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * custom PhoneStateListener
 * 
 * @author handsomezhou
 */
public class CustomPhoneStateListener extends PhoneStateListener {
	private static final String TAG="CustomPhoneStateListener";
	@Override
	public void onCallStateChanged(int state, String incomingNumber) {
		switch (state) {
		case TelephonyManager.CALL_STATE_IDLE:
			Log.i(TAG, "state:["+state+"]"+"incomingNumber:["+incomingNumber+"]");
			break;
		case TelephonyManager.CALL_STATE_RINGING:
			Log.i(TAG, "state:["+state+"]"+"incomingNumber:["+incomingNumber+"]");
			break;
		case TelephonyManager.CALL_STATE_OFFHOOK:
			Log.i(TAG, "state:["+state+"]"+"incomingNumber:["+incomingNumber+"]");
			break;

		default:
			break;
			
		}
		CallRecordHelper.getInstance().startLoadCallRecord();
		super.onCallStateChanged(state, incomingNumber);
	}

}
