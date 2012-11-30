package org.vkedco.mobappdev.stored_sqlite_query_app;

/*
 *********************************************************
 * Bugs to vladimir dot kulyukin at gmail dot com
 *********************************************************
 */

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

public class MathDbPopulator {

	static final String LOGTAG = MathDbPopulator.class.getSimpleName()
			+ "_LOGTAG";
	public static final String XML_ENTRY_SEPARATOR = ";";
	
	public static void populateMathInfoDb(Context cntxt, MathDbAdptr dbAdptr) {
		dbAdptr.openWriteDb();

		String[] math_table = getXMLTableSpecs(cntxt, R.array.mathematician_table);

		//<item>Muhammad;al-Kwarizmi;708;850;algebra;Persia;http://en.wikipedia.org/wiki/Muhammad_ibn_Musa_al-Khwarizmi</item>
		String[] math_row_parts;
		for (String math_row : math_table) {
			math_row_parts = math_row.trim().split(XML_ENTRY_SEPARATOR);
			Log.d(LOGTAG, math_row);

			dbAdptr
				.insertUniqueMathematicianRecord(
					MathDbPopulator
						.createMathematicianObject(
								math_row_parts[0], // first name
								math_row_parts[1], // last name
								math_row_parts[2], // birth year
								math_row_parts[3], // death year
								math_row_parts[4], // research area
								math_row_parts[5], // country
								math_row_parts[6]  // wiki
						)
				);
		}

		dbAdptr.closeDb();
	}

	public static String[] getXMLTableSpecs(Context cntxt, int table_name) {
		Resources mRes = cntxt.getResources();
		switch (table_name) {
		case R.array.mathematician_table:
			return mRes.getStringArray(R.array.mathematician_table);
		default:
			return null;
		}
	}

	static Mathematician createMathematicianObject(
			String fn,      // first name
			String ln,      // last name
			String by,      // birth year
			String dy,      // death year
			String ra,      // research area
			String country, // country
			String wiki)    // wiki
	{
		return new Mathematician(fn, 
				ln, 
				Integer.parseInt(by), 
				Integer.parseInt(dy), 
				ra, 
				country, 
				wiki);
	}

}

