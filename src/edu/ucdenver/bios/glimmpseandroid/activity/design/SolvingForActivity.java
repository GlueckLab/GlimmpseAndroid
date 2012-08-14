package edu.ucdenver.bios.glimmpseandroid.activity.design;


import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.glimmpseandroid.application.StuyDesignContext;

public class SolvingForActivity extends Activity implements OnClickListener {
	static RadioGroup values;
	static RadioButton checked;
	static String solvingFor;	
	static StuyDesignContext stuyDesignContext = StuyDesignContext.getInstance();
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
		setContentView(R.layout.design_solving_for);

		if (useTitleFeature) {
			window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		}
		Button homeButton = (Button) findViewById(R.id.home_button);
		homeButton.setOnClickListener(this);

		Button back = (Button) findViewById(R.id.back_button);
		back.setOnClickListener(this);

		TextView title = (TextView) findViewById(R.id.window_title);
		title.setText(getResources().getString(R.string.title_solving_for));

		values = (RadioGroup) findViewById(R.id.radioGroup_solvingFor);
		
		solvingFor = stuyDesignContext.getSolvingFor();
		if(solvingFor != null){
			if(solvingFor.equals(((RadioButton)values.getChildAt(0)).getText())) {				
				((RadioButton)values.getChildAt(0)).setSelected(true);
			}
			else {				
				((RadioButton)values.getChildAt(1)).setChecked(true);
			}
		}
		
				
	}	
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
         }
         return super.onKeyDown(keyCode, event);
	}
	
	public void onClick(View v) {	
		int selected = values.getCheckedRadioButtonId();
		checked = (RadioButton) findViewById(selected);
		solvingFor = ""+checked.getText();
		/*switch(selected){
			case 0:
				
				solvingFor = getResources().getString(R.id.radio_power);
				break;
			case 1:
				solvingFor = getResources().getString(R.id.radio_sample_size);
				break;
		}*/
		stuyDesignContext.setSolvingFor(solvingFor);			
		finish();
	}
}