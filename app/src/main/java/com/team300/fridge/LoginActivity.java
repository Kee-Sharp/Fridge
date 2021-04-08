package com.team300.fridge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.team300.fridge.POJOs.Model;
import com.team300.fridge.POJOs.User;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //set up our fields
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);
        Button registerButton = findViewById(R.id.registerButton);

        loginButton.setOnClickListener((v) -> {
            User attemptedUser = attemptLogin();
            if (attemptedUser != null) {
                Model.getInstance().switchUser(attemptedUser);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        });
        registerButton.setOnClickListener((v) -> {
            //go to register activity
        });
    }

    public User attemptLogin() {
        List<User> allUsers = Model.getInstance().getUsers();
        String currentEmail = emailEditText.getText().toString();
        String currentPass = passwordEditText.getText().toString();
        //search for a matching email, and then check if password is correct
        for (User user: allUsers) {
            if (user.getEmail().equals(currentEmail)) {
                if (user.getPasshash() == User.hash(currentPass)) {
                    return user;
                } else {
                    passwordEditText.setError("Incorrect Password");
                    passwordEditText.requestFocus();
                    return null;
                }
            }
        }
        emailEditText.setError("Email not found");
        emailEditText.requestFocus();
        return null;
    }
}