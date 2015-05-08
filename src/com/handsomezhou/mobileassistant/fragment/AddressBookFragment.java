package com.handsomezhou.mobileassistant.fragment;


import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.handsomezhou.mobileassistant.R;
import com.handsomezhou.mobileassistant.Interface.OnTabChange;
import com.handsomezhou.mobileassistant.adapter.AddressBookFragmentPagerAdapter;
import com.handsomezhou.mobileassistant.fragment.TelephoneFragment.OnTelephoneDialChange;
import com.handsomezhou.mobileassistant.helper.CallRecordHelper;
import com.handsomezhou.mobileassistant.helper.CallRecordHelper.OnCallLogLoad;
import com.handsomezhou.mobileassistant.helper.ContactsHelper;
import com.handsomezhou.mobileassistant.helper.ContactsHelper.OnContactsLoad;
import com.handsomezhou.mobileassistant.model.AddressBookView;
import com.handsomezhou.mobileassistant.model.IconButtonData;
import com.handsomezhou.mobileassistant.model.IconButtonValue;
import com.handsomezhou.mobileassistant.service.MobileAssistantService;
import com.handsomezhou.mobileassistant.util.ViewUtil;
import com.handsomezhou.mobileassistant.view.BottomTabView;
import com.handsomezhou.mobileassistant.view.CustomViewPager;

public class AddressBookFragment extends BaseFragment implements OnTabChange,OnCallLogLoad,OnContactsLoad,OnTelephoneDialChange{
	private static final String TAG="AddressBookFragment";
	private List<AddressBookView> mAddressBookViews;
	private BottomTabView mBottomTabView;//without call
	private BottomTabView mBottomTabCallView;//with call 
	private CustomViewPager mCustomViewPager;
	private AddressBookFragmentPagerAdapter mAddressBookFragmentPagerAdapter;
	   
    public enum BOTTOM_TAB_TAG{
        CALL,
        CONTACTS,
        SMS,
        MORE,
    }
    
    public enum BOTTOM_TAB_CALL_TAG{
        CALL,
        DIAL,
        DELETE,
    }
    
	@Override
	protected void initData() {
		setContext(getActivity());
		mAddressBookViews=new ArrayList<AddressBookView>();
		
		/*Start: call view*/
		TelephoneFragment telephoneFragment=new TelephoneFragment();
		telephoneFragment.setOnTelephoneDialChange(this);
		AddressBookView callAddressBookView=new AddressBookView(BOTTOM_TAB_TAG.CALL, telephoneFragment);
		mAddressBookViews.add(callAddressBookView);
		/*End: call view*/
		
		/*Start: contacts view*/
		AddressBookView contactsAddressBookView=new AddressBookView(BOTTOM_TAB_TAG.CONTACTS, new ContactsQwertyFragment());
		mAddressBookViews.add(contactsAddressBookView);
		/*End: contacts view*/
		
		/*Start: contacts view*/
		AddressBookView smsAddressBookView=new AddressBookView(BOTTOM_TAB_TAG.SMS, new SmsFragment());
		mAddressBookViews.add(smsAddressBookView);
		/*End: contacts view*/
		
		/*Start: contacts view*/
		AddressBookView moreAddressBookView=new AddressBookView(BOTTOM_TAB_TAG.MORE, new MoreFragment());
		mAddressBookViews.add(moreAddressBookView);
		/*End: contacts view*/
		
		CallRecordHelper.getInstance().setOnCallLogLoad(this);
		CallRecordHelper.getInstance().startLoadCallRecord();
		
		ContactsHelper.getInstance().setOnContactsLoad(this);
		ContactsHelper.getInstance().startLoadContacts();
		
		Intent  intent=new Intent(getContext(), MobileAssistantService.class);
		intent.setAction(MobileAssistantService.ACTION_MOBILE_ASSISTANT_SERVICE);
		getContext().startService(intent);
		
	
		
	}

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container) {
		View view=inflater.inflate(R.layout.fragment_address_book, mBottomTabView, false);
		mCustomViewPager=(CustomViewPager)view.findViewById(R.id.custom_view_pager_address_book);
		mCustomViewPager.setPagingEnabled(true);
		
		/*Start : BOTTOM_TAB_TAG*/
	    mBottomTabView=(BottomTabView) view.findViewById(R.id.bottom_tab_view);
	    mBottomTabView.removeAllViews();
	    /*Start: call tab*/
	    IconButtonValue callIconBtnValue=new IconButtonValue(BOTTOM_TAB_TAG.CALL,R.drawable.call_icon_selected_unfocused,R.drawable.call_icon_selected_focused, R.drawable.call_icon_unselected, R.string.call);
	    IconButtonData callIconBtnData=new IconButtonData(getContext(), callIconBtnValue);
	    mBottomTabView.addIconButtonData(callIconBtnData);
	    /*End: call tab*/
	    
	    /*Start: contacts tab*/
        IconButtonValue contactsIconBtnValue=new IconButtonValue(BOTTOM_TAB_TAG.CONTACTS,R.drawable.contacts_icon_selected_unfocused, R.drawable.contacts_icon_unselected, R.string.contacts);
        IconButtonData contactsIconBtnData=new IconButtonData(getContext(), contactsIconBtnValue);
        mBottomTabView.addIconButtonData(contactsIconBtnData);
        /*End: contacts tab*/
        
        /*Start: sms tab*/
        IconButtonValue smsIconBtnValue=new IconButtonValue(BOTTOM_TAB_TAG.SMS,R.drawable.sms_icon_selected_unfocused, R.drawable.sms_icon_unselected, R.string.sms);
        IconButtonData smsIconBtnData=new IconButtonData(getContext(), smsIconBtnValue);
        mBottomTabView.addIconButtonData(smsIconBtnData);
        /*End: sms tab*/
        
        /*Start: more tab*/
        IconButtonValue moreIconBtnValue=new IconButtonValue(BOTTOM_TAB_TAG.MORE,R.drawable.more_icon_selected_unfocused, R.drawable.more_icon_unselected, R.string.more);
        IconButtonData moreIconBtnData=new IconButtonData(getContext(), moreIconBtnValue);
        mBottomTabView.addIconButtonData(moreIconBtnData);
        /*End: more tab*/
        mBottomTabView.setOnTabChange(this);
        /*End : BOTTOM_TAB_TAG*/
        
        /*Start :BOTTOM_TAB_CALL_TAG*/
        mBottomTabCallView=(BottomTabView) view.findViewById(R.id.bottom_tab_call_view);
        mBottomTabCallView.removeAllViews();
        
        /*Start: call tab*/
	    IconButtonValue hideIconBtnValue=new IconButtonValue(BOTTOM_TAB_CALL_TAG.CALL,R.drawable.call_icon_selected_unfocused,R.drawable.call_icon_selected_focused, R.string.call);
	    IconButtonData hideIconBtnData=new IconButtonData(getContext(), hideIconBtnValue);
	    mBottomTabCallView.addIconButtonData(hideIconBtnData);
	    /*End: call tab*/
	    
	    /*Start: dial tab*/
	    IconButtonValue dialIconBtnValue=new IconButtonValue(BOTTOM_TAB_CALL_TAG.DIAL,R.drawable.dial_icon, R.string.call);
	    dialIconBtnValue.setWeight(2.0f);
	    dialIconBtnValue.setBackgroundResource(R.color.deep_sky_blue);
	    dialIconBtnValue.setTextVisibility(View.GONE);
	    IconButtonData dialIconBtnData=new IconButtonData(getContext(), dialIconBtnValue);
	    mBottomTabCallView.addIconButtonData(dialIconBtnData);
	    /*End: dial tab*/
	    
	    /*Start: delete tab*/
	    IconButtonValue deleteIconBtnValue=new IconButtonValue(BOTTOM_TAB_CALL_TAG.DELETE,R.drawable.delete_icon, R.string.delete);
	    IconButtonData deleteIconBtnData=new IconButtonData(getContext(), deleteIconBtnValue);
	    mBottomTabCallView.addIconButtonData(deleteIconBtnData);
	    /*End: delete tab*/
	    mBottomTabCallView.setOnTabChange(this);
	    /*Start: contacts tab*/
      /*  IconButtonValue contactsIconBtnValue=new IconButtonValue(BOTTOM_TAB_TAG.CONTACTS,R.drawable.contacts_icon_selected_unfocused, R.drawable.contacts_icon_unselected, R.string.contacts);
        IconButtonData contactsIconBtnData=new IconButtonData(getContext(), contactsIconBtnValue);
        mBottomTabView.addIconButtonData(contactsIconBtnData);*/
        /*End: contacts tab*/
        /*End :BOTTOM_TAB_CALL_TAG*/

		return view;
	}

	@Override
	protected void initListener() {
		FragmentManager fm=getActivity().getSupportFragmentManager();
		mAddressBookFragmentPagerAdapter=new AddressBookFragmentPagerAdapter(fm, mAddressBookViews);
		mCustomViewPager.setAdapter(mAddressBookFragmentPagerAdapter);
		mCustomViewPager.setPagingEnabled(false);
		mCustomViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int pos) {
				
				AddressBookView addressBookView=mAddressBookViews.get(pos);
				mBottomTabView.setCurrentTabItem(addressBookView.getTag());
				changeToTab(null, mBottomTabView.getCurrentTab(),TAB_CHANGE_STATE.TAB_SELECTED_FOCUSED);
			}
			
			@Override
			public void onPageScrolled(int pos, float posOffset, int posOffsetPixels) {
				
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				
			}
		});
	
		
	}

	
	
	@Override
	public void onStart() {
		//ContactsHelper.getInstance().startLoadContacts();//restart load contacts when contacts has been changed
		//CallRecordHelper.getInstance().startLoadCallRecord();//restart load callLog when callLog has been changed
		super.onStart();
	}

	/*Start: OnTabChange*/
	@Override
	public void onChangeToTab(Object fromTab, Object toTab,
			TAB_CHANGE_STATE tabChangeState) {
		//Toast.makeText(getContext(), "onChangeToTab"+"["+fromTab.toString()+"]["+toTab.toString()+"]tabChangeState["+tabChangeState.toString()+"]", Toast.LENGTH_SHORT).show();
		if(toTab instanceof BOTTOM_TAB_TAG){
			int item=getAddressBookViewItem(toTab);
			mCustomViewPager.setCurrentItem(item);
			changeToTab(fromTab, toTab, tabChangeState);
		}else if(toTab instanceof BOTTOM_TAB_CALL_TAG){
			Toast.makeText(getContext(), fromTab.toString()+"->"+toTab.toString(), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onClickTab(Object currentTab, TAB_CHANGE_STATE tabChangeState) {
		//Toast.makeText(getContext(), "onClickTab"+"["+currentTab.toString()+"]tabChangeState¡¾"+tabChangeState.toString()+"]", Toast.LENGTH_SHORT).show();
		if(currentTab instanceof BOTTOM_TAB_TAG){
			Fragment fragment=mAddressBookViews.get(getAddressBookViewItem(currentTab)).getFragment();
			switch ((BOTTOM_TAB_TAG)currentTab) {
			case CALL:
				if(fragment instanceof TelephoneFragment){
					((TelephoneFragment) fragment).updateView(tabChangeState);
				}
				break;
	
			default:
				break;
			}
		}else if(currentTab instanceof BOTTOM_TAB_CALL_TAG){
			Toast.makeText(getContext(), currentTab.toString(), Toast.LENGTH_SHORT).show();
			/*Fragment fragment=mAddressBookViews.get(getAddressBookViewItem(currentTab)).getFragment();
			switch ((BOTTOM_TAB_CALL_TAG)currentTab) {
			case HIDE:
				if(fragment instanceof TelephoneFragment){
					((TelephoneFragment) fragment).updateView(tabChangeState);
				}
				ViewUtil.hideView(mBottomTabCallView);
				ViewUtil.showView(mBottomTabView);
				break;
	
			default:
				break;
			}*/
		}
	}
	/*End: OnTabChange*/
	
	/*Start : OnCallLogLoad*/
	@Override
	public void onCallLogLoadSuccess() {
		Log.i(TAG, "onCallLogLoadSuccess size"+CallRecordHelper.getInstance().getBaseCallRecord().size());
		Fragment telephoneFragment=mAddressBookViews.get(getAddressBookViewItem(BOTTOM_TAB_TAG.CALL)).getFragment();
		if(telephoneFragment instanceof TelephoneFragment){
			((TelephoneFragment) telephoneFragment).callLogLoadSuccess();
		}
		
	}

	@Override
	public void onCallLogLoadFailed() {
		Log.i(TAG, "onCallLogLoadFailed size"+CallRecordHelper.getInstance().getBaseCallRecord().size());
		Fragment telephoneFragment=mAddressBookViews.get(getAddressBookViewItem(BOTTOM_TAB_TAG.CALL)).getFragment();
		if(telephoneFragment instanceof TelephoneFragment){
			((TelephoneFragment) telephoneFragment).callLogLoadFailed();
		}
	}
	/*End : OnCallLogLoad*/
	
	/*Start : OnContactsLoad*/
	@Override
	public void onContactsLoadSuccess() {
		Log.i(TAG, "onContactsLoadSuccess size"+ContactsHelper.getInstance().getBaseContacts().size());
		
		Fragment telephoneFragment=mAddressBookViews.get(getAddressBookViewItem(BOTTOM_TAB_TAG.CALL)).getFragment();
		if(telephoneFragment instanceof TelephoneFragment){
			((TelephoneFragment) telephoneFragment).contactsLoadSuccess();
		}
		
		Fragment contactsQwertyFragment=mAddressBookViews.get(getAddressBookViewItem(BOTTOM_TAB_TAG.CONTACTS)).getFragment();
		if(contactsQwertyFragment instanceof ContactsQwertyFragment){
			((ContactsQwertyFragment) contactsQwertyFragment).contactsLoadSuccess();
		}
	}

	@Override
	public void onContactsLoadFailed() {
		Log.i(TAG, "onContactsLoadFailed size"+ContactsHelper.getInstance().getBaseContacts().size());
		Fragment telephoneFragment=mAddressBookViews.get(getAddressBookViewItem(BOTTOM_TAB_TAG.CALL)).getFragment();
		if(telephoneFragment instanceof TelephoneFragment){
			((TelephoneFragment) telephoneFragment).contactsLoadFailed();
		}
		
		Fragment contactsQwertyFragment=mAddressBookViews.get(getAddressBookViewItem(BOTTOM_TAB_TAG.CONTACTS)).getFragment();
		if(contactsQwertyFragment instanceof ContactsQwertyFragment){
			((ContactsQwertyFragment) contactsQwertyFragment).contactsLoadFailed();
		}
	}
	/*End : OnContactsLoad*/
	
	/*Start : OnTelephoneDialChange*/
	@Override
	public void onPhoneNumberChange(String phoneNumber) {
		if(TextUtils.isEmpty(phoneNumber)){
			ViewUtil.hideView(mBottomTabCallView);
			ViewUtil.showView(mBottomTabView);
			
		}else{
			ViewUtil.hideView(mBottomTabView);
			ViewUtil.showView(mBottomTabCallView);
		}
	}
	
	/*End : OnTelephoneDialChange*/
	
	private void changeToTab(Object fromTab, Object toTab,TAB_CHANGE_STATE tabChangeState){
	    if(null==toTab){
	        return;
	    }
	    
	    Fragment fragment=mAddressBookViews.get(getAddressBookViewItem(toTab)).getFragment();
        switch ((BOTTOM_TAB_TAG)toTab) {
            case CALL:
                if(fragment instanceof TelephoneFragment){
                    ((TelephoneFragment) fragment).updateView(tabChangeState);
                    ((TelephoneFragment) fragment).updateSearch();
                }
                break;
            case CONTACTS:
                if(fragment instanceof ContactsQwertyFragment){
                    ((ContactsQwertyFragment) fragment).updateSearch();
                }
                break;

            default:
                break;
        }    
	}
	
	private int getAddressBookViewItem(Object tag){
		int item=0;;
		do{
			if(null==tag){
				break;
			}
			
			for(int i=0; i<mAddressBookViews.size();i++){
				if(mAddressBookViews.get(i).getTag().equals(tag)){
					item=i;
					break;
				}
			}
		}while(false);
	
		return item;
	}

}
