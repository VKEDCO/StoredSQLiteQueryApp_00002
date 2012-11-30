package org.vkedco.mobappdev.stored_sqlite_query_app;

/*
 *********************************************************
 * Bugs to vladimir dot kulyukin at gmail dot com
 *********************************************************
 */

public class Mathematician {
	
	String mFirstName;
	String mLastName;
	int mBirthYear;
	int mDeathYear;
	String mResearchArea;
	String mCountry;
	String mWikiURL;
	
	public Mathematician(String fn, String ln, int by, int dy, String ra, 
			String country, String wiki) {
		mFirstName = fn;
		mLastName  = ln;
		mBirthYear = by;
		mDeathYear = dy;
		mResearchArea = ra;
		mCountry = country;
		mWikiURL = wiki;
	}
	
	String getFirstName() { return mFirstName; }
	String getLastName() { return mLastName; }
	int getBirthYear() { return mBirthYear; }
	int getDeathYear() { return mDeathYear; }
	String getResearchArea() { return mResearchArea; }
	String getWikiURL() { return mWikiURL; }
	String getCountry() { return mCountry; }
}

