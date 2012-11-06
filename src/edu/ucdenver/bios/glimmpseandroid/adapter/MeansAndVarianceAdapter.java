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

import java.text.DecimalFormat;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.glimmpseandroid.application.StuyDesignContext;

// TODO: Auto-generated Javadoc
/**
 * The Class MeansAndVarianceAdapter.
 * @author Uttara Sakhadeo
 */
public class MeansAndVarianceAdapter extends BaseAdapter {
    
    private final int ROW_ONE = 0;
    
    /** The groups. */
    private int groups;
    
    /** The m layout inflater. */
    private LayoutInflater mLayoutInflater;
    // private Context mcontext;
    /** The img. */
    static Drawable img;
    
    /** The variance. */
    static double variance;
    
    /** The mean. */
    static double mean;
    
    /** The Constant DEFAULT_VARIANCE. */
    static final double DEFAULT_VARIANCE = 1;
    
    /** The Constant DEFAULT_MEAN. */
    static final double DEFAULT_MEAN = 0;
    // static ViewHolder holder;
    /** The variance text. */
    static EditText varianceText;
    
    /** The mean text. */
    static EditText meanText;
    
    /** The v. */
    static View v;

    /**
     * Instantiates a new means and variance adapter.
     *
     * @param context the context
     * @param groups the groups
     * @param img the img
     */
    public MeansAndVarianceAdapter(Context context, int groups, Drawable img) {

        this.groups = groups;

        // get the layout inflater
        mLayoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // this.mcontext = context;

        this.img = img;
        img.setBounds(0, 0, 20, 20);

    }

    // get the data of an item from a specific position
    // i represents the position of the item in the list
    /* (non-Javadoc)
     * @see android.widget.Adapter#getItem(int)
     */
    public Object getItem(int i) {
        return null;
    }

