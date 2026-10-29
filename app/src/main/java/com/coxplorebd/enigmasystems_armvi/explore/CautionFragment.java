package com.coxplorebd.enigmasystems_armvi.explore;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.text.Html;
import android.util.Log;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class CautionFragment extends Fragment {

    TextView cautionTxt;

    public static CautionFragment newInstance() {
        return new CautionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootViewCaution = inflater.inflate(R.layout.fragment_item_caution, container, false);
        cautionTxt = rootViewCaution.findViewById(R.id.cautionTxt);

        new CautionNetworkGenerator().execute();

        return rootViewCaution;
    }

    public class CautionNetworkGenerator extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL("http://www.coxplorebd.com/coxplore_api/caution_api.php");
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
                Log.e("API_ERROR", "Network error", e);
                return null;
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (Exception e) {
                    Log.e("API_ERROR", "Reader close error", e);
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
                Log.e("API_ERROR", "Empty or null response");
                return;
            }

            try {
                JSONObject object = new JSONObject(result);
                String responseCaution = object.optString("caution_details", "");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    cautionTxt.setText(Html.fromHtml(responseCaution, Html.FROM_HTML_MODE_LEGACY));
                } else {
                    cautionTxt.setText(Html.fromHtml(responseCaution));
                }

                Log.d("API_RAW_RESPONSE", result);

            } catch (Exception e) {
                Log.e("API_ERROR", "Invalid JSON: " + result, e);
            }
        }
    }
}
