package com.example.clarify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TrainPath extends AppCompatActivity {

    // View variable
    Button lvl1_s1, lvl1_s2, lvl1_s3, lvl1_s4;
    Button lvl2_s1, lvl2_s2, lvl2_s3, lvl2_s4;
    Button lvl3_s1, lvl3_s2, lvl3_s3, lvl3_s4;
    Button lvl4_s1, lvl4_s2, lvl4_s3, lvl4_s4;

    // Variable
    String level;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_path);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Hooks
        lvl1_s1 = findViewById(R.id.lvl_1S1);
        lvl1_s2 = findViewById(R.id.lvl_1S2);
        lvl1_s3 = findViewById(R.id.lvl_1S3);
        lvl1_s4 = findViewById(R.id.lvl_1S4);

        lvl2_s1 = findViewById(R.id.lvl_2S1);
        lvl2_s2 = findViewById(R.id.lvl_2S2);
        lvl2_s3 = findViewById(R.id.lvl_2S3);
        lvl2_s4 = findViewById(R.id.lvl_2S4);

        lvl3_s1 = findViewById(R.id.lvl_3S1);
        lvl3_s2 = findViewById(R.id.lvl_3S2);
        lvl3_s3 = findViewById(R.id.lvl_3S3);
        lvl3_s4 = findViewById(R.id.lvl_3S4);

        lvl4_s1 = findViewById(R.id.lvl_4S1);
        lvl4_s2 = findViewById(R.id.lvl_4S2);
        lvl4_s3 = findViewById(R.id.lvl_4S3);
        lvl4_s4 = findViewById(R.id.lvl_4S4);


        // Redirect to the stage activity
        lvl1_s1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), Stage.class);
                level = "1.1";
                intent.putExtra("selectedLevel", level);
                startActivity(intent);
            }
        });
        lvl1_s2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), Stage.class);
                level = "1.2";
                intent.putExtra("selectedLevel", level);
                startActivity(intent);
            }
        });
        lvl1_s3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), Stage.class);
                level = "1.3";
                intent.putExtra("selectedLevel", level);
                startActivity(intent);
            }
        });
        lvl1_s4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), Stage.class);
                level = "1.4";
                intent.putExtra("selectedLevel", level);
                startActivity(intent);
            }
        });
        // Level 2
        lvl2_s1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), Stage.class);
                level = "2.1";
                intent.putExtra("selectedLevel", level);
                startActivity(intent);
            }
        });
        lvl2_s2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), Stage.class);
                level = "2.2";
                intent.putExtra("selectedLevel", level);
                startActivity(intent);
            }
        });
        lvl2_s3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), Stage.class);
                level = "2.3";
                intent.putExtra("selectedLevel", level);
                startActivity(intent);
            }
        });
        lvl2_s4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), Stage.class);
                level = "2.4";
                intent.putExtra("selectedLevel", level);
                startActivity(intent);
            }
        });
        // Level 3
        lvl3_s1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), Stage.class);
                level = "3.1";
                intent.putExtra("selectedLevel", level);
                startActivity(intent);
            }
        });
        lvl3_s2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), Stage.class);
                level = "3.2";
                intent.putExtra("selectedLevel", level);
                startActivity(intent);
            }
        });
        lvl3_s3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), Stage.class);
                level = "3.3";
                intent.putExtra("selectedLevel", level);
                startActivity(intent);
            }
        });
        lvl3_s4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), Stage.class);
                level = "3.4";
                intent.putExtra("selectedLevel", level);
                startActivity(intent);
            }
        });
        // Level 4
        lvl4_s1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), Stage.class);
                level = "4.1";
                intent.putExtra("selectedLevel", level);
                startActivity(intent);
            }
        });
        lvl4_s2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), Stage.class);
                level = "4.2";
                intent.putExtra("selectedLevel", level);
                startActivity(intent);
            }
        });
        lvl4_s3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), Stage.class);
                level = "4.3";
                intent.putExtra("selectedLevel", level);
                startActivity(intent);
            }
        });
        lvl4_s4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), Stage.class);
                level = "4.4";
                intent.putExtra("selectedLevel", level);
                startActivity(intent);
            }
        });
    }

    public void logoutFn(View view) {
        SaveLogin.clearPhone(getApplicationContext());
        Intent i = new Intent(getApplicationContext(), Splash.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}