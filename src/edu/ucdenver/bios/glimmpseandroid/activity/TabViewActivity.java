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

import java.util.Timer;

import org.restlet.Client;
import org.restlet.data.Parameter;
import org.restlet.data.Protocol;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
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

/*
 @SuppressWarnings("deprecation")
 public class TabViewActivity extends TabActivity {

 @SuppressWarnings("deprecation")
 @Override
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

 setContentView(R.layout.tabview);

 if (useTitleFeature) {
 window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
 R.layout.titlebar);

 Button homeButton = (Button) findViewById(R.id.home_button);
 homeButton.setOnClickListener(new OnClickListener() {

 public void onClick(View v) {
 finish();
 }

 });
 TabHost tabHost = getTabHost();

 Resources res = getResources();

 String[] labels = getResources().getStringArray(R.array.tab_labels);			
 final String[] titles = getResources().getStringArray(R.array.tabed_window_titles);

 Intent tutorialIntent = new Intent(this, TutorialActivity.class);
 tabHost.addTab(tabHost.newTabSpec(labels[0])
 .setIndicator(labels[0])
 .setContent(tutorialIntent));


 Intent designIntent = new Intent(this, StartActivity.class);
 tabHost.addTab(tabHost.newTabSpec(labels[1])
 .setIndicator(labels[1])
 .setContent(designIntent));

 Intent aboutUsIntent = new Intent(this, LearnMoreActivity.class);
 tabHost.addTab(tabHost.newTabSpec(labels[2])
 .setIndicator(labels[2])
 .setContent(aboutUsIntent));

 TextView title = (TextView)findViewById(R.id.window_title);

 Bundle extras = this.getIntent().getExtras();
 if (extras != null) {
 int tab_id = extras.getInt("tab_id");			
 title.setText(titles[tab_id]);
 tabHost.setCurrentTab(tab_id);			
 } else {
 title.setText(titles[0]);
 }

 tabHost.setOnTabChangedListener(new OnTabChangeListener() {

 public void onTabChanged(String tabId) {

 TextView title = (TextView)findViewById(R.id.window_title);
 if(tabId != null) {
 title.setText(tabId);

 }
 else {
 title.setText(titles[0]);						
 }					
 }
 });
 }

 }

 *//**
 * This method is called when the user clicks the device's Menu button the
 * first time for this Activity. Android passes in a Menu object that is
 * populated with items.
 * 
 * @param menu
 *            the menu
 * @return true, if successful
 */
/*
 @Override
 public boolean onCreateOptionsMenu(Menu menu) {
 getMenuInflater().inflate(R.menu.activity_main, menu);
 // getMenuInflater().inflate(R.menu.common_botoom_menu, menu);
 return true;
 }

 *//**
 * This method is called when a menu item is selected. Android passes in the
 * selected item. The switch statement in this method calls the appropriate
 * method to perform the action the user chose.
 * 
 * @param item
 *            the item
 * @return true, if successful
 */
/*
 * @Override public boolean onOptionsItemSelected(MenuItem item) { // Handle
 * item selection switch (item.getItemId()) { case R.id.menu_home: // finish
 * current activity finish(); return true;
 * 
 * case R.id.menu_start: // finish current activity finish(); // Start Start
 * activity Intent startIntent = new Intent(getBaseContext(),
 * StartActivity.class); startActivityForResult(startIntent, 0); return true;
 * case R.id.menu_aboutus: return true; case R.id.menu_tutorial: // finish
 * current activity finish(); // Start Tutorial activity Intent tutorialIntent =
 * new Intent(getBaseContext(), TutorialActivity.class);
 * startActivityForResult(tutorialIntent, 0); return true;
 * 
 * default: return super.onOptionsItemSelected(item); } }
 * 
 * }
 */
