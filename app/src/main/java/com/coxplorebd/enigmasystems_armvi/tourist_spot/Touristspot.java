package com.coxplorebd.enigmasystems_armvi.tourist_spot;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.coxplorebd.enigmasystems_armvi.HttpParser.HttpParseClass;
import com.coxplorebd.enigmasystems_armvi.adapter.ListViewAdapterTouristspot;
import com.coxplorebd.enigmasystems_armvi.model.TouristspotModel;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.coxplorebd.enigmasystems_armvi.databinding.ActivityTouristspotBinding;

import com.coxplorebd.enigmasystems_armvi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Touristspot extends AppCompatActivity {


    ListView listView;
    ProgressBar progressBar;
    ArrayList<TouristspotModel> UserList = new ArrayList<TouristspotModel>();
    String HttpURL = "http://www.coxplorebd.com/coxplore_api/touristspot_api.php";

    ListViewAdapterTouristspot listViewAdapterTouristspot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touristspot);

        listView = (ListView) findViewById(R.id.listView1);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        new ParseJSonDataClass(Touristspot.this).execute();

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
                            TouristspotModel user;

                            UserList = new ArrayList<TouristspotModel>();

                            for (int i = 0; i < jsonArray.length(); i++) {

                                jsonObject = jsonArray.getJSONObject(i);

                                String Uname = jsonObject.getString("tourist_spot_name").toString();

                                String Udescription = jsonObject.getString("tourist_spot_details").toString();

                                String Ulatitude = jsonObject.getString("latitude").toString();

                                String Ulongitude = jsonObject.getString("longitude").toString();

                                String Uimage = jsonObject.getString("image_one_path").toString();

                                String UimageTwo = jsonObject.getString("image_two_path").toString();

                                String UimageThree = jsonObject.getString("image_three_path").toString();

                                user = new TouristspotModel(Uname, Udescription, Ulatitude, Ulongitude, Uimage, UimageTwo, UimageThree);

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
            listViewAdapterTouristspot = new ListViewAdapterTouristspot(Touristspot.this, R.layout.listview_item_touristspot, UserList);
            listView.setAdapter(listViewAdapterTouristspot);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(final AdapterView<?> adapterView, View view, final int position, long l) {
                    TouristspotModel dataPicker = (TouristspotModel) adapterView.getItemAtPosition(position);
//                    Toast.makeText(getApplicationContext(), dataPicker.getLatitude() +" "+ dataPicker.getLongitude()+""+dataPicker.getDescription(),Toast.LENGTH_SHORT).show();
                    Intent touristspotIntentDisp = new Intent(Touristspot.this, TouristspotDataDisp.class);

                    touristspotIntentDisp.putExtra("touristspot_name", dataPicker.getName());

                    touristspotIntentDisp.putExtra("touristspot_image", dataPicker.getImage());
                    touristspotIntentDisp.putExtra("touristspot_image_two", dataPicker.getImageTwo());
                    touristspotIntentDisp.putExtra("touristspot_image_three", dataPicker.getImageThree());

                    touristspotIntentDisp.putExtra("touristspot_description", dataPicker.getDescription());
                    touristspotIntentDisp.putExtra("touristspot_latitude", dataPicker.getLatitude());
                    touristspotIntentDisp.putExtra("touristspot_longitude", dataPicker.getLongitude());

                    startActivity(touristspotIntentDisp);
                }
            });
        }
    }


}