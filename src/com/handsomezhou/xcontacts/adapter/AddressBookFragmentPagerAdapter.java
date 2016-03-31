package com.handsomezhou.xcontacts.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.handsomezhou.xcontacts.model.AddressBookView;

public class AddressBookFragmentPagerAdapter extends FragmentPagerAdapter {
	List<AddressBookView> mAddressBookViews;
	public AddressBookFragmentPagerAdapter(FragmentManager fm,List<AddressBookView> addressBookViews) {
		super(fm);
		mAddressBookViews=addressBookViews;
	}

	@Override
	public Fragment getItem(int pos) {
		AddressBookView addressBookView=mAddressBookViews.get(pos);
		return addressBookView.getFragment();
	}

	@Override
	public int getCount() {
		
		return mAddressBookViews.size();
	}

}
