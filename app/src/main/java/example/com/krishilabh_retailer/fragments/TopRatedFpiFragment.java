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

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import example.com.krishilabh_retailer.Adapter.CustomTopFpiAdapter;
import example.com.krishilabh_retailer.FpiInfo;
import example.com.krishilabh_retailer.R;
import example.com.krishilabh_retailer.Data.Retailer;


/**
 * Created by User on 3/29/2017.
 */

public class TopRatedFpiFragment extends android.support.v4.app.Fragment {
    ListView listView;

    ArrayList<String> listItems = new ArrayList<String>();
    int retailerAmount;
    int retailerQuantity;
    Retailer retailer;
    String product;
    ArrayList list = new ArrayList();

    Map<String, Object> name;
    Map<String, Object> productName;
    Map<String, Object> fields;
    //HashMap<String, Object> Itemname;
    HashMap<String, Object> Items = new HashMap<String, Object>();
    //HashMap<String ,Object> Product=new HashMap<String ,Object>();

    static final int dutiesCost = 250;
    static final int logisticsCost = 2000;
    static final int damagesCost = 350;


    ArrayList<String> Name = new ArrayList<>();
    ArrayList<String> Itemname = new ArrayList<String>();
    ArrayList<String> Product = new ArrayList<String>();
    String latitude;
    String longitude;
    ArrayList<String> Loc = new ArrayList<String>();
    ArrayList<Integer> Matchpercent = new ArrayList<Integer>();
    String Gainpercent[] = {"90%", "80%", "70%", "60%", "50%", "40%"};
    CustomTopFpiAdapter customTopFpiAdapter;
    int Count = 0, i;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        name = new HashMap<String, Object>();
        productName = new HashMap<String, Object>();
        fields = new HashMap<String, Object>();
        //Itemname=new HashMap<String, Object>();
        retailer = new Retailer();
        product = "papad";
        retailerAmount = 500;
        retailerQuantity = 2;//retailer.getQuantity();
        retailerAmount *= retailerQuantity;
        retailerAmount = retailerAmount + dutiesCost + logisticsCost - damagesCost;
//
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference("Retailer_update").child(settings.getString("company", null));
        System.out.println("check");


        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                try {
                    name = (Map<String, Object>) dataSnapshot.getValue();

                    String[] key = new String[name.size()];
                    int i = 0;
                    for (String k : name.keySet()) {
                        key[i] = k;
                        Object USER = name.get(key[i]);
                        HashMap<String, Object> test = (HashMap<String, Object>) USER;

                        Itemname.add((String) test.get("product"));
                        Log.e("Test 1", Itemname.toString());
                    }
               /* Log.e("Test", name.toString() );*/

                }catch (NullPointerException e){
                    Log.e("Exception",e.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Test", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        myRef2.addValueEventListener(postListener);


        latitude = settings.getString("latitude", null);
        longitude = settings.getString("longitude", null);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("path/to/geofire");
        GeoFire geoFire = new GeoFire(ref);
        // geoFire.setLocation("firebase-hq", new GeoLocation(37.7853889, -122.4056973));

        //making search query in nearby area

        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(Double.parseDouble(latitude), Double.parseDouble(longitude)), 50);
        final HashMap<String, GeoLocation> loc = new HashMap<>();
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(final String key, GeoLocation location) {
                System.out.println(String.format("Key %s entered the search area at [%f,%f]", key, location.latitude, location.longitude));
                loc.put(key, location);
                Name.add(key);
                Count = 0;
                DatabaseReference myRef3 = FirebaseDatabase.getInstance().getReference("FPI_Product_list").child(key);
                myRef3.addValueEventListener( new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        HashMap<String, Object> fpiname = new HashMap<String, Object>();
                        fpiname = (HashMap<String, Object>) dataSnapshot.getValue();
                        try{
                            Log.e("fpiName",fpiname.toString());
                            String[] ky = new String[fpiname.size()];
                            int j = 0;
                            for (String t : fpiname.keySet()) {
                                ky[j] = t;
                                Object User = fpiname.get(ky[j]);
                                HashMap<String, Object> TEST = (HashMap<String, Object>) User;
                                Log.e("Test 3", TEST.toString());
                                Product.add((String) TEST.get("product"));
                            }
                            for (int counter = 0; counter < Itemname.size(); counter++) {
                                if (Product.contains(Itemname.get(counter))) {
                                    Count++;
                                }
                            }
                            if (Count > 0) {
                                listItems.add(key);
                                Matchpercent.add((Count * 100) / Itemname.size());
                                Count=0;
                            }

                            Log.e("Test 4", listItems.toString());
                            System.out.println("Match%" + " " + Matchpercent);

                        }
                        catch (NullPointerException nulpointer){
                            Log.e("Exception",nulpointer.toString());
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                settingadapter();

            }

            @Override
            public void onKeyExited(String key) {
                System.out.println(String.format("Key %s is no longer in the search area", key));
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
                System.out.println(String.format("Key %s moved within the search area to [%f,%f]", key, location.latitude, location.longitude));
            }

            @Override
            public void onGeoQueryReady() {
                System.out.println("All initial data has been loaded and events have been fired!");
                System.out.println(String.format(loc.toString()));

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {
                System.err.println("There was an error with this query: " + error);
            }
        });


        View view = (View) inflater.inflate(R.layout.top_rated_list_view, container, false);
        listView = (ListView)view.findViewById(R.id.ListView);



        //listView.setAdapter(customTopFpiAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Intent in = new Intent(getActivity(), FpiInfo.class);
                in.putExtra("Company", listItems.get(position));
                startActivity(in);
            }
        });

        return view;
    }
    public void settingadapter()
    {
        listView.setAdapter(customTopFpiAdapter);
        customTopFpiAdapter = new CustomTopFpiAdapter(getActivity(), listItems,Matchpercent);

    }

}
