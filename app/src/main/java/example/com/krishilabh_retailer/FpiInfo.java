package example.com.krishilabh_retailer;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
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
import java.util.Map;

import example.com.krishilabh_retailer.Adapter.FpiInfoAdapter;


/**
 * Created by User on 4/2/2017.
 */

public class FpiInfo extends Activity implements View.OnClickListener {
    ListView listView;
    ArrayList<String> ItemName=new ArrayList<String>();
    ArrayList<String> Rate=new ArrayList<String>();
    FpiInfoAdapter fpiInfoAdapter;
    TextView proceed;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fpi_info);
        proceed=(TextView)findViewById(R.id.proceed);
       /* proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FpiInfo.this, "Proceed after", Toast.LENGTH_SHORT).show();
            }
        });*/




        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            String companyName=bundle.getString("Company");
            DatabaseReference myRef2= FirebaseDatabase.getInstance().getReference("FPI_Product_list").child(companyName);
            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    System.out.println("Hello");// Get Post object and use the values to update the UI

                    Map<String,Object> Name = new HashMap<String, Object>();
                    Name = (Map<String, Object>)dataSnapshot.getValue();


//                Toast.makeText(getApplicationContext(), name.toString(), Toast.LENGTH_LONG).show();
//
//                Toast.makeText(getApplicationContext(), (String) name.get("product"), Toast.LENGTH_LONG).show();
                    String []key=new String [Name.size()];
                    int i=0;
                    for(String k:Name.keySet())
                    {
                        key[i] = k;

                        Object USER = Name.get(key[i]);
                        HashMap<String, Object> test = (HashMap<String, Object>) USER;
//                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                    String company = settings.getString("company", "");
//                    if(k.equals(company))
//                    {
                        System.out.print("NAME"+Name);

                        ItemName.add((String) test.get("product"));

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
            fpiInfoAdapter=new FpiInfoAdapter(getApplicationContext(),ItemName,Rate);
            listView.setAdapter(fpiInfoAdapter);
        }


    }

    @Override
    public void onClick(View v) {

    }
}
