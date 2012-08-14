package edu.ucdenver.bios.glimmpseandroid.activity.design;

import java.util.List;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.glimmpseandroid.adapter.MeansAndVarianceAdapter;
import edu.ucdenver.bios.glimmpseandroid.application.GlobalVariables;
import edu.ucdenver.bios.glimmpseandroid.application.StuyDesignContext;;

public class MeansAndVarianceActivity extends Activity implements OnClickListener{
	private ListView meanVarianceList;
	static Drawable img;
	
	
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
		setContentView(R.layout.design_means_and_variance);

		if (useTitleFeature) {
			window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		}
		Button homeButton = (Button) findViewById(R.id.home_button);
		homeButton.setOnClickListener(this);

		Button back = (Button) findViewById(R.id.back_button);
		back.setOnClickListener(this);

		TextView title = (TextView) findViewById(R.id.window_title);
		title.setText(getResources().getString(R.string.title_means_and_variance));
		
		img = getResources().getDrawable( R.drawable.clear_button );
		img.setBounds( 0, 0, 32, 32 );

		meanVarianceList = (ListView)findViewById(R.id.means_and_variance_list_view);
		View header =  getLayoutInflater().inflate(R.layout.design_means_and_variance_list_header, null, false);
        if(meanVarianceList.getHeaderViewsCount() == 0)
            meanVarianceList.addHeaderView(header);
        int groups = StuyDesignContext.getInstance().getGroups();
        if(groups > 0)
            meanVarianceList.setAdapter(new MeansAndVarianceAdapter(MeansAndVarianceActivity.this,groups, img));
		
		
	}	
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
         }
         return super.onKeyDown(keyCode, event);
	}
	
	
	public void onClick(View v) {		
		//GlobalVariables.getInstance().setMeans(means);		    
		finish();
	}
	
}
