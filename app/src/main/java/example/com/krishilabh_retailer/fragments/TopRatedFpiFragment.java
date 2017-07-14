package example.com.krishilabh_retailer.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import example.com.krishilabh_retailer.Adapter.CustomTopFpiAdapter;
import example.com.krishilabh_retailer.FpiInfo;
import example.com.krishilabh_retailer.R;
import example.com.krishilabh_retailer.Retailer;


/**
 * Created by User on 3/29/2017.
 */

public class TopRatedFpiFragment extends android.support.v4.app.Fragment {
    ListView listView;

    ArrayList<String> listItems=new ArrayList<String>();
    int retailerAmount;
    int retailerQuantity;
    int FPIQuantity;
    int FPIAmount;
    int distance;
    Retailer retailer;
    String product;
    ArrayList list=new ArrayList();

    Map<String, Object> name;
    Map<String, Object> productName;
    Map<String, Object> fields;

    static final int dutiesCost = 250;
    static final int logisticsCost = 2000;
    static final int damagesCost = 350;



    ArrayList<String> Loc= new ArrayList<String>();
    String Matchpercent[]={"90%","80%","70%","60%","50%","40%"};
    String Gainpercent[]={"90%","80%","70%","60%","50%","40%"};
    CustomTopFpiAdapter customTopFpiAdapter;
    ViewGroup rootview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        name = new HashMap<String, Object>();
        productName = new HashMap<String, Object>();
        fields = new HashMap<String, Object>();
        retailer = new Retailer();
        product = "papad";
        retailerAmount = 500;
        retailerQuantity = 2;//retailer.getQuantity();
        retailerAmount *= retailerQuantity;
        retailerAmount = retailerAmount+dutiesCost+logisticsCost-damagesCost;
//        listItems.add("Fpi 1");
//        listItems.add("Fpi 2");
//        listItems.add("Fpi 3");
//        listItems.add("Fpi 4");
//        listItems.add("Fpi 5");
//        listItems.add("Fpi 6");
//        listItems.add("Fpi 7");
//
//
//        Loc.add("Fpi 1");
//        Loc.add("Fpi 2");
//        Loc.add("Fpi 3");
//        Loc.add("Fpi 4");
//        Loc.add("Fpi 5");
//        Loc.add("Fpi 6");
//        Loc.add("Fpi 7");

                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
         product = settings.getString("product", "");
        final String company = settings.getString("company", "");

        Log.e("Test", name.toString() );
       /* FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:234194199814:android:004e0890eb3925e0") // Required for Analytics.
                .setApiKey("AIzaSyA7NOx6ZjkcAm24kiQ3WnWtE8JSmIBd9PY") // Required for Auth.
                .setDatabaseUrl("https://krishilabhfpi.firebaseio.com/") // Required for RTDB.
                .build();
        FirebaseApp.initializeApp(getActivity() *//* Context *//*, options, "secondary");
        //Retrieve my other app.
        FirebaseApp app = FirebaseApp.getInstance("secondary");*/
        DatabaseReference myRef2= FirebaseDatabase.getInstance().getReference("FPI_Product_list");
        System.out.println("check");

       // Toast.makeText(getActivity(), "hey", Toast.LENGTH_SHORT).show();
       // System.out.print(myRef2.toString());
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
              name = (Map<String, Object>) dataSnapshot.getValue();

                String []key=new String [name.size()];
                int i=0;
                for(String k:name.keySet())
                {
                    key[i]=k;
                    Object USER=name.get(key[i]);
                    HashMap<String,Object>test=(HashMap<String, Object>) USER;


                    if(test.containsKey(product))
                    {System.out.println("Author: " +test);
                        listItems.add(k);
                        //Loc.add(k);
                    }

                }

//                Toast.makeText(getActivity(), name.toString(), Toast.LENGTH_LONG).show();
                Log.e("Test", name.toString() );

                System.out.println("Title: " + name.toString());

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



        rootview = (ViewGroup) inflater.inflate(R.layout.list_view, container, false);
        listView=(ListView)rootview.findViewById(R.id.listview);


        customTopFpiAdapter=new CustomTopFpiAdapter(getActivity(),listItems,Matchpercent,Gainpercent);
        listView.setAdapter(customTopFpiAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Intent in=new Intent (getActivity(),FpiInfo.class);
                in.putExtra("Company",listItems.get(position));
                startActivity(in);
            }
        });
        return rootview;
    }

    public void getAllFPI()
    {
        Set keys = name.keySet();

        for (Iterator i = keys.iterator(); i.hasNext();)
        {
            Integer key = (Integer) i.next();
            productName = (Map) name.get(key);
            if(productName.containsKey(product))
            {
                fields = (Map) productName.get(product);
                FPIQuantity = (int) fields.get("quantity");
                FPIAmount = (int) fields.get("Rate");
//                distance = (int) fields.get("Distance");
                if(FPIQuantity >= retailerQuantity)
                {
                    if((FPIAmount*retailerQuantity) > retailerAmount)
                        System.out.print("NO BUY");
                    else
                        list.add(name.get(key)+":"+product+":"+retailerQuantity+":"+retailerAmount);
                }
            }
        }

    }

    public void sortList()
    {
        for(int i=0;i<list.size()-1;i++)
        {
            String str = (String) list.get(i);
            String arr[] = str.split(":");
            int a = Integer.parseInt(arr[4]);
            for(int j=i+1;j<list.size();j++)
            {
                String str1 = (String) list.get(i);
                String brr[] = str1.split(":");
                int b = Integer.parseInt(brr[4]);
                if(a > b)
                    Collections.swap(list, i, j);
            }
        }
    }
}
