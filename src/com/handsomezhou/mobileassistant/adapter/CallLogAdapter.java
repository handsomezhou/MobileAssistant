package com.handsomezhou.mobileassistant.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.handsomezhou.mobileassistant.R;
import com.handsomezhou.mobileassistant.model.CallRecord;

public class CallLogAdapter extends ArrayAdapter<CallRecord> {
	private Context mContext;
	private int mTextViewResourceId;
	private List<CallRecord> mCallRecords;
	
	public CallLogAdapter(Context context, int textViewResourceId, List<CallRecord> callRecords) {
		super(context, textViewResourceId, callRecords);
		mContext=context;
		mTextViewResourceId=textViewResourceId;
		mCallRecords=callRecords;
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view=null;
		ViewHolder viewHolder;
		CallRecord callRecord=getItem(position);
		if(null==convertView){
			view=LayoutInflater.from(mContext).inflate(mTextViewResourceId, null);
			viewHolder=new ViewHolder();
			viewHolder.mNameTv=(TextView) view.findViewById(R.id.name_text_view);
			view.setTag(viewHolder);
		}else{
			view=convertView;
			viewHolder=(ViewHolder) view.getTag();
		}
		
		viewHolder.mNameTv.setText(callRecord.getContacts().getName());
		
		return view;
	}
	
	private class ViewHolder{
		TextView mNameTv;
	}
}
