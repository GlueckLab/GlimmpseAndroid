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
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.glimmpseandroid.application.StuyDesignContext;
import edu.ucdenver.bios.webservice.common.domain.SampleSize;

// TODO: Auto-generated Javadoc
/**
 * The Class SampleSizeAdapter.
 * @author Uttara Sakhadeo
 */
public class SampleSizeAdapter extends BaseAdapter{
    
    /** The m layout inflater. */
    private LayoutInflater mLayoutInflater;
    
    private static List<SampleSize> list;
    /** The sample size. */
    static int sampleSize;
    
    /** The sample size text. */
    static TextView sampleSizeText;
    
    /** The img. */
    private static Drawable img;
    
    /** The global variables. */
    private static StuyDesignContext globalVariables;
    
    /**
     * Instantiates a new sample size adapter.
     *
     * @param context the context
     * @param value the value
     */
    public SampleSizeAdapter(Context context, Integer value){
        
        //get the layout inflater
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        globalVariables = StuyDesignContext.getInstance();
        list = globalVariables.getStudyDesign().getSampleSizeList();
        if(value != null) { 
            if(value == -1) {
                globalVariables.clearSampleSizeList();
                value = null;                
            }
            else if (getCount() < 5) {                
                sampleSize = value;
                globalVariables.setSampleSize(sampleSize, getCount());                
            }
        }
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();        
        float density = metrics.density;          
        int measurement = (int)(density*20);
        
        img = context.getResources().getDrawable( R.drawable.clear_button);
        img.setBounds(0,0,measurement,measurement);         
    }
    
    /* (non-Javadoc)
     * @see android.widget.Adapter#getItem(int)
     */
    public Object getItem(int i) {
        return list.get(i).getValue();
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
        return globalVariables.getSampleSizeListSize();        
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    public View getView(final int position, View view, ViewGroup parent) {      
        ViewHolder holder;      
        //check to see if the reused view is null or not, if is not null then reuse it
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.design_sample_size_item, null);
            
            holder = new ViewHolder();
            holder.textLine = (TextView) view.findViewById(R.id.list_item_textView_sample_size);            
            
            view.setTag(holder);
            
        }
        else {
            holder = (ViewHolder) view.getTag();
        }
        
        holder.textLine.setText(String.valueOf(globalVariables.getSampleSize(position))); 
        holder.textLine.setCompoundDrawables(null, null, img, null);
        holder.textLine.setOnTouchListener(new OnTouchListener() {
            
            public boolean onTouch(View v, MotionEvent arg1) {
                sampleSizeText = (TextView) v.findViewById(R.id.list_item_textView_sample_size);
                if (arg1.getX() > sampleSizeText.getWidth()
                - img.getIntrinsicWidth() - 10) {
                    globalVariables.clearSampleSize(position);                                        
                    notifyDataSetChanged();
                }
                return false;
            }
    
        });
        
        return view;
    }
    
    /**
     * The Class ViewHolder.
     */
    static class ViewHolder {
        
        /** The text line. */
        TextView textLine;        
    }
}
