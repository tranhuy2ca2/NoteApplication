package com.example.noteapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signup extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText msignupemail, msignuppassword, mverifypassword;
    private RelativeLayout msignup;
    private TextView mgotologin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        msignupemail = findViewById(R.id.signupemail);
        msignuppassword = findViewById(R.id.signuppassword);
        mverifypassword = findViewById(R.id.confirmyourpassword);
        msignup = findViewById(R.id.signup);
        mgotologin = findViewById(R.id.backtologinpage);

        firebaseAuth = FirebaseAuth.getInstance();

        mgotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signup.this, MainActivity.class);
                startActivity(intent);
            }
        });

        msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = msignupemail.getText().toString().trim();
                String password = msignuppassword.getText().toString().trim();
                String connfirmpassword = mverifypassword.getText().toString().trim();
                if(mail.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"All field are required",Toast.LENGTH_SHORT).show();
                }else if(password.length()<7){
                    Toast.makeText(getApplicationContext(),"Password should longer than 7 character",Toast.LENGTH_SHORT).show();
                }else if(!password.equals(connfirmpassword)){
                    Toast.makeText(getApplicationContext(),"Password and confirm password not match!",Toast.LENGTH_SHORT).show();
                }else{
                    //code
                    firebaseAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Registration successfull",Toast.LENGTH_SHORT).show();
                                sendEmailVerification();
                            }else{
                                Toast.makeText(getApplicationContext(),"Failed to register",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

    }

    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(),"Verification Email is sent, Verify and Login again",Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(signup.this,MainActivity.class));
                }
            });
        }else{
            Toast.makeText(getApplicationContext(),"Failed to sent verification Email",Toast.LENGTH_SHORT).show();
        }
    }
}