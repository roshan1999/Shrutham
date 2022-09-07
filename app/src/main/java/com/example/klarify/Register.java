package com.example.klarify;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    // View variables
    Button login_redirect, register;
    TextInputLayout usr_name, usr_phone, usr_hospital_id, usr_dob;
    ImageView app_logo;
    TextView greeting;
    RadioGroup usr_gender;

    // Firebase variables
    private FirebaseDatabase root_node;
    private DatabaseReference db_ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Hooks
        login_redirect = findViewById(R.id.BTNLogin);
        register = findViewById(R.id.BTNRegister);
        usr_name = findViewById(R.id.TFName);
        usr_phone = findViewById(R.id.TFPhone);
        usr_dob = findViewById(R.id.TFDOB);
        usr_hospital_id = findViewById(R.id.TFHospitalID);
        app_logo = findViewById(R.id.IVLogo);
        greeting = findViewById(R.id.TVGreeting);
        usr_gender = findViewById(R.id.RGGender);

        // Redirect to login page
        login_redirect.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Create pair for transitions
                Pair[] transitions = new Pair[4];
                transitions[0] = new Pair<View, String>(app_logo, "appLogo");
                transitions[1] = new Pair<View, String>(greeting, "appName");
                transitions[2] = new Pair<View, String>(login_redirect, "colorlessBtn");
                transitions[3] = new Pair<View, String>(register, "coloredBtn");

                Intent loginIntent = new Intent(getApplicationContext(), Login.class);
                ActivityOptions options = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation(Register.this, transitions);
                }
                startActivity(loginIntent, options.toBundle());
            }
        });

        // Register the new user
        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Get root instance
                root_node = FirebaseDatabase.getInstance();
                // Get reference to the child Users
                db_ref = root_node.getReference("Users");
                Log.d("Firebase",""+db_ref.toString());

                // Call validation funtions
                if(!validateName() | !validateDOB() | !validatePhone() | !validateHospitalId())
                {
                    return;
                }

                // Gather the information of the new user
                String name = usr_name.getEditText().getText().toString();
                String dob = usr_dob.getEditText().getText().toString();
                String hospital_id = usr_hospital_id.getEditText().getText().toString();
                String phone = usr_phone.getEditText().getText().toString();
                RadioButton selected_gender = findViewById(usr_gender.getCheckedRadioButtonId());
                String gender = selected_gender.getText().toString();
                if(gender.equals(""))
                {
                    gender = "Prefer not to say";
                }
                // Call the helper function
                UserHelper new_user = new UserHelper(name, dob, hospital_id, phone, gender);
                // Add the new User to the firebase
                db_ref.child(phone).setValue(new_user);
                Intent i;
                if(phone!=null){
                    i = new Intent(getApplicationContext(), Verification.class);
                    i.putExtra("PhoneNo", phone);
                }
                else{
                    i = new Intent(getApplicationContext(),Login.class);
                }
                startActivity(i);
            }
        });
    }

    // Validation functions
    private Boolean validateName()
    {
        String name = usr_name.getEditText().getText().toString();
        if(name.isEmpty())
        {
            usr_name.setError("Please add your name");
            return false;
        }
        else
        {
            usr_name.setError(null);
            usr_name.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateDOB()
    {
        String dob_format = "([0-9]{2}-[0-9]{2}-[0-9]{4}$)";
        String dob = usr_dob.getEditText().getText().toString();
        if(dob.isEmpty())
        {
            usr_dob.setError("Please add your Date of birth");
            return false;
        }
        else if(!dob.matches(dob_format))
        {
            usr_dob.setError("Please enter the date of birth in the given format");
            return false;
        }
        else
        {
            usr_dob.setError(null);
            usr_dob.setErrorEnabled(false);
            return true;
        }
    }

    // hospital ID validation (Hospital ID is a 7 or 8 digit number)
    private Boolean validateHospitalId()
    {
        String id = usr_hospital_id.getEditText().getText().toString();
        if(id.isEmpty())
        {
            usr_hospital_id.setError("Please enter Hospital ID");
            return false;
        }
        else if(id.length()>8 && id.length()<7)
        {
            usr_hospital_id.setError("Enter a valid Hospital ID");
            return false;
        }
        else
        {
            usr_hospital_id.setError(null);
            usr_hospital_id.setErrorEnabled(false);
            return true;
        }
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

    // Gender validation
}