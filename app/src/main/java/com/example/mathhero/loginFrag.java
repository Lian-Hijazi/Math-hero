package com.example.mathhero;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class loginFrag extends Fragment {
    private TextInputEditText et_email, et_password;
    private Button btn_submit, signBtn;
    private FirebaseAuth mAuth;
    private ImageView logo,imageView;
    private FirebaseUser currentUser;



    public loginFrag() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        mAuth=FirebaseAuth.getInstance();
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_login_, container, false);
        logo=view.findViewById(R.id.logo);
        imageView=view.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.math);
        logo.setImageResource(R.drawable.logo);
        signBtn=view.findViewById(R.id.signButton2);
        et_email=view.findViewById(R.id.et_email);
        et_password=view.findViewById(R.id.et_password);
        btn_submit=view.findViewById(R.id.submitButton);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmailPass();
            }
        });

        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.Home_frame.setVisibility(View.INVISIBLE);
                MainActivity.Login_frame.setVisibility(View.INVISIBLE);
                MainActivity.sign_frame.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    @SuppressLint("SuspiciousIndentation")

    public void checkEmailPass(){
        String email,password;
        email=et_email.getText().toString();
        password=et_password.getText().toString();
        if(!(email.equals("")||password.equals(""))) {
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getActivity(), "login seccessful", Toast.LENGTH_SHORT).show();
                        MainActivity.Home_frame.setVisibility(View.VISIBLE);
                        MainActivity.Login_frame.setVisibility(View.INVISIBLE);
                        MainActivity.sign_frame.setVisibility(View.INVISIBLE);
                        MainActivity.isLog=true;
                        currentUser= mAuth.getCurrentUser();
                        MainActivity.gitUserid(currentUser.getUid());

                        MainActivity.startData(() -> {
                            // ✅ لما تجهز البيانات من Firebase
                            MainActivity.Home_frame.setVisibility(View.VISIBLE);
                            MainActivity.Login_frame.setVisibility(View.INVISIBLE);
                            MainActivity.sign_frame.setVisibility(View.INVISIBLE);
                            MainActivity.isLog = true;

                            et_email.setText("");
                            et_password.setText("");
                        });
                    }
                    else
                        Toast.makeText(getActivity(), "login failed", Toast.LENGTH_SHORT).show();

                }
            });
        }
        else {
            Toast.makeText(getActivity(), "pleas fill fields", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();
        if(currentUser!=null) {
            updateUI();
            MainActivity.gitUserid(currentUser.getUid());

            MainActivity.gitUserid(currentUser.getUid());
            MainActivity.startData(() -> {
                // ✅ لما تجهز البيانات من Firebase
                MainActivity.Home_frame.setVisibility(View.VISIBLE);
                MainActivity.Login_frame.setVisibility(View.INVISIBLE);
                MainActivity.sign_frame.setVisibility(View.INVISIBLE);
                MainActivity.isLog = true;

            });
        }

    }

    public void updateUI(){
        MainActivity.isLog=true;
        MainActivity.Home_frame.setVisibility(View.VISIBLE);
        MainActivity.Login_frame.setVisibility(View.INVISIBLE);
    }
}
