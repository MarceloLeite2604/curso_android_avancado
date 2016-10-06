package br.com.targettrust.aula2;

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

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        SugarContext.init(getBaseContext());

        // Sugar ORM Issue #613 Workaround.
        // https://github.com/satyan/sugar/issues/613
        SugarRecord.executeQuery("CREATE TABLE IF NOT EXISTS USER (ID INTEGER PRIMARY KEY AUTOINCREMENT, COMPLETE_NAME TEXT, LARGE_PICTURE_ADDRESS TEXT, EMAIL_ADDRESS TEXT)");

        Ion.with(getBaseContext())
                .load("http://api.randomuser.me/?results=1000")
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
        JsonArray jsonArrayResults = jsonObjectUsers.get("results").getAsJsonArray();
        Log.d(MainActivity.LOG_TAG, "MainActivity:onCompleted:44 Result size: " + jsonArrayResults.size());

        ArrayList<User> userArrayList = new ArrayList<>();

        User user;
        JsonObject jsonObjectUser;
        for (int counter = 0; counter < jsonArrayResults.size(); counter++) {
            jsonObjectUser = jsonArrayResults.get(counter).getAsJsonObject();
            user = createUserFromJsonObject(jsonObjectUser);
            userArrayList.add(user);
        }

        final ListView listViewIntegers = (ListView) findViewById(R.id.listview_users);
        listViewIntegers.setAdapter(new UserAdapter(userArrayList, ListActivity.this));

    }

    private User createUserFromJsonObject(JsonObject jsonObject) {
        User user;
        JsonObject jsonObjectName = jsonObject.get("name").getAsJsonObject();
        String firstName = jsonObjectName.get("first").getAsString();
        String lastName = jsonObjectName.get("last").getAsString();
        String completeName = firstName + " " + lastName;


        List<User> userList = (List<User>) User.find(User.class, "COMPLETE_NAME = ?", completeName);
        if (userList.size() > 0 ) {
            user = userList.get(0);
            Log.d(MainActivity.LOG_TAG, "ListActivity:createUserFromJsonObject:74 User \"" + user.getCompleteName() + "\" found on cache.");
        } else {
            // Log.d(MainActivity.LOG_TAG, "ListActivity:createUserFromJsonObject:74 Obtaining user \"" + completeName + "\" from JsonObject.");
            String email = jsonObject.get("email").getAsString();

            JsonObject jsonObjectPicture = jsonObject.get("picture").getAsJsonObject();
            String largePictureAddress = jsonObjectPicture.get("large").getAsString();

            user = new User(completeName, largePictureAddress, email);
            user.save();
        }

        return user;
    }

}
