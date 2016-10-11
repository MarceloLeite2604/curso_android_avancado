package br.com.targettrust.moviesfun.Objeto;

/**
 * Created by instrutor on 05/09/2015.
 */
import com.orm.SugarRecord;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Movie extends SugarRecord <Movie> {
    String id;
    public String title;
    String year;
    String rated;
    String released;
    String genre;
    String director;
    String writer;
    String actors;
    String plot;
    public String poster;
    String runtime;
    String rating;
    String votes;
    public String imdb;
    String tstamp;

    public Movie(){ }

    public Movie(String title, String imdb, String poster){
        this.title = title;
        this.imdb = imdb;
        this.poster = poster;
        this.tstamp = getDateTime();
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}