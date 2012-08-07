package edu.ucdenver.bios.glimmpseandroid.activity.design;

import java.text.DecimalFormat;
import java.text.Format;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.glimmpseandroid.application.GlobalVariables;

public class TypeIErrorActivity extends Activity implements OnClickListener, TextWatcher {
	static EditText value;
	static Double alpha;
	static Drawable img;
	static  DecimalFormat format = new DecimalFormat(".##");
	GlobalVariables globalVariables = GlobalVariables.getInstance();

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
		setContentView(R.layout.design_type_one_error);

		if (useTitleFeature) {
			window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
		}
		
		img = getResources().getDrawable( R.drawable.clear_button );
        img.setBounds( 0, 0, 32, 32 );
        
		Button homeButton = (Button) findViewById(R.id.home_button);
		homeButton.setOnClickListener(this);

		Button back = (Button) findViewById(R.id.back_button);
		back.setOnClickListener(this);

		TextView title = (TextView) findViewById(R.id.window_title);
		title.setText(getResources().getString(R.string.title_type_i_error));

		value = (EditText) findViewById(R.id.alpha);
		value.addTextChangedListener(this);
		value.setOnTouchListener(new OnTouchListener() {
            
            public boolean onTouch(View arg0, MotionEvent arg1) {               
            if (arg1.getX() > value.getWidth()
            - img.getIntrinsicWidth() - 10) {
                value.setText("");
                resetText();
            }
            return false;
            }
    
        });
		
		alpha = globalVariables.getTypeIError();
		if(alpha != null){		    
		    alpha = Double.parseDouble(format.format(alpha));
		    value.setText(String.format("%010d", toInt(alpha)));
		    value.setCompoundDrawables( null, null, img, null );
		}
		else {
		    value.setText("");
		    value.setCompoundDrawables( null, null, null, null );
		}
		
	}	
	
	private Double toDouble(int value){
	    return Double.parseDouble(format.format(new Double(value)));
	}
	
	private int toInt(Double value) {
	   return new Double(value*100).intValue();
	   //return Integer.parseInt(data);	   
	}
		
	
	private void resetText(){
        if(alpha == null)
            alpha = Double.parseDouble(format.format(0));
        GlobalVariables.getInstance().setTypeIError(alpha);  
    }
		
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub
        
    }

    public void beforeTextChanged(CharSequence s, int start, int count,
            int after) {
        // TODO Auto-generated method stub
        
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // TODO Auto-generated method stub
        if(String.valueOf(s) != null || String.valueOf(s).equals("")) {
            value.setCompoundDrawables( null, null, img, null );
            try {
                alpha = toDouble(Integer.parseInt(String.valueOf(s)));
                //clear.setVisibility(View.VISIBLE);
            }
            catch(Exception e){
                alpha = Double.parseDouble(format.format(0));
                value.setCompoundDrawables( null, null, null, null );
                //clear.setVisibility(View.INVISIBLE);
            }
        }
        else {
            value.setCompoundDrawables( null, null, null, null );
            //clear.setVisibility(View.INVISIBLE);
        }
    }
    
    public void onClick(View v) {   
        globalVariables.setTypeIError(alpha);       
        finish();
    }

}