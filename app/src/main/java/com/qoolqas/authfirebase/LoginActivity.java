package com.qoolqas.authfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout email, password;
    TextView register;
    Button login;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        register = findViewById(R.id.txtRegister);
        login = findViewById(R.id.loginButton);
        email = findViewById(R.id.loginEtEmail);
        password = findViewById(R.id.loginEtPassword);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateName() || validatePassword()) {
                    mAuth.signInWithEmailAndPassword(email.getEditText().getText().toString(), password.getEditText().getText().toString())
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(String.valueOf(task), "signIn:onComplete:" + task.isSuccessful());
                                    //hideProgressDialog();

                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Sign In Failed",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }

        });
    }

    private boolean validateName() {
        String nameV = email.getEditText().getText().toString().trim();

        if (nameV.isEmpty()) {
            email.setError("Nama tidak boleh kosong");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String pw = password.getEditText().getText().toString().trim();

        if (pw.isEmpty()) {
            password.setError("Password tidak boleh kosong");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }
}
