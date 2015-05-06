package com.handsomezhou.mobileassistant.fragment;

import com.handsomezhou.mobileassistant.R;
import com.handsomezhou.mobileassistant.adapter.ContactsT9Adapter;
import com.handsomezhou.mobileassistant.helper.ContactsHelper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class ContactsT9Fragment extends BaseFragment {
	private static final String TAG="ContactsT9Fragment";
	private ListView mContactsT9Lv;
	private ContactsT9Adapter mContactsT9Adapter;
	@Override
	protected void initData() {
		setContext(getActivity());
		Log.i(TAG, "ContactsHelper.getInstance().getBaseContacts().size()="+ContactsHelper.getInstance().getBaseContacts().size());
//		ContactsHelper.getInstance().parseT9InputSearchContacts(null);
		mContactsT9Adapter=new ContactsT9Adapter(getContext(), R.layout.contacts_t9_list_item,ContactsHelper.getInstance().getBaseContacts());
	}

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container) {
		View view=inflater.inflate(R.layout.fragment_contacts_t9, container, false);
		mContactsT9Lv=(ListView) view.findViewById(R.id.contacts_t9_list_view);
		mContactsT9Lv.setAdapter(mContactsT9Adapter);
		return view;
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}
	
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
				
			}else{
				
			}
		}
	}

}
