package targettrust.com.br.aula2.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import targettrust.com.br.aula2.R;
import targettrust.com.br.aula2.model.User;

/**
 * Created by sala01 on 05/10/2016.
 */

public class UserAdapter extends BaseAdapter {

    ArrayList<User> userArrayList;
    Activity activity;

    public UserAdapter(ArrayList<User> userArrayList, Activity activity) {
        this.userArrayList = userArrayList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return userArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return userArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View viewRowUser = layoutInflater.inflate(R.layout.row_user, viewGroup, false);

        final TextView textViewName = (TextView) viewRowUser.findViewById(R.id.textview_name);
        final TextView textViewEmail = (TextView) viewRowUser.findViewById(R.id.textview_email);
        final ImageView imageViewPicture = (ImageView) viewRowUser.findViewById(R.id.imageview_picture);

        User user = (User) getItem(i);

        textViewName.setText(user.getCompleteName());
        textViewEmail.setText(user.getEmailAddress());
        Ion.with(activity.getBaseContext()).load(user.getLargePictureAddress()).intoImageView(imageViewPicture);

        return viewRowUser;
    }
}
