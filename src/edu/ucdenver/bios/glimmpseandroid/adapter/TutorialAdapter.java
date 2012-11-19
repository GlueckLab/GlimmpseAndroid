/*
 * Mobile - Android, User Interface for the GLIMMPSE Software System.  Allows
 * users to perform power, sample size calculations. 
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
package edu.ucdenver.bios.glimmpseandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.glimmpseandroid.activity.TutorialSubScreenActivity;

// TODO: Auto-generated Javadoc
/*
 * @author Uttara Sakhadeo
 */
public class TutorialAdapter extends BaseAdapter implements Filterable{
    
    /** The m list items. */
    private String[] mListItems;
    
    /** The m layout inflater. */
    private LayoutInflater mLayoutInflater;
    
    /** The mcontext. */
    private Context mcontext;
    
    /** The img. */
    private static Drawable img;
    
 
    /**
     * Instantiates a new tutorial adapter.
     *
     * @param context the context
     * @param arrayList the array list
     */
    public TutorialAdapter(Context context, String[] arrayList){
 
        mListItems = arrayList;
 
        //get the layout inflater
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        this.mcontext = context; 
        
        img = context.getResources().getDrawable( R.drawable.ic_list_expand);
        img.setBounds(0,0,30,30);  
    }
 
    
    /* (non-Javadoc)
     * @see android.widget.Adapter#getCount()
     */
    public int getCount() {
        //getCount() represents how many items are in the list
        return mListItems.length;
    }
 
    
        //get the data of an item from a specific position
        //i represents the position of the item in the list
    /* (non-Javadoc)
         * @see android.widget.Adapter#getItem(int)
         */
        public Object getItem(int i) {
        return mListItems[i];
    }
 
    
        //get the position id of the item from the list
    /* (non-Javadoc)
         * @see android.widget.Adapter#getItemId(int)
         */
        public long getItemId(int i) {
        return i;
    }
 
    
 
    /* (non-Javadoc)
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    public View getView(final int position, View view, ViewGroup viewGroup) {
 
    	 ViewHolder holder;    	
    	 //View newView = null;
        //check to see if the reused view is null or not, if is not null then reuse it
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.tutorial_list_item, null);                       
            
            holder = new ViewHolder();
            holder.textLine = (TextView) view.findViewById(R.id.list_item_textView_tutorial);
            holder.detailLine = (TextView) view.findViewById(R.id.expandable_toggle_button_details);
            
            //holder.buttonLine = (ImageButton) view.findViewById(R.id.expandable_toggle_button);
            
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder) view.getTag();
        }
        holder.detailLine.setCompoundDrawables(null, null, img, null);
            view.setOnClickListener(new OnClickListener() {
            	         
            	//Intent intent;
            	
            	public void onClick(View v) {
            	//Toast.makeText(mcontext, "Click-" + String.valueOf(pos), Toast.LENGTH_SHORT).show();      
            		 /*switch(position){
	     	            case 0:
	     	            	intent = new Intent(mcontext, TutorialSolvingForActivity.class);
	     	            	break;
	     	           case 1:
	     	            	intent = new Intent(mcontext, TutorialTypeIErrorActivity.class);
	     	            	break;
	                 }*/
            		/*intent = new Intent(v.getContext(),TutorialSubScreenActivity.class);
            		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    				Bundle bundle = new Bundle();
    				bundle.putInt("sub_screen", position);
    				intent.putExtras(bundle);
    				mcontext.startActivity(intent);*/
            		callIntent(v, position);
            	}
        	});
            	 
        	/*holder.buttonLine.setOnClickListener(new OnClickListener() {
        		private int pos = position;          
            	//Intent intent;
	            	 
	            	public void onClick(View v) {
	            	//Toast.makeText(mcontext, "Delete-" + String.valueOf(pos), Toast.LENGTH_SHORT).show();
	            		switch(position){
	     	            case 0:
	     	            	intent = new Intent(mcontext, TutorialSolvingForActivity.class);
	     	            	break;
	     	           case 1:
	     	            	intent = new Intent(mcontext, TutorialTypeIErrorActivity.class);
	     	            	break;
	            		}
	            		intent = new Intent(v.getContext(),TutorialSubScreenActivity.class);
	            		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    				Bundle bundle = new Bundle();
	    				bundle.putInt("sub_screen", position);
	    				intent.putExtras(bundle);
	            		mcontext.startActivity(intent);
	            		callIntent(v, position);
	            	}
        	});*/
        //}
        //view.setTag(holder);
        //get the string item from the position "position" from array list to put it on the TextView
        String stringItem = mListItems[position];
        if (stringItem != null) {
 
            TextView itemName = (TextView) view.findViewById(R.id.list_item_textView_tutorial);
 
            if (itemName != null) {
                //set the item name on the TextView
                itemName.setText(stringItem);
            }
        }              
        
        
 
        //this method must return the view corresponding to the data at the specified position.
        return view;
 
    }
    
    /**
     * Call intent.
     *
     * @param v the v
     * @param position the position
     */
    public void callIntent(View v, int position) {
    	Intent intent = new Intent(v.getContext(),TutorialSubScreenActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Bundle bundle = new Bundle();
		bundle.putInt("sub_screen", position);
		intent.putExtras(bundle);
		mcontext.startActivity(intent);
		
		
		
    }
    
    /**
     * The Class ViewHolder.
     */
    static class ViewHolder {
    	
	    /** The text line. */
	    TextView textLine;
    	
	    /** The detail line. */
	    TextView detailLine;
    	//ImageButton buttonLine;
    	}

	/* (non-Javadoc)
	 * @see android.widget.Filterable#getFilter()
	 */
	public Filter getFilter() {
		// TODO Auto-generated method stub
		return null;
	}
}
