package com.example.clarify;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Open extends AppCompatActivity {

    // View variables
    Button login_redirect, register_redirect;
    ImageView app_logo;
    TextView app_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Hooks
        login_redirect = findViewById(R.id.BTNLogin);
        register_redirect = findViewById(R.id.BTNRegister);
        app_logo = findViewById(R.id.IVLogo);
        app_name = findViewById(R.id.TVAppName);

        // Redirect to login page
        login_redirect.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Create transition pairs
                Pair[] transitions = new Pair[4];
                transitions[0] = new Pair<View, String>(app_logo, "appLogo");
                transitions[1] = new Pair<View, String>(app_name, "appName");
                transitions[2] = new Pair<View, String>(register_redirect, "colorBtn");
                transitions[3] = new Pair<View, String>(login_redirect, "colorlessBtn");

                Intent loginIntent = new Intent(getApplicationContext(), Login.class);
                ActivityOptions options = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation(Open.this, transitions);
                }
                startActivity(loginIntent, options.toBundle());
            }
        });

        // Redirect to Register page
        register_redirect.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Create transition pairs
                Pair[] transitions = new Pair[4];
                transitions[0] = new Pair<View, String>(app_logo, "appLogo");
                transitions[1] = new Pair<View, String>(app_name, "appName");
                transitions[2] = new Pair<View, String>(register_redirect, "colorBtn");
                transitions[3] = new Pair<View, String>(login_redirect, "colorlessBtn");

                Intent loginIntent = new Intent(getApplicationContext(), Register.class);
                ActivityOptions options = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation(Open.this, transitions);
                }
                startActivity(loginIntent, options.toBundle());

            }
        });
    }
}