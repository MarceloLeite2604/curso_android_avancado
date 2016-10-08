package br.com.targettrust.aula3.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageView;

import br.com.targettrust.aula3.MainActivity;
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
        // Inflate the layout for this fragment

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

        Animation fadeInAnimation = new AlphaAnimation(0.0f, 1.0f);
        fadeInAnimation.setFillEnabled(true);
        fadeInAnimation.setDuration(1000);
        fadeInAnimation.setRepeatCount(0);

        Animation fadeOutAnimation = new AlphaAnimation(1.0f, 0.0f);
        fadeOutAnimation.setFillEnabled(true);
        fadeOutAnimation.setDuration(1000);
        fadeOutAnimation.setRepeatCount(0);

        AnimationSet fadeAnimationSet = new AnimationSet(true);
        fadeAnimationSet.addAnimation(fadeInAnimation);
        fadeAnimationSet.addAnimation(fadeOutAnimation);

        fadeAnimationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.d(MainActivity.LOG_TAG, "SignInFragment:onAnimationStart:94 ");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d(MainActivity.LOG_TAG, "SignInFragment:onAnimationEnd:100 ");
                imageViewLogo.setAlpha(0.0f);
                imageViewLogo.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.d(MainActivity.LOG_TAG, "SignInFragment:onAnimationRepeat:103 ");
            }
        });

        imageViewLogo.startAnimation(fadeAnimationSet);

        return viewFragment;
    }

    public interface OnSignInFragmentInteractionListener {
        void onSignInFragmentInteraction(int action);
    }
}
