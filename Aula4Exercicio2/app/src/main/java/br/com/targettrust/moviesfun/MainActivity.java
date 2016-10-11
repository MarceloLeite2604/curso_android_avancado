package br.com.targettrust.moviesfun;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import br.com.targettrust.moviesfun.Objeto.Movie;

public class MainActivity extends AppCompatActivity {

    List<Movie> topFilmes = new ArrayList<Movie>();
    ListView listViewTop;
    MovieAdapter movieAdapter;
    private DisplayMetrics metrics;
    private int mode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        listar();

        String server = getResources().getString(R.string.api_server);
        String token = getResources().getString(R.string.token);
        String api_top  = getResources().getString(R.string.api_top);
        final LinearLayout main = (LinearLayout)findViewById(R.id.main);
        Ion.with(getBaseContext())
                .load(server + api_top + token)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject colecao) {
                        JsonArray filmes = colecao.getAsJsonArray("results");
                        Log.v("API TOP","Numero de filmes " + filmes.size());
                        int total = filmes.size();
                        for(int i = 0; i < total; i++){
                            JsonObject filme = filmes.get(i).getAsJsonObject();
                            Log.v("API Top","Filme" + filme.get("title").getAsString());
                            String title = filme.get("title").getAsString();
                            String idIMDB = filme.get("id").getAsString();
                            String urlPoster = "https://image.tmdb.org/t/p/w370/"+filme.get("poster_path").getAsString();
                            Movie objFilme = new Movie(title,idIMDB,urlPoster);
                            objFilme.save();
                            topFilmes.add(objFilme);
                        }
                        movieAdapter.notifyDataSetChanged();
                    }
                });

    }

    public void listar(){
        listViewTop = (ListView)findViewById(R.id.lista);
        movieAdapter = new MovieAdapter(getApplication(),topFilmes,metrics,mode);
        listViewTop.setAdapter(movieAdapter);
        listViewTop.setFadingEdgeLength(0);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, 1, 0, "TranslateAnimation1");
        menu.add(Menu.NONE, 2, 0, "TranslateAnimation2");
        menu.add(Menu.NONE, 3, 0, "ScaleAnimation");
        menu.add(Menu.NONE, 4, 0, "fade_in");
        menu.add(Menu.NONE, 5, 0, "hyper_space_in");
        menu.add(Menu.NONE, 6, 0, "hyper_space_out");
        menu.add(Menu.NONE, 7, 0, "wave_scale");
        menu.add(Menu.NONE, 8, 0, "push_left_in");
        menu.add(Menu.NONE, 9, 0, "push_left_out");
        menu.add(Menu.NONE, 10, 0, "push_up_in");
        menu.add(Menu.NONE, 11, 0, "push_up_out");
        menu.add(Menu.NONE, 12, 0, "shake");
        menu.add(Menu.NONE, 13, 0, "rotate3D");
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mode = item.getItemId();
        listar();
        return super.onOptionsItemSelected(item);
    }
}
