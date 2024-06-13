package com.example.bloodcollectionandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    private EditText fullNameEditText, emailEditText, passwordEditText;
    private Button createAccountButton;
    private TextView signInTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullNameEditText = findViewById(R.id.full_name);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        createAccountButton = findViewById(R.id.create_account_button);
        signInTextView = findViewById(R.id.signInText);

        createAccountButton.setOnClickListener(view -> {
            // Handle create account logic here
        });

        signInTextView.setOnClickListener(view -> {
            // Handle sign in logic here
        });


    }
}