package com.example.mathhero;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;




public class home_Fragment extends Fragment {

    private TextView level1,level2,level3,level4;
    private ImageView logo;
    public static TextView leveltv,scoretv,startIn;

    private ImageView lock;;


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
        lock = view.findViewById(R.id.lock);

        level1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.player_level>=1) {
                    MainActivity.level1_frame.setVisibility(View.VISIBLE);
                    level1_Fragment.newTimer(1);
                    level1_Fragment.stage=1;
                    level1_Fragment.n=1;
                    play();
                }
            }
        });

        level2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.player_level>=2) {
                    MainActivity.level2_frame.setVisibility(View.VISIBLE);
                    level2_Fragment.newTimer(2);
                    level2_Fragment.stage=1;
                    level2_Fragment.n=1;
                    play();
                }
                else {showLock();}
            }
        });

        level3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.player_level>=3) {
                    MainActivity.level3_frame.setVisibility(View.VISIBLE);
                    level1_Fragment.newTimer(3);
                    level3_Fragment.stage=1;
                    level3_Fragment.n=1;
                    play();
                }
                else {showLock();}
            }
        });

        level4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.player_level==4){
                MainActivity.level4_frame.setVisibility(View.VISIBLE);
                    level1_Fragment.newTimer(4);
                    level4_Fragment.stage=1;
                    level4_Fragment.n=1;
                    play();
                }
                else {showLock();}
            }
        });
        return view;

    }

    public void play() {
        MainActivity.is_playing=true;
        MainActivity.Home_frame.setVisibility(View.INVISIBLE);
    }

    private void showLock() {
        lock.setVisibility(View.VISIBLE);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                lock.setVisibility(View.GONE);
            }
        }, 1500);
    }


}