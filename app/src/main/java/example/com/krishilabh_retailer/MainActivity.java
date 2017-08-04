package example.com.krishilabh_retailer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonElement;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.util.Map;

import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.model.AIError;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.ui.AIButton;
import example.com.krishilabh_retailer.fragments.FpiNearbyViewPagerFragmentActivity;

import static android.R.attr.offset;

public class MainActivity extends Activity implements View.OnClickListener{
    ImageButton drawer_menu,notify,search;
    DrawerLayout drawerLayout;
    TextView store_name,home_option,profile_option,settings_option,changeLanguage_option,share_option,rate_option,logOut_option;
    FrameLayout framelay;
    View menu_view;

    ImageView imageView;
    Double longitude= new Double(0);
    Double latitude= new Double(0);
    ProgressBar progressBar;
    private MediaRecorder mRecorder;
    private String Filename=null;
    private static final String LOG_TAG="Record";
    public String company;
    public String lon,lat;
    public int count=0;
    /*DrawerArrowDrawable drawerArrowDrawable;
    float offset;
    boolean flipped;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*final Resources resources = getResources();
        drawerArrowDrawable = new DrawerArrowDrawable(this);
        drawerArrowDrawable.setColor(getResources().getColor(R.color.teal));*/
        //imageView.setImageDrawable(drawerArrowDrawable);
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor2 = settings.edit();
        progressBar= (ProgressBar) findViewById(R.id.progressActivityMain);
        imageView=(ImageView)findViewById(R.id.main_loading_layout);

        DatabaseReference myRef2= FirebaseDatabase.getInstance().getReference();
        myRef2.child("Retailer").orderByChild("username").equalTo(settings.getString("Email",null)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("hello1");
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()){
                    String company=childSnapshot.getKey();
                    System.out.println("check"+" "+company);
                    /*if(settings.getString("company",null)!=null){
                        editor2.remove("company").apply();
                        editor2.putString("company",company).apply();

                    }
                    else{
                        editor2.putString("company",company).apply();
                    }*/
                    editor2.putString("company",company);
                    editor2.commit();
                    /*DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                    myRef.child("Notification").child(settings.getString("company",null)).setValue("true");*/
                    //store_name.setText(settings.getString("company",null));

                    DatabaseReference ref2=FirebaseDatabase.getInstance().getReference("Retailer");
                    DatabaseReference ref3=ref2.child(settings.getString("company",null)).child("latitude");
                    Log.e("reference",ref3.getKey());
                    ValueEventListener postlistnerRef3= new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            System.out.println("Hello"+" "+settings.getString("company",null)+" "+settings.getString("latitude",null));

                            //String lat=String.valueOf(dataSnapshot.getValue(Long.class));

                            latitude=dataSnapshot.getValue(Double.class);
                            lat=String.valueOf(latitude);

                            if(settings.getString("latitude",null)!=null){
                                editor2.remove("latitude").apply();
                                editor2.putString("latitude",lat).apply();
                                System.out.println("MainActivity"+" "+"Latitude"+" "+settings.getString("latitude",null));
                            }
                            else{
                                editor2.putString("latitude",lat).apply();
                                System.out.println("MainActivity"+" "+"Latitude"+settings.getString("latitude",null));
                            }

                            editor2.commit();
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    };
                    ref3.addValueEventListener(postlistnerRef3);
                    DatabaseReference ref4=ref2.child(settings.getString("company",null)).child("longitude");
                    ValueEventListener postlistnerRef4=new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            System.out.println("Hello 1");
                            //           Double Lon = dataSnapshot.getValue(Double.class);

                            longitude=dataSnapshot.getValue(Double.class);
                            lon=String.valueOf(longitude);

