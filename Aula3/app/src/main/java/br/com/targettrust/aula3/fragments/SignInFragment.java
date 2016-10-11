package br.com.targettrust.aula3.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import br.com.targettrust.aula3.R;

public class SignInFragment extends Fragment {

    public static final int ACTION_REGISTER = 69034;
    public static final int ACTION_SIGN_IN = 39403;

    private ImageView imageViewLogo;

    private OnSignInFragmentInteractionListener mListener;

    public SignInFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSignInFragmentInteractionListener) {
            mListener = (OnSignInFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewFragment = inflater.inflate(R.layout.fragment_sign_in, container, false);

        final Button buttonRegister = (Button) viewFragment.findViewById(R.id.button_register);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onSignInFragmentInteraction(ACTION_REGISTER);
            }
        });

        final Button buttonSignIn = (Button) viewFragment.findViewById(R.id.button_sign_in);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onSignInFragmentInteraction(ACTION_SIGN_IN);
            }
        });


        imageViewLogo = (ImageView) viewFragment.findViewById(R.id.imageview_logo);

        /*YoYo.with(Techniques.FadeInUp)
                .duration(1000)
                .playOn(viewFragment.findViewById(R.id.imageview_logo));*/

        // Animating using "Animation" class.
        Animation fadeAnimation = new AlphaAnimation(1.0f, 0.7f);
        fadeAnimation.setRepeatCount(Animation.INFINITE);
        fadeAnimation.setRepeatMode(Animation.REVERSE);
        fadeAnimation.setDuration(2000);
        imageViewLogo.setAnimation(fadeAnimation);

        // Animating using YoYo library.
        YoYo.with(Techniques.FadeInUp)
                .duration(500)
                .playOn(viewFragment.findViewById(R.id.edittext_email));

        //YoYo.with(Techniques.FadeInUp)
        YoYo.with(Techniques.FadeInUp)
                .duration(500)
                .playOn(viewFragment.findViewById(R.id.edittext_password));

        YoYo.with(Techniques.BounceInUp)
                .duration(1500)
                .playOn(viewFragment.findViewById(R.id.button_register));

        YoYo.with(Techniques.BounceInUp)
                .delay(500)
                .duration(1500)
                .playOn(viewFragment.findViewById(R.id.button_sign_in));


        return viewFragment;
    }

    public interface OnSignInFragmentInteractionListener {
        void onSignInFragmentInteraction(int action);
    }
}
