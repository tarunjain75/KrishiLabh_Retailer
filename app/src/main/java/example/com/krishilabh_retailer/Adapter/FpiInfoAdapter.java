package example.com.krishilabh_retailer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;


import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker;
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPickerListener;

import java.util.ArrayList;
import java.util.Arrays;

import example.com.krishilabh_retailer.Data.DataFpiInfo;
import example.com.krishilabh_retailer.R;


/**
 * Created by User on 4/2/2017.
 */

public class FpiInfoAdapter extends RecyclerView.Adapter<FpiInfoAdapter.ViewHolder> {
    ArrayList<DataFpiInfo> dataFpiInfos = new ArrayList<DataFpiInfo>();
    Integer total = 0, Sum = 0;
    Integer[] previous=new Integer[100];
    Context context;

    public FpiInfoAdapter(ArrayList<DataFpiInfo> dataFpiInfos, Context context) {
        this.dataFpiInfos = dataFpiInfos;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fpi_input_card_layout, parent, false);
        FpiInfoAdapter.ViewHolder vh = new FpiInfoAdapter.ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        for (int j = 0; j <dataFpiInfos.size(); j++) {
            previous[j]=0;
        }

        holder.productName.setText(dataFpiInfos.get(position).getProductName());
        holder.itemRate.setText(dataFpiInfos.get(position).getRate());

        holder.horizontalNumberPicker.setListener(new ScrollableNumberPickerListener() {
            @Override
            public void onNumberPicked(int value) {
                if (holder.horizontalNumberPicker.getValue() >= previous[position]) {
                    Log.e("position", Integer.toString(position));
                    System.out.println(Arrays.toString(previous));
                    total += (Integer.parseInt(dataFpiInfos.get(position).getRate()));
                    previous[position]= holder.horizontalNumberPicker.getValue();
                    System.out.println(Arrays.toString(previous));
                    Log.e("NumberPickerValue",Integer.toString(holder.horizontalNumberPicker.getValue()));
                    Log.e("Total 1", Integer.toString(total));
                    Intent intent = new Intent("custom-message");
                    intent.putExtra("TOTAL", Integer.toString(total));
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                } else {
                    Log.e("position", Integer.toString(position));
                    System.out.println(Arrays.toString(previous));
                    total = total - (Integer.parseInt(dataFpiInfos.get(position).getRate()));
                    if (total < 0) {
                        total = 0;
                    }
                    previous[position]= holder.horizontalNumberPicker.getValue();
                    System.out.println(Arrays.toString(previous));
                    Log.e("NumberPickerValue",Integer.toString(holder.horizontalNumberPicker.getValue()));
                    Log.e("Total 2", Integer.toString(total));
                    Intent intent = new Intent("custom-message");
                    intent.putExtra("TOTAL", Integer.toString(total));
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataFpiInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productName, itemRate;
        ScrollableNumberPicker horizontalNumberPicker;


        public ViewHolder(View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.Fpi_item_name);
            itemRate = (TextView) itemView.findViewById(R.id.rateOfProduct);
            horizontalNumberPicker = (ScrollableNumberPicker) itemView.findViewById(R.id.snp_horizontal);
        }

    }
}
