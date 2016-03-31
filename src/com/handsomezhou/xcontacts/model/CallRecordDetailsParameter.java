package com.handsomezhou.xcontacts.model;

import java.io.Serializable;

/**
 * 
 * parameters passed that jump to  the interface of the call log details.
 * @author handsomezhou
 *
 */
public class CallRecordDetailsParameter implements Serializable{
	public static final int CURRENT_INDEX_DEFAULT=0;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int mCurrentIndex;//the current index of callRecords.

	
	public CallRecordDetailsParameter() {
		super();
		setCurrentIndex(CURRENT_INDEX_DEFAULT);
	}

	public int getCurrentIndex() {
		return mCurrentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		mCurrentIndex = currentIndex;
	}
}
