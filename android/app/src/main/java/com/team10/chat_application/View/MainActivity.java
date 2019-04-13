package com.team10.chat_application.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.team10.chat_application.Controller.Controller;
import com.team10.chat_application.Model.MessageType;
import com.team10.chat_application.R;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {
    Controller controller;
    public static MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;
        controller = Controller.getInstance();
        String ip = "10.42.0.1";
        String port = "3000";
        if (controller.chat("moamen", "abcd", ip, port)) {
            System.out.println("connected successfully");
        }
    }

    public void showToast(String message, MessageType messageType) {
        switch (messageType) {
            case ERROR:
                Toasty.error(MainActivity.mainActivity, message, Toast.LENGTH_SHORT, true).show();
                break;
            case WARNINGS:
                Toasty.warning(MainActivity.mainActivity, message, Toast.LENGTH_SHORT, true).show();
                break;
            case SUCCESS:
                Toasty.success(MainActivity.mainActivity, message, Toast.LENGTH_SHORT, true).show();
                break;
            case INFO:
                Toasty.info(MainActivity.mainActivity, message, Toast.LENGTH_SHORT, true).show();
                break;
            case NORMAL:
                Toasty.normal(MainActivity.mainActivity, message, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}