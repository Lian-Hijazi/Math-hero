package com.example.mathhero;

import android.os.Bundle;
import android.os.CountDownTimer;
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
    private int score;
    private TextView number1, number2, answer;
    public static TextView scoreT;
    private int stage, n;
    public static TextView timerText;
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
        think_img = view.findViewById(R.id.think_img);
        think_img.setImageResource(R.drawable.help);
        leave = view.findViewById(R.id.leave);
        check = view.findViewById(R.id.check);
        hint = view.findViewById(R.id.hint);
        hint.setImageResource(R.drawable.hint);
        scoreT = view.findViewById(R.id.score);
        number1 = view.findViewById(R.id.number1);
        number2 = view.findViewById(R.id.number2);
        answer = view.findViewById(R.id.answer);
        timerText = view.findViewById(R.id.timerText);
        newEexercise();
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.updateHint();
                score = Integer.parseInt(scoreT.getText().toString().substring(7));
                if (score > 4) {
                    score -= 5;
                    scoreT.setText("score: " + score);
                    answer.setText(Integer.toString(Integer.parseInt(number1.getText().toString()) + Integer.parseInt(number2.getText().toString())));
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            answer.setText("");
                        }
                    }, 2000);
                } else {
                    Toast.makeText(getActivity(), "you don't have enough score", Toast.LENGTH_SHORT).show();
                }
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answer.getText().toString().trim().isEmpty())
                    Toast.makeText(getActivity(), "input your answer", Toast.LENGTH_SHORT).show();
                else {
                    score = Integer.parseInt(scoreT.getText().toString().substring(7));
                    if (answer.getText().toString().equals(Integer.toString(Integer.parseInt(number1.getText().toString()) + Integer.parseInt(number2.getText().toString())))) {
                        if (n == 1) score += 2;
                        if (n == 2) score += 4;
                        if (n == 3) score += 6;
                        n++;
                        if (n == 3 && stage == 3)
                            finish();
                        if (n == 3) {
                            stage++;
                            n = 1;
                        }
                    }
                    MainActivity.updateScore(score);
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
            }
        });

        return view;
    }

    public void newEexercise() {
        newTimer(stage);
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
            b = (int) (Math.random() * 90) + 10;
        }

        number1.setText(Integer.toString(a));
        number2.setText(Integer.toString(b));

    }


    public void finish() {
        MainActivity.updateLevel();
        MainActivity.is_playing = false;
        MainActivity.Home_frame.setVisibility(View.VISIBLE);
        MainActivity.level1_frame.setVisibility(View.INVISIBLE);
    }

    private static CountDownTimer countDownTimer; // عشان نقدر نوقف المؤقت القديم إذا احتجنا

    public static void newTimer(int stage) {

        // نحدد الوقت حسب قيمة المرحلة
        int timeInSeconds;
        switch (stage) {
            case 1:
                timeInSeconds = 5;
                break;
            case 2:
                timeInSeconds = 10;
                break;
            case 3:
                timeInSeconds = 15;
                break;
            default:
                timeInSeconds = 0;
                break;
        }

        // نلغي المؤقت القديم إذا كان شغال
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        // نبدأ مؤقت جديد
        countDownTimer = new CountDownTimer(timeInSeconds * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                int secondsLeft = (int) (millisUntilFinished / 1000);
                timerText.setText(String.valueOf(secondsLeft));
            }

            public void onFinish() {
                timerText.setText("0");
            }
        }.start();
    }
}

