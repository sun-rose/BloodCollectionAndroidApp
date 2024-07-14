package com.example.bloodcollectionandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class LoginActivity extends AppCompatActivity {
    Button signInButton, twitterSignInButton, facebookSignInButton;
    TextView signUpText,forgotPasswordText;
    EditText mEmail, mPassword;
    String email, password;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();
         mEmail=findViewById(R.id.email);
         mPassword=findViewById(R.id.password);
         signInButton = findViewById(R.id.sign_in);
         twitterSignInButton = findViewById(R.id.sign_in_twitter);
         facebookSignInButton = findViewById(R.id.sign_in_facebook);
         signUpText = findViewById(R.id.sign_up);
        forgotPasswordText = findViewById(R.id.forgot_password);
        signInButton.setOnClickListener(v -> {
            ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
            if (netInfo == null) {
                Toast.makeText(LoginActivity.this, "No Internet", Toast.LENGTH_SHORT).show();

            }
            else {
                email = mEmail.getText().toString().trim();
                password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required.");
                    return;
                }
//
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required.");
                    return;
                }

                if (password.length() < 6) {
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;
                }
                // authenticate the user

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(), MainPage.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(LoginActivity.this, "Error ! " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        twitterSignInButton.setOnClickListener(v -> {
            // Handle Twitter sign in button click
        });

        facebookSignInButton.setOnClickListener(v -> {
            // Handle Facebook sign in button click
        });

        signUpText.setOnClickListener(v -> {

            Intent intent=new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
            finish();
        });


        forgotPasswordText.setOnClickListener(v -> {
            final EditText resetMail = new EditText(v.getContext());
            final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
            passwordResetDialog.setTitle("Reset Password ?");
            passwordResetDialog.setMessage("Enter Your Email To Received Reset Link.");
            passwordResetDialog.setView(resetMail);
            passwordResetDialog.setPositiveButton("Yes", (dialog, which) -> {
                // extract the email and send reset link
                String mail = resetMail.getText().toString();
                fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(aVoid -> Toast.makeText(LoginActivity.this, "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(LoginActivity.this, "Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show());

            });

            passwordResetDialog.setNegativeButton("No", (dialog, which) -> {
                // close the dialog
            });
            passwordResetDialog.create().show();

        });

    }
}