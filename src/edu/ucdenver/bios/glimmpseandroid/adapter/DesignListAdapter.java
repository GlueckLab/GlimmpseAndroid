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
import android.widget.TextView;
import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.glimmpseandroid.activity.design.GroupCountActivity;
import edu.ucdenver.bios.glimmpseandroid.activity.design.MeansAndVarianceActivity;
import edu.ucdenver.bios.glimmpseandroid.activity.design.RelativeGroupSizeActivity;
import edu.ucdenver.bios.glimmpseandroid.activity.design.SmallestGroupSizeActivity;
import edu.ucdenver.bios.glimmpseandroid.activity.design.SolvingForActivity;
import edu.ucdenver.bios.glimmpseandroid.activity.design.TypeIErrorActivity;
import edu.ucdenver.bios.glimmpseandroid.application.StuyDesignContext;
import edu.ucdenver.bios.webservice.common.enums.SolutionTypeEnum;

public class DesignListAdapter extends BaseAdapter implements Filterable {
    private String[] mListItems;
    private LayoutInflater mLayoutInflater;
    private static int groups;
    private String solvingFor;
    private static boolean equalityFlagRelativeGp;
    private final String RELATIVE_GP_SIZE_EQUAL = "Equal";
    private final String RELATIVE_GP_SIZE_UNEQUAL = "Unequal";
    StuyDesignContext globalVariables = StuyDesignContext.getInstance();
    private static Drawable img;
    
    private final int SOLVING_FOR_ROW = 0;
    private final int POWER_OR_SAMPLE_SIZE_ROW = 1;
    private final int TYPE_I_ERROR_ROW = 2;
    private final int NUMBER_OF_GROUPS_ROW = 3;
    private final int RELATIVE_GROUP_SIZE_ROW = 4;
    private final int SMALLEST_GROUP_SIZE_ROW = 5;
    private final int MEANS_VARIANCE_ROW = 6;
    

    public DesignListAdapter(Context context, String[] arrayList) {

        mListItems = arrayList;
        // get the layout inflater
        mLayoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
        img = context.getResources().getDrawable( R.drawable.ic_list_expand);
        img.setBounds(0,0,30,30);      
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
            /*holder.buttonLine = (ImageButton) view
                    .findViewById(R.id.details_toggle_button);*/

            if (position == POWER_OR_SAMPLE_SIZE_ROW) {
                if (globalVariables.getSolvingFor() == null) {
                    // view.setVisibility(View.INVISIBLE);
                    view.setLayoutParams(new AbsListView.LayoutParams(1, 1));
                } else {
                    // view.setVisibility(View.VISIBLE);
                    view.setLayoutParams(new AbsListView.LayoutParams(
                            AbsListView.LayoutParams.WRAP_CONTENT,
                            AbsListView.LayoutParams.WRAP_CONTENT));
                }
            }
            else if(position == SMALLEST_GROUP_SIZE_ROW){
                if (globalVariables.getSolvingFor()!= null && globalVariables.getSolvingFor().equals(SolutionTypeEnum.SAMPLE_SIZE.getId())) {
                    view.setLayoutParams(new AbsListView.LayoutParams(1, 1));
                    globalVariables.setSmallestGroupSize(0);
                }else {
                    // view.setVisibility(View.VISIBLE);
                    view.setLayoutParams(new AbsListView.LayoutParams(
                            AbsListView.LayoutParams.WRAP_CONTENT,
                            AbsListView.LayoutParams.WRAP_CONTENT));
                }
            }

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
                    // if (globalVariables.getIndividualProgress(position) != 0)
                    // {
                    if (groups != 0) {
                        equalityFlagRelativeGp = globalVariables
                                .relativeGroupEquality();
                        holder.detailLine
                                .setText((equalityFlagRelativeGp) ? RELATIVE_GP_SIZE_EQUAL
                                        : RELATIVE_GP_SIZE_UNEQUAL);
                    }
                    // }
                    break;
                case SMALLEST_GROUP_SIZE_ROW:
                    if (globalVariables.getIndividualProgress(position) != 0) {
                        holder.detailLine.setText(Integer
                                .toString(globalVariables
                                        .getSmallestGroupSize()));
                    }
                    break;
                case MEANS_VARIANCE_ROW:
                    if (globalVariables.getIndividualProgress(position) != 0) {
                        
                    }
                    break;
                }
            } else {
                groups = 0;
            }

            view.setOnClickListener(new OnClickListener() {
                private int pos = position;

                public void onClick(View v) {
                    callIntent(v, pos);
                    if (globalVariables.getIndividualProgress(pos) == 0) {
                        globalVariables.setProgress(pos);
                    }
                    getView(pos, v, viewGroup);
                }
            });

            /*holder.buttonLine.setOnClickListener(new OnClickListener() {
                private int pos = position;

                public void onClick(View v) {
                    callIntent(v, pos);
                    if (globalVariables.getIndividualProgress(pos) == 0) {
                        globalVariables.setProgress(pos);
                    }
                    getView(pos, v, viewGroup);
                }
            });*/
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
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            break;
        case POWER_OR_SAMPLE_SIZE_ROW:
            break;
        case TYPE_I_ERROR_ROW:
            intent = new Intent(v.getContext(), TypeIErrorActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            break;
        case NUMBER_OF_GROUPS_ROW:
            intent = new Intent(v.getContext(), GroupCountActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            break;
        case RELATIVE_GROUP_SIZE_ROW:
            intent = new Intent(v.getContext(), RelativeGroupSizeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            break;
        case SMALLEST_GROUP_SIZE_ROW:
            intent = new Intent(v.getContext(), SmallestGroupSizeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            break;
        case MEANS_VARIANCE_ROW:
            intent = new Intent(v.getContext(), MeansAndVarianceActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            break;
        }
        if (intent != null) {
            v.getContext().startActivity(intent);
        }
    }

    static class ViewHolder {
        TextView textLine;
        TextView detailLine;
        //ImageButton buttonLine;
    }

    public Filter getFilter() {
        // TODO Auto-generated method stub
        return null;
    }

}
