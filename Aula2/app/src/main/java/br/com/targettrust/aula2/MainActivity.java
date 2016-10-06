package br.com.targettrust.aula2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = "LOG_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Ion.with(getBaseContext())
                .load("https://randomuser.me/api/")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Log.d(LOG_TAG, "MainActivity:onCompleted:27 Download completed.");
                        if (e != null) {
                            Log.d(LOG_TAG, "MainActivity:onCompleted:28  Exception: " + e.getMessage());
                            return;
                        }

                        JsonArray jsonArrayResults = result.get("results").getAsJsonArray();

                        JsonObject jsonObjectResult = jsonArrayResults.get(0).getAsJsonObject();

                        JsonObject jsonObjectName = jsonObjectResult.get("name").getAsJsonObject();
                        String firstName = jsonObjectName.get("first").getAsString();
                        String lastName = jsonObjectName.get("last").getAsString();
                        Log.d(LOG_TAG, "MainActivity:onCompleted:42 Name: " + firstName + " " + lastName);

                        String email = jsonObjectResult.get("email").getAsString();
                        Log.d(LOG_TAG, "MainActivity:onCompleted:45 Email: " + email);

                        JsonObject jsonObjectPicture = jsonObjectResult.get("picture").getAsJsonObject();
                        String largePictureAddress = jsonObjectPicture.get("large").getAsString();
                        Log.d(LOG_TAG, "MainActivity:onCompleted:49 Picture address: " + largePictureAddress);

                        final TextView textViewName = (TextView) findViewById(R.id.textview_name);
                        final TextView textViewEmail = (TextView) findViewById(R.id.textview_email);
                        final ImageView imageViewPicture = (ImageView) findViewById(R.id.imageview_picture);

                        textViewName.setText(firstName + " " + lastName);
                        textViewEmail.setText(email);
                        Ion.with(getBaseContext()).load(largePictureAddress).intoImageView(imageViewPicture);

                    }
                });
    }
}