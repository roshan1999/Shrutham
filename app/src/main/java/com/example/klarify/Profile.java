package com.example.klarify;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {

    // View variables
    Button logout;
    TextView name;
    ImageView display_pic;
    TextInputLayout usr_name, usr_phone, usr_hospital_id, usr_dob;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USER = "USER";

    // TODO: Rename and change types of parameters
    private UserHelper user_obj;
    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user_obj User details from database of type [UserHelper].
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(UserHelper user_obj) {
        Log.e("JSON", user_obj.toString());
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        Gson gson = new Gson();
        String json = gson.toJson(user_obj);
        args.putString(USER, json);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Gson gson = new Gson();
            String json = getArguments().getString(USER);
            user_obj = gson.fromJson(json, UserHelper.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View user_profile =  inflater.inflate(R.layout.fragment_profile, container, false);

        // Hooks
        usr_name = user_profile.findViewById(R.id.TFName);
        usr_phone = user_profile.findViewById(R.id.TFPhone);
        usr_dob = user_profile.findViewById(R.id.TFDOB);
        usr_hospital_id = user_profile.findViewById(R.id.TFHospitalID);
        display_pic = user_profile.findViewById(R.id.usrProfile);
        name = user_profile.findViewById(R.id.usrName);
        logout = user_profile.findViewById(R.id.usrLogout);

        // Set the user details from the object
        name.setText(user_obj.getName());
        usr_name.getEditText().setText(user_obj.getName());
        usr_phone.getEditText().setText(user_obj.getPhoneNumber());
        usr_dob.getEditText().setText(user_obj.getDOB());
        usr_hospital_id.getEditText().setText(user_obj.getHospitalID());

        // Log out the user
        logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SaveLogin.clearPhone(getActivity().getApplicationContext());
                Intent i = new Intent(getActivity().getApplicationContext(), Splash.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
        return user_profile;
    }
}