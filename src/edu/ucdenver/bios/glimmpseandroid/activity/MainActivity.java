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
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.glimmpseandroid.application.StuyDesignContext;

// TODO: Auto-generated Javadoc
/**
 * The Class MainActivity deals with the 'Home' screen of the GLIMMPSE LITE
 * Application.
 * 
 * @author Uttara Sakhadeo
 * @version 1.0.0
 */
public class MainActivity extends Activity {

    /**
     * The Constant TAB_ID.
     * 
     * <p>
     * TAB_ID is passed to TabViewActivity along with actual tab number.
     * </p>
     */
    public static final String TAB_ID = "tab_id";

    /**
     * The Constant ZERO.
     * 
     * <p>
     * If user clicks on 'Learn More' button, which is available on Home Screen
     * then TAB_ID and ZERO parameters are bundled together and passed to
     * TabViewActivity. With the help of these parameters tab 'Tutorial' is set
     * as active tab on TabViewActivity screen.
     * </p>
     */
    public static final int ZERO = 0;

    /**
     * The Constant ONE.
     * 
     * <p>
     * If user clicks on 'Start' button, which is available on Home Screen then
     * TAB_ID and ONE parameters are bundled together and passed to
     * TabViewActivity. With the help of these parameters tab 'Design' is set as
     * active tab on TabViewActivity screen.
     * </p>
     */
    public static final int ONE = 1;

    /**
     * Called when an Activity is first started. From the incoming Intent, it
     * determines what kind of editing is desired, and then does it.
     * 
     * @param savedInstanceState
     *            the saved instance state
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        /* Initialize required activity. */
        super.onCreate(savedInstanceState);

        /* Setting custom title bar. */
        final Window window = getWindow();
        boolean useTitleFeature = false;
        /*
         * If the window has a container, then we are not free to request window
         * features.
         */
        if (window.getContainer() == null) {
            useTitleFeature = window
                    .requestFeature(Window.FEATURE_CUSTOM_TITLE);
        }
        setContentView(R.layout.home_screen);

        if (useTitleFeature) {
            window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);

            Button homeButton = (Button) findViewById(R.id.home_button);
            homeButton.setVisibility(View.GONE);

            Resources res = getResources();
            /* Setting Window Title. */
            TextView title = (TextView) findViewById(R.id.window_title);
            title.setText(res.getString(R.string.title_home));

        }

        Resources resources = getResources();

        TextView version = (TextView) this
                .findViewById(R.id.version_textView_home);
        version.setText(resources.getString(R.string.version) + " "
                + resources.getString(R.string._VERSION_NUMBER_));

        Button start = (Button) this.findViewById(R.id.start_button_home);
        /* Setting action event for Start Button. */
        start.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Intent tabIntent = new Intent(v.getContext(),
                        TabViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(TAB_ID, ONE);
                tabIntent.putExtras(bundle);
                startActivity(tabIntent);
            }

        });

        Button learnMore = (Button) this
                .findViewById(R.id.learn_more_button_home);
        /* Setting action event for Back Button. */
        learnMore.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Intent tabIntent = new Intent(v.getContext(),
                        TabViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(TAB_ID, ZERO);
                tabIntent.putExtras(bundle);
                startActivity(tabIntent);
            }

        });
    }

    /**
     * Called when the screen is no longer visible to the user. Next possible
     * methods to receive could be either onRestart(), onDestroy(), or nothing,
     * depending on later user activity.
     * 
     * @see android.app.Activity#onStop()
     */
    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * Method called when a new hardware key event occurs.
     * 
     * @param keyCode
     *            the key code
     * @param event
     *            the event
     * @return true, if successful
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            /* Finish the activity if user presses back key from keyboard. */
            StuyDesignContext.resetInstance();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
