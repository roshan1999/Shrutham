package com.example.clarify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Verification extends AppCompatActivity {

    // View Variables
    ProgressBar login_progress;
    Button verify_otp;
    TextInputLayout usr_otp;

    // Firebase variables
    private FirebaseAuth mAuth;
    PhoneAuthProvider.ForceResendingToken mResendToken;

    // Variables
    String phone_number;
    String verificationOTP;
    String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Hooks
        login_progress = findViewById(R.id.PBLogin);
        verify_otp = findViewById(R.id.BTNLogin);
        usr_otp = findViewById(R.id.TFOTP);

        // Get shared instance of firebase auth
        mAuth = FirebaseAuth.getInstance();

        // Get the phone number
        phone_number = getIntent().getStringExtra("PhoneNo");

        // Send OTP to user
//        Toast.makeText(getApplicationContext(), "+91"+phone_number, Toast.LENGTH_LONG).show();
        sendOTP(phone_number);

        verify_otp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                if(!validateOTP())
                    return;
                otp = usr_otp.getEditText().getText().toString();
                if(otp != null)
                {
                    login_progress.setVisibility(View.VISIBLE);
                    verifyOTP(otp);
                }
            }
        });
    }

    private Boolean validateOTP()
    {
        String otp = usr_otp.getEditText().getText().toString();
        // check for number only limiting at length 6
        String otp_pattern = "([0-9]{6})";
        if(!otp.matches(otp_pattern))
        {
            usr_otp.setError("OTP is a 6 digit code. Numbers only");
            return false;
        }
        else {
            usr_otp.setError(null);
            usr_otp.setErrorEnabled(false);
            return true;
        }
    }

    private void sendOTP(String phoneNo) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+phoneNo)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this) // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationOTP = s;
            mResendToken = forceResendingToken;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            // Get the OTP
            otp = phoneAuthCredential.getSmsCode();
            if(otp != null)
            {
                login_progress.setVisibility(View.VISIBLE);
                verifyOTP(otp);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            // OTP verification failed => Notify user via a Toast
            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                Toast.makeText(getApplicationContext(), "SMS quota exceeded", Toast.LENGTH_LONG).show();
            }
        }
    };

    private void verifyOTP(String otp)
    {
        PhoneAuthCredential credentials = PhoneAuthProvider.getCredential(verificationOTP, otp);
        // Sign in the user
        loginUser(credentials);
    }

    private void loginUser(PhoneAuthCredential credentials) {
        mAuth.signInWithCredential(credentials)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            // Log.d(TAG, "signInWithCredential:success");
                            // Sign in the user and save the number
                            SaveLogin.setPhone(Verification.this, phone_number);
                            Intent intent = new Intent(getApplicationContext(), TrainPath.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                            FirebaseUser user = task.getResult().getUser();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            // Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
}