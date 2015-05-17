package com.handsomezhou.mobileassistant.activity;

import com.handsomezhou.mobileassistant.fragment.CallLogDetailsFragment;

import android.support.v4.app.Fragment;

public class CallLogDetailsActivity extends BaseSingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		
		return new CallLogDetailsFragment();
	}

	@Override
	protected boolean isRealTimeLoadFragment() {
		
		return false;
	}

}
