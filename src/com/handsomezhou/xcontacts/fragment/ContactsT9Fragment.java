package com.handsomezhou.xcontacts.fragment;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handsomezhou.xcontacts.R;
import com.handsomezhou.xcontacts.adapter.ContactsT9Adapter;
import com.handsomezhou.xcontacts.adapter.ContactsT9Adapter.OnContactsT9Adapter;
import com.handsomezhou.xcontacts.helper.ContactsHelper;
import com.handsomezhou.xcontacts.model.Contacts;
import com.handsomezhou.xcontacts.util.ShareUtil;
import com.handsomezhou.xcontacts.util.ViewUtil;

public class ContactsT9Fragment extends BaseFragment implements OnContactsT9Adapter{
	private static final String TAG=ContactsT9Fragment.class.getSimpleName();
	private ListView mContactsT9Lv;
	private TextView mSearchResultPromptTv;
	private ContactsT9Adapter mContactsT9Adapter;
	@Override
	protected void initData() {
		setContext(getActivity());
		Log.i(TAG, "ContactsHelper.getInstance().getBaseContacts().size()="+ContactsHelper.getInstance().getT9SearchContacts().size());
		mContactsT9Adapter=new ContactsT9Adapter(getContext(), R.layout.contacts_t9_list_item,ContactsHelper.getInstance().getT9SearchContacts());
		mContactsT9Adapter.setOnContactsT9Adapter(this);
		mContactsT9Adapter.setSelectContactsCbVisible(false);
	}

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container) {
		View view=inflater.inflate(R.layout.fragment_contacts_t9, container, false);
		mContactsT9Lv=(ListView) view.findViewById(R.id.contacts_t9_list_view);
		mSearchResultPromptTv=(TextView)view.findViewById(R.id.search_result_prompt_text_view);
		
		mContactsT9Lv.setAdapter(mContactsT9Adapter);
		return view;
	}

	@Override
	protected void initListener() {
		mContactsT9Lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Contacts contacts=(Contacts) parent.getItemAtPosition(position);
				if(false==contacts.isFirstMultipleContacts()){
					return;
				}
				
				Contacts.hideOrUnfoldMultipleContactsView(contacts);
				
				
			}
		});

	}
	
	/*Start: OnContactsT9Adapter*/
	@Override
	public void onAddContactsSelected(Contacts contacts) {
		if(null==contacts){
			return;
		}
		
		Log.i(TAG, "onAddContactsSelected name=["+contacts.getName()+"] phoneNumber=["+contacts.getPhoneNumber()+"]");
		Toast.makeText(getContext(),"Add ["+contacts.getName()+":"+contacts.getPhoneNumber()+"]", Toast.LENGTH_SHORT).show();
		ContactsHelper.getInstance().addSelectedContacts(contacts);
		
	}

	@Override
	public void onRemoveContactsSelected(Contacts contacts) {
		if(null==contacts){
			return;
		}

		Log.i(TAG, "onRemoveContactsSelected name=["+contacts.getName()+"] phoneNumber=["+contacts.getPhoneNumber()+"]");
		Toast.makeText(getContext(),"Remove ["+contacts.getName()+":"+contacts.getPhoneNumber()+"]", Toast.LENGTH_SHORT).show();
		ContactsHelper.getInstance().removeSelectedContacts(contacts);
	}

	@Override
	public void onContactsCall(Contacts contacts) {
		if(null==contacts){
			return;
		}

		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+contacts.getPhoneNumber()));
		getContext().startActivity(intent);
	}

	@Override
	public void onContactsSms(Contacts contacts) {
		if(null==contacts){
			return;
		}

		//Toast.makeText(getContext(), "onContactsSms", Toast.LENGTH_SHORT).show();
		ShareUtil.shareTextBySms(getContext(), contacts.getPhoneNumber(), null);
	}

	@Override
	public void onContactsCopy(Contacts contacts) {
		if(null==contacts){
			return;
		}

		ShareUtil.copyText(getContext(), contacts.getName()+"\n"+contacts.getPhoneNumber());
		Toast.makeText(getContext(), getContext().getString(R.string.copy_success), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onContactsRefreshView() {
		//Toast.makeText(getContext(), "onContactsRefreshView", Toast.LENGTH_SHORT).show();
		updateView();
	}
	/*Start: OnContactsT9Adapter*/
	
	public void updateView(){
		updateContactsT9Lv();
	}
	
	private void updateContactsT9Lv(){
		if(null==mContactsT9Lv){
			return;
		}
		
		BaseAdapter baseAdapter=(BaseAdapter)mContactsT9Lv.getAdapter();
		if(null!=baseAdapter){
			baseAdapter.notifyDataSetChanged();
			if(baseAdapter.getCount()>0){
				ViewUtil.showView(mContactsT9Lv);
				ViewUtil.hideView(mSearchResultPromptTv);
			}else{
				ViewUtil.hideView(mContactsT9Lv);
				ViewUtil.showView(mSearchResultPromptTv);
			}
		}
	}


}
