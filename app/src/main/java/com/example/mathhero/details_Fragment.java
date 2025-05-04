package com.example.mathhero;

import static com.example.mathhero.MainActivity.currentUserId;
import static com.example.mathhero.MainActivity.db;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class details_Fragment extends Fragment {
    private FloatingActionButton btnlogout;
    public static TextView name,user,phone,age,email,level,score,hints;

    public details_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_details_, container, false);
        btnlogout=view.findViewById(R.id.btn_logout);

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        name=view.findViewById(R.id.textViewName);
        user=view.findViewById(R.id.textViewUserName);
        phone=view.findViewById(R.id.textViewPhone);
        age=view.findViewById(R.id.textViewAge);
        email=view.findViewById(R.id.textViewEmail);
        level=view.findViewById(R.id.textViewLevel);
        score=view.findViewById(R.id.textViewScore);
        hints=view.findViewById(R.id.textViewHints);
        return view;
    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        MainActivity.isLog=false;
        MainActivity.Login_frame.setVisibility(View.VISIBLE);
        MainActivity.details_frame.setVisibility(View.INVISIBLE);
    }
}