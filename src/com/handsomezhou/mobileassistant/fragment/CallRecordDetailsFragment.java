package com.handsomezhou.mobileassistant.fragment;

import com.handsomezhou.mobileassistant.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CallRecordDetailsFragment extends BaseFragment {

	@Override
	protected void initData() {
		setContext(getActivity());

	}

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container) {
		View view=inflater.inflate(R.layout.fragment_call_record_details, container, false);
		return view;
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}

}
