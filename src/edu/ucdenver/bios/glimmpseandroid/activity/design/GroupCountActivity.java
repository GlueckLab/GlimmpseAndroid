package edu.ucdenver.bios.glimmpseandroid.activity.design;


import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.glimmpseandroid.application.GlobalVariables;

public class GroupCountActivity extends Activity implements OnClickListener {
	static SeekBar seekbar;
	static TextView value;
	static int groups;	
	//static GlobalVariables globalVariables;

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
		setContentView(R.layout.design_number_of_groups);

		if (useTitleFeature) {
			window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		}
		Button homeButton = (Button) findViewById(R.id.home_button);
		homeButton.setOnClickListener(this);

		Button back = (Button) findViewById(R.id.back_button);
		back.setOnClickListener(this);

		TextView title = (TextView) findViewById(R.id.window_title);
		title.setText(getResources().getString(R.string.title_groups));

		value = (TextView) findViewById(R.id.see_progress);
		seekbar = (SeekBar) findViewById(R.id.seekbar);

		/*
		 * Bundle extras = this.getIntent().getExtras(); if(extras != null) {
		 * if(extras.containsKey("groups")) { groups = extras.getInt("groups");
		 * seekbar.setProgress(groups-2);
		 * value.setText(Integer.toString(groups)); } }
		 */
		groups = GlobalVariables.getInstance().getGroups();
		seekbar.setProgress(groups
				- Integer.parseInt(getResources().getString(
						R.string.default_groups)));
		value.setText(Integer.toString(groups));

		/*
		 * List<BetweenParticipantFactor> list = new
		 * ArrayList<BetweenParticipantFactor>(2); int i=0; while(i<2){
		 * list.add(new BetweenParticipantFactor()); i++; } StudyDesign sd =
		 * GlobalVariables.getInstance().getStudyDesign();
		 * sd.setBetweenParticipantFactorList(list);
		 */

		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				groups = progress
						+ Integer.parseInt(getBaseContext().getString(
								R.string.default_groups));
				value.setText(Integer.toString(groups));
				// GlobalVariableApplication.globalVariables.getPowerResult().set				
				
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}

			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}
		});
	}	
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
         }
         return super.onKeyDown(keyCode, event);
	}
	
	public void onClick(View v) {
		/*
		 * // TODO Auto-generated method stub Bundle bundle = new Bundle();
		 * bundle.putInt("design_list_item", 2); value = (TextView)
		 * findViewById(R.id.see_progress); bundle.putInt("group_count",
		 * Integer.parseInt(value.getText().toString())); Intent result=new
		 * Intent(GroupCountActivity.this,DesignListAdapter.class); //Intent
		 * result=new Intent(); result.putExtras(bundle); setResult(0, result);
		 */
		GlobalVariables.getInstance().setGroups(groups);			
		finish();
	}
}