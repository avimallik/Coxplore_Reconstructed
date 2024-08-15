package com.coxplorebd.enigmasystems_armvi.hotel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coxplorebd.enigmasystems_armvi.R;
import com.coxplorebd.enigmasystems_armvi.webpageloader.WebpageLoader;
import com.squareup.picasso.Picasso;

public class HotelDataDisp extends AppCompatActivity {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String phone_number_holder ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_data_disp);

        final TextView nameCap = (TextView) findViewById(R.id.nameCap);
        TextView phoneCap = (TextView) findViewById(R.id.phoneCap);
        TextView descriptionCap = (TextView) findViewById(R.id.descriptionCap);
        final TextView webCap = (TextView) findViewById(R.id.webCap);
        final TextView mapBtn = (TextView) findViewById(R.id.mapBtn);
        ImageView imageCap = (ImageView) findViewById(R.id.imageCap);
        RelativeLayout phoneBtn = (RelativeLayout) findViewById(R.id.phoneBtn);
        RelativeLayout webBtn = (RelativeLayout) findViewById(R.id.webBtn);

        nameCap.setText(getIntent().getExtras().getString("hotel_name"));
        phoneCap.setText(getIntent().getExtras().getString("hotel_phone"));
        webCap.setText(getIntent().getExtras().getString("hotel_website"));
        descriptionCap.setText(Html.fromHtml(getIntent().getExtras().getString("hotel_description")));

        if(getIntent().getExtras().getString("hotel_image").toString().contains("jpg") || getIntent().getExtras().getString("hotel_image").toString().contains("png") || getIntent().getExtras().getString("hotel_image").toString().contains("jpeg")){
            Picasso.get().load(getIntent().getExtras().getString("hotel_image")).into(imageCap);
        }else{
            imageCap.setImageResource(R.drawable.dummy);
        }

        //Phone Number Store
        final SharedPreferences sharedPref = getSharedPreferences("status_memory", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("status_storage",getIntent().getExtras().getString("hotel_phone"));
        editor.putString("status_latitude", getIntent().getExtras().getString("hotel_latitude"));
        editor.putString("status_longitude", getIntent().getExtras().getString("hotel_longitude"));
        editor.putString("status_image", getIntent().getExtras().getString("hotel_image"));
        editor.apply();

        phone_number_holder = sharedPref.getString("status_storage", "");

        final String latitudeGen = sharedPref.getString("status_latitude", "");
        final String longitudeGen = sharedPref.getString("status_longitude", "");
        final String imageGen = sharedPref.getString("status_image", "");

        phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String validNumber = "^[+]?[0-9]{8,15}$";
                String number = phone_number_holder;
                if (number.matches(validNumber)) {
                    callIntenStart("tel:"+number);
                } else {
                    Toast.makeText(getApplicationContext(), "no phone number available", Toast.LENGTH_SHORT).show();
                }

            }
        });

        webBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent webDataLoderIntent = new Intent(HotelDataDisp.this, WebpageLoader.class);
                webDataLoderIntent.putExtra("web_data_hotel", webCap.getText().toString());
                startActivity(webDataLoderIntent);
            }
        });

//        mapBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), latitudeGen + longitudeGen, Toast.LENGTH_SHORT).show();
//
//                Intent mapIntent = new Intent(getApplicationContext(), Map.class);
//                mapIntent.putExtra("latitude_hotel_key", latitudeGen);
//                mapIntent.putExtra("longitude_hotel_key", longitudeGen);
//                mapIntent.putExtra("hotel_name", nameCap.getText().toString());
//                mapIntent.putExtra("hotel_image", imageGen);
//                startActivity(mapIntent);
//            }
//        });

    }

    void callIntenStart(String number){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(number));
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }

}