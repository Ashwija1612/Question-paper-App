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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class resetpassword extends AppCompatActivity {
    EditText userpassword, userconfpass;
    Button savebtn;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);

        userpassword = findViewById(R.id.newpass);
        userconfpass = findViewById(R.id.confpassword);

        user = FirebaseAuth.getInstance().getCurrentUser();
         savebtn = findViewById(R.id.btn_save);
         savebtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(userpassword.getText().toString().isEmpty()){
                     userpassword.setError("Required field");
                     return;
                 }

                 if (userconfpass.getText().toString().isEmpty()){
                     userconfpass.setError("Required field");
                     return;
                 }

                 if (!userpassword.getText().toString().equals(userconfpass.getText().toString())){
                     userconfpass.setError("Password don't match");
                     return;
                 }
                 user.updatePassword(userpassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void aVoid) {
                         Toast.makeText(resetpassword.this, "Password changed", Toast.LENGTH_SHORT).show();
                         startActivity(new Intent(getApplicationContext(), MainActivity.class));
                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Toast.makeText(resetpassword.this, e.getMessage(),Toast.LENGTH_SHORT).show();

                     }
                 });
             }
         });
    }
}