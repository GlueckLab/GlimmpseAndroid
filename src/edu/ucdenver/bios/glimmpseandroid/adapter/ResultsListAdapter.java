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

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.glimmpseandroid.application.StuyDesignContext;
import edu.ucdenver.bios.webservice.common.domain.PowerResult;
import edu.ucdenver.bios.webservice.common.domain.PowerResultList;

// TODO: Auto-generated Javadoc
/**
 * The Class ResultsListAdapter.
 * 
 * @author Uttara Sakhadeo
 * @version 1.0.0
 */
public class ResultsListAdapter extends BaseAdapter {

    /** The count. */
    private static int count;

    /** The m layout inflater. */
    private LayoutInflater mLayoutInflater;

    /** The list. */
    private static PowerResultList list;

    private static final int MAX_PRECISION = 3;
    private static final MathContext mc = new MathContext(MAX_PRECISION,
            RoundingMode.HALF_EVEN);

    /**
     * Instantiates a new results list adapter.
     * 
     * @param context
     *            the context
     * @param powerResultList
     *            the power result list
     */
    public ResultsListAdapter(Context context, PowerResultList powerResultList) {

        // get the layout inflater
        mLayoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        list = powerResultList;
        count = list.size();

    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getItem(int)
     */
    public Object getItem(int i) {
        return null;
    }

    // get the position id of the item from the list
    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getItemId(int)
     */
    public long getItemId(int i) {
        return 0;
    }

    /**
     * The Class ViewHolder.
     */
    static class ViewHolder {

        /** The power line. */
        TextView powerLine;

        /** The sample size line. */
        TextView sampleSizeLine;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getCount()
     */
    public int getCount() {
        // TODO Auto-generated method stub
        return count;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getView(int, android.view.View,
     * android.view.ViewGroup)
     */
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        view = mLayoutInflater.inflate(R.layout.results_list_item, null);
        holder = new ViewHolder();
        holder.powerLine = (TextView) view
                .findViewById(R.id.list_item_textView_power);
        holder.sampleSizeLine = (TextView) view
                .findViewById(R.id.list_item_textView_sample_size);

        PowerResult result = list.get(position);

        double power = new BigDecimal(result.getNominalPower().getValue(), mc)
                .doubleValue();
        int sampleSize = result.getTotalSampleSize();

        holder.powerLine.setText(Double.toString(power));
        holder.sampleSizeLine.setText(Integer.toString(sampleSize));

        String solvingFor = StuyDesignContext.getInstance().getSolvingFor();
        if (solvingFor != null) {

            String enumPower = mLayoutInflater.getContext().getString(
                    R.string.enum_power_value);
            if (enumPower.equals(solvingFor)) {
                holder.powerLine.setBackgroundColor(Color.argb(10, 10, 10, 10));
            } else {
                holder.sampleSizeLine.setBackgroundColor(Color.argb(10, 10, 10,
                        10));
            }
        }

        return view;
    }
}
