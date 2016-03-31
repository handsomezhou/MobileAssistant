package com.handsomezhou.xcontacts.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.handsomezhou.xcontacts.R;
import com.handsomezhou.xcontacts.activity.CallRecordDetailsActivity;
import com.handsomezhou.xcontacts.adapter.CallRecordAdapter;
import com.handsomezhou.xcontacts.adapter.CallRecordAdapter.OnCallRecordAdapter;
import com.handsomezhou.xcontacts.helper.CallRecordHelper;
import com.handsomezhou.xcontacts.model.CallRecord;
import com.handsomezhou.xcontacts.model.CallRecordDetailsParameter;

public class CallRecordFragment extends BaseFragment implements OnCallRecordAdapter{
	private static final String TAG=CallRecordFragment.class.getSimpleName();
	private ListView mCallRecordLv;
	private CallRecordAdapter mCallRecordAdapter;
	@Override
	protected void initData() {
		setContext(getActivity());
		Log.i(TAG, "CallRecordHelper.getInstance().getBaseCallRecord()="+CallRecordHelper.getInstance().getBaseCallRecord().size());
		mCallRecordAdapter=new CallRecordAdapter(getContext(), R.layout.call_record_list_item, CallRecordHelper.getInstance().getBaseCallRecord());
		mCallRecordAdapter.setOnCallLogAdapter(this);
	}

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container) {
		View view=inflater.inflate(R.layout.fragment_call_record, container, false);
		mCallRecordLv=(ListView) view.findViewById(R.id.call_log_list_view);
		mCallRecordLv.setAdapter(mCallRecordAdapter);
		return view;
	}

	@Override
	protected void initListener() {
		mCallRecordLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				CallRecord callRecord=(CallRecord) parent.getItemAtPosition(position);
				//Toast.makeText(getContext(), callRecord.getContacts().getName(), Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+callRecord.getContacts().getPhoneNumber()));
				getContext().startActivity(intent);
				
			}
		});
		
		mCallRecordLv.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				Toast.makeText(getContext(), "mCallLogLv", Toast.LENGTH_SHORT).show();
				return true;
			}
		});
	}
	
	/*Start: OnCallLogAdapter*/
	@Override
	public void onCallLogDetails(CallRecord callRecord,int position) {
		if(null==callRecord){
			return;
		}
		
		Intent intent=new Intent(getActivity(), CallRecordDetailsActivity.class);
		Bundle bundle=new Bundle();
		CallRecordDetailsParameter callRecordDetailsParameter=new CallRecordDetailsParameter();
		callRecordDetailsParameter.setCurrentIndex(position);
		bundle.putSerializable(CallRecordDetailsFragment.EXTRA_CALL_RECORD_DETAILS_PARAMETER,callRecordDetailsParameter);
		intent.putExtras(bundle);
		startActivity(intent);
		//Toast.makeText(getContext(), callRecord.getContacts().getName(), Toast.LENGTH_SHORT).show();
		
	}
	/*End: OnCallLogAdapter*/
	public void updateView(){
		updateCallLogLv();
	}
	
	private void updateCallLogLv(){
		if(null==mCallRecordLv){
			return;
		}
		
		BaseAdapter baseAdapter=(BaseAdapter)mCallRecordLv.getAdapter();
		if(null!=baseAdapter){
			baseAdapter.notifyDataSetChanged();
			if(baseAdapter.getCount()>0){
				
			}else{
				
			}
		}
	}
}
