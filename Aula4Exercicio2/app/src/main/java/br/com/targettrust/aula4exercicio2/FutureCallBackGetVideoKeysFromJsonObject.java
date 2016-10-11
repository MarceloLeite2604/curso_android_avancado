package br.com.targettrust.aula4exercicio2;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;

import br.com.targettrust.aula4exercicio2.objects.Movie;

/**
 * Created by marcelo on 11/10/16.
 */

public class FutureCallBackGetVideoKeysFromJsonObject implements FutureCallback<JsonObject> {

    private Context context;
    private OnTaskCompletedInterface onTaskCompletedInterface;

    public FutureCallBackGetVideoKeysFromJsonObject(Context context, OnTaskCompletedInterface onTaskCompletedInterface) {
        this.context = context;
        this.onTaskCompletedInterface = onTaskCompletedInterface;
    }

    @Override
    public void onCompleted(Exception e, JsonObject result) {
        Log.d(MainActivity.LOG_TAG, "{onCompleted, 30} result: " + result);
        JsonArray jsonArrayMovies = result.getAsJsonArray("results");
        Log.d(MainActivity.LOG_TAG, "{onCompleted, 67} Total videos returned: " + jsonArrayMovies.size());

        ArrayList<String> videoKeyArrayList = new ArrayList<>();
        String videoKey;
        JsonObject jsonObjectVideo;
        for (int i = 0; i < jsonArrayMovies.size(); i++) {
            jsonObjectVideo = jsonArrayMovies.get(i).getAsJsonObject();
            videoKey = jsonObjectVideo.get("key").getAsString();
            videoKeyArrayList.add(videoKey);
        }
        onTaskCompletedInterface.getVideoKeysFromJsonObjectCompleted(videoKeyArrayList);
    }

    public interface OnTaskCompletedInterface {
        void getVideoKeysFromJsonObjectCompleted(ArrayList<String> videoKeyArrayList);
    }
}
