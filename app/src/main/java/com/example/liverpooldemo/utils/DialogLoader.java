package com.example.liverpooldemo.utils;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import com.example.liverpooldemo.R;


public class DialogLoader extends DialogFragment {
    ObjectAnimator anim;
    ObjectAnimator anim2;
    /*
    Para la descripcion del loader
     */
    public boolean shoulDisplayDescription = false;
    public String textDescription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ColorDrawable fondoTransparente = new ColorDrawable(Color.TRANSPARENT);
        getDialog().getWindow().setBackgroundDrawable(fondoTransparente);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View vista = inflater.inflate(R.layout.dialog_loader, null);

        animacion(vista);

        builder.setView(vista);
        if (this.shoulDisplayDescription){
            TextView textView = vista.findViewById(R.id.textLoadesDescription);
            textView.setVisibility(View.VISIBLE);
            textView.setText(textDescription);
        }else{
            TextView textView = vista.findViewById(R.id.textLoadesDescription);
            textView.setVisibility(View.GONE);
        }
        final AlertDialog vB = builder.create();
        vB.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return vB;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        anim.cancel();
        anim2.cancel();
        anim = null;
        anim2 = null;
    }

    public void animacion(final View vista) {
        try {
            System.out.println("entra");
            final CardView slider = vista.findViewById(R.id.slider);
            anim = ObjectAnimator.ofFloat(slider, "x", MyUtilsJava.convertDPToPixels(270, getActivity()));
            anim.setDuration(1000); // duration 5 seconds
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    anim2 = ObjectAnimator.ofFloat(slider, "x", 0);
                    anim2.setDuration(1000); // duration 5 seconds
                    anim2.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            animacion(vista);
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
                    anim2.start();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            anim.start();

            int colorFrom = getResources().getColor(R.color.colorAccent);
            int colorMiddle = getResources().getColor(R.color.color_white);
            int colorTo = getResources().getColor(R.color.colorAccent);
            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorMiddle, colorTo);
            colorAnimation.setDuration(2000); // milliseconds
            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    slider.setBackgroundColor((int) animator.getAnimatedValue());
                }

            });
            colorAnimation.start();
        } catch (Exception e) {
        } finally {

        }
    }
}
