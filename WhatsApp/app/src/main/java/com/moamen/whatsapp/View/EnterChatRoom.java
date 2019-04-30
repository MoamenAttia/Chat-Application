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

public class EnterChatRoom extends AppCompatActivity {
    EditText passwordEditText;
    Button enterRoomBtn;
    TextView roomText;
    public Controller controller;
    public static EnterChatRoom enterChatRoomActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_chat_room);

        // Activity, Controller
        enterChatRoomActivity = this;
        controller = Controller.getInstance();


        // get From Previous
        Intent intent = getIntent();
        roomText = findViewById(R.id.TV_chat_room_name);
        enterRoomBtn = findViewById(R.id.BT_Enter_Chat_Room);
        passwordEditText = findViewById(R.id.ET_Chat_Room_Pass);
        roomText.setText(intent.getStringExtra("ROOM_NAME"));

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Room Security");
            actionBar.setSubtitle(intent.getStringExtra("ROOM_NAME"));
        }


        // Click Listeners
        enterRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinRoom();
            }
        });
    }

    public void loadRoom() {
        Intent i = new Intent(this, ChatRoom.class);
        i.putExtra("room_name", roomText.getText().toString());
        i.putExtra("room_pass", passwordEditText.getText().toString());
        i.putExtra("username", getIntent().getStringExtra("username"));
        startActivity(i);
    }

    public void joinRoom() {
        if (passwordEditText.getText().toString().equals("")) {
            showToast("please enter password", MessageType.WARNINGS);
        } else {
            controller.joinRoom(roomText.getText().toString(), passwordEditText.getText().toString());
        }
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
