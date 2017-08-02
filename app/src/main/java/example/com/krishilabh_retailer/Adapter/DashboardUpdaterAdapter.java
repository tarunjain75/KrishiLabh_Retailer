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
                /**//*
                inflator=mInflator.inflate(R.layout.edit_dailog_layout,null);
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.edit_dailog_layout);
                EditText editPrice=(EditText)dailog.findViewById(R.id.edit_price);
                editQuantity=(EditText)dailog.findViewById(R.id.edit_quant);
                editUnit=(EditText)dailog.findViewById(R.id.edit_unit);
                update=(TextView)dailog.findViewById(R.id.UpadteItemDialog);


                final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                final DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference();
                final Product user2 = new Product(product_name, editQuantity.getText().toString(), editPrice.getText().toString(), editUnit.getText().toString());
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {

                            myRef2.child("Retailer_update").child(settings.getString("company",null)).child(product_name).setValue(user2);
                            dailog.dismiss();
                            Toast.makeText(DashBoardFragmentActivity.this,"Item Updated Succesfully",Toast.LENGTH_LONG).show();

                        } catch (DatabaseException r) {
                            System.out.println(r);
                        }
                    }
                });
                dailog.show();*/
            }
        });
        return convertView;
    }
}
