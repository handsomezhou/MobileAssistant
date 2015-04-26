package com.handsomezhou.mobileassistant.activity;

import android.support.v4.app.Fragment;

import com.handsomezhou.mobileassistant.fragment.AddressBookFragment;

public class MainActivity extends BaseSingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		
		return new AddressBookFragment();
	}

	@Override
	protected boolean isRealTimeLoadFragment() {
		return false;
	}
	
}
