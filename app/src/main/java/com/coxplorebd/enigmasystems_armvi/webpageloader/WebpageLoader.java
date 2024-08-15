package com.coxplorebd.enigmasystems_armvi.webpageloader;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.coxplorebd.enigmasystems_armvi.databinding.ActivityWebpageLoaderBinding;

import com.coxplorebd.enigmasystems_armvi.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebpageLoader extends AppCompatActivity {

    ProgressBar progressBar;
    WebView webView;
    FloatingActionButton refreshBtn;
    public static final String URL_REGEX = "^^(http|https)://";
    public static final String googleClientString = "https://www.google.com/search?q=";
    public static final String protocol = "https://";
    String urlHolder = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webpage_loader);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        refreshBtn = (FloatingActionButton)findViewById(R.id.refreshBtn);

        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        WebSettings WebSetting = webView.getSettings();
        WebSetting.setJavaScriptEnabled(true);
        //Improve Webview Performance
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setCacheMode(WebSetting.LOAD_CACHE_ELSE_NETWORK);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewpageLoaderClient());
        WebSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        WebSetting.setDomStorageEnabled(true);
        WebSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        WebSetting.setUseWideViewPort(true);
        WebSetting.setSavePassword(true);
        WebSetting.setSaveFormData(true);
        WebSetting.setEnableSmoothTransition(true);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        if (getIntent().hasExtra("web_data_transport_bus")) {
            Pattern p = Pattern.compile(URL_REGEX);
            Matcher m = p.matcher(getIntent().getExtras().getString("web_data_transport_bus"));
//            urlHolder = protocol + getIntent().getExtras().getString("web_data_transport_bus");
            if(m.find()){
                webView.loadUrl(getIntent().getExtras().getString("web_data_transport_bus"));
            }else{
                webView.loadUrl(protocol+getIntent().getExtras().getString("web_data_transport_bus"));
            }

        }else if(getIntent().hasExtra("web_data_hotel")){
            Pattern p = Pattern.compile(URL_REGEX);
            Matcher m = p.matcher(getIntent().getExtras().getString("web_data_hotel"));
//            urlHolder = protocol + getIntent().getExtras().getString("web_data_transport_bus");
            if(m.find()){
                webView.loadUrl(getIntent().getExtras().getString("web_data_hotel"));
            }else{
                webView.loadUrl(protocol+getIntent().getExtras().getString("web_data_hotel"));
            }
        }else if(getIntent().hasExtra("weather_app_111")){
            webView.loadUrl(getIntent().getExtras().getString("weather_app_111"));
        }

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.reload();
                webView.clearCache(true);
            }
        });

    }

    private class WebViewpageLoaderClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
            webView.clearCache(true);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.INVISIBLE);
            webView.clearCache(true);
        }
    }


    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}