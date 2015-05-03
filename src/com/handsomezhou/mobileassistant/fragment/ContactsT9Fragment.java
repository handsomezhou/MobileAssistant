package com.handsomezhou.mobileassistant.fragment;

import com.handsomezhou.mobileassistant.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ContactsT9Fragment extends BaseFragment {
	private ListView mContactsT9Lv;
	@Override
	protected void initData() {
		setContext(getActivity());

	}

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container) {
		View view=inflater.inflate(R.layout.fragment_contacts_t9, container, false);
		mContactsT9Lv=(ListView) view.findViewById(R.id.contacts_t9_list_view);
		return view;
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}

}
