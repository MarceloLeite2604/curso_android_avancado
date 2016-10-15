package br.com.targettrust.aula5exercicio2;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieAdapter extends CursorAdapter {

    private LayoutInflater inflater;

    public MovieAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.titleTextView.setText(cursor.getString(viewHolder.titleIndex));

        Picasso.with(context).load(cursor.getString(viewHolder.posterIndex)).into(viewHolder.imageViewPoster);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = inflater.inflate(R.layout.row_movie, null);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.titleTextView = (TextView) view.findViewById(R.id.title);
        viewHolder.imageViewPoster = (ImageView) view.findViewById(R.id.poster);
        viewHolder.titleIndex = cursor.getColumnIndexOrThrow(MovieProvider.TITLE);
        viewHolder.posterIndex = cursor.getColumnIndexOrThrow(MovieProvider.POSTER);
        view.setTag(viewHolder);
        return view;
    }

    private static class ViewHolder {
        int titleIndex;
        int posterIndex;
        TextView titleTextView;
        ImageView imageViewPoster;
    }
}