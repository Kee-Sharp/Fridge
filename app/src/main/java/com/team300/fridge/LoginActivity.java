package com.team300.fridge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.team300.fridge.POJOs.AppUser;
import com.team300.fridge.POJOs.Model;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MainActivity.setContext(this);

        //set up our fields
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);
        Button registerButton = findViewById(R.id.registerButton);

        loginButton.setOnClickListener((v) -> {
            AppUser attemptedUser = attemptLogin();
            if (attemptedUser != null) {
                Model.switchUser(attemptedUser);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        });
        registerButton.setOnClickListener((v) -> {
            //go to register activity
        });
    }

    public AppUser attemptLogin() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("Fridge.realm")
                .schemaVersion(MainActivity.getSchemaVNow())
                .deleteRealmIfMigrationNeeded()// if migration needed then this method will remove the existing database and will create new database
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();
        MainActivity.setUiThreadRealm(Realm.getInstance(config));
        List<AppUser> allUsers = Model.getInstance().getUsers();
        String currentEmail = emailEditText.getText().toString();
        String currentPass = passwordEditText.getText().toString();
        //search for a matching email, and then check if password is correct
        for (AppUser user: allUsers) {
            if (user.getEmail().equals(currentEmail)) {
                if (user.getPasshash() == AppUser.hash(currentPass)) {
                    Model.switchUser(user);
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