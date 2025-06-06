package com.example.mathhero;

import static com.example.mathhero.MainActivity.gitUserid;
import static com.example.mathhero.MainActivity.startData;

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
    private FirebaseUser currentUser;

    public loginFrag() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        mAuth=FirebaseAuth.getInstance();
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_login_, container, false);
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
                        MainActivity.Login_frame.setVisibility(View.INVISIBLE);
                        MainActivity.sign_frame.setVisibility(View.INVISIBLE);
                        MainActivity.isLog=true;
                        currentUser= mAuth.getCurrentUser();
                        gitUserid(currentUser.getUid(),null);
                        MainActivity.Login_frame.setVisibility(View.INVISIBLE);
                        startData(() -> {
                            // ✅ لما تجهز البيانات من Firebase
                            MainActivity.Login_frame.setVisibility(View.INVISIBLE);
                            MainActivity.sign_frame.setVisibility(View.INVISIBLE);
                            MainActivity.isLog = true;
                            MainActivity.start.setVisibility(View.VISIBLE);
                            et_email.setText("");
                            et_password.setText("");
                        });
                    }
                    else Toast.makeText(getActivity(), "login failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else Toast.makeText(getActivity(), "pleas fill fields", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI();
            gitUserid(currentUser.getUid(), new MainActivity.OnUserDataLoaded() {
                @Override
                public void onLoaded() {
                    // الآن بعد التأكد من أن currentUserId تم تعيينه، يمكن استدعاء startData()
                    startData(() -> {
                        MainActivity.Login_frame.setVisibility(View.INVISIBLE);
                        MainActivity.sign_frame.setVisibility(View.INVISIBLE);
                        MainActivity.isLog = true;
                        MainActivity.start.setVisibility(View.VISIBLE);
                    });
                }
            });
        }
    }
    public void updateUI(){
        MainActivity.isLog=true;
        MainActivity.Login_frame.setVisibility(View.INVISIBLE);
    }
}
