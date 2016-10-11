package br.com.targettrust.aula4exercicio2;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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

import br.com.targettrust.aula4exercicio2.objects.Movie;

/**
 * Created by instrutor on 05/09/2015.
 */
public class MovieAdapter extends BaseAdapter {

    private List<Movie> movieList;
    private MainActivity activity;
    private DisplayMetrics displayMetrics;
    private AnimationMode animationMode;

    public MovieAdapter(MainActivity activity, List<Movie> movieList, DisplayMetrics displayMetrics, AnimationMode animationMode) {
        super();
        this.movieList = movieList;
        this.animationMode = animationMode;
        this.activity = activity;
        this.displayMetrics = displayMetrics;
    }

    public void setAnimationMode(AnimationMode animationMode) {
        this.animationMode = animationMode;
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Movie getItem(int position) {
        return movieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder;

        // Reuse views
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.row_movie, null);

            // Configure view holder
            TextView textViewTitle = (TextView) rowView.findViewById(R.id.textview_title);
            ImageView imageViewPoster = (ImageView) rowView.findViewById(R.id.imageview_poster);
            viewHolder = new ViewHolder(textViewTitle, imageViewPoster);

            rowView.setBackgroundResource(R.drawable.background);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Movie movie = getItem(position);

        viewHolder.getTextViewTitle().setText(movie.getTitle());

        Ion.with(viewHolder.getImageViewPoster())
                .load(movie.getPoster());

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.showVideoFrame(movie);
            }
        });

        Animation animation = createAnimation(rowView);

        rowView.startAnimation(animation);

        return rowView;
    }

    private Animation createAnimation(View rowView) {
        Animation animation = null;
        switch (animationMode) {
            case X_TRANSLATION: {
                float fromXDelta = displayMetrics.widthPixels / 2;
                animation = new TranslateAnimation(fromXDelta, 0, 0, 0);
                break;
            }

            case Y_TRANSLATION: {
                float fromYDelta = displayMetrics.heightPixels;
                animation = new TranslateAnimation(0, 0, fromYDelta, 0);
                break;
            }

            case SCALE: {
                animation = new ScaleAnimation(1.0f, 1.0f, 0f, 1.0f);
                break;
            }

            case FADE_IN: {
                animation = AnimationUtils.loadAnimation(activity, R.anim.fade_in);
                break;
            }
            case HYPERSPACE_IN: {
                animation = AnimationUtils.loadAnimation(activity, R.anim.hyperspace_in);
                break;
            }
            case HYPERSPACE_OUT: {
                animation = AnimationUtils.loadAnimation(activity, R.anim.hyperspace_out);
                break;
            }
            case WAVE_SCALE: {
                animation = AnimationUtils.loadAnimation(activity, R.anim.wave_scale);
                break;
            }
            case PUSH_LEFT_IN: {
                animation = AnimationUtils.loadAnimation(activity, R.anim.push_left_in);
                break;
            }
            case PUSH_LEFT_OUT: {
                animation = AnimationUtils.loadAnimation(activity, R.anim.push_left_out);
                break;
            }
            case PUSH_UP_IN: {
                animation = AnimationUtils.loadAnimation(activity, R.anim.push_up_in);
                break;
            }
            case PUSH_UP_OUT: {
                animation = AnimationUtils.loadAnimation(activity, R.anim.push_up_out);
                break;
            }
            case SHAKE: {
                animation = AnimationUtils.loadAnimation(activity, R.anim.shake);
                break;
            }
            case ROTATE: {
                animation = new Rotate3dAnimation(90.0f, 0.0f, 100.0f, false, rowView);
                break;
            }
        }
        animation.setDuration(500l);
        return animation;
    }

    static class ViewHolder {
        private TextView textViewTitle;
        private ImageView ImageViewPoster;

        public ViewHolder(TextView textViewTitle, ImageView imageViewPoster) {
            this.textViewTitle = textViewTitle;
            ImageViewPoster = imageViewPoster;
        }

        public TextView getTextViewTitle() {
            return textViewTitle;
        }

        public ImageView getImageViewPoster() {
            return ImageViewPoster;
        }
    }

}
