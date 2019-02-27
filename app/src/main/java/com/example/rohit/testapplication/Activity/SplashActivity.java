package com.example.rohit.testapplication.Activity;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rohit.testapplication.Adapter.AnimationListenerAdapter;
import com.example.rohit.testapplication.R;
import com.example.rohit.testapplication.SqlLite.DatabaseHelper;

public class SplashActivity extends AppCompatActivity {

    // Screen parameters
    public static int screenHeightPx;
    public static int screenWidthPx;

    // Animation values
    private float globalPaddingPx;
    private float helpButtonSizePx;

    private ImageView titleIcon;
    private FrameLayout titleLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // title bar layout
        titleLayout = findViewById(R.id.sr_title_layout);
        titleIcon = findViewById(R.id.sr_title_icon);

        // Get screen dimensions for animation purposes
        findDeviceWidthHeight();

        // calculations for animation purposes
        globalPaddingPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
        helpButtonSizePx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());

        playInitialAnimations();
    }

    private void findDeviceWidthHeight(){
        // Animation Purpose
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidthPx = size.x;
        screenHeightPx = size.y;

    }

    private void playInitialAnimations() {

        final float titleRowHeightPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
        final float titleIconWidthPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());

        final float titleYCenterPx = screenHeightPx / 2 - titleRowHeightPx / 2 - globalPaddingPx;
        final float titleXCenterPx = screenWidthPx / 2 - titleIconWidthPx / 2 - globalPaddingPx;

        TranslateAnimation slideFromRight = new TranslateAnimation(titleXCenterPx, 0, titleYCenterPx, titleYCenterPx);
        slideFromRight.setStartOffset(1800);
        slideFromRight.setDuration(600);
        slideFromRight.setInterpolator(new AccelerateDecelerateInterpolator());
        slideFromRight.setAnimationListener(new AnimationListenerAdapter() {
            @Override
            public void onAnimationEnd(Animation animation) {
                TranslateAnimation titleIconSlideUp = new TranslateAnimation(0, 0, titleYCenterPx, 0);
                titleIconSlideUp.setInterpolator(new AccelerateDecelerateInterpolator());
                titleIconSlideUp.setStartOffset(300);
                titleIconSlideUp.setDuration(700);
                titleIcon.startAnimation(titleIconSlideUp);
            }
        });

        // Title text slide from right and move up with title icon
        final TextView title = findViewById(R.id.sr_title_text);
        TranslateAnimation titleSlideFromRight = new TranslateAnimation(screenWidthPx - globalPaddingPx, 0, titleYCenterPx, titleYCenterPx);
        titleSlideFromRight.setInterpolator(new AccelerateDecelerateInterpolator());
        titleSlideFromRight.setStartOffset(1830);
        titleSlideFromRight.setDuration(600);
        titleSlideFromRight.setAnimationListener(new AnimationListenerAdapter() {
            @Override
            public void onAnimationEnd(Animation animation) {
                TranslateAnimation titleSlideUp = new TranslateAnimation(0, 0, titleYCenterPx, 0);
                titleSlideUp.setInterpolator(new AccelerateDecelerateInterpolator());
                titleSlideUp.setStartOffset(310);
                titleSlideUp.setDuration(700);
                title.startAnimation(titleSlideUp);

                titleSlideUp.setAnimationListener(new AnimationListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        startActivity(new Intent(SplashActivity.this,HomeActivity.class));
                        finish();
                    }
                });
            }
        });


        title.startAnimation(titleSlideFromRight);
        titleIcon.startAnimation(slideFromRight);
    }


}
