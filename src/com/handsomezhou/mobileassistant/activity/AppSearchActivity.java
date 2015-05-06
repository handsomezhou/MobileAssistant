package com.handsomezhou.mobileassistant.activity;

import com.handsomezhou.mobileassistant.fragment.AppSearchFragment;

import android.support.v4.app.Fragment;

public class AppSearchActivity extends BaseSingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		
		return new AppSearchFragment();
	}

	@Override
	protected boolean isRealTimeLoadFragment() {
		
		return false;
	}

}
