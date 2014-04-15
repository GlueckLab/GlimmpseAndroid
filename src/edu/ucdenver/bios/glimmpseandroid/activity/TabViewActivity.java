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

import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
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

// TODO: Auto-generated Javadoc
/**
 * The Class TabViewActivity deals with 'Tutorial', 'Design' and 'About Us'
 * screens of the GLIMMPSE LITE Application.
 * 
 * @author Uttara Sakhadeo
 * @version 1.0.0
 */
public class TabViewActivity extends Activity implements Runnable,
        TabContentFactory {

    public String exceptionMessage = null;

    /** The Constant SERVICE_URL. */
    private static final String SERVICE_URL = "http://glimmpse.samplesizeshop.org/power/";

    /** The Constant FEEDBACK_ADDRESS. */
    private static final String FEEDBACK_ADDRESS = "samplesizeshop@gmail.com";

    /** The Constant TAB_ID. */
    public static final String TAB_ID = "tab_id";

    /** The Constant TAB_HEIGHT. */
    public static final int TAB_HEIGHT = 50;

    /** The Constant WIFI_AVAILABLE. */
    public static final int WIFI_AVAILABLE = 1;

    /** The Constant MOBILE_NETWORK_AVAILABLE. */
    public static final int MOBILE_NETWORK_AVAILABLE = 2;

    /** The Constant TUTORIAL_TAB. */
    public static final int TUTORIAL_TAB = 0;

    /** The Constant DESIGN_TAB. */
    public static final int DESIGN_TAB = 1;

    /** The Constant ABOUT_US_TAB. */
    public static final int ABOUT_US_TAB = 2;

    /** The labels. */
    String[] labels;

    /** The calculate button. */
    private Button calculateButton;

    /** The reset button. */
    private Button resetButton;

    /** The resources. */
    private Resources resources;

    /** The tutorial list view. */
    private ListView tutorialListView;

    /** The design list view. */
    private ListView designListView;

    /** The m tab host. */
    private TabHost mTabHost;

    /** The tab one content view. */
    private View tabOneContentView;

    /** The tab two content view. */
    private View tabTwoContentView;

    /** The tab three content view. */
    private View tabThreeContentView;

    /** The global variables. */
    StuyDesignContext globalVariables;

    /** The NE t_ connection. */
    private boolean NET_CONNECTION = false;

    /** The json str. */
    private String jsonStr;

    /** The handler. */
    private static Handler handler;

    /** The progress. */
    private ProgressDialog progressDialog;

    /** The context. */
    private Context context;

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onResume()
     */
    protected void onResume() {
        super.onResume();
        if (mTabHost.getCurrentTab() == DESIGN_TAB) {
            designListPopulate();
            globalVariables = StuyDesignContext.getInstance();
            int progress = globalVariables.getTotalProgress();
            if (progress == 6) {
                calculateButtonEnabled();
            }
        } else if (mTabHost.getCurrentTab() == ABOUT_US_TAB) {
            aboutUsPopulate();
            for (int i = TUTORIAL_TAB; i <= ABOUT_US_TAB; i++) {
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
        // set the view for the activity

        final Window window = getWindow();
        boolean useTitleFeature = false;

        context = TabViewActivity.this;

        resources = getResources();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(resources
                .getString(R.string.progress_dialog_title));
        progressDialog.setMessage(resources
                .getString(R.string.progress_dialog_message));
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                progressDialog.dismiss();
                if (jsonStr != null && !jsonStr.isEmpty()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("results", jsonStr);
                    Intent intent = new Intent(context, ResultsActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    jsonStr = null;
                    NET_CONNECTION = false;
                } else if (!NET_CONNECTION) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            context);
                    builder.setTitle(resources
                            .getString(R.string.no_net_connection_title));
                    builder.setMessage(
                            resources
                                    .getString(R.string.no_net_connection_description))
                            .setCancelable(false)
                            .setPositiveButton(
                                    resources.getString(R.string.ok),
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) {

                                        }
                                    });
                    builder.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            context);
                    builder.setTitle(resources
                            .getString(R.string.calculation_error_title));
                    builder.setMessage(
                            resources
                                    .getString(R.string.calculation_error_description)
                                    + " " + exceptionMessage)
                            .setCancelable(false)
                            .setPositiveButton(
                                    resources.getString(R.string.ok),
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

        labels = resources.getStringArray(R.array.tab_labels);

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

        // setup each individual tab
        setupTab(labels[TUTORIAL_TAB]);
        setupTab(labels[DESIGN_TAB]);
        setupTab(labels[ABOUT_US_TAB]);
        setTabHeight(TAB_HEIGHT);

        TextView title = (TextView) findViewById(R.id.window_title);

        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            int tab_id = extras.getInt(TAB_ID);
            for (int i = 2; i >= tab_id; i--) {
                mTabHost.setCurrentTab(i);
            }
            title.setText(labels[tab_id]);
        } else {
            for (int i = ABOUT_US_TAB; i >= TUTORIAL_TAB; i--) {
                mTabHost.setCurrentTab(i);
            }
            title.setText(labels[TUTORIAL_TAB]);
        }

        mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

            public void onTabChanged(String tabId) {
                TextView title = (TextView) findViewById(R.id.window_title);
                if (tabId != null) {
                    title.setText(tabId);
                    if (tabId.equals(labels[DESIGN_TAB])) {
                        designListPopulate();
                    }

                } else {
                    tutorialListPopulate();
                    title.setText(labels[TUTORIAL_TAB]);
                }

            }
        });
    }

    /**
     * Reset button functionality.
     */
    private void resetButtonFunctionality() {
        StuyDesignContext.resetInstance();
        designListPopulate();
        jsonStr = null;
        NET_CONNECTION = false;
        globalVariables.resetProgress();
        calculateButtonDisabled();
    }

    /**
     * Populating Tutorial list.
     */
    private void tutorialListPopulate() {
        String[] tutorialList = getResources().getStringArray(
                R.array.tutorial_list);

        tutorialListView = (ListView) findViewById(R.id.tutorial_list_view);

        tutorialListView.setAdapter(new TutorialAdapter(getBaseContext(),
                tutorialList));
    }

    /**
     * Populating Design list.
     */
    private void designListPopulate() {

        resetButton = (Button) findViewById(R.id.reset);
        resetButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(resources.getString(R.string.confirm_reset));
                builder.setMessage(resources.getString(R.string.reset_message))
                        .setCancelable(false)
                        .setPositiveButton(resources.getString(R.string.yes),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                            int id) {
                                        resetButtonFunctionality();
                                    }
                                })
                        .setNegativeButton(resources.getString(R.string.no),
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
            calculateButtonDisabled();

        } else {
            calculateButtonEnabled();
        }
        calculateButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                calculateButtonDisabled();
                int network = detectNetwork();
                if (network == WIFI_AVAILABLE
                        || network == MOBILE_NETWORK_AVAILABLE) {
                    displayLoading();
                } else {
                    calculateButtonEnabled();
                    handler.sendEmptyMessage(0);
                }
            }
        });

        String solvingFor = StuyDesignContext.getInstance().getSolvingFor();

        String[] designList = getResources()
                .getStringArray(R.array.design_list);

        if (solvingFor != null) {
            String enumSampleSize = getString(R.string.enum_sample_size_value);
            if (enumSampleSize.equals(solvingFor)) {
                designList[1] = getString(R.string.enum_power_value);
                designList[2] = resources.getString(R.string.title_type_i_error);
                designList[3] = resources.getString(R.string.title_groups);
                designList[4] = resources.getString(R.string.title_relative_group_size);
                designList[5] = resources.getString(R.string.title_means_and_variance);
            } else {
                designList[1] = resources.getString(R.string.title_type_i_error);
                designList[2] = resources.getString(R.string.title_groups);
                designList[3] = resources.getString(R.string.title_smallest_group_size);
                designList[4] = resources.getString(R.string.title_smallest_group_size);
                designList[5] = resources.getString(R.string.title_means_and_variance);
            }
        }

        designListView = (ListView) findViewById(R.id.design_list_view);

        designListView.setAdapter(new DesignListAdapter(getBaseContext(),
                designList));

    }

    /**
     * Populating About Us screen.
     */
    private void aboutUsPopulate() {
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

                i.putExtra(Intent.EXTRA_EMAIL,
                        new String[] { FEEDBACK_ADDRESS });
                i.putExtra(Intent.EXTRA_SUBJECT,
                        resources.getString(R.string.feedback_mail_subject));

                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(TabViewActivity.this,
                            resources.getString(R.string.no_mail_client_error),
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    /**
     * Sets up the Views for each tab.
     */
    public void setupViews() {
        tabOneContentView = findViewById(R.id.tutorial_view);
        tabTwoContentView = findViewById(R.id.start_view);
        tabThreeContentView = findViewById(R.id.about_us_view);

    }

    /**
     * Sets up the tabs for this Activity.
     */
    public void setupTabs() {

    }

    /**
     * Sets up a new tab with given tag by creating a View for the tab (via
     * createTabView() ) and adding it to the tab host.
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
     * Creates a View for a tab.
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

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float density = metrics.density;
        int width = (int) (density * 20);
        int height = width - 10;

        if (tag.compareTo(labels[0]) == 0) {
            image = context.getResources().getDrawable(R.drawable.tutorial);
            // image.setBounds(0, 0, 32, 12);
            image.setBounds(0, 0, width, height);
        } else if (tag.compareTo(labels[1]) == 0) {
            image = context.getResources().getDrawable(R.drawable.design);
            // image.setBounds(0, 0, 26, 28);
            image.setBounds(0, 0, width, height);
        } else {
            image = context.getResources().getDrawable(R.drawable.aboutus);
            // image.setBounds(0, 0, 24, 21);
            image.setBounds(0, 0, width, height);
        }
        // image.setBounds(0,0,image.getIntrinsicWidth(),image.getIntrinsicHeight());
        textView.setCompoundDrawables(null, image, null, null);
        // this sets the tab's tag as the label that displays on the view, but
        // can be anything
        textView.setText(tag);
        return view;
    }

    /**
     * Sets a custom height for the TabWidget, in dp-units.
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

    /*
     * (non-Javadoc)
     * 
     * @see
     * android.widget.TabHost.TabContentFactory#createTabContent(java.lang.String
     * )
     */
    public View createTabContent(String tag) {
        if (tag.compareTo(labels[TUTORIAL_TAB]) == 0) {
            tutorialListPopulate();
            return tabOneContentView;
        } else if (tag.compareTo(labels[DESIGN_TAB]) == 0) {
            designListPopulate();
            return tabTwoContentView;
        } else if (tag.compareTo(labels[ABOUT_US_TAB]) == 0) {
            aboutUsPopulate();
            return tabThreeContentView;
        } else
            return tabOneContentView;
    }

    /**
     * Detect network.
     * 
     * @return the int
     */
    private int detectNetwork() {
        NET_CONNECTION = false;
        jsonStr = null;
        final ConnectivityManager connMgr = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        final android.net.NetworkInfo wifi_Network = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile_Network = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        String service = Context.WIFI_SERVICE;
        WifiManager wifi = (WifiManager) getSystemService(service);

        if (!wifi.isWifiEnabled()) {
            if (wifi.getWifiState() != WifiManager.WIFI_STATE_ENABLING)
                wifi.setWifiEnabled(true);
        }

        if (wifi_Network.isAvailable()
                && wifi_Network.isConnectedOrConnecting()) {
            NET_CONNECTION = true;
            return WIFI_AVAILABLE;
        } else if (mobile_Network.isAvailable()
                && mobile_Network.isConnectedOrConnecting()) {
            NET_CONNECTION = true;
            return MOBILE_NETWORK_AVAILABLE;
        } else {
            Toast.makeText(getApplicationContext(),
                    resources.getString(R.string.net_connection_error),
                    Toast.LENGTH_SHORT).show();
        }
        return 0;
    }

    /**
     * Display loading.
     */
    private void displayLoading() {
        progressDialog = ProgressDialog.show(context,
                resources.getString(R.string.calculating_message) + " "
                        + globalVariables.getSolvingFor().toLowerCase(),
                resources.getString(R.string.loading), true, false);

        Thread thread = new Thread(TabViewActivity.this,
                resources.getString(R.string.loading_thread));

        thread.start();
        try {
            thread.join(2);
        } catch (InterruptedException e) {
            Log.e("Tag: " + this.getClass(), "Error: "+e);             
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    public void run() {
        setConnection();
        handler.sendEmptyMessage(0);
    }

    /**
     * Sets the connection.
     */
    private void setConnection() {
        try {
            String solvingFor = globalVariables.getSolvingFor();
            if (solvingFor != null && !solvingFor.isEmpty()) {
                String URL;
                String enumPower = getString(R.string.enum_power_value);
                if (enumPower.equals(solvingFor))
                    URL = SERVICE_URL + "power";
                else
                    URL = SERVICE_URL + "samplesize";

                ClientResource cr = new ClientResource(URL);

                StudyDesign studyDesign = globalVariables.getStudyDesign();
                globalVariables.setDefaults();

                Representation repr = cr.post(studyDesign);
                if (repr != null) {
                    jsonStr = repr.getText();
                    if (jsonStr == null || jsonStr.isEmpty()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(
                                context);
                        builder.setTitle(resources
                                .getString(R.string.calculation_error_title));
                        builder.setMessage(
                                resources
                                        .getString(R.string.calculation_error_description))
                                .setCancelable(false)
                                .setPositiveButton(
                                        resources.getString(R.string.ok),
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int id) {

                                            }
                                        });
                        builder.show();
                    }

                } else {
                }
            }

        } catch (Exception e) {
            Log.e("Tag: " + this.getClass(), "Error: "+e); 
        }
    }

    /**
     * Calculate button disabled.
     */
    private void calculateButtonDisabled() {
        calculateButton
                .setBackgroundResource(R.drawable.inactive_button_selector);
        calculateButton.setEnabled(false);
        calculateButton.setClickable(false);
    }

    /**
     * Calculate button enabled.
     */
    private void calculateButtonEnabled() {
        calculateButton
                .setBackgroundResource(R.drawable.calculate_button_selector);
        calculateButton.setEnabled(true);
        calculateButton.setClickable(true);
    }

}