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
import android.widget.TextView;
import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.glimmpseandroid.application.StuyDesignContext;

public class SmallestGroupSizeActivity extends Activity implements TextWatcher{
	
	static Integer smallestGroupSize;	
	static EditText value;
	static Drawable img;
	static StuyDesignContext stuyDesignContext = StuyDesignContext.getInstance();
	//static ImageButton clear;
	
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		final Window window = getWindow();
		boolean useTitleFeature = false;
		// If the window has a container, then we are not free
		// to request window features.
		if (window.getContainer() == null) {
			useTitleFeature = window
					.requestFeature(Window.FEATURE_CUSTOM_TITLE);
		}
		setContentView(R.layout.design_smallest_group_size);

		if (useTitleFeature) {
			window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		}
		Button homeButton = (Button) findViewById(R.id.home_button);
		 homeButton.setText(getResources().getString(R.string.title_design));
		homeButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				exit();		
			}
		});
		
		
		img = getResources().getDrawable( R.drawable.clear_button );
		img.setBounds( 0, 0, 32, 32 );

		Button back = (Button) findViewById(R.id.back_button);
		back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				exit();	
			}
		});		
		
		Button clear = (Button) findViewById(R.id.clear);
		clear.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                value.setText("");
                resetText();
            }
        });
		
		TextView title = (TextView) findViewById(R.id.window_title);
		title.setText(getResources().getString(R.string.title_smallest_group_size));			
		
		value = (EditText) findViewById(R.id.smallest_group_size);
		/*
		if(stuyDesignContext.getSmallestGroupSize() == 0)
		    stuyDesignContext.setDefaultSmallestGroupSize();		
		smallestGroupSize = stuyDesignContext.getSmallestGroupSize();*/
		if(smallestGroupSize != null) {
			if(smallestGroupSize == 0) {
				value.setText("");	
				//clear.setVisibility(View.INVISIBLE);
			}
			else {
				value.setText(Integer.toString(smallestGroupSize));
				value.setCompoundDrawables( null, null, img, null );	
				//clear.setVisibility(View.VISIBLE);
			}
		}
		
		value.addTextChangedListener(this);
		value.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View arg0, MotionEvent arg1) {				
			if (arg1.getX() > value.getWidth()
			- img.getIntrinsicWidth() - 10) {
				value.setText("");
				resetText();
			}
			return false;
			}
	
		});
		
		
		/*clear = (ImageButton) findViewById(R.id.clear);
		clear.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				value.setText("");
				resetText();	
			}
		});*/

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
				smallestGroupSize = Integer.parseInt(String.valueOf(s));
				//clear.setVisibility(View.VISIBLE);
			}
			catch(Exception e){
				smallestGroupSize = 0;
				value.setCompoundDrawables( null, null, null, null );
				//clear.setVisibility(View.INVISIBLE);
			}
		}
		else {
			value.setCompoundDrawables( null, null, null, null );
			//clear.setVisibility(View.INVISIBLE);
		}
	}
	
	private void exit(){
		resetText();	
		finish();
	}
	
	private void resetText(){
		if(smallestGroupSize == null)
			smallestGroupSize = 0;
		//stuyDesignContext.setSmallestGroupSize(smallestGroupSize);	
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
         }
         return super.onKeyDown(keyCode, event);
    }
}
