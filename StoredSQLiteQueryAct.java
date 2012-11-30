package org.vkedco.mobappdev.stored_sqlite_query_app;

/*
 *********************************************************
 * Bugs to vladimir dot kulyukin at gmail dot com
 *********************************************************
 */

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class StoredSQLiteQueryAct extends Activity {
	
	MathDbAdptr mDbAdptr = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stored_sqlite_query);
        mDbAdptr = new MathDbAdptr(this);
        MathDbPopulator.populateMathInfoDb(this, mDbAdptr);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_stored_sqlite_query, menu);
        return true;
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch ( item.getItemId() ) {
		case R.id.options_menu_query_01:
			startActivity(new Intent(this, ResearchAreaAct.class));
			break;
		case R.id.options_menu_query_02:
			startActivity(new Intent(this, CountryAct.class));
			break;
		}
		return true;
	}
    
    
}
