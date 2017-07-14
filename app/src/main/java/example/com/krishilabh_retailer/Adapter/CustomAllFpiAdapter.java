package example.com.krishilabh_retailer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import example.com.krishilabh_retailer.R;


/**
 * Created by User on 3/29/2017.
 */

public class CustomAllFpiAdapter extends BaseAdapter {
    LayoutInflater newinflator;
    Context context;
    ArrayList<String> names=new ArrayList<String>();
    ArrayList <String> LOC=new ArrayList<String>();
    public CustomAllFpiAdapter(Context context,ArrayList<String> nam){
        this.context=context;
        this.names=nam;

        newinflator = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=newinflator.inflate(R.layout.all_fpi_nearby,null);
        TextView name=(TextView)convertView.findViewById(R.id.Fpi_name);
        //TextView location=(TextView)convertView.findViewById(R.id.loca_fpi);
        name.setText(names.get(position));
        //location.setText(LOC.get(position));

        return convertView;
    }
}
