package com.example.mathhero;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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

    public static void updateLevel() {
        if (player_level != 3) {
            player_level++;
            details_Fragment.level.setText("Level:    " + player_level);
            home_Fragment.leveltv.setText("level:" + player_level);
            home_Fragment.startIn.setText("Let's start in level " + player_level);
            db.collection("users").document(currentUserId).update("level", player_level);
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

        }
    }

}



