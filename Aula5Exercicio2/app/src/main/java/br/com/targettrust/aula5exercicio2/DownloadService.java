package br.com.targettrust.aula5exercicio2;

/**
 * Created by instrutor on 22/10/2015.
 */

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import br.com.targettrust.aula5exercicio2.model.Movie;

/**
 * A background download service for the application.
 */
public class DownloadService extends IntentService {

    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;

    private static final String LOG_TAG = "LOG_TAG";

    public DownloadService() {
        super(DownloadService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(LOG_TAG, "{onHandleIntent, 46} Service started");

        final ResultReceiver resultReceiver = intent.getParcelableExtra("resultReceiver");
        String url = intent.getStringExtra("url");
        Log.d(LOG_TAG, "{onHandleIntent, 50} Url: " + url);

        Bundle bundle = new Bundle();

        if (!TextUtils.isEmpty(url)) {
            // Update UI: Download Service is running.
            resultReceiver.send(STATUS_RUNNING, Bundle.EMPTY);

            try {
                ArrayList<Movie> movieArrayList = downloadData(url);

                // If the download returned content.
                if (null != movieArrayList && movieArrayList.size() > 0) {
                    Log.d(MainActivity.LOG_TAG, "{onHandleIntent, 64} Total movies: " + movieArrayList.size());
                    bundle.putSerializable("results", movieArrayList);
                    resultReceiver.send(STATUS_FINISHED, bundle);
                }
            } catch (Exception e) {

                // Sending error message back to activity.
                bundle.putString(Intent.EXTRA_TEXT, e.toString());
                resultReceiver.send(STATUS_ERROR, bundle);
            }
        }
        Log.d(MainActivity.LOG_TAG, "{onHandleIntent, 75} Service stopping.");
        this.stopSelf();
    }

    private ArrayList<Movie> downloadData(String requestUrl) throws IOException, DownloadException {
        HttpURLConnection urlConnection;

        // Forming the java.net.URL object.
        URL url = new URL(requestUrl);

        urlConnection = (HttpURLConnection) url.openConnection();

        // Optional request header properties.
        urlConnection.setRequestProperty("Content-Type", "application/json");
        urlConnection.setRequestProperty("Accept", "application/json");

        urlConnection.setRequestMethod("GET");

        int statusCode = urlConnection.getResponseCode();

        // 200 represents HTTP OK.
        InputStream inputStream;
        if (statusCode == 200) {
            inputStream = new BufferedInputStream(urlConnection.getInputStream());

            String response = convertInputStreamToString(inputStream);

            ArrayList<Movie> movieArrayList = parseResult(response);

            return movieArrayList;
        } else {
            throw new DownloadException("Failed to fetch data!!");
        }
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        String result = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        if (null != inputStream) {
            inputStream.close();
        }

        return result;
    }

    private ArrayList<Movie> parseResult(String result) {

        ArrayList<Movie> movieArrayList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(result);

            JSONArray jsonArray = jsonObject.optJSONArray("results");

            for (int counter = 0; counter < jsonArray.length(); counter++) {
                JSONObject post = jsonArray.optJSONObject(counter);

                Movie movie = new Movie(post.optString("id"),
                        post.optString("title"),
                        "https://image.tmdb.org/t/p/w370" + post.optString("poster_path"));
                movieArrayList.add(movie);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieArrayList;
    }

    public class DownloadException extends Exception {

        public DownloadException(String message) {
            super(message);
        }

        public DownloadException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}