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
package edu.ucdenver.bios.glimmpseandroid.activity.design;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.glimmpseandroid.activity.TabViewActivity;
import edu.ucdenver.bios.glimmpseandroid.adapter.GestureFilter;
import edu.ucdenver.bios.glimmpseandroid.adapter.GestureFilter.SimpleGestureListener;
import edu.ucdenver.bios.glimmpseandroid.application.StuyDesignContext;
// TODO: Auto-generated Javadoc
/*
 * @author Uttara Sakhadeo
 */
/**
 * The Class GroupCountActivity.
 */
public class GroupCountActivity extends Activity implements OnClickListener, SimpleGestureListener {
	
	/** The seekbar. */
	static SeekBar seekbar;
	
	/** The value. */
	static TextView value;
	
	/** The groups. */
	int groups;		
	
	/** The stuy design context. */
	static StuyDesignContext stuyDesignContext = StuyDesignContext.getInstance();
	
	/** The detector. */
	private static GestureFilter detector;

	/**
     * This method is called by Android when the Activity is first started. From
     * the incoming Intent, it determines what kind of editing is desired, and
     * then does it.
     * 
     * @param savedInstanceState
     *            the saved instance state
     */
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		final Window window = getWindow();
		boolean useTitleFeature = false;
		detector = new GestureFilter(this,this);
		// If the window has a container, then we are not free
		// to request window features.
		if (window.getContainer() == null) {
			useTitleFeature = window
					.requestFeature(Window.FEATURE_CUSTOM_TITLE);
		}
		setContentView(R.layout.design_number_of_groups);

		if (useTitleFeature) {
			window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		}
		Button homeButton = (Button) findViewById(R.id.home_button);
		homeButton.setText(getResources().getString(R.string.title_design));
		homeButton.setOnClickListener(this);

		/*Button back = (Button) findViewById(R.id.back_button);
		back.setOnClickListener(this);*/

		TextView title = (TextView) findViewById(R.id.window_title);
		title.setText(getResources().getString(R.string.title_groups));

		value = (TextView) findViewById(R.id.see_progress);
		seekbar = (SeekBar) findViewById(R.id.seekbar);

		/*
		 * Bundle extras = this.getIntent().getExtras(); if(extras != null) {
		 * if(extras.containsKey("groups")) { groups = extras.getInt("groups");
		 * seekbar.setProgress(groups-2);
		 * value.setText(Integer.toString(groups)); } }
		 */
		if(stuyDesignContext.getGroups() == 0) {
		    stuyDesignContext.setDefaultGroups();
		}
		groups = stuyDesignContext.getGroups();
		seekbar.setProgress(groups
				- Integer.parseInt(getResources().getString(
						R.string.default_groups)));
		value.setText(Integer.toString(groups));

		/*
		 * List<BetweenParticipantFactor> list = new
		 * ArrayList<BetweenParticipantFactor>(2); int i=0; while(i<2){
		 * list.add(new BetweenParticipantFactor()); i++; } StudyDesign sd =
		 * GlobalVariables.getInstance().getStudyDesign();
		 * sd.setBetweenParticipantFactorList(list);
		 */

		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				groups = progress
						+ Integer.parseInt(getBaseContext().getString(
								R.string.default_groups));
				value.setText(Integer.toString(groups));
				// GlobalVariableApplication.globalVariables.getPowerResult().set				
				
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}

			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}
		});
	}	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
         }
         return super.onKeyDown(keyCode, event);
	}
	
	/*// Called at the end of full lifetime.
    @Override
    protected void onDestroy() {       
       
       if(seekbar != null)
           seekbar = null;
                
        if(value != null)
            value = null;
        
        if(stuyDesignContext != null)
            stuyDesignContext = null;
        
        if(detector != null)
            detector = null;
        
        System.gc();
        
        super.onDestroy();
    }*/
	
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public void onClick(View v) {
		/*
		 * // TODO Auto-generated method stub Bundle bundle = new Bundle();
		 * bundle.putInt("design_list_item", 2); value = (TextView)
		 * findViewById(R.id.see_progress); bundle.putInt("group_count",
		 * Integer.parseInt(value.getText().toString())); Intent result=new
		 * Intent(GroupCountActivity.this,DesignListAdapter.class); //Intent
		 * result=new Intent(); result.putExtras(bundle); setResult(0, result);
		 */
	    stuyDesignContext.setGroups(groups);			
		finish();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#dispatchTouchEvent(android.view.MotionEvent)
	 */
	public boolean dispatchTouchEvent(MotionEvent me){
        //System.out.println("dispatchTouchEvent");
        detector.onTouchEvent(me);
       return super.dispatchTouchEvent(me);
      }


	/* (non-Javadoc)
	 * @see edu.ucdenver.bios.glimmpseandroid.adapter.GestureFilter.SimpleGestureListener#onSwipe(int)
	 */
	public void onSwipe(int direction) {
        // TODO Auto-generated method stub
        /*if(direction == 3)
            finish();*/
        String str = "";
             
            switch (direction) {
             
            case GestureFilter.SWIPE_RIGHT : str = "Swipe Right";
                stuyDesignContext.setGroups(groups);    
                finish();
                 break;
            /*case GestureFilter.SWIPE_LEFT :  str = "Swipe Left";
                                                           break;
            case GestureFilter.SWIPE_DOWN :  str = "Swipe Down";
                                                           break;
            case GestureFilter.SWIPE_UP :    str = "Swipe Up";
                                                           break;*/
                                                      
            }
             //Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    /* (non-Javadoc)
     * @see edu.ucdenver.bios.glimmpseandroid.adapter.GestureFilter.SimpleGestureListener#onDoubleTap()
     */
    public void onDoubleTap() {
        // TODO Auto-generated method stub
        
    }
        
    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_screen_menu, menu);
        return true;
    }
    
    /**
     * Menu selection.
     *
     * @param item the item
     * @return true, if successful
     */
    private boolean menuSelection(MenuItem item){
        switch (item.getItemId()) { 
        case R.id.menu_tutorial: 
            stuyDesignContext.setGroups(groups);
            finish();
            Intent tabIntent = new Intent(this.getBaseContext(),
                    TabViewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("tab_id", 0);
            tabIntent.putExtras(bundle);
            tabIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(tabIntent);
            return true;
        case R.id.menu_start:           
            stuyDesignContext.setGroups(groups);
            finish();           
            return true;
        case R.id.menu_aboutus:             
            stuyDesignContext.setGroups(groups);
            finish();
            tabIntent = new Intent(this.getBaseContext(),
                    TabViewActivity.class);
            bundle = new Bundle();
            bundle.putInt("tab_id", 2);
            tabIntent.putExtras(bundle);
            tabIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(tabIntent);
            return true;
        default: 
            return super.onOptionsItemSelected(item); 
            }
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    public boolean onOptionsItemSelected(MenuItem item) { // Handle
         return menuSelection(item);
        }

}