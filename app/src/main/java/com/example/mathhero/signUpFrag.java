package com.example.mathhero;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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
import com.google.firebase.firestore.FirebaseFirestore;

public class signUpFrag extends Fragment {
    private FirebaseUser currentUser;
    private Button signBtn;
    private TextInputEditText user,name,phone,email,password,confirmPassword,age;
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    private ImageView imageView,logo;

    public signUpFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_sign_up_, container, false);
        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        logo=view.findViewById(R.id.logo);
        imageView=view.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.math);
        logo.setImageResource(R.drawable.logo);
        signBtn=view.findViewById(R.id.signButton1);
        user=view.findViewById(R.id.et_user);
        name=view.findViewById(R.id.et_name);
        phone=view.findViewById(R.id.et_phone);
        email=view.findViewById(R.id.et_email);
        age=view.findViewById(R.id.age);
        password=view.findViewById(R.id.et_password);
        confirmPassword=view.findViewById(R.id.et_confirm);
        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result =true;
                String finalToast="There is an error in:";
                if(!checkPhone()) {
                    finalToast += "phone, ";
                    result = false;
                }
                if(!checkEmail()) {
                    finalToast += "email, ";
                    email.setError("Invaild Email");
                    email.requestFocus();
                    result = false;
                }
                if(!checkName()) {
                        finalToast += "name, ";
                        result = false;
                }
                if(!checkUser()) {
                    finalToast += "userName, ";
                    result = false;
                }

                if(Integer.parseInt(age.getText().toString())<10) {
                    finalToast += "age, ";
                    result = false;
                }

                if(!checkPassword()) {
                    finalToast += "password, ";
                    result = false;
                }

                if(!(password.getText().toString().equals(confirmPassword.getText().toString()))) {
                    finalToast += "confirm Password,";
                    result = false;
                }

                if(!result)
                    Toast.makeText(getActivity(), finalToast, Toast.LENGTH_SHORT).show();

                if(result){
                    SignUp();
                }

            }
        });
        return view;
    }

    private void SignUp() {
        String email2=email.getText().toString();
        String password2=password.getText().toString();
        mAuth.createUserWithEmailAndPassword(email2,password2)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(), "signUp seccessful", Toast.LENGTH_SHORT).show();
                            currentUser= mAuth.getCurrentUser();
                            addUserToFireStore(currentUser.getUid());
                            startlogin();
                            clearFields();

                        }
                        else {
                            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void clearFields(){
        user.setText("");
        name.setText("");
        phone.setText("");
        email.setText("");
        password.setText("");
        confirmPassword.setText("");
        age.setText("");
    }

   public void startlogin(){
        MainActivity.sign_frame.setVisibility(View.INVISIBLE);
        MainActivity.Login_frame.setVisibility(View.VISIBLE);
        MainActivity.Home_frame.setVisibility(View.INVISIBLE);
   }

    public void addUserToFireStore( String userId){
        User user1=new User(name.getText().toString(),user.getText().toString(),phone.getText().toString(),age.getText().toString());
        db.collection("users").document(userId).set(user1) ;
    }

    public boolean checkUser(){
        return (!user.getText().equals("null"));
    }

    public boolean checkName(){
        return (!name.getText().equals("null"))&&(name.getText().length()>=4);
    }

    public boolean checkPhone(){
        char[] p=phone.getText().toString().toCharArray();
        for (int n=0;n<phone.getText().length();n++){
            if(!Character.isDigit(p[n]))
                return false;
        }
        return phone.getText().length()==10;
    }

    public boolean checkEmail(){
        return Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches();
    }

    public boolean checkPassword(){
        char[] pass=password.getText().toString().toCharArray();
        int nums=0,Capetal=0,small=0;
        for (int i=0;i<pass.length;i++){
            if(Character.isDigit(pass[i]))
                nums++;
            if(Character.isUpperCase(pass[i]))
                Capetal++;
            if(Character.isLowerCase(pass[i]))
                small++;

        }
        return ((pass.length>=6)&&nums>0&&Capetal>0&&small>0&&nums+Capetal+small==pass.length);

    }

}