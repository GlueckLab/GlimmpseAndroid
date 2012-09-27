package edu.ucdenver.bios.glimmpseandroid.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.webservice.common.domain.PowerResult;
import edu.ucdenver.bios.webservice.common.domain.PowerResultList;
import edu.ucdenver.bios.webservice.common.enums.SolutionTypeEnum;
import edu.ucdenver.bios.glimmpseandroid.application.StuyDesignContext;

// TODO: Auto-generated Javadoc
/**
 * The Class ResultsListAdapter.
 * @author Uttara Sakhadeo
 */
public class ResultsListAdapter extends BaseAdapter{
    
    /** The count. */
    private static int count;
    
    /** The m layout inflater. */
    private LayoutInflater mLayoutInflater;
    
    /** The list. */
    private static PowerResultList list;

    /**
     * Instantiates a new results list adapter.
     *
     * @param context the context
     * @param powerResultList the power result list
     */
    public ResultsListAdapter(Context context, PowerResultList powerResultList) {

        // get the layout inflater
        mLayoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // this.mcontext = context;   
        list = powerResultList;
        count = list.size();

    }
    
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

    /**
     * The Class ViewHolder.
     */
    static class ViewHolder {
        
        /** The power line. */
        TextView powerLine;
        
        /** The sample size line. */
        TextView sampleSizeLine;        
    }
    
    /* (non-Javadoc)
     * @see android.widget.Adapter#getCount()
     */
    public int getCount() {
        // TODO Auto-generated method stub
        return count;
    }
    
    
    /* (non-Javadoc)
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        
        view = mLayoutInflater.inflate(
                R.layout.results_list_item, null);
        holder = new ViewHolder();
        holder.powerLine = (TextView) view
                .findViewById(R.id.list_item_textView_power);
        holder.sampleSizeLine = (TextView) view.findViewById(R.id.list_item_textView_sample_size);
        
        PowerResult result = list.get(position);
        double power = result.getActualPower();
        int sampleSize = result.getTotalSampleSize();
                
        holder.powerLine.setText(Double.toString(power));
        holder.sampleSizeLine.setText(Integer.toString(sampleSize));
        
        String solvingFor = StuyDesignContext.getInstance().getSolvingFor();
        if(solvingFor != null){
         if(solvingFor.equals(SolutionTypeEnum.POWER.getId())){
             //holder.powerLine.setBackgroundColor(Color.BLUE);
             holder.powerLine.setBackgroundColor(Color.argb(10, 10, 10, 10));
         }
         else{
             //holder.sampleSizeLine.setBackgroundColor(Color.BLUE);
             holder.sampleSizeLine.setBackgroundColor(Color.argb(10, 10, 10, 10));
         }
        }            
        
        return view;
    }
}
