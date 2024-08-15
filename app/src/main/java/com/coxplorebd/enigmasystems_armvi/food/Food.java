package com.coxplorebd.enigmasystems_armvi.food;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.coxplorebd.enigmasystems_armvi.HttpParser.HttpParseClass;
import com.coxplorebd.enigmasystems_armvi.adapter.ListViewAdapterFood;
import com.coxplorebd.enigmasystems_armvi.model.FoodModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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

public class Food extends AppCompatActivity {

    ListView listView;
    ProgressBar progressBar;
    ArrayList<FoodModel> UserList = new ArrayList<FoodModel>();
    String HttpURL = "http://www.coxplorebd.com/coxplore_api/food_api.php";

    ListViewAdapterFood listViewAdapterFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        listView = (ListView) findViewById(R.id.listView1);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ParseJSonDataClass(Food.this).execute();
            }
        });

        new ParseJSonDataClass(Food.this).execute();
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
                            FoodModel user;

                            UserList = new ArrayList<FoodModel>();

                            for (int i = 0; i < jsonArray.length(); i++) {

                                jsonObject = jsonArray.getJSONObject(i);

                                String Uname = jsonObject.getString("restaurant_name").toString();

                                String Uphone = jsonObject.getString("phone").toString();

                                String Udescription = jsonObject.getString("restaurant_details").toString();

                                String Ulatitude = jsonObject.getString("latitude").toString();

                                String Ulongitude = jsonObject.getString("longitude").toString();

                                String Uimage = jsonObject.getString("image_path").toString();

                                user = new FoodModel(Uname, Uphone, Udescription, Ulatitude, Ulongitude, Uimage);

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
//
//            ProgressBar progressBar;
//            progressBar = (ProgressBar) findViewById(R.id.progressbar);
//            progressBar.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            listViewAdapterFood = new ListViewAdapterFood(Food.this, R.layout.listview_item_food, UserList);
            listView.setAdapter(listViewAdapterFood);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(final AdapterView<?> adapterView, View view, final int position, long l) {

                    //use next
                    FoodModel dataPicker = (FoodModel) adapterView.getItemAtPosition(position);

////                    Toast.makeText(getApplicationContext(), dataPicker.getLatitude() +" "+ dataPicker.getLongitude()+""+dataPicker.getDescription(),Toast.LENGTH_SHORT).show();
                    Intent foodIntentDisp = new Intent(Food.this, FoodDataDisp.class);

                    foodIntentDisp.putExtra("restaurant_name", dataPicker.getName());
                    foodIntentDisp.putExtra("restaurant_phone", dataPicker.getPhone());
                    foodIntentDisp.putExtra("restaurant_image", dataPicker.getImage());
                    foodIntentDisp.putExtra("restaurant_description", dataPicker.getDescription());
                    foodIntentDisp.putExtra("restaurant_latitude", dataPicker.getLatitude());
                    foodIntentDisp.putExtra("restaurant_longitude", dataPicker.getLongitude());
                    startActivity(foodIntentDisp);

                }
            });
        }
    }


}