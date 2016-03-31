package com.handsomezhou.xcontacts.model;

import java.util.Calendar;

public class DateTime implements Cloneable{
	private int mYear;
	private int mMonth;
	private int mDayOfMonth;
	private int mDayOfWeek;
	private int mHourOfDay;
	private int mMinute;
	private int mSecond;
	
	/**
	 * {@link Calendar#getTimeInMillis()}
	 */
	private long mTime;

	
	public DateTime() {
		super();
		Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDayOfMonth = c.get(Calendar.DAY_OF_MONTH);
		mDayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		mHourOfDay = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);
		mSecond = c.get(Calendar.SECOND);
		mTime=c.getTimeInMillis();
	}
	
	public DateTime(long time) {
		super();
		mTime = time;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(mTime);
		
		this.mYear = calendar.get(Calendar.YEAR);
		this.mMonth = calendar.get(Calendar.MONTH);
		this.mDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		this.mDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		this.mHourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
		this.mMinute = calendar.get(Calendar.MINUTE);
		this.mSecond = calendar.get(Calendar.SECOND);
	}
	
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public int getYear() {
		return mYear;
	}

	public void setYear(int year) {
		mYear = year;
	}

	public int getMonth() {
		return mMonth;
	}

	public void setMonth(int month) {
		mMonth = month;
	}

	public int getDayOfMonth() {
		return mDayOfMonth;
	}

	public void setDayOfMonth(int dayOfMonth) {
		mDayOfMonth = dayOfMonth;
	}

	public int getDayOfWeek() {
		return mDayOfWeek;
	}

	public void setDayOfWeek(int dayOfWeek) {
		mDayOfWeek = dayOfWeek;
	}

	public int getHourOfDay() {
		return mHourOfDay;
	}

	public void setHourOfDay(int hourOfDay) {
		mHourOfDay = hourOfDay;
	}

	public int getMinute() {
		return mMinute;
	}

	public void setMinute(int minute) {
		mMinute = minute;
	}

	public int getSecond() {
		return mSecond;
	}

	public void setSecond(int second) {
		mSecond = second;
	}

	public long getTime() {
		return mTime;
	}

	public void setTime(long time) {
		mTime = time;
	}
}
