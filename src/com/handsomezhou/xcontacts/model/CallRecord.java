package com.handsomezhou.xcontacts.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CallRecord implements Cloneable{
	private Contacts mContacts; 
	
	private String mId;
	private int mType;
	private long mDate;
	private DateTime mDateTime;//mTime details 
	private long mDuration;
	private CallRecord mNextCallRecord;//point the call record information who belong to the same contacts. 
	
	public CallRecord() {
		super();
		mNextCallRecord=null;
	}
	
	public CallRecord(String id, String name, String number, int type, long date,
			long duration) {
		super();
		setContacts(new Contacts());
		//getContacts().setId(id);
		getContacts().setName(name);
		getContacts().setPhoneNumber(number);
		mId=id;
		mType = type;
		mDate = date;
		mDateTime=new DateTime(date);
		mDuration = duration;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		CallRecord obj=(CallRecord)super.clone();
		obj.mContacts=(Contacts) mContacts.clone();
		obj.mDateTime=(DateTime) mDateTime.clone();
		obj.mNextCallRecord=mNextCallRecord;
		return super.clone();
	}

	public static Comparator<CallRecord> mAscByDate=new Comparator<CallRecord>(){

		@Override
		public int compare(CallRecord lhs, CallRecord rhs) {
			
			return (lhs.mDate>rhs.mDate)?(1):(-1);
		}
		
	};
	
	public static Comparator<CallRecord> mDesByDate=new Comparator<CallRecord>(){

		@Override
		public int compare(CallRecord lhs, CallRecord rhs) {
			
			return (lhs.mDate<rhs.mDate)?(1):(-1);
		}
		
	};
	
	/**
	 * convert the variable of CallRecord to the list of CallRecord
	 * @param callRecord
	 * @return
	 */
	public static List<CallRecord> praseCallRecords(CallRecord callRecord){
		List<CallRecord> callRecords=new ArrayList<CallRecord>();
		do{
			if(null==callRecord){
				break;
			}
			CallRecord currentCallRecord=callRecord;
			while(null!=currentCallRecord){
				callRecords.add(currentCallRecord);
				currentCallRecord=currentCallRecord.getNextCallRecord();
			}
			break;
		}while(false);
		
		return callRecords;
	}
	
	public Contacts getContacts() {
		return mContacts;
	}

	public void setContacts(Contacts contacts) {
		mContacts = contacts;
	}

	public String getId() {
		return mId;
	}

	public void setId(String id) {
		mId = id;
	}

	public int getType() {
		return mType;
	}
	
	public void setType(int type) {
		mType = type;
	}
	
	public long getDate() {
		return mDate;
	}
	
	public void setDate(long date) {
		mDate = date;
	}
	
	public DateTime getDateTime() {
		return mDateTime;
	}

	public void setDateTime(DateTime dateTime) {
		mDateTime = dateTime;
	}
	
	public long getDuration() {
		return mDuration;
	}
	
	public void setDuration(long duration) {
		mDuration = duration;
	}
	
	public CallRecord getNextCallRecord() {
		return mNextCallRecord;
	}

	public void setNextCallRecord(CallRecord nextCallRecord) {
		mNextCallRecord = nextCallRecord;
	}

	
}
