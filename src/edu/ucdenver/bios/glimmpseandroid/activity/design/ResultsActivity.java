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
package edu.ucdenver.bios.glimmpseandroid.activity.design;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.codehaus.jackson.map.ObjectMapper;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
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
import edu.ucdenver.bios.glimmpseandroid.adapter.ResultsListAdapter;
import edu.ucdenver.bios.glimmpseandroid.application.StuyDesignContext;
import edu.ucdenver.bios.webservice.common.domain.PowerResult;
import edu.ucdenver.bios.webservice.common.domain.PowerResultList;

// TODO: Auto-generated Javadoc
/**
 * The Class ResultsActivity deals with the 'Result' Screen of the GLIMMPSE LITE
 * Application.
 * 
 * @author Uttara Sakhadeo
 * @version 1.0.0
 */
public class ResultsActivity extends Activity implements SimpleGestureListener {

    /** The detector. */
    private static GestureFilter detector;

    /** The list. */
    private static PowerResultList list;

    /** The resources. */
    private Resources resources;

    /** The Constant RESULTS_DIR. */
    public static final String RESULTS_DIR = "/PowerResultData";

    // private StuyDesignContext globalVariables;

    /**
     * Instantiates a new results activity.
     */
    public ResultsActivity() {
        // TODO Auto-generated constructor stub

    }

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
        setContentView(R.layout.results);

        if (useTitleFeature) {
            window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
        }

        TextView title = (TextView) findViewById(R.id.window_title);
        title.setText(getResources().getString(R.string.title_results));

        Button homeButton = (Button) findViewById(R.id.home_button);
        homeButton.setText(getResources().getString(R.string.title_design));
        homeButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        Button back = (Button) findViewById(R.id.back_button);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        resources = getResources();

        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            String jsonString = extras.getString("results");
            ListView resultsListView = (ListView) findViewById(R.id.results_list_view);
            View header1 = getLayoutInflater().inflate(
                    R.layout.results_list_header, null, false);
            if (resultsListView.getHeaderViewsCount() == 0)
                resultsListView.addHeaderView(header1);

            ObjectMapper mapper = new ObjectMapper();
            try {
                list = mapper.readValue(jsonString, PowerResultList.class);

                resultsListView.setAdapter(new ResultsListAdapter(
                        ResultsActivity.this, list));
            } catch (Exception e) {
                Log.e("Tag: " + this.getClass(), "Error: "+e);                 
            }
        }

        Button saveEmail = (Button) findViewById(R.id.save_email_button);
        saveEmail.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                String dataString = null;
                for (PowerResult power : list) {
                    if (dataString == null || dataString.isEmpty()) {
                        dataString = new String();
                    }
                    dataString = dataString
                            + "\""
                            + ((power.getTest() != null) ? power.getTest()
                                    .getType() : null)
                            + "\",\""
                            + power.getActualPower()
                            + "\",\""
                            + power.getTotalSampleSize()
                            + "\",\""
                            + ((power.getBetaScale() != null) ? power
                                    .getBetaScale().getValue() : null)
                            + "\",\""
                            + ((power.getSigmaScale() != null) ? power
                                    .getSigmaScale().getValue() : null)
                            + "\",\""
                            + ((power.getAlpha() != null) ? power.getAlpha()
                                    .getAlphaValue() : null)
                            + "\",\""
                            + ((power.getNominalPower() != null) ? power
                                    .getNominalPower().getValue() : null)
                            + "\",\""
                            + ((power.getPowerMethod() != null) ? power
                                    .getPowerMethod().getPowerMethodEnum()
                                    : null)
                            + "\",\""
                            + ((power.getQuantile() != null) ? power
                                    .getQuantile().getValue() : null)
                            + "\",\""
                            + ((power.getConfidenceInterval() != null) ? power
                                    .getConfidenceInterval().getLowerLimit()
                                    : null)
                            + "\",\""
                            + ((power.getConfidenceInterval() != null) ? power
                                    .getConfidenceInterval().getUpperLimit()
                                    : null) + "\"" + "\n";
                }
                String combinedString = resources
                        .getString(R.string.column_string);
                if (dataString != null && !dataString.isEmpty())
                    combinedString = combinedString + "\n" + dataString;

                ArrayList<Uri> uris = new ArrayList<Uri>(2);

                File file = null;
                File file1 = null;
                File root = Environment.getExternalStorageDirectory();
                if (root.canWrite()) {
                    File dir = new File(root.getAbsolutePath() + RESULTS_DIR);
                    dir.mkdirs();
                    file = new File(dir, resources
                            .getString(R.string.power_results_file));
                    FileOutputStream out = null;
                    try {
                        out = new FileOutputStream(file);
                    } catch (FileNotFoundException e) {
                        Log.e("Tag: " + this.getClass(), "Error: "+e);                         
                    }
                    try {
                        out.write(combinedString.getBytes());
                    } catch (IOException e) {
                        Log.e("Tag: " + this.getClass(), "Error: "+e); 
                    }
                    try {
                        out.close();
                    } catch (IOException e) {
                        Log.e("Tag: " + this.getClass(), "Error: "+e); 
                    }

                    file1 = new File(dir, resources
                            .getString(R.string.study_design_file));
                    FileOutputStream out1 = null;
                    try {
                        out1 = new FileOutputStream(file1);
                    } catch (FileNotFoundException e) {
                        Log.e("Tag: " + this.getClass(), "Error: "+e); 
                    }
                    try {
                        ObjectMapper m = new ObjectMapper();
                        m.writeValue(out1, StuyDesignContext.getInstance()
                                .getStudyDesign());
                    } catch (IOException e) {
                        Log.e("Tag: " + this.getClass(), "Error: "+e); 
                    }
                    try {
                        out1.close();
                    } catch (IOException e) {
                        Log.e("Tag: " + this.getClass(), "Error: "+e); 
                    }
                }
                Uri url = null;
                if (file != null) {
                    url = Uri.fromFile(file);
                    uris.add(url);
                }

                Uri url1 = null;
                if (file1 != null) {
                    url1 = Uri.fromFile(file1);
                    uris.add(url1);
                }

                if (uris != null) {
                    Intent sendIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                    sendIntent.putExtra(Intent.EXTRA_SUBJECT,
                            resources.getString(R.string.mail_subject));
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            resources.getString(R.string.mail_body));
                    sendIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM,
                            uris);
                    sendIntent.setType("message/rfc822");
                    startActivity(sendIntent);
                }

            }
        });

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
     * @see android.app.Activity#dispatchTouchEvent(android.view.MotionEvent)
     */
    public boolean dispatchTouchEvent(MotionEvent me) {
        if (detector != null)
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
            finish();
            break;
        }
    }
}
