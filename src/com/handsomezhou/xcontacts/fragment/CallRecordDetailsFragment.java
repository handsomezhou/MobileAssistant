package com.handsomezhou.xcontacts.fragment;

import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handsomezhou.xcontacts.R;
import com.handsomezhou.xcontacts.helper.CallRecordHelper;
import com.handsomezhou.xcontacts.model.CallRecord;
import com.handsomezhou.xcontacts.model.CallRecordDetailsParameter;

public class CallRecordDetailsFragment extends BaseFragment {
	public static final String TAG=CallRecordDetailsFragment.class.getSimpleName();
    public static final String EXTRA_CALL_RECORD_DETAILS_PARAMETER = "CallRecordDetailsFragment.EXTRA_CALL_RECORD_DETAILS_PARAMETER";

	private CallRecordDetailsParameter mCallRecordDetailsParameter;
	private CallRecord mCurrentCallRecord;
	private List<CallRecord> mCallRecords;

	public static CallRecordDetailsFragment newInstance(CallRecordDetailsParameter callRecordDetailsParameter){
		 Bundle bundle = new Bundle();
		 if(null!=callRecordDetailsParameter){
			 bundle.putSerializable(EXTRA_CALL_RECORD_DETAILS_PARAMETER, callRecordDetailsParameter);
		 }else{
			 bundle.putSerializable(EXTRA_CALL_RECORD_DETAILS_PARAMETER, new CallRecordDetailsParameter());
		 }
		 
		 CallRecordDetailsFragment fragment=new CallRecordDetailsFragment();
		 fragment.setArguments(bundle);
		 
		 return fragment;
	}
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		if(getArguments().containsKey(EXTRA_CALL_RECORD_DETAILS_PARAMETER)){
			mCallRecordDetailsParameter=(CallRecordDetailsParameter)getArguments().getSerializable(EXTRA_CALL_RECORD_DETAILS_PARAMETER);
		}else{
			mCallRecordDetailsParameter=new CallRecordDetailsParameter();
		}
		Log.i(TAG,"mCurrentIndex"+ mCallRecordDetailsParameter.getCurrentIndex());
		Log.i(TAG,"onCreate() CallRecordHelper.getInstance().getBaseCallRecord().size()="+ CallRecordHelper.getInstance().getBaseCallRecord().size());
		
		//setCurrentCallRecord(CallRecordHelper.getInstance().getBaseCallRecord().get(mCallRecordDetailsParameter.getCurrentIndex()));
	/*	setCallRecords(new ArrayList<CallRecord>());
		getCallRecords().addAll(CallRecord.praseCallRecords(getCurrentCallRecord()));*/
	//	Log.i(TAG, getCurrentCallRecord().getContacts().getName()+getCurrentCallRecord().getDate());
		
	/*	for(CallRecord cr:getCallRecords()){
			Log.i(TAG, cr.getContacts().getName()+cr.getDate());
		}*/
		super.onCreate(savedInstanceState);
	}


	

	@Override
	public void onResume() {
		Log.i(TAG,"onResume() CallRecordHelper.getInstance().getBaseCallRecord().size()="+ CallRecordHelper.getInstance().getBaseCallRecord().size());
		super.onResume();
	}



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

	private CallRecord getCurrentCallRecord() {
		return mCurrentCallRecord;
	}

	private void setCurrentCallRecord(CallRecord currentCallRecord) {
		mCurrentCallRecord = currentCallRecord;
	}
	
	private List<CallRecord> getCallRecords() {
		return mCallRecords;
	}

	private void setCallRecords(List<CallRecord> callRecords) {
		mCallRecords = callRecords;
	}
	
	

}
