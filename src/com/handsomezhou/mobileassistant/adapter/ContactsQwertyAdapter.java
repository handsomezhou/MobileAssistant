package com.handsomezhou.mobileassistant.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.handsomezhou.mobileassistant.R;
import com.handsomezhou.mobileassistant.model.Contacts;
import com.handsomezhou.mobileassistant.model.Contacts.SearchByType;
import com.handsomezhou.mobileassistant.util.ViewUtil;
import com.handsomezhou.mobileassistant.view.QuickAlphabeticBar;


public class ContactsQwertyAdapter extends ArrayAdapter<Contacts> implements SectionIndexer{
	//public static final String PINYIN_FIRST_LETTER_DEFAULT_VALUE="#";
	private Context mContext;
	private int mTextViewResourceId;
	private List<Contacts> mContacts;
	private OnContactsQwertyAdapter mOnContactsQwertyAdapter;
	
	public interface OnContactsQwertyAdapter{
		void onAddContactsSelected(Contacts contacts);
		void onRemoveContactsSelected(Contacts contacts);
		void onContactsCall(Contacts contacts);
		void onContactsSms(Contacts contacts);
		void onContactsCopy(Contacts contacts);
		void onContactsRefreshView();
	}
	
	public ContactsQwertyAdapter(Context context, int textViewResourceId,
			List<Contacts> contacts) {
		super(context, textViewResourceId, contacts);
		mContext=context;
		mTextViewResourceId=textViewResourceId;
		mContacts=contacts;
	
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view=null;
		ViewHolder viewHolder;
		Contacts contacts=getItem(position);
		if(null==convertView){
			view=LayoutInflater.from(mContext).inflate(mTextViewResourceId, null);
			viewHolder=new ViewHolder();
			viewHolder.mAlphabetTv=(TextView)view.findViewById(R.id.alphabet_text_view);
			viewHolder.mContactsMultiplePhoneOperationPromptIv=(ImageView)view.findViewById(R.id.contacts_multiple_phone_operation_prompt_image_view);
			viewHolder.mSelectContactsCB=(CheckBox) view.findViewById(R.id.select_contacts_check_box);
			viewHolder.mNameTv=(TextView) view.findViewById(R.id.name_text_view);
			viewHolder.mPhoneNumberTv=(TextView) view.findViewById(R.id.phone_number_text_view);
			viewHolder.mOperationViewIv=(ImageView) view.findViewById(R.id.operation_view_image_view);
			viewHolder.mDivisionLineView=view.findViewById(R.id.division_line_view);
			viewHolder.mOperationViewLayout=(View) view.findViewById(R.id.operation_view_layout);
			viewHolder.mCallIv=(ImageView) view.findViewById(R.id.call_image_view);
			viewHolder.mSmsIv=(ImageView) view.findViewById(R.id.sms_image_view);
			viewHolder.mCopyIv=(ImageView) view.findViewById(R.id.copy_image_view);
			view.setTag(viewHolder);
		}else{
			view=convertView;
			viewHolder=(ViewHolder) view.getTag();
		}
		
		//show the first alphabet of name
		showAlphabetIndex(viewHolder.mAlphabetTv, position, contacts);
		//show name and phone number
		switch (contacts.getSearchByType()) {
		case SearchByNull:
			ViewUtil.showTextNormal(viewHolder.mNameTv, contacts.getName());
			
			if(false==contacts.isBelongMultipleContactsPhone()){
				ViewUtil.hideView(viewHolder.mContactsMultiplePhoneOperationPromptIv);
				ViewUtil.showTextNormal(viewHolder.mPhoneNumberTv, contacts.getPhoneNumber());
			}else{
				if(true==contacts.isFirstMultipleContacts()){
					if(true==contacts.getNextContacts().isHideMultipleContacts()){
						ViewUtil.hideView(viewHolder.mContactsMultiplePhoneOperationPromptIv);
						ViewUtil.showTextNormal(viewHolder.mPhoneNumberTv, contacts.getPhoneNumber()+mContext.getString(R.string.phone_number_count, Contacts.getMultipleNumbersContactsCount(contacts)+1));
					}else{
						ViewUtil.showView(viewHolder.mContactsMultiplePhoneOperationPromptIv);
						ViewUtil.showTextNormal(viewHolder.mPhoneNumberTv, contacts.getPhoneNumber()+"("+mContext.getString(R.string.click_to_hide)+")");
					}
				}else{
					if(false==contacts.isHideMultipleContacts()){
						ViewUtil.invisibleView(viewHolder.mContactsMultiplePhoneOperationPromptIv);
					}else{
						ViewUtil.hideView(viewHolder.mContactsMultiplePhoneOperationPromptIv);
					}
					ViewUtil.showTextNormal(viewHolder.mPhoneNumberTv, contacts.getPhoneNumber());
				}
			}
			break;
		case SearchByPhoneNumber:
			ViewUtil.hideView(viewHolder.mContactsMultiplePhoneOperationPromptIv);
			ViewUtil.showTextNormal(viewHolder.mNameTv, contacts.getName());
			ViewUtil.showTextHighlight(viewHolder.mPhoneNumberTv, contacts.getPhoneNumber(), contacts.getMatchKeywords().toString());
			break;
		case SearchByName:
			ViewUtil.hideView(viewHolder.mContactsMultiplePhoneOperationPromptIv);
			ViewUtil.showTextHighlight(viewHolder.mNameTv, contacts.getName(), contacts.getMatchKeywords().toString());
			ViewUtil.showTextNormal(viewHolder.mPhoneNumberTv, contacts.getPhoneNumber());
			break;
		default:
			break;
		}
		
		
		viewHolder.mSelectContactsCB.setTag(position);
		viewHolder.mSelectContactsCB.setChecked(contacts.isSelected());
		viewHolder.mSelectContactsCB.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				int position = (Integer) buttonView.getTag();
				Contacts contacts = getItem(position);
				if((true==isChecked)&&(false==contacts.isSelected())){
					contacts.setSelected(isChecked);
					addSelectedContacts(contacts);
					
				}else if((false==isChecked)&&(true==contacts.isSelected())){
					contacts.setSelected(isChecked);
					removeSelectedContacts(contacts);
				}else{
					return;
				}
			}
		});
		
		viewHolder.mOperationViewIv.setTag(position);
		int resid=(true==contacts.isHideOperationView())?(R.drawable.arrow_down_selector):(R.drawable.arrow_up_selector);
		viewHolder.mOperationViewIv.setBackgroundResource(resid);
		if(true==contacts.isHideOperationView()){
			ViewUtil.hideView(viewHolder.mDivisionLineView);
			ViewUtil.hideView(viewHolder.mOperationViewLayout);
		}else{
			ViewUtil.showView(viewHolder.mDivisionLineView);
			ViewUtil.showView(viewHolder.mOperationViewLayout);
		}
		viewHolder.mOperationViewIv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int position = (Integer) v.getTag();
				Contacts contacts = getItem(position);
				contacts.setHideOperationView(!contacts.isHideOperationView());
				if(null!=mOnContactsQwertyAdapter){
					mOnContactsQwertyAdapter.onContactsRefreshView();
				}	
			}
		});

		viewHolder.mCallIv.setTag(position);
		viewHolder.mCallIv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int position = (Integer) v.getTag();
				Contacts contacts = getItem(position);
				if(null!=mOnContactsQwertyAdapter){
					mOnContactsQwertyAdapter.onContactsCall(contacts);
				}
				
			}
		});
		
		viewHolder.mSmsIv.setTag(position);
		viewHolder.mSmsIv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int position = (Integer) v.getTag();
				Contacts contacts = getItem(position);
				if(null!=mOnContactsQwertyAdapter){
					mOnContactsQwertyAdapter.onContactsSms(contacts);
				}
			}
		});
		
		viewHolder.mCopyIv.setTag(position);
		viewHolder.mCopyIv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int position = (Integer) v.getTag();
				Contacts contacts = getItem(position);
				if(null!=mOnContactsQwertyAdapter){
					mOnContactsQwertyAdapter.onContactsCopy(contacts);
				}
				
			}
		});
		
		return view;
	}
	
	

	@Override
	public Object[] getSections() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPositionForSection(int section) {
		Contacts contacts=null;
		if(QuickAlphabeticBar.DEFAULT_INDEX_CHARACTER==section){
			return 0;
		}else{
			int count=getCount();
			for(int i=0; i<count; i++){
				contacts=getItem(i);
				char firstChar=contacts.getSortKey().charAt(0);
				if(firstChar==section){
					return i;
				}
			}
		}
		
		return -1;
	}

	@Override
	public int getSectionForPosition(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public OnContactsQwertyAdapter getOnContactsAdapter() {
		return mOnContactsQwertyAdapter;
	}

	public void setOnContactsAdapter(OnContactsQwertyAdapter onContactsAdapter) {
		mOnContactsQwertyAdapter = onContactsAdapter;
	}
	
	public void clearSelectedContacts(){
		//clear data
		for(Contacts contacts:mContacts){
			contacts.setSelected(false);
			
			//other phoneNumber
			
			if(null!=contacts.getNextContacts()){
				Contacts currentContact=contacts.getNextContacts();
				Contacts nextContact=null;
				while(null!=currentContact){
					currentContact.setSelected(false);
					nextContact=currentContact;
					currentContact=nextContact.getNextContacts();
				}
			}
		}
		
		//refresh view
		notifyDataSetChanged();
	}

	private class ViewHolder{
		TextView mAlphabetTv;
		ImageView mContactsMultiplePhoneOperationPromptIv;
		CheckBox mSelectContactsCB;
		TextView mNameTv;
		TextView mPhoneNumberTv;
		ImageView mOperationViewIv;
		View mDivisionLineView;
		View mOperationViewLayout;
		ImageView mCallIv;
		ImageView mSmsIv;
		ImageView mCopyIv;
	}
	
	private void showAlphabetIndex(TextView textView, int position, final Contacts contacts){
		if((null==textView)||position<0||(null==contacts)){
			return;
		}
		String curAlphabet=getAlphabet(contacts.getSortKey());
		if(position>0){
			Contacts preContacts=getItem(position-1);
			String preAlphabet=getAlphabet(preContacts.getSortKey());
			if(curAlphabet.equals(preAlphabet)||(SearchByType.SearchByNull!=contacts.getSearchByType())){
				textView.setVisibility(View.GONE);
				textView.setText(curAlphabet);
			}else{
				textView.setVisibility(View.VISIBLE);
				textView.setText(curAlphabet);
			}
		}else {
			if((SearchByType.SearchByNull==contacts.getSearchByType())){
				textView.setVisibility(View.VISIBLE);
				textView.setText(curAlphabet);
			}else{
				textView.setVisibility(View.GONE);
			}
		}
		
		return ;
	}
	
	private String getAlphabet(String str){
		if((null==str)||(str.length()<=0)){
			return String.valueOf(QuickAlphabeticBar.DEFAULT_INDEX_CHARACTER);
		}
		String alphabet=null;
		char chr=str.charAt(0);
		if (chr >= 'A' && chr <= 'Z') {
			alphabet = String.valueOf(chr);
		} else if (chr >= 'a' && chr <= 'z') {
			alphabet = String.valueOf((char) ('A' + chr - 'a'));
		} else {
			alphabet = String.valueOf(QuickAlphabeticBar.DEFAULT_INDEX_CHARACTER);
		}
		return alphabet;
	}
	
	private boolean addSelectedContacts(Contacts contacts){
		
		
		do{
			if(null==contacts){
				break;
			}
			
			if(null!=mOnContactsQwertyAdapter){
				mOnContactsQwertyAdapter.onAddContactsSelected(contacts);
			}
			
			return true;
		}while(false);
		
		return false;
	
	}
	
	private void removeSelectedContacts(Contacts contacts){
		if(null==contacts){
			return;
		}
		
		if(null!=mOnContactsQwertyAdapter){
			mOnContactsQwertyAdapter.onRemoveContactsSelected(contacts);
		}
	}
}
