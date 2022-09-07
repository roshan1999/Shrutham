package com.example.klarify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    // View variables
    TextInputLayout usr_phone;
    Button login, register_redirect;

    // Variables
    String phoneNo;

    // Firebase variables
    private FirebaseDatabase root_node;
    private DatabaseReference db_ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        // Hooks
        usr_phone = findViewById(R.id.TFPhone);
        login = findViewById(R.id.BTNLogin);
        register_redirect = findViewById(R.id.BTNRegister);

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!validatePhone())
                {
                    return;
                }
                phoneNo = usr_phone.getEditText().getText().toString();

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
                            // The phone number exists => existing user
                            // Send number to the next activity
                            Intent intent = new Intent(getApplicationContext(), Verification.class);
                            intent.putExtra("PhoneNo", phoneNo);
                            // Toast.makeText(getApplicationContext(), phoneNo, Toast.LENGTH_LONG).show();
                            startActivity(intent);
                        }
                        else {
                            // User Doesn't exist
                            usr_phone.setError("Please register for the app. New user");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Called when failed to read value
                    }
                });
            }
        });
    }

    // Go back to register activity
    public void onRegister(View view) {
        Intent i = new Intent(this,Register.class);
        startActivity(i);
    }

    private Boolean validatePhone()
    {
        String phone = usr_phone.getEditText().getText().toString();
        String phone_pattern = "[0-9]{10}";
        if(phone.isEmpty())
        {
            usr_phone.setError("Please enter Mobile number");
            return false;
        }
        else if(!phone.matches(phone_pattern))
        {
            usr_phone.setError("Enter a valid phone number");
            return false;
        }
        else
        {
            usr_phone.setError(null);
            usr_phone.setErrorEnabled(false);
            return true;
        }
    }
}