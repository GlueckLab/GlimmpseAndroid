package edu.ucdenver.bios.glimmpseandroid.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import edu.ucdenver.bios.glimmpseandroid.R;
public class TutorialSubScreenActivity extends Activity implements OnClickListener{
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
		setContentView(R.layout.tutorial_sub_screen);
		if (useTitleFeature) {
			 window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				        R.layout.titlebar);
			 
			 Button homeButton = (Button) findViewById(R.id.home_button);
			 homeButton.setOnClickListener(this);
			 
			 Button back = (Button) findViewById(R.id.back_button);
			 back.setOnClickListener(this);
			 
			 TextView title = (TextView)findViewById(R.id.window_title);
			 title.setText(getResources().getString(R.string.title_tutorial));
			 
			 TextView heading = (TextView) findViewById(R.id.title_textView_tutorial_sub_screen);
			 	 
			 TextView description = (TextView) findViewById(R.id.description_textView_tutorial_sub_screen);
			 
			 Bundle extras = this.getIntent().getExtras();
				if (extras != null) {
					int screenId = extras.getInt("sub_screen");
					String subTitle = null;
					String data = null;
					switch(screenId) {
						case 0:
							subTitle = getResources().getString(R.string.tutorial_solving_for);
							data = getResources().getString(R.string.description_tutorial_solving_for);
							break;
						case 1:
							subTitle = getResources().getString(R.string.tutorial_type_one_error);
							data = getResources().getString(R.string.description_tutorial_alpha);
							break;
						case 2:
							subTitle = getResources().getString(R.string.tutorial_number_of_groups);
							data = getResources().getString(R.string.description_tutorial_groups);
							break;
						case 3:
							subTitle = getResources().getString(R.string.tutorial_relative_group_size);
							data = getResources().getString(R.string.description_tutorial_relative_size);
							break;
						case 4:
							subTitle = getResources().getString(R.string.tutorial_smallest_group_size);
							data = getResources().getString(R.string.description_tutorial_smallest_group);
							break;
						case 5:
							subTitle = getResources().getString(R.string.tutorial_means_and_variance);
							data = getResources().getString(R.string.description_tutorial_mean_variance);
							break;
					}
					heading.setText(subTitle);
					description.setText(data);
					 
				} else {
					
				}
			 
			 
		}
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		finish();
	}
}
