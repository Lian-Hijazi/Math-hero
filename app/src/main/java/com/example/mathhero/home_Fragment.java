package com.example.mathhero;

import static com.example.mathhero.MainActivity.currentUserId;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class home_Fragment extends Fragment {

    private TextView level1,level2,level3,level4;
    private ImageView logo;
    public static TextView leveltv,scoretv,startIn;
    public home_Fragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home_, container, false);
        logo=view.findViewById(R.id.logo);
        logo.setImageResource(R.drawable.logo);
        leveltv=view.findViewById(R.id.level);
        scoretv=view.findViewById(R.id.score);
        level1=view.findViewById(R.id.addition);
        level2=view.findViewById(R.id.subtraction);
        level3=view.findViewById(R.id.multiplication);
        level4=view.findViewById(R.id.division);
        startIn=view.findViewById(R.id.startIn);

        level1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.player_level>=1) {
                    MainActivity.level1_frame.setVisibility(View.VISIBLE);
                    play();
                }

            }
        });

        level2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.player_level>=2) {
                    MainActivity.level2_frame.setVisibility(View.VISIBLE);
                    play();
                }
            }
        });

        level3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.player_level>=3) {
                    MainActivity.level3_frame.setVisibility(View.VISIBLE);
                    play();
                }
            }
        });

        level4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.player_level==4){
                MainActivity.level4_frame.setVisibility(View.VISIBLE);
                play();
                }
            }
        });
        return view;

    }

    public void play() {
        MainActivity.is_playing=true;
        MainActivity.Home_frame.setVisibility(View.INVISIBLE);
    }






}