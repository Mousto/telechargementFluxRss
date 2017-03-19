package com.dia.mous.telechargementrss;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Mous on 08/03/2017.
 */

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        // On récupère l'intent qui a lancé cette activité
        Intent i = getIntent();
        // Puis on récupère l'url donné dans la RSSAdapter.
        String url = i.getStringExtra(RSSAdapter.monUrl);
        //Titre
       /* ActionBar ab = getSupportActionBar();
        ab.setTitle(htmlContent[0]);*/

        WebView wv = (WebView)findViewById(R.id.ma_webview);
        //activer la possibilité d'utiliser javascript dans la webview
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //permettre à la page web charger de s'ouvrir au sein de la webview et non dans le navigateur du téléphone
        wv.setWebViewClient(new WebViewClient() {
        /*Give the host application a chance to take over the control when a new url is about to be loaded in the current WebView.
        If WebViewClient is not provided, by default WebView will ask Activity Manager to choose the proper handler for the url.
        If WebViewClient is provided, return true means the host application handles the url, while return false means the current
        WebView handles the url.*/
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.toString());
            return false;
            }
        });
        wv.loadUrl(url);
    }

    }
