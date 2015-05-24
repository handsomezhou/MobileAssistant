package com.handsomezhou.mobileassistant.adapter;

import java.util.List;

import android.content.Context;
import android.provider.CallLog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.handsomezhou.mobileassistant.R;
import com.handsomezhou.mobileassistant.model.CallRecord;
import com.handsomezhou.mobileassistant.util.StringUtil;

public class CallRecordAdapter extends ArrayAdapter<CallRecord> {
	private Context mContext;
	private int mTextViewResourceId;
	private List<CallRecord> mCallRecords;
	private OnCallRecordAdapter mOnCallRecordAdapter;

	public CallRecordAdapter(Context context, int textViewResourceId, List<CallRecord> callRecords) {
		super(context, textViewResourceId, callRecords);
		mContext=context;
		mTextViewResourceId=textViewResourceId;
		mCallRecords=callRecords;
	}

	public interface OnCallRecordAdapter{
		void onCallLogDetails(CallRecord callRecord);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view=null;
		ViewHolder viewHolder;
		CallRecord callRecord=getItem(position);
		if(null==convertView){
			view=LayoutInflater.from(mContext).inflate(mTextViewResourceId, null);
			viewHolder=new ViewHolder();
			viewHolder.mCallTypeIv=(ImageView) view.findViewById(R.id.call_type_image_view);
			viewHolder.mNameTv=(TextView) view.findViewById(R.id.name_text_view);
			viewHolder.mDateTv=(TextView) view.findViewById(R.id.date_text_view);
			viewHolder.mCallRecordDetailsIv=(ImageView) view.findViewById(R.id.call_record_details_image_view);
			view.setTag(viewHolder);
		}else{
			view=convertView;
			viewHolder=(ViewHolder) view.getTag();
		}
		
		viewHolder.mCallTypeIv.setBackgroundResource(getCallTypeResId(callRecord.getType()));
		
		if(!TextUtils.isEmpty(callRecord.getContacts().getName())){
			viewHolder.mNameTv.setText(callRecord.getContacts().getName()+":"+callRecord.getContacts().getPhoneNumber());
		}else{
			if(!TextUtils.isEmpty(callRecord.getContacts().getPhoneNumber())){
				viewHolder.mNameTv.setText(callRecord.getContacts().getPhoneNumber());
			}else{
				viewHolder.mNameTv.setText(R.string.unknow_phone_number);
			}	
		}
		
		viewHolder.mDateTv.setText(String.valueOf(StringUtil.getCallDate(mContext,callRecord.getDateTime())));
		
		viewHolder.mCallRecordDetailsIv.setTag(position);
		viewHolder.mCallRecordDetailsIv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int position=(Integer) v.getTag();
				CallRecord callRecord=getItem(position);
				if(null!=mOnCallRecordAdapter){
					mOnCallRecordAdapter.onCallLogDetails(callRecord);
				}
				
			}
		});
		
		return view;
	}
	
	public OnCallRecordAdapter getOnCallLogAdapter() {
		return mOnCallRecordAdapter;
	}

	public void setOnCallLogAdapter(OnCallRecordAdapter onCallLogAdapter) {
		mOnCallRecordAdapter = onCallLogAdapter;
	}
	
	private class ViewHolder{
		ImageView mCallTypeIv;
		TextView mNameTv;
		TextView mDateTv;
		ImageView mCallRecordDetailsIv;
	}
	
	private int getCallTypeResId(int type){
		int resId=R.drawable.call_type_voicemail;
		switch (type) {
		case CallLog.Calls.INCOMING_TYPE:
			resId=R.drawable.call_type_incoming;
			break;
		case CallLog.Calls.OUTGOING_TYPE:
			resId=R.drawable.call_type_outgoing;
			break;
		case CallLog.Calls.MISSED_TYPE:
			resId=R.drawable.call_type_missed;
			break;
		//case CallLog.Calls.VOICEMAIL_TYPE:
		default:
			resId=R.drawable.call_type_voicemail;
			break;
		}
		return resId;
	}
}
