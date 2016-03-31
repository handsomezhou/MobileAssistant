package com.handsomezhou.xcontacts.activity;

import android.support.v4.app.Fragment;

import com.handsomezhou.xcontacts.fragment.AddressBookFragment;

public class MainActivity extends BaseSingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		
		return new AddressBookFragment();
	}

	@Override
	protected boolean isRealTimeLoadFragment() {
		return false;
	}

	@Override
	public void onBackPressed() {
		moveTaskToBack(true);
	}

}
