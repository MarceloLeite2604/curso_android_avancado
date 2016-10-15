package br.com.targettrust.aula5exercicio2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.targettrust.aula5exercicio2.model.Movie;


public class MainActivity extends ActionBarActivity implements DownloadResultReceiver.Receiver, LoaderManager.LoaderCallbacks<Cursor> {

    public static final String LOG_TAG = "LOG_TAG";

    private ListView listView = null;

    private MovieAdapter movieAdapter = null;

    private DownloadResultReceiver downloadResultReceiver;

    final String url = "http://api.themoviedb.org/3/movie/top_rated?api_key=246bf886104d519a1d2bf62aef1054ff&page=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Allow activity to show indeterminate progressbar.
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);


        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Initialize listView.
        listView = (ListView) findViewById(R.id.listView);

        /** Creating a loader for populating listview from sqlite database */
        /** This statement, invokes the method onCreatedLoader() */

        // Starting download service.
        downloadResultReceiver = new DownloadResultReceiver(new Handler());
        downloadResultReceiver.setReceiver(this);
        Intent intent = new Intent(Intent.ACTION_SYNC, null, this, DownloadService.class);

        // Send optional extras to Download IntentService.
        intent.putExtra("url", url);
        intent.putExtra("resultReceiver", downloadResultReceiver);
        intent.putExtra("requestId", 101);
        startService(intent);

        // Fetch all movies from database.
        Uri movies = Uri.parse("content://br.com.targettrust.aula5exercicio2/movies");
        Cursor movieCursor = getContentResolver().query(movies, null, null, null, "title");


        movieAdapter = new MovieAdapter(this, movieCursor, 0);
        listView.setAdapter(movieAdapter);
        //getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {

            case DownloadService.STATUS_RUNNING:

                setSupportProgressBarIndeterminateVisibility(true);
                break;
            case DownloadService.STATUS_FINISHED:
                /* Hide progress & extract result from bundle */
                setProgressBarIndeterminateVisibility(false);

                Log.d(MainActivity.LOG_TAG, "{onReceiveResult, 116} Download finished.");
                ArrayList<Movie> movieArrayList = (ArrayList<Movie>) resultData.getSerializable("results");

                if (null != movieArrayList && movieArrayList.size() > 0) {
                    Log.d(MainActivity.LOG_TAG, "{onReceiveResult, 123} Total movies: " + movieArrayList.size());

                    for (int counter = 0; counter < movieArrayList.size(); counter++) {
                        ContentValues values = new ContentValues();
                        Movie movie = movieArrayList.get(counter);

                        values.put(MovieProvider.TITLE, movie.getTitle());
                        values.put(MovieProvider.POSTER, movie.getPosterPath());

                        Uri uri = getContentResolver().insert(MovieProvider.CONTENT_URI, values);
                        Log.d(MainActivity.LOG_TAG, "{onReceiveResult, 133} Movie URI: " + uri);
                    }
                    Cursor moviesCursor = getContentResolver().query(MovieProvider.CONTENT_URI, null, null, null, MovieProvider.TITLE);
                    movieAdapter.swapCursor(moviesCursor);
                }


                break;
            case DownloadService.STATUS_ERROR:
                // Handle the error.
                String error = resultData.getString(Intent.EXTRA_TEXT);
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                break;
        }
    }

    /**
     * A callback method invoked by the loader when initLoader() is called
     */
    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        Log.d(MainActivity.LOG_TAG, "{onCreateLoader, 152} ");
        Uri uri = MovieProvider.CONTENT_URI;
        return new CursorLoader(this, uri, null, null, null, null);
    }

    /**
     * A callback method, invoked after the requested content provider returned all the data
     */
    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
        Log.d(MainActivity.LOG_TAG, "{onLoadFinished, 160} ");
        movieAdapter.swapCursor(arg1);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        movieAdapter.swapCursor(null);
    }
}
