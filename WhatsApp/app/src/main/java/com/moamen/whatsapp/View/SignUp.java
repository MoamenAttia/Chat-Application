package com.moamen.whatsapp.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.moamen.whatsapp.Controller.Controller;
import com.moamen.whatsapp.Model.MessageType;
import com.moamen.whatsapp.R;

import es.dmoral.toasty.Toasty;

public class SignUp extends AppCompatActivity {
    EditText user, password, email;
    TextView signUpButton;
    public static SignUp signUpActivity;
    public Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // Activity, Controller
        controller = Controller.getInstance();
        signUpActivity = this;
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("WhatsApp");
            actionBar.setSubtitle("SignUp");
        }

        // Bind Components
        signUpButton = findViewById(R.id.BT_sign_up);
        user = findViewById(R.id.ET_user);
        email = findViewById(R.id.ET_email);
        password = findViewById(R.id.ET_Pass);

        // Click Listeners
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }


    private void signUp() {
        if (user.getText().toString().equals("") || email.getText().toString().equals("") || password.getText().toString().equals("")) {
            showToast("please enter username, email and password", MessageType.WARNINGS);
        } else {
            controller.addUser(user.getText().toString(), email.getText().toString(), password.getText().toString());
        }
    }


    public void showToast(String message, MessageType messageType) {
        switch (messageType) {
            case ERROR:
                Toasty.error(SignUp.signUpActivity, message, Toast.LENGTH_SHORT, true).show();
                break;
            case WARNINGS:
                Toasty.warning(SignUp.signUpActivity, message, Toast.LENGTH_SHORT, true).show();
                break;
            case SUCCESS:
                Toasty.success(SignUp.signUpActivity, message, Toast.LENGTH_SHORT, true).show();
                break;
            case INFO:
                Toasty.info(SignUp.signUpActivity, message, Toast.LENGTH_SHORT, true).show();
                break;
            case NORMAL:
                Toasty.normal(SignUp.signUpActivity, message, Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
