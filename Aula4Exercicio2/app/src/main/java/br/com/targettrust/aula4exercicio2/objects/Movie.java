package br.com.targettrust.aula4exercicio2.objects;

/**
 * Created by instrutor on 05/09/2015.
 */

import com.orm.SugarRecord;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Movie extends SugarRecord<Movie> implements Serializable {
    private String id;
    private String title;
    //    private String year;
//    private String rated;
//    private String released;
//    private String genre;
//    private String director;
//    private String writer;
//    private String actors;
//    private String plot;
    private String poster;
    //    private String runtime;
//    private String rating;
//    private String votes;
    private String imdb;
    private String tstamp;

    public Movie() {
    }

    public Movie(String title, String imdb, String poster) {
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

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getImdb() {
        return imdb;
    }
}