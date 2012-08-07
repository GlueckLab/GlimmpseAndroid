package edu.ucdenver.bios.glimmpseandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.glimmpseandroid.activity.TutorialSubScreenActivity;

/*public class TutorialAdapter extends BaseAdapter implements Filterable {
		private LayoutInflater mInflater;
		private Bitmap mIcon1;
		private Context context;
		 
		public TutorialAdapter(Context context) {
		// Cache the LayoutInflate to avoid asking for a new one each time.
		mInflater = LayoutInflater.from(context);
		this.context = context;
		}
		 
		*//**
		* Make a view to hold each row.
		*
		* @see android.widget.ListAdapter#getView(int, android.view.View,
		*      android.view.ViewGroup)
		*//*
		public View getView(final int position, View convertView, ViewGroup parent) {
		// A ViewHolder keeps references to children views to avoid
		// unneccessary calls
		// to findViewById() on each row.
		ViewHolder holder;
		 
		// When convertView is not null, we can reuse it directly, there is
		// no need
		// to reinflate it. We only inflate a new View when the convertView
		// supplied
		// by ListView is null.
		if (convertView == null) {
		convertView = mInflater.inflate(R.layout.expandable_list_item, null);
		 
		// Creates a ViewHolder and store references to the two children
		// views
		// we want to bind data to.
		holder = new ViewHolder();
		holder.textLine = (TextView) convertView.findViewById(R.id.textLine);
		holder.detailLine = (TextView) convertView.findViewById(R.id.iconLine);
		holder.buttonLine = (Button) convertView.findViewById(R.id.buttonLine);
		 
		 
		convertView.setOnClickListener(new OnClickListener() {
		private int pos = position;
		 
		
		public void onClick(View v) {
		Toast.makeText(context, "Click-" + String.valueOf(pos), Toast.LENGTH_SHORT).show();   
		}
		});
		 
		holder.buttonLine.setOnClickListener(new OnClickListener() {
		private int pos = position;
		 
		
		public void onClick(View v) {
		Toast.makeText(context, "Delete-" + String.valueOf(pos), Toast.LENGTH_SHORT).show();
		 
		}
		});
		 
		 
		 
		convertView.setTag(holder);
		} else {
		// Get the ViewHolder back to get fast access to the TextView
		// and the ImageView.
		holder = (ViewHolder) convertView.getTag();
		}
		 
		// Get flag name and id
		String filename = "flag_" + String.valueOf(position);
		int id = context.getResources().getIdentifier(filename, "drawable", context.getString(R.string.package_str));
		 
		// Icons bound to the rows.
		if (id != 0x0) {
		mIcon1 = BitmapFactory.decodeResource(context.getResources(), id);
		}
		 
		// Bind the data efficiently with the holder.
		holder.iconLine.setImageBitmap(mIcon1);
		holder.textLine.setText("flag " + String.valueOf(position));
		 
		return convertView;
		}
		 
		 static class ViewHolder {
		    	TextView textLine;
		    	TextView detailLine;
		    	Button buttonLine;
		    	}
		 
		@Override
		public Filter getFilter() {
		// TODO Auto-generated method stub
		return null;
		}
		 
		@Override
		public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
		}
		 
		@Override
		public int getCount() {
		// TODO Auto-generated method stub
		return data.length;
		}
		 
		@Override
		public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data[position];
		}
 
}*/

public class TutorialAdapter extends BaseAdapter implements Filterable{
    private String[] mListItems;
    private LayoutInflater mLayoutInflater;
    private Context mcontext;
 
    public TutorialAdapter(Context context, String[] arrayList){
 
        mListItems = arrayList;
 
        //get the layout inflater
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        this.mcontext = context;
    }
 
    
    public int getCount() {
        //getCount() represents how many items are in the list
        return mListItems.length;
    }
 
    
        //get the data of an item from a specific position
        //i represents the position of the item in the list
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
            view = mLayoutInflater.inflate(R.layout.tutorial_list_item, null);
            
            holder = new ViewHolder();
            holder.textLine = (TextView) view.findViewById(R.id.list_item_textView_tutorial);
            holder.detailLine = (TextView) view.findViewById(R.id.expandable_toggle_button_details);
            holder.buttonLine = (ImageButton) view.findViewById(R.id.expandable_toggle_button);
            
           
            
            view.setOnClickListener(new OnClickListener() {
            	private int pos = position;          
            	//Intent intent;
            	
            	public void onClick(View v) {
            	//Toast.makeText(mcontext, "Click-" + String.valueOf(pos), Toast.LENGTH_SHORT).show();      
            		 /*switch(position){
	     	            case 0:
	     	            	intent = new Intent(mcontext, TutorialSolvingForActivity.class);
	     	            	break;
	     	           case 1:
	     	            	intent = new Intent(mcontext, TutorialTypeIErrorActivity.class);
	     	            	break;
	                 }*/
            		/*intent = new Intent(v.getContext(),TutorialSubScreenActivity.class);
            		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    				Bundle bundle = new Bundle();
    				bundle.putInt("sub_screen", position);
    				intent.putExtras(bundle);
    				mcontext.startActivity(intent);*/
            		callIntent(v, position);
            	}
        	});
            	 
        	holder.buttonLine.setOnClickListener(new OnClickListener() {
        		private int pos = position;          
            	//Intent intent;
	            	 
	            	public void onClick(View v) {
	            	//Toast.makeText(mcontext, "Delete-" + String.valueOf(pos), Toast.LENGTH_SHORT).show();
	            		/*switch(position){
	     	            case 0:
	     	            	intent = new Intent(mcontext, TutorialSolvingForActivity.class);
	     	            	break;
	     	           case 1:
	     	            	intent = new Intent(mcontext, TutorialTypeIErrorActivity.class);
	     	            	break;
	            		}*/
	            		/*intent = new Intent(v.getContext(),TutorialSubScreenActivity.class);
	            		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    				Bundle bundle = new Bundle();
	    				bundle.putInt("sub_screen", position);
	    				intent.putExtras(bundle);
	            		mcontext.startActivity(intent);*/
	            		callIntent(v, position);
	            	}
        	});
        }
        //view.setTag(holder);
        //get the string item from the position "position" from array list to put it on the TextView
        String stringItem = mListItems[position];
        if (stringItem != null) {
 
            TextView itemName = (TextView) view.findViewById(R.id.list_item_textView_tutorial);
 
            if (itemName != null) {
                //set the item name on the TextView
                itemName.setText(stringItem);
            }
        }              
        
        
 
        //this method must return the view corresponding to the data at the specified position.
        return view;
 
    }
    
    public void callIntent(View v, int position) {
    	Intent intent = new Intent(v.getContext(),TutorialSubScreenActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Bundle bundle = new Bundle();
		bundle.putInt("sub_screen", position);
		intent.putExtras(bundle);
		mcontext.startActivity(intent);
    }
    
    static class ViewHolder {
    	TextView textLine;
    	TextView detailLine;
    	ImageButton buttonLine;
    	}

	public Filter getFilter() {
		// TODO Auto-generated method stub
		return null;
	}
}
