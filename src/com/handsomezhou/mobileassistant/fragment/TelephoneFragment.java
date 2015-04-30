package com.handsomezhou.mobileassistant.fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.handsomezhou.mobileassistant.R;
import com.handsomezhou.mobileassistant.Interface.OnTabChange.TAB_CHANGE_STATE;
import com.handsomezhou.mobileassistant.helper.ContactsHelper;
import com.handsomezhou.mobileassistant.view.ContactsOperationView;
import com.handsomezhou.mobileassistant.view.T9TelephoneDialpadView;
import com.handsomezhou.mobileassistant.view.T9TelephoneDialpadView.OnT9TelephoneDialpadView;



public class TelephoneFragment extends BaseFragment implements OnT9TelephoneDialpadView{
	private static final String TAG="TelephoneFragment";
	private T9TelephoneDialpadView mT9TelephoneDialpadView;
	private ContactsOperationView mContactsOperationView;

	@Override
	protected void initData() {
		setContext(getActivity().getApplicationContext());
	}

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container) {
		View view=inflater.inflate(R.layout.fragment_telephone, container, false);
		/*mTelephoneBtn=(Button) view.findViewById(R.id.telephone_btn);*/
		mT9TelephoneDialpadView=(T9TelephoneDialpadView) view.findViewById(R.id.t9_telephone_dialpad_layout);
		mT9TelephoneDialpadView.setOnT9TelephoneDialpadView(this);
		mContactsOperationView=(ContactsOperationView)view.findViewById(R.id.contacts_operation_layout);
		return view;
	}

	@Override
	protected void initListener() {
		/*if(null!=mTelephoneBtn){
			mTelephoneBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Toast.makeText(getActivity().getApplicationContext(),getContext().getResources().getString(R.string.telephone) , Toast.LENGTH_SHORT).show();
				}
			});
		}*/
		
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
        }else{
            ContactsHelper.getInstance().parseT9InputSearchContacts(curCharacter);
        }
        mContactsOperationView.updateContactsList(TextUtils.isEmpty(curCharacter)); 
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
