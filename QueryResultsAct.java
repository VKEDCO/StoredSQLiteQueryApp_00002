package org.vkedco.mobappdev.stored_sqlite_query_app;

/*
 *********************************************************
 * Bugs to vladimir dot kulyukin at gmail dot com
 *********************************************************
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class QueryResultsAct extends Activity {
	
	EditText mEdTxtQueryOutput = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_results_layout);
	
		Intent i = getIntent();
		int qn = i.getIntExtra(getResources().getString(R.string.query_number_key), 0);
		
		if ( qn > 0 ) {
			MathDbAdptr dbAdptr = new MathDbAdptr(getApplicationContext());
			dbAdptr.openReadDb();
			String query_output = null;
			switch ( qn ) {
			case MathDbAdptr.RESEARCH_AREA_QUERY_NUMBER: 
				String ra = i.getStringExtra(getResources().getString(R.string.research_area_key));
				query_output = dbAdptr.retrieveMathematicianByResearchArea(ra); 
				break;
			case MathDbAdptr.COUNTRY_QUERY_NUMBER:
				String cn = i.getStringExtra(getResources().getString(R.string.country_key));
				query_output = dbAdptr.retrieveMathematicianByCountry(cn);
			default: break;
			}
			EditText mEdTxtQueryOutput = (EditText) findViewById(R.id.ed_txt_query_output);
			mEdTxtQueryOutput.setText(query_output);
			dbAdptr.closeDb();
		}
	}
}
