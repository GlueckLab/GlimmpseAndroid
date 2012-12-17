/*
 * Mobile - Android, User Interface for the GLIMMPSE Software System.  Allows
 * users to perform power= and sample size calculations. 
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
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.glimmpseandroid.activity.TabViewActivity;
import edu.ucdenver.bios.glimmpseandroid.adapter.GestureFilter;
import edu.ucdenver.bios.glimmpseandroid.adapter.GestureFilter.SimpleGestureListener;
import edu.ucdenver.bios.glimmpseandroid.adapter.SampleSizeAdapter;

// TODO: Auto-generated Javadoc
/**
 * The Class SampleSizeActivit deals with the 'Smallest Group Size' screen of
 * the GLIMMPSE LITE Application.
 * 
 * @author Uttara Sakhadeo
 * @version 1.0.0
 */
public class SampleSizeActivity extends Activity implements OnClickListener,
        SimpleGestureListener {

    /** The sample size list view. */
    private ListView sampleSizeListView;

    /** The value text. */
    private EditText valueText;

    /** The img. */
    private Drawable img;

    /** The detector. */
    private GestureFilter detector;

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
        detector = new GestureFilter(this, this);
        /*
         * If the window has a container, then we are not free to request window
         * features.
         */
        if (window.getContainer() == null) {
            useTitleFeature = window
                    .requestFeature(Window.FEATURE_CUSTOM_TITLE);
        }
        setContentView(R.layout.design_sample_size);

        if (useTitleFeature) {
            window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
        }

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float density = metrics.density;
        int measurement = (int) (density * 20);

        img = getResources().getDrawable(R.drawable.clear_button);
        img.setBounds(0, 0, measurement, measurement);

        TextView title = (TextView) findViewById(R.id.window_title);
        title.setText(getResources().getString(
                R.string.title_smallest_group_size));

        Button homeButton = (Button) findViewById(R.id.home_button);
        homeButton.setText(getResources().getString(R.string.title_design));
        homeButton.setOnClickListener(this);

        sampleSizeListPopulate();

    }

    /**
     * Sample size list populate.
     */
    private void sampleSizeListPopulate() {
        sampleSizeListView = (ListView) findViewById(R.id.sample_size_list_view);
        View header1 = getLayoutInflater().inflate(
                R.layout.design_sample_size_list_header, null, false);
        if (sampleSizeListView.getHeaderViewsCount() == 0) {
            sampleSizeListView.addHeaderView(header1);
        }

        valueText = (EditText) findViewById(R.id.sample_size_value);

        valueText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
                String data = String.valueOf(s);
                valueText.setCompoundDrawables(null, null, img, null);
                if (data == null || data.equals("") || data.isEmpty()) {
                    valueText.setCompoundDrawables(null, null, null, null);
                }
            }
        });

        valueText.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getX() > valueText.getWidth()
                        - img.getIntrinsicWidth() - 10) {
                    valueText.setText("");
                    valueText.requestFocusFromTouch();
                }
                return false;
            }

        });

        Button addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                addValue();
            }
        });

        Button clearAllButton = (Button) findViewById(R.id.delete_all_button);
        clearAllButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(valueText.getWindowToken(), 0);
                EditText valueText = (EditText) findViewById(R.id.sample_size_value);
                valueText.setText("");
                sampleSizeListView.setAdapter(new SampleSizeAdapter(
                        SampleSizeActivity.this, -1));
                valueText.requestFocusFromTouch();
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        });

        sampleSizeListView.setAdapter(new SampleSizeAdapter(
                SampleSizeActivity.this, null));
    }

    /**
     * Adds the value.
     */
    private void addValue() {
        valueText = (EditText) findViewById(R.id.sample_size_value);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(valueText.getWindowToken(), 0);
        String data = String.valueOf(valueText.getText());
        if (data != null && !data.isEmpty()) {
            int value = Integer.parseInt(data);
            sampleSizeListView.setAdapter(new SampleSizeAdapter(
                    SampleSizeActivity.this, value));
            valueText.setText("");
            valueText.requestFocusFromTouch();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View v) {
        addValue();
        finish();
        // onExit();
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#dispatchTouchEvent(android.view.MotionEvent)
     */
    public boolean dispatchTouchEvent(MotionEvent me) {
        detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
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
            addValue();
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
     * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            addValue();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

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
            addValue();
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
            addValue();
            finish();
            return true;
        case R.id.menu_aboutus:
            addValue();
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
