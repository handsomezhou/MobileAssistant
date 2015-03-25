package com.handsomezhou.mobileassistant.model;

public class CallRecord {
	private Contacts mContacts; 
	
	private int mType;
	private long mDate;
	private long mDuration;
	
	
	public CallRecord() {
		super();
	}
	
	public CallRecord(String id, String name, String number, int type, long date,
			long duration) {
		super();
		setContacts(new Contacts());
		getContacts().setId(id);
		getContacts().setName(name);
		getContacts().setPhoneNumber(number);
		mType = type;
		mDate = date;
		mDuration = duration;
	}
	
	public Contacts getContacts() {
		return mContacts;
	}

	public void setContacts(Contacts contacts) {
		mContacts = contacts;
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
	
	public long getDuration() {
		return mDuration;
	}
	
	public void setDuration(long duration) {
		mDuration = duration;
	}
}
