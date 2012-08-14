package edu.ucdenver.bios.glimmpseandroid.activity.design;

import edu.ucdenver.bios.glimmpseandroid.R;
import edu.ucdenver.bios.glimmpseandroid.adapter.RelativeGroupSizeAdapter;
import edu.ucdenver.bios.glimmpseandroid.application.StuyDesignContext;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class RelativeGroupSizeActivity extends Activity implements OnClickListener {
    private ListView relativeGroupSizeListView;
    
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final Window window = getWindow();
        boolean useTitleFeature = false;
        // If the window has a container, then we are not free
        // to request window features.
        if (window.getContainer() == null) {
            useTitleFeature = window
                    .requestFeature(Window.FEATURE_CUSTOM_TITLE);
        }
        setContentView(R.layout.design_relative_group_size);

        if (useTitleFeature) {
            window.setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
        }
        Button homeButton = (Button) findViewById(R.id.home_button);
        homeButton.setOnClickListener(this);

        Button back = (Button) findViewById(R.id.back_button);
        back.setOnClickListener(this);

        TextView title = (TextView) findViewById(R.id.window_title);
        title.setText(getResources().getString(R.string.title_relative_group_size));
        
        relativeGroupSizeListView = (ListView)findViewById(R.id.relative_group_size_list_view);
        View header =  getLayoutInflater().inflate(R.layout.design_relative_group_size_list_header, null, false);
        if(relativeGroupSizeListView.getHeaderViewsCount() == 0)
            relativeGroupSizeListView.addHeaderView(header);
        int groups = StuyDesignContext.getInstance().getGroups();
        if(groups > 0)
            relativeGroupSizeListView.setAdapter(new RelativeGroupSizeAdapter(RelativeGroupSizeActivity.this,groups));
        else{
            
        }
        
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
         }
         return super.onKeyDown(keyCode, event);
    }
    
    public void onClick(View v) {
        /*
         * // TODO Auto-generated method stub Bundle bundle = new Bundle();
         * bundle.putInt("design_list_item", 2); value = (TextView)
         * findViewById(R.id.see_progress); bundle.putInt("group_count",
         * Integer.parseInt(value.getText().toString())); Intent result=new
         * Intent(GroupCountActivity.this,DesignListAdapter.class); //Intent
         * result=new Intent(); result.putExtras(bundle); setResult(0, result);
         */
        //stuyDesignContext.setGroups(groups);            
        finish();
    }
}
