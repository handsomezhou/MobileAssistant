package com.handsomezhou.mobileassistant.activity;

import com.handsomezhou.mobileassistant.fragment.CallRecordDetailsFragment;
import com.handsomezhou.mobileassistant.model.CallRecordDetailsParameter;

import android.support.v4.app.Fragment;

public class CallRecordDetailsActivity extends BaseSingleFragmentActivity {
	
	@Override
	protected Fragment createFragment() {
		CallRecordDetailsParameter callRecordDetailsParameter=(CallRecordDetailsParameter) getIntent().getSerializableExtra(CallRecordDetailsFragment.EXTRA_CALL_RECORD_DETAILS_PARAMETER);
		return CallRecordDetailsFragment.newInstance(callRecordDetailsParameter);
	}

	@Override
	protected boolean isRealTimeLoadFragment() {
		
		return false;
	}

}
