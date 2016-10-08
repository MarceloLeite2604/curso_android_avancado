package br.com.targettrust.aula2;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.orm.SugarContext;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import br.com.targettrust.aula2.adapters.UserAdapter;
import br.com.targettrust.aula2.model.User;

public class ListActivity extends AppCompatActivity implements AsyncTaskProcessJsonArrayUsers.OnTaskCompletedInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        SugarContext.init(getBaseContext());

        // Sugar ORM Issue #613 Workaround.
        // https://github.com/satyan/sugar/issues/613
        SugarRecord.executeQuery("CREATE TABLE IF NOT EXISTS USER (ID INTEGER PRIMARY KEY AUTOINCREMENT, COMPLETE_NAME TEXT, LARGE_PICTURE_ADDRESS TEXT, EMAIL_ADDRESS TEXT)");

        Ion.with(getBaseContext())
                .load("http://api.randomuser.me/?results=500")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Log.d(MainActivity.LOG_TAG, "MainActivity:onCompleted:27 Download completed.");

                        if (e != null) {
                            Log.d(MainActivity.LOG_TAG, "MainActivity:onCompleted:28  Exception: " + e.getMessage());
                            return;
                        }

                        readJsonObjectUsers(result);
                    }
                });
    }


    private void readJsonObjectUsers(JsonObject jsonObjectUsers) {
        AsyncTaskProcessJsonArrayUsers asyncTaskProcessJsonArrayUsers = new AsyncTaskProcessJsonArrayUsers(this, this);
        JsonArray jsonArrayResults = jsonObjectUsers.get("results").getAsJsonArray();
        asyncTaskProcessJsonArrayUsers.execute(jsonArrayResults);
    }

    public void showUsers(ArrayList<User> userArrayList) {
        final ListView listViewIntegers = (ListView) findViewById(R.id.listview_users);
        listViewIntegers.setAdapter(new UserAdapter(userArrayList, ListActivity.this));
    }

    @Override
    public void processJsonArrayUsersTaskCompleted(ArrayList<User> userArrayList) {
        showUsers(userArrayList);
    }
}