                            if(settings.getString("longitude",null)!=null){
                                editor2.remove("longitude").apply();
                                editor2.putString("longitude",lon).apply();
                                System.out.println("MainActivity"+" "+"Longitude"+settings.getString("longitude",null));
                            }
                            else{
                                editor2.putString("longitude",lon).apply();
                                System.out.println("MainActivity"+" "+"Longitude"+settings.getString("longitude",null));
                            }
                            progressBar.setVisibility(View.GONE);
                            imageView.setVisibility(View.GONE);
                            editor2.commit();

                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    };
                    ref4.addValueEventListener(postlistnerRef4);


                    store_name=(TextView)findViewById(R.id.store_name);
                    store_name.setText(settings.getString("company",null));
                    System.out.println("Company main"+settings.getString("company",null));
                    progressBar.setVisibility(View.GONE);
                    imageView.setVisibility(View.GONE);
                    System.out.println("check 1"+" "+settings.getString("company",null));
                    /*DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("Notification");
                    ValueEventListener postlistener2=new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            System.out.println("check 2"+" "+settings.getString("company",null));
                            if (!dataSnapshot.hasChild(settings.getString("company",null))) {
                                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                                myRef.child("Notification").child(settings.getString("company",null)).setValue("true");

                                // run some code
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    };
                    rootRef.addValueEventListener(postlistener2);*/



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Test", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });










        /*DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference();

        myRef2.child("Current_User").child(settings.getString("company", null)).setValue("true");
*/

        final AIConfiguration config = new AIConfiguration("257cd8e17d844690ac5e271733f24c1c",
                AIConfiguration.SupportedLanguages.English,   //for voice and both for text
                AIConfiguration.RecognitionEngine.System);

        final EditText input=(EditText)findViewById(R.id.editText);
        ImageButton go=(ImageButton)findViewById(R.id.notification);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hideSoftKeyboard(MainActivity.this);
                final AIDataService aiDataService = new AIDataService(config);

                final AIRequest aiRequest = new AIRequest();
                aiRequest.setQuery(input.getText().toString());

                new AsyncTask<AIRequest, Void, AIResponse>() {
                    @Override
                    protected AIResponse doInBackground(AIRequest... requests) {
                        final AIRequest request = requests[0];
                        try {
                            final AIResponse response = aiDataService.request(aiRequest);
                            return response;
                        } catch (AIServiceException e) {
                            Log.d("ApiAi", "onError from text");
                        }
                        return null;
                    }


                    @Override
                    protected void onPostExecute(AIResponse aiResponse) {
                        if (aiResponse != null) {
                            // process aiResponse here
                            //// TODO: 1/4/17 add textview
                            input.setText("Query:" + aiResponse.getResult().getResolvedQuery()+"\nResponse:"+aiResponse.getResult().getFulfillment().getSpeech());
//                            transaction = response.getText().toString();
                            if(input.getText().toString().split("\n")[1].equals("Response:you are being redirected to FPI having pickle"))
                            {  SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                                SharedPreferences.Editor editor2 = settings.edit();
                                editor2.putString("product", "papad");
                                editor2.commit();

                                Intent intent = new Intent(MainActivity.this, FpiNearbyViewPagerFragmentActivity.class);
                                startActivity(intent);
                                //new RetrieveFeedTask().execute("http://www.yourdomain.com/serverside-script.php");

                            }
                            else if(input.getText().toString().split("\n")[1].equals("Response:you are being redirected to FPI having papad"))
                            {SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                                SharedPreferences.Editor editor2 = settings.edit();
                                editor2.putString("product", "papad");
                                editor2.commit();
                                Intent intent = new Intent(MainActivity.this, FpiNearbyViewPagerFragmentActivity.class);
                                startActivity(intent);
                            }
                            else if(input.getText().toString().split("\n")[1].equals("Response:you are being redirected to FPI having paneer"))
                            {SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                                SharedPreferences.Editor editor2 = settings.edit();
                                editor2.putString("product", "papad");
                                editor2.commit();
                                Intent intent = new Intent(MainActivity.this, FpiNearbyViewPagerFragmentActivity.class);
                                startActivity(intent);
                            }

                        }
                    }
                }.execute(aiRequest);
                input.setText("");
            }
        });




//Speech button
        AIButton aiButton = (AIButton) findViewById(R.id.micButton);


//for the speech recoggnition
        aiButton.initialize(config);

        aiButton.setResultsListener(new AIButton.AIButtonListener() {
            @Override
            public void onResult(AIResponse result) {
                Log.d("ApiAi", "onResult");
                String parameterString = "";
                if (result.getResult().getParameters() != null && !result.getResult().getParameters().isEmpty()) {
                    for (final Map.Entry<String, JsonElement> entry : result.getResult().getParameters().entrySet()) {
                        parameterString += "(" + entry.getKey() + ", " + entry.getValue() + ") ";
                    }
                }
                //// TODO: 1/4/17 textview
                input.setText("Query:" + result.getResult().getResolvedQuery()+"\nResponse:"+result.getResult().getFulfillment().getSpeech());

                if(input.getText().toString().split("\n")[1].equals("Response:you are being redirected to FPI having pickle"))
                {

                    Intent intent = new Intent(MainActivity.this, FpiNearbyViewPagerFragmentActivity.class);
                    startActivity(intent);
                    //new RetrieveFeedTask().execute("http://www.yourdomain.com/serverside-script.php");

                }
                else if(input.getText().toString().split("\n")[1].equals("Response:you are being redirected to FPI having papad"))
                {
                    Intent intent = new Intent(MainActivity.this, FpiNearbyViewPagerFragmentActivity.class);
                    startActivity(intent);
                }
                else if(input.getText().toString().split("\n")[1].equals("Response:you are being redirected to FPI having paneer"))
                {
                    Intent intent = new Intent(MainActivity.this, FpiNearbyViewPagerFragmentActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onError(AIError error) {
                Log.d("ApiAi", "onError");

                //// TODO: 1/4/17 textview
                input.setText(error.toString());
            }

            @Override
            public void onCancelled() {
                Log.d("ApiAi", "onCancelled");
            }
        });
//        latitude=getIntent().getExtras().getString("Latitude");
//        longitude=getIntent().getExtras().getString("Longitude");

        //System.out.println(latitude+","+longitude);


        initView();
    }

    public void initView() {
        drawer_menu=(ImageButton)findViewById(R.id.menu);
        //drawer_menu.setImageDrawable(drawerArrowDrawable);
        drawer_menu.setOnClickListener(this);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);


        
        menu_view=(View) findViewById(R.id.Menu_view);

        notify=(ImageButton)findViewById(R.id.notification);
        framelay=(FrameLayout)findViewById(R.id.frame_lay);
        framelay.setOnClickListener(this);
        home_option=(TextView)findViewById(R.id.homeDrawer);
        /*profile_option=(TextView)findViewById(R.id.profileDrawer);
        settings_option=(TextView)findViewById(R.id.settingDrawer);
        changeLanguage_option=(TextView)findViewById(R.id.changeLanguage);*/
        share_option=(TextView)findViewById(R.id.shareappDrawer);
        rate_option=(TextView)findViewById(R.id.rateTheApp);
        logOut_option=(TextView)findViewById(R.id.logout);

    }

    /**
     * When false, rotates from 3 o'clock to 9 o'clock between a drawer icon and a back arrow.
     * When true, rotates from 9 o'clock to 3 o'clock between a back arrow and a drawer icon.
     */


    public void UpdateDemand(View view){

    }
    public void onClick(View v) {
        if(v==drawer_menu){
            if(!drawerLayout.isDrawerOpen(menu_view))
                drawerLayout.openDrawer(Gravity.LEFT);
            else
                drawerLayout.closeDrawer(menu_view);
        }

        else if(v==framelay)
        {
            if(drawerLayout.isDrawerOpen(menu_view))
                drawerLayout.closeDrawer(menu_view);
        }



    }
    public void FPInearby(View view){
        Intent intent=new Intent(getApplicationContext(), FpiNearbyViewPagerFragmentActivity.class);
        intent.putExtra("Latitude",String.valueOf(latitude));
        intent.putExtra("Longitude",String.valueOf(longitude));
        startActivity(intent);
    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
    public void DashBoard(View view){
        Intent i=new Intent(getApplicationContext(), DashBoardFragmentActivity.class);
        startActivity(i);
    }
    public void Update_Demand(View view){
        Intent n=new Intent(getApplicationContext(),UpdateItemActivity.class);
        startActivity(n);
    }
    public void HOME(View view){
        home_option.setSelected(true);
        /*profile_option.setSelected(false);
        settings_option.setSelected(false);
        changeLanguage_option.setSelected(false);*/
        share_option.setSelected(false);
        rate_option.setSelected(false);
        logOut_option.setSelected(false);
        Intent h=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(h);
    }
    /* public void PROFILE(View view){
         home_option.setSelected(false);
         profile_option.setSelected(true);
         settings_option.setSelected(false);
         changeLanguage_option.setSelected(false);
         share_option.setSelected(false);
         rate_option.setSelected(false);
         logOut_option.setSelected(false);
     }
     public void SETTINGS(View view){
         home_option.setSelected(false);
         profile_option.setSelected(false);
         settings_option.setSelected(true);
         changeLanguage_option.setSelected(false);
         share_option.setSelected(false);
         rate_option.setSelected(false);
         logOut_option.setSelected(false);
     }
     public void CHANGELANGUAGE(View view){
         home_option.setSelected(false);
         profile_option.setSelected(false);
         settings_option.setSelected(false);
         changeLanguage_option.setSelected(true);
         share_option.setSelected(false);
         rate_option.setSelected(false);
         logOut_option.setSelected(false);
     }*/
    public void SHARE_THIS_APP(View view){
        home_option.setSelected(false);
       /* profile_option.setSelected(false);
        settings_option.setSelected(false);
        changeLanguage_option.setSelected(false);*/
        share_option.setSelected(true);
        rate_option.setSelected(false);
        logOut_option.setSelected(false);
    }
    public void RATE_THIS_APP(View view){
        home_option.setSelected(false);
        /*profile_option.setSelected(false);
        settings_option.setSelected(false);
        changeLanguage_option.setSelected(false);*/
        share_option.setSelected(false);
        rate_option.setSelected(true);
        logOut_option.setSelected(false);
    }
    public void LOGOUT(View view){
        home_option.setSelected(false);
/*        profile_option.setSelected(false);
        settings_option.setSelected(false);
        changeLanguage_option.setSelected(false);*/
        share_option.setSelected(false);
        rate_option.setSelected(false);
        logOut_option.setSelected(true);
        new User(MainActivity.this).removeUser();
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Current_User");
        reference.child(settings.getString("company",null)).removeValue();
        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
    public void Payment(View view){
        Intent j=new Intent(getApplicationContext(),PaymentActivity.class);
        startActivity(j);
    }
    public void PurchaseHistory(View view){
        Toast.makeText(getApplicationContext(), "purchase history section selected!",
                Toast.LENGTH_SHORT).show();
    }
    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(Filename);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }
    public void onBackPressed()
    {
        if( count == 1)
        {
            count=0;
            finish();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Press Back again to quit.", Toast.LENGTH_SHORT).show();
            count++;
        }

        return;
    }


}



