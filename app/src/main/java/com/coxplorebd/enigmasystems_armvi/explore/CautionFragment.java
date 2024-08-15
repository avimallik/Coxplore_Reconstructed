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

public class CautionFragment extends Fragment {

    TextView cautionTxt;

    public static CautionFragment newInstance() {
        CautionFragment fragment = new CautionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new CautionNetworkGenerator().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootViewCaution = inflater.inflate(R.layout.fragment_item_caution,
                container, false);
        cautionTxt = (TextView) rootViewCaution.findViewById(R.id.cautionTxt);
        return rootViewCaution;
    }

    public class CautionNetworkGenerator extends AsyncTask<Void, Void, String> {
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
                HttpGet httpPost = new HttpGet("http://www.coxplorebd.com/coxplore_api/caution_api.php");
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
                String responseCaution = object.getString("caution_details");
                cautionTxt.setText(Html.fromHtml(responseCaution));

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
