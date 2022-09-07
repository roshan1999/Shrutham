package com.example.klarify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class Home extends AppCompatActivity {

    // View variables
    BottomNavigationView tabs;

    // Variables
    private String phoneNo;
    UserHelper current_user;

    // Firebase variables
    private FirebaseDatabase root_node;
    private DatabaseReference db_ref;

    // Fragment variables
    Fragment selectedFragment;

    SharedPreferences sharedpreferences = getSharedPreferences(pref, Context.MODE_PRIVATE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // View variables
        tabs = findViewById(R.id.bottom_navigation);


        // When the app opens the default train fragment should be shown
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Train()).commit();

        tabs.setOnNavigationItemSelectedListener(navListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.train:
                    selectedFragment = new Train();
                    break;
                case R.id.profile:
                    phoneNo = ""+SaveLogin.getPhone(Home.this);
                    Log.e("Hello", "Come here bitch");
                    // Verify that the number entered has an account registered to it
                    root_node = FirebaseDatabase.getInstance();
                    db_ref = root_node.getReference("Users");
                    db_ref.child(phoneNo).addValueEventListener(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot)
                        {
                            // Called initially and when an update is made
                            if (dataSnapshot.exists()) {
                                // The phone number exists => correct token
                                // Create user object
                                Log.e("Hello", "hello");
                                String dob = dataSnapshot.child(phoneNo).child("age").getValue(String.class);
                                String hospitalID = dataSnapshot.child(phoneNo).child("hospitalID").getValue(String.class);
                                String name = dataSnapshot.child(phoneNo).child("name").getValue(String.class);
                                String phoneNumber = dataSnapshot.child(phoneNo).child("phoneNumber").getValue(String.class);
                                String gender = dataSnapshot.child(phoneNo).child("gender").getValue(String.class);
                                Toast.makeText(getApplicationContext(), "Database data "+dob+hospitalID+name+phoneNumber+gender, Toast.LENGTH_LONG).show();
                                current_user = new UserHelper(name, dob, hospitalID, phoneNumber, gender);
                                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                                Gson gson = new Gson();
                                String json = gson.toJson(myObject);
                                prefsEditor.putString("MyObject", json);
                                prefsEditor.commit();
                                selectedFragment = new Profile();
                            }
                            else {
                                // User token is wrong i.e. logout and ask to login again
                                Log.e("Hello", "Else");
                                Toast.makeText(getApplicationContext(), "Please login again", Toast.LENGTH_LONG).show();
                                SaveLogin.clearPhone(getApplicationContext());
                                Intent i = new Intent(getApplicationContext(), Login.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Called when failed to read value
                            Toast.makeText(getApplicationContext(), "Failed to read values", Toast.LENGTH_LONG).show();
                        }
                    });
                    break;
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();
            return true;
        }
    };
}