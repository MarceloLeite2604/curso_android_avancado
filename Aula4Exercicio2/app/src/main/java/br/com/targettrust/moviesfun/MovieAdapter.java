package br.com.targettrust.moviesfun;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.List;

import br.com.targettrust.moviesfun.Objeto.Movie;

/**
 * Created by instrutor on 05/09/2015.
 */
public class MovieAdapter extends BaseAdapter {
    List<Movie> movies;
    Context ctx;
    private DisplayMetrics metrics_;
    int mode = 1;

    private String TAG = MovieAdapter.class.getSimpleName();
    public MovieAdapter(Context ctx, List movies, DisplayMetrics metrics, int mode) {
        super();
        this.movies = movies;
        this.mode = mode;
        this.ctx = ctx;
        this.metrics_ = metrics;
        Log.v(TAG, "Construtor");
    }
    @Override
    public int getCount() {
        Log.v(TAG,"getCount : " +  movies.size() );
        return movies.size();
    }
    @Override
    public Movie getItem(int position) {
        Log.v(TAG,"getItem : " + position);
        return movies.get(position);
    }
    @Override
    public long getItemId(int position) {
        Log.v(TAG,"getItemId : " + position);
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.v(TAG,"getView : " + position);
        View rowView = convertView;
        ViewHolder viewHolder;
        // reusa views
        if (rowView == null) {
            Log.v(TAG,"getView ViewHolder: " + position);
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.linha_filme, null);

            // configura view holder
            viewHolder = new ViewHolder();
            viewHolder.nome = (TextView) rowView.findViewById(R.id.titulo);
            rowView.setBackgroundResource(R.drawable.background);
            viewHolder.foto = (ImageView) rowView.findViewById(R.id.poster);
            rowView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // acessar o dado da posição
        Movie movie = getItem(position);

        // fill data
        viewHolder.nome.setText( movie.title);

        Ion.with(viewHolder.foto)
                .load(movie.poster);

        Animation animation = null;

        switch (mode) {
            case 1:
                animation = new TranslateAnimation(metrics_.widthPixels / 2, 0,
                        0, 0);
                break;

            case 2:
                animation = new TranslateAnimation(0, 0, metrics_.heightPixels,
                        0);
                break;

            case 3:
                animation = new ScaleAnimation((float) 1.0, (float) 1.0,
                        (float) 0, (float) 1.0);
                break;

            case 4:
                animation = AnimationUtils.loadAnimation(ctx, R.anim.fade_in);
                break;
            case 5:
                animation = AnimationUtils.loadAnimation(ctx, R.anim.hyperspace_in);
                break;
            case 6:
                animation = AnimationUtils.loadAnimation(ctx, R.anim.hyperspace_out);
                break;
            case 7:
                animation = AnimationUtils.loadAnimation(ctx, R.anim.wave_scale);
                break;
            case 8:
                animation = AnimationUtils.loadAnimation(ctx, R.anim.push_left_in);
                break;
            case 9:
                animation = AnimationUtils.loadAnimation(ctx, R.anim.push_left_out);
                break;
            case 10:
                animation = AnimationUtils.loadAnimation(ctx, R.anim.push_up_in);
                break;
            case 11:
                animation = AnimationUtils.loadAnimation(ctx, R.anim.push_up_out);
                break;
            case 12:
                animation = AnimationUtils.loadAnimation(ctx, R.anim.shake);
                break;
            case 13:
                animation = new Rotate3dAnimation(90.0f, 0.0f, 100.0f, false, rowView);
                animation.setDuration(500l);
                break;
        }

			animation.setDuration(1000);
        rowView.startAnimation(animation);
        animation = null;

        // retorna o layout preenchido
        return rowView;
    }

    static class ViewHolder {
        TextView nome;
        ImageView foto;
        int position;
    }

}
