package br.com.targettrust.aula4exercicio2;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import br.com.targettrust.aula4exercicio2.objects.Movie;

public class MainActivity extends AppCompatActivity implements FutureCallBackGetMoviesFromJsonObject.OnTaskCompletedInterface, MovieVideosFragment.OnFragmentInteractionListener {

    public static final String LOG_TAG = "LOG_TAG";

    private static final AnimationMode DEFAULT_ANIMATION_MODE = AnimationMode.X_TRANSLATION;

    private ListView listViewMovies;
    private MovieAdapter movieAdapter;
    private DisplayMetrics displayMetrics;
    private AnimationMode animationMode = DEFAULT_ANIMATION_MODE;
    private FrameLayout frameLayoutVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewMovies = (ListView) findViewById(R.id.listview_movies);
        frameLayoutVideo = (FrameLayout) findViewById(R.id.framelayout_video);
        showFrameVideo(false);

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        getTopMovies();
    }

    private void getTopMovies() {
        // Elaborate service address.
        String imdbApiServer = getString(R.string.imdb_api_server);
        String apiTopRatedMovied = getString(R.string.imdb_api_top_rated_movies);
        String serviceAddress = imdbApiServer + apiTopRatedMovied;

        // Add the key parameter.
        String keyParameterName = getString(R.string.key_parameter_name);
        String key = getString(R.string.imdb_api_key);
        serviceAddress += keyParameterName + "=" + key;

        FutureCallBackGetMoviesFromJsonObject futureCallBackGetMoviesFromJsonObject = new FutureCallBackGetMoviesFromJsonObject(this, this);

        Ion.with(getBaseContext())
                .load(serviceAddress)
                .asJsonObject()
                .setCallback(futureCallBackGetMoviesFromJsonObject);
    }

    private void createMovieList(ArrayList<Movie> movieArrayList) {
        movieAdapter = new MovieAdapter(this, movieArrayList, displayMetrics, animationMode);
        listViewMovies.setAdapter(movieAdapter);
        listViewMovies.setFadingEdgeLength(0);
    }

    @Override
    public void getMoviesFromJsonObjectCompleted(ArrayList<Movie> movieArrayList) {
        Log.d(MainActivity.LOG_TAG, "{getMoviesFromJsonObjectCompleted, 86} ");
        createMovieList(movieArrayList);
    }

    public void showVideoFrame(Movie movie) {
        Log.d(MainActivity.LOG_TAG, "{showVideoFrame, 106} Movie: " + movie.getTitle());
        FragmentTransaction fragmentTransaction = createNewFragmentTransaction();
        Integer[] screenSize = {displayMetrics.heightPixels, displayMetrics.widthPixels};

        Fragment movieVideosFragment = MovieVideosFragment.newInstance(movie, screenSize);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.framelayout_video, movieVideosFragment, null).commit();
        showFrameVideo(true);
    }


    @Override
    public void onMovieDetailFragmentInteraction(Uri uri) {
        Log.d(MainActivity.LOG_TAG, "{onMovieDetailFragmentInteraction, 107} ");
    }

    @Override
    public void onBackPressed() {
        if (frameLayoutVideo.getVisibility() == View.VISIBLE) {
            showFrameVideo(false);
        } else {
            super.onBackPressed();
        }
    }

    private FragmentTransaction createNewFragmentTransaction() {
        return getSupportFragmentManager().beginTransaction();
    }

    private void showFrameVideo(boolean show) {
        if (show) {
            listViewMovies.setVisibility(View.GONE);
            frameLayoutVideo.setVisibility(View.VISIBLE);
        } else {
            listViewMovies.setVisibility(View.VISIBLE);
            frameLayoutVideo.setVisibility(View.GONE);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);

        for (AnimationMode animationMode : AnimationMode.values()) {
            menu.add(Menu.NONE, animationMode.getId(), 0, animationMode.getDescription());
        }
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        animationMode = AnimationMode.getAnimationModeFromId(item.getItemId());
        movieAdapter.setAnimationMode(animationMode);
        return super.onOptionsItemSelected(item);
    }


}
