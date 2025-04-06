package com.example.mathhero;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class home_Fragment extends Fragment {

    private TextView level,score,level1,level2,level3,level4,startIn;
    private ImageView logo;
    private int currentLevel;


    public home_Fragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home_, container, false);
        logo=view.findViewById(R.id.logo);
        logo.setImageResource(R.drawable.logo);
        level=view.findViewById(R.id.level);
        score=view.findViewById(R.id.score);
        level1=view.findViewById(R.id.addition);
        level2=view.findViewById(R.id.subtraction);
        level3=view.findViewById(R.id.multiplication);
        level4=view.findViewById(R.id.division);
        startIn=view.findViewById(R.id.startIn);
        currentLevel=MainActivity.player_level;

        level1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentLevel==0) {
                    MainActivity.level1_frame.setVisibility(View.VISIBLE);
                    play();
                }

            }
        });

        level2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentLevel==2) {
                    MainActivity.level2_frame.setVisibility(View.VISIBLE);
                    play();
                }
            }
        });

        level3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentLevel==3) {
                    MainActivity.level3_frame.setVisibility(View.VISIBLE);
                    play();
                }

            }
        });

        level4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentLevel==4){
                MainActivity.level4_frame.setVisibility(View.VISIBLE);
                play();
                }

            }
        });

        filldata();

        return view;

    }

    public void play() {
        MainActivity.is_playing=true;
        MainActivity.Home_frame.setVisibility(View.INVISIBLE);
    }

    public void filldata(){
        MainActivity.startData(() -> {
            score.setText(String.valueOf(MainActivity.player_score));
            level.setText(String.valueOf(MainActivity.player_level));
            startIn.setText("Let's start in level " + MainActivity.player_level);
        });

    }



}