package example.com.krishilabh_retailer;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by User on 7/1/2017.
 */

public class User {

    Context context;

    public void removeUser(){
        sharedPreferences.edit().clear().commit();
    }

    public String getName() {
        name = sharedPreferences.getString("userData",null);
        return name;
    }

    public void setName(String name) {
        this.name = name;
        sharedPreferences.edit().putString("userData",name).commit();
    }

    private String name;
    SharedPreferences sharedPreferences;
    public User(Context context){
        this.context=context;
        sharedPreferences=context.getSharedPreferences("UserInfo",Context.MODE_PRIVATE);

    }
}
