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

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.glimmpseandroid.adapter.PowerListAdapter.ViewHolder;
import edu.ucdenver.bios.glimmpseandroid.application.StuyDesignContext;
import edu.ucdenver.bios.webservice.common.domain.RelativeGroupSize;



// TODO: Auto-generated Javadoc
/**
 * The Class RelativeGroupSizeAdapter.
 * @author Uttara Sakhadeo
 */
public class RelativeGroupSizeAdapter extends BaseAdapter{
    
    /** The groups. */
    //private int groups;
    
    //private static List<RelativeGroupSize> list;
    
    /** The m layout inflater. */
    private LayoutInflater mLayoutInflater;
    
    /** The relative group size. */
    static int relativeGroupSize;
    //static ViewHolder holder;
    /** The value text. */
    static TextView valueText; 
    /** The global variables. */
    private static StuyDesignContext globalVariables;
    
    /**
     * Instantiates a new relative group size adapter.
     *
     * @param context the context
     * @param groups the groups
     */
    public RelativeGroupSizeAdapter(Context context){
        
        //this.groups = groups;

        //get the layout inflater
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
        globalVariables = StuyDesignContext.getInstance();
        /*list = globalVariables.getStudyDesign().getRelativeGroupSizeList();
        for(RelativeGroupSize a : list)
            System.out.println("value "+a.getValue());*/
    }       
    
    /* (non-Javadoc)
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    public View getView(final int position, View view, ViewGroup viewGroup) {
        
        ViewHolder holder;      
        
       /*if (view == null) {
           System.out.println("view null");*/
           view = mLayoutInflater.inflate(R.layout.design_relative_group_size_item, null);
           
           holder = new ViewHolder();
           holder.textLine = (TextView) view.findViewById(R.id.list_item_textView_relative_group_size);
           holder.valueLine = (SeekBar) view.findViewById(R.id.relativeSize);
           holder.detailLine = (TextView) view.findViewById(R.id.see_progress); 
           //int tempRelGpSize = list.get(position).getValue();
           int tempRelGpSize = globalVariables.getRelativeGroupSize(position);
           if(tempRelGpSize == 0) {
               globalVariables.setDefaultRelativeGroupSize(position);
               relativeGroupSize = globalVariables.getRelativeGroupSize(position);
               //holder.valueLine.setText(Double.toString(variance));               
               //clear.setVisibility(View.VISIBLE);       
               //relativeGroupSize = list.get(position).getValue();
           }
           else{
               relativeGroupSize = tempRelGpSize;
           }
           
           /*view.setTag(holder);                
       }
       else {
           holder = (ViewHolder) view.getTag();
       }*/          
       //relativeGroupSize = list.get(position).getValue();
       
       System.out.println("getView() position : "+position+" value : "+relativeGroupSize);
       holder.valueLine.setProgress(relativeGroupSize - 1);
       holder.detailLine.setText(""+relativeGroupSize);
       holder.valueLine.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
            
           public void onProgressChanged(SeekBar seekBar, int progress,
                   boolean fromUser) {
               // TODO Auto-generated method stub                   
               relativeGroupSize = progress+ 1;
               RelativeLayout mlayout=(RelativeLayout) seekBar.getParent();
               if (mlayout.getChildCount() > 0) {                   
               valueText = (TextView) mlayout.getChildAt(0);                   
               valueText.setText(""+relativeGroupSize);
               globalVariables.setRelativeGroupSize(relativeGroupSize,position);
            // GlobalVariableApplication.globalVariables.getPowerResult().set   
               }
                           
               
           }

           public void onStartTrackingTouch(SeekBar seekBar) {
               // TODO Auto-generated method stub
           }

           public void onStopTrackingTouch(SeekBar seekBar) {
               // TODO Auto-generated method stub
           }
       });     
       
       //view.setTag(holder);
       //get the string item from the position "position" from array list to put it on the TextView
       String stringItem = "Group "+(position+1);
       if (stringItem != null) {

           TextView itemName = (TextView) view.findViewById(R.id.list_item_textView_relative_group_size);

           if (itemName != null) {
               //set the item name on the TextView
               itemName.setText(stringItem);
           }
       }                           

       //this method must return the view corresponding to the data at the specified position.
       return view;

   }       
   
   /**
    * The Class ViewHolder.
    */
   static class ViewHolder {
       
       /** The text line. */
       TextView textLine;       
       /** The value line. */
       SeekBar valueLine;       
       /** The detail line. */
       TextView detailLine;
    }
   
   /* (non-Javadoc)
    * @see android.widget.Adapter#getItem(int)
    */
   public Object getItem(int i) {
       //return list.get(i).getValue();
       return globalVariables.getRelativeGroupSize(i);
   }

   
       //get the position id of the item from the list
   /* (non-Javadoc)
        * @see android.widget.Adapter#getItemId(int)
        */
       public long getItemId(int i) {
       return i;
   }
   
   /* (non-Javadoc)
    * @see android.widget.Adapter#getCount()
    */
   public int getCount() {
       // TODO Auto-generated method stub
       return globalVariables.getGroups();
   }
     
}