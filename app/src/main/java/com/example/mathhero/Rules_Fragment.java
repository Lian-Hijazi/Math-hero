package com.example.mathhero;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Rules_Fragment extends Fragment {
    private ImageView img,logo;
    TextView rules,stages,conditions,points,help;
    Button back;
    public Rules_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_rules_, container, false);
        back=view.findViewById(R.id.back);
        rules=view.findViewById(R.id.rules);
        stages=view.findViewById(R.id.stages);
        conditions=view.findViewById(R.id.conditions);
        points=view.findViewById(R.id.points);
        help=view.findViewById(R.id.help);
        logo=view.findViewById(R.id.logo);
        logo.setImageResource(R.drawable.logo);
        img = view.findViewById(R.id.imageView);
        img.setImageResource(R.drawable.math);
        rules.setVisibility(View.INVISIBLE);
        back.setVisibility(View.INVISIBLE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rules.setVisibility(View.INVISIBLE);
                back.setVisibility(View.INVISIBLE);
            }
        });

        help.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                rules.setText("If you need help solving a question, you can get assistance, but 5 points will be deducted.\n\n" +
                        "If your points are less than 5 and you need help, you must choose " +
                        "either to solve the question on your own or restart the game from the beginning (Stage 1) while keeping your current points.");

                rules.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);
            }
        });

        points.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                rules.setText("Each correct answer earns you points based on the level:\n\n" +
                        "Level 1: 2 points.\n" +
                        "Level 2: 4 points.\n" +
                        "Level 3: 6 points.");

                rules.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);
            }
        });

        conditions.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                rules.setText("you must be over 10 years old.\n\n" +
                        "The game starts at Stage 1 and ends at Stage 4.\n\n" +
                        "To win the game, you have to complete all four stages.\n\n" +
                        "To move to the next level, you have to answer 5 exercises correctly in the current level.");

                rules.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);
            }
        });

        stages.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                rules.setText("There are four stages in the game, and each stage has three levels.\n\n\n" +
                        "Stage 1: Addition\n" +
                        "Stage 2: Subtraction\n" +
                        "Stage 3: Multiplication\n" +
                        "Stage 4: Division\n\n" +
                        "Level 1: single-digit numbers.\n" +
                        "Level 2: two-digit numbers.\n" +
                        "Level 3: three-digit numbers.\n");

                rules.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);
            }
        });


        return view;
    }
}