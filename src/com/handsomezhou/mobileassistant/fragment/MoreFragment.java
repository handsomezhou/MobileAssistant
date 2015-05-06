package com.handsomezhou.mobileassistant.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.handsomezhou.mobileassistant.R;
import com.handsomezhou.mobileassistant.activity.AppSearchActivity;
import com.handsomezhou.mobileassistant.adapter.MoreAdapter;
import com.handsomezhou.mobileassistant.model.IconButtonData;
import com.handsomezhou.mobileassistant.model.IconButtonValue;

public class MoreFragment extends BaseFragment {
	private List<IconButtonData> mMoreData;
	private GridView mMoreGv;
	private MoreAdapter mMoreAdapter;
	
	public enum MORE_TAG{
		PHONE_INFO,
		APP_SEARCH,
		SETTING,
		ABOUT,
		
	}
	
	@Override
	protected void initData() {
		setContext(getActivity());
		mMoreData=new ArrayList<IconButtonData>();
		
		/*Start : phone info*/
		IconButtonValue phoneInfoIconButtonValue=new IconButtonValue(MORE_TAG.PHONE_INFO, R.drawable.phone_info, R.string.phone_info);
		IconButtonData phoneInfoIconButtonData=new IconButtonData(getContext(), phoneInfoIconButtonValue);
		mMoreData.add(phoneInfoIconButtonData);
		/*End : phone info*/
		
		/*Start : app search*/
		IconButtonValue appSearchIconButtonValue=new IconButtonValue(MORE_TAG.APP_SEARCH, R.drawable.app_search, R.string.app_search);
		IconButtonData appSearchIconButtonData=new IconButtonData(getContext(), appSearchIconButtonValue);
		mMoreData.add(appSearchIconButtonData);
		/*End : app search*/
		
		/*Start : setting*/
		IconButtonValue settingIconButtonValue=new IconButtonValue(MORE_TAG.SETTING, R.drawable.setting, R.string.setting);
		IconButtonData settingIconButtonData=new IconButtonData(getContext(),settingIconButtonValue);
		mMoreData.add(settingIconButtonData);
		/*End : setting*/
		
		/*Start : about*/
		IconButtonValue aboutIconButtonValue=new IconButtonValue(MORE_TAG.ABOUT, R.drawable.about, R.string.about);
		IconButtonData aboutIconButtonData=new IconButtonData(getContext(), aboutIconButtonValue);
		mMoreData.add(aboutIconButtonData);
		/*End : PHONE_INFO*/
	}

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container) {
		View view=inflater.inflate(R.layout.fragment_more, container, false);
		mMoreGv=(GridView)view.findViewById(R.id.more_grid_view);
		mMoreAdapter=new MoreAdapter(getContext(), R.layout.icon_button_grid_item, mMoreData);
		mMoreGv.setAdapter(mMoreAdapter);
		
		return view;
	}

	@Override
	protected void initListener() {
		mMoreGv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				IconButtonData iconButtonData = mMoreData.get(position);
				viewSelect(iconButtonData);
				
			}
		});
	}

	 private void viewSelect(IconButtonData iconButtonData) {
	        if (null == iconButtonData) {
	            return;
	        }

		switch ((MORE_TAG) iconButtonData.getIconButtonValue().getTag()) {

		case PHONE_INFO:

			break;
		case APP_SEARCH:
			enterAppSearch();
			break;
		case SETTING:

			break;
		case ABOUT:

			break;

		default:
			break;
		}

	        Toast.makeText(getContext(), iconButtonData.getIconButtonValue().getTag().toString(),
	                Toast.LENGTH_SHORT).show();
	    }
	 
	 private void enterAppSearch(){
		 Intent intent=new Intent(getContext(), AppSearchActivity.class);
		 startActivity(intent);
	 }
}
