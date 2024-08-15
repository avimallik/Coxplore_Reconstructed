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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Arm_AVI on 1/20/2019.
 */

public class LetsGoFragment extends Fragment {

    TextView letsGoTxt ;
    SliderView imageSlider;
    ArrayList<Slider> sliderList;

    public static LetsGoFragment newInstance() {
        LetsGoFragment fragment = new LetsGoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LetsGoNetworkGenerator().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_letsgo,
                container, false);

        letsGoTxt = (TextView) rootView.findViewById(R.id.letsGoTxt);
        imageSlider = (SliderView) rootView.findViewById(R.id.image_slider);

        return rootView;
    }

    //Letsgo Network Generator
    public class LetsGoNetworkGenerator extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            String out = null;
            JSONObject jObject;
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();

                final HttpParams httpParameters = httpClient.getParams();

                HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
                HttpConnectionParams.setSoTimeout(httpParameters, 15000);
                HttpGet httpPost = new HttpGet("http://www.coxplorebd.com/coxplore_api/explore_api.php");
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                out = EntityUtils.toString(httpEntity, org.apache.http.protocol.HTTP.UTF_8);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return out;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try{
                JSONObject object = new JSONObject(result);

                String responseLetsGo = object.getString("explore_details");
                String imageUrlOne = object.getString("image_one_path");
                String imageUrlTwo = object.getString("image_two_path");
                String imageUrlThree = object.getString("image_three_path");

                sliderList = new ArrayList<>();
                sliderList.add(new Slider(imageUrlOne));
                sliderList.add(new Slider(imageUrlTwo));
                sliderList.add(new Slider(imageUrlThree));
                SliderAdapter adapter = new SliderAdapter(getActivity(), sliderList);

                imageSlider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
                imageSlider.setSliderAdapter(adapter);
                imageSlider.setScrollTimeInSec(3);
                imageSlider.setAutoCycle(true);
                imageSlider.startAutoCycle();

                letsGoTxt.setText(Html.fromHtml(responseLetsGo));

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
