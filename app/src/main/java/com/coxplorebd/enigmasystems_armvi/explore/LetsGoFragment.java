package com.coxplorebd.enigmasystems_armvi.explore;

import android.os.AsyncTask;
import android.os.Bundle;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.coxplorebd.enigmasystems_armvi.R;
import com.coxplorebd.enigmasystems_armvi.adapter.SliderAdapter;
import com.coxplorebd.enigmasystems_armvi.model.Slider;

import com.smarteist.autoimageslider.SliderView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Arm_AVI on 1/20/2019.
 */

public class LetsGoFragment extends Fragment {

    TextView letsGoTxt;
    SliderView imageSlider;
    ArrayList<Slider> sliderList;

    public static LetsGoFragment newInstance() {
        return new LetsGoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LetsGoNetworkGenerator().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_letsgo, container, false);

        letsGoTxt = rootView.findViewById(R.id.letsGoTxt);
        imageSlider = rootView.findViewById(R.id.image_slider);

        return rootView;
    }

    public class LetsGoNetworkGenerator extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL("http://www.coxplorebd.com/coxplore_api/explore_api.php");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(15000);
                connection.setReadTimeout(15000);
                connection.connect();

                int responseCode = connection.getResponseCode();
                InputStream inputStream;

                if (responseCode >= 200 && responseCode < 300) {
                    inputStream = connection.getInputStream();
                } else {
                    inputStream = connection.getErrorStream();
                }

                if (inputStream == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuilder output = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    output.append(line);
                }

                return output.toString();

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (!isAdded() || getActivity() == null || result == null || result.isEmpty()) {
                return;
            }

            try {
                JSONObject object = new JSONObject(result);

                String responseLetsGo = object.optString("explore_details");
                String imageUrlOne = object.optString("image_one_path");
                String imageUrlTwo = object.optString("image_two_path");
                String imageUrlThree = object.optString("image_three_path");

                sliderList = new ArrayList<>();
                if (!imageUrlOne.isEmpty()) sliderList.add(new Slider(imageUrlOne));
                if (!imageUrlTwo.isEmpty()) sliderList.add(new Slider(imageUrlTwo));
                if (!imageUrlThree.isEmpty()) sliderList.add(new Slider(imageUrlThree));

                SliderAdapter adapter = new SliderAdapter(getActivity(), sliderList);

                imageSlider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
                imageSlider.setSliderAdapter(adapter);
                imageSlider.setScrollTimeInSec(3);
                imageSlider.setAutoCycle(true);
                imageSlider.startAutoCycle();

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    letsGoTxt.setText(Html.fromHtml(responseLetsGo, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    letsGoTxt.setText(Html.fromHtml(responseLetsGo));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}