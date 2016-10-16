package br.com.targettrust.aula5exercicio3;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;

/**
 * Created by marcelo on 15/10/16.
 */

public class ContactsLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context context;
    private SimpleCursorAdapter simpleCursorAdapter;

    public ContactsLoader(Context context, SimpleCursorAdapter simpleCursorAdapter) {
        this.context = context;
        this.simpleCursorAdapter = simpleCursorAdapter;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Define the columns to retrieve
        String[] projectionFields = new String[]{ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.PHOTO_URI};

        // Construct the loader
        CursorLoader cursorLoader = new CursorLoader(context, ContactsContract.Contacts.CONTENT_URI,
                projectionFields, null, null, null);

        // Return the loader for use
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // The swapCursor() method assigns the new Cursor to the simpleCursorAdapter
        simpleCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Clear the Cursor we were using with another call to the swapCursor()
        simpleCursorAdapter.swapCursor(null);
    }
}