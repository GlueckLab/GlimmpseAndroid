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

import java.text.DecimalFormat;
import java.text.Format;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.glimmpseandroid.adapter.GestureFilter;
import edu.ucdenver.bios.glimmpseandroid.adapter.GestureFilter.SimpleGestureListener;
import edu.ucdenver.bios.glimmpseandroid.application.GlobalVariables;
import edu.ucdenver.bios.glimmpseandroid.application.StuyDesignContext;

public class TypeIErrorActivity extends Activity implements OnClickListener, TextWatcher, SimpleGestureListener {
	static EditText value;
	static Double alpha;
	static Drawable img;
	static  DecimalFormat format = new DecimalFormat(".##");
	static StuyDesignContext stuyDesignContext = StuyDesignContext.getInstance();
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
		setContentView(R.layout.design_type_one_error);

		if (useTitleFeature) {
			window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		}
		
		img = getResources().getDrawable( R.drawable.clear_button );
        img.setBounds( 0, 0, 32, 32 );
                
		Button homeButton = (Button) findViewById(R.id.home_button);
		 homeButton.setText(getResources().getString(R.string.title_design));
		homeButton.setOnClickListener(this);

		/*Button back = (Button) findViewById(R.id.back_button);
		back.setOnClickListener(this);*/

		TextView title = (TextView) findViewById(R.id.window_title);
		title.setText(getResources().getString(R.string.title_type_i_error));

		value = (EditText) findViewById(R.id.alpha);
		value.addTextChangedListener(this);
		value.setOnTouchListener(new OnTouchListener() {
            
            public boolean onTouch(View arg0, MotionEvent arg1) {               
            if (arg1.getX() > value.getWidth()
            - img.getIntrinsicWidth() - 10) {
                value.setText("0.");
                resetText();
            }
            return false;
            }
    
        });
		
		if(stuyDesignContext.getTypeIError() == 0.0)
		    stuyDesignContext.setDefaultTypeIError();
		alpha = stuyDesignContext.getTypeIError();
		if(alpha != null){		    
		    alpha = Double.parseDouble(format.format(alpha));
		    //value.setText(Double.toString(alpha%1));
		    //value.setText(String.format("%02d", toInt(alpha)));
		    value.setText(Double.toString(alpha));
		    value.setCompoundDrawables( null, null, img, null );
		    value.requestFocus();
		}
		else {
		    value.setText("0.");
		    value.setCompoundDrawables( null, null, null, null );
		    value.requestFocus();
		}
		
	}	
	
	/*private Double toDouble(int value){
	    return Double.parseDouble(format.format(new Double(value)/100));
	}*/	
	
	private void resetText(){
        if(alpha == null)
            alpha = Double.parseDouble(format.format(0));
        GlobalVariables.getInstance().setTypeIError(alpha);
        value.requestFocus();
    }
		
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub
        
    }

    public void beforeTextChanged(CharSequence s, int start, int count,
            int after) {
        // TODO Auto-generated method stub
        
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // TODO Auto-generated method stub
        if(String.valueOf(s) != null || String.valueOf(s).equals("")) {
            value.setCompoundDrawables( null, null, img, null );
            try {
                //alpha = toDouble(Integer.parseInt(String.valueOf(s)));
                alpha = (Double.parseDouble(String.valueOf(s)))%1;                
            }
            catch(Exception e){
                alpha = Double.parseDouble(format.format(0));
                value.setCompoundDrawables( null, null, null, null );                
            }
        }
        else {
            value.setCompoundDrawables( null, null, null, null );            
        }
    }
    
    public void onClick(View v) {   
        stuyDesignContext.setTypeIError(alpha);       
        finish();
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
         }
         return super.onKeyDown(keyCode, event);
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
               stuyDesignContext.setTypeIError(alpha);  
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