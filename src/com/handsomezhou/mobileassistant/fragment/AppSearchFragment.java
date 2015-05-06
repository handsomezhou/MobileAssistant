package com.handsomezhou.mobileassistant.fragment;

import com.handsomezhou.mobileassistant.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AppSearchFragment extends BaseFragment {

	@Override
	protected void initData() {
		setContext(getActivity());

	}

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container) {
		View view=inflater.inflate(R.layout.fragment_app_search, container, false);
		return view;
	}

	@Override
	protected void initListener() {
	
	}

}
