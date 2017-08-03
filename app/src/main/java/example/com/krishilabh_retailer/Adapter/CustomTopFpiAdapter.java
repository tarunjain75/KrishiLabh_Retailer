package example.com.krishilabh_retailer.Adapter;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.CircleProgress;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import example.com.krishilabh_retailer.R;


/**
 * Created by User on 3/29/2017.
 */

public class CustomTopFpiAdapter extends BaseAdapter {

    public LayoutInflater Newinflator;
    public Context context;
    public ArrayList<String> listItems=new ArrayList<>();
    public CircleProgress circleProgress;

    public ArrayList<Integer>  Matchpercent=new ArrayList<Integer> ();
    public String Gainpercent[];

    public CustomTopFpiAdapter(Context context, ArrayList<String> listItems, ArrayList<Integer> M ){
        this.context=context;
        this.listItems=listItems;
        this.Matchpercent=M;

        Newinflator= LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return (Matchpercent.size()-1 );
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=Newinflator.inflate(R.layout.top_fpi_nearby,null);
        TextView title=(TextView)convertView.findViewById(R.id.title);

         circleProgress=(CircleProgress)convertView.findViewById(R.id.Matchpercent);

        title.setText(listItems.get(position));
        circleProgress.setProgress(Matchpercent.get(position));
        //gainpercent.setText(Gainpercent[position]);
        return convertView;
    }
}
