package com.example.klarify;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    // View variables
    TextView app_name, app_slogan;
    ImageView app_logo;

    // Animation variables
    Animation slide_up, slide_down;
    private static int SPLASH_SCREEN = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // Fullscreen activity
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Animations
        slide_up = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        slide_down = AnimationUtils.loadAnimation(this, R.anim.slide_down);

        // Hooks
        app_name = findViewById(R.id.TVAppName);
        app_slogan = findViewById(R.id.TVSlogan);
        app_logo = findViewById(R.id.IVLogo);

        // Set animation
        app_logo.setAnimation(slide_down);
        app_name.setAnimation(slide_up);
        app_slogan.setAnimation(slide_up);

        // check if the user has logged in previously i.e. auto login
        if(SaveLogin.getPhone(Splash.this).length() == 0)
        {
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    Intent main = new Intent(Splash.this, Open.class);
                    Pair[] transitions = new Pair[2];
                    transitions[0] = new Pair<View, String>(app_logo, "appLogo");
                    transitions[1] = new Pair<View, String>(app_name, "appName");
                    ActivityOptions options = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        options = ActivityOptions.makeSceneTransitionAnimation(Splash.this, transitions);
                    }
                    startActivity(main, options.toBundle());
                    finish();
                }
            }, SPLASH_SCREEN);
        }
        else
        {
            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    Intent main = new Intent(Splash.this, Home.class);
                    Pair[] transitions = new Pair[2];
                    transitions[0] = new Pair<View, String>(app_logo, "appLogo");
                    transitions[1] = new Pair<View, String>(app_name, "appName");
                    ActivityOptions options = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        options = ActivityOptions.makeSceneTransitionAnimation(Splash.this, transitions);
                    }
                    startActivity(main, options.toBundle());
                    finish();
                }
            }, SPLASH_SCREEN);
        }
    }
}