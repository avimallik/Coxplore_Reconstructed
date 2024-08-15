package com.coxplorebd.enigmasystems_armvi.hotel;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.coxplorebd.enigmasystems_armvi.HttpParser.HttpParseClass;
import com.coxplorebd.enigmasystems_armvi.adapter.ListViewAdapterHotel;
import com.coxplorebd.enigmasystems_armvi.model.HotelModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.coxplorebd.enigmasystems_armvi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Hotel extends AppCompatActivity {

    ListView listView;
    ProgressBar progressBar;
    ArrayList<HotelModel> UserList = new ArrayList<HotelModel>();
    String HttpURL = "http://www.coxplorebd.com/coxplore_api/hotel_api.php";
    ListViewAdapterHotel listViewAdapterHotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ParseJSonDataClass(Hotel.this).execute();
            }
        });

        listView = (ListView) findViewById(R.id.listView1);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        new ParseJSonDataClass(Hotel.this).execute();

    }

    private class ParseJSonDataClass extends AsyncTask<Void, Void, Void> {
        public Context context;
        String FinalJSonResult;

        public ParseJSonDataClass(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpParseClass httpParseClass = new HttpParseClass(HttpURL);
            try {
                httpParseClass.ExecutePostRequest();
                if (httpParseClass.getResponseCode() == 200) {
                    FinalJSonResult = httpParseClass.getResponse();
                    if (FinalJSonResult != null) {

                        JSONArray jsonArray = null;
                        try {

                            jsonArray = new JSONArray(FinalJSonResult);
                            JSONObject jsonObject;
                            HotelModel user;

                            UserList = new ArrayList<HotelModel>();

                            for (int i = 0; i < jsonArray.length(); i++) {

                                jsonObject = jsonArray.getJSONObject(i);

                                String Uname = jsonObject.getString("hotel_name").toString();

                                String Uphone = jsonObject.getString("hotel_phone").toString();

                                String Udescription = jsonObject.getString("hotel_details").toString();

                                String Uweb = jsonObject.getString("hotel_website").toString();

                                String Ulatitude = jsonObject.getString("latitude").toString();

                                String Ulongitude = jsonObject.getString("longitude").toString();

                                String Uimage = jsonObject.getString("image_path").toString();

                                user = new HotelModel(Uname, Uphone, Udescription,Uweb, Ulatitude, Ulongitude, Uimage);

                                UserList.add(user);
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } else {

                    Toast.makeText(context, httpParseClass.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){

            progressBar.setVisibility(View.INVISIBLE);
            listViewAdapterHotel = new ListViewAdapterHotel(Hotel.this, R.layout.listview_item_hotel, UserList);
            listView.setAdapter(listViewAdapterHotel);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(final AdapterView<?> adapterView, View view, final int position, long l) {

                    //Commenting out foe further use

                    HotelModel dataPicker = (HotelModel) adapterView.getItemAtPosition(position);
//                    Toast.makeText(getApplicationContext(), dataPicker.getLatitude() +" "+ dataPicker.getLongitude()+""+dataPicker.getDescription(),Toast.LENGTH_SHORT).show();
                    Intent hotelIntentDisp = new Intent(Hotel.this, HotelDataDisp.class);
                    hotelIntentDisp.putExtra("hotel_name", dataPicker.getName());
                    hotelIntentDisp.putExtra("hotel_phone", dataPicker.getPhone());
                    hotelIntentDisp.putExtra("hotel_image", dataPicker.getImage());
                    hotelIntentDisp.putExtra("hotel_description", dataPicker.getDescription());
                    hotelIntentDisp.putExtra("hotel_website", dataPicker.getWeb());
                    hotelIntentDisp.putExtra("hotel_latitude", dataPicker.getLatitude());
                    hotelIntentDisp.putExtra("hotel_longitude", dataPicker.getLongitude());
                    startActivity(hotelIntentDisp);

                }
            });
        }
    }


}