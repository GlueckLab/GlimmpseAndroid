package edu.ucdenver.bios.glimmpseandroid.activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;
import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.glimmpseandroid.adapter.DesignListAdapter;
import edu.ucdenver.bios.glimmpseandroid.adapter.TutorialAdapter;
import edu.ucdenver.bios.glimmpseandroid.application.StuyDesignContext;

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
public class TabViewActivity extends Activity implements TabContentFactory {
    String[] labels;
    //String[] titles;
    private ListView tutorialListView;
    private ListView designListView;
    private TabHost mTabHost;
    private final int TAB_HEIGHT = 50; // set desired value here instead of 50
    private View tabOneContentView;
    private View tabTwoContentView;
    private View tabThreeContentView;
    StuyDesignContext globalVariables;

    protected void onResume() {
        super.onResume();
        // If current screen is Design
        if (mTabHost.getCurrentTab() == 1) {
            designListPopulate();
            globalVariables = StuyDesignContext.getInstance();
            ProgressBar inputProgress = (ProgressBar) findViewById(R.id.input_progress);
            // System.out.println("Tab View total progress : "+globalVariables.getTotalProgress());
            inputProgress.setProgress(globalVariables.getTotalProgress());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the view for the activity

        final Window window = getWindow();
        boolean useTitleFeature = false;
        // If the window has a container, then we are not free
        // to request window features.
        if (window.getContainer() == null) {
            useTitleFeature = window
                    .requestFeature(Window.FEATURE_CUSTOM_TITLE);
        }
        setContentView(R.layout.main);
        setupViews();
        if (useTitleFeature) {
            window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
        }
        Resources res = getResources();

        labels = res.getStringArray(R.array.tab_labels);
        //titles = res.getStringArray(R.array.tabed_window_titles);

        Button homeButton = (Button) findViewById(R.id.home_button);
        homeButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // GlobalVariables.resetInstance();
                finish();
            }
        });

        globalVariables = StuyDesignContext.getInstance();

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
            //title.setText(titles[tab_id]);
            title.setText(labels[tab_id]);
        } else {
            for (int i = 2; i >= 0; i--) {
                mTabHost.setCurrentTab(i);
            }
            //title.setText(titles[0]);
            title.setText(labels[0]);
        }

        mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

            public void onTabChanged(String tabId) {
                TextView title = (TextView) findViewById(R.id.window_title);
                if (tabId != null) {
                    /*if (!tabId.equals(labels[0]))
                        title.setText(tabId);
                    else {
                        
                    }*/
                    title.setText(tabId);
                    if (tabId.equals(labels[1]))
                        designListPopulate();

                } else {
                    tutorialListPopulate();
                    //title.setText(titles[0]);
                    title.setText(labels[0]);
                }
            }
        });

        Button resetButton = (Button) findViewById(R.id.reset);
        resetButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                StuyDesignContext.resetInstance();
                designListPopulate();
                globalVariables.resetProgress();
                ProgressBar inputProgress = (ProgressBar) findViewById(R.id.input_progress);
                System.out.println("reset progress : "
                        + globalVariables.getTotalProgress());
                inputProgress.setProgress(globalVariables.getTotalProgress());
            }
        });

        Button calculateButton = (Button) findViewById(R.id.calculate_button);
        ProgressBar loadingProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
        TextView loadingText = (TextView) findViewById(R.id.textView1);
        loadingProgressBar.setVisibility(View.INVISIBLE);
        loadingText.setVisibility(View.INVISIBLE);
        if (StuyDesignContext.getInstance().getTotalProgress() != 6) {
            calculateButton.setClickable(false);
            calculateButton.setEnabled(false);

        } else {
            calculateButton.setEnabled(true);
            calculateButton.setClickable(true);
        }
        calculateButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                v.setVisibility(View.INVISIBLE);
                ProgressBar loadingProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
                TextView loadingText = (TextView) findViewById(R.id.textView1);
                loadingProgressBar.setVisibility(View.VISIBLE);
                loadingText.setVisibility(View.VISIBLE);
            }
        });
    }

    private void tutorialListPopulate() {
        String[] tutorialList = getResources().getStringArray(
                R.array.tutorial_list);

        tutorialListView = (ListView) findViewById(R.id.tutorial_list_view);
        View header1 = getLayoutInflater().inflate(
                R.layout.tutorial_list_header, null, false);
        if (tutorialListView.getHeaderViewsCount() == 0)
            tutorialListView.addHeaderView(header1);

        tutorialListView.setAdapter(new TutorialAdapter(getBaseContext(),
                tutorialList));
    }

    private void designListPopulate() {
        //String solvingFor = StuyDesignContext.getInstance().getStudyDesign().getSolutionTypeEnum().toString();
        String solvingFor = StuyDesignContext.getInstance().getSolvingFor();
       
        String[] designList = getResources().getStringArray(
                R.array.tutorial_list);
        //if(solvingFor != null) {
            String[] temp = new String[7];
            int index = 6;
            while(index > 1) {
                temp[index] = designList[index-1]; 
                index--;
            }
            if(solvingFor != null) 
             temp[1] = solvingFor;            
            temp[0] = designList[0];
            designList = temp;
        //}
        /*if(solvingFor != null) {
            String[] temp = new String[7];
            int index = 0;
            while(index < 6) {
                temp[index+1] = designList[index]; 
                index++;
            }
            temp[6] = solvingFor;
        }*/
        
        designListView = (ListView) findViewById(R.id.design_list_view);

        designListView.setAdapter(new DesignListAdapter(getBaseContext(),
                designList));
        
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
        } else if (tag.compareTo(labels[2]) == 0)
            return tabThreeContentView;
        else
            return tabOneContentView;
    }

    /*
     * public boolean onKeyDown(int keyCode, KeyEvent event) { if (keyCode ==
     * KeyEvent.KEYCODE_BACK) { GlobalVariables.resetInstance(); finish();
     * return true; } return super.onKeyDown(keyCode, event); }
     */

}