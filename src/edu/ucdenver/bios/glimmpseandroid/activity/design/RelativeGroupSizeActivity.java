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
import android.widget.ListView;
import android.widget.TextView;
import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.glimmpseandroid.adapter.GestureFilter;
import edu.ucdenver.bios.glimmpseandroid.adapter.GestureFilter.SimpleGestureListener;
import edu.ucdenver.bios.glimmpseandroid.adapter.RelativeGroupSizeAdapter;
import edu.ucdenver.bios.glimmpseandroid.application.StuyDesignContext;

public class RelativeGroupSizeActivity extends Activity implements OnClickListener,SimpleGestureListener {
//public class RelativeGroupSizeActivity extends Activity implements OnClickListener {
    private ListView relativeGroupSizeListView;
    private static GestureFilter detector;
    
    /*protected void onResume() {
        super.onResume();
        relativeGroupSizeListPopulate();
        // If current screen is Design
        if (mTabHost.getCurrentTab() == 1) {
            designListPopulate();
            globalVariables = StuyDesignContext.getInstance();
            ProgressBar inputProgress = (ProgressBar) findViewById(R.id.input_progress);
            // System.out.println("Tab View total progress : "+globalVariables.getTotalProgress());
            inputProgress.setProgress(globalVariables.getTotalProgress());
        }
    }*/
    
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
        setContentView(R.layout.design_relative_group_size);

        if (useTitleFeature) {
            window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
        }
        Button homeButton = (Button) findViewById(R.id.home_button);
        homeButton.setText(getResources().getString(R.string.title_design));
        homeButton.setOnClickListener(this);
        
        Button resetButton = (Button) findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new OnClickListener() {
            
            public void onClick(View v) {
                // TODO Auto-generated method stub
                StuyDesignContext.getInstance().setDefaultRelativeGroupSize();
                relativeGroupSizeListPopulate();
            }
        });

        /*Button back = (Button) findViewById(R.id.back_button);
        back.setOnClickListener(this);*/

        TextView title = (TextView) findViewById(R.id.window_title);
        title.setText(getResources().getString(R.string.title_relative_group_size));
        
        /*relativeGroupSizeListView = (ListView)findViewById(R.id.relative_group_size_list_view);
        View header =  getLayoutInflater().inflate(R.layout.design_relative_group_size_list_header, null, false);
        if(relativeGroupSizeListView.getHeaderViewsCount() == 0)
            relativeGroupSizeListView.addHeaderView(header);
        int groups = StuyDesignContext.getInstance().getGroups();
        if(groups > 0)
            relativeGroupSizeListView.setAdapter(new RelativeGroupSizeAdapter(RelativeGroupSizeActivity.this,groups));
        else{
            
        }*/
        relativeGroupSizeListPopulate();
        
    }
    
    private void relativeGroupSizeListPopulate(){
        relativeGroupSizeListView = (ListView)findViewById(R.id.relative_group_size_list_view);
        View header =  getLayoutInflater().inflate(R.layout.design_relative_group_size_list_header, null, false);
        if(relativeGroupSizeListView.getHeaderViewsCount() == 0)
            relativeGroupSizeListView.addHeaderView(header);
        int groups = StuyDesignContext.getInstance().getGroups();
        if(groups > 0)
            relativeGroupSizeListView.setAdapter(new RelativeGroupSizeAdapter(RelativeGroupSizeActivity.this,groups));
        else{
            
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
        /*
         * // TODO Auto-generated method stub Bundle bundle = new Bundle();
         * bundle.putInt("design_list_item", 2); value = (TextView)
         * findViewById(R.id.see_progress); bundle.putInt("group_count",
         * Integer.parseInt(value.getText().toString())); Intent result=new
         * Intent(GroupCountActivity.this,DesignListAdapter.class); //Intent
         * result=new Intent(); result.putExtras(bundle); setResult(0, result);
         */
        //stuyDesignContext.setGroups(groups);            
        finish();
    }
    
    public boolean dispatchTouchEvent(MotionEvent me){
        //System.out.println("dispatchTouchEvent");
        detector.onTouchEvent(me);
       return super.dispatchTouchEvent(me);
      }

    public void onSwipe(int direction) {
        // TODO Auto-generated method stub
        if(direction == 3)
            finish();
        String str = "";
             
            switch (direction) {
             
            case GestureFilter.SWIPE_RIGHT : str = "Swipe Right";
                                                    finish();
                                                     break;
            case GestureFilter.SWIPE_LEFT :  str = "Swipe Left";
                                                           break;
            case GestureFilter.SWIPE_DOWN :  str = "Swipe Down";
                                                           break;
            case GestureFilter.SWIPE_UP :    str = "Swipe Up";
                                                           break;
                                                      
            }
             //Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public void onDoubleTap() {
        // TODO Auto-generated method stub
        
    }
}
