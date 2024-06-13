package com.example.bloodcollectionandroidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import static android.content.ContentValues.TAG;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private EditText fullNameEditText, emailEditText, passwordEditText;
    String txt_username, txt_password, txt_email, username;
    FirebaseAuth auth;
    DatabaseReference reference;
    Toolbar toolbar;
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
        auth = FirebaseAuth.getInstance();
            createAccountButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    txt_username=fullNameEditText.getText().toString().trim();
                    txt_password=passwordEditText.getText().toString().trim();
                    txt_email=emailEditText.getText().toString().trim();
                    if(TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password) || TextUtils.isEmpty(txt_username)){
                        Toast.makeText(RegisterActivity.this,"Email and password fields are required", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Register(txt_username, txt_email, txt_password);
                    }
                }
            });

        signInTextView.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        });
    }

        private void Register(String Username, String Email, String Password) {

            auth.createUserWithEmailAndPassword(Email, Password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser firebaseUser = auth.getCurrentUser();
                                assert firebaseUser != null;
                                String userId = firebaseUser.getUid();
                                reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

                                Toast.makeText(RegisterActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
                                Map<String, Object> user = new HashMap<>();
                                user.put("email", txt_email);
                                user.put("password", txt_password);
                                //user.put("username", txt_username);
                                //user.put("imageUrl", "default");
                                reference.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: user Profile is created for " + userId);

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: " + e.toString());
                                    }
                                });
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                finish();

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }



}