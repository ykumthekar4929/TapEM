package edu.uta.se1.team6.tapem.Helpers;

/**
 * Created by yashodhan on 3/23/18.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

/**
 * Created by yashodhank on 19/10/16.
 */

public class ObjectAnimations {

//	final ScaleAnimation animationEnlarge = new ScaleAnimation(2, 1, 2, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//	animationEnlarge.setFillAfter(true);
//	animationEnlarge.setDuration(200);
//
//	final ScaleAnimation animation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//	animation.setFillAfter(true);
//	animation.setDuration(200);
//
//	final RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_PARENT, Animation.RELATIVE_TO_PARENT);
//	anim.setRepeatCount(Animation.ABSOLUTE);
//	anim.setDuration(700);


    public static ObjectAnimator lift(final Object target) {
        final ObjectAnimator lift = ObjectAnimator.ofFloat(target, "translationZ", 5);
        lift.start();
        lift.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                drop(target);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        return lift;
    }

    public static ObjectAnimator drop(Object target) {
        final ObjectAnimator drop = ObjectAnimator.ofFloat(target, "translationZ", 0);
        drop.start();
        return drop;

    }

    public static void slide_out_right(final View childView, View container) {
        childView.animate()
                .translationXBy(container.getWidth())
                .setDuration(300)
                .alphaBy(50)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        childView.setVisibility(View.GONE);
                    }});
    }

    public static void slide_in_right(final View childView, View container) {
        final float xValue = childView.getX();
        childView.setX(container.getWidth());

        childView.animate()
                .translationXBy(-container.getWidth())
                .setDuration(300)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        childView.setVisibility(View.VISIBLE);
//						childView.setX(xValue);

                    }});
    }

    public static void fade_down(final View chilView) {
        final float xValue = chilView.getHeight();

        chilView.animate().translationYBy(xValue)
                .setDuration(200)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        chilView.setVisibility(View.GONE);
                        super.onAnimationEnd(animation);
                    }
                });

    }

    public static void slide_down_menu(final RelativeLayout groupsMenu) {

        final float yVal = groupsMenu.getHeight();

        groupsMenu.animate().translationYBy(yVal)
                .setDuration(200)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        groupsMenu.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
    }

    public static void fade_up(final View chilView) {
        final float xValue = chilView.getHeight();
        float d = chilView.getBottom();
        chilView.setY(d);

        chilView.animate().translationYBy(-xValue)
                .setDuration(200)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        chilView.setVisibility(View.VISIBLE);
                        super.onAnimationEnd(animation);
                    }
                });

    }

    public static void swapViews(final View toBeDismissed, final View toBeSwappedIn, final View container) {
        final float xValueofToBeDismissed = toBeDismissed.getX();
        final float xValueOfToBeSwappedIn = toBeSwappedIn.getX();
        final float containerWidth = container.getWidth();
        final float DeltaX = containerWidth - xValueOfToBeSwappedIn;
        toBeDismissed.animate()
                .translationXBy(container.getWidth())
                .setDuration(300)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        toBeSwappedIn.setX(containerWidth);
                        toBeSwappedIn.animate()
                                .translationXBy(-DeltaX)
                                .setDuration(300)
                                .setInterpolator(new AccelerateDecelerateInterpolator())
                                .setListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {
                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        toBeSwappedIn.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {
                                    }
                                });
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        toBeDismissed.setVisibility(View.GONE);
                        toBeDismissed.setX(xValueofToBeDismissed);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }


                });

    }

}