    // get the position id of the item from the list
    /* (non-Javadoc)
     * @see android.widget.Adapter#getItemId(int)
     */
    public long getItemId(int i) {
        return 0;
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    public View getView(final int position, View view, ViewGroup viewGroup) {
        v = view;
        ViewHolder holder;
        // check to see if the reused view is null or not, if is not null then
        // reuse it
        //if (view == null) {
            view = mLayoutInflater.inflate(
                    R.layout.design_means_and_variance_item, null);

            holder = new ViewHolder();
            holder.textLine = (TextView) view
                    .findViewById(R.id.list_item_textView_group);
            holder.meanLine = (EditText) view.findViewById(R.id.mean);
            holder.varianceLine = (EditText) view.findViewById(R.id.variance);
            
            InputMethodManager imm = (InputMethodManager)mLayoutInflater.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(holder.meanLine.getWindowToken(), 0);
            imm.hideSoftInputFromWindow(holder.varianceLine.getWindowToken(), 0);
            
            mean = StuyDesignContext.getInstance().getMean(position);
            double temp = mean % 1;
            //System.out.println(temp);
            DecimalFormat format = new DecimalFormat("#.0");
            temp = Double.parseDouble(format.format(temp));            
            if(temp != 0.0)
                holder.meanLine.setText(Double.toString(mean));
            else{
                //format = new DecimalFormat("#");
                holder.meanLine.setText(Integer.toString(((Double)mean).intValue()));
            }
            holder.meanLine.setSelection(holder.meanLine.getText().length());
            holder.meanLine.setCompoundDrawables(null, null, img, null);
            holder.meanLine.setOnTouchListener(new OnTouchListener() {

                public boolean onTouch(View v, MotionEvent arg1) {
                    meanText = (EditText) v.findViewById(R.id.mean);
                    if (arg1.getX() > meanText.getWidth()
                            - img.getIntrinsicWidth() + 10) {
                        meanText.setText("");
                        mean = DEFAULT_MEAN;                                              
                    }
                    return false;
                }

            });
            holder.meanLine.addTextChangedListener(new TextWatcher() {
                
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    meanText = (EditText) v.findViewById(R.id.mean);
                    String value = String.valueOf(s);
                    if (value != null && !value.isEmpty()) {
                        try {                                 
                           mean = Double.parseDouble(value);     
                           meanText.setCompoundDrawables(null, null, img, null);                            
                        } catch (Exception e) {
                            System.out.println("Exception : "+e.getMessage());
                            mean = DEFAULT_MEAN;                                        
                        }
                    } else {
                        mean = DEFAULT_MEAN;  
                        meanText.setCompoundDrawables(null, null, null, null);                        
                    }
                }
                
                public void beforeTextChanged(CharSequence s, int start, int count,
                        int after) {
                    // TODO Auto-generated method stub
                    
                }
                
                public void afterTextChanged(Editable s) {                    
                    StuyDesignContext.getInstance().setMean(mean,position);
                    System.out.println("mean : "+mean+" getMean : "+StuyDesignContext.getInstance().getMean(position));
                }
            });
            
           /* if(position == 0) {
                holder.meanLine.requestFocusFromTouch();
            }*/
            
            if(position == ROW_ONE){
                variance = StuyDesignContext.getInstance().getVariance();
                //holder.varianceLine.setText(Double.toString(variance));
                temp = variance % 1;
                format = new DecimalFormat("#.0");
                temp = Double.parseDouble(format.format(temp));            
                if(temp != 0.0)
                    holder.varianceLine.setText(Double.toString(variance));
                else{
                    //format = new DecimalFormat("#");
                    holder.varianceLine.setText(Integer.toString(((Double)variance).intValue()));
                }
                holder.varianceLine.setSelection(holder.varianceLine.getText().length());
                holder.varianceLine.setCompoundDrawables(null, null, img, null);
                
                
    
                holder.varianceLine.addTextChangedListener(new TextWatcher() {
    
                    public void afterTextChanged(Editable s) {                        
                        if(variance < 1)
                            variance = 1.0;
                        StuyDesignContext.getInstance().setVariance(variance);                    
                    }
    
                    public void beforeTextChanged(CharSequence s, int start, int count,
                            int after) {                        
    
                    }
    
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // TODO Auto-generated method stub
                        //varianceText = (EditText) v.findViewById(R.id.variance);
                        if (String.valueOf(s) != null || String.valueOf(s).equals("")) {
                            //
                            try {      
                                String value = String.valueOf(s);
                                if(value != null && !value.isEmpty()) {
                                    variance = Double.parseDouble(value);     
                                    varianceText.setCompoundDrawables(null, null, img, null);
                                }
                                else {                    
                                    variance = DEFAULT_VARIANCE;    
                                }
                                // clear.setVisibility(View.VISIBLE);
                            } catch (Exception e) {
                                System.out.println("Exception : "+e.getMessage());
                                variance = DEFAULT_VARIANCE;                
                                // clear.setVisibility(View.INVISIBLE);
                            }
                        } else {
                            varianceText.setCompoundDrawables(null, null, null, null);
                            // clear.setVisibility(View.INVISIBLE);
                        }
                    } 
                    
                });
                holder.varianceLine.setOnTouchListener(new OnTouchListener() {
    
                    public boolean onTouch(View v, MotionEvent arg1) {
                        varianceText = (EditText) v.findViewById(R.id.variance);
                        if (arg1.getX() > varianceText.getWidth()
                                - img.getIntrinsicWidth() + 10) {
                            varianceText.setText("");
                            variance = DEFAULT_VARIANCE;                                              
                        }
                        return false;
                    }
    
                });
            }
            else{
                holder.varianceLine.setVisibility(View.GONE);  
            }
            
        String stringItem = "Group " + (position + 1);
        if (stringItem != null) {

            TextView itemName = (TextView) view
                    .findViewById(R.id.list_item_textView_group);

            if (itemName != null) {
                // set the item name on the TextView
                itemName.setText(stringItem);
            }
        }
                

        // this method must return the view corresponding to the data at the
        // specified position.
        return view;

    }

    /**
     * The Class ViewHolder.
     */
    static class ViewHolder {
        
        /** The text line. */
        TextView textLine;
        
        /** The mean line. */
        EditText meanLine;
        
        /** The variance line. */
        EditText varianceLine;
    }
    
    /* (non-Javadoc)
     * @see android.widget.Adapter#getCount()
     */
    public int getCount() {
        // TODO Auto-generated method stub
        return groups;
    }    

}