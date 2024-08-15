package com.coxplorebd.enigmasystems_armvi.tourist_spot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.coxplorebd.enigmasystems_armvi.adapter.SliderAdapter;
import com.coxplorebd.enigmasystems_armvi.model.Slider;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import com.coxplorebd.enigmasystems_armvi.R;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class TouristspotDataDisp extends AppCompatActivity {

    SliderView imageSlider;
    ArrayList<Slider> sliderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touristspot_data_disp);

        final TextView nameCap = (TextView) findViewById(R.id.nameCap);
        TextView descriptionCap = (TextView) findViewById(R.id.descriptionCap);
        final TextView mapBtn = (TextView) findViewById(R.id.mapBtn);
        imageSlider = (SliderView) findViewById(R.id.image_slider);

        nameCap.setText(getIntent().getExtras().getString("touristspot_name"));
        descriptionCap.setText(Html.fromHtml(getIntent().getExtras().getString("touristspot_description")));

        final SharedPreferences sharedPref = getSharedPreferences("status_memory", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putString("status_storage",getIntent().getExtras().getString("restaurant_phone"));
        editor.putString("status_latitude", getIntent().getExtras().getString("touristspot_latitude"));
        editor.putString("status_longitude", getIntent().getExtras().getString("touristspot_longitude"));
        editor.putString("status_image", getIntent().getExtras().getString("touristspot_image"));
        editor.putString("status_image_two", getIntent().getExtras().getString("touristspot_image_two"));
        editor.putString("status_image_three", getIntent().getExtras().getString("touristspot_image_three"));
        editor.apply();

        final String latitudeGen = sharedPref.getString("status_latitude", "");
        final String longitudeGen = sharedPref.getString("status_longitude", "");

        //Image slider
        final String imageGenOne = sharedPref.getString("status_image", "");
        final String imageGenTwo = sharedPref.getString("status_image_two", "");
        final String imageGenThree = sharedPref.getString("status_image_three", "");

        sliderList = new ArrayList<>();
        sliderList.add(new Slider(imageGenOne));
        sliderList.add(new Slider(imageGenTwo));
        sliderList.add(new Slider(imageGenThree));
        SliderAdapter adapter = new SliderAdapter(getApplicationContext(), sliderList);

        imageSlider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        imageSlider.setSliderAdapter(adapter);
        imageSlider.setScrollTimeInSec(3);
        imageSlider.setAutoCycle(true);
        imageSlider.startAutoCycle();


        //Commenting out for further use
//        mapBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), latitudeGen + longitudeGen, Toast.LENGTH_SHORT).show();
//
//                Intent mapIntent = new Intent(getApplicationContext(), Map.class);
//                mapIntent.putExtra("latitude_touristspot_key", latitudeGen);
//                mapIntent.putExtra("longitude_touristspot_key", longitudeGen);
//                mapIntent.putExtra("touristspot_name", nameCap.getText().toString());
//                mapIntent.putExtra("touristspot_image", imageGenOne);
//                startActivity(mapIntent);
//            }
//        });

    }

}