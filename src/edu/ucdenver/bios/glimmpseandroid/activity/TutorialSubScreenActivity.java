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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.glimmpseandroid.adapter.GestureFilter;
import edu.ucdenver.bios.glimmpseandroid.adapter.GestureFilter.SimpleGestureListener;
public class TutorialSubScreenActivity extends Activity implements OnClickListener, SimpleGestureListener{
    private static GestureFilter detector;
    
    private final int SOLVING_FOR_ROW = 0;
    private final int POWER_OR_SAMPLE_SIZE_ROW = 1;
    private final int TYPE_I_ERROR_ROW = 2;
    private final int NUMBER_OF_GROUPS_ROW = 3;
    private final int RELATIVE_GROUP_SIZE_ROW = 4;
    //private final int SMALLEST_GROUP_SIZE_ROW = 5;
    private final int MEANS_VARIANCE_ROW = 5;
    
	@Override
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
		setContentView(R.layout.tutorial_sub_screen);
		if (useTitleFeature) {
			 window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				        R.layout.titlebar);
			 
			 Button homeButton = (Button) findViewById(R.id.home_button);
	            homeButton.setText(getResources().getString(R.string.title_tutorial));
			 homeButton.setOnClickListener(this);
			 /*
			 Button back = (Button) findViewById(R.id.back_button);
			 back.setOnClickListener(this);*/
			 
			 TextView title = (TextView)findViewById(R.id.window_title);
			 //title.setText(getResources().getString(R.string.menu_tutorial));			 
			 
			 //TextView heading = (TextView) findViewById(R.id.title_textView_tutorial_sub_screen);
			 	 
			 TextView description = (TextView) findViewById(R.id.description_textView_tutorial_sub_screen);
			 
			 Bundle extras = this.getIntent().getExtras();
				if (extras != null) {
					int screenId = extras.getInt("sub_screen");
					String subTitle = null;
					String data = null;
					switch(screenId) {
						case SOLVING_FOR_ROW:
							subTitle = getResources().getString(R.string.tutorial_solving_for);
							data = getResources().getString(R.string.description_tutorial_solving_for);
							break;
						case POWER_OR_SAMPLE_SIZE_ROW:
                            subTitle = getResources().getString(R.string.tutorial_smallest_group_size);
                            data = getResources().getString(R.string.description_tutorial_smallest_group);
                            break;
						case TYPE_I_ERROR_ROW:
							subTitle = getResources().getString(R.string.tutorial_type_one_error);
							data = getResources().getString(R.string.description_tutorial_alpha);
							break;
						case NUMBER_OF_GROUPS_ROW:
							subTitle = getResources().getString(R.string.tutorial_number_of_groups);
							data = getResources().getString(R.string.description_tutorial_groups);
							break;
						case RELATIVE_GROUP_SIZE_ROW:
							subTitle = getResources().getString(R.string.tutorial_relative_group_size);
							data = getResources().getString(R.string.description_tutorial_relative_size);
							break;						
						case MEANS_VARIANCE_ROW:
							subTitle = getResources().getString(R.string.tutorial_means_and_variance);
							data = getResources().getString(R.string.description_tutorial_mean_variance);
							break;
					}
					//heading.setText(subTitle);
					title.setText(subTitle);
					description.setText(data);
					 
				} else {
					
				}
			 
			 
		}
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
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
