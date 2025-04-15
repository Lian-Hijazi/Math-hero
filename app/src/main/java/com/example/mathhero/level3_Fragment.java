package com.example.mathhero;

import static java.lang.Integer.parseInt;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class level3_Fragment extends Fragment {

    private ImageView think_img,hint;
    private Button check,leave;
    private int score;
    private static TextView  number1;
    private static TextView number2;
    private static TextView answer;
    public static TextView scoreT;
    public static int stage, n;
    public static TextView timerText;
    private ImageView emojiImage;



    public level3_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_level3_, container, false);
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
        newExercise();
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check.setEnabled(false);
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                MainActivity.updateHint();
                if (MainActivity.player_score > 4) {
                    MainActivity.updateScore(-5);
                    scoreT.setText("score: " + MainActivity.player_score);
                    answer.setText(Integer.toString(Integer.parseInt(number1.getText().toString()) * Integer.parseInt(number2.getText().toString())));
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            answer.setText("");
                            check.setEnabled(true);
                            newTimer(parseInt(timerText.getText().toString()));
                        }
                    }, 2000);
                } else {
                    Toast.makeText(getActivity(), "you don't have enough score", Toast.LENGTH_SHORT).show();
                }
            }
        });

        emojiImage = view.findViewById(R.id.emojiImage);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answer.getText().toString().trim().isEmpty())
                    Toast.makeText(getActivity(), "input your answer", Toast.LENGTH_SHORT).show();
                else {
                    // إيقاف المؤقت
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                    }

                    if (answer.getText().toString().equals(Integer.toString(Integer.parseInt(number1.getText().toString()) * Integer.parseInt(number2.getText().toString())))) {
                        emojiImage.setVisibility(View.VISIBLE);
                        if (n == 1) MainActivity.updateScore(2);
                        if (n == 2) MainActivity.updateScore(4);
                        if (n == 3) MainActivity.updateScore(6);
                        n++;
                        if (n == 3 && stage == 3)
                            finish();
                        if (n == 3) {
                            stage++;
                            n = 1;
                        }
                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                emojiImage.setVisibility(View.GONE);
                                newExercise();
                            }
                        }, 1500);
                    }
                    else {
                        n=1;
                        showCorrectAnswer();
                    }
                }

            }
        });

        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Stop the timer
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }

                // Show confirmation dialog
                new android.app.AlertDialog.Builder(getActivity())
                        .setTitle("Exit Game")
                        .setMessage("Are you sure you want to leave?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            // User confirmed exit
                            MainActivity.is_playing = false;
                            MainActivity.Home_frame.setVisibility(View.VISIBLE);
                            MainActivity.level3_frame.setVisibility(View.INVISIBLE);
                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            // User canceled exit - resume timer
                            newTimer(stage);
                            dialog.dismiss();
                        })
                        .setCancelable(false)
                        .show();
            }
        });
        return view;
    }


    public static void newExercise() {
        int h=0;
        if(stage==1) h=10;
        if(stage==2) h=15;
        if(stage==3) h=20;
        newTimer(h);        answer.setText("");
        int  a = (int) (Math.random() * 10),b=0;

        if (stage == 1) b = (int) (Math.random() * 10);

        if (stage == 2) b = (int) (Math.random() * 90)+10;

        if (stage == 3) b = (int) (Math.random() * 900) + 100;

        number1.setText(Integer.toString(a));
        number2.setText(Integer.toString(b));

    }

    public void finish() {
        MainActivity.updateLevel(3, getContext());
        MainActivity.is_playing = false;
        MainActivity.Home_frame.setVisibility(View.VISIBLE);
        MainActivity.level3_frame.setVisibility(View.INVISIBLE);
        MainActivity.startPartyTimes(getContext());
    }


    static CountDownTimer countDownTimer; // عشان نقدر نوقف المؤقت القديم إذا احتجنا

    public static void newTimer(int timeInSeconds) {
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
                // المستخدم ما جاوب => تمرين جديد
                new Handler(Looper.getMainLooper()).postDelayed(() -> newExercise(), 500);
            }
        }.start();
    }
    public void showCorrectAnswer() {
        // إيقاف المؤقت
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        new AlertDialog.Builder(getActivity())
                .setTitle("Correct Answer")
                .setMessage(number1.getText().toString() + " x " + number2.getText().toString() + " = "
                        + (Integer.parseInt(number1.getText().toString()) * Integer.parseInt(number2.getText().toString())))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        // ✅ بعد الضغط OK فقط:
                        newExercise();
                    }
                })
                .setCancelable(false)
                .show();
    }

}