package br.com.targettrust.aula5;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import br.com.targettrust.aula5.objects.Movie;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MovieVideosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovieVideosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieVideosFragment extends Fragment implements FutureCallBackGetVideoKeysFromJsonObject.OnTaskCompletedInterface {
    private static final String ARG_PARAM1 = "movie";
    private static final String ARG_PARAM2 = "displayMetrics";

    private Movie movie;
    private View fragmentView;
    private OnFragmentInteractionListener onFragmentInteractionListener;
    private Integer[] screenSize;

    public MovieVideosFragment() {
    }

    public static MovieVideosFragment newInstance(Movie movie, Integer[] screenSize) {
        MovieVideosFragment fragment = new MovieVideosFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, movie);
        args.putSerializable(ARG_PARAM2, screenSize);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movie = (Movie) getArguments().getSerializable(ARG_PARAM1);
            screenSize = (Integer[]) getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(MainActivity.LOG_TAG, "{onCreateView, 56} ");
        fragmentView = inflater.inflate(R.layout.fragment_movie_videos, container, false);
        getMovieVideosAddresses();

        return fragmentView;
    }

    private void getMovieVideosAddresses() {

        // Elaborate address to get movie videos.
        String apiServer = getString(R.string.imdb_api_server);
        String apiMovieVideos = getString(R.string.imdb_api_movie_videos).replace("#movieId#", movie.getImdb());
        String serviceAddress = apiServer + apiMovieVideos;

        // Add the key parameter.
        String keyParameterName = getString(R.string.key_parameter_name);
        String key = getString(R.string.imdb_api_key);
        serviceAddress += keyParameterName + "=" + key;
        Log.d(MainActivity.LOG_TAG, "{getMovieVideosAddresses, 70} Video keys api address: " + serviceAddress);

        FutureCallBackGetVideoKeysFromJsonObject futureCallBackGetVideoAddressesFromJsonObject = new FutureCallBackGetVideoKeysFromJsonObject(getContext(), this);

        Ion.with(getContext()).load(serviceAddress).asJsonObject()
                .setCallback(futureCallBackGetVideoAddressesFromJsonObject);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            onFragmentInteractionListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onFragmentInteractionListener = null;
    }

    @Override
    public void getVideoKeysFromJsonObjectCompleted(ArrayList<String> videoKeyArrayList) {
        Log.d(MainActivity.LOG_TAG, "{getVideoKeysFromJsonObjectCompleted, 102} Creating web view.");
        WebView webViewVideo = (WebView) fragmentView.findViewById(R.id.webview_video);

        String videoSource = null;
        if (videoKeyArrayList.size() > 0) {
            videoSource = getString(R.string.youtube_video_source).replace("#videoKey#", videoKeyArrayList.get(0));
            Log.d(MainActivity.LOG_TAG, "{getVideoKeysFromJsonObjectCompleted, 118} Video source: " + videoSource);
        }

        Log.d(MainActivity.LOG_TAG, "{getVideoKeysFromJsonObjectCompleted, 107} Video source: " + videoSource);


        //
        if (videoSource != null) {
            webViewVideo.getSettings().setJavaScriptEnabled(true);
            webViewVideo.loadUrl(videoSource);
        } else {
            String customHtml = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    " <meta charset=\"utf-8\">\n" +
                    " <meta name=\"viewport\" content=\"width=device-width\">\n" +
                    " <title>Trailer</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<p>Sorry, but no video is available for this movie!</p>\n" +
                    "</body>\n" +
                    "</html>";
            webViewVideo.getSettings().setJavaScriptEnabled(false);
            webViewVideo.loadData(customHtml, "text/html", "UTF-8");
        }

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onMovieDetailFragmentInteraction(Uri uri);
    }
}
