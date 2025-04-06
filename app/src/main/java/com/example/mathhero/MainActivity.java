package com.example.mathhero;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {



    public static FrameLayout Home_frame,Login_frame,sign_frame,details_frame,rules_frame;
    public static home_Fragment homeFrag;
    public static loginFrag loginfrag;
    public static details_Fragment detailsFrag;
    public static Rules_Fragment rulesFrag;
    public static boolean is_playing;

   public static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static FrameLayout level1_frame,level2_frame,level3_frame,level4_frame;
    public static level1_Fragment level1Frag;
    public static level2_Fragment level2Frag;
    public static level3_Fragment level3Frag;
    public static level4_Fragment level4Frag;

    public static int player_level, player_score ,player_hint;
    public static String currentUserId;


    public static signUpFrag signUpFrag;
    private BottomNavigationView bottom_navigation;
    public static boolean isLog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        is_playing=false;

        Home_frame=findViewById(R.id.home_Frame);
        Login_frame=findViewById(R.id.login_Frame);
        details_frame=findViewById(R.id.details_Frame);
        sign_frame=findViewById(R.id.signUp_Frame);
        rules_frame=findViewById(R.id.rules_Frame);

        //levels frames
        level1_frame=findViewById(R.id.level1_Frame);
        level2_frame=findViewById(R.id.level2_Frame);
        level3_frame=findViewById(R.id.level3_Frame);
        level4_frame=findViewById(R.id.level4_Frame);

        bottom_navigation=findViewById(R.id.bottom_navigation);
        startFragment();


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
                    if (item.getItemId() == R.id.menu_home && is_playing != true && isLog) {
                        Home_frame.setVisibility(View.VISIBLE);
                        Login_frame.setVisibility(View.INVISIBLE);
                        sign_frame.setVisibility(View.INVISIBLE);
                        details_frame.setVisibility(View.INVISIBLE);
                        rules_frame.setVisibility(View.INVISIBLE);
                    }

                    if (item.getItemId() == R.id.menu_login && is_playing != true && !isLog) {
                        Home_frame.setVisibility(View.INVISIBLE);
                        Login_frame.setVisibility(View.VISIBLE);
                        sign_frame.setVisibility(View.INVISIBLE);
                        details_frame.setVisibility(View.INVISIBLE);
                        rules_frame.setVisibility(View.INVISIBLE);

                    }

                    if (item.getItemId() == R.id.menu_details && is_playing != true && isLog) {
                        Home_frame.setVisibility(View.INVISIBLE);
                        Login_frame.setVisibility(View.INVISIBLE);
                        sign_frame.setVisibility(View.INVISIBLE);
                        details_frame.setVisibility(View.VISIBLE);
                        rules_frame.setVisibility(View.INVISIBLE);

                    }

                    if (item.getItemId() == R.id.menu_rules && is_playing != true) {
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

    public static void gitUserid(String id){
        currentUserId = id;
    }


    public interface OnUserDataLoaded {
        void onLoaded();
    }

    public static void startData(OnUserDataLoaded listener) {
        if (currentUserId != null) {
            DocumentReference docRef = db.collection("users").document(currentUserId);
            docRef.get().addOnSuccessListener(documentSnapshot -> {
                User user = documentSnapshot.toObject(User.class);
                if (user != null) {
                    player_level = user.getLevel();
                    player_score = user.getScore();
                    player_hint = user.getHint();

                    if (listener != null) {
                        listener.onLoaded(); // 🔥 هون بنستدعي الكود اللي بعد ما توصل البيانات
                    }
                }
            });
        }

    }


    public static void setData(int score){
        player_score=score;
        player_level++;
        if(player_level<5) {
            DocumentReference docRef = db.collection("users").document(currentUserId);
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User user = documentSnapshot.toObject(User.class);
                    user.setLevel(player_level);
                    user.setScore(player_score);
                    user.setHint(player_hint);
                    db.collection("users").document(currentUserId).set(user);
                }
            });
        }
        else{
            DocumentReference docRef = db.collection("users").document(currentUserId);
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User user = documentSnapshot.toObject(User.class);
                    user.setLevel(1);
                    user.setScore(5);
                    user.setHint(0);
                    db.collection("users").document(currentUserId).set(user);
                }
            });
        }
    }

    public static void setDataLeave(int score,int hint) {
        player_score = score;
        player_hint+=hint;
        DocumentReference docRef = db.collection("users").document(currentUserId);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                user.setScore(player_score);
                user.setHint(player_hint);
                db.collection("users").document(currentUserId).set(user);
            }
        });
    }



}