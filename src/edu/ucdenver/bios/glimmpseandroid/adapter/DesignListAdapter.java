package edu.ucdenver.bios.glimmpseandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.glimmpseandroid.activity.design.GroupCountActivity;
import edu.ucdenver.bios.glimmpseandroid.activity.design.MeansAndVarianceActivity;
import edu.ucdenver.bios.glimmpseandroid.activity.design.SmallestGroupSizeActivity;
import edu.ucdenver.bios.glimmpseandroid.activity.design.SolvingForActivity;
import edu.ucdenver.bios.glimmpseandroid.activity.design.TypeIErrorActivity;
import edu.ucdenver.bios.glimmpseandroid.application.GlobalVariables;

public class DesignListAdapter extends BaseAdapter implements Filterable{
    private String[] mListItems;
    private LayoutInflater mLayoutInflater;
    private Context mcontext;
    int groups = 2;
    double typeIError = 0.01;
    Integer smallestGroupSize;
    String solvingFor;    
    GlobalVariables globalVariables = GlobalVariables.getInstance();
 
    public DesignListAdapter(Context context, String[] arrayList){
 
        mListItems = arrayList; 
        //get the layout inflater
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);        
        this.mcontext = context;
    }
 
    
    public int getCount() {
        //getCount() represents how many items are in the list
        return mListItems.length;
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
 
    
 
    public View getView(final int position, View view, final ViewGroup viewGroup) {    	
    	 ViewHolder holder;    	 
        //check to see if the reused view is null or not, if is not null then reuse it
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.design_list_item, null);
            
            holder = new ViewHolder();
            holder.textLine = (TextView) view.findViewById(R.id.list_item_textView_design);
            holder.detailLine = (TextView) view.findViewById(R.id.text_view_value_design);
            holder.buttonLine = (ImageButton) view.findViewById(R.id.details_toggle_button);
            if(globalVariables.getTotalProgress() != 0) {
            	//System.out.println("adapter total progress : "+globalVariables.getTotalProgress());
            	switch(position){	            	
	            	case 0:  	            		
	            		if(globalVariables.getIndividualProgress(position) != 0){	
	            			solvingFor = globalVariables.getSolvingFor();
	            			holder.detailLine.setText(solvingFor);
	            		}
	            		break;
	            	case 1:  	            		
	            		if(globalVariables.getIndividualProgress(position) != 0){
		            		typeIError = globalVariables.getTypeIError();
		            		holder.detailLine.setText(Double.toString(typeIError));
	            		}
	            		break;
	            	case 2:  
	            		if(globalVariables.getIndividualProgress(position) != 0){
			            	groups = globalVariables.getGroups();	            	
			            	holder.detailLine.setText(Integer.toString(groups));
	            		}
		            	break;
	            	case 4:  	            		
	            		if(globalVariables.getIndividualProgress(position) != 0){	
	            			smallestGroupSize = globalVariables.getSmallestGroupSize();	            			
	            			holder.detailLine.setText(Integer.toString(smallestGroupSize));	            			
	            		}
	            		break;
	            	case 5:  	            		
	            		if(globalVariables.getIndividualProgress(position) != 0){	
	            			           			
	            		}
	            		break;
	            }
            }
            
            view.setOnClickListener(new OnClickListener() {
            	private int pos = position;          
            	
            	public void onClick(View v) {            	
            		callIntent(v, pos);
            		if(globalVariables.getIndividualProgress(pos) == 0){
            			globalVariables.setProgress(pos);
            		}
            		getView(pos, v, viewGroup);
            	}
        	});
            	 
        	holder.buttonLine.setOnClickListener(new OnClickListener() {
        		private int pos = position;          
            		 
	            	public void onClick(View v) {	            	
	            		callIntent(v, pos);	 	    
	            		if(globalVariables.getIndividualProgress(pos) == 0){
	            			globalVariables.setProgress(pos);
	            		}
	            		getView(pos, v, viewGroup);
	            	}
        	});
        }
                
        //view.setTag(holder);
        //get the string item from the position "position" from array list to put it on the TextView
        String stringItem = mListItems[position];
        if (stringItem != null) {
 
            TextView itemName = (TextView) view.findViewById(R.id.list_item_textView_design);
 
            if (itemName != null) {
                //set the item name on the TextView
                itemName.setText(stringItem);
            }
        }              
        
        //this method must return the view corresponding to the data at the specified position.
        return view;
 
    }
    
    public void callIntent(View v, int position) {
    	Intent intent=null;   
    	Bundle bundle = new Bundle();
    	switch(position){
    	case 0:
    		intent = new Intent(v.getContext(),SolvingForActivity.class);	    	
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);	
    		break; 
    	case 1:
    		intent = new Intent(v.getContext(),TypeIErrorActivity.class);	    	
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);	
    		break;    		
    	case 2:
	    	intent = new Intent(v.getContext(),GroupCountActivity.class);	    	
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);			
			break;
    	case 4:
	    	intent = new Intent(v.getContext(),SmallestGroupSizeActivity.class);	    	
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);			
			break;
    	case 5:
	    	intent = new Intent(v.getContext(),MeansAndVarianceActivity.class);	    	
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);			
			break;
    	}
    	if(intent != null) {    		
    		v.getContext().startActivity(intent);
		}
    }
    
    static class ViewHolder {
    	TextView textLine;
    	TextView detailLine;
    	ImageButton buttonLine;
    	}

	public Filter getFilter() {
		// TODO Auto-generated method stub
		return null;
	}
		
}
