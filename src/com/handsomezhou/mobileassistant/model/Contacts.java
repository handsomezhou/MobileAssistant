package com.handsomezhou.mobileassistant.model;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import com.pinyinsearch.model.PinyinUnit;

public class Contacts extends BaseContacts{
	public enum SearchByType {
		SearchByNull, SearchByName, SearchByPhoneNumber,
	}
	private String mSortKey; // as the sort key word

	private List<PinyinUnit> mNamePinyinUnits; // save the mName converted to Pinyin characters.

	private SearchByType mSearchByType; // Used to save the type of search
	private StringBuffer mMatchKeywords; // Used to save the type of Match Keywords.(name or phoneNumber)
	
	//is multiple number:mMultipleNumbersContacts.size()>0,non multiple number:mMultipleNumbersContacts.size()=0£»
	private List<Contacts> mMultipleNumbersContacts;//save the contacts information who has multiple numbers.
	
	public Contacts() {
		super();
		setMultipleNumbersContacts(new ArrayList<Contacts>());
		setMatchKeywords(new StringBuffer());
		getMatchKeywords().delete(0, getMatchKeywords().length());
	}
	
	public Contacts(String id ,String name, String phoneNumber) {
		// super();
		setId(id);
		setName(name);
		setPhoneNumber(phoneNumber);
		setNamePinyinUnits(new ArrayList<PinyinUnit>());
		setSearchByType(SearchByType.SearchByNull);
		
		setMatchKeywords(new StringBuffer());
		getMatchKeywords().delete(0, getMatchKeywords().length());
		
		setMultipleNumbersContacts(new ArrayList<Contacts>());
	}
	
	

	private static Comparator<Object> mChineseComparator = Collator.getInstance(Locale.CHINA);
	
	public static Comparator<Contacts> mDesComparator = new Comparator<Contacts>() {

		@Override
		public int compare(Contacts lhs, Contacts rhs) {
		
			return mChineseComparator.compare(rhs.mSortKey, lhs.mSortKey);
		}
	};

	public static Comparator<Contacts> mAscComparator = new Comparator<Contacts>() {

		@Override
		public int compare(Contacts lhs, Contacts rhs) {
			return mChineseComparator.compare(lhs.mSortKey, rhs.mSortKey);
		}
	};
	
	public String getSortKey() {
		return mSortKey;
	}


	public void setSortKey(String sortKey) {
		mSortKey = sortKey;
	}


	public List<PinyinUnit> getNamePinyinUnits() {
		return mNamePinyinUnits;
	}


	public void setNamePinyinUnits(List<PinyinUnit> namePinyinUnits) {
		mNamePinyinUnits = namePinyinUnits;
	}


	public SearchByType getSearchByType() {
		return mSearchByType;
	}


	public void setSearchByType(SearchByType searchByType) {
		mSearchByType = searchByType;
	}


	public StringBuffer getMatchKeywords() {
		return mMatchKeywords;
	}


	public void setMatchKeywords(StringBuffer matchKeywords) {
		mMatchKeywords = matchKeywords;
	}
	
	public void setMatchKeywords(String matchKeywords) {
		mMatchKeywords.delete(0, mMatchKeywords.length());
		mMatchKeywords.append(matchKeywords);
	}

	public void clearMatchKeywords() {
		mMatchKeywords.delete(0, mMatchKeywords.length());
	}


	public List<Contacts> getMultipleNumbersContacts() {
		return mMultipleNumbersContacts;
	}
	
	public void setMultipleNumbersContacts(List<Contacts> multipleNumbersContacts) {
		mMultipleNumbersContacts = multipleNumbersContacts;
	}
	
	public void addPhoneNumber(String phoneNumber){
		if(getPhoneNumber().equals(phoneNumber)){
			return;
		}
		
		int i=0;
		for (i = 0; i < getMultipleNumbersContacts().size(); i++) {
			if (getMultipleNumbersContacts().get(i).getPhoneNumber().equals(phoneNumber)) {
				break;
			}
		}
		
		if (i >= getMultipleNumbersContacts().size()) {
			Contacts cs=new Contacts(getId(), getName(), phoneNumber);
			cs.setSortKey(mSortKey);
			cs.setNamePinyinUnits(mNamePinyinUnits);// not deep copy
		
			mMultipleNumbersContacts.add(cs);
			
		}
		
		return;
	}
}
