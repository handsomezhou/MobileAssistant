package com.handsomezhou.xcontacts.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.provider.CallLog;
import android.text.TextUtils;
import android.util.Log;

import com.handsomezhou.xcontacts.application.XContactsApplication;
import com.handsomezhou.xcontacts.model.CallRecord;


public class CallRecordHelper {
	private static final String TAG=CallRecordHelper.class.getSimpleName();
	private Context mContext;
	private static CallRecordHelper mInstance = null;
	private List<CallRecord> mBaseCallRecord=null;
	private AsyncTask<Object, List<CallRecord> , List<CallRecord>> mLoadTask = null;
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
		Log.i(TAG, "isCallLogChanged"+isCallLogChanged());
		if(false==isCallLogChanged()){
			return false;
		}
		
		if(true==isSearching()){
			return false;
		}
		Log.i(TAG, "after isCallLogChanged"+isCallLogChanged());
		mLoadTask = new AsyncTask<Object, List<CallRecord>, List<CallRecord>>() {

			@Override
			protected List<CallRecord> doInBackground(Object... params) {
				return loadCallRecord(mContext);
			}

			
			@Override
			protected void onProgressUpdate(List<CallRecord>... values) {
				parseCallRecord(values[0]);//may be refresh ui in the callback function of the function
			}


			@SuppressWarnings("unchecked")
			@Override
			protected void onPostExecute(List<CallRecord> result) {
				publishProgress(result);
				mLoadTask = null;
			}
		}.execute();
		setCallLogChanged(false);

		return true;

	}
	
	private void initCallRecordHelper(){
		mContext=XContactsApplication.getContext();
		if(null==mBaseCallRecord){
			mBaseCallRecord=new ArrayList<CallRecord>();
		}
		setCallLogChanged(true);
		return;
	}
	
	private boolean isSearching() {
		return (mLoadTask != null && mLoadTask.getStatus() == Status.RUNNING);
	}
	
	private List<CallRecord> loadCallRecord(Context context){
		List<CallRecord> callRecords=new ArrayList<CallRecord>();
		HashMap<String, CallRecord> callRecordHashMap=new HashMap<String, CallRecord>();
		
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
				
				if(/*(TextUtils.isEmpty(name))||*/(TextUtils.isEmpty(number))||!((type>=CallLog.Calls.INCOMING_TYPE)&&(type<=CallLog.Calls.MISSED_TYPE))){
					continue;
				}
				
				boolean callRecordBelongSameContactsExist=callRecordHashMap.containsKey(number);
				cr=new CallRecord(id, name, number, type, date, duration);
				if(true==callRecordBelongSameContactsExist){
					CallRecord curCallRecord=callRecordHashMap.get(number);
					curCallRecord.setNextCallRecord(cr);
					
				}else{
					callRecordHashMap.put(number, cr);
				}
				//callRecords.add(cr);
			}
			
			if(null!=cursor){
				cursor.close();
				cursor=null;
			}
			callRecords.addAll(callRecordHashMap.values());
			Collections.sort(callRecords,CallRecord.mDesByDate);
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
		Log.i(TAG, "mBaseCallRecord.size()"+mBaseCallRecord.size());
	}
	
	
	private void showCallRecord(CallRecord callRecord){
		if(null==callRecord){
			return;
		}
		
		Log.i(TAG, "["+callRecord.getContacts().getId()+"]["+callRecord.getContacts().getName()+"]["+callRecord.getContacts().getPhoneNumber()+"]["+callRecord.getType()+"]["+callRecord.getDate()+"]["+callRecord.getDuration()+"]");
	}
}
