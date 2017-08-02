package example.com.krishilabh_retailer;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import example.com.krishilabh_retailer.Adapter.FpiInfoAdapter;
import example.com.krishilabh_retailer.Adapter.PaymentListCustomAdapter;
import example.com.krishilabh_retailer.Data.PaymentDetails;


/**
 * Created by User on 4/2/2017.
 */

public class PaymentActivity extends Activity implements View.OnClickListener {
    ImageView backbutn;
    public ArrayList<String> FirmNAME=new ArrayList<>();
    public ArrayList<String> Cost=new ArrayList<>();
    ArrayList<PaymentDetails> paymentDetails= new ArrayList<PaymentDetails>();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_layout);


        try{
            final SharedPreferences settings= PreferenceManager.getDefaultSharedPreferences(this);
            DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Notification");
            reference.orderByChild("retail").equals(settings.getString("company",null));
            ValueEventListener postlistener=new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        HashMap<String, Object> Retailer = new HashMap<>();
                        Retailer = (HashMap<String, Object>) dataSnapshot.getValue();
                        Log.e("RetailerInfo",Retailer.toString());
                        String[] key = new String[Retailer.size()];
                        int i = 0;
                        for (String k : Retailer.keySet()) {
                            key[i]=k;
                            Object USER = Retailer.get(key[i]);
                            Log.e("USER",USER.toString());
                            if(!USER.equals("true")) {
                                HashMap<String, Object> test = (HashMap<String, Object>) USER;
                                for(HashMap.Entry<String, Object> entry: test.entrySet()) {

                                    if(entry.getKey().equals(settings.getString("company",null))) {
                                        String[] KEY = new String[test.size()];
                                        int j = 0;
                                        for (String t : test.keySet()) {
                                            KEY[j] = t;
                                            Object U = test.get(KEY[j]);
                                            Log.e("U", U.toString());
                                            HashMap<String, Object> data = (HashMap<String, Object>) U;
                                            if(data.containsValue(settings.getString("company",null))){
                                            FirmNAME.add((String) data.get("fpi"));
                                            Cost.add((String) data.get("cost"));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Log.e("FirmName", FirmNAME.toString());
                        Log.e("Cost", Cost.toString());
                        for(int x=0;x<Cost.size();x++){
                            System.out.println("Firm"+" "+FirmNAME.get(x)+" "+Cost.get(x));
                            paymentDetails.add(new PaymentDetails( FirmNAME.get(x), Cost.get(x)));
                        }
                        Log.e("PaymentDetails", paymentDetails.toString());
                        recyclerView=(RecyclerView)findViewById(R.id.paymentRecyclerView);
                        layoutManager = new LinearLayoutManager(getApplication(), LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new PaymentListCustomAdapter(paymentDetails, PaymentActivity.this);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                    }
                    catch (ClassCastException e){
                        Log.e("Exception",e.toString());
                    }
                    catch (NullPointerException e){
                        Log.e("Exception",e.toString());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            reference.addValueEventListener(postlistener);


        }catch (NullPointerException e){
            Log.e("exception",e.toString());
        }




        initView ();
    }

    private void initView() {

            backbutn=(ImageView)findViewById(R.id.pay_back_payment);
            backbutn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
    }

    @Override
    public void onClick(View v) {

    }
}
