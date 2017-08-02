package example.com.krishilabh_retailer;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import example.com.krishilabh_retailer.Adapter.FpiInfoAdapter;
import example.com.krishilabh_retailer.Data.DataFpiInfo;
import example.com.krishilabh_retailer.Data.Notify;


/**
 * Created by User on 4/2/2017.
 */

public class FpiInfo extends Activity  {
    ListView listView;
    ArrayList<String> ItemName = new ArrayList<String>();
    ArrayList<String> Rate = new ArrayList<String>();
    FpiInfoAdapter fpiInfoAdapter;
    TextView proceed,afterProceed;
    PieChart mChart;
    ArrayList<DataFpiInfo> dataFpiInfo = new ArrayList<DataFpiInfo>();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    public TextView Total_value;
    public String companyName,T;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fpi_info);
        FirebaseDatabase.getInstance().getReference().keepSynced(true);
         BroadcastReceiver mMessageReceiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                T=intent.getStringExtra("TOTAL");
                Total_value=(TextView)findViewById(R.id.total_value);
                Total_value.setText(T);
                if(T.equals("0")){
                    proceed.setVisibility(View.GONE);
                }else{
                    proceed.setVisibility(View.VISIBLE);
                }


            }
        };

        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));

        //NumberPicker numberPicker = (NumberPicker) findViewById(R.id.number_picker);
        proceed=(TextView)findViewById(R.id.proceed);
        mChart = (PieChart) findViewById(R.id.chart1);

        //   mChart.setUsePercentValues(true);
        mChart.setDescription(null);

        mChart.setRotationEnabled(true);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            companyName = bundle.getString("Company");
            Log.e("bundle",bundle.toString());
            bundle.clear();
            Log.e("bundleClear",bundle.toString());
            DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference("FPI_Product_list").child(companyName);
            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    System.out.println("Hello");// Get Post object and use the values to update the UI

                    try {
                        Map<String, Object> Name = new HashMap<String, Object>();
                        Name = (Map<String, Object>) dataSnapshot.getValue();


                        String[] key = new String[Name.size()];
                        int i = 0;
                        for (String k : Name.keySet()) {
                            key[i] = k;

                            Object USER = Name.get(key[i]);
                            HashMap<String, Object> test = (HashMap<String, Object>) USER;

                            System.out.print("NAME" + Name);

                            ItemName.add((String) test.get("product"));

                            Rate.add((String) test.get("Rate"));

                        }

                        Log.e("Data", Rate.toString());
                        for (int j = 0; j < ItemName.size(); j++) {
                            dataFpiInfo.add(new DataFpiInfo(ItemName.get(j), Rate.get(j)));
                        }

                        proceed.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Notify user = new Notify("pending",T,companyName,settings.getString("company",null));
                                DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference("Notification")
                                        .child(companyName).child(settings.getString("company", null));

                                myRef1.setValue(user);
                                Intent i=new Intent("message");
                                System.out.println("cost and firmName"+" "+T+" "+companyName);

                                Toast.makeText(FpiInfo.this, "request send to"+""+companyName, Toast.LENGTH_SHORT).show();

                            }
                        });

                        recyclerView = (RecyclerView) findViewById(R.id.recycler_View);
                        layoutManager = new LinearLayoutManager(getApplication(), LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new FpiInfoAdapter(dataFpiInfo, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        setDataForPieChart();

                    }catch (ClassCastException classCastException){
                        Log.e("Exception",classCastException.toString());
                    }
                    catch (NullPointerException nullPointerException){
                        Log.e("exception",nullPointerException.toString());
                    }
                    // ...
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w("Test", "loadPost:onCancelled", databaseError.toException());
                    // ...
                }
            };
            myRef2.addValueEventListener(postListener);

            mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {
                    if (e == null)
                        return;

                }

                @Override
                public void onNothingSelected() {

                }
            });



        }


    }

    public void setDataForPieChart() {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < ItemName.size(); i++)
            xVals.add(ItemName.get(i));

        ArrayList<PieEntry> yVals1 = new ArrayList<PieEntry>();
        for (int i = 0; i < Rate.size(); i++)
            yVals1.add(new PieEntry(Float.parseFloat(Rate.get(i)), xVals.get(i)));


        // create pieDataSet
        PieDataSet dataSet = new PieDataSet(yVals1, "Products");
        dataSet.setSliceSpace(3);
        //dataSet.setSelectionShift(3);


        // adding colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        // Added My Own colors
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);


        dataSet.setColors(colors);

        //  create pie data object and set xValues and yValues and set it to the pieChart
        PieData data = new PieData(dataSet);
        //   data.setValueFormatter(new DefaultValueFormatter());
        //   data.setValueFormatter(new PercentFormatter());
        data.setValueFormatter(new MyValueFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);

        mChart.setData(data);
        // undo all highlights
        mChart.highlightValues(null);

        // refresh/update pie chart
        mChart.invalidate();

        // animate piechart
        mChart.animateXY(2000, 2000);


        // Legends to show on bottom of the graph
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);
        l.setTextSize(12f);
        l.setFormSize(10f); // set the size of the legend forms/shapes


    }



}
