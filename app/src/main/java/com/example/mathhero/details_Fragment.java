package com.example.mathhero;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class details_Fragment extends Fragment {
    private ImageView logo,img;
    private FloatingActionButton btnlogout;


    public details_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_details_, container, false);
        logo = view.findViewById(R.id.logo);
        logo.setImageResource(R.drawable.logo);
        img=view.findViewById(R.id.imageView);
        img.setImageResource(R.drawable.math);
        btnlogout=view.findViewById(R.id.btn_logout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
            });

        return view;
    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        MainActivity.isLog=false;
        MainActivity.Login_frame.setVisibility(View.VISIBLE);
        MainActivity.details_frame.setVisibility(View.INVISIBLE);
    }
}