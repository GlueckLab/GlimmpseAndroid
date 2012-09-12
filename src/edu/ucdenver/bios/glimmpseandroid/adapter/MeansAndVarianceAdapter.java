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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.glimmpseandroid.application.StuyDesignContext;

public class MeansAndVarianceAdapter extends BaseAdapter {
    private int groups;
    private LayoutInflater mLayoutInflater;
    // private Context mcontext;
    static Drawable img;
    static double variance;
    static double mean;
    static final double DEFAULT_VARIANCE = 1.0;
    static final double DEFAULT_MEAN = 0.0;
    // static ViewHolder holder;
    static EditText varianceText;
    static EditText meanText;
    static View v;

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
    public Object getItem(int i) {
        return null;
    }

    // get the position id of the item from the list
    public long getItemId(int i) {
        return 0;
    }

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
            holder.meanLine.setText(Double.toString(mean));
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
                    String value = String.valueOf(s);
                    if (value != null || !value.isEmpty()) {
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
                    //System.out.println("mean : "+mean+" getMean : "+StuyDesignContext.getInstance().getMean(position));
                }
            });
            
           /* if(position == 0) {
                holder.meanLine.requestFocusFromTouch();
            }*/
            
            variance = StuyDesignContext.getInstance().getVariance();
            holder.varianceLine.setText(Double.toString(variance));
            holder.varianceLine.setCompoundDrawables(null, null, img, null);
            /*if(variance != DEFAULT_VARIANCE) {
                holder.varianceLine.setText(Double.toString(variance));
                holder.varianceLine.setCompoundDrawables(null, null, img, null);
            } else {
                holder.varianceLine.setText("");
                holder.varianceLine.setCompoundDrawables(null, null, null, null);
            }*/
            

            holder.varianceLine.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                    // TODO Auto-generated method stub
                    /*
                     * if(variance!=null) {
                     * holder.varianceLine.setText(variance.toString());
                     * GlobalVariables.getInstance().setVariance(variance); }
                     */
                    if(variance < 1)
                        variance = 1.0;
                    StuyDesignContext.getInstance().setVariance(variance);
                    System.out.println("Variance : "+variance+" getVariance : "+StuyDesignContext.getInstance().getVariance());
                }

                public void beforeTextChanged(CharSequence s, int start, int count,
                        int after) {
                    // TODO Auto-generated method stub

                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // TODO Auto-generated method stub
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

            // holder.varianceLine.setOnFocusChangeListener(this);
            holder.varianceLine
                    .setOnFocusChangeListener(new OnFocusChangeListener() {

                        public void onFocusChange(View v, boolean hasFocus) {
                            // TODO Auto-generated method stub
                            //System.out.println("calling floodVariance()");
                            floodVariance();                            
                        }
                    });           
                
            /*
             * holder.varianceLine.setOnKeyListener(new OnKeyListener() {
             * 
             * public boolean onKey(View v, int keyCode, KeyEvent event) { if
             * (event.getAction() == KeyEvent.ACTION_DOWN) { if (keyCode ==
             * KeyEvent.KEYCODE_ENTER) { floodVariance(); } else if (keyCode ==
             * KeyEvent.KEYCODE_0 || keyCode == KeyEvent.KEYCODE_1 || keyCode ==
             * KeyEvent.KEYCODE_2 || keyCode == KeyEvent.KEYCODE_3 || keyCode ==
             * KeyEvent.KEYCODE_4 || keyCode == KeyEvent.KEYCODE_5 || keyCode ==
             * KeyEvent.KEYCODE_6 || keyCode == KeyEvent.KEYCODE_7 || keyCode ==
             * KeyEvent.KEYCODE_8 || keyCode == KeyEvent.KEYCODE_9) { String
             * data =
             * varianceText.getText().toString().concat(String.valueOf(keyCode
             * )); varianceText.setText(data); } } return true; } });
             */

        //}
        // view.setTag(holder);
        // get the string item from the position "position" from array list to
        // put it on the TextView
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

    static class ViewHolder {
        TextView textLine;
        EditText meanLine;
        EditText varianceLine;
    }
    
    public int getCount() {
        // TODO Auto-generated method stub
        return groups;
    }

     

    private void floodVariance() {
        /*ListView listView = (ListView) ((LinearLayout) ((RelativeLayout) varianceText
                .getParent()).getParent()).getParent();*/
        ListView listView = (ListView) ((RelativeLayout) varianceText
                .getParent()).getParent();
        int children = listView.getChildCount();
        for (int index = 1; index < children; index++) {
            // System.out.println("children : "+((RelativeLayout)((LinearLayout)listView.getChildAt(1)).getChildAt(0)));
            // EditText text =
            // (EditText)((RelativeLayout)listView.getChildAt(index)).getChildAt(0);            
            /*EditText text = (EditText) ((RelativeLayout) ((LinearLayout) listView
                    .getChildAt(index)).getChildAt(0)).getChildAt(1);*/
            EditText text = (EditText) ((RelativeLayout) (listView
                    .getChildAt(index))).getChildAt(1);
            text.setText(Double.toString(variance));
            text.setCompoundDrawables(null, null, img, null);   
            /*EditText text = (EditText) ( listView
                    .getChildAt(index));
            text.setText(Double.toString(variance));
            text.setCompoundDrawables(null, null, img, null); */            
        }
    }

    /*
     * public void onFocusChange(View v, boolean hasFocus) { // TODO
     * Auto-generated method stub //LinearLayout mlayout=
     * (LinearLayout)((RelativeLayout) varianceText.getParent()).getParent();
     * floodVariance(); }
     */

    /*public boolean onKeyDown(int keyCode, KeyEvent event) { 
        System.out.println("abc");
        //if(EditText == event.getSource()) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            System.out.println("Source "+event.getSource());
            floodVariance();
            return true;
        }
        return true;
        //}
        //return false;
    }*/

}