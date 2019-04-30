package com.moamen.whatsapp.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.moamen.whatsapp.Controller.Controller;
import com.moamen.whatsapp.Model.MessageType;
import com.moamen.whatsapp.R;

import es.dmoral.toasty.Toasty;

public class SignIn extends AppCompatActivity {
    EditText username;
    EditText pass;
    Button signInButton, createButton;
    public static SignIn signInActivity;
    public Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Activity, Controller
        controller = Controller.getInstance();
        signInActivity = this;

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("WhatsApp");
            actionBar.setSubtitle("SignIn");
        }

        // Bind Components
        signInButton = findViewById(R.id.BT_sign_in);
        createButton = findViewById(R.id.account);
        username = findViewById(R.id.ET_user_name);
        pass = findViewById(R.id.ET_Password);

        // Click Listener
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSignUpActivity();
            }
        });

    }

    private void signIn() {
        if (username.getText().toString().equals("") || pass.getText().toString().equals("")) {
            showToast("please enter username and password", MessageType.WARNINGS);
        } else {
            controller.signIn(username.getText().toString(), pass.getText().toString());
        }
    }


    public void goToSignUpActivity() {
        Intent i = new Intent(this, SignUp.class);
        startActivity(i);
    }

    public void goToHomeActivity() {
        Intent i = new Intent(this, Home.class);
        i.putExtra("username", username.getText().toString());
        startActivity(i);
        finish();
    }


    public void showToast(String message, MessageType messageType) {
        switch (messageType) {
            case ERROR:
                Toasty.error(this, message, Toast.LENGTH_SHORT, true).show();
                break;
            case WARNINGS:
                Toasty.warning(this, message, Toast.LENGTH_SHORT, true).show();
                break;
            case SUCCESS:
                Toasty.success(this, message, Toast.LENGTH_SHORT, true).show();
                break;
            case INFO:
                Toasty.info(this, message, Toast.LENGTH_SHORT, true).show();
                break;
            case NORMAL:
                Toasty.normal(this, message, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
