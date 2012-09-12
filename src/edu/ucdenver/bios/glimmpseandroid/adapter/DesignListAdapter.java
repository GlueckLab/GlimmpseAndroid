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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.glimmpseandroid.activity.design.GroupCountActivity;
import edu.ucdenver.bios.glimmpseandroid.activity.design.MeansAndVarianceActivity;
import edu.ucdenver.bios.glimmpseandroid.activity.design.PowerActivity;
import edu.ucdenver.bios.glimmpseandroid.activity.design.RelativeGroupSizeActivity;
import edu.ucdenver.bios.glimmpseandroid.activity.design.SampleSizeActivity;
import edu.ucdenver.bios.glimmpseandroid.activity.design.SmallestGroupSizeActivity;
import edu.ucdenver.bios.glimmpseandroid.activity.design.SolvingForActivity;
import edu.ucdenver.bios.glimmpseandroid.activity.design.TypeIErrorActivity;
import edu.ucdenver.bios.glimmpseandroid.application.StuyDesignContext;
import edu.ucdenver.bios.webservice.common.enums.SolutionTypeEnum;

public class DesignListAdapter extends BaseAdapter implements Filterable {
    private String[] mListItems;
    private LayoutInflater mLayoutInflater;

    private final int SOLVING_FOR_ROW = 0;
    private final int POWER_OR_SAMPLE_SIZE_ROW = 1;
    private final int TYPE_I_ERROR_ROW = 2;
    private final int NUMBER_OF_GROUPS_ROW = 3;
    private final int RELATIVE_GROUP_SIZE_ROW = 4;
    //private final int SMALLEST_GROUP_SIZE_ROW = 5;
    private final int MEANS_VARIANCE_ROW = 5;

    private static int groups;
    private String solvingFor;
    private static boolean equalityFlagRelativeGp;
    private final String COMPLETE = "Complete";
    private final String POWER = "Power";
    private final String SAMPLE_SIZE = "Sample Size";
    private final String RELATIVE_GP_SIZE_EQUAL = "Equal";
    private final String RELATIVE_GP_SIZE_UNEQUAL = "Unequal";
    StuyDesignContext globalVariables;
    private static Drawable img;
    private static Drawable checkMarkImg;
    //private static Drawable notAllowedImg;

    public DesignListAdapter(Context context, String[] arrayList) {

        mListItems = arrayList;
        // get the layout inflater
        mLayoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        img = context.getResources().getDrawable(R.drawable.ic_list_expand);
        img.setBounds(0, 0, 30, 30);
        
        checkMarkImg = context.getResources().getDrawable(R.drawable.green_checkmark);
        checkMarkImg.setBounds(0, 0, 30, 30);     
        
        /*notAllowedImg = context.getResources().getDrawable(R.drawable.not_allowed);
        notAllowedImg.setBounds(0, 0, 40, 40);*/
    }

    public int getCount() {
        // getCount() represents how many items are in the list
        return mListItems.length;
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

    public View getView(final int position, View view, final ViewGroup viewGroup) {
        ViewHolder holder;
        globalVariables = StuyDesignContext.getInstance();
        // check to see if the reused view is null or not, if is not null then
        // reuse it
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.design_list_item, null);
            System.out.println();
            holder = new ViewHolder();
            holder.textLine = (TextView) view
                    .findViewById(R.id.list_item_textView_design);
            holder.detailLine = (TextView) view
                    .findViewById(R.id.text_view_value_design);            
            holder.detailLine.setCompoundDrawables(null, null, img, null);
            
            if(globalVariables.getIndividualProgress(position) != 0){                
                holder.textLine.setCompoundDrawables(checkMarkImg, null, null, null);
            }
            /*else {
                holder.textLine.setCompoundDrawables(null, null, null, null);
            }*/
            
            /*
             * holder.buttonLine = (ImageButton) view
             * .findViewById(R.id.details_toggle_button);
             */
            
            /*if((position ==  RELATIVE_GROUP_SIZE_ROW || position == MEANS_VARIANCE_ROW) && groups < 2) {
                holder.textLine.setCompoundDrawables(notAllowedImg, null, null, null);
            }*/

            solvingFor = globalVariables.getSolvingFor();
            
            if (position == POWER_OR_SAMPLE_SIZE_ROW) {                
                if (solvingFor == null) {
                    // view.setVisibility(View.INVISIBLE);
                    view.setLayoutParams(new AbsListView.LayoutParams(1, 1));                    
                } else {
                    // view.setVisibility(View.VISIBLE);
                    view.setLayoutParams(new AbsListView.LayoutParams(
                            AbsListView.LayoutParams.WRAP_CONTENT,
                            AbsListView.LayoutParams.WRAP_CONTENT));
                    Integer sampleSizeListSize = globalVariables
                            .getSampleSizeListSize();
                    if (globalVariables.getPowerListSize() > 0
                            || (sampleSizeListSize != null && sampleSizeListSize > 0)) {
                        holder.detailLine.setText(COMPLETE);
                    }                    
                }
                
            } /*else if (position == SMALLEST_GROUP_SIZE_ROW) {
                solvingFor = globalVariables.getSolvingFor();
                // System.out.println("Solving for Type : "+solvingForType);
                if (solvingFor == null) {
                    view.setLayoutParams(new AbsListView.LayoutParams(1, 1));
                } else if (solvingFor != null
                        && solvingFor.equals(SolutionTypeEnum.SAMPLE_SIZE
                                .getId())) {
                    view.setLayoutParams(new AbsListView.LayoutParams(1, 1));                    
                } else {
                    // view.setVisibility(View.VISIBLE);
                    //globalVariables.setSmallestGroupSize(0);
                    view.setLayoutParams(new AbsListView.LayoutParams(
                            AbsListView.LayoutParams.WRAP_CONTENT,
                            AbsListView.LayoutParams.WRAP_CONTENT));
                }                
            }*/
            /*
             * else if(position == RELATIVE_GROUP_SIZE_ROW){
             * //System.out.println("Groups : "+groups); if (groups > 0) {
             * setItemClickable(true, view); } else { setItemClickable(false,
             * view); } } else if(position == MEANS_VARIANCE_ROW){
             * //System.out.println("Groups : "+groups); if (groups > 0) {
             * setItemClickable(true, view); } else { setItemClickable(false,
             * view); } }
             */

            /*if (globalVariables.getTotalProgress() == 0) {
                groups =0;
            }*/            
                        
            if (globalVariables.getTotalProgress() != 0) {
                // System.out.println("adapter total progress : "+globalVariables.getTotalProgress());
                switch (position) {
                case SOLVING_FOR_ROW:
                    if (globalVariables.getIndividualProgress(position) != 0) {
                        solvingFor = globalVariables.getSolvingFor();
                        holder.detailLine.setText(solvingFor);                        
                    }                    
                    break;                
                case POWER_OR_SAMPLE_SIZE_ROW:
                    if (solvingFor != null) {
                       if (solvingFor.equals(SAMPLE_SIZE) && globalVariables.getPowerListSize() > 0) {
                            holder.detailLine.setText(COMPLETE);
                        } else if (solvingFor.equals(POWER)) {
                            Integer sampleSizeListSize = globalVariables
                                    .getSampleSizeListSize();
                            if (sampleSizeListSize != null
                                    && sampleSizeListSize > 0) {
                                holder.detailLine.setText(COMPLETE);
                            }
                            else{
                                holder.detailLine.setText(null);
                            }
                        }else {
                            holder.detailLine.setText(null);
                        }

                    }
                    break;
                case TYPE_I_ERROR_ROW:
                    if (globalVariables.getIndividualProgress(position) != 0) {
                        holder.detailLine.setText(Double
                                .toString(globalVariables.getTypeIError()));                        
                    }
                    break;
                case NUMBER_OF_GROUPS_ROW:
                    if (globalVariables.getIndividualProgress(position) != 0) {
                        int groupChanges = groups - globalVariables.getGroups();
                        /*
                         * groupChanges +ve when groups are removed -ve when
                         * groups are added
                         */
                        if (groupChanges != 0) {
                            globalVariables.synchForGroupChanges(groupChanges);
                        }
                        groups = globalVariables.getGroups();
                        holder.detailLine.setText(Integer.toString(groups));
                        equalityFlagRelativeGp = globalVariables
                                .relativeGroupEquality();
                    }                    
                    break;
                case RELATIVE_GROUP_SIZE_ROW:
                    if (globalVariables.getIndividualProgress(position) != 0)
                    {
                        if (groups > 0) {
                            equalityFlagRelativeGp = globalVariables
                                    .relativeGroupEquality();
                            holder.detailLine
                                    .setText((equalityFlagRelativeGp) ? RELATIVE_GP_SIZE_EQUAL
                                            : RELATIVE_GP_SIZE_UNEQUAL);
                        }
                    }
                    break;                
                case MEANS_VARIANCE_ROW:
                    if (groups > 0) {
                        holder.detailLine.setText(COMPLETE);
                    }                    
                    break;
                }
            } else {
                groups = 0;
            }

            view.setOnClickListener(new OnClickListener() {
                private int pos = position;

                public void onClick(View v) {
                    if (groups < 2
                            && (position == MEANS_VARIANCE_ROW || position == RELATIVE_GROUP_SIZE_ROW)) {
                        (Toast.makeText(v.getContext(),
                                "Please select the number of groups first !!!",
                                Toast.LENGTH_SHORT)).show();
                    } else {
                        callIntent(v, pos);
                        /*if (globalVariables.getIndividualProgress(pos) == 0) {                            
                            globalVariables.setProgress(pos);
                        }*/
                        /*if(pos == NUMBER_OF_GROUPS_ROW){
                            globalVariables.setProgress(RELATIVE_GROUP_SIZE_ROW);
                            globalVariables.setProgress(MEANS_VARIANCE_ROW);
                        }
                        if(pos == SOLVING_FOR_ROW){
                            globalVariables.resetProgress(POWER_OR_SAMPLE_SIZE_ROW);
                        }*/
                        getView(pos, v, viewGroup);
                    }
                    // getView(pos, v, viewGroup);
                }
            });

            /*
             * holder.buttonLine.setOnClickListener(new OnClickListener() {
             * private int pos = position;
             * 
             * public void onClick(View v) { callIntent(v, pos); if
             * (globalVariables.getIndividualProgress(pos) == 0) {
             * globalVariables.setProgress(pos); } getView(pos, v, viewGroup); }
             * });
             */
        }

        // view.setTag(holder);
        // get the string item from the position "position" from array list to
        // put it on the TextView
        String stringItem = mListItems[position];
        if (stringItem != null) {

            TextView itemName = (TextView) view
                    .findViewById(R.id.list_item_textView_design);

            if (itemName != null) {
                // set the item name on the TextView
                itemName.setText(stringItem);
            }
        }

        // this method must return the view corresponding to the data at the
        // specified position.
        return view;

    }

    public void callIntent(View v, int position) {
        Intent intent = null;
        switch (position) {
        case SOLVING_FOR_ROW:
            intent = new Intent(v.getContext(), SolvingForActivity.class);
            break;
        case POWER_OR_SAMPLE_SIZE_ROW:
            String data = globalVariables.getSolvingFor();
            if (data.equals(POWER)) {
                //intent = new Intent(v.getContext(), PowerActivity.class);
                intent = new Intent(v.getContext(), SampleSizeActivity.class);
            } else if (data.equals(SAMPLE_SIZE)) {
                intent = new Intent(v.getContext(), PowerActivity.class);
                //intent = new Intent(v.getContext(), SampleSizeActivity.class);
            }
            break;
        case TYPE_I_ERROR_ROW:
            intent = new Intent(v.getContext(), TypeIErrorActivity.class);
            break;
        case NUMBER_OF_GROUPS_ROW:
            intent = new Intent(v.getContext(), GroupCountActivity.class);
            break;
        case RELATIVE_GROUP_SIZE_ROW:
            intent = new Intent(v.getContext(), RelativeGroupSizeActivity.class);
            break;
        /*case SMALLEST_GROUP_SIZE_ROW:
            //intent = new Intent(v.getContext(), SmallestGroupSizeActivity.class);
            intent = new Intent(v.getContext(), SampleSizeActivity.class);
            break;*/
        case MEANS_VARIANCE_ROW:
            intent = new Intent(v.getContext(), MeansAndVarianceActivity.class);
            break;
        }
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            v.getContext().startActivity(intent);
        }
    }

    static class ViewHolder {
        TextView textLine;
        TextView detailLine;
        // ImageButton buttonLine;
    }

    public Filter getFilter() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * private void setItemClickable(boolean flag, View view){
     * view.setClickable(flag); view.setEnabled(flag); }
     */

}
