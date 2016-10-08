package br.com.targettrust.aula3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import br.com.targettrust.aula3.fragments.RegisterFragment;
import br.com.targettrust.aula3.fragments.SignInFragment;

public class MainActivity extends AppCompatActivity implements SignInFragment.OnSignInFragmentInteractionListener {

    public static final String LOG_TAG = "LOG_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = createFragmentTransaction();
        fragmentTransaction.replace(R.id.framelayout_main, new SignInFragment());
        fragmentTransaction.commit();
    }

    private FragmentTransaction createFragmentTransaction() {
        return getSupportFragmentManager().beginTransaction();
    }

    @Override
    public void onSignInFragmentInteraction(int action) {
        Fragment fragment = null;
        switch (action) {
            case SignInFragment.ACTION_REGISTER: {
                fragment = new RegisterFragment();
                break;
            }

            case SignInFragment.ACTION_SIGN_IN: {
                fragment = new SignInFragment();
                break;
            }
        }

        FragmentTransaction fragmentTransaction = createFragmentTransaction();
        fragmentTransaction.replace(R.id.framelayout_sign_in, fragment);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();

    }
}
