package com.handsomezhou.mobileassistant.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.provider.CallLog;
import android.util.Log;

import com.handsomezhou.mobileassistant.main.MobileAssistantApplication;
import com.handsomezhou.mobileassistant.model.CallRecord;


public class CallRecordHelper {
	private static final String TAG="CallRecordHelper";
	private Context mContext;
	private static CallRecordHelper mInstance = null;
	private List<CallRecord> mBaseCallRecord=null;
	private AsyncTask<Object, Object, List<CallRecord>> mLoadTask = null;
	private OnCallLogLoad mOnCallLogLoad;
	private static boolean mCallLogChanged=true;

	public interface OnCallLogLoad{
		void onCallLogLoadSuccess();
		void onCallLogLoadFailed();
	}
	
	public static CallRecordHelper getInstance() {
		if (null == mInstance) {
			mInstance = new CallRecordHelper();
		}

		return mInstance;
	}
	
	private CallRecordHelper(){
		initCallRecordHelper();
	}
	
	public List<CallRecord> getBaseCallRecord() {
		return mBaseCallRecord;
	}

	public void setBaseCallRecord(List<CallRecord> baseCallLog) {
		mBaseCallRecord = baseCallLog;
	}
	
	public OnCallLogLoad getOnCallLogLoad() {
		return mOnCallLogLoad;
	}

	public void setOnCallLogLoad(OnCallLogLoad onCallLogLoad) {
		mOnCallLogLoad = onCallLogLoad;
	}
	
	public  boolean isCallLogChanged() {
		return mCallLogChanged;
	}

	public  void setCallLogChanged(boolean mCallLogChanged) {
		CallRecordHelper.mCallLogChanged = mCallLogChanged;
	}
	
	public boolean startLoadCallRecord(){
		if(false==isCallLogChanged()){
			return false;
		}
		
		if(true==isSearching()){
			return false;
		}
		
		mLoadTask = new AsyncTask<Object, Object, List<CallRecord>>() {

			@Override
			protected List<CallRecord> doInBackground(Object... params) {
				return loadCallRecord(mContext);
			}

			@Override
			protected void onPostExecute(List<CallRecord> result) {
				parseCallRecord(result);
				setCallLogChanged(false);
				mLoadTask = null;
				super.onPostExecute(result);
			}
		}.execute();

		return true;

	}
	
	private void initCallRecordHelper(){
		mContext=MobileAssistantApplication.getContextObject();
		setBaseCallRecord(new ArrayList<CallRecord>());
		setCallLogChanged(true);
		return;
	}
	
	private boolean isSearching() {
		return (mLoadTask != null && mLoadTask.getStatus() == Status.RUNNING);
	}
	
	private List<CallRecord> loadCallRecord(Context context){
		List<CallRecord> callRecords=new ArrayList<CallRecord>();
		
		CallRecord cr=null;
		Cursor cursor=null;
		 String[] projection=new String[]{CallLog.Calls._ID,CallLog.Calls.CACHED_NAME,CallLog.Calls.NUMBER,CallLog.Calls.TYPE,CallLog.Calls.DATE,CallLog.Calls.DURATION,};
		long startLoadTime=System.currentTimeMillis();
		cursor=context.getContentResolver().query(CallLog.Calls.CONTENT_URI, projection, null, null,  CallLog.Calls.DEFAULT_SORT_ORDER);
		
		int idIndex=cursor.getColumnIndex(CallLog.Calls._ID);
		int nameIndex=cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
		int numberIndex=cursor.getColumnIndex(CallLog.Calls.NUMBER);
		int typeIndex=cursor.getColumnIndex(CallLog.Calls.TYPE);
		int dateIndex=cursor.getColumnIndex(CallLog.Calls.DATE);
		int durationIndex=cursor.getColumnIndex(CallLog.Calls.DURATION);
		
		if(null!=cursor){
			while(cursor.moveToNext()){
				String id=cursor.getString(idIndex);
				String name=cursor.getString(nameIndex);
				String number=cursor.getString(numberIndex);
				int type=Integer.valueOf(cursor.getString(typeIndex));
				long date=Long.valueOf(cursor.getString(dateIndex));
				long duration=Long.valueOf(cursor.getString(durationIndex));
				
				cr=new CallRecord(id, name, number, type, date, duration);
				callRecords.add(cr);
			}
			
			cursor.close();
			cursor=null;
		}
		long endLoadTime=System.currentTimeMillis();
		Log.i(TAG, "endLoadTime-startLoadTime=["+(endLoadTime-startLoadTime)+"]callRecords.size()["+callRecords.size()+"]");
		return callRecords;
	}
	
	private void parseCallRecord(List<CallRecord> callRecords){
		if((null==callRecords)||callRecords.size()<1){
			if(null!=mOnCallLogLoad){
				mOnCallLogLoad.onCallLogLoadFailed();
			}
			return ;
		}
		
		for(CallRecord cr:callRecords){
			if(!mBaseCallRecord.contains(cr)){
				showCallRecord(cr);
				mBaseCallRecord.add(cr);
			}
		}
		
		if(null!=mOnCallLogLoad){
			mOnCallLogLoad.onCallLogLoadSuccess();
		}
	}
	
	private void showCallRecord(CallRecord callRecord){
		if(null==callRecord){
			return;
		}
		
		Log.i(TAG, "["+callRecord.getContacts().getId()+"]["+callRecord.getContacts().getName()+"]["+callRecord.getContacts().getPhoneNumber()+"]["+callRecord.getType()+"]["+callRecord.getDate()+"]["+callRecord.getDuration()+"]");
	}
}
