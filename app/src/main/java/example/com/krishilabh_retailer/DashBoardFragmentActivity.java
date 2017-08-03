package example.com.krishilabh_retailer;


import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import example.com.krishilabh_retailer.Adapter.DashboardUpdaterAdapter;
import example.com.krishilabh_retailer.Data.Product;


/**
 * Created by User on 4/1/2017.
 */

public class DashBoardFragmentActivity extends Activity {
    ListView listView;
    ImageView loadingLayout,arrowback;
    ProgressBar progressBar;
    ArrayList<String> ItemName=new ArrayList<String>();
    ArrayList<String> Quantity=new ArrayList<String>();
    ArrayList<String>  Rate=new ArrayList<String>();
    ArrayList<String> Unit=new ArrayList<String>();

    DashboardUpdaterAdapter dashboardUpdaterAdapter;
    public SwipeRefreshLayout swipeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        progressBar=(ProgressBar)findViewById(R.id.progressDashboard);
        loadingLayout=(ImageView)findViewById(R.id.Dashboard_loading_layout);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(true);

                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String company = settings.getString("company","");

                DatabaseReference myRef2= FirebaseDatabase.getInstance().getReference("Retailer_update").child(company);

                ValueEventListener postListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        System.out.println("hello inside");// Get Post object and use the values to update the UI

                        try {
                            ItemName.clear();
                            Quantity.clear();
                            Rate.clear();
                            Unit.clear();
                            Map<String, Object> name = new HashMap<String, Object>();
                            name = (Map<String, Object>) dataSnapshot.getValue();
                            String[] key = new String[name.size()];
                            int i = 0;
                            for (String k : name.keySet()) {
                                key[i] = k;
                                System.out.print(k);
                                Object USER = name.get(key[i]);
                                HashMap<String, Object> test = (HashMap<String, Object>) USER;
                                Log.e("Test inside",test.toString());

                                ItemName.add((String) test.get("product"));
                                Quantity.add((String) test.get("quantity"));
                                Rate.add((String) test.get("Rate"));
                                Unit.add((String)test.get("unit"));


                            }
                        }catch (NullPointerException exception){
                            Log.e("exception",exception.toString());
                        }
                        Log.e("Quantity inside",Quantity.toString());
                        Log.e("Rate inside",Rate.toString());
                        swipeContainer.setRefreshing(false);

                       /* loadingLayout.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
*/                      listView=(ListView)findViewById(R.id.listview);
                        dashboardUpdaterAdapter= new DashboardUpdaterAdapter(getApplicationContext(),ItemName,Quantity,Rate,Unit);
                        listView.setAdapter(dashboardUpdaterAdapter);
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


            }
        });
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String company = settings.getString("company", "");

        DatabaseReference myRef2= FirebaseDatabase.getInstance().getReference("Retailer_update").child(company);

     ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("hello");// Get Post object and use the values to update the UI
                Log.e("dataSnapshot",dataSnapshot.getValue().toString());
                try {

                    HashMap<String, Object> name = new HashMap<String, Object>();
                    name = (HashMap<String, Object>) dataSnapshot.getValue();
                    String[] key = new String[name.size()];
                    int i = 0;
                    for (String k : name.keySet()) {
                        key[i] = k;
                        System.out.print(k);
                        Object USER = name.get(key[i]);
                        HashMap<String, Object> test = (HashMap<String, Object>) USER;


                        ItemName.add((String) test.get("product"));
                        Quantity.add((String) test.get("quantity"));
                        Rate.add((String) test.get("Rate"));
                        Unit.add((String)test.get("unit"));


                    }
                }catch (NullPointerException exception){
                    Log.e("exception",exception.toString());
                }
                listView=(ListView)findViewById(R.id.listview);
                dashboardUpdaterAdapter= new DashboardUpdaterAdapter(getApplicationContext(),ItemName,Quantity,Rate,Unit);
                listView.setAdapter(dashboardUpdaterAdapter);
                loadingLayout.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);

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




        initView();

    }

    private void initView() {


        arrowback=(ImageView)findViewById(R.id.dash_back);
        arrowback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


}
