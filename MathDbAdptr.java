package org.vkedco.mobappdev.stored_sqlite_query_app;

/*
 *********************************************************
 * Bugs to vladimir dot kulyukin at gmail dot com
 *********************************************************
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class MathDbAdptr {
	
	static final String LOGTAG     = MathDbAdptr.class.getSimpleName() + "_LOGTAG";
	static final String DB_NAME    = "math_info.db";
	static final int    DB_VERSION = 1;
	static final String MATH_TBL   = "mathematician";
	
	static final String SEPARATOR  = "=======================";
	static final String NEWLINE    = "\n";
	
	static final int RESEARCH_AREA_QUERY_NUMBER = 1;
	static final int COUNTRY_QUERY_NUMBER       = 2;
	
	// constants for mathematician table column names
	static final String MATH_TBL_ID_COL_NAME   = "ID";
	static final String MATH_TBL_FN_COL_NAME   = "FirstName";
	static final String MATH_TBL_LN_COL_NAME   = "LastName";
	static final String MATH_TBL_BY_COL_NAME   = "BirthYear";
	static final String MATH_TBL_DY_COL_NAME   = "DeathYear";
	static final String MATH_TBL_RA_COL_NAME   = "ResearchArea";
	static final String MATH_TBL_COUNTRY_COL_NAME = "Country";
	static final String MATH_TBL_WIKI_COL_NAME = "Wiki";
	
	// constants for mathematician table column numbers
	static final int MATH_TBL_ID_COL_NUM   = 0;
	static final int MATH_TBL_FN_COL_NUM   = 1;
	static final int MATH_TBL_LN_COL_NUM   = 2;
	static final int MATH_TBL_BY_COL_NUM   = 3;
	static final int MATH_TBL_DY_COL_NUM   = 4;
	static final int MATH_TBL_RA_COL_NUM   = 5;
	static final int MATH_TBL_COUNTRY_COL_NUM = 6;
	static final int MATH_TBL_WIKI_COL_NUM = 7;
	
	
	private SQLiteDatabase   	 mDb = null;
	private MathInfoDBOpenHelper mDbHelper = null;
	
	// bookInfoDBOpenHelper class creates the table in the database
	private static class MathInfoDBOpenHelper extends SQLiteOpenHelper {
		
		public MathInfoDBOpenHelper(Context context, String name, 
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}
		
		// table creation string constant
		static final String MATH_TBL_CREATE =
			"create table " + MATH_TBL + 
			" (" + 
			MATH_TBL_ID_COL_NAME    + " integer primary key autoincrement, " + 
			MATH_TBL_FN_COL_NAME    + " text not null, " + 
			MATH_TBL_LN_COL_NAME    + " text not null, " +
			MATH_TBL_BY_COL_NAME    + " integer not null, " + 
			MATH_TBL_DY_COL_NAME    + " integer not null, " +
			MATH_TBL_RA_COL_NAME    + " text not null, " +
			MATH_TBL_COUNTRY_COL_NAME    + " text not null, " +
			MATH_TBL_WIKI_COL_NAME  + " text not null " +
			");";
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d(LOGTAG, MATH_TBL_CREATE);
			db.execSQL(MATH_TBL_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, 
				int newVersion) {
			Log.d(LOGTAG, "Upgrading from version " +
					oldVersion + " to " +
					newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + MATH_TBL_CREATE);
			onCreate(db);
		}
	} // end of MathInfoDBOpenHelper class
	
	// initialize the mContext and the helper objects
	public MathDbAdptr(Context context) {
		mDbHelper = new MathInfoDBOpenHelper(context, DB_NAME, null, DB_VERSION);
	}
	
	void openReadDb() throws SQLiteException {
		try {
			mDb = mDbHelper.getReadableDatabase();
		}
		catch ( SQLiteException ex ) {
			Log.d(LOGTAG, ex.toString());
		}
	}
	
	void openWriteDb() throws SQLiteException {
		try {
			mDb = mDbHelper.getWritableDatabase();
		}
		catch ( SQLiteException ex ) {
			Log.d(LOGTAG, ex.toString());
		}
	}
	
	void closeDb() { if ( mDb != null ) mDb.close(); }
	
	// Strongly typed insertion method. Insert the book if and only if it is not already
	// in the database.
	long insertUniqueMathematicianRecord(Mathematician mathematician) {
		ContentValues mathRecord = new ContentValues();
		mathRecord.put(MathDbAdptr.MATH_TBL_FN_COL_NAME, mathematician.getFirstName());
		mathRecord.put(MathDbAdptr.MATH_TBL_LN_COL_NAME, mathematician.getLastName());
		mathRecord.put(MathDbAdptr.MATH_TBL_BY_COL_NAME, mathematician.getBirthYear());
		mathRecord.put(MathDbAdptr.MATH_TBL_DY_COL_NAME, mathematician.getDeathYear());
		mathRecord.put(MathDbAdptr.MATH_TBL_RA_COL_NAME, mathematician.getResearchArea());
		mathRecord.put(MathDbAdptr.MATH_TBL_COUNTRY_COL_NAME, mathematician.getCountry());
		mathRecord.put(MathDbAdptr.MATH_TBL_WIKI_COL_NAME, mathematician.getWikiURL());
		
		Cursor rslt = mDb.query(MATH_TBL, 
				new String[] {MATH_TBL_LN_COL_NAME}, 
				MATH_TBL_LN_COL_NAME + "=" + "\"" + mathematician.getLastName() + "\"", 
				null, null, null, null);
		long insertedRowIndex = -1;
		if ((rslt.getCount() == 0 || !rslt.moveToFirst()) ) {
			insertedRowIndex =  mDb.insertWithOnConflict(MATH_TBL, 
					null, 
					mathRecord, 
					SQLiteDatabase.CONFLICT_REPLACE);	
		}		
		
		// close the cursor and make it completely invalid
		rslt.close();
		Log.d(LOGTAG, "Inserted mathematician " + insertedRowIndex);
		return insertedRowIndex;
	}
	
	
	static final String RESEARCH_AREA_ORDER_BY = MATH_TBL_BY_COL_NAME + " DESC";
	static final String[] RESEARCH_AREA_TBL_COL_PROJECTIONS = 
		new String[]{ MATH_TBL_FN_COL_NAME, MATH_TBL_LN_COL_NAME, 
					  MATH_TBL_BY_COL_NAME, MATH_TBL_WIKI_COL_NAME };
	String retrieveMathematicianByResearchArea(String research_area) {
		Cursor rslt =
			mDb.query(MATH_TBL, // table name 
					MathDbAdptr.RESEARCH_AREA_TBL_COL_PROJECTIONS, // column names 
					MATH_TBL_RA_COL_NAME + "=" + "\"" + research_area + "\"", // where clause
					null,  // selection args
					null,  // group by
					null,  // having
					MathDbAdptr.RESEARCH_AREA_ORDER_BY); // order by
		
		StringBuilder buffer = new StringBuilder("");
		buffer.append(MathDbAdptr.SEPARATOR);
		buffer.append(MathDbAdptr.NEWLINE);
		
		if ( rslt.getCount() != 0 ) {
			rslt.moveToFirst();
			while ( rslt.isAfterLast() == false ) {
				String first_name = rslt.getString(rslt.getColumnIndex(MATH_TBL_FN_COL_NAME));
				String last_name = rslt.getString(rslt.getColumnIndex(MATH_TBL_LN_COL_NAME));
				int by = rslt.getInt(rslt.getColumnIndex(MATH_TBL_BY_COL_NAME));
				String wiki = rslt.getString(rslt.getColumnIndex(MATH_TBL_WIKI_COL_NAME));
				buffer.append("FirstName = " + first_name);
				buffer.append(MathDbAdptr.NEWLINE);
				buffer.append("LastName  = " + last_name);
				buffer.append(MathDbAdptr.NEWLINE);
				buffer.append("BirthYear = " + by);
				buffer.append(MathDbAdptr.NEWLINE);
				buffer.append("Wiki URL = " + wiki);
				buffer.append(MathDbAdptr.NEWLINE);
				buffer.append(MathDbAdptr.SEPARATOR);
				buffer.append(MathDbAdptr.NEWLINE);
				rslt.moveToNext();
			}
		}
		
		rslt.close();
		return buffer.toString();
	}
	
	static final String COUNTRY_ORDER_BY = MATH_TBL_BY_COL_NAME + " ASC";
	static final String[] COUNTRY_TBL_COL_PROJECTIONS = 
		new String[]{ MATH_TBL_FN_COL_NAME, MATH_TBL_LN_COL_NAME, 
					  MATH_TBL_BY_COL_NAME, MATH_TBL_WIKI_COL_NAME };
	String retrieveMathematicianByCountry(String country) {
		Cursor rslt =
			mDb.query(MATH_TBL, // table name 
					MathDbAdptr.RESEARCH_AREA_TBL_COL_PROJECTIONS, // column names 
					MATH_TBL_COUNTRY_COL_NAME + "=" + "\"" + country + "\"", // where clause
					null,  // selection args
					null,  // group by
					null,  // having
					MathDbAdptr.COUNTRY_ORDER_BY); // order by
		
		StringBuilder buffer = new StringBuilder("");
		buffer.append(MathDbAdptr.SEPARATOR);
		buffer.append(MathDbAdptr.NEWLINE);
		
		if ( rslt.getCount() != 0 ) {
			rslt.moveToFirst();
			while ( rslt.isAfterLast() == false ) {
				String first_name = rslt.getString(rslt.getColumnIndex(MATH_TBL_FN_COL_NAME));
				String last_name = rslt.getString(rslt.getColumnIndex(MATH_TBL_LN_COL_NAME));
				int by = rslt.getInt(rslt.getColumnIndex(MATH_TBL_BY_COL_NAME));
				String wiki = rslt.getString(rslt.getColumnIndex(MATH_TBL_WIKI_COL_NAME));
				buffer.append("FirstName = " + first_name);
				buffer.append(MathDbAdptr.NEWLINE);
				buffer.append("LastName  = " + last_name);
				buffer.append(MathDbAdptr.NEWLINE);
				buffer.append("BirthYear = " + by);
				buffer.append(MathDbAdptr.NEWLINE);
				buffer.append("Wiki URL = " + wiki);
				buffer.append(MathDbAdptr.NEWLINE);
				buffer.append(MathDbAdptr.SEPARATOR);
				buffer.append(MathDbAdptr.NEWLINE);
				rslt.moveToNext();
			}
		}
		
		rslt.close();
		return buffer.toString();
	}

}

