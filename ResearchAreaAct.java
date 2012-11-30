package org.vkedco.mobappdev.stored_sqlite_query_app;

/*
 *********************************************************
 * Bugs to vladimir dot kulyukin at gmail dot com
 *********************************************************
 */

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ResearchAreaAct extends ListActivity {
	
	ListActivity mThisAct = null;
	Resources mRes = null;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mThisAct = this;
		mRes = getResources();

		ArrayAdapter<CharSequence> adptr
			= ArrayAdapter.createFromResource(this, 
					R.array.research_areas,
					android.R.layout.simple_list_item_1);
		setListAdapter(adptr);
		
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View child,
					int position, long id) {
				Intent i = new Intent(mThisAct, QueryResultsAct.class);
				i.putExtra(mRes.getString(R.string.research_area_key), 
						((TextView) child).getText().toString());
				i.putExtra(mRes.getString(R.string.query_number_key), 
						MathDbAdptr.RESEARCH_AREA_QUERY_NUMBER);
				startActivity(i);
			}
		});
	}

}
