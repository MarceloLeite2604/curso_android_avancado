package br.com.targettrust.aula5;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;

import br.com.targettrust.aula5.objects.Movie;

/**
 * Created by marcelo on 11/10/16.
 */

public class FutureCallBackGetMoviesFromJsonObject implements FutureCallback<JsonObject> {

    private Context context;
    private OnTaskCompletedInterface onTaskCompletedInterface;

    public FutureCallBackGetMoviesFromJsonObject(Context context, OnTaskCompletedInterface onTaskCompletedInterface) {
        this.context = context;
        this.onTaskCompletedInterface = onTaskCompletedInterface;
    }

    @Override
    public void onCompleted(Exception e, JsonObject result) {
        JsonArray jsonArrayMovies = result.getAsJsonArray("results");
        Log.d(MainActivity.LOG_TAG, "{onCompleted, 67} Total movies returned: " + jsonArrayMovies.size());

        ArrayList<Movie> movieArrayList = new ArrayList<>();
        for (int i = 0; i < jsonArrayMovies.size(); i++) {

            JsonObject jsonObjectMovie = jsonArrayMovies.get(i).getAsJsonObject();
            Movie movie = createMovieFromJsonObject(jsonObjectMovie);
            movie.save();
            movieArrayList.add(movie);
        }
        onTaskCompletedInterface.getMoviesFromJsonObjectCompleted(movieArrayList);
    }

    private Movie createMovieFromJsonObject(JsonObject jsonObject) {
        String title = jsonObject.get("title").getAsString();
        Log.d(MainActivity.LOG_TAG, "{createMovieFromJsonObject, 42} Movie title: " + title);
        String id = jsonObject.get("id").getAsString();
        String posterPath = jsonObject.get("poster_path").getAsString();

        String urlPoster = context.getString(R.string.imdb_images_server) + posterPath;
        return new Movie(title, id, urlPoster);
    }

    public interface OnTaskCompletedInterface {
        void getMoviesFromJsonObjectCompleted(ArrayList<Movie> movieArrayList);
    }
}
