package com.handsomezhou.mobileassistant.util;

import android.content.Context;

import com.handsomezhou.mobileassistant.R;
import com.handsomezhou.mobileassistant.Interface.RelativeTimeDay;
import com.handsomezhou.mobileassistant.model.DateTime;

public class StringUtil {
	public static String getCallDate(Context context,DateTime dateTime){
		StringBuffer callDateStringBuffer=new StringBuffer();
		
		if(dateTime.getYear()==TimeUtil.getYear()){
			int nowDays=TimeUtil.getDaySince1970January1(-1);
			int callDays=TimeUtil.getDaySince1970January1(dateTime.getTime());
			switch (nowDays-callDays) {
			case (RelativeTimeDay.TODAY-RelativeTimeDay.TODAY):
				callDateStringBuffer.append(context.getString(R.string.today)+" "+StringUtil.prefixFillZero(dateTime.getHourOfDay(), 2)+":"+StringUtil.prefixFillZero(dateTime.getMinute(), 2));
				break;
			case (RelativeTimeDay.TODAY-RelativeTimeDay.YESTERDAY):
				callDateStringBuffer.append(context.getString(R.string.yesterday)+" "+StringUtil.prefixFillZero(dateTime.getHourOfDay(), 2)+":"+StringUtil.prefixFillZero(dateTime.getMinute(), 2));
				break;
			case (RelativeTimeDay.TODAY-RelativeTimeDay.THE_DAY_BEFORE_YESTERDAY):
				callDateStringBuffer.append(context.getString(R.string.the_day_before_yesterday)+" "+StringUtil.prefixFillZero(dateTime.getHourOfDay(), 2)+":"+StringUtil.prefixFillZero(dateTime.getMinute(), 2));
				break;
			default:
				callDateStringBuffer.append(StringUtil.prefixFillZero(dateTime.getMonth()+1, 2)+"-"+StringUtil.prefixFillZero(dateTime.getDayOfMonth(), 2));
				break;
			}
		}else{
			callDateStringBuffer.append(dateTime.getYear()+"-"+StringUtil.prefixFillZero(dateTime.getMonth()+1, 2)+"-"+StringUtil.prefixFillZero(dateTime.getDayOfMonth(), 2));
		}
		
		return callDateStringBuffer.toString();
	}
	/**
	 * fill zero in the prefix of number
	 * @param number
	 * @param numberOfDigits
	 * @return
	 */
	public static String prefixFillZero(int number,int numberOfDigits){
		StringBuffer numberBuffer=new StringBuffer();
		do{
			if(numberOfDigits<=0){
				numberBuffer.append(String.valueOf(number));
				break;
			}
			
			if(number<0){
				numberBuffer.append(String.valueOf(number));
				break;
			}
			
			int numberDigitCount=number/10;
			if(numberDigitCount>=numberOfDigits){
				numberBuffer.append(String.valueOf(number));
				break;
			}
			
			while(numberOfDigits-(numberDigitCount+1)>0){
				numberBuffer.append("0");
				numberDigitCount++;
			}
			numberBuffer.append(String.valueOf(number));
			break;
			
				
		}while(false);
		
		return numberBuffer.toString();
		
	}
	

}
