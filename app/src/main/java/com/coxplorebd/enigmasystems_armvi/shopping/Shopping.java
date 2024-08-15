package com.coxplorebd.enigmasystems_armvi.shopping;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Html;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.coxplorebd.enigmasystems_armvi.R;
import com.coxplorebd.enigmasystems_armvi.adapter.SliderAdapter;
import com.coxplorebd.enigmasystems_armvi.model.Slider;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Shopping extends AppCompatActivity {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String phone_number_holder;

    private static String data = "";
    TextView shoppingDescription;

    SliderView imageSlider;
    ArrayList<Slider> sliderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        shoppingDescription = (TextView) findViewById(R.id.shoppingDescription);
        imageSlider = (SliderView) findViewById(R.id.image_slider);

        String url = "http://www.coxplorebd.com/coxplore_api/shopping_api.php";
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    //Data collection
                    //Description
                    String description = jsonObject.getString("shopping_details");
                    shoppingDescription.setText(Html.fromHtml(description));
                    //Image URL
                    String image = jsonObject.getString("image_one_path");
                    String imageTwo = jsonObject.getString("image_two_path");
                    String imageThree = jsonObject.getString("image_three_path");

                    sliderList = new ArrayList<>();
                    sliderList.add(new Slider(image));
                    sliderList.add(new Slider(imageTwo));
                    sliderList.add(new Slider(imageThree));
                    SliderAdapter adapter = new SliderAdapter(getApplicationContext(), sliderList);

                    imageSlider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
                    imageSlider.setSliderAdapter(adapter);
                    imageSlider.setScrollTimeInSec(3);
                    imageSlider.setAutoCycle(true);
                    imageSlider.startAutoCycle();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Anything you want
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

}