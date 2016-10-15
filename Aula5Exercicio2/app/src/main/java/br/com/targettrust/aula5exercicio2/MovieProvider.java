package br.com.targettrust.aula5exercicio2;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;

/**
 * The purpose of this classe is to provide all necessary functions to create SQLite entities to
 * store movie informations and its DML.
 */
public class MovieProvider extends ContentProvider {

    static final String PROVIDER_NAME = "br.com.targettrust.aula5exercicio2";
    static final String URL = "content://" + PROVIDER_NAME + "/movies";
    static final Uri CONTENT_URI = Uri.parse(URL);

    // fields for the database
    static final String ID = "_id";
    static final String TITLE = "title";
    static final String POSTER = "poster";

    // integer values used in content URI
    static final int MOVIE = 1;
    static final int MOVIE_ID = 2;

    DBHelper dbHelper;

    // projection map for a query
    private static HashMap<String, String> moviesHashMap;

    // maps content URI "patterns" to the integer values that were set above
    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "movies", MOVIE);
        uriMatcher.addURI(PROVIDER_NAME, "movies/#", MOVIE_ID);
    }

    // database declarations
    private SQLiteDatabase database;
    static final String DATABASE_NAME = "aula5exercicio2";
    static final String TABLE_NAME = "movies";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_TABLE =
            " CREATE TABLE " + TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " title TEXT NOT NULL, " +
                    " poster TEXT NOT NULL);";


    // class that creates and manages the provider's database
    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d(MainActivity.LOG_TAG, "{onUpgrade, 73} Upgrading database from version \"" + oldVersion + "\" to \"" + newVersion + "\". Old data will be destroyed");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

    }

    @Override
    public boolean onCreate() {
        // TODO Auto-generated method stub
        Context context = getContext();
        dbHelper = new DBHelper(context);
        // permissions to be writable
        database = dbHelper.getWritableDatabase();

        if (database == null)
            return false;
        else
            return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        queryBuilder.setTables(TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            // maps all database column names
            case MOVIE:
                queryBuilder.setProjectionMap(moviesHashMap);
                break;
            case MOVIE_ID:
                queryBuilder.appendWhere(ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        if (sortOrder == null || sortOrder == "") {
            sortOrder = TITLE;
        }
        Cursor cursor = queryBuilder.query(database, projection, selection,
                selectionArgs, null, null, sortOrder);
        /**
         * register to watch a content URI for changes
         */
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(MainActivity.LOG_TAG, "{insert, 133} ");
        long row = database.insert(TABLE_NAME, "", values);

        // If record is added successfully
        if (row > 0) {
            Uri newUri = ContentUris.withAppendedId(CONTENT_URI, row);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        throw new SQLException("Fail to add a new record into " + uri);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int count;
        switch (uriMatcher.match(uri)) {
            case MOVIE:
                count = database.update(TABLE_NAME, values, selection, selectionArgs);
                break;
            case MOVIE_ID:
                count = database.update(TABLE_NAME, values, ID +
                        " = " + uri.getLastPathSegment() +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int count;
        switch (uriMatcher.match(uri)) {
            case MOVIE:
                // delete all the records of the table
                count = database.delete(TABLE_NAME, selection, selectionArgs);
                break;
            case MOVIE_ID:
                String id = uri.getLastPathSegment();    //gets the id
                count = database.delete(TABLE_NAME, ID + " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;


    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            // Get all movies
            case MOVIE:
                // Obs: "vnd.android.cursor.dir" -> expected to receive 0..N items.
                return "vnd.android.cursor.dir/vnd.aula5exercicio2.movies";
            // Get a particular movie
            case MOVIE_ID:
                // Obs: "vnd.android.cursor.item" -> expected to receive one and only one item.
                return "vnd.android.cursor.item/vnd.aula5exercicio2.movies";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
}
