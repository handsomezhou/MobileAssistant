package com.handsomezhou.mobileassistant.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.provider.ContactsContract;
import android.util.Log;

import com.handsomezhou.mobileassistant.application.MobileAssistantApplication;
import com.handsomezhou.mobileassistant.model.Contacts;
import com.handsomezhou.mobileassistant.model.Contacts.SearchByType;
import com.pinyinsearch.model.PinyinBaseUnit;
import com.pinyinsearch.model.PinyinUnit;
import com.pinyinsearch.util.PinyinUtil;
import com.pinyinsearch.util.QwertyMatchPinyinUnits;
import com.pinyinsearch.util.T9MatchPinyinUnits;



public class ContactsHelper {

	private static final String TAG = "ContactsHelper";
	private Context mContext;
	private static ContactsHelper mInstance = null;
	private List<Contacts> mBaseContacts = null; // The basic data used for the search
	/*
	 * save the first input string which search no result.
	 * mXXXFirstNoSearchResultInput.size<=0, means that the first input string
	 * which search no result not appear. mXXXFirstNoSearchResultInput.size>0,
	 * means that the first input string which search no result has appeared,
	 * it's mXXXFirstNoSearchResultInput.toString(). We can reduce the number of
	 * search basic data by the first input string which search no result.
	 */	
	private List<Contacts> mT9SearchContacts=null;	
	private static StringBuffer  mT9FirstNoSearchResultInput=null;
	
	private List<Contacts> mQwertySearchContacts=null;
	private static StringBuffer  mQwertyFirstNoSearchResultInput=null;
	
	private AsyncTask<Object, Object, List<Contacts>> mLoadTask = null;
	private OnContactsLoad mOnContactsLoad = null;

	private static boolean mContactsChanged = true;
	private HashMap<String, Contacts> mSelectedContactsHashMap=null; //(id+phoneNumber)as key

	public interface OnContactsLoad {
		void onContactsLoadSuccess();

		void onContactsLoadFailed();
	}

	public interface OnContactsChanged {
		void onContactsChanged();
	}

	private ContactsHelper() {
		initContactsHelper();
	}

	public static ContactsHelper getInstance() {
		if (null == mInstance) {
			mInstance = new ContactsHelper();
		}

		return mInstance;
	}

	public void destroy() {
		if (null != mInstance) {
			mInstance = null;// the system will free other memory.
		}
	}

	public List<Contacts> getBaseContacts() {
		return mBaseContacts;
	}

	public List<Contacts> getT9SearchContacts(){
		return mT9SearchContacts;
	}
	
	public List<Contacts> getQwertySearchContacts(){
		return mQwertySearchContacts;
	}
	
	public int getQwertySearchContactsIndex(Contacts contacts) {
		int index = -1;
		if (null == contacts) {
			return -1;
		}
		int searchContactsCount = mQwertySearchContacts.size();
		for (int i = 0; i < searchContactsCount; i++) {
			if (contacts.getName().charAt(0) == mQwertySearchContacts.get(i)
					.getName().charAt(0)) {
				index = i;
				break;
			}
		}

		return index;
	}

	// public void setSearchContacts(List<Contacts> searchContacts) {
	// mSearchContacts = searchContacts;
	// }

	public OnContactsLoad getOnContactsLoad() {
		return mOnContactsLoad;
	}

	public void setOnContactsLoad(OnContactsLoad onContactsLoad) {
		mOnContactsLoad = onContactsLoad;
	}

	public boolean isContactsChanged() {
		return mContactsChanged;
	}

	public void setContactsChanged(boolean contactsChanged) {
		mContactsChanged = contactsChanged;
	}
	
	
	public HashMap<String, Contacts> getSelectedContacts() {
		return mSelectedContactsHashMap;
	}

	public void setSelectedContacts(HashMap<String, Contacts> selectedContacts) {
		mSelectedContactsHashMap = selectedContacts;
	}
	
	/**
	 * Provides an function to start load contacts
	 * 
	 * @return start load success return true, otherwise return false
	 */
	public boolean startLoadContacts() {
	
		if (true == isSearching()) {
			return false;
		}
		
		if (false == isContactsChanged()) {
			return false;
		}
	
		mLoadTask = new AsyncTask<Object, Object,List<Contacts>>() {

			@Override
			protected List<Contacts> doInBackground(Object... params) {
				
				return loadContacts(mContext);
			}

			@Override
			protected void onPostExecute(List<Contacts> result) {
				
				parseContacts(result);
				super.onPostExecute(result);
				setContactsChanged(false);
				mLoadTask = null;
			}
		}.execute();

		return true;
	}

