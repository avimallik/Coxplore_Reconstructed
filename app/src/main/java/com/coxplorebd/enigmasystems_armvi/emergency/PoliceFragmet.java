package com.coxplorebd.enigmasystems_armvi.emergency;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.coxplorebd.enigmasystems_armvi.HttpParser.HttpParseClass;
import com.coxplorebd.enigmasystems_armvi.R;

import com.coxplorebd.enigmasystems_armvi.adapter.ListViewAdapterEmergency;
import com.coxplorebd.enigmasystems_armvi.model.EmergencyModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Arm_AVI on 1/29/2019.
 */

public class PoliceFragmet extends Fragment {

    ListView listView;
    FloatingActionButton refreshBtn;
    ProgressBar progressBar;
    ArrayList<EmergencyModel> UserList = new ArrayList<EmergencyModel>();
    String HttpURL = "http://www.coxplorebd.com/coxplore_api/police_api.php";

    ListViewAdapterEmergency listViewAdapterEmergency;

    public static PoliceFragmet newInstance() {
        PoliceFragmet fragment = new PoliceFragmet();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ParseJSonDataClass(getActivity()).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootViewPolice = inflater.inflate(R.layout.fragment_item_police,
                container, false);
        listView = (ListView) rootViewPolice.findViewById(R.id.listView1);
        progressBar = (ProgressBar) rootViewPolice.findViewById(R.id.progressbar);
        refreshBtn = (FloatingActionButton) rootViewPolice.findViewById(R.id.refreshBtn);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ParseJSonDataClass(getActivity()).execute();
            }
        });
//        nameTest = (TextView) rootViewBus.findViewById(R.id.nameTest);
        return rootViewPolice;
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
                            EmergencyModel user;

                            UserList = new ArrayList<EmergencyModel>();

                            for (int i = 0; i < jsonArray.length(); i++) {

                                jsonObject = jsonArray.getJSONObject(i);

                                String Uname = jsonObject.getString("police_name").toString();

                                String Uphone = jsonObject.getString("phone").toString();

                                String Udescription = jsonObject.getString("police_details").toString();

                                String Ulatitude = jsonObject.getString("latitude").toString();

                                String Ulongitude = jsonObject.getString("longitude").toString();

                                String Uimage = jsonObject.getString("image_path").toString();

                                user = new EmergencyModel(Uname, Uphone, Udescription, Ulatitude, Ulongitude, Uimage);

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
            listViewAdapterEmergency = new ListViewAdapterEmergency(getActivity(), R.layout.listview_item_emergency, UserList);
            listView.setAdapter(listViewAdapterEmergency);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(final AdapterView<?> adapterView, View view, final int position, long l) {

                    //Next use
                    EmergencyModel dataPicker = (EmergencyModel) adapterView.getItemAtPosition(position);
//                    Toast.makeText(getActivity(), dataPicker.getLatitude() +" "+ dataPicker.getLongitude()+""+dataPicker.getDescription(),Toast.LENGTH_SHORT).show();
                    Intent emergencyIntentDisp = new Intent(getActivity(), EmergencyDataDisp.class);

                    emergencyIntentDisp.putExtra("emergency_name", dataPicker.getName());
                    emergencyIntentDisp.putExtra("emegency_phone", dataPicker.getPhone());
                    emergencyIntentDisp.putExtra("emergency_image", dataPicker.getImage());
                    emergencyIntentDisp.putExtra("emergency_description", dataPicker.getDescription());
                    emergencyIntentDisp.putExtra("emergency_latitude", dataPicker.getLatitude());
                    emergencyIntentDisp.putExtra("emergency_longitude", dataPicker.getLongitude());
                    startActivity(emergencyIntentDisp);

                }
            });
        }
    }

}
