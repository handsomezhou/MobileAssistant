package com.handsomezhou.mobileassistant.fragment;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handsomezhou.mobileassistant.R;
import com.handsomezhou.mobileassistant.Interface.OnTabChange.TAB_CHANGE_STATE;
import com.handsomezhou.mobileassistant.adapter.TelephoneFragmentPagerAdapter;
import com.handsomezhou.mobileassistant.helper.ContactsHelper;
import com.handsomezhou.mobileassistant.view.CustomViewPager;
import com.handsomezhou.mobileassistant.view.T9TelephoneDialpadView;
import com.handsomezhou.mobileassistant.view.T9TelephoneDialpadView.OnT9TelephoneDialpadView;



public class TelephoneFragment extends BaseFragment implements OnT9TelephoneDialpadView{
	private static final String TAG="TelephoneFragment";
	private List<Fragment> mFragments;
	private CustomViewPager mCustomViewPager;
	private TelephoneFragmentPagerAdapter mTelephoneFragmentPagerAdapter;
	
	private T9TelephoneDialpadView mT9TelephoneDialpadView;
	
	/*private CallLogFragment mCallLogFragment;
	private ContactsT9Fragment mContactsT9Fragment;*/

	@Override
	protected void initData() {
		setContext(getActivity());
		mFragments=new ArrayList<Fragment>();
		if(null!=mFragments){
			
			
			Fragment contactsT9Fragment=new ContactsT9Fragment();
			if(null!=contactsT9Fragment){
				mFragments.add(contactsT9Fragment);
			}
			
			Fragment callLogFragment = new CallLogFragment();
			if (null != callLogFragment) {
				mFragments.add(callLogFragment);
			}
		}
		
	}

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container) {
		View view=inflater.inflate(R.layout.fragment_telephone, container, false);
		/*mTelephoneBtn=(Button) view.findViewById(R.id.telephone_btn);*/
		mCustomViewPager=(CustomViewPager)view.findViewById(R.id.custom_view_pager_telephone);
		mCustomViewPager.setPagingEnabled(true);
		
		mT9TelephoneDialpadView=(T9TelephoneDialpadView) view.findViewById(R.id.t9_telephone_dialpad_layout);
		mT9TelephoneDialpadView.setOnT9TelephoneDialpadView(this);

		return view;
	}

	@Override
	protected void initListener() {
		FragmentManager fm=getActivity().getSupportFragmentManager();
		mTelephoneFragmentPagerAdapter=new TelephoneFragmentPagerAdapter(fm, mFragments);
		mCustomViewPager.setAdapter(mTelephoneFragmentPagerAdapter);
		
		mCustomViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int pos) {
				//mCustomViewPager.setCurrentItem(pos);
				
			}
			
			@Override
			public void onPageScrolled(int pos, float posOffset, int posOffsetPixels) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	/*Start: OnT9TelephoneDialpadView*/
    @Override
    public void onAddDialCharacter(String addCharacter) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onDeleteDialCharacter(String deleteCharacter) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onDialInputTextChanged(String curCharacter) {
        updateSearch(curCharacter);   
    }

    @Override
    public void onHideT9TelephoneDialpadView() {
        // TODO Auto-generated method stub
        
    }
    /*End: OnT9TelephoneDialpadView*/

    public void updateSearch(){  
        updateSearch(mT9TelephoneDialpadView.getT9Input());
    }
    
    private void updateSearch(String search){
        String curCharacter;
        if(null==search){
            curCharacter=search;
        }else{
            curCharacter=search.trim();
        }
        
        if(TextUtils.isEmpty(curCharacter)){
            ContactsHelper.getInstance().parseT9InputSearchContacts(null);
            mCustomViewPager.setCurrentItem(0);
        }else{
            ContactsHelper.getInstance().parseT9InputSearchContacts(curCharacter);
            mCustomViewPager.setCurrentItem(1);
        }
        
        
        
        //mContactsOperationView.updateContactsList(TextUtils.isEmpty(curCharacter)); 
        
    }
    
	public void  updateView(TAB_CHANGE_STATE tabChangeState){	    
		switch (tabChangeState) {
		case TAB_UNSELECTED:
			//Toast.makeText(getContext(), TAG+tabChangeState.toString(), Toast.LENGTH_SHORT).show();
		    mT9TelephoneDialpadView.showT9TelephoneDialpadView();
			break;
		case TAB_SELECTED_UNFOCUSED:
			//Toast.makeText(getContext(), TAG+tabChangeState.toString(), Toast.LENGTH_SHORT).show();
		    mT9TelephoneDialpadView.hideT9TelephoneDialpadView();
			break;
			
		case TAB_SELECTED_FOCUSED:
			//Toast.makeText(getContext(), TAG+tabChangeState.toString(), Toast.LENGTH_SHORT).show();
		    mT9TelephoneDialpadView.showT9TelephoneDialpadView();
			break;
			
		default:
			break;
		}
	}

	
}
