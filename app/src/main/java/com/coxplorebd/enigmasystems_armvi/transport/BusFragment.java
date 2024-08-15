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

public class BusFragment extends Fragment {

    ListView listView;
    FloatingActionButton refreshBtn;
    ProgressBar progressBar;
    ArrayList<TransportModel> UserList = new ArrayList<TransportModel>();
    String HttpURL = "http://www.coxplorebd.com/coxplore_api/transport_bus_api.php";

    ListViewAdapterTransport listViewAdapterTransport;


    public static BusFragment newInstance() {
        BusFragment fragment = new BusFragment();
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
        View rootViewBus = inflater.inflate(R.layout.fragment_item_bus,
                container, false);
        listView = (ListView) rootViewBus.findViewById(R.id.listView1);
        progressBar = (ProgressBar) rootViewBus.findViewById(R.id.progressbar);
        refreshBtn = (FloatingActionButton) rootViewBus.findViewById(R.id.refreshBtn);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ParseJSonDataClass(getActivity()).execute();
            }
        });

//        nameTest = (TextView) rootViewBus.findViewById(R.id.nameTest);
        return rootViewBus;
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
                            TransportModel user;

                            UserList = new ArrayList<TransportModel>();

                            for (int i = 0; i < jsonArray.length(); i++) {

                                jsonObject = jsonArray.getJSONObject(i);

                                String Uname = jsonObject.getString("transport_name").toString();

                                String Uphone = jsonObject.getString("transport_phone").toString();

                                String Udescription = jsonObject.getString("transport_details").toString();

                                String Uimage = jsonObject.getString("image_path").toString();

                                String Uweb = jsonObject.getString("transport_website").toString();

                                user = new TransportModel(Uname, Uphone, Udescription, Uimage, Uweb);

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
            listViewAdapterTransport = new ListViewAdapterTransport(getActivity(), R.layout.listview_items_transport, UserList);
            listView.setAdapter(listViewAdapterTransport);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(final AdapterView<?> adapterView, View view, final int position, long l) {

//                    TransportModel dataPicker = (TransportModel) adapterView.getItemAtPosition(position);
//                    Intent transportDataDisplyIntentBus = new Intent(getActivity(), TransportDataDisplay.class);
//
//                    transportDataDisplyIntentBus.putExtra("transport_name", dataPicker.getName());
//                    transportDataDisplyIntentBus.putExtra("transport_phone", dataPicker.getPhone());
//                    transportDataDisplyIntentBus.putExtra("transport_web", dataPicker.getWeb());
//                    transportDataDisplyIntentBus.putExtra("transport_image", dataPicker.getImage());
//                    transportDataDisplyIntentBus.putExtra("transport_description", dataPicker.getDescription());
//                    startActivity(transportDataDisplyIntentBus);

                    //Comment for further use
                    TransportModel dataPicker = (TransportModel) adapterView.getItemAtPosition(position);
                    Intent transportDataDisplyIntentBus = new Intent(getActivity(), TransportDataDisplay.class);

                    transportDataDisplyIntentBus.putExtra("transport_name", dataPicker.getName());
                    transportDataDisplyIntentBus.putExtra("transport_phone", dataPicker.getPhone());
                    transportDataDisplyIntentBus.putExtra("transport_web", dataPicker.getWeb());
                    transportDataDisplyIntentBus.putExtra("transport_image", dataPicker.getImage());
                    transportDataDisplyIntentBus.putExtra("transport_description", dataPicker.getDescription());
                    startActivity(transportDataDisplyIntentBus);

                }
            });
        }

    }

}
