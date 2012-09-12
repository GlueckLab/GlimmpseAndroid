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
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.glimmpseandroid.adapter.GestureFilter;
import edu.ucdenver.bios.glimmpseandroid.adapter.GestureFilter.SimpleGestureListener;
import edu.ucdenver.bios.glimmpseandroid.application.StuyDesignContext;
import edu.ucdenver.bios.webservice.common.enums.SolutionTypeEnum;

public class SolvingForActivity extends Activity implements OnClickListener,SimpleGestureListener {
    private static RadioGroup values;
    private static RadioButton checked;
    private static String solvingFor;	
	//private static final String SAMPLE_SIZE = "Sample Size";
	private static StuyDesignContext stuyDesignContext = StuyDesignContext.getInstance();
	private static GestureFilter detector;
	//static GlobalVariables globalVariables;

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
		setContentView(R.layout.design_solving_for);

		if (useTitleFeature) {
			window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		}
		Button homeButton = (Button) findViewById(R.id.home_button);
		 homeButton.setText(getResources().getString(R.string.title_design));
		homeButton.setOnClickListener(this);

		/*Button back = (Button) findViewById(R.id.back_button);
		back.setOnClickListener(this);*/

		TextView title = (TextView) findViewById(R.id.window_title);
		title.setText(getResources().getString(R.string.title_solving_for));

		values = (RadioGroup) findViewById(R.id.radioGroup_solvingFor);
		
		solvingFor = stuyDesignContext.getSolvingFor();
		if(solvingFor != null){
			if(solvingFor.equals(((RadioButton)values.getChildAt(0)).getText())) {				
				((RadioButton)values.getChildAt(0)).setSelected(true);
			}
			else {				
				((RadioButton)values.getChildAt(1)).setChecked(true);
			}
		}
		
				
	}	
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
         }
         return super.onKeyDown(keyCode, event);
	}
	
	public void onClick(View v) {	
		/*int selected = values.getCheckedRadioButtonId();
		checked = (RadioButton) findViewById(selected);
		solvingFor = ""+checked.getText();
		switch(selected){
			case 0:
				
				solvingFor = getResources().getString(R.id.radio_power);
				break;
			case 1:
				solvingFor = getResources().getString(R.id.radio_sample_size);
				break;
		}
		stuyDesignContext.setSolvingFor(solvingFor);	
		//stuyDesignContext.clearSampleSizeList();
		if(solvingFor.equals(SolutionTypeEnum.SAMPLE_SIZE.getId()) && stuyDesignContext.getPowerListSize() > 0){
		    stuyDesignContext.clearPowerList();		    
		}
		if(solvingFor.equals(SolutionTypeEnum.POWER.getId()) && stuyDesignContext.getPowerListSize() > 0){
		    stuyDesignContext.clearSampleSizeList();
		}
		
		finish();*/
	    exit();
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
                                                   exit();
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
    
    private void exit(){
        int selected = values.getCheckedRadioButtonId();
        checked = (RadioButton) findViewById(selected);
        solvingFor = ""+checked.getText();
        /*switch(selected){
            case 0:
                
                solvingFor = getResources().getString(R.id.radio_power);
                break;
            case 1:
                solvingFor = getResources().getString(R.id.radio_sample_size);
                break;
        }*/
        stuyDesignContext.setSolvingFor(solvingFor);    
        //stuyDesignContext.clearSampleSizeList();
        /*if(solvingFor.equals(SolutionTypeEnum.SAMPLE_SIZE.getId()) && stuyDesignContext.getPowerListSize() > 0){
            stuyDesignContext.clearPowerList();         
        }
        if(solvingFor.equals(SolutionTypeEnum.POWER.getId()) && stuyDesignContext.getPowerListSize() > 0){
            stuyDesignContext.clearSampleSizeList();
        }*/
        if(stuyDesignContext.getPowerListSize() > 0){
            stuyDesignContext.clearPowerList();         
        }
        if(stuyDesignContext.getSampleSizeListSize() > 0){
            stuyDesignContext.clearSampleSizeList();
        }
        
        finish();
    }
}