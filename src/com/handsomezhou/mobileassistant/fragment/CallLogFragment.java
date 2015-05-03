package com.handsomezhou.mobileassistant.fragment;

import com.handsomezhou.mobileassistant.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class CallLogFragment extends BaseFragment {
	private ListView mCallLogLv;
	@Override
	protected void initData() {
		setContext(getActivity());

	}

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container) {
		View view=inflater.inflate(R.layout.fragment_call_log, container, false);
		mCallLogLv=(ListView) view.findViewById(R.id.call_log_list_view);
		return view;
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}

}
