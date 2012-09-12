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
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.glimmpseandroid.application.StuyDesignContext;

public class SampleSizeAdapter extends BaseAdapter{
    private LayoutInflater mLayoutInflater;
    private static int count;
    static int sampleSize;
    //static ViewHolder holder;
    static TextView sampleSizeText;
    private static Drawable img;
    private static StuyDesignContext globalVariables = StuyDesignContext.getInstance();
    
    public SampleSizeAdapter(Context context, Integer value){
        
        //get the layout inflater
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Integer size = globalVariables.getSampleSizeListSize();
        System.out.println("sampleSizeAdapter size : "+size);
        if(size == null)
            count = 0;
        else if(count > 0 && size == 0)
            count=0;
        else if(count > size)
            count = size;
        if(value != null && count < 5) { 
            if(value == -1) {
                globalVariables.clearSampleSizeList();
                value = null;
                count = 0;
            }
            else {                
                System.out.println("Count : "+count+" list size : "+size); 
                count++;
                sampleSize = value;
                globalVariables.setSampleSize(sampleSize, count-1);
                System.out.println("SampleSize : "+sampleSize+" Count : "+count+" SampleSize : "+globalVariables.getSampleSize(count-1));
            }
        }
        img = context.getResources().getDrawable( R.drawable.clear_button);
        img.setBounds(0,0,30,30); 
        //notifyDataSetChanged();
    }
    
    public Object getItem(int i) {
        return null;
    }
 
    
        //get the position id of the item from the list
    public long getItemId(int i) {
        return 0;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return count;
    }

    public View getView(final int position, View view, ViewGroup parent) {      
        ViewHolder holder;      
        //check to see if the reused view is null or not, if is not null then reuse it
        //if (view == null) {
            view = mLayoutInflater.inflate(R.layout.design_sample_size_item, null);
            
            holder = new ViewHolder();
            holder.textLine = (TextView) view.findViewById(R.id.list_item_textView_sample_size);            
            
            System.out.println("position : "+position);
            holder.textLine.setText(String.valueOf(globalVariables.getSampleSize(position))); 
            holder.textLine.setCompoundDrawables(null, null, img, null);
            holder.textLine.setOnTouchListener(new OnTouchListener() {
                
                public boolean onTouch(View v, MotionEvent arg1) {
                    sampleSizeText = (TextView) v.findViewById(R.id.list_item_textView_sample_size);
                    if (arg1.getX() > sampleSizeText.getWidth()
                    - img.getIntrinsicWidth() - 10) {
                        globalVariables.clearSampleSize(position);                                        
                        //v.setLayoutParams(new RelativeLayout.LayoutParams(1, 1)); 
                        View parentView = (LinearLayout)v.getParent().getParent();
                        parentView.setLayoutParams(new AbsListView.LayoutParams(1,1));                        
                        //count--;
                    }
                    return false;
                }
        
            });
            
            /*holder.textLine.setOnFocusChangeListener(new OnFocusChangeListener() {

                public void onFocusChange(View v, boolean hasFocus) {
                    System.out.println("calling floodVariance()");                    
                    if (!hasFocus){
                        final int position = v.getId();
                        final TextView text = (TextView) v;
                        myItems.get(position).caption = text.getText().toString();
                    }      
                }
            });*/
            
                        
        //}
        return view;
    }
    
    static class ViewHolder {
        TextView textLine;        
    }
}
