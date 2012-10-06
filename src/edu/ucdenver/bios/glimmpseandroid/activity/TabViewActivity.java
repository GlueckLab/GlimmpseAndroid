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

import org.restlet.data.Cookie;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.util.Series;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;
import android.widget.Toast;
import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.glimmpseandroid.activity.design.ResultsActivity;
import edu.ucdenver.bios.glimmpseandroid.adapter.DesignListAdapter;
import edu.ucdenver.bios.glimmpseandroid.adapter.TutorialAdapter;
import edu.ucdenver.bios.glimmpseandroid.application.StuyDesignContext;
import edu.ucdenver.bios.webservice.common.domain.StudyDesign;
import edu.ucdenver.bios.webservice.common.enums.SolutionTypeEnum;

/**
 * The Class TabViewActivity deals with the Screens with Tab view of the
 * GLIMMPSE LITE Application.
 * 
 * @author Uttara Sakhadeo
 */
// public class TabViewActivity extends Activity implements
// TabContentFactory,SimpleGestureListener, Runnable{
public class TabViewActivity extends Activity implements Runnable,
        TabContentFactory {
    // private static final String SERVICE_URL =
    // "http://glimmpse.samplesizeshop.com/power/";
    private static final String SERVICE_URL = "http://140.226.53.117:8080/power/";
    //private static final String SERVICE_URL = "http://10.0.2.2:8080/power/";
    String[] labels;
    // String[] titles;
    private Button calculateButton;
    private Button resetButton;
    /*
     * private static ProgressBar loadingProgressBar; private static TextView
     * loadingText;
     */
    private ListView tutorialListView;
    private ListView designListView;
    private TabHost mTabHost;
    private final int TAB_HEIGHT = 50; // set desired value here instead of 50
    private final String GLIMMPSE_COOKIE = "GLIMMPSELite";
    private View tabOneContentView;
    private View tabTwoContentView;
    private View tabThreeContentView;
    StuyDesignContext globalVariables;
    // private static GestureFilter detector;

    private String jsonStr;

    private Handler handler;
    private ProgressDialog progress;
    private Context context;
    
    
    protected void onResume() {
        super.onResume();
        System.out.println("Tab View On Resume");
        // If current screen is Design
        if (mTabHost.getCurrentTab() == 1) {
            designListPopulate();
            globalVariables = StuyDesignContext.getInstance();
            // ProgressBar inputProgress = (ProgressBar)
            // findViewById(R.id.input_progress);
            // System.out.println("Tab View total progress : "+globalVariables.getTotalProgress());
            int progress = globalVariables.getTotalProgress();
            // inputProgress.setProgress(progress);
            if (progress == 6) {
                calculateButton.setEnabled(true);
                calculateButton.setClickable(true);
            }
        }
        else if(mTabHost.getCurrentTab()==2){
            aboutUsPopulate();
            for (int i = 0; i <= 2; i++) {
                mTabHost.setCurrentTab(i);
            }
        }
    }

    /**
     * This method is called by Android when the Activity is first started. From
     * the incoming Intent, it determines what kind of editing is desired, and
     * then does it.
     * 
     * @param savedInstanceState
     *            the saved instance state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        final Window window = getWindow();
        boolean useTitleFeature = false;

        context = TabViewActivity.this;

        progress = new ProgressDialog(this);
        progress.setTitle("Please Wait!!");
        progress.setMessage("Wait!!");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                
        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                progress.dismiss();
                if (jsonStr != null && !jsonStr.isEmpty()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("results", jsonStr);
                    Intent intent = new Intent(context, ResultsActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            context);
                    builder.setTitle("No internet Connection");
                    builder.setMessage("Not connected to internet")
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) {

                                        }
                                    });
                    
                    builder.show();
                }
                super.handleMessage(msg);
            }

        };

        
        if (window.getContainer() == null) {
            useTitleFeature = window
                    .requestFeature(Window.FEATURE_CUSTOM_TITLE);
        }
        setContentView(R.layout.main);

        if (useTitleFeature) {
            window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
        }
        Resources res = getResources();

        labels = res.getStringArray(R.array.tab_labels);
        

        Button homeButton = (Button) findViewById(R.id.home_button);
        homeButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                
                finish();
            }
        });
        
        globalVariables = StuyDesignContext.getInstance();
        setupViews();
        // setup Views for each tab

        // call method to set up tabs
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        mTabHost.getTabWidget().setDividerDrawable(R.drawable.tab_divider);
        // mTabHost.setOnTabChangedListener(this); //use this Activity as the
        // onTabChangedListener

        // setup each individual tab
        setupTab(labels[0]);
        setupTab(labels[1]);
        setupTab(labels[2]);
        setTabHeight(TAB_HEIGHT);

        TextView title = (TextView) findViewById(R.id.window_title);

        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            // if(extras.containsKey("tab_id")){
            int tab_id = extras.getInt("tab_id");
            for (int i = 2; i >= tab_id; i--) {
                mTabHost.setCurrentTab(i);                
            }
            // title.setText(titles[tab_id]);
            title.setText(labels[tab_id]);
        } else {            
            for (int i = 2; i >= 0; i--) {
                mTabHost.setCurrentTab(i);
            }
            // title.setText(titles[0]);
            title.setText(labels[0]);            
        }

        mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

            public void onTabChanged(String tabId) {
                System.out.println("in on tabchanged listener .....");
                TextView title = (TextView) findViewById(R.id.window_title);
                if (tabId != null) {                                                      
                    
                    title.setText(tabId);
                    if (tabId.equals(labels[1])) {                        
                        designListPopulate();                        
                    }

                } else {
                    tutorialListPopulate();
                    // title.setText(titles[0]);
                    title.setText(labels[0]);                    
                }
                
            }
        });

                /*
         * loadingProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
         * loadingText = (TextView) findViewById(R.id.textView1);
         * loadingProgressBar.setVisibility(View.INVISIBLE);
         * loadingText.setVisibility(View.INVISIBLE);
         */

    }

    private void resetButtonFunctionality() {
       /* CookieManager cookieManager = 
        StuyDesignContext.resetInstance();*/
        
        designListPopulate();
        globalVariables.resetProgress();
        // ProgressBar inputProgress = (ProgressBar)
        // findViewById(R.id.input_progress);
        System.out.println("reset progress : "
                + globalVariables.getTotalProgress());
        // inputProgress.setProgress(globalVariables.getTotalProgress());
        calculateButton.setEnabled(false);
        calculateButton.setClickable(false);
        /*
         * loadingProgressBar.setVisibility(View.INVISIBLE);
         * loadingText.setVisibility(View.INVISIBLE);
         */
    }

    private void tutorialListPopulate() {
        String[] tutorialList = getResources().getStringArray(
                R.array.tutorial_list);

        tutorialListView = (ListView) findViewById(R.id.tutorial_list_view);        

        tutorialListView.setAdapter(new TutorialAdapter(getBaseContext(),
                tutorialList));
    }

    private void designListPopulate() {
        
        resetButton = (Button) findViewById(R.id.reset);
        resetButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirm Reset...");
                builder.setMessage(
                        "This action will clear any unsaved study design information.  Continue?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                            int id) {
                                        resetButtonFunctionality();
                                    }
                                })
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                            int id) {
                                        dialog.cancel();
                                    }
                                });                
                builder.show();
                
            }
        });

        calculateButton = (Button) findViewById(R.id.calculate_button);

        if (StuyDesignContext.getInstance().getTotalProgress() != 6) {
            calculateButton.setClickable(false);
            calculateButton.setEnabled(false);

        } else {
            calculateButton.setEnabled(true);
            calculateButton.setClickable(true);
        }
        calculateButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                progress = ProgressDialog.show(context, "Working..",
                        "Loading ...", true, false);

                Thread thread = new Thread(TabViewActivity.this, "Loading");

                thread.start();
                try {
                    thread.join(2);
                } catch (InterruptedException e) {
                    
                    System.out.println("Inturrupted Exception : "
                            + e.getMessage());
                }
                
            }
        });
        
        // String solvingFor =
        // StuyDesignContext.getInstance().getStudyDesign().getSolutionTypeEnum().toString();
        String solvingFor = StuyDesignContext.getInstance().getSolvingFor();

        String[] designList = getResources().getStringArray(
                R.array.design_list);        

        if (solvingFor != null) {
            if (solvingFor.equals(SolutionTypeEnum.SAMPLE_SIZE.getIdx())) {
                designList[1] = SolutionTypeEnum.POWER.getIdx();
            } else {
                designList[1] = "Smallest Group Size";
            }
        }

        designListView = (ListView) findViewById(R.id.design_list_view);

        designListView.setAdapter(new DesignListAdapter(getBaseContext(),
                designList));

    }

    public void aboutUsPopulate() {
        // Set scrollbar visibility to true.
        TextView text = (TextView) findViewById(R.id.description_textView_aboutus);
        text.setMovementMethod(new ScrollingMovementMethod());
        text.setMovementMethod(LinkMovementMethod.getInstance());
        // Email button action
        Button email = (Button) findViewById(R.id.email_button_home);
        email.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                // i.putExtra(Intent.EXTRA_EMAIL , new
                // String[]{"uttara.sakhadeo@ucdenver.edu"});
                i.putExtra(Intent.EXTRA_EMAIL,
                        new String[] { "uttarasakhadeo@gmail.com" });
                i.putExtra(Intent.EXTRA_SUBJECT, "Glimmpse Lite Queries");
                // i.putExtra(Intent.EXTRA_TEXT , "");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(TabViewActivity.this,
                            "There are no email clients installed.",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    /**
     * Sets up the Views for each tab
     */
    public void setupViews() {
        tabOneContentView = findViewById(R.id.tutorial_view);
        tabTwoContentView = findViewById(R.id.start_view);
        tabThreeContentView = findViewById(R.id.about_us_view);
       
    }

    /**
     * Sets up the tabs for this Activity
     */
    public void setupTabs() {}

    /**
     * Sets up a new tab with given tag by creating a View for the tab (via
     * createTabView() ) and adding it to the tab host
     * 
     * @param tag
     *            The tag of the tab to setup
     */
    public void setupTab(String tag) {
        View tabView = createTabView(mTabHost.getContext(), tag);
        mTabHost.addTab(mTabHost.newTabSpec(tag).setIndicator(tabView)
                .setContent(this));
    }

    /**
     * Creates a View for a tab
     * 
     * @param context
     *            The context from which the LayoutInflater is obtained
     * @param tag
     *            The tag to be used as the label on the tab
     * @return The inflated view to be used by a tab
     */
    private View createTabView(Context context, String tag) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.tabs_bg, null);
        TextView textView = (TextView) view.findViewById(R.id.tabsText);
        Drawable image = null;
        if(tag.compareTo(labels[0]) == 0){
            image = context.getResources().getDrawable(R.drawable.tutorial);
            //image.setBounds(0, 0, 32, 12);            
            image.setBounds(0, 0, 32, 21);            
        }
        else if(tag.compareTo(labels[1])  == 0){
            image = context.getResources().getDrawable(R.drawable.design);
            //image.setBounds(0, 0, 26, 28);            
            image.setBounds(0, 0, 32, 21);            
        }
        else{
            image = context.getResources().getDrawable(R.drawable.aboutus);
            //image.setBounds(0, 0, 24, 21);            
            image.setBounds(0, 0, 32, 21);            
        }        
        //image.setBounds(0,0,image.getIntrinsicWidth(),image.getIntrinsicHeight());
        textView.setCompoundDrawables(null,image,null,null);
        // this sets the tab's tag as the label that displays on the view, but
        // can be anything
        textView.setText(tag);
        return view;
    }

    /**
     * Sets a custom height for the TabWidget, in dp-units
     * 
     * @param height
     *            The height value, corresponding to dp-units
     */
    public void setTabHeight(int height) {
        for (int i = 0; i < mTabHost.getTabWidget().getTabCount(); i++) {
            mTabHost.getTabWidget().getChildAt(i).getLayoutParams().height = (int) (height * this
                    .getResources().getDisplayMetrics().density);
        }
    }

    public View createTabContent(String tag) {
        if (tag.compareTo(labels[0]) == 0) {
            tutorialListPopulate();
            return tabOneContentView;
        } else if (tag.compareTo(labels[1]) == 0) {
            designListPopulate();
            return tabTwoContentView;
        } else if (tag.compareTo(labels[2]) == 0) {
            aboutUsPopulate();
            return tabThreeContentView;
        } else
            return tabOneContentView;
    }

    public void run() {
        
        String service = Context.WIFI_SERVICE;
        WifiManager wifi = (WifiManager)getSystemService(service);
        
        if(!wifi.isWifiEnabled()){
            if(wifi.getWifiState() != WifiManager.WIFI_STATE_ENABLING)
                wifi.setWifiEnabled(true);
        }
        
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);        
        // For Wifi
        NetworkInfo mWifi = connManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI); 
        if (mWifi.isConnected()) {

            try {
                String solvingFor = globalVariables.getSolvingFor();
                if (solvingFor != null && !solvingFor.isEmpty()) {
                    String URL;
                    if (solvingFor.equals(SolutionTypeEnum.POWER.getIdx()))
                        URL = SERVICE_URL + "power";
                    else
                        URL = SERVICE_URL + "samplesize";

                    ClientResource cr = new ClientResource(URL);
                                        
                    /*Series<Cookie> cookies= cr.getCookies();
                    if(cookies.getValues(GLIMMPSE_COOKIE) == null){
                        cookies.removeAll(GLIMMPSE_COOKIE);
                    }
                        cookies.add(GLIMMPSE_COOKIE, "StudyDesign");
                    
                    System.out.println("------------------------------");
                    System.out.println("Cookies Saved : "+cr.getCookies().size());
                    System.out.println("------------------------------");*/

                    StudyDesign studyDesign = globalVariables.getStudyDesign();
                    globalVariables.setDefaults();
                   
                    System.out.println(studyDesign);

                    Representation repr = cr.post(studyDesign);

                    jsonStr = repr.getText();        
                    
                    

                }

            } catch (Exception e) {                
                System.out.println("testPower Failed to retrieve: "
                        + e.getMessage());
            }
        }
        handler.sendEmptyMessage(0);
        
        
        /*ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);        
        // For Wifi
        NetworkInfo activeNetwork = connManager
                .getActiveNetworkInfo();
        int networkType = activeNetwork.getType();
        System.out.println("------------------------------");
        System.out.println(networkType);
        System.out.println("------------------------------");
        switch(networkType){
        case (ConnectivityManager.TYPE_WIFI)  :
            NetworkInfo mWifi = connManager
            .getNetworkInfo(ConnectivityManager.TYPE_WIFI);   
            if (mWifi.isConnected()) {
                
                try {
                    String solvingFor = globalVariables.getSolvingFor();
                    if (solvingFor != null && !solvingFor.isEmpty()) {
                        String URL;
                        if (solvingFor.equals(SolutionTypeEnum.POWER.getId()))
                            URL = SERVICE_URL + "power";
                        else
                            URL = SERVICE_URL + "samplesize";
                        
                        ClientResource cr = new ClientResource(URL);
                        
                        StudyDesign studyDesign = globalVariables.getStudyDesign();
                        globalVariables.setDefaults();
                        
                        System.out.println(studyDesign);
                        
                        Representation repr = cr.post(studyDesign);
                        
                        jsonStr = repr.getText();                   
                        
                    }
                    
                } catch (Exception e) {                
                    System.out.println("testPower Failed to retrieve: "
                            + e.getMessage());
                }
            }
            handler.sendEmptyMessage(0);
            break;
        case (ConnectivityManager.TYPE_MOBILE) :
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    context);
            builder.setTitle("3G Connection");
            builder.setMessage("Using Mobile 3G for internate connection")
                    .setCancelable(false)
                    .setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                            int id) {
                                        
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                            int id) {
                                        dialog.cancel();
                                    }
                                });
            
            builder.show();
            break;
        default :
            break;
        }*/
        
    }

 
}