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
import edu.ucdenver.bios.glimmpseandroid.application.GlobalVariables;


// TODO: Auto-generated Javadoc
/**
 * The Class MainActivity.
 */
public class MainActivity extends Activity {
	
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
		// If the window has a container, then we are not free
		// to request window features.
		if (window.getContainer() == null) {
		    useTitleFeature = window
		        .requestFeature(Window.FEATURE_CUSTOM_TITLE);
		}

		setContentView(R.layout.home_screen);		
		
		if (useTitleFeature) {
		    window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
		        R.layout.titlebar);


		Resources res = getResources();
		
		Button homeButton = (Button) findViewById(R.id.home_button);
		homeButton.setVisibility(View.GONE);
						
		TextView title = (TextView)findViewById(R.id.window_title);
		title.setText(res.getString(R.string.app_name));
		
		
		}

		Button start = (Button) this.findViewById(R.id.start_button_home);
		start.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent startIntent = new Intent(v.getContext(),
						StartActivity.class);
				Intent tabIntent = new Intent(v.getContext(),TabViewActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("tab_id", 1);
				tabIntent.putExtras(bundle);				
				startActivity(tabIntent);
			}

		});

		Button learnMore = (Button) this.findViewById(R.id.learn_more_button_home);
		learnMore.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent learnMoreIntent = new Intent(v.getContext(),
						TutorialActivity.class);
				//startActivityForResult(learnMoreIntent, 0);
				Intent tabIntent = new Intent(v.getContext(),TabViewActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("tab_id", 0);
				tabIntent.putExtras(bundle);
				startActivity(tabIntent);
			}

		});
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        	GlobalVariables.resetInstance();
            finish();
            return true;
         }
         return super.onKeyDown(keyCode, event);
	}
	

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) {
	 * getMenuInflater().inflate(R.menu.activity_main, menu); return true; }
	 * 
	 * @Override public boolean onOptionsItemSelected(MenuItem item) { // Handle
	 * item selection switch (item.getItemId()) { case R.id.menu_home: finish();
	 * return true; default: return super.onOptionsItemSelected(item); } }
	 */
}
