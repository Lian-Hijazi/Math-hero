package com.example.mathhero;
import android.Manifest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import android.view.animation.LinearInterpolator;

import java.util.Random;


public class MainActivity extends AppCompatActivity {


    public static FrameLayout Home_frame, Login_frame, sign_frame, details_frame, rules_frame;
    public static home_Fragment homeFrag;
    public static loginFrag loginfrag;
    public static details_Fragment detailsFrag;
    public static Rules_Fragment rulesFrag;
    public static boolean is_playing, isStart;

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static FrameLayout level1_frame, level2_frame, level3_frame, level4_frame;
    public static level1_Fragment level1Frag;
    public static level2_Fragment level2Frag;
    public static level3_Fragment level3Frag;
    public static level4_Fragment level4Frag;

    public static int player_level, player_score, player_hint;
    public static String currentUserId;


    public static signUpFrag signUpFrag;
    private BottomNavigationView bottom_navigation;
    public static boolean isLog;
    private Button start;

    public static FrameLayout partyLayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        isStart = false;
        is_playing = false;
        start = findViewById(R.id.start);
        Home_frame = findViewById(R.id.home_Frame);
        Login_frame = findViewById(R.id.login_Frame);
        details_frame = findViewById(R.id.details_Frame);
        sign_frame = findViewById(R.id.signUp_Frame);
        rules_frame = findViewById(R.id.rules_Frame);

        //levels frames
        level1_frame = findViewById(R.id.level1_Frame);
        level2_frame = findViewById(R.id.level2_Frame);
        level3_frame = findViewById(R.id.level3_Frame);
        level4_frame = findViewById(R.id.level4_Frame);

        partyLayer = findViewById(R.id.partyLayer);

        bottom_navigation = findViewById(R.id.bottom_navigation);
        startFragment();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.setVisibility(View.INVISIBLE);
                Home_frame.setVisibility(View.VISIBLE);
                isStart = true;
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("game_channel", "Game Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

    }

