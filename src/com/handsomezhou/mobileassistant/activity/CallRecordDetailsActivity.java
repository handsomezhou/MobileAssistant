package com.handsomezhou.mobileassistant.activity;

import com.handsomezhou.mobileassistant.fragment.CallRecordDetailsFragment;

import android.support.v4.app.Fragment;

public class CallRecordDetailsActivity extends BaseSingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		
		return new CallRecordDetailsFragment();
	}

	@Override
	protected boolean isRealTimeLoadFragment() {
		
		return false;
	}

}
