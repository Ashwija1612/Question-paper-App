package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class Register extends AppCompatActivity {
    EditText regFullName, regEmail, regPassword, regConfpass;
    Button regUserbtn, gotlogin;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        regFullName = findViewById(R.id.regfullname);
        regEmail = findViewById(R.id.regemail);
        regPassword = findViewById(R.id.regpassword);
        regConfpass = findViewById(R.id.regrepassword);
        regUserbtn = findViewById(R.id.btn_register);
        gotlogin = findViewById(R.id.btn_login);

        fAuth = FirebaseAuth.getInstance();

        gotlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });
        regUserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //extract the data from the form
                String fullName = regFullName.getText().toString();
                String email = regEmail.getText().toString();
                String password = regPassword.getText().toString();
                String repass = regConfpass.getText().toString();

                if (fullName.isEmpty()){
                    regFullName.setError("Full name is required");
                    return;
                }
                if (email.isEmpty()){
                    regEmail.setError("Email is required");
                    return;
                }
                if (password.isEmpty()){
                    regPassword.setError("Password is required");
                    return;
                }
                if (repass.isEmpty()){
                    regConfpass.setError("Re enter the password is required");
                    return;
                }

                if (!password.equals(repass)){
                    regConfpass.setError("Password don't match");
                }

                Toast.makeText(Register.this, "Data Validation", Toast.LENGTH_SHORT).show();

                fAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
     }
}