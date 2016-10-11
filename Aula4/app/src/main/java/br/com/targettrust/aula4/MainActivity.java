package br.com.targettrust.aula4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = (WebView)findViewById(R.id.webview_web);

        // Activate javascript on web browser.
        webView.getSettings().setJavaScriptEnabled(true);

        // The new WebInterface object will be available through "Android" on
        webView.addJavascriptInterface(new WebInterface(this), "Android");
        webView.loadUrl("file:///android_asset/www/index.html");
    }
}
