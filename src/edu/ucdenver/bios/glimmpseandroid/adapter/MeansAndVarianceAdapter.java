package edu.ucdenver.bios.glimmpseandroid.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.glimmpseandroid.application.GlobalVariables;

public class MeansAndVarianceAdapter extends BaseAdapter implements Filterable, TextWatcher{
    private int groups;
    private LayoutInflater mLayoutInflater;
    //private Context mcontext;    
    static Drawable img;
    static Double variance;
    //static ViewHolder holder;
    static EditText varianceText;
    
    public MeansAndVarianceAdapter(Context context, int groups, Drawable img){
 
        this.groups = groups;
 
        //get the layout inflater
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        //this.mcontext = context;
        
        this.img = img;
		img.setBounds( 0, 0, 32, 32 );
    }
 
        
        //get the data of an item from a specific position
        //i represents the position of the item in the list
    public Object getItem(int i) {
        return null;
    }
 
    
        //get the position id of the item from the list
    public long getItemId(int i) {
        return 0;
    }
 
    
 
    public View getView(final int position, View view, ViewGroup viewGroup) {
 
    	 ViewHolder holder;    	 
        //check to see if the reused view is null or not, if is not null then reuse it
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.design_means_and_variance_item, null);
            
            holder = new ViewHolder();
            holder.textLine = (TextView) view.findViewById(R.id.list_item_textView_group);
            holder.meanLine = (EditText) view.findViewById(R.id.mean);
            holder.varianceLine = (EditText) view.findViewById(R.id.variance);
            
            variance = GlobalVariables.getInstance().getVariance();
    		if(variance != null) {
    			holder.varianceLine.setText(Double.toString(variance));
    			holder.varianceLine.setCompoundDrawables( null, null, img, null );	
    			//clear.setVisibility(View.VISIBLE);    			
    		}
    		
    		holder.varianceLine.addTextChangedListener(this);
    		holder.varianceLine.setOnTouchListener(new OnTouchListener() {
    			
    			public boolean onTouch(View v, MotionEvent arg1) {
    				varianceText = (EditText) v.findViewById(R.id.variance);
    			if (arg1.getX() > varianceText.getWidth()
    			- img.getIntrinsicWidth() - 10) {
    				varianceText.setText("");
    				resetVariance();
    			}
    			return false;
    			}
    	
    		});
            
            /*view.setOnClickListener(new OnClickListener() {
            	private int pos = position;          
            	//Intent intent;
            	
            	public void onClick(View v) {            	
            		
            	}
        	});*/
            	         	
        }
        //view.setTag(holder);
        //get the string item from the position "position" from array list to put it on the TextView
        String stringItem = "Group "+(position+1);
        if (stringItem != null) {
 
            TextView itemName = (TextView) view.findViewById(R.id.list_item_textView_group);
 
            if (itemName != null) {
                //set the item name on the TextView
                itemName.setText(stringItem);
            }
        }              
        
        
 
        //this method must return the view corresponding to the data at the specified position.
        return view;
 
    }       
    
    static class ViewHolder {
    	TextView textLine;
    	EditText meanLine;
    	EditText varianceLine;
    	}

	public Filter getFilter() {
		// TODO Auto-generated method stub
		return null;
	}


	public int getCount() {
		// TODO Auto-generated method stub
		return groups;
	}


	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		/*if(variance!=null) {
			holder.varianceLine.setText(variance.toString());
			GlobalVariables.getInstance().setVariance(variance);
		}*/
			
	}


	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}


	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub		
		if(String.valueOf(s) != null || String.valueOf(s).equals("")) {
			varianceText.setCompoundDrawables( null, null, img, null );
			try {
				variance = Double.parseDouble(String.valueOf(s));
				//clear.setVisibility(View.VISIBLE);
			}
			catch(Exception e){
				variance = 0.0;
				varianceText.setCompoundDrawables( null, null, null, null );
				//clear.setVisibility(View.INVISIBLE);
			}
		}
		else {
			varianceText.setCompoundDrawables( null, null, null, null );
			//clear.setVisibility(View.INVISIBLE);
		}
	}
	
	private void resetVariance(){
		if(variance == null)
			variance = 0.0;
		GlobalVariables.getInstance().setVariance(variance);	
	}
}