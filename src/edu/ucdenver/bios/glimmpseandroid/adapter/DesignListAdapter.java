/*
 * Mobile - Android, User Interface for the GLIMMPSE Software System.  Allows
 * users to perform power and sample size calculations. 
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
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;
import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.glimmpseandroid.activity.design.GroupCountActivity;
import edu.ucdenver.bios.glimmpseandroid.activity.design.MeansAndVarianceActivity;
import edu.ucdenver.bios.glimmpseandroid.activity.design.PowerActivity;
import edu.ucdenver.bios.glimmpseandroid.activity.design.RelativeGroupSizeActivity;
import edu.ucdenver.bios.glimmpseandroid.activity.design.SampleSizeActivity;
import edu.ucdenver.bios.glimmpseandroid.activity.design.SolvingForActivity;
import edu.ucdenver.bios.glimmpseandroid.activity.design.TypeIErrorActivity;
import edu.ucdenver.bios.glimmpseandroid.application.StuyDesignContext;

// TODO: Auto-generated Javadoc
/**
 * The Class DesignListAdapter handles the List view .
 * 
 * @author Uttara Sakhadeo
 * @version 1.0.0
 */
public class DesignListAdapter extends BaseAdapter implements Filterable {

    /** The list items. */
    private String[] mListItems;

    /** The layout inflater. */
    private LayoutInflater mLayoutInflater;

    /** The Solving For Row Number. */
    private final int SOLVING_FOR_ROW = 0;

    /** The Power/Sample Size Row Number. */
    private final int POWER_OR_SAMPLE_SIZE_ROW = 1;

    /** The Type I Error Row Number. */
    private final int TYPE_I_ERROR_ROW = 2;

    /** The Number of Groups Row Number. */
    private final int NUMBER_OF_GROUPS_ROW = 3;

    /** The Relative Group Size Row Number. */
    private final int RELATIVE_GROUP_SIZE_ROW = 4;

    /** The MEAN s_ varianc e_ row. */
    private final int MEANS_VARIANCE_ROW = 5;

    /** The groups. */
    private static int groups;

    /** The solving for. */
    private String solvingFor;

    /** The equality flag relative gp. */
    private static boolean equalityFlagRelativeGp;

    /** The resources. */
    private Resources resources;

    /** The POWER. */
    private final String POWER = "Power";

    /** The SAMPL e_ size. */
    private final String SAMPLE_SIZE = "Sample Size";

    /** The global variables. */
    StuyDesignContext globalVariables;

    /** The img. */
    private static Drawable img;

    /** The check mark img. */
    private static Drawable checkMarkImg;

    /** The edit img. */
    private static Drawable editImg;

    /**
     * Instantiates a new design list adapter.
     * 
     * @param context
     *            the context
     * @param arrayList
     *            the array list
     */
    public DesignListAdapter(Context context, String[] arrayList) {

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float density = metrics.density;
        int measurement = (int) (density * 20);

        mListItems = arrayList;
        // get the layout inflater
        mLayoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        img = context.getResources().getDrawable(R.drawable.ic_list_expand);
        img.setBounds(0, 0, measurement, measurement);

        checkMarkImg = context.getResources().getDrawable(
                R.drawable.green_checkmark);
        checkMarkImg.setBounds(0, 0, measurement, measurement);

        editImg = context.getResources()
                .getDrawable(R.drawable.incomplete_icon);
        editImg.setBounds(0, 0, measurement, measurement);

        resources = context.getResources();

    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getCount()
     */
    public int getCount() {
        // getCount() represents how many items are in the list
        return mListItems.length;
    }

    /**
     * get the data of an item from a specific position i represents the
     * position of the item in the list
     */
    public Object getItem(int i) {
        return null;
    }

    /**
     * get the position id of the item from the list
     * 
     * @see android.widget.Adapter#getItemId(int)
     */
    public long getItemId(int i) {
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getView(int, android.view.View,
     * android.view.ViewGroup)
     */
    public View getView(final int position, View view, final ViewGroup viewGroup) {
        ViewHolder holder;
        globalVariables = StuyDesignContext.getInstance();
        // check to see if the reused view is null or not, if is not null then
        // reuse it
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.design_list_item, null);            
            holder = new ViewHolder();
            holder.textLine = (TextView) view
                    .findViewById(R.id.list_item_textView_design);
            holder.detailLine = (TextView) view
                    .findViewById(R.id.text_view_value_design);
            holder.detailLine.setCompoundDrawables(null, null, img, null);

            if (globalVariables.getIndividualProgress(position) != 0) {
                holder.textLine.setCompoundDrawables(checkMarkImg, null, null,
                        null);
            } else {
                holder.textLine.setCompoundDrawables(editImg, null, null, null);
            }
            holder.textLine.setCompoundDrawablePadding(3);

            solvingFor = globalVariables.getSolvingFor();

            if (position == POWER_OR_SAMPLE_SIZE_ROW) {
                if (solvingFor == null) {
                    view.setLayoutParams(new AbsListView.LayoutParams(1, 1));
                } else {
                    view.setLayoutParams(new AbsListView.LayoutParams(
                            AbsListView.LayoutParams.WRAP_CONTENT,
                            AbsListView.LayoutParams.WRAP_CONTENT));
                    Integer sampleSizeListSize = globalVariables
                            .getSampleSizeListSize();
                    if (globalVariables.getPowerListSize() > 0
                            || (sampleSizeListSize != null && sampleSizeListSize > 0)) {
                        holder.detailLine.setText(resources
                                .getString(R.string.complete));
                    }
                }

            }

            if (globalVariables.getTotalProgress() != 0) {
                switch (position) {
                case SOLVING_FOR_ROW:
                    if (globalVariables.getIndividualProgress(position) != 0) {
                        solvingFor = globalVariables.getSolvingFor();
                        holder.detailLine.setText(solvingFor);
                    }
                    break;
                case POWER_OR_SAMPLE_SIZE_ROW:
                    if (solvingFor != null) {
                        if (solvingFor.equals(SAMPLE_SIZE)
                                && globalVariables.getPowerListSize() > 0) {
                            holder.detailLine.setText(resources
                                    .getString(R.string.complete));
                        } else if (solvingFor.equals(POWER)) {
                            Integer sampleSizeListSize = globalVariables
                                    .getSampleSizeListSize();
                            if (sampleSizeListSize != null
                                    && sampleSizeListSize > 0) {
                                holder.detailLine.setText(resources
                                        .getString(R.string.complete));
                            } else {
                                holder.detailLine.setText(null);
                            }
                        } else {
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
                    if (globalVariables.getIndividualProgress(position) != 0) {
                        if (groups > 0) {
                            equalityFlagRelativeGp = globalVariables
                                    .relativeGroupEquality();
                            holder.detailLine
                                    .setText((equalityFlagRelativeGp) ? resources
                                            .getString(R.string.equal_relative_group_size)
                                            : resources
                                                    .getString(R.string.unequal_relative_group_size));
                        }
                    }
                    break;
                case MEANS_VARIANCE_ROW:
                    if (groups > 0) {
                        holder.detailLine.setText(resources
                                .getString(R.string.complete));
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
                                "Please Select Number of Groups First",
                                Toast.LENGTH_SHORT)).show();
                    } else {
                        callIntent(v, pos);
                        getView(pos, v, viewGroup);
                    }
                }
            });

        }

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

    /**
     * Call intent.
     * 
     * @param v
     *            the v
     * @param position
     *            the position
     */
    public void callIntent(View v, int position) {
        Intent intent = null;
        switch (position) {
        case SOLVING_FOR_ROW:
            intent = new Intent(v.getContext(), SolvingForActivity.class);
            break;
        case POWER_OR_SAMPLE_SIZE_ROW:
            String data = globalVariables.getSolvingFor();
            if (data.equals(POWER)) {
                intent = new Intent(v.getContext(), SampleSizeActivity.class);
            } else if (data.equals(SAMPLE_SIZE)) {
                intent = new Intent(v.getContext(), PowerActivity.class);
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
        case MEANS_VARIANCE_ROW:
            intent = new Intent(v.getContext(), MeansAndVarianceActivity.class);
            break;
        }
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            v.getContext().startActivity(intent);
        }
    }

    /**
     * The Class ViewHolder.
     */
    static class ViewHolder {

        /** The text line. */
        TextView textLine;

        /** The detail line. */
        TextView detailLine;
        // ImageButton buttonLine;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Filterable#getFilter()
     */
    public Filter getFilter() {
        return null;
    }

}
