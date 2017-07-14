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
 * Created by User on 4/1/2017.
 */

public class DashboardUpdaterAdapter extends BaseAdapter {
    LayoutInflater newinflator;
    Context context;
    ArrayList<String> itemname=new ArrayList<>();
    ArrayList <String>quantity=new ArrayList<>();;
   ArrayList<String> rate=new ArrayList<>();;

    public DashboardUpdaterAdapter(Context context,ArrayList<String> item,ArrayList<String> quant,ArrayList<String> Rate){
        this.context=context;
        this.itemname=item;
        this.quantity=quant;
        this.rate=Rate;
        newinflator=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (itemname.size() );
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
        convertView=newinflator.inflate(R.layout.dashboard_card_layout,null);
        TextView item=(TextView)convertView.findViewById(R.id.ItemNAme);
        TextView Quantity=(TextView)convertView.findViewById(R.id.Quantity);
        TextView Rate =(TextView)convertView.findViewById(R.id.PriceRate);
        item.setText(itemname.get(position));
        Quantity.setText(quantity.get(position));
        Rate.setText(rate.get(position));
        return convertView;
    }
}
