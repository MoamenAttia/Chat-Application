package com.moamen.whatsapp.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.moamen.whatsapp.Controller.Controller;
import com.moamen.whatsapp.Model.MessageType;
import com.moamen.whatsapp.R;

import es.dmoral.toasty.Toasty;

public class CreateRoom extends AppCompatActivity {
    public static CreateRoom createRoomActivity;
    public Controller controller;
    Button createBtn;
    EditText roomName, roomPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        // Activity, Controller
        createRoomActivity = this;
        controller = Controller.getInstance();
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("WhatsApp");
            actionBar.setSubtitle("Room Creation");
        }

        // Bind Components
        createBtn = findViewById(R.id.BT_create_room);
        roomName = findViewById(R.id.ET_room_name);
        roomPassword = findViewById(R.id.ET_room_password);

        // Click Listeners
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (roomName.getText().toString().equals("") || roomPassword.getText().toString().equals("")) {
                    showToast("please enter room name and password", MessageType.WARNINGS);
                } else {
                    controller.addRoom(roomName.getText().toString(), roomPassword.getText().toString());
                }
            }
        });
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