    public void startFragment() {
        homeFrag = new home_Fragment();
        loginfrag = new loginFrag();
        signUpFrag = new signUpFrag();
        detailsFrag = new details_Fragment();
        rulesFrag = new Rules_Fragment();
        getSupportFragmentManager().beginTransaction().add(R.id.rules_Frame, rulesFrag).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.details_Frame, detailsFrag).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.home_Frame, homeFrag).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.login_Frame, loginfrag).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.signUp_Frame, signUpFrag).commit();
        //levels
        level1Frag = new level1_Fragment();
        level2Frag = new level2_Fragment();
        level3Frag = new level3_Fragment();
        level4Frag = new level4_Fragment();
        getSupportFragmentManager().beginTransaction().add(R.id.level1_Frame, level1Frag).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.level2_Frame, level2Frag).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.level3_Frame, level3Frag).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.level4_Frame, level4Frag).commit();
        //hide other fragments

        details_frame.setVisibility(View.INVISIBLE);
        sign_frame.setVisibility(View.INVISIBLE);
        rules_frame.setVisibility(View.INVISIBLE);

        //hide levels fragment
        level1_frame.setVisibility(View.INVISIBLE);
        level2_frame.setVisibility(View.INVISIBLE);
        level3_frame.setVisibility(View.INVISIBLE);
        level4_frame.setVisibility(View.INVISIBLE);

        Home_frame.setVisibility(View.INVISIBLE);
        Login_frame.setVisibility(View.VISIBLE);

        //set up navigation View listener
        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_home && is_playing != true && isLog && isStart) {
                    Home_frame.setVisibility(View.VISIBLE);
                    Login_frame.setVisibility(View.INVISIBLE);
                    sign_frame.setVisibility(View.INVISIBLE);
                    details_frame.setVisibility(View.INVISIBLE);
                    rules_frame.setVisibility(View.INVISIBLE);
                }

                if (item.getItemId() == R.id.menu_login && is_playing != true && !isLog && isStart) {
                    Home_frame.setVisibility(View.INVISIBLE);
                    Login_frame.setVisibility(View.VISIBLE);
                    sign_frame.setVisibility(View.INVISIBLE);
                    details_frame.setVisibility(View.INVISIBLE);
                    rules_frame.setVisibility(View.INVISIBLE);

                }

                if (item.getItemId() == R.id.menu_details && is_playing != true && isLog && isStart) {
                    Home_frame.setVisibility(View.INVISIBLE);
                    Login_frame.setVisibility(View.INVISIBLE);
                    sign_frame.setVisibility(View.INVISIBLE);
                    details_frame.setVisibility(View.VISIBLE);
                    rules_frame.setVisibility(View.INVISIBLE);

                }

                if (item.getItemId() == R.id.menu_rules && is_playing != true && isStart) {
                    Home_frame.setVisibility(View.INVISIBLE);
                    Login_frame.setVisibility(View.INVISIBLE);
                    sign_frame.setVisibility(View.INVISIBLE);
                    details_frame.setVisibility(View.INVISIBLE);
                    rules_frame.setVisibility(View.VISIBLE);
                }

                return true;
            }
        });
    }

    public static void gitUserid(String id, OnUserDataLoaded callback) {
        currentUserId = id;

        if (currentUserId != null && !currentUserId.isEmpty()) {
            if (callback != null) {
                callback.onLoaded();  // تنفيذ callback لتنفيذ startData() بعد التأكد من currentUserId
            }
        } else {
            Log.e("MainActivity", "User ID is null or empty.");
        }
    }

    public interface OnUserDataLoaded {
        void onLoaded();
    }

    public static void startData(OnUserDataLoaded callback) {
        DocumentReference docRef = db.collection("users").document(currentUserId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    User user = documentSnapshot.toObject(User.class);
                    if (user != null) {
                        player_score = user.getScore();
                        player_level = user.getLevel();
                        player_hint = user.getHint();
                        Log.d("UserData", "Score: " + player_score + ", Level: " + player_level + ", Hint: " + player_hint);
                        if (callback != null) {
                            callback.onLoaded();
                        }
                        //input home values
                        home_Fragment.scoretv.setText("score:" + player_score);
                        home_Fragment.leveltv.setText("level:" + player_level);
                        home_Fragment.startIn.setText("Let's start in level " + player_level);
                        //input details values
                        details_Fragment.name.setText("Name:   " + user.getName());
                        details_Fragment.user.setText("User Name:    " + user.getUserName());
                        details_Fragment.phone.setText("Phone:    " + user.getPhone());
                        details_Fragment.age.setText("Age:    " + user.getAge());
                        details_Fragment.hints.setText("Number of Hints:    " + user.getHint());
                        details_Fragment.level.setText("Level:    " + user.getLevel());
                        details_Fragment.score.setText("Score:    " + user.getScore());
                        details_Fragment.email.setText("Email:    " + db.collection("users").document(currentUserId));
                        //input levels scores values
                        level1_Fragment.scoreT.setText("score: " + player_score);
                        level2_Fragment.scoreT.setText("score: " + player_score);
                        level3_Fragment.scoreT.setText("score: " + player_score);
                        level4_Fragment.scoreT.setText("score: " + player_score);
                    }
                }
            }
        });
    }

    public static void updateHint() {
        player_hint++;
        details_Fragment.hints.setText("Number of Hints:    " + player_hint);
    }

    public static void updateScore(int player_score) {
        MainActivity.player_score = player_score;
        level1_Fragment.scoreT.setText("score: " + player_score);
        level2_Fragment.scoreT.setText("score: " + player_score);
        level3_Fragment.scoreT.setText("score: " + player_score);
        level4_Fragment.scoreT.setText("score: " + player_score);
        details_Fragment.score.setText("Score:    " + player_score);
        home_Fragment.scoretv.setText("score:" + player_score);
        db.collection("users").document(currentUserId).update("score", player_score);
    }

    public static void updateLevel(int level, Context context) {
        if (player_level != 4) {
            if (player_level == level) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("🎉 Well Done!");
                builder.setMessage("You completed the level successfully! Keep going!");

                builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        player_level++;
                        details_Fragment.level.setText("Level:    " + player_level);
                        home_Fragment.leveltv.setText("level:" + player_level);
                        home_Fragment.startIn.setText("Let's start in level " + player_level);
                        db.collection("users").document(currentUserId).update("level", player_level);
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        } else {
            player_level = 1;
            player_score = 5;
            player_hint = 0;
            level1_Fragment.scoreT.setText("score: " + player_score);
            level2_Fragment.scoreT.setText("score: " + player_score);
            level3_Fragment.scoreT.setText("score: " + player_score);
            level4_Fragment.scoreT.setText("score: " + player_score);
            details_Fragment.hints.setText("Number of Hints:    " + player_hint);
            details_Fragment.level.setText("Level:    " + player_level);
            details_Fragment.score.setText("Score:    " + player_score);
            home_Fragment.scoretv.setText("score:" + player_score);
            home_Fragment.leveltv.setText("level:" + player_level);
            home_Fragment.startIn.setText("Let's start in level " + player_level);
            db.collection("users").document(currentUserId).update("level", player_level);
            db.collection("users").document(currentUserId).update("score", player_score);
            db.collection("users").document(currentUserId).update("hint", player_hint);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Well done!");
            builder.setMessage("All respect! You have completed the game and were a math hero. Now, let's start from the beginning to become even stronger!");
            builder.setPositiveButton("Start Again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setCancelable(false); // لمنع إغلاق الـ Dialog من الخارج
            builder.show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // تحقق إذا كان يمكن جدولة المنبهات الدقيقة (من خلال AlarmManager غير ثابت)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (getSystemService(AlarmManager.class).canScheduleExactAlarms()) {
                // ضبط الوقت الذي يجب أن يمر قبل أن يُرسل الإشعار (دقيقة)
                long triggerAtMillis = System.currentTimeMillis() + 60000; // 60,000 ميلي ثانية = دقيقة
                scheduleAlarm(triggerAtMillis);
            }
        } else {
            // في الإصدارات القديمة، مباشرة قم بجدولة المنبهات
            long triggerAtMillis = System.currentTimeMillis() + 60000;
            scheduleAlarm(triggerAtMillis);
        }
    }

    private void scheduleAlarm(long triggerAtMillis) {
        // إنشاء الـ Intent و PendingIntent
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // الحصول على الـ AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // تعيين المنبه ليُرسل الإشعار بعد دقيقة
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);

        Log.d("MainActivity", "Alarm set for 1 minute from now: " + triggerAtMillis);
    }


    @Override
    protected void onResume() {
        super.onResume();

        // إلغاء المنبه عند العودة للنشاط
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent); // إلغاء المنبه عند العودة للنشاط
    }

    public static void party(Context context) {
            // عدد البالونات بناءً على العرض
            int balloonWidth = 200; // عرض البالونة
            int balloonSpacing = 50; // المسافة بين البالونات
            int screenWidth = partyLayer.getWidth(); // عرض الشاشة

            // حساب عدد البالونات الممكنة بالعرض
            int numBalloons = (screenWidth - balloonSpacing) / (balloonWidth + balloonSpacing);

            // التأكد من قياسات الشاشة داخل partyLayer
            if (partyLayer == null) {
                Toast.makeText(context, "Party layer not found!", Toast.LENGTH_SHORT).show();
                return;
            }

            // التأكد من قياسات الشاشة بعد التحقق من الـ Layout
            partyLayer.post(() -> {
                int screenHeight = partyLayer.getHeight();

                // الحصول على الارتفاع المخصص للـ BottomNavigation
                int bottomNavHeight = context.getResources().getDimensionPixelSize(R.dimen.design_bottom_navigation_height);

                // المساحة المتاحة في الشاشة بدون الـ BottomNavigation
                int availableHeight = screenHeight - bottomNavHeight;

                // إضافة البالونات
                for (int i = 0; i < numBalloons; i++) {
                    ImageView balloon = new ImageView(context);
                    balloon.setImageResource(R.drawable.balloon); // تأكد من وجود صورة بالونة في drawable

                    // تكبير حجم البالونات
                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(balloonWidth + 200, 500); // حجم أكبر للبالونات

                    // تحديد موقع البالونة داخل النطاق المحدد (توزيع البالونات بشكل مرتب)
                    int xPosition = i * (balloonWidth + balloonSpacing); // حساب المسافة بين البالونات
                    int yPosition = availableHeight; // البداية من أسفل الشاشة بدون الـ BottomNavigation

                    params.leftMargin = xPosition;
                    params.topMargin = yPosition;

                    balloon.setLayoutParams(params);

                    // إضافة البالونة فورًا بعد تحديث الـ Layout
                    partyLayer.addView(balloon);
                    partyLayer.invalidate();

                    // تحريك البالونة للأعلى مع تسريع الحركة لتكون أكثر سلاسة
                    ObjectAnimator animator = ObjectAnimator.ofFloat(balloon, "translationY", yPosition, -screenHeight); // تحريك إلى أعلى الشاشة
                    animator.setDuration(5000); // مدة التحريك (5 ثواني)
                    animator.setInterpolator(new LinearInterpolator()); // جعل الحركة أكثر سلاسة
                    animator.start();
                    // لما تخلص الأنيميشن، امسح البالون
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            partyLayer.removeView(balloon);
                        }
                    });
                    animator.start();
                }
            });
    }

    public static void startPartyTimes(Context context) {
        Handler handler = new Handler();
        for (int i = 0; i < 5; i++) {
            int delay = i * 1000; // كل 6 ثواني
            handler.postDelayed(() -> party(context), delay);
        }
    }

}



