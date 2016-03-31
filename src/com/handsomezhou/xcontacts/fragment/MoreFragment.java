package com.handsomezhou.xcontacts.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handsomezhou.xcontacts.R;

public class MoreFragment extends BaseFragment {

	@Override
	protected void initData() {
		setContext(getActivity());

	}

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container) {
		View view = inflater.inflate(R.layout.fragment_more, container, false);

		return view;
	}

	@Override
	protected void initListener() {

	}

}
