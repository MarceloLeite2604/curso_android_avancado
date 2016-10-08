package br.com.targettrust.aula3.fragments;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.androidanimations.library.attention.FlashAnimator;

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

        YoYo.with(Techniques.FadeInUp)
                .duration(1000)
                .playOn(viewFragment.findViewById(R.id.imageview_logo));

        YoYo.with(Techniques.FadeInUp)
                .duration(500)
                .playOn(viewFragment.findViewById(R.id.edittext_email));

        YoYo.with(Techniques.FadeInUp)
                .duration(500)
                .playOn(viewFragment.findViewById(R.id.edittext_password));

        YoYo.with(Techniques.BounceInUp)
                .duration(500)
                .playOn(viewFragment.findViewById(R.id.button_register));
        YoYo.with(Techniques.BounceInUp)
                .duration(500)
                .playOn(viewFragment.findViewById(R.id.button_sign_in));



        return viewFragment;
    }

    public interface OnSignInFragmentInteractionListener {
        void onSignInFragmentInteraction(int action);
    }
}
