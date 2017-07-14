package example.com.krishilabh_retailer;


import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import example.com.krishilabh_retailer.Adapter.DashboardUpdaterAdapter;


/**
 * Created by User on 4/1/2017.
 */

public class DashBoardFragmentActivity extends Activity {
    ListView listView;
    ImageView editButton,arrowback;

    Dialog dailog;
    EditText editQuantity,editPrice;
    TextView update,priceRate,quantity;
    ArrayList<String> ItemName=new ArrayList<String>();
    ArrayList<String> Quantity=new ArrayList<String>();
    ArrayList<String>  Rate=new ArrayList<String>();

    DashboardUpdaterAdapter dashboardUpdaterAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String company = settings.getString("company", null);

        DatabaseReference myRef2= FirebaseDatabase.getInstance().getReference("Retailer_update").child(company);

        // Toast.makeText(getActivity(), "hey", Toast.LENGTH_SHORT).show();
        // System.out.print(myRef2.toString());
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("hello");// Get Post object and use the values to update the UI

                Map<String,Object> name = new HashMap<String, Object>();
                name = (Map<String, Object>)dataSnapshot.getValue();


//                Toast.makeText(getApplicationContext(), name.toString(), Toast.LENGTH_LONG).show();
//
//                Toast.makeText(getApplicationContext(), (String) name.get("product"), Toast.LENGTH_LONG).show();
                String []key=new String [name.size()];
                int i=0;
                for(String k:name.keySet())
                {
                    key[i] = k;

                    Object USER = name.get(key[i]);
                    HashMap<String, Object> test = (HashMap<String, Object>) USER;
//                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                    String company = settings.getString("company", "");
//                    if(k.equals(company))
//                    {
                    System.out.print(name);

                    ItemName.add((String) test.get("product"));
                    Quantity.add((String) test.get("quantity"));
                    Rate.add((String) test.get("Rate"));

//                    }
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



        listView=(ListView)findViewById(R.id.listview);
        dashboardUpdaterAdapter= new DashboardUpdaterAdapter(getApplicationContext(),ItemName,Quantity,Rate);
        listView.setAdapter(dashboardUpdaterAdapter);
        initView();

    }

    private void initView() {
        priceRate=(TextView)findViewById(R.id.PriceRate);
        quantity=(TextView)findViewById(R.id.Quantity);
        arrowback=(ImageView)findViewById(R.id.dash_back);
        arrowback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


}
