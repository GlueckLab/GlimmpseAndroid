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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.Toast;
import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.glimmpseandroid.activity.TabViewActivity;
import edu.ucdenver.bios.glimmpseandroid.adapter.GestureFilter;
import edu.ucdenver.bios.glimmpseandroid.adapter.GestureFilter.SimpleGestureListener;
import edu.ucdenver.bios.glimmpseandroid.adapter.PowerListAdapter;

// TODO: Auto-generated Javadoc
/**
 * The Class PowerActivity.
 * @author Uttara Sakhadeo
 */
public class PowerActivity extends Activity implements OnClickListener, SimpleGestureListener{
    
    /** The power list view. */
    private ListView powerListView;
    
    /** The value text. */
    private static EditText valueText;
    
    /** The img. */
    static Drawable img;
    
    /** The detector. */
    private static GestureFilter detector;
        
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
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
        setContentView(R.layout.design_power);

        if (useTitleFeature) {
            window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
        }
        
        img = getResources().getDrawable( R.drawable.clear_button );
        img.setBounds( 0, 0, 32, 32 );
        
        TextView title = (TextView) findViewById(R.id.window_title);
        title.setText(getResources().getString(R.string.title_power));
        
        Button homeButton = (Button) findViewById(R.id.home_button);
        homeButton.setText(getResources().getString(R.string.title_design));
        homeButton.setOnClickListener(this);
        
        /*Button resetButton = (Button) findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new OnClickListener() {
            
            public void onClick(View v) {
                // TODO Auto-generated method stub
                StuyDesignContext.getInstance().setDefaultRelativeGroupSize();
                powerListPopulate();
            }
        });*/                          

        /*Button back = (Button) findViewById(R.id.back_button);
        back.setOnClickListener(this);*/                       
        
        powerListPopulate();
        
    }              
    
    private void clearText(){
        valueText.setText("");   
        valueText.requestFocusFromTouch();
    }
    
    /**
     * Power list populate.
     */
    private void powerListPopulate(){
        powerListView = (ListView)findViewById(R.id.power_list_view);
        View header1 = getLayoutInflater().inflate(
                R.layout.design_power_list_header, null, false);
        if (powerListView.getHeaderViewsCount() == 0)
            powerListView.addHeaderView(header1);
        
        valueText = (EditText) findViewById(R.id.power_value);  
        
        valueText.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
                // TODO Auto-generated method stub
                
            }
            
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                String data = String.valueOf(s);
                valueText.setCompoundDrawables( null, null, img, null );  
                if(data == null || data.equals("") || data.isEmpty()) {
                    valueText.setCompoundDrawables( null, null, null, null );            
                }
            }
        });       

        
        valueText.setOnTouchListener(new OnTouchListener() {
            
            public boolean onTouch(View arg0, MotionEvent arg1) {
                //valueText.setCompoundDrawables( null, null, img, null );
                if (arg1.getX() > valueText.getWidth()
                - img.getIntrinsicWidth() - 10) {
                    /*valueText.setText("");   
                    valueText.requestFocusFromTouch();*/
                    clearText();
                }
            return false;
            }
    
        });                  
                 
        Button addButton = (Button) findViewById(R.id.power_add_button);
        addButton.setOnClickListener(new OnClickListener() {            
            public void onClick(View v) {                
                addValue();
            }
        });
        
        /*Button addButtonExtra = (Button) findViewById(R.id.add_button_extra);
        addButtonExtra.setOnClickListener(new OnClickListener() {            
            public void onClick(View v) {
                addValue();   
            }
        });*/
        
        /*Button clearButton = (Button) findViewById(R.id.clear_button);
        clearButton.setOnClickListener(new OnClickListener() {
            
            public void onClick(View v) {
                EditText valueText = (EditText) findViewById(R.id.power_value);
                valueText.setText("");   
                valueText.requestFocusFromTouch();
            }
        });*/
        
        Button clearAllButton = (Button) findViewById(R.id.delete_all_button);
        clearAllButton.setOnClickListener(new OnClickListener() {
            
            @SuppressLint("NewApi")
            public void onClick(View v) {
                valueText = (EditText) findViewById(R.id.power_value);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);    
                //System.out.println("inputmethod manager : "+imm.getLastInputMethodSubtype());                
                imm.hideSoftInputFromWindow(valueText.getWindowToken(), 0);                
                powerListView.setAdapter(new PowerListAdapter(PowerActivity.this, -1.0));
                /*valueText.setText("");                
                valueText.requestFocusFromTouch();  */
                clearText();
            }
        });
        
        powerListView.setAdapter(new PowerListAdapter(PowerActivity.this, null));      
    }
    
    /**
     * Adds the value.
     */
    private void addValue(){
        valueText = (EditText) findViewById(R.id.power_value);        
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(valueText.getWindowToken(), 0);
        //imm.hideSoftInputFromWindow(valueText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);        
        String data = String.valueOf(valueText.getText());
        System.out.println("Data : "+data);
        if(data != null && !data.isEmpty()) {
            if(!data.equals(".")){               
                Double value = Double.parseDouble(data);
                if(value <= 1) {                    
                    powerListView.setAdapter(new PowerListAdapter(PowerActivity.this, value));
                }  
                else{
                    //powerListView.setAdapter(new PowerListAdapter(PowerActivity.this, value));
                    (Toast.makeText(getBaseContext(),
                            "Please type a decimal number in the range of 0 to 1 !!!",
                            Toast.LENGTH_SHORT)).show();
                }
            }
            /*valueText.setText("");
            valueText.requestFocusFromTouch();*/
            clearText();
            //
        }
    }
       
    
    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View v) {
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
                addValue();
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
    
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_screen_menu, menu);
        return true;
    }
    
    private boolean menuSelection(MenuItem item){
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
    
    public boolean onOptionsItemSelected(MenuItem item) { // Handle
         return menuSelection(item);
        }
}
