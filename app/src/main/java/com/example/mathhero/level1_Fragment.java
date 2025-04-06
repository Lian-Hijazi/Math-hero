package com.example.mathhero;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


public class level1_Fragment extends Fragment {
    private ImageView think_img, hint;
    private Button check, leave;
    private int score, Nhint;
    private TextView scoreT, number1, number2, answer;
    private int stage, n;
    TextView timerText;
    int time, t;
    boolean answered = false;


    public level1_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_level1_, container, false);

        stage = 1;
        n = 1;
        Nhint = 0;
        think_img = view.findViewById(R.id.think_img);
        think_img.setImageResource(R.drawable.help);
        leave = view.findViewById(R.id.leave);
        check = view.findViewById(R.id.check);
        hint = view.findViewById(R.id.hint);
        hint.setImageResource(R.drawable.hint);
        score = MainActivity.player_score;
        scoreT = view.findViewById(R.id.score);
        number1 = view.findViewById(R.id.number1);
        number2 = view.findViewById(R.id.number2);
        answer = view.findViewById(R.id.answer);
        scoreT.setText("score: " + score);
        timerText = view.findViewById(R.id.timerText);
        newEexercise();
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (score > 4) {
                    score -= 5;
                    scoreT.setText("score: " + score);
                    answer.setText(Integer.toString(Integer.parseInt(number1.getText().toString()) + Integer.parseInt(number2.getText().toString())));
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            answer.setText("");
                        }
                    }, 3000);
                } else {
                    Toast.makeText(getActivity(), "you don't have enough score", Toast.LENGTH_SHORT).show();
                }
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answered = true;
                if (answer.getText().toString().equals(Integer.toString(Integer.parseInt(number1.getText().toString()) + Integer.parseInt(number2.getText().toString())))) {
                    if (n == 1) score += 2;
                    if (n == 2) score += 4;
                    if (n == 3) score += 6;
                    scoreT.setText("score: " + score);
                    n++;
                    if (n == 3 && stage == 4)
                        finish();
                    if (n == 3) {
                        stage++;
                        n = 1;
                    }

                    newEexercise();
                    answer.setText("");
                } else {
                    newEexercise();
                }
            }
        });

        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.is_playing = false;
                MainActivity.Home_frame.setVisibility(View.VISIBLE);
                MainActivity.level1_frame.setVisibility(View.INVISIBLE);
                MainActivity.setDataLeave(score, Nhint);
            }
        });

        return view;
    }

    public void newEexercise() {
        answered = false;
        answer.setText("");

        int a = 0, b = 0;
        if (stage == 1) {
            a = (int) (Math.random() * 10);
            b = (int) (Math.random() * 10);
        }

        if (stage == 2) {
            a = (int) (Math.random() * 90) + 10;
            b = (int) (Math.random() * 90) + 10;
        }

        if (stage == 3) {
            a = (int) (Math.random() * 900) + 100;
            b = (int) (Math.random() * 900) + 100;
        }

        number1.setText(Integer.toString(a));
        number2.setText(Integer.toString(b));

    }

    public void finish() {
        MainActivity.is_playing = false;
        MainActivity.Home_frame.setVisibility(View.VISIBLE);
        MainActivity.level1_frame.setVisibility(View.INVISIBLE);
        MainActivity.setData(score);
    }
}