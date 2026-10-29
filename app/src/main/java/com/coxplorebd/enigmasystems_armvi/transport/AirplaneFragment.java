package com.coxplorebd.enigmasystems_armvi.transport;

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
import com.coxplorebd.enigmasystems_armvi.adapter.ListViewAdapterTransport;
import com.coxplorebd.enigmasystems_armvi.model.TransportModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Arm_AVI on 1/21/2019.
 */

public class AirplaneFragment extends Fragment {

    ListView listView;
    FloatingActionButton refreshBtn;
    ProgressBar progressBar;
    ArrayList<TransportModel> UserList = new ArrayList<>();
    String HttpURL = "http://www.coxplorebd.com/coxplore_api/transport_airplane_api.php";

    ListViewAdapterTransport listViewAdapterTransport;

    public static AirplaneFragment newInstance() {
        return new AirplaneFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootViewAirplnae = inflater.inflate(R.layout.fragment_item_airplane, container, false);

        listView = rootViewAirplnae.findViewById(R.id.listView1);
        progressBar = rootViewAirplnae.findViewById(R.id.progressbar);
        refreshBtn = rootViewAirplnae.findViewById(R.id.refreshBtn);

        new ParseJSonDataClass(requireContext()).execute();

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ParseJSonDataClass(requireContext()).execute();
            }
        });

        return rootViewAirplnae;
    }

    private class ParseJSonDataClass extends AsyncTask<Void, Void, Void> {
        public android.content.Context context;
        String FinalJSonResult;

        public ParseJSonDataClass(android.content.Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            UserList.clear();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpParseClass httpParseClass = new HttpParseClass(HttpURL);
            try {
                httpParseClass.ExecutePostRequest();

                if (httpParseClass.getResponseCode() == 200) {
                    FinalJSonResult = httpParseClass.getResponse();

                    if (FinalJSonResult != null && !FinalJSonResult.isEmpty()) {
                        JSONArray jsonArray = new JSONArray(FinalJSonResult);
                        UserList = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String Uname = jsonObject.optString("transport_name");
                            String Uphone = jsonObject.optString("transport_phone");
                            String Udescription = jsonObject.optString("transport_details");
                            String Uimage = jsonObject.optString("image_path");
                            String Uweb = jsonObject.optString("transport_website");

                            TransportModel user = new TransportModel(Uname, Uphone, Udescription, Uimage, Uweb);
                            UserList.add(user);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (!isAdded() || getActivity() == null) return;

            progressBar.setVisibility(View.INVISIBLE);
            listViewAdapterTransport = new ListViewAdapterTransport(getActivity(), R.layout.listview_items_transport, UserList);
            listView.setAdapter(listViewAdapterTransport);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(final AdapterView<?> adapterView, View view, final int position, long l) {
                    TransportModel dataPicker = (TransportModel) adapterView.getItemAtPosition(position);
                    Intent transportDataDisplyIntentAirplane = new Intent(getActivity(), TransportDataDisplay.class);

                    transportDataDisplyIntentAirplane.putExtra("transport_name", dataPicker.getName());
                    transportDataDisplyIntentAirplane.putExtra("transport_phone", dataPicker.getPhone());
                    transportDataDisplyIntentAirplane.putExtra("transport_web", dataPicker.getWeb());
                    transportDataDisplyIntentAirplane.putExtra("transport_image", dataPicker.getImage());
                    transportDataDisplyIntentAirplane.putExtra("transport_description", dataPicker.getDescription());
                    startActivity(transportDataDisplyIntentAirplane);
                }
            });
        }
    }
}
