package com.coxplorebd.enigmasystems_armvi.travel_agent;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.coxplorebd.enigmasystems_armvi.HttpParser.HttpParseClass;
import com.coxplorebd.enigmasystems_armvi.adapter.ListViewAdapterTravelAgent;
import com.coxplorebd.enigmasystems_armvi.model.TravelAgentModel;


import androidx.appcompat.app.AppCompatActivity;

import com.coxplorebd.enigmasystems_armvi.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TravelAgent extends AppCompatActivity {

    ListView listView;
    ProgressBar progressBar;
    ArrayList<TravelAgentModel> UserList = new ArrayList<TravelAgentModel>();
    String HttpURL = "http://www.coxplorebd.com/coxplore_api/travelagent_api.php";

    ListViewAdapterTravelAgent listViewAdapterTravelAgent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_agent);
        listView = (ListView) findViewById(R.id.listView1);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        new ParseJSonDataClass(getApplicationContext()).execute();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ParseJSonDataClass(getApplicationContext()).execute();
            }
        });
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
                            TravelAgentModel user;

                            UserList = new ArrayList<TravelAgentModel>();

                            for (int i = 0; i < jsonArray.length(); i++) {

                                jsonObject = jsonArray.getJSONObject(i);

                                String Uname = jsonObject.getString("tourist_agent_name").toString();

                                String Uphone = jsonObject.getString("phone").toString();

                                String Udescription = jsonObject.getString("tourist_agent_details").toString();

                                String Ulatitude = jsonObject.getString("latitude").toString();

                                String Ulongitude = jsonObject.getString("longitude").toString();

                                String Uimage = jsonObject.getString("image_path").toString();

                                user = new TravelAgentModel(Uname, Uphone, Udescription, Ulatitude, Ulongitude, Uimage);

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
            listViewAdapterTravelAgent = new ListViewAdapterTravelAgent(TravelAgent.this, R.layout.listview_item_travelagent, UserList);
            listView.setAdapter(listViewAdapterTravelAgent);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(final AdapterView<?> adapterView, View view, final int position, long l) {

                    //Next use
                    TravelAgentModel dataPicker = (TravelAgentModel) adapterView.getItemAtPosition(position);
//                    Toast.makeText(getApplicationContext(), dataPicker.getLatitude() +" "+ dataPicker.getLongitude()+""+dataPicker.getDescription(),Toast.LENGTH_SHORT).show();
                    Intent travelAgentIntentDisp = new Intent(TravelAgent.this, TravelAgentDataDisp.class);

                    travelAgentIntentDisp.putExtra("tourist_agent_name", dataPicker.getName());
                    travelAgentIntentDisp.putExtra("tourist_agent_phone", dataPicker.getPhone());
                    travelAgentIntentDisp.putExtra("tourist_agent_image", dataPicker.getImage());
                    travelAgentIntentDisp.putExtra("tourist_agent_description", dataPicker.getDescription());
                    travelAgentIntentDisp.putExtra("tourist_agent_latitude", dataPicker.getLatitude());
                    travelAgentIntentDisp.putExtra("tourist_agent_longitude", dataPicker.getLongitude());
                    startActivity(travelAgentIntentDisp);

                }
            });
        }
    }

}