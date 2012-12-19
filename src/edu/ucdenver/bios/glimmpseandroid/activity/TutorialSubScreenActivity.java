/*
 * Mobile - Android, User Interface for the GLIMMPSE Software System.  Allows
 * users to perform power and sample size calculations. 
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
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.glimmpseandroid.adapter.GestureFilter;
import edu.ucdenver.bios.glimmpseandroid.adapter.GestureFilter.SimpleGestureListener;

// TODO: Auto-generated Javadoc
/**
 * The Class TutorialSubScreenActivity deals with the Tutorial sub-screens of
 * the GLIMMPSE LITE Application.
 * 
 * @author Uttara Sakhadeo
 * @version 1.0.0
 */
public class TutorialSubScreenActivity extends Activity implements
        OnClickListener, SimpleGestureListener {

    /** The detector. */
    private static GestureFilter detector;

    /** The Constant GETTING_STARTED_ROW. */
    public static final int GETTING_STARTED_ROW = 0;

    /** The Constant SOLVING_FOR_ROW. */
    public static final int SOLVING_FOR_ROW = 1;

    /** The Constant POWER_ROW. */
    public static final int POWER_ROW = 2;

    /** The Constant SAMPLE_SIZE_ROW. */
    public static final int SAMPLE_SIZE_ROW = 3;

    /** The Constant TYPE_I_ERROR_ROW. */
    public static final int TYPE_I_ERROR_ROW = 4;

    /** The Constant NUMBER_OF_GROUPS_ROW. */
    public static final int NUMBER_OF_GROUPS_ROW = 5;

    /** The Constant RELATIVE_GROUP_SIZE_ROW. */
    public static final int RELATIVE_GROUP_SIZE_ROW = 6;

    /** The Constant MEANS_VARIANCE_ROW. */
    public static final int MEANS_VARIANCE_ROW = 7;

    /** The Constant RESULTS_ROW. */
    public static final int RESULTS_ROW = 8;
    
    /** The menu flag. */
    //private static boolean menuFlag = false;

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
        final Window window = getWindow();
        boolean useTitleFeature = false;
        detector = new GestureFilter(this, this);
        /*
         * If the window has a container, then we are not free to request window
         * features.
         */
        if (window.getContainer() == null) {
            useTitleFeature = window
                    .requestFeature(Window.FEATURE_CUSTOM_TITLE);
        }
        setContentView(R.layout.tutorial_sub_screen);
        if (useTitleFeature) {
            window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);

            Button homeButton = (Button) findViewById(R.id.home_button);
            homeButton.setText(getResources()
                    .getString(R.string.title_tutorial));
            homeButton.setOnClickListener(this);
            /* Setting Window Title. */
            TextView title = (TextView) findViewById(R.id.window_title);

            TextView description = (TextView) findViewById(R.id.description_textView_tutorial_sub_screen);
            description.setMovementMethod(new ScrollingMovementMethod());
            

            Bundle extras = this.getIntent().getExtras();
            if (extras != null) {
                int screenId = extras.getInt("sub_screen");
                String subTitle = null;
                String data = null;
                switch (screenId) {
                case GETTING_STARTED_ROW:
                    subTitle = getResources().getString(
                            R.string.tutorial_getting_started);
                    data = getResources().getString(
                            R.string.description_tutorial_getting_started);
                    break;
                case SOLVING_FOR_ROW:
                    subTitle = getResources().getString(
                            R.string.tutorial_solving_for);
                    data = getResources().getString(
                            R.string.description_tutorial_solving_for);
                    break;
                case POWER_ROW:
                    subTitle = getResources()
                            .getString(R.string.tutorial_power);
                    data = getResources().getString(
                            R.string.description_tutorial_power);
                    break;
                case SAMPLE_SIZE_ROW:
                    subTitle = getResources().getString(
                            R.string.tutorial_smallest_group_size);
                    data = getResources().getString(
                            R.string.description_tutorial_smallest_group);
                    break;
                case TYPE_I_ERROR_ROW:
                    subTitle = getResources().getString(
                            R.string.tutorial_type_one_error);
                    data = getResources().getString(
                            R.string.description_tutorial_alpha);
                    break;
                case NUMBER_OF_GROUPS_ROW:
                    subTitle = getResources().getString(
                            R.string.tutorial_number_of_groups);
                    data = getResources().getString(
                            R.string.description_tutorial_groups);
                    break;
                case RELATIVE_GROUP_SIZE_ROW:
                    subTitle = getResources().getString(
                            R.string.tutorial_relative_group_size);
                    data = getResources().getString(
                            R.string.description_tutorial_relative_size);
                    break;
                case MEANS_VARIANCE_ROW:
                    subTitle = getResources().getString(
                            R.string.tutorial_means_and_variance);
                    data = getResources().getString(
                            R.string.description_tutorial_mean_variance);
                    break;
                case RESULTS_ROW:
                    subTitle = getResources().getString(
                            R.string.tutorial_results);
                    data = getResources().getString(
                            R.string.description_tutorial_results);
                    break;
                }
                title.setText(subTitle);
                description.setText(Html.fromHtml(data));

            } else {

            }

        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View v) {
        finish();
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#dispatchTouchEvent(android.view.MotionEvent)
     */
    public boolean dispatchTouchEvent(MotionEvent me) {
        if (detector != null){
            detector.onTouchEvent(me);
            return super.dispatchTouchEvent(me);
        }        
        return false;        
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.ucdenver.bios.glimmpseandroid.adapter.GestureFilter.SimpleGestureListener
     * #onSwipe(int)
     */
    public void onSwipe(int direction) {
        switch (direction) {

        case GestureFilter.SWIPE_RIGHT:
            //menuFlag = false;
            finish();
            break;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.ucdenver.bios.glimmpseandroid.adapter.GestureFilter.SimpleGestureListener
     * #onDoubleTap()
     */
    public void onDoubleTap() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onTouchEvent(android.view.MotionEvent)
     */
    /*public boolean onTouchEvent(MotionEvent event) {
        if(!menuFlag){
            menuFlag = true;
            this.openOptionsMenu();
            return true;
        }
        else{
            menuFlag = false;
            return false;
        }
    }*/

    /*
     * (non-Javadoc)
     * 
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
     * @param item
     *            the item
     * @return true, if successful
     */
    private boolean menuSelection(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menu_tutorial:
            finish();
            return true;
        case R.id.menu_start:
            finish();
            Intent tabIntent = new Intent(this.getBaseContext(),
                    TabViewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("tab_id", 1);
            tabIntent.putExtras(bundle);
            tabIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(tabIntent);
            return true;
        case R.id.menu_aboutus:
            finish();
            tabIntent = new Intent(this.getBaseContext(), TabViewActivity.class);
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

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    public boolean onOptionsItemSelected(MenuItem item) { // Handle
        return menuSelection(item);
    }
}
