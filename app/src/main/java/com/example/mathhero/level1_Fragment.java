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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


public class level1_Fragment extends Fragment {

    private ImageView  hint, goodImage;
    private Button check, leave;
    public static TextView number1,number2,answer,scoreT,timerText;
    public static int stage, n;
    public static ImageView timeImage;

    public level1_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_level1_, container, false);
        leave = view.findViewById(R.id.leave);
        check = view.findViewById(R.id.check);
        hint = view.findViewById(R.id.hint);
        scoreT = view.findViewById(R.id.score);
        number1 = view.findViewById(R.id.number1);
        number2 = view.findViewById(R.id.number2);
        answer = view.findViewById(R.id.answer);
        timerText = view.findViewById(R.id.timerText);
        timeImage = view.findViewById(R.id.timeImage);
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.player_score > 4) {
                    countDownTimer.cancel();           // نلغي المؤقت القديم إذا كان شغال
                    timeImage.clearAnimation();        // نلغي حركة ايموجي الوقت إذا كان شغال
                    MainActivity.updateHint();
                    check.setEnabled(false);
                    hint.setEnabled(false);
                    MainActivity.updateScore(-5);
                    answer.setText(Integer.toString(Integer.parseInt(number1.getText().toString()) + Integer.parseInt(number2.getText().toString())));
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {     //مؤقت لظهور الاجابة (3 ثواني)
                        @Override
                        public void run() {
                            answer.setText("");
                            check.setEnabled(true);
                            hint.setEnabled(true);
                            newTimer(parseInt(timerText.getText().toString())+3);
                        }
                    }, 3000);
                }
                else {Toast.makeText(getActivity(), "you don't have enough score", Toast.LENGTH_SHORT).show();}
            }
        });

        goodImage = view.findViewById(R.id.goodImage);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answer.getText().toString().trim().isEmpty())
                    Toast.makeText(getActivity(), "input your answer", Toast.LENGTH_SHORT).show();
                else {
                    // إيقاف المؤقت والايموجي
                    timeImage.clearAnimation();
                    timeImage.setVisibility(View.INVISIBLE);
                    countDownTimer.cancel();

                    if (answer.getText().toString().equals(Integer.toString(parseInt(number1.getText().toString()) + parseInt(number2.getText().toString())))) {
                        goodImage.setVisibility(View.VISIBLE);
                        if (n == 1) MainActivity.updateScore(2);
                        if (n == 2) MainActivity.updateScore(4);
                        if (n == 3) MainActivity.updateScore(6);
                        n++;
                        if (n == 4 && stage == 3)
                            finish();  //لقد ختم المرحلة
                        if (n == 4) {
                            stage++;
                            n = 1;
                        }
                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {   // اضافة صورة عمل جيد لمدة ثانية ونصف
                            @Override
                            public void run() {
                                goodImage.setVisibility(View.GONE);
                                newExercise();
                            }
                        }, 1500);
                    }
                    else {              // اذا اجاب اجابة خاطئة يبدا بالمستوى نفسه من جديد n=1
                        n=1;
                        showCorrectAnswer();
                    }
                }
            }
        });

        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // إيقاف المؤقت والايموجي
                timeImage.clearAnimation();
                timeImage.setVisibility(View.INVISIBLE);
                countDownTimer.cancel();
                // عرض حوار لتاكيد الخروج
                new android.app.AlertDialog.Builder(getActivity())
                        .setTitle("Exit Game")
                        .setMessage("Are you sure you want to leave?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            MainActivity.is_playing = false;
                            MainActivity.Home_frame.setVisibility(View.VISIBLE);
                            MainActivity.level1_frame.setVisibility(View.INVISIBLE);
                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            newTimer(Integer.parseInt(timerText.getText().toString().trim())); // تشفيل العداد
                            dialog.dismiss();
                        })
                        .setCancelable(false)
                        .show();
            }
        });
        return view;
    }

    public static void newExercise() {
        int h=0;     // عدد الثواني للعداد
        if(stage==1) h=10;
        if(stage==2) h=15;
        if(stage==3) h=20;
        newTimer(h);
        answer.setText("");
        int a = 0, b = 0;
        if (stage == 1) {                        // اعداد احادية المنازل
            a = (int) (Math.random() * 10);
            b = (int) (Math.random() * 10);
        }
        if (stage == 2) {                        // اعداد ثنائية المنازل
            a = (int) (Math.random() * 90) + 10;
            b = (int) (Math.random() * 90) + 10;
        }
        if (stage == 3) {                        // عدد ثنائي المنازل وعدد ثلاثي المنازل
            a = (int) (Math.random() * 900) + 100;
            b = (int) (Math.random() * 90) + 10;
        }
        number1.setText(Integer.toString(a));
        number2.setText(Integer.toString(b));
    }

    public void finish() {                                //احتفال اختتام المرحلة وتغديل المتغيرات
        MainActivity.updateLevel(1, getContext());
        MainActivity.is_playing = false;
        MainActivity.Home_frame.setVisibility(View.VISIBLE);
        MainActivity.level1_frame.setVisibility(View.INVISIBLE);
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
                if (secondsLeft <6) {       //اذا تبقى اقل من 6 ثواني طهور ايموجي الوقت
                    timeImage.setVisibility(View.VISIBLE);
                    Animation blink = new AlphaAnimation(0.0f, 1.0f); // إنشاء تأثير وميض يغيّر الشفافية من 0 إلى 1
                    blink.setDuration(500); // تحديد مدة كل ومضة (اختفاء وظهور) بـ 500 مللي ثانية
                    blink.setStartOffset(0); // بدء الأنيميشن فورًا بدون تأخير
                    blink.setRepeatMode(Animation.REVERSE); // جعل الأنيميشن يعكس نفسه (من الظهور للاختفاء والعكس)
                    blink.setRepeatCount(Animation.INFINITE); // تكرار الأنيميشن إلى ما لا نهاية
                    timeImage.startAnimation(blink); // تطبيق تأثير الوميض على العنصر timeImage
                }
            }
            public void onFinish() {          //عند انتهاء الوقت وعدم الاجابة
                timeImage.clearAnimation();
                timeImage.setVisibility(View.INVISIBLE);
                // المستخدم ما جاوب => تمرين جديد
                new Handler(Looper.getMainLooper()).postDelayed(() -> newExercise(), 500);
            }
        }.start();
    }

    public void showCorrectAnswer() {
        MainActivity.false_Answer.start();
        new AlertDialog.Builder(getActivity())
                .setTitle("Correct Answer")
                .setMessage(number1.getText().toString() + " + " + number2.getText().toString() + " = "
                        + (parseInt(number1.getText().toString()) + parseInt(number2.getText().toString())))
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

