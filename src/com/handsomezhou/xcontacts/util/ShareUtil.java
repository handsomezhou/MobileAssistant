package com.handsomezhou.xcontacts.util;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * share content by all kinds of ways
 * @author handsomezhou
 * 
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ShareUtil {
	/**
	 * share text by sms
	 * 
	 * @param context
	 * @param RecipientsPhoneNumber	phoneNumberformat:"phoneNumber1;phoneNumber2;..."
	 * @param textContent
	 *          
	 */
	public static void shareTextBySms(Context context,
			String RecipientsPhoneNumber, String textContent) {
		Intent share = new Intent(Intent.ACTION_VIEW);

		share.putExtra("address", RecipientsPhoneNumber);
		share.putExtra("sms_body", textContent);
		share.setType("vnd.android-dir/mms-sms");

		context.startActivity(share);
	}
	
	/**
	 * copy text
	 * @param context
	 * @param content
	 */
	public static void copyText( Context context,String content){
		ClipboardManager clipboardManager = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);  
		ClipData clipData = ClipData.newPlainText("text", content);
		clipboardManager.setPrimaryClip(clipData);
	}
	
	/**
	 * paste text
	 * @param context
	 * @return the string of paste text
	 */
	public static String pasteText(Context context){
		ClipboardManager clipboardManager = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
		ClipData clipData=clipboardManager.getPrimaryClip();
		
		return clipData.getItemAt(0).getText().toString();  
	}
}
