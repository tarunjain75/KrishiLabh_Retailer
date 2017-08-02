package example.com.krishilabh_retailer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import example.com.krishilabh_retailer.Data.PaymentDetails;
import example.com.krishilabh_retailer.PaymentActivity2;
import example.com.krishilabh_retailer.R;


/**
 * Created by User on 4/2/2017.
 */

public class PaymentListCustomAdapter extends RecyclerView.Adapter<PaymentListCustomAdapter.Viewholder> {
    ArrayList<PaymentDetails> paymentDetails=new ArrayList<>();
    Context context;

    public PaymentListCustomAdapter(ArrayList<PaymentDetails> paymentDetails, Context context) {
        this.paymentDetails = paymentDetails;
        this.context = context;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_list_view_layout,parent,false);
        PaymentListCustomAdapter.Viewholder vh=new PaymentListCustomAdapter.Viewholder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final Viewholder holder, final int position) {
            holder.FIRMName.setText(paymentDetails.get(position).getFirm());
            holder.COST.setText(paymentDetails.get(position).getAmount());
            holder.ApprovedFirmName.setText(paymentDetails.get(position).getFirm());
            holder.PAY.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final SharedPreferences settings= PreferenceManager.getDefaultSharedPreferences(context);
                    DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Notification")
                            .child(paymentDetails.get(position).getFirm()).child(settings.getString("company",null));
                    ValueEventListener postlistner= new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            HashMap<String,Object> status=new HashMap<String, Object>();
                            status=(HashMap<String,Object>)dataSnapshot.getValue();
                            Log.e("Status",status.toString());
                            String s=(String)status.get("status");
                            if(s.equals("Approved")){
                                Intent intent=new Intent(context, PaymentActivity2.class);
                                intent.putExtra("FPIName",paymentDetails.get(position).getFirm());
                                intent.putExtra("TotalCost",paymentDetails.get(position).getAmount());
                                context.startActivity(intent);
                            }
                            else if(s.equals("pending")){

                                new MaterialDialog.Builder(context)
                                        .title("Please Wait")
                                        .content("Not Approved yet by"+" "+paymentDetails.get(position).getFirm())
                                        .cancelable(true)
                                        .show();

                            }else if(s.equals("PaymentDone")){
                                holder.ApprovedLay.setVisibility(View.VISIBLE);
                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    };
                    reference.addValueEventListener(postlistner);


                }
            });
    }

    @Override
    public int getItemCount() {
        return paymentDetails.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView FIRMName,COST,ApprovedFirmName;
        Button PAY;
        LinearLayout ApprovedLay;


        public Viewholder(View itemView) {
            super(itemView);
            FIRMName=(TextView)itemView.findViewById(R.id.fpiname);
            COST=(TextView)itemView.findViewById(R.id.cost);
            PAY=(Button)itemView.findViewById(R.id.payButton);
            ApprovedFirmName=(TextView)itemView.findViewById(R.id.ApprovedCompanyName);
            ApprovedLay=(LinearLayout)itemView.findViewById(R.id.ApprovedLay);
        }
    }
}
