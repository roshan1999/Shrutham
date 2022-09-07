package com.example.klarify;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Train#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Train extends Fragment {

    // View variable
    Button lvl1_s1, lvl1_s2, lvl1_s3, lvl1_s4;
    Button lvl2_s1, lvl2_s2, lvl2_s3, lvl2_s4;
    Button lvl3_s1, lvl3_s2, lvl3_s3, lvl3_s4;
    Button lvl4_s1, lvl4_s2, lvl4_s3, lvl4_s4;

    // Variable
    String level;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Train() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Train.
     */
    // TODO: Rename and change types and number of parameters
    public static Train newInstance(String param1, String param2) {
        Train fragment = new Train();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View trainPath = inflater.inflate(R.layout.fragment_train, container, false);
        // Hooks
        lvl1_s1 = trainPath.findViewById(R.id.lvl_1S1);
        lvl1_s2 = trainPath.findViewById(R.id.lvl_1S2);
        lvl1_s3 = trainPath.findViewById(R.id.lvl_1S3);
        lvl1_s4 = trainPath.findViewById(R.id.lvl_1S4);

        lvl2_s1 = trainPath.findViewById(R.id.lvl_2S1);
        lvl2_s2 = trainPath.findViewById(R.id.lvl_2S2);
        lvl2_s3 = trainPath.findViewById(R.id.lvl_2S3);
        lvl2_s4 = trainPath.findViewById(R.id.lvl_2S4);

        lvl3_s1 = trainPath.findViewById(R.id.lvl_3S1);
        lvl3_s2 = trainPath.findViewById(R.id.lvl_3S2);
        lvl3_s3 = trainPath.findViewById(R.id.lvl_3S3);
        lvl3_s4 = trainPath.findViewById(R.id.lvl_3S4);

        lvl4_s1 = trainPath.findViewById(R.id.lvl_4S1);
        lvl4_s2 = trainPath.findViewById(R.id.lvl_4S2);
        lvl4_s3 = trainPath.findViewById(R.id.lvl_4S3);
        lvl4_s4 = trainPath.findViewById(R.id.lvl_4S4);

        // Redirect to the stage activity
        lvl1_s1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity().getApplicationContext(), Stage.class);
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
                Intent intent = new Intent(getActivity().getApplicationContext(), Stage.class);
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
                Intent intent = new Intent(getActivity().getApplicationContext(), Stage.class);
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
                Intent intent = new Intent(getActivity().getApplicationContext(), Stage.class);
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
                Intent intent = new Intent(getActivity().getApplicationContext(), Stage.class);
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
                Intent intent = new Intent(getActivity().getApplicationContext(), Stage.class);
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
                Intent intent = new Intent(getActivity().getApplicationContext(), Stage.class);
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
                Intent intent = new Intent(getActivity().getApplicationContext(), Stage.class);
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
                Intent intent = new Intent(getActivity().getApplicationContext(), Stage.class);
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
                Intent intent = new Intent(getActivity().getApplicationContext(), Stage.class);
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
                Intent intent = new Intent(getActivity().getApplicationContext(), Stage.class);
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
                Intent intent = new Intent(getActivity().getApplicationContext(), Stage.class);
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
                Intent intent = new Intent(getActivity().getApplicationContext(), Stage.class);
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
                Intent intent = new Intent(getActivity().getApplicationContext(), Stage.class);
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
                Intent intent = new Intent(getActivity().getApplicationContext(), Stage.class);
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
                Intent intent = new Intent(getActivity().getApplicationContext(), Stage.class);
                level = "4.4";
                intent.putExtra("selectedLevel", level);
                startActivity(intent);
            }
        });

        return trainPath;
    }
}