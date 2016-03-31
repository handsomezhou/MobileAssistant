package com.handsomezhou.xcontacts.fragment;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.handsomezhou.xcontacts.R;
import com.handsomezhou.xcontacts.helper.ContactsHelper;
import com.handsomezhou.xcontacts.helper.ContactsIndexHelper;
import com.handsomezhou.xcontacts.model.Contacts;
import com.handsomezhou.xcontacts.util.ShareUtil;
import com.handsomezhou.xcontacts.view.ContactsOperationView;
import com.handsomezhou.xcontacts.view.ContactsOperationView.OnContactsOperationView;
import com.handsomezhou.xcontacts.view.SearchBox;
import com.handsomezhou.xcontacts.view.SearchBox.OnSearchBox;

public class ContactsQwertyFragment extends BaseFragment implements OnContactsOperationView,OnSearchBox{
	private static final String TAG=ContactsQwertyFragment.class.getSimpleName();
	private SearchBox mSearchBox;
	private ContactsOperationView mContactsOperationView;


	@Override
	protected void initData() {
		setContext(getActivity());
		//ContactsHelper.getInstance().setOnContactsLoad(this);
	}

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container) {
		View view =inflater.inflate(R.layout.fragment_contacts_qwerty, container, false);
		mSearchBox=(SearchBox) view.findViewById(R.id.search_box);
		mSearchBox.setOnSearchBox(this);
		mContactsOperationView = (ContactsOperationView)view.findViewById(R.id.contacts_operation_layout);
		mContactsOperationView.setOnContactsOperationView(this);
		/*boolean startLoad = ContactsHelper.getInstance().startLoadContacts();
		if (true == startLoad) {
			mContactsOperationView.contactsLoading();
		}*/
		return view;
	}

	@Override
	protected void initListener() {
		
	}

	/*start: OnSearchBox*/
	@Override
	public void onSearchTextChanged(String curCharacter) {
		updateSearch(curCharacter);
	}
	/*end: OnSearchBox*/
	
	/*start:OnContactsOperationView*/
	@Override
	public void onListItemClick(Contacts contacts,int position){
		ContactsHelper.getInstance().getQwertySearchContacts(null);
		mContactsOperationView.updateContactsList(true);
	}

	@Override
	public void onAddContactsSelected(Contacts contacts) {
		if(null!=contacts){
			Log.i(TAG, "onAddContactsSelected name=["+contacts.getName()+"] phoneNumber=["+contacts.getPhoneNumber()+"]");
			Toast.makeText(getContext(),"Add ["+contacts.getName()+":"+contacts.getPhoneNumber()+"]", Toast.LENGTH_SHORT).show();
			ContactsHelper.getInstance().addSelectedContacts(contacts);
		}
	}


	@Override
	public void onRemoveContactsSelected(Contacts contacts) {
		if(null!=contacts){
			Log.i(TAG, "onRemoveContactsSelected name=["+contacts.getName()+"] phoneNumber=["+contacts.getPhoneNumber()+"]");
			Toast.makeText(getContext(),"Remove ["+contacts.getName()+":"+contacts.getPhoneNumber()+"]", Toast.LENGTH_SHORT).show();
			ContactsHelper.getInstance().removeSelectedContacts(contacts);
		}
	}
	
	@Override
	public void onContactsCall(Contacts contacts) {
		//Toast.makeText(mContext, "onContactsCall"+contacts.getPhoneNumber(), Toast.LENGTH_SHORT).show();
		if(null!=contacts){
			 Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+contacts.getPhoneNumber()));
			 getContext().startActivity(intent);
		}
	}


	@Override
	public void onContactsSms(Contacts contacts) {
		//Toast.makeText(mContext, "onContactsSms"+contacts.getPhoneNumber(), Toast.LENGTH_SHORT).show();
		if(null!=contacts){
			ShareUtil.shareTextBySms(getContext(), contacts.getPhoneNumber(), null);
		}
	}
	
	@Override
	public void onContactsCopy(Contacts contacts) {
		if(null!=contacts){
			ShareUtil.copyText(getContext(), contacts.getName()+"\n"+contacts.getPhoneNumber());
			Toast.makeText(getContext(), getContext().getString(R.string.copy_success), Toast.LENGTH_SHORT).show();
		}
		
	}
	/*end:OnContactsOperationView*/
	
	public void contactsLoadSuccess(){
		ContactsHelper.getInstance().getQwertySearchContacts(null);
		mContactsOperationView.contactsLoadSuccess();
		ContactsIndexHelper.getInstance().praseContacts(ContactsHelper.getInstance().getBaseContacts());
	}
	
	public void contactsLoadFailed(){
		mContactsOperationView.contactsLoadFailed();
	}
	
	public void updateSearch(){
		if(null!=mSearchBox){
			updateSearch(mSearchBox.getSearchEtInput());
		}
	}
	
	private void updateSearch(String search){
	    String curCharacter;
	    if(null==search){
	        curCharacter=search;
	    }else{
	        curCharacter=search.trim();
	    }
	   
        if(TextUtils.isEmpty(curCharacter)){
            ContactsHelper.getInstance().getQwertySearchContacts(null);
        }else{
            ContactsHelper.getInstance().getQwertySearchContacts(curCharacter);
        }
        mContactsOperationView.updateContactsList(TextUtils.isEmpty(curCharacter));
	}

	

}
