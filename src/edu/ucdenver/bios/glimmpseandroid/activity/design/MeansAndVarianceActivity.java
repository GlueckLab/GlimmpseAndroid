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
import android.graphics.drawable.Drawable;
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
import android.widget.ListView;
import android.widget.TextView;
import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.glimmpseandroid.activity.TabViewActivity;
import edu.ucdenver.bios.glimmpseandroid.adapter.GestureFilter;
import edu.ucdenver.bios.glimmpseandroid.adapter.GestureFilter.SimpleGestureListener;
import edu.ucdenver.bios.glimmpseandroid.adapter.MeansAndVarianceAdapter;
import edu.ucdenver.bios.glimmpseandroid.application.StuyDesignContext;
/*
 * @author Uttara Sakhadeo
 */
public class MeansAndVarianceActivity extends Activity implements OnClickListener, SimpleGestureListener{
	private ListView meanVarianceList;
	static Drawable img;
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
		setContentView(R.layout.design_means_and_variance);

		if (useTitleFeature) {
			window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		}
		Button homeButton = (Button) findViewById(R.id.home_button);
		homeButton.setText(getResources().getString(R.string.title_design));
		homeButton.setOnClickListener(this);

		/*Button back = (Button) findViewById(R.id.back_button);
		back.setOnClickListener(this);*/

		TextView title = (TextView) findViewById(R.id.window_title);
		title.setText(getResources().getString(R.string.title_means_and_variance));
		
		img = getResources().getDrawable( R.drawable.clear_button );
		img.setBounds( 0, 0, 32, 32 );

		meanVarianceList = (ListView)findViewById(R.id.means_and_variance_list_view);
		View header =  getLayoutInflater().inflate(R.layout.design_means_and_variance_list_header, null, false);
        if(meanVarianceList.getHeaderViewsCount() == 0)
            meanVarianceList.addHeaderView(header);
        int groups = StuyDesignContext.getInstance().getGroups();
        if(groups > 0)
            meanVarianceList.setAdapter(new MeansAndVarianceAdapter(MeansAndVarianceActivity.this,groups, img));
		
		
	}	
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
         }
        return false;
         //return super.onKeyDown(keyCode, event);
	}
	
	
	public void onClick(View v) {		
		//GlobalVariables.getInstance().setMeans(means);		    
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
    
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_screen_menu, menu);
        return true;
    }
    
    private boolean menuSelection(MenuItem item){
        switch (item.getItemId()) { 
        case R.id.menu_tutorial: 
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
            finish();           
            return true;
        case R.id.menu_aboutus:             
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
