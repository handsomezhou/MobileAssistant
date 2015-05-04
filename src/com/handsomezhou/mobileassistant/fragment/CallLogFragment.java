package com.handsomezhou.mobileassistant.fragment;

import com.handsomezhou.mobileassistant.R;
import com.handsomezhou.mobileassistant.adapter.CallLogAdapter;
import com.handsomezhou.mobileassistant.helper.CallRecordHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class CallLogFragment extends BaseFragment {
	private ListView mCallLogLv;
	private CallLogAdapter mCallLogAdapter;
	@Override
	protected void initData() {
		setContext(getActivity());
		mCallLogAdapter=new CallLogAdapter(getContext(), R.layout.call_log_list_item, CallRecordHelper.getInstance().getBaseCallRecord());

	}

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container) {
		View view=inflater.inflate(R.layout.fragment_call_log, container, false);
		mCallLogLv=(ListView) view.findViewById(R.id.call_log_list_view);
		mCallLogLv.setAdapter(mCallLogAdapter);
		return view;
	}

	@Override
	protected void initListener() {

	}

}
