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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.*/
 
package edu.ucdenver.bios.glimmpseandroid.activity;


/*
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.glimmpseandroid.adapter.TutorialAdapter;


 * The Activity Class handles interaction for Tutorial screen. 
 
public class TutorialActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial);

        Resources res = getResources();
		 Fetch labels for the tabs 
		String[] labels = { res.getString(R.string.tutorial_solving_for),
				res.getString(R.string.tutorial_type_one_error),
				res.getString(R.string.tutorial_number_of_groups),
				res.getString(R.string.tutorial_relative_group_size),
				res.getString(R.string.tutorial_smallest_group_size),
				res.getString(R.string.tutorial_means_and_variance)
				};
		String[] description ={
				res.getString(R.string.tutorial_solving_for_description),
				res.getString(R.string.tutorial_type_one_error_description),
				res.getString(R.string.tutorial_number_of_groups_description),
				res.getString(R.string.tutorial_relative_group_size_description),
				res.getString(R.string.tutorial_smallest_group_size_description),
				res.getString(R.string.tutorial_means_and_variance_description)
		};
		
		//setListAdapter(new TutorialArrayAdapter(this,labels));
		
        ListView listView = (ListView) findViewById(R.id.tutorial_list);
        
        // First paramenter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        	android.R.layout.simple_list_item_1, labels);  
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
            	R.layout.tutorial_list_item, labels);  
        
        // Assign adapter to ListView
        listView.setAdapter(adapter);
		
		ListView list = (ListView)findViewById(R.id.tutorial_list);
		ListAdapter adapter = new ArrayAdapter<String>(
			this,
			R.layout.expandable_list_item,
			R.id.list_item_textView_tutorial,
			labels
		);
		list.setAdapter(
				new SlideExpandableListAdapter(
					adapter,
					R.id.expandable_toggle_button,
					R.id.expandable,
					R.id.tutorial_list_item_description,
					description
				)
			);  
    }
	
	private ListView myList;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial);

        Resources res = getResources();
		 		
		String[] description ={
				res.getString(R.string.tutorial_solving_for_description),
				res.getString(R.string.tutorial_type_one_error_description),
				res.getString(R.string.tutorial_number_of_groups_description),
				res.getString(R.string.tutorial_relative_group_size_description),
				res.getString(R.string.tutorial_smallest_group_size_description),
				res.getString(R.string.tutorial_means_and_variance_description)
		};
		
		String[] tutorialList = getResources().getStringArray(R.array.tutorial_list);
		
		//this.setListAdapter(new ArrayAdapter<String>(this, R.layout.expandable_list_item, R.id.list_item_textView_tutorial, tutorial_list));
		//this.setListAdapter(new ArrayAdapter<String>(this,R.layout.tutorial, R.id.list_item_textView_tutorial, tutorial_list));
		
		myList = (ListView)findViewById(R.id.tutorial_list_view);
		
		 
        //show the ListView on the screen
        // The adapter MyCustomAdapter is responsible for maintaining the data backing this list and for producing
        // a view to represent an item in that data set.
        myList.setAdapter(new TutorialAdapter(TutorialActivity.this,tutorialList));
    }
	
	
	*//**
	 * This method is called when the user clicks the device's Menu button the
	 * first time for this Activity. Android passes in a Menu object that is
	 * populated with items.
	 * 
	 * @param menu
	 *            the menu
	 * @return true, if successful
	 *//*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		//getMenuInflater().inflate(R.menu.common_botoom_menu, menu);		
		return true;
	}

	*//**
	 * This method is called when a menu item is selected. Android passes in the
	 * selected item. The switch statement in this method calls the appropriate
	 * method to perform the action the user chose.
	 * 
	 * @param item
	 *            the item
	 * @return true, if successful
	 *//*
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.menu_home:
				// finish current activity
				finish();
				return true;
			case R.id.menu_start:
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
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

}
*/
public class TutorialActivity{
    
}