package com.handsomezhou.mobileassistant.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.handsomezhou.mobileassistant.R;
import com.handsomezhou.mobileassistant.service.MobileAssistantService;
import com.handsomezhou.mobileassistant.util.CallRecordHelper;

public class MainActivity extends Activity {
	private static final String TAG="MainActivity";
	private Button mStartServiceBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initData();
		initView();
		initListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void initData(){
	
		return;
	}
	
	private void initView(){
		mStartServiceBtn=(Button)findViewById(R.id.start_service_btn);
		return;
	}
	
	private void initListener(){
		mStartServiceBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent  intent=new Intent(MainActivity.this, MobileAssistantService.class);
				intent.setAction(MobileAssistantService.ACTION_MOBILE_ASSISTANT_SERVICE);
				startService(intent);
				
				CallRecordHelper.getInstance().startLoadCallRecord();
			}
		});
		return;
	}
	
}
