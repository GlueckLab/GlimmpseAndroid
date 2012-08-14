package edu.ucdenver.bios.glimmpseandroid.adapter;

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
import edu.ucdenver.bios.glimmpseandroid.application.StuyDesignContext;



public class RelativeGroupSizeAdapter extends BaseAdapter{
    private int groups;
    private LayoutInflater mLayoutInflater;
    static int relativeGroupSize;
    //static ViewHolder holder;
    static TextView valueText;    
    
    public RelativeGroupSizeAdapter(Context context, int groups){
        
        this.groups = groups;
 
        //get the layout inflater
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);               
    }
    
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
           view = mLayoutInflater.inflate(R.layout.design_relative_group_size_item, null);
           
           holder = new ViewHolder();
           holder.textLine = (TextView) view.findViewById(R.id.list_item_textView_relative_group_size);
           holder.valueLine = (SeekBar) view.findViewById(R.id.relativeSize);
           holder.detailLine = (TextView) view.findViewById(R.id.see_progress);
                      
           if(StuyDesignContext.getInstance().getRelativeGroupSize(position) == 0) {
               StuyDesignContext.getInstance().setDefaultRelativeGroupSize(position);
               //holder.valueLine.setText(Double.toString(variance));               
               //clear.setVisibility(View.VISIBLE);                
           }           
           relativeGroupSize = StuyDesignContext.getInstance().getRelativeGroupSize(position);
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
                   StuyDesignContext.getInstance().setRelativeGroupSize(relativeGroupSize,position);
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

           TextView itemName = (TextView) view.findViewById(R.id.list_item_textView_relative_group_size);

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
       SeekBar valueLine;
       TextView detailLine;
       }
   
   public int getCount() {
       // TODO Auto-generated method stub
       return groups;
   }

   
   private void resetVariance(){
       /*if(relativeGroupSize == null)
           relativeGroupSize = 0;*/
       //GlobalVariables.getInstance().setRelatirelativeGroupSize(relativeGroupSize);    
   }
}