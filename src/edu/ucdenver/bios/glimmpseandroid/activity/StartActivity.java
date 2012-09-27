/*
 * Mobile - Android, User Interface for the GLIMMPSE Software System.  Allows
 * users to perform power, sample size calculations. 
 * 
 * Copyright (C) 2010 Regents of the University of Colorado.  
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package edu.ucdenver.bios.glimmpseandroid.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import edu.ucdenver.bios.glimmpseandroid.R;

// TODO: Auto-generated Javadoc
/**
 * The Activity Class handles interaction for Start screen.
 * @author Uttara Sakhadeo
 */
public class StartActivity extends Activity {

	/**
	 * This method is called by Android when the Activity is first started. From
	 * the incoming Intent, it determines what kind of editing is desired, and
	 * then does it.
	 * 
	 * @param savedInstanceState
	 *            the saved instance state
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.design);			
	}

	/**
	 * This method is called when the user clicks the device's Menu button the
	 * first time for this Activity. Android passes in a Menu object that is
	 * populated with items.
	 * 
	 * @param menu
	 *            the menu
	 * @return true, if successful
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		//getMenuInflater().inflate(R.menu.common_botoom_menu, menu);		
		return true;
	}

	/**
	 * This method is called when a menu item is selected. Android passes in the
	 * selected item. The switch statement in this method calls the appropriate
	 * method to perform the action the user chose.
	 * 
	 * @param item
	 *            the item
	 * @return true, if successful
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.menu_home:
				// finish current activity
				finish();
				return true;
			/*case R.id.menu_start:
				return true;
			case R.id.menu_aboutus:
				// finish current activity
				finish();
				// Start AboutUs activity
				Intent aboutUsIntent = new Intent(getBaseContext(),
						LearnMoreActivity.class);
				startActivityForResult(aboutUsIntent, 0);
				return true;
			case R.id.menu_tutorial:
				// finish current activity
				finish();
				// Start Tutorial activity
				Intent tutorialIntent = new Intent(getBaseContext(),
						TutorialActivity.class);
				startActivityForResult(tutorialIntent, 0);
				return true;*/
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
