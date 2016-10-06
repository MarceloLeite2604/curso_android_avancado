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

import java.util.ArrayList;

import br.com.targettrust.aula2.adapters.UserAdapter;
import br.com.targettrust.aula2.model.User;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        SugarContext.init(getBaseContext());

        Ion.with(getBaseContext())
                .load("http://api.randomuser.me/?results=100")
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


        User cachedUser = (User) User.find(User.class, "completeName = ?", completeName);

        if (cachedUser != null) {
            Log.d(MainActivity.LOG_TAG, "ListActivity:createUserFromJsonObject:74 User \"" + cachedUser.getCompleteName() + "\" found on cache.");
            user = cachedUser;
        } else {
            Log.d(MainActivity.LOG_TAG, "ListActivity:createUserFromJsonObject:74 Obtaining user \"" + cachedUser.getCompleteName() + "\" from JsonObject.");
            String email = jsonObject.get("email").getAsString();

            JsonObject jsonObjectPicture = jsonObject.get("picture").getAsJsonObject();
            String largePictureAddress = jsonObjectPicture.get("large").getAsString();

            user = new User(completeName, largePictureAddress, email);
            user.save();
        }

        return user;
    }

}