	/**
	 * @description T9 search base data according to string parameter
	 * @param search
	 *            (valid characters include:'0'~'9','*','#')
	 * @return void
	 *
	 * 
	 */
	public void getT9SearchContacts(String search) {
		List<Contacts> mSearchByNameContacts=new ArrayList<Contacts>();
		List<Contacts> mSearchByPhoneNumberContacts=new ArrayList<Contacts>();

		if (null == search) {// add all base data to search
			if (null != mT9SearchContacts) {
				mT9SearchContacts.clear();
			} else {
				mT9SearchContacts = new ArrayList<Contacts>();
			}

			for (int i=0; i<mBaseContacts.size(); i++) {
				Contacts currentContacts=null;
				for(currentContacts=mBaseContacts.get(i); null!=currentContacts; currentContacts=currentContacts.getNextContacts()){
					currentContacts.setSearchByType(SearchByType.SearchByNull);
					currentContacts.clearMatchKeywords();
					currentContacts.setMatchStartIndex(-1);
					currentContacts.setMatchLength(0);
					if(true==currentContacts.isFirstMultipleContacts()){
						mT9SearchContacts.add(currentContacts);
					}else{
						if(false==currentContacts.isHideMultipleContacts()){
							mT9SearchContacts.add(currentContacts);
						}
					}
				}
			}
			
			//mT9SearchContacts.addAll(mBaseContacts);
			mT9FirstNoSearchResultInput.delete(0,mT9FirstNoSearchResultInput.length());
			Log.i(TAG, "null==search,mT9FirstNoSearchResultInput.length()="+ mT9FirstNoSearchResultInput.length());
			return;
		}

		if (mT9FirstNoSearchResultInput.length() > 0) {
			if (search.contains(mT9FirstNoSearchResultInput.toString())) {
				Log.i(TAG,
						"no need  to search,null!=search,mT9FirstNoSearchResultInput.length()="
								+ mT9FirstNoSearchResultInput.length() + "["
								+ mT9FirstNoSearchResultInput.toString() + "]"
								+ ";searchlen=" + search.length() + "["
								+ search + "]");
				return;
			} else {
				Log.i(TAG,
						"delete  mT9FirstNoSearchResultInput, null!=search,mT9FirstNoSearchResultInput.length()="
								+ mT9FirstNoSearchResultInput.length()
								+ "["
								+ mT9FirstNoSearchResultInput.toString()
								+ "]"
								+ ";searchlen="
								+ search.length()
								+ "["
								+ search + "]");
				mT9FirstNoSearchResultInput.delete(0,
						mT9FirstNoSearchResultInput.length());
			}
		}

		if (null != mT9SearchContacts) {
			mT9SearchContacts.clear();
		} else {
			mT9SearchContacts = new ArrayList<Contacts>();
		}

		int contactsCount = mBaseContacts.size();

		/**
		 * search process: 1:Search by name (1)Search by name pinyin
		 * characters(org name->name pinyin characters) ('0'~'9','*','#')
		 * (2)Search by org name ('0'~'9','*','#') 2:Search by phone number
		 * ('0'~'9','*','#')
		 */
		for (int i = 0; i < contactsCount; i++) {

			List<PinyinUnit> pinyinUnits = mBaseContacts.get(i).getNamePinyinUnits();
			StringBuffer chineseKeyWord = new StringBuffer();// In order to get Chinese KeyWords.Ofcourse it's maybe not Chinese characters.
			String name = mBaseContacts.get(i).getName();
			if (true == T9MatchPinyinUnits.matchPinyinUnits(pinyinUnits, name,search, chineseKeyWord)) {// search by NamePinyinUnits;
				
				Contacts currentContacts=null;
				Contacts firstContacts=null;
				for(currentContacts=mBaseContacts.get(i),firstContacts=currentContacts; null!=currentContacts; currentContacts=currentContacts.getNextContacts()){
					currentContacts.setSearchByType(SearchByType.SearchByName);
					currentContacts.setMatchKeywords(chineseKeyWord.toString());
					currentContacts.setMatchStartIndex(firstContacts.getName().indexOf(firstContacts.getMatchKeywords().toString()));
					currentContacts.setMatchLength(firstContacts.getMatchKeywords().length());
					mSearchByNameContacts.add(currentContacts);
				}
				chineseKeyWord.delete(0, chineseKeyWord.length());
				
				continue;
			} else {
				Contacts currentContacts=null;
				for(currentContacts=mBaseContacts.get(i); null!=currentContacts; currentContacts=currentContacts.getNextContacts()){
					if(currentContacts.getPhoneNumber().contains(search)){// search by phone number
						currentContacts.setSearchByType(SearchByType.SearchByPhoneNumber);
						currentContacts.setMatchKeywords(search);
						currentContacts.setMatchStartIndex(currentContacts.getPhoneNumber().indexOf(search));
						currentContacts.setMatchLength(search.length());
						mSearchByPhoneNumberContacts.add(currentContacts);
					}
				}
				continue;

			}
		}
		
		if(mSearchByNameContacts.size()>0){
			Collections.sort(mSearchByNameContacts, Contacts.mSearchComparator);
		}
		if(mSearchByPhoneNumberContacts.size()>0){
			Collections.sort(mSearchByPhoneNumberContacts, Contacts.mSearchComparator);
		}
		
		mT9SearchContacts.clear();
		mT9SearchContacts.addAll(mSearchByNameContacts);
		mT9SearchContacts.addAll(mSearchByPhoneNumberContacts);
		
		if (mT9SearchContacts.size() <= 0) {
			if (mT9FirstNoSearchResultInput.length() <= 0) {
				mT9FirstNoSearchResultInput.append(search);
				Log.i(TAG,
						"no search result,null!=search,mFirstNoSearchResultInput.length()="
								+ mT9FirstNoSearchResultInput.length() + "["
								+ mT9FirstNoSearchResultInput.toString() + "]"
								+ ";searchlen=" + search.length() + "["
								+ search + "]");
			} else {

			}
		}

	}

	/**
	 * @description Qwerty search base data according to string parameter
	 * @param search
	 * @return void
	 */
	public void getQwertySearchContacts(String search) {
		if (null == search) {// add all base data to search
			if (null != mQwertySearchContacts) {
				mQwertySearchContacts.clear();
			} else {
				mQwertySearchContacts = new ArrayList<Contacts>();
			}

			for(int i=0; i<mBaseContacts.size(); i++){
				Contacts currentContacts=null;
				for(currentContacts=mBaseContacts.get(i); null!=currentContacts; currentContacts=currentContacts.getNextContacts()){
					currentContacts.setSearchByType(SearchByType.SearchByNull);
					currentContacts.clearMatchKeywords();
					currentContacts.setMatchStartIndex(-1);
					currentContacts.setMatchLength(0);
					if(true==currentContacts.isFirstMultipleContacts()){
						mQwertySearchContacts.add(currentContacts);
					}else{
						if(false==currentContacts.isHideMultipleContacts()){
							mQwertySearchContacts.add(currentContacts);
						}
					}
				}
			}

			//mQwertySearchContacts.addAll(mBaseContacts);
			mQwertyFirstNoSearchResultInput.delete(0,mQwertyFirstNoSearchResultInput.length());
			Log.i(TAG, "null==search,mQwertyFirstNoSearchResultInput.length()="+ mQwertyFirstNoSearchResultInput.length());
			return;
		}

		if (mQwertyFirstNoSearchResultInput.length() > 0) {
			if (search.contains(mQwertyFirstNoSearchResultInput.toString())) {
				Log.i(TAG,
						"no need  to search,null!=search,mQwertyFirstNoSearchResultInput.length()="
								+ mQwertyFirstNoSearchResultInput.length() + "["
								+ mQwertyFirstNoSearchResultInput.toString() + "]"
								+ ";searchlen=" + search.length() + "["
								+ search + "]");
				return;
			} else {
				Log.i(TAG,
						"delete  mQwertyFirstNoSearchResultInput, null!=search,mQwertyFirstNoSearchResultInput.length()="
								+ mQwertyFirstNoSearchResultInput.length()
								+ "["
								+ mQwertyFirstNoSearchResultInput.toString()
								+ "]"
								+ ";searchlen="
								+ search.length()
								+ "["
								+ search + "]");
				mQwertyFirstNoSearchResultInput.delete(0,
						mQwertyFirstNoSearchResultInput.length());
			}
		}

		if (null != mQwertySearchContacts) {
			mQwertySearchContacts.clear();
		} else {
			mQwertySearchContacts = new ArrayList<Contacts>();
		}

		int contactsCount = mBaseContacts.size();

		/**
		 * search process: 1:Search by name (1)Search by original name (2)Search
		 * by name pinyin characters(original name->name pinyin characters)
		 * 2:Search by phone number
		 */
		for (int i = 0; i < contactsCount; i++) {

			List<PinyinUnit> pinyinUnits = mBaseContacts.get(i).getNamePinyinUnits();
			StringBuffer chineseKeyWord = new StringBuffer();// In order to get Chinese KeyWords.Ofcourse it's maybe not Chinese characters.
			
			String name = mBaseContacts.get(i).getName();
			if (true == QwertyMatchPinyinUnits.matchPinyinUnits(pinyinUnits,name, search, chineseKeyWord)) {// search by NamePinyinUnits;
				Contacts currentContacts=null;
				Contacts firstContacts=null;
				for(currentContacts=mBaseContacts.get(i),firstContacts=currentContacts; null!=currentContacts; currentContacts=currentContacts.getNextContacts()){
					currentContacts.setSearchByType(SearchByType.SearchByName);
					currentContacts.setMatchKeywords(chineseKeyWord.toString());
					currentContacts.setMatchStartIndex(firstContacts.getName().indexOf(firstContacts.getMatchKeywords().toString()));
					currentContacts.setMatchLength(firstContacts.getMatchKeywords().length());
					mQwertySearchContacts.add(currentContacts);
				}
				chineseKeyWord.delete(0, chineseKeyWord.length());
				
				continue;
			} else {
				Contacts currentContacts=null;
				for(currentContacts=mBaseContacts.get(i); null!=currentContacts; currentContacts=currentContacts.getNextContacts()){
					if(currentContacts.getPhoneNumber().contains(search)){// search by phone number
						currentContacts.setSearchByType(SearchByType.SearchByPhoneNumber);
						currentContacts.setMatchKeywords(search);
						currentContacts.setMatchStartIndex(currentContacts.getPhoneNumber().indexOf(search));
						currentContacts.setMatchLength(search.length());
						mQwertySearchContacts.add(currentContacts);
					}
				}
				continue;
			}
		}

		if (mQwertySearchContacts.size() <= 0) {
			if (mQwertyFirstNoSearchResultInput.length() <= 0) {
				mQwertyFirstNoSearchResultInput.append(search);
				Log.i(TAG,
						"no search result,null!=search,mQwertyFirstNoSearchResultInput.length()="
								+ mQwertyFirstNoSearchResultInput.length() + "["
								+ mQwertyFirstNoSearchResultInput.toString() + "]"
								+ ";searchlen=" + search.length() + "["
								+ search + "]");
			} else {

			}
		}else{
			Collections.sort(mQwertySearchContacts, Contacts.mSearchComparator);
		}

	}

	public void clearSelectedContacts(){
		if(null==mSelectedContactsHashMap){
			mSelectedContactsHashMap=new HashMap<String, Contacts>();
			return;
		}
		
		mSelectedContactsHashMap.clear();
	}
	
	public boolean addSelectedContacts(Contacts contacts){
		do{
			if(null==contacts){
				break;
			}
			
			if(null==mSelectedContactsHashMap){
				mSelectedContactsHashMap=new HashMap<String, Contacts>();
			}
			
			mSelectedContactsHashMap.put(getSelectedContactsKey(contacts), contacts);
			
			return true;
		}while(false);
		
		return false;
	
	}
	
	public void removeSelectedContacts(Contacts contacts){
		if(null==contacts){
			return;
		}
		
		if(null==mSelectedContactsHashMap){
			return;
		}
		
		mSelectedContactsHashMap.remove(getSelectedContactsKey(contacts));
	}
	
	// just for debug
	public void showContactsInfo() {
		int contactsCount = ContactsHelper.getInstance().getBaseContacts().size();
		for (int i = 0; i < contactsCount; i++) {
			Contacts currentCoutacts=null;
			for(currentCoutacts=ContactsHelper.getInstance().getBaseContacts().get(i);null!=currentCoutacts; currentCoutacts=currentCoutacts.getNextContacts()){
				Log.i(TAG, "======================================================================");
				String name = currentCoutacts.getName();
				List<PinyinUnit> pinyinUnit = currentCoutacts.getNamePinyinUnits();
				Log.i(TAG,
						"++++++++++++++++++++++++++++++:name=[" + name + "] phoneNumber"+currentCoutacts.getPhoneNumber()
								+currentCoutacts.isHideMultipleContacts()+ "firstCharacter=["
								+ PinyinUtil.getFirstCharacter(pinyinUnit) + "]"
								+ "firstLetter=["
								+ PinyinUtil.getFirstLetter(pinyinUnit) + "]"
								+ "+++++++++++++++++++++++++++++");
				int pinyinUnitCount = pinyinUnit.size();
				for (int j = 0; j < pinyinUnitCount; j++) {
					PinyinUnit pyUnit = pinyinUnit.get(j);
					Log.i(TAG, "j=" + j + ",isPinyin[" + pyUnit.isPinyin()
							+ "],startPosition=[" + pyUnit.getStartPosition() + "]");
					List<PinyinBaseUnit> stringIndex = pyUnit
							.getPinyinBaseUnitIndex();
					int stringIndexLength = stringIndex.size();
					for (int k = 0; k < stringIndexLength; k++) {
						Log.i(TAG, "k=" + k + "["
								+ stringIndex.get(k).getOriginalString() + "]"
								+ "[" + stringIndex.get(k).getPinyin() + "]+["
								+ stringIndex.get(k).getNumber() + "]");
					}
				}
			}
			
			
		}
	}

	private void initContactsHelper() {
		mContext = MobileAssistantApplication.getContextObject();
		setContactsChanged(true);
		if (null == mBaseContacts) {
			mBaseContacts = new ArrayList<Contacts>();
		} else {
			mBaseContacts.clear();
		}

		if(null==mT9SearchContacts){
			mT9SearchContacts=new ArrayList<Contacts>();
		}else{
			mT9SearchContacts.clear();
		}
		
		if(null==mT9FirstNoSearchResultInput){
			mT9FirstNoSearchResultInput=new StringBuffer();
		}else{
			mT9FirstNoSearchResultInput.delete(0,
					mT9FirstNoSearchResultInput.length());
		}
		
		if (null == mQwertySearchContacts) {
			mQwertySearchContacts = new ArrayList<Contacts>();
		} else {
			mQwertySearchContacts.clear();
		}

		if (null == mQwertyFirstNoSearchResultInput) {
			mQwertyFirstNoSearchResultInput = new StringBuffer();
		} else {
			mQwertyFirstNoSearchResultInput.delete(0,
					mQwertyFirstNoSearchResultInput.length());
		}
		
		if(null==mSelectedContactsHashMap){
			mSelectedContactsHashMap=new HashMap<String, Contacts>();
		}else{
			mSelectedContactsHashMap.clear();
		}
	}

	private boolean isSearching() {
		return (mLoadTask != null && mLoadTask.getStatus() == Status.RUNNING);
	}

	@SuppressLint("DefaultLocale")
	private List<Contacts> loadContacts(Context context) {
		List<Contacts> kanjiStartContacts = new ArrayList<Contacts>();
		HashMap<String, Contacts> kanjiStartContactsHashMap=new HashMap<String, Contacts>();
		
		List<Contacts>  nonKanjiStartContacts = new ArrayList<Contacts>();
		HashMap<String, Contacts> nonKanjiStartContactsHashMap=new HashMap<String,Contacts>();
		
		List<Contacts> contacts=new ArrayList<Contacts>();
		
		Contacts cs=null;
		Cursor cursor = null;
		String sortkey = null;
		long startLoadTime=System.currentTimeMillis();
		String[] projection=new String[] {ContactsContract.CommonDataKinds.Phone.CONTACT_ID, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER};
		try {

			cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection,null, null, "sort_key");
			
			int idColumnIndex=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID);
			int dispalyNameColumnIndex=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
			int numberColumnIndex=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
			while (cursor.moveToNext()) {

				String id=cursor.getString(idColumnIndex);
				String displayName = cursor.getString(dispalyNameColumnIndex);
				String phoneNumber = cursor.getString(numberColumnIndex);
			//	Log.i(TAG, "id=["+id+"]name=["+displayName+"]"+"number=["+phoneNumber+"]");
				
				boolean kanjiStartContactsExist=kanjiStartContactsHashMap.containsKey(id);
				boolean nonKanjiStartContactsExist=nonKanjiStartContactsHashMap.containsKey(id);
				
				if(true==kanjiStartContactsExist){
					cs=kanjiStartContactsHashMap.get(id);
					Contacts.addMulitpleContact(cs, phoneNumber);
				}else if(true==nonKanjiStartContactsExist){
					cs=nonKanjiStartContactsHashMap.get(id);
					Contacts.addMulitpleContact(cs, phoneNumber);
				}else{
					
					cs = new Contacts(id,displayName, phoneNumber);
					PinyinUtil.chineseStringToPinyinUnit(cs.getName(),cs.getNamePinyinUnits());
					sortkey = PinyinUtil.getSortKey(cs.getNamePinyinUnits()).toUpperCase();
					cs.setSortKey(praseSortKey(sortkey));
					boolean isKanji=PinyinUtil.isKanji(cs.getName().charAt(0));
					
					if(true==isKanji){
						kanjiStartContactsHashMap.put(id, cs);
					}else{
						nonKanjiStartContactsHashMap.put(id, cs);
					}
					
				}
			}
		} catch (Exception e) {

		} finally {
			if (null != cursor) {
				cursor.close();
				cursor = null;
			}
		}
		
		kanjiStartContacts.addAll(kanjiStartContactsHashMap.values());
		Collections.sort(kanjiStartContacts, Contacts.mAscComparator);
		
		nonKanjiStartContacts.addAll(nonKanjiStartContactsHashMap.values());
		Collections.sort(nonKanjiStartContacts, Contacts.mAscComparator);
		
		//contacts.addAll(nonKanjiStartContacts);
		contacts.addAll(kanjiStartContacts);
	
		//merge nonKanjiStartContacts and kanjiStartContacts
		int lastIndex=0;
		boolean shouldBeAdd=false;
		for(int i=0; i<nonKanjiStartContacts.size(); i++){
			String nonKanfirstLetter=PinyinUtil.getFirstLetter(nonKanjiStartContacts.get(i).getNamePinyinUnits());
			//Log.i(TAG, "nonKanfirstLetter=["+nonKanfirstLetter+"]");
			int j=0;
			for(j=0+lastIndex; j<contacts.size(); j++){
				String firstLetter=PinyinUtil.getFirstLetter(contacts.get(j).getNamePinyinUnits());
				lastIndex++;
				if(firstLetter.charAt(0)>nonKanfirstLetter.charAt(0)){
					shouldBeAdd=true;
					break;
				}else{
					shouldBeAdd=false;
				}
			}
			
			if(lastIndex>=contacts.size()){
				lastIndex++;
				shouldBeAdd=true;
				//Log.i(TAG, "lastIndex="+lastIndex);
			}
			
			if(true==shouldBeAdd){
				contacts.add(j, nonKanjiStartContacts.get(i));
				shouldBeAdd=false;
			}
		}
	
		long endLoadTime=System.currentTimeMillis();
		Log.i(TAG, "endLoadTime-startLoadTime=["+(endLoadTime-startLoadTime)+"] contacts.size()="+contacts.size());
		
		/*for(int i=0; i<contacts.size(); i++){
			Log.i(TAG, "****************************************");
			Contacts currentContacts=contacts.get(i);
			while(null!=currentContacts){
				Log.i(TAG, "name["+currentContacts.getName()+"]phoneNumber["+currentContacts.getPhoneNumber()+"]");
				currentContacts=currentContacts.getNextContacts();
			}
				
		}*/
		return contacts;
	}
	
	private void parseContacts(List<Contacts> contacts) {
		
		if (null == contacts || contacts.size() < 1) {
			if (null != mOnContactsLoad) {
				mOnContactsLoad.onContactsLoadFailed();
			}
			
			return;
		}

		for (Contacts contact : contacts) {
			if (!mBaseContacts.contains(contact)) {
				mBaseContacts.add(contact);
			}
		}

		if (null != mOnContactsLoad) {
			mOnContactsLoad.onContactsLoadSuccess();
			
		}

		return;
	}

	private String praseSortKey(String sortKey) {
		if (null == sortKey || sortKey.length() <= 0) {
			return null;
		}

		if ((sortKey.charAt(0) >= 'a' && sortKey.charAt(0) <= 'z')
				|| (sortKey.charAt(0) >= 'A' && sortKey.charAt(0) <= 'Z')) {
			return sortKey;
		}

		/*return String.valueOf(QuickAlphabeticBar.DEFAULT_INDEX_CHARACTER)
				+ sortKey;*/
		return  String.valueOf('#')+ sortKey;
	}
	
	/**
	 * key=id+phoneNumber
	 * */
	private String getSelectedContactsKey(Contacts contacts){
		if(null==contacts){
			return null;
		}
		
		return contacts.getId()+contacts.getPhoneNumber();
	}	
}
