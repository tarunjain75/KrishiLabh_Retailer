package example.com.krishilabh_retailer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.firebase.database.FirebaseDatabase;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by User on 7/1/2017.
 */

public class SplashScreenActivity extends Activity {
     AVLoadingIndicatorView avi;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_screen_layout);
        //FirebaseDatabase.getInstance().getReference().keepSynced(true);
        avi= (AVLoadingIndicatorView) findViewById(R.id.avi);
        final User USER=new User(SplashScreenActivity.this);
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                if(USER.getName()!=null){
                    Intent i=new Intent(getApplicationContext(),MainActivity.class);

                    System.out.println("Check"+USER.getName());
                    startActivity(i);
                    finish();
                }
                else{
                    Intent i=new Intent(getApplicationContext(),LoginActivity.class);
                    System.out.println("Check 1"+" "+USER.getName());

                    startActivity(i);
                    finish();

                }
            }
        },2000);

    }
}
