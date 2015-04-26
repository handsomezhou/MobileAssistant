package com.handsomezhou.mobileassistant.listener;

import com.handsomezhou.mobileassistant.helper.CallRecordHelper;

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
	private static int mLastState=TelephonyManager.CALL_STATE_IDLE;
	@Override
	public void onCallStateChanged(int state, String incomingNumber) {
		switch (state) {
		case TelephonyManager.CALL_STATE_IDLE:
			Log.i(TAG, "state:["+state+"]"+"incomingNumber:["+incomingNumber+"]");
			if((getmLastState()==TelephonyManager.CALL_STATE_RINGING)||(getmLastState()==TelephonyManager.CALL_STATE_OFFHOOK)){
				CallRecordHelper.getInstance().setCallLogChanged(true);
				//CallRecordHelper.getInstance().startLoadCallRecord();
			}
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
		setmLastState(state);
		super.onCallStateChanged(state, incomingNumber);
	}
	
	public static int getmLastState() {
		return mLastState;
	}
	public static void setmLastState(int mLastState) {
		CustomPhoneStateListener.mLastState = mLastState;
	}

}
