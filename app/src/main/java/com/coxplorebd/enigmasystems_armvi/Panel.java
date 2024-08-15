package com.coxplorebd.enigmasystems_armvi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.coxplorebd.enigmasystems_armvi.adapter.CustomAdapter;
import com.coxplorebd.enigmasystems_armvi.emergency.Emergency;
import com.coxplorebd.enigmasystems_armvi.explore.Explore;
import com.coxplorebd.enigmasystems_armvi.food.Food;
import com.coxplorebd.enigmasystems_armvi.hotel.Hotel;
import com.coxplorebd.enigmasystems_armvi.model.Model;
import com.coxplorebd.enigmasystems_armvi.shopping.Shopping;
import com.coxplorebd.enigmasystems_armvi.tourist_spot.Touristspot;
import com.coxplorebd.enigmasystems_armvi.transport.Transport;
import com.coxplorebd.enigmasystems_armvi.travel_agent.TravelAgent;
import com.coxplorebd.enigmasystems_armvi.webpageloader.WebpageLoader;

import java.util.ArrayList;
import java.util.List;

public class Panel extends AppCompatActivity {


    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    CustomAdapter adapter;
    GridView gv;

    String weatherWebAppUrl = "file:///android_res/raw/weather.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);

        checkAndRequestPermissions();

        gv= (GridView) findViewById(R.id.gv);
        adapter = new CustomAdapter(this,getData());
        gv.setAdapter(adapter);

//        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.grid_item_anim);
//        GridLayoutAnimationController controller = new GridLayoutAnimationController(animation, .2f, .2f);
//        gv.setLayoutAnimation(controller);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

//                mInterstitialAd.show();

                if(position == 0){
                    //Ads Launcher
                    if(checkInternet()){
                        startActivity(new Intent(getApplicationContext(), Explore.class));
                    } else {
                        alert();
                    }

                }else if(position == 2){

//                    mInterstitialAd.show();

                    if(checkInternet()){
                        // Step 2.2: Start the new activity
                        startActivity(new Intent(getApplicationContext(), Transport.class));
                    }
                    else{
                        alert();
                    }

                }else if(position == 7){

//                    mInterstitialAd.show();

                    if(checkInternet()){
                        // Step 2.2: Start the new activity
                        startActivity(new Intent(getApplicationContext(), Shopping.class));
                    }
                    else{
                        alert();
                    }
                }else if(position == 8){

//                    mInterstitialAd.show();

                    if(checkInternet()){
                        startActivity(new Intent(getApplicationContext(), TravelAgent.class));
                    }
                    else{
                        alert();
                    }
                }else if(position == 5){

//                    mInterstitialAd.show();

                    if(checkInternet()){
                        startActivity(new Intent(getApplicationContext(), Food.class));
                    }
                    else{
                        alert();
                    }

                }else if(position == 3){

//                    mInterstitialAd.show();

                    if(checkInternet()){
                        startActivity(new Intent(getApplicationContext(), Hotel.class));
                    }
                    else{
                        alert();
                    }
                }else if(position == 1){

//                    mInterstitialAd.show();

                    if(checkInternet()){
                        Intent weatherIntent = new Intent(Panel.this, WebpageLoader.class);
                        weatherIntent.putExtra("weather_app_111", weatherWebAppUrl);
                        startActivity(weatherIntent);
                    }
                    else{
                        alert();
                    }
                }else if(position == 6){

//                    mInterstitialAd.show();

                    if(checkInternet()){
                        startActivity(new Intent(getApplicationContext(), Emergency.class));
                    }
                    else{
                        alert();
                    }
                }else if(position == 4){

//                    mInterstitialAd.show();

                    if(checkInternet()){
                        startActivity(new Intent(getApplicationContext(), Touristspot.class));
                    }
                    else{
                        alert();
                    }
                }
            }
        });

    }

    private ArrayList getData() {

        ArrayList<Model> models = new ArrayList<>();

        Model s=new Model();
        s.setName(getString(R.string.explore_title));
        s.setImage(R.drawable.exploreico);
        models.add(s);

        s=new Model();
        s.setName(getString(R.string.weather_title));
        s.setImage(R.drawable.weatherico);
        models.add(s);


        s=new Model();
        s.setName(getString(R.string.transport_title));
        s.setImage(R.drawable.transportico);
        models.add(s);


        s=new Model();
        s.setName(getString(R.string.hotel_title));
        s.setImage(R.drawable.hotelico);
        models.add(s);

        s=new Model();
        s.setName(getString(R.string.tourist_spot_title));
        s.setImage(R.drawable.touristspotico);
        models.add(s);

        s=new Model();
        s.setName(getString(R.string.food_title));
        s.setImage(R.drawable.foodico);
        models.add(s);

        s=new Model();
        s.setName(getString(R.string.emerency_title));
        s.setImage(R.drawable.emergencyico);
        models.add(s);

        s=new Model();
        s.setName(getString(R.string.shopping_title));
        s.setImage(R.drawable.shoppingico);
        models.add(s);

        s=new Model();
        s.setName(getString(R.string.atm_tab_title));
        s.setImage(R.drawable.atmico);
        models.add(s);

        return models;
    }

    public boolean checkInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        }
        return false;
    }

    public void alert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Panel.this);
        builder.setTitle(getResources().getString(R.string.confirmation));
        builder.setMessage(getResources().getString(R.string.confirmation_msg));
        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                final Intent intent = new Intent(Intent.ACTION_MAIN, null);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                final ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.wifi.WifiSettings");
                intent.setComponent(cn);
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show(); }

    // Function for Multiple Device permission in Runtime
    private  boolean checkAndRequestPermissions() {
        int phoneCall = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE);
        int location = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (phoneCall != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CALL_PHONE);
        }
        if (location != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty())
        {
            ActivityCompat.requestPermissions(this,listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}