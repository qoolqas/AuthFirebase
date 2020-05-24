package com.qoolqas.authfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    TextInputLayout email, password,password2;
    private FirebaseAuth mAuth;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.registerName);
        password = findViewById(R.id.registerPassword);
        register = findViewById(R.id.registerButton);
        password2 = findViewById(R.id.registerPassword2);

        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!validateForm()) {
                    return;
                }
                String email2 = email.getEditText().getText().toString();
                String password3 = password.getEditText().getText().toString();
                mAuth.createUserWithEmailAndPassword(email2, password3)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(String.valueOf(task), "signIn:onComplete:" + task.isSuccessful());
                                //hideProgressDialog();

                                if (task.isSuccessful()) {
                                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                } else {

                                    Toast.makeText(RegisterActivity.this, "Register Failed",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }
    public boolean isValidPassword(String s) {
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "[a-zA-Z0-9\\!\\@\\#\\$]{8,24}");

        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
    }
    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(email.getEditText().getText().toString())||email.getEditText().length() < 6) {
            email.setError("Periksa Kembali Email Anda Minimal 6 huruf");
            result = false;
        } else {
            email.setError(null);
        }

        if (TextUtils.isEmpty(password.getEditText().getText().toString())||password.getEditText().length() < 6) {
            password.setError("Password Minimal 6 huruf dan Terdapat Huruf dan Angka");
            result = false;
        } else {
            password.setError(null);
        }

        if (TextUtils.isEmpty(password2.getEditText().getText().toString())||password2.getEditText().length() < 6) {
            if (!password2.equals(password))
            {
                Toast.makeText(RegisterActivity.this, "Password do not match", Toast.LENGTH_SHORT).show();
            }
            password2.setError("Periksa Kembali Password Anda Minimal 6 huruf");
            result = false;
        } else {
            password2.setError(null);
        }

        return result;
    }
}
