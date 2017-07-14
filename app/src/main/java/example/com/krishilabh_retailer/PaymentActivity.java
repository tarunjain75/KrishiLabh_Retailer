package example.com.krishilabh_retailer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import example.com.krishilabh_retailer.Adapter.PaymentListCustomAdapter;


/**
 * Created by User on 4/2/2017.
 */

public class PaymentActivity extends Activity implements View.OnClickListener {
    ImageView backbutn;
    ListView listView;
    String fpiName[]={"beta","bhangal"};
    PaymentListCustomAdapter paymentadapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_layout);
        listView=(ListView)findViewById(R.id.listview);
         paymentadapter=new PaymentListCustomAdapter(getApplicationContext(),fpiName);
        listView.setAdapter(paymentadapter);
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(PaymentActivity.this, "payment will proceed", Toast.LENGTH_SHORT).show();
                Intent in=new Intent(PaymentActivity.this,PaymentActivity2.class);
                startActivity(in);
            }
        });

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
