package com.handsomezhou.mobileassistant.model;

public class CallRecord {
	private String mId;
	private String mName;
	private String mNumber;
	private int mType;
	private long mDate;
	private long mDuration;
	
	
	public CallRecord() {
		super();
	}
	
	public CallRecord(String id, String name, String number, int type, long date,
			long duration) {
		super();
		mId = id;
		mName = name;
		mNumber = number;
		mType = type;
		mDate = date;
		mDuration = duration;
	}

	public String getId() {
		return mId;
	}
	public void setId(String id) {
		mId = id;
	}
	public String getName() {
		return mName;
	}
	public void setName(String name) {
		mName = name;
	}
	public String getNumber() {
		return mNumber;
	}
	public void setNumber(String number) {
		mNumber = number;
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
