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
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import edu.ucdenver.bios.glimmpseandroid.adapter.GestureFilter;
import edu.ucdenver.bios.glimmpseandroid.adapter.PowerListAdapter;
import edu.ucdenver.bios.glimmpseandroid.adapter.GestureFilter.SimpleGestureListener;

public class PowerActivity extends Activity implements OnClickListener, SimpleGestureListener{
    private ListView powerListView;
    private static EditText valueText;
    static Drawable img;
    private static GestureFilter detector;
        
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
                    valueText.setText("");
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
            
            public void onClick(View v) {
                EditText valueText = (EditText) findViewById(R.id.power_value);
                valueText.setText("");
                powerListView.setAdapter(new PowerListAdapter(PowerActivity.this, -1.0));
                valueText.requestFocusFromTouch();
                
            }
        });
        
        powerListView.setAdapter(new PowerListAdapter(PowerActivity.this, null));      
    }
    
    private void addValue(){
        valueText = (EditText) findViewById(R.id.power_value);        
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(valueText.getWindowToken(), 0);
        //imm.hideSoftInputFromWindow(valueText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);        
        String data = String.valueOf(valueText.getText());
        if(data != null && !data.isEmpty()) {
            if(!data.equals(".")){               
                Double value = Double.parseDouble(data);
                if(value <= 1) {
                    powerListView.setAdapter(new PowerListAdapter(PowerActivity.this, value));
                }                
            }
            valueText.setText("");
            valueText.requestFocusFromTouch();
            //
        }
    }
       
    
    public void onClick(View v) {
        finish();
     }
    
    public boolean dispatchTouchEvent(MotionEvent me){
        //System.out.println("dispatchTouchEvent");
        detector.onTouchEvent(me);
       return super.dispatchTouchEvent(me);
      }

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

    public void onDoubleTap() {
        // TODO Auto-generated method stub
        
    }
}
