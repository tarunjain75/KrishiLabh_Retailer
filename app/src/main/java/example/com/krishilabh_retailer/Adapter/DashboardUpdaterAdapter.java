package example.com.krishilabh_retailer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import example.com.krishilabh_retailer.R;
import example.com.krishilabh_retailer.UpdateItemActivity;


/**
 * Created by User on 4/1/2017.
 */

public class DashboardUpdaterAdapter extends BaseAdapter {
    LayoutInflater newinflator,mInflator;
    Context context;
    ArrayList<String> itemname=new ArrayList<>();
    ArrayList <String>quantity=new ArrayList<>();
   ArrayList<String> rate=new ArrayList<>();
    ArrayList<String>UNIT=new ArrayList<>();

    public DashboardUpdaterAdapter(Context context,ArrayList<String> item,ArrayList<String> quant,ArrayList<String> Rate,ArrayList<String> unit){
        this.context=context;
        this.itemname=item;
        this.quantity=quant;
        this.rate=Rate;
        this.UNIT=unit;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = newinflator.inflate(R.layout.dashboard_card_layout,null);
        TextView item=(TextView)convertView.findViewById(R.id.ItemNAme);
        TextView Quantity=(TextView)convertView.findViewById(R.id.Quantity);
        TextView Rate =(TextView)convertView.findViewById(R.id.PriceRate);
        Button edit=(Button) convertView.findViewById(R.id.edit_product);
        item.setText(itemname.get(position));
        Quantity.setText(quantity.get(position)+" "+UNIT.get(position));
        Rate.setText(rate.get(position));
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, UpdateItemActivity.class);
                intent.putExtra("product_Name",itemname.get(position));
                context.startActivity(intent);

            }
        });
        return convertView;
    }
}
