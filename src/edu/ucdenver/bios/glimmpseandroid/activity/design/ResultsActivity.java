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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.codehaus.jackson.map.ObjectMapper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

public class ResultsActivity extends Activity implements SimpleGestureListener {
    private static GestureFilter detector;
    private static PowerResultList list;
    //private StuyDesignContext globalVariables;
    private final String RESULT = "Result";
    private final static String MAIL_BODY = "Thank you for using the GLIMMPSE software.  Your power results are attached to this email. \n\nTo learn more about power and sample size, please visit http://samplesizeshop.org.";
    private final static String MAIL_SUBJECT = "GLIMMPSE Power Results";
    private final static String RESULT_FILE = "GLIMMPSEPowerResults.csv";
    private final static String columnString = "\"test\",\"actualPower\",\"sampleSize\",\"betaScale\",\"sigmaScale\",\"alpha\",\"nominalPower\",\"powerMethod\",\"quantile\",\"ciLower\",\"ciUpper\"";

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
        // If the window has a container, then we are not free
        // to request window features.
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
                // GlobalVariables.resetInstance();
                finish();
            }
        });

        Button back = (Button) findViewById(R.id.back_button);
        back.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // GlobalVariables.resetInstance();
                finish();
            }
        });
        
        /*TextView solvingFor = (TextView) findViewById(R.id.textView_solving_for);
        solvingFor.setText(globalVariables.getSolvingFor()+RESULT);*/

        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {

            System.out.println("Extras not null");
            String jsonString = extras.getString("results");
            System.out.println("JsonString : " + jsonString);
            ListView resultsListView = (ListView) findViewById(R.id.results_list_view);
            View header1 = getLayoutInflater().inflate(
                    R.layout.results_list_header, null, false);
            if (resultsListView.getHeaderViewsCount() == 0)
                resultsListView.addHeaderView(header1);

            ObjectMapper mapper = new ObjectMapper();
            try {
                list = mapper.readValue(jsonString, PowerResultList.class);
                System.out
                        .println("-----------------------------------------------------");
                /*
                 * if(list == null || list.isEmpty())
                 * System.out.println("Empty results"); else
                 * System.out.println(list);
                 */
                for (PowerResult power : list) {
                    String data = power.toXML() + "\n";
                    // text.setText(data);
                    System.out.println(data);
                }
                // resultsListView.setAdapter(new
                // ResultsListAdapter(ResultsActivity.this,list));
                resultsListView.setAdapter(new ResultsListAdapter(
                        ResultsActivity.this, list));
            } catch (Exception e) {
                // text.setText("testPower Failed to retrieve: " +
                // e.getMessage());
                System.out.println("Exception: " + e.getMessage());
            }
        }

        Button saveEmail = (Button) findViewById(R.id.save_email_button);
        saveEmail.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                // String columnString = "\"Power\",\"SampleSize\"";
                // String columnString =
                // "\"test\",\"actualPower\",\"sampleSize\",\"betaScale\",\"sigmaScale\",\"alpha\",\"nominalPower\",\"powerMethod\",\"quantile\",\"ciLower\",\"ciUpper\"";
                String dataString = null;
                for (PowerResult power : list) {
                    if (dataString == null || dataString.isEmpty()) {
                        dataString = new String();
                    }
                    // dataString = dataString + "\"" + power.getActualPower()
                    // +"\",\"" + power.getTotalSampleSize() + "\"" + "\n";
                    dataString = dataString + "\"" + ((power.getTest() != null) ? power.getTest().getType() : null) + "\",\""
                            + power.getActualPower() + "\",\""
                            + power.getTotalSampleSize() + "\",\""
                            + ((power.getBetaScale() != null) ? power.getBetaScale().getValue() : null) + "\",\""
                            + ((power.getSigmaScale() != null) ? power.getSigmaScale().getValue() : null) + "\",\""
                            + ((power.getAlpha() != null) ? power.getAlpha().getAlphaValue() : null) + "\",\""
                            + ((power.getNominalPower() != null) ? power.getNominalPower().getValue() : null) + "\",\""
                            + ((power.getPowerMethod() != null) ? power.getPowerMethod().getPowerMethodEnum() : null) + "\",\""
                            + ((power.getQuantile() != null) ? power.getQuantile().getValue() : null) + "\",\""
                            + ((power.getConfidenceInterval() != null) ? power.getConfidenceInterval().getLowerLimit() : null) + "\",\""
                            + ((power.getConfidenceInterval() != null) ? power.getConfidenceInterval().getUpperLimit() : null) + "\"" + "\n";
                }
                String combinedString = columnString;
                if (dataString != null && !dataString.isEmpty())
                    combinedString = combinedString + "\n" + dataString;
                
                ArrayList<Uri> uris = new ArrayList<Uri>(2);

                /*File file = null;                
                File root = Environment.getExternalStorageDirectory();
                if (root.canWrite()) {
                    File dir = new File(root.getAbsolutePath()
                            + "/PowerResultData");
                    System.out.println(dir);
                    dir.mkdirs();
                    file = new File(dir, "GLIMMPSEPowerResults.csv");
                    FileOutputStream out = null;
                    try {
                        out = new FileOutputStream(file);
                    } catch (FileNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    try {
                        out.write(combinedString.getBytes());
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    try {
                        out.close();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
                Uri url = null;
                url = Uri.fromFile(file);*/
                
                File file = null;                
                File file1 = null;                
                File root = Environment.getExternalStorageDirectory();
                if (root.canWrite()) {
                    File dir = new File(root.getAbsolutePath()
                            + "/PowerResultData");
                    System.out.println(dir);
                    dir.mkdirs();
                    file = new File(dir, "GLIMMPSEPowerResults.csv");
                    FileOutputStream out = null;
                    try {
                        out = new FileOutputStream(file);
                    } catch (FileNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    try {
                        out.write(combinedString.getBytes());
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    try {
                        out.close();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    
                    
                    file1 = new File(dir, "GLIMMPSEStudyDesign.json");
                    FileOutputStream out1 = null;
                    try {
                        out1 = new FileOutputStream(file1);
                    } catch (FileNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    try {
                        ObjectMapper m = new ObjectMapper();
                        m.writeValue(out1, StuyDesignContext.getInstance().getStudyDesign());
                        //out.write(combinedString.getBytes());
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    try {
                        out1.close();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
                Uri url = null;
                url = Uri.fromFile(file);
                uris.add(url);
                
                Uri url1 = null;
                url1 = Uri.fromFile(file1);                                             
                uris.add(url1);
                                               

                Intent sendIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, MAIL_SUBJECT);
                sendIntent.putExtra(Intent.EXTRA_TEXT, MAIL_BODY);
                //sendIntent.putExtra(Intent.EXTRA_STREAM, url);
                sendIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);                
                // sendIntent.setType("text/html");
                sendIntent.setType("message/rfc822");
                startActivity(sendIntent);

            }
        });

    }

    public void onDoubleTap() {
        // TODO Auto-generated method stub

    }

    public boolean dispatchTouchEvent(MotionEvent me) {
        // System.out.println("dispatchTouchEvent");
        if(detector != null)
            detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }

    public void onSwipe(int direction) {
        // TODO Auto-generated method stub
        /*
         * if(direction == 3) finish();
         */
        String str = "";

        switch (direction) {

        case GestureFilter.SWIPE_RIGHT:
            str = "Swipe Right";
            finish();
            break;
        /*
         * case GestureFilter.SWIPE_LEFT : str = "Swipe Left"; break; case
         * GestureFilter.SWIPE_DOWN : str = "Swipe Down"; break; case
         * GestureFilter.SWIPE_UP : str = "Swipe Up"; break;
         */

        }
        // Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