// public class TabViewActivity extends Activity implements
// TabContentFactory,SimpleGestureListener{
public class TabViewActivity extends Activity implements Runnable,
        TabContentFactory {
    private static final String SERVICE_URL = "http://glimmpse.samplesizeshop.com/power/";
    // private static final String SERVICE_URL =
    // "http://140.226.53.117:8080/power/";
    String[] labels;
    // String[] titles;
    private static Button calculateButton;
    /*
     * private static ProgressBar loadingProgressBar; private static TextView
     * loadingText;
     */
    private ListView tutorialListView;
    private ListView designListView;
    private TabHost mTabHost;
    private final int TAB_HEIGHT = 50; // set desired value here instead of 50
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
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the view for the activity

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
                    Intent intent = new Intent(context,
                            ResultsActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else{
                    /*Toast.makeText(TabViewActivity.this,
                            "Internet Connection Not available",
                            Toast.LENGTH_SHORT).show();*/
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("No internet Connection");
                    builder.setMessage(
                            "Not connected to internet")
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                int id) {
                                            
                                        }
                                    });                            
                    // AlertDialog alert = builder.create();
                    builder.show();
                }
                super.handleMessage(msg);
            }

        };

        // detector = new GestureFilter(this,this);
        // If the window has a container, then we are not free
        // to request window features.
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
        // titles = res.getStringArray(R.array.tabed_window_titles);

        Button homeButton = (Button) findViewById(R.id.home_button);
        homeButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // GlobalVariables.resetInstance();
                finish();
            }
        });

        globalVariables = StuyDesignContext.getInstance();
        setupViews();
        // setup Views for each tab

        // call method to set up tabs
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
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
                TextView title = (TextView) findViewById(R.id.window_title);
                if (tabId != null) {
                    /*
                     * if (!tabId.equals(labels[0])) title.setText(tabId); else
                     * {
                     * 
                     * }
                     */
                    title.setText(tabId);
                    if (tabId.equals(labels[1]))
                        designListPopulate();

                } else {
                    tutorialListPopulate();
                    // title.setText(titles[0]);
                    title.setText(labels[0]);
                }
            }
        });

        Button resetButton = (Button) findViewById(R.id.reset);
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
                // AlertDialog alert = builder.create();
                builder.show();

                /*
                 * StuyDesignContext.resetInstance(); designListPopulate();
                 * globalVariables.resetProgress(); //ProgressBar inputProgress
                 * = (ProgressBar) findViewById(R.id.input_progress);
                 * System.out.println("reset progress : " +
                 * globalVariables.getTotalProgress());
                 * //inputProgress.setProgress
                 * (globalVariables.getTotalProgress());
                 * calculateButton.setEnabled(false);
                 * calculateButton.setClickable(false);
                 * loadingProgressBar.setVisibility(View.INVISIBLE);
                 * loadingText.setVisibility(View.INVISIBLE);
                 */
            }
        });

        calculateButton = (Button) findViewById(R.id.calculate_button);
        /*
         * loadingProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
         * loadingText = (TextView) findViewById(R.id.textView1);
         * loadingProgressBar.setVisibility(View.INVISIBLE);
         * loadingText.setVisibility(View.INVISIBLE);
         */
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

                Thread thread = new Thread(TabViewActivity.this,"Loading");                            
                
                /*Timer time = new Timer();
                time.schedule(thread.interrupt(),1000L);*/
                thread.start();   
                try {
                    thread.join(1);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    System.out.println("Inturrupted Exception : "+e.getMessage());
                }
                

                // new PerformBackgroundTask(context).execute();

                /*
                 * progress.show(); new Thread() { public void run() { // Write
                 * Your Downloading logic here // at the end write this.
                 * handler.sendEmptyMessage(0); }
                 * 
                 * }.start();
                 */

                // v.setVisibility(View.VISIBLE);
                /*
                 * ProgressBar loadingProgressBar = (ProgressBar)
                 * findViewById(R.id.progressBar1); TextView loadingText =
                 * (TextView) findViewById(R.id.textView1);
                 * loadingProgressBar.setVisibility(View.VISIBLE);
                 * loadingText.setVisibility(View.VISIBLE);
                 */

                /*try {
                    String solvingFor = globalVariables.getSolvingFor();
                    if (solvingFor != null && !solvingFor.isEmpty()) {
                        String URL;
                        if (solvingFor.equals(SolutionTypeEnum.POWER.getId()))
                            URL = SERVICE_URL + "power";
                        else
                            URL = SERVICE_URL + "samplesize";

                        ClientResource cr = new ClientResource(URL);

                        StudyDesign studyDesign = globalVariables
                                .getStudyDesign();
                        globalVariables.setDefaults();

                        
                         * ObjectMapper mapper1 = new ObjectMapper(); try{
                         * //File f = new
                         * File(Environment.getExternalStorageDirectory() +
                         * File.separator + "user.json"); //File f = new
                         * File("/data/myPackage/files/media/user.json"); //File
                         * mediaDir = new
                         * File(Environment.getExternalStorageDirectory
                         * ()+"/power"); File f = new File("data/power");
                         * if(!f.exists() && f.mkdir()) { f = new
                         * File("user.json"); if(!f.exists()) f.createNewFile();
                         * } f.createNewFile(); if(f.exists())
                         * mapper1.writeValue(f, studyDesign);
                         * mapper1.writeValue(System.out, studyDesign); }
                         * catch(Exception e){
                         * System.out.println(e.getMessage()); }
                         
                        System.out.println(studyDesign);

                        Representation repr = cr.post(studyDesign);

                        String jsonStr = repr.getText();
                        // Toast.makeText(v.getContext(),
                        // jsonStr,Toast.LENGTH_LONG).show();
                        if (jsonStr != null && !jsonStr.isEmpty()) {
                            Bundle bundle = new Bundle();
                            bundle.putString("results", jsonStr);
                            Intent intent = new Intent(v.getContext(),
                                    ResultsActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                        
                         * ObjectMapper mapper = new ObjectMapper();
                         * PowerResultList list = mapper.readValue(jsonStr,
                         * PowerResultList.class); System.out.println(
                         * "-----------------------------------------------------"
                         * ); if(list == null || list.isEmpty())
                         * System.out.println("Empty results"); else
                         * System.out.println(list); for(PowerResult power :
                         * list) { String data = power.toXML()+"\n";
                         * //text.setText(data); System.out.println(data); }
                         

                    }

                } catch (Exception e) {
                    // text.setText("testPower Failed to retrieve: " +
                    // e.getMessage());
                    System.out.println("testPower Failed to retrieve: "
                            + e.getMessage());
                }*/
            }
        });
    }

    private void resetButtonFunctionality() {
        StuyDesignContext.resetInstance();
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
        /*
         * View header1 = getLayoutInflater().inflate(
         * R.layout.tutorial_list_header, null, false); if
         * (tutorialListView.getHeaderViewsCount() == 0)
         * tutorialListView.addHeaderView(header1);
         */

        tutorialListView.setAdapter(new TutorialAdapter(getBaseContext(),
                tutorialList));
    }

    private void designListPopulate() {
        // String solvingFor =
        // StuyDesignContext.getInstance().getStudyDesign().getSolutionTypeEnum().toString();
        String solvingFor = StuyDesignContext.getInstance().getSolvingFor();

        String[] designList = getResources().getStringArray(
                R.array.tutorial_list);
        // if(solvingFor != null) {
        /*
         * String[] temp = new String[6]; int index = 5; while(index > 1) {
         * temp[index] = designList[index-1]; index--; } if(solvingFor != null)
         * { if(solvingFor.equals(SolutionTypeEnum.SAMPLE_SIZE .getId())) {
         * temp[1] = SolutionTypeEnum.POWER .getId(); }else { temp[1] =
         * "Smallest Group Size"; } } temp[0] = designList[0]; designList =
         * temp;
         */
        // }
        /*
         * if(solvingFor != null) { String[] temp = new String[7]; int index =
         * 0; while(index < 6) { temp[index+1] = designList[index]; index++; }
         * temp[6] = solvingFor; }
         */

        if (solvingFor != null) {
            if (solvingFor.equals(SolutionTypeEnum.SAMPLE_SIZE.getId())) {
                designList[1] = SolutionTypeEnum.POWER.getId();
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
        /*
         * globalVariables.setTabOneContentView(findViewById(R.id.tutorial_view))
         * ;
         * globalVariables.setTabTwoContentView(findViewById(R.id.start_view));
         * globalVariables
         * .setTabThreeContentView(findViewById(R.id.about_us_view));
         */
    }

    /**
     * Sets up the tabs for this Activity
     */
    public void setupTabs() {
        /*
         * mTabHost = (TabHost) findViewById(android.R.id.tabhost);
         * mTabHost.setup(); //must be called
         * mTabHost.setOnTabChangedListener(this); //use this Activity as the
         * onTabChangedListener //setup each individual tab
         * setupTab(TAB_ONE_TAG); setupTab(TAB_TWO_TAG);
         * setupTab(TAB_THREE_TAG); setTabHeight(TAB_HEIGHT);
         */
        // Workaround for "bleeding-through" overlay issue. Set each tab as
        // current tab, by index.
        // Order should not matter, but the first tab to be shown should be set
        // last.
        // mTabHost.setCurrentTab(1);
        // mTabHost.setCurrentTab(0);
    }

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
        TextView tv = (TextView) view.findViewById(R.id.tabsText);
        // this sets the tab's tag as the label that displays on the view, but
        // can be anything
        tv.setText(tag);
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
        // TODO Auto-generated method stub
        
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

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
    
                    StudyDesign studyDesign = globalVariables
                            .getStudyDesign();
                    globalVariables.setDefaults();                                       
    
                    /*
                     * ObjectMapper mapper1 = new ObjectMapper(); try{
                     * //File f = new
                     * File(Environment.getExternalStorageDirectory() +
                     * File.separator + "user.json"); //File f = new
                     * File("/data/myPackage/files/media/user.json"); //File
                     * mediaDir = new
                     * File(Environment.getExternalStorageDirectory
                     * ()+"/power"); File f = new File("data/power");
                     * if(!f.exists() && f.mkdir()) { f = new
                     * File("user.json"); if(!f.exists()) f.createNewFile();
                     * } f.createNewFile(); if(f.exists())
                     * mapper1.writeValue(f, studyDesign);
                     * mapper1.writeValue(System.out, studyDesign); }
                     * catch(Exception e){
                     * System.out.println(e.getMessage()); }
                     */
                    System.out.println(studyDesign);
                    
//                    org.restlet.Context context = new org.restlet.Context();
//                    context.setParameters(parameters).parameters.add "socketTimeout", "1000"
//                    resource.next = new Client(context, [Protocol.HTTP])
                    // see ClientConnectionHelper from restlet (socketConnectTimeoutMS)
                    
                    Client client = new Client(Protocol.HTTP);
                    client.setConnectTimeout(15);
                    
                    cr.setNext(client);
               
                    Representation repr = cr.post(studyDesign);
    
                    jsonStr = repr.getText();
                    // Toast.makeText(v.getContext(),
                    // jsonStr,Toast.LENGTH_LONG).show();
                    /*if (jsonStr != null && !jsonStr.isEmpty()) {
                        Bundle bundle = new Bundle();
                        bundle.putString("results", jsonStr);
                        Intent intent = new Intent(context,
                                ResultsActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }*/
                    /*
                     * ObjectMapper mapper = new ObjectMapper();
                     * PowerResultList list = mapper.readValue(jsonStr,
                     * PowerResultList.class); System.out.println(
                     * "-----------------------------------------------------"
                     * ); if(list == null || list.isEmpty())
                     * System.out.println("Empty results"); else
                     * System.out.println(list); for(PowerResult power :
                     * list) { String data = power.toXML()+"\n";
                     * //text.setText(data); System.out.println(data); }
                     */
    
                }
    
            } catch (Exception e) {
                // text.setText("testPower Failed to retrieve: " +
                // e.getMessage());
                System.out.println("testPower Failed to retrieve: "
                        + e.getMessage());
            }
        }        
        handler.sendEmptyMessage(0);
    }

    /*
     * public boolean dispatchTouchEvent(MotionEvent me){
     * //System.out.println("dispatchTouchEvent"); detector.onTouchEvent(me);
     * return super.dispatchTouchEvent(me); }
     * 
     * public void onSwipe(int direction) { // TODO Auto-generated method stub
     * String str = "";
     * 
     * TextView title = (TextView) findViewById(R.id.window_title);
     * 
     * int tabId = mTabHost.getCurrentTab();
     * 
     * switch (direction) {
     * 
     * case GestureFilter.SWIPE_RIGHT : str = "Swipe Right"; if(tabId==0) break;
     * else{ if(tabId-1 == 0){ mTabHost.setCurrentTab(0);
     * title.setText(labels[0]); tutorialListPopulate(); } else if(tabId-1 ==
     * 1){ mTabHost.setCurrentTab(1); title.setText(labels[1]);
     * designListPopulate(); } }
     * this.overridePendingTransition(R.anim.animation_enter,
     * R.anim.animation_leave); break; case GestureFilter.SWIPE_LEFT : str =
     * "Swipe Left"; if(tabId==(labels.length-1)) break; else{ if(tabId+1 == 2){
     * mTabHost.setCurrentTab(2); title.setText(labels[2]); } else if(tabId+1 ==
     * 1){ mTabHost.setCurrentTab(1); title.setText(labels[1]);
     * designListPopulate(); } }
     * this.overridePendingTransition(R.anim.animation_leave,
     * R.anim.animation_enter); break; case GestureFilter.SWIPE_DOWN : str =
     * "Swipe Down"; break; case GestureFilter.SWIPE_UP : str = "Swipe Up";
     * break;
     * 
     * } }
     * 
     * public void onDoubleTap() { // TODO Auto-generated method stub
     * 
     * }
     */

    /*
     * public boolean onKeyDown(int keyCode, KeyEvent event) { if (keyCode ==
     * KeyEvent.KEYCODE_BACK) { GlobalVariables.resetInstance(); finish();
     * return true; } return super.onKeyDown(keyCode, event); }
     */

}