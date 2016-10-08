package br.com.targettrust.aula2;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import br.com.targettrust.aula2.model.User;

/**
 * Created by marcelo on 08/10/16.
 */

public class AsyncTaskProcessJsonArrayUsers extends AsyncTask<JsonArray, Integer, ArrayList<User>> {

    private AppCompatActivity activity;
    private OnTaskCompletedInterface onTaskCompletedInterface;

    private ProgressDialog progressDialog;

    public AsyncTaskProcessJsonArrayUsers(AppCompatActivity activity, OnTaskCompletedInterface onTaskCompletedInterface) {
        this.activity = activity;
        this.onTaskCompletedInterface = onTaskCompletedInterface;
    }

    @Override
    protected void onPreExecute() {
        Log.d(MainActivity.LOG_TAG, "{onPreExecute, 67} onPreExecute");
        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle(activity.getString(R.string.progressbar_fetching_title));
        progressDialog.setMessage("0% completed.");
        progressDialog.show();
    }


    @Override
    protected ArrayList<User> doInBackground(JsonArray... jsonArrays) {
        ArrayList<User> userArrayList = getUsersFromJsonArray(jsonArrays[0]);
        return userArrayList;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        Log.d(MainActivity.LOG_TAG, "{onProgressUpdate, 52} " + values[0]);
        progressDialog.setMessage(values[0] + "% complete.");
    }

    private ArrayList<User> getUsersFromJsonArray(JsonArray jsonArrayUsers) {
        User user;
        JsonObject jsonObjectUser;
        int progressStep = 1;
        ArrayList<User> userArrayList = new ArrayList<>();
        for (int counter = 0; counter < jsonArrayUsers.size(); counter++) {
            if (((double) counter / (double) jsonArrayUsers.size() > 0.1 * progressStep)) {
                publishProgress(progressStep++ * 10);
            }
            jsonObjectUser = jsonArrayUsers.get(counter).getAsJsonObject();
            user = createUserFromJsonObject(jsonObjectUser);
            userArrayList.add(user);
        }
        publishProgress(progressStep++ * 10);
        return userArrayList;
    }

    private User createUserFromJsonObject(JsonObject jsonObject) {
        User user;
        JsonObject jsonObjectName = jsonObject.get("name").getAsJsonObject();
        String firstName = jsonObjectName.get("first").getAsString();
        String lastName = jsonObjectName.get("last").getAsString();
        String completeName = firstName + " " + lastName;


        List<User> userList = User.find(User.class, "COMPLETE_NAME = ?", completeName);
        if (userList.size() > 0) {
            user = userList.get(0);
        } else {
            String email = jsonObject.get("email").getAsString();

            JsonObject jsonObjectPicture = jsonObject.get("picture").getAsJsonObject();
            String largePictureAddress = jsonObjectPicture.get("large").getAsString();

            user = new User(completeName, largePictureAddress, email);
            user.save();
        }

        return user;
    }

    @Override
    protected void onPostExecute(ArrayList<User> userArrayList) {
        progressDialog.dismiss();
        onTaskCompletedInterface.processJsonArrayUsersTaskCompleted(userArrayList);
    }

    public interface OnTaskCompletedInterface {
        void processJsonArrayUsersTaskCompleted(ArrayList<User> userArrayList);
    }


}
