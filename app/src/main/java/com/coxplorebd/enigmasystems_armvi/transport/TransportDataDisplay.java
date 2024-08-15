package com.coxplorebd.enigmasystems_armvi.transport;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.coxplorebd.enigmasystems_armvi.webpageloader.WebpageLoader;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.coxplorebd.enigmasystems_armvi.databinding.ActivityTransportDataDisplayBinding;

import com.coxplorebd.enigmasystems_armvi.R;
import com.squareup.picasso.Picasso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransportDataDisplay extends AppCompatActivity {


    SharedPreferences pref;
    SharedPreferences.Editor editor;
    //
    //Image extension checker
    String imageExtExp = "([^\\\\s]+(\\\\.(?i)(jpg|png|gif|bmp))$)";
    private Pattern pattern;
    private Matcher matcher;


    TextView nameCap, phoneCap, descriptionCap, webCap;
    ImageView imageCap;
    RelativeLayout phoneBtn, webBtn;
    String phone_number_holder ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_data_display);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();

        nameCap = (TextView) findViewById(R.id.nameCap);
        phoneCap = (TextView) findViewById(R.id.phoneCap);
        descriptionCap = (TextView) findViewById(R.id.descriptionCap);
        webCap = (TextView) findViewById(R.id.webCap);
        imageCap = (ImageView) findViewById(R.id.imageCap);
        phoneBtn = (RelativeLayout) findViewById(R.id.phoneBtn);
        webBtn = (RelativeLayout) findViewById(R.id.webBtn);

        nameCap.setText(getIntent().getExtras().getString("transport_name"));
        phoneCap.setText(getIntent().getExtras().getString("transport_phone"));
        descriptionCap.setText(Html.fromHtml(getIntent().getExtras().getString("transport_description")));
        webCap.setText(getIntent().getExtras().getString("transport_web"));

        Picasso.get().load(getIntent().getExtras().getString("transport_image")).into(imageCap);

        pattern = Pattern.compile(imageExtExp);
        matcher = pattern.matcher(getIntent().getExtras().getString("transport_image"));

        if(getIntent().getExtras().getString("transport_image").toString().contains("jpg") || getIntent().getExtras().getString("transport_image").toString().contains("png") || getIntent().getExtras().getString("transport_image").toString().contains("jpeg")){
            Picasso.get().load(getIntent().getExtras().getString("transport_image")).into(imageCap);
        }else{
            imageCap.setImageResource(R.drawable.dummy);
        }

//        if(getIntent().getExtras().getString("transport_image").contains("jpg") || getIntent().getExtras().getString("transport_image").contains("jpg") || getIntent().getExtras().getString("transport_image").contains("jpg") || getIntent().getExtras().getString("transport_image").contains("jpg")){
//            Picasso.with(getApplicationContext()).load(getIntent().getExtras().getString("transport_image")).into(imageCap);
//        }
//        else{
//           imageCap.setImageResource(R.drawable.dummy);
//        }

//        Picasso.with(getApplicationContext()).load(getIntent().getExtras().getString("transport_image")).into(imageCap);


        //Phone Number Store
        SharedPreferences sharedPref = getSharedPreferences("status_memory", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("status_storage",getIntent().getExtras().getString("transport_phone"));
        editor.apply();

        phone_number_holder = sharedPref.getString("status_storage", "");

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
                Intent webDataLoderIntent = new Intent(TransportDataDisplay.this, WebpageLoader.class);
                webDataLoderIntent.putExtra("web_data_transport_bus", webCap.getText().toString());
                startActivity(webDataLoderIntent);
            }
        });
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