package com.example.healthworker;

import android.content.Intent;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    ProgressBar progressBar;
    EditText regEmail, regPass;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regEmail = (EditText) findViewById(R.id.regEmail);
        regPass  = (EditText) findViewById(R.id.regPass);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.regSignUp).setOnClickListener(this);
    }

    public void registerUser(){
        String email = regEmail.getText().toString().trim();
        String password = regPass.getText().toString().trim();

        if(email.isEmpty()){
            regEmail.setError("Email is required!!");
            regEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            regEmail.setError("Please enter a valid email");
            regEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            regPass.setError("Password is required!!");
            regEmail.requestFocus();
            return;
        }

        if(password.length()<6){
            regPass.setError("Minimum length of password should be 6");
            regPass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Some error occured", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.regSignUp:
                registerUser();
                break;

            case R.id.regLogin:
                startActivity(new Intent(this, LoginActivity.class));
                break;

        }
    }
}
