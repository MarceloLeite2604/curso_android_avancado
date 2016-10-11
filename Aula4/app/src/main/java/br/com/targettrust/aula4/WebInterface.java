package br.com.targettrust.aula4;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by sala01 on 10/10/2016.
 */

public class WebInterface {

    private Context context;

    public WebInterface(Context context) {
        this.context = context;
    }

    // After API 17, the annotation below is mandatory so the method can be used through JavaScript scripts.
    @JavascriptInterface
    public void showToast() {
        Toast.makeText(context, "Standard message.", Toast.LENGTH_LONG).show();
    }

    @JavascriptInterface
    public void showName(String name) {
        Toast.makeText(context, "Hello, " + name + "!", Toast.LENGTH_LONG).show();
    }

    @JavascriptInterface
    public void showSecondActivity(String name) {
        Intent showSecondActivityIntent = new Intent(context, SecondActivity.class);
        showSecondActivityIntent.putExtra(SecondActivity.EXTRA_NAME, name);
        context.startActivity(showSecondActivityIntent);
    }
}
