package com.handsomezhou.xcontacts.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.handsomezhou.xcontacts.R;
import com.handsomezhou.xcontacts.util.ViewUtil;

public class BottomTabCallView extends LinearLayout {
	private Context mContext;
	
	/*Start:BottomTabCallView*/
	private View mBottomTabCallView;
	private View mCallView;
	private View mDialView;
	private View mDeleteView;
	/*End:BottomTabCallView*/
	private OnBottomTabCallView mOnBottomTabCallView;

	public interface OnBottomTabCallView{
		void onCallClick();
		void onDialClick();
		void onDeleteClick();
		void onDeleteLongClick();
	}
	
	public BottomTabCallView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext=context;
		initData();
		initView();
		initListener();
	}
	
	public OnBottomTabCallView getOnBottomTabCallView() {
		return mOnBottomTabCallView;
	}

	public void setOnBottomTabCallView(OnBottomTabCallView onBottomTabCallView) {
		mOnBottomTabCallView = onBottomTabCallView;
	}
	
	public void show(){
		ViewUtil.showView(this);
	}
	
	public void hide(){
		ViewUtil.hideView(this);
	}
	
	private void initData(){
		
		return;
	}
	
	private void initView(){
		LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mBottomTabCallView=inflater.inflate(R.layout.bottom_tab_call_layout, this);
		mCallView=mBottomTabCallView.findViewById(R.id.call_layout);
		mDialView=mBottomTabCallView.findViewById(R.id.dial_layout);
		mDeleteView=mBottomTabCallView.findViewById(R.id.delete_layout);
		return;
	}
	
	private void initListener(){
		mCallView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				callClick();
			}
		});
		
	
		mDialView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialClick();
			}
		});
		
		mDeleteView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				deleteClick();
			}
		});
		
		mDeleteView.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View arg0) {
				deleteLongClick();
				return false;
			}
		});
		return;
	}

	private void callClick(){
		
		if(null!=mOnBottomTabCallView){
			mOnBottomTabCallView.onCallClick();
		}
		
		return;
	}
	
	private void dialClick(){
		
		if(null!=mOnBottomTabCallView){
			mOnBottomTabCallView.onDialClick();
		}
		
		return;
	}
	
	private void deleteClick(){
		
		if(null!=mOnBottomTabCallView){
			mOnBottomTabCallView.onDeleteClick();
		}
		
		return;
	}
	
	private void deleteLongClick(){
		
		if(null!=mOnBottomTabCallView){
			mOnBottomTabCallView.onDeleteLongClick();
		}
		
		return;
	}
}

