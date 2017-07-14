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
 * Created by User on 4/2/2017.
 */

public class FpiInfoAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    Context context;
    ArrayList<String> itemName;
    ArrayList<String> rate;

    public FpiInfoAdapter(Context context,ArrayList<String> ItemName,ArrayList<String> Rate){
        this.context=context;
        this.itemName=ItemName;
        this.rate=Rate;
        layoutInflater= LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return (itemName.size());
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
        convertView=layoutInflater.inflate(R.layout.fpi_input_card_layout,null);
        TextView ItemName=(TextView)convertView.findViewById(R.id.Fpi_item_name);
        TextView Rate=(TextView)convertView.findViewById(R.id.rateOfProduct);
        ItemName.setText(itemName.get(position));
        Rate.setText(rate.get(position));
        return convertView;
    }
}
