package com.moamen.whatsapp.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.moamen.whatsapp.Controller.Controller;
import com.moamen.whatsapp.Model.Message.Message;
import com.moamen.whatsapp.Model.Message.MessageAdapter;
import com.moamen.whatsapp.Model.MessageType;
import com.moamen.whatsapp.Model.Room;
import com.moamen.whatsapp.R;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class ChatRoom extends AppCompatActivity {
    public static ChatRoom chatRoomActivity;
    public Controller controller;
    public static Room room;
    public Button btnSend;
    public EditText inputMsg;

    private ArrayList<Message> messages;
    private MessageAdapter adapter;
    private RecyclerView recyclerView;

    private String roomName, roomPass, username, ip, port;
    public static Boolean active = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        // get from previous
        Intent intent = getIntent();
        roomName = intent.getStringExtra("room_name");
        roomPass = intent.getStringExtra("room_pass");
        username = intent.getStringExtra("username");

        // Activity, Controller
        controller = Controller.getInstance();
        chatRoomActivity = this;
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Room");
            actionBar.setSubtitle(roomName);
        }
        // Bind Components
        inputMsg = findViewById(R.id.inputMsg);
        btnSend = findViewById(R.id.btnSend);


        // Socket IO
        room = new Room(roomName, roomPass, new ArrayList<Message>());
        controller.loadRoom(roomName, roomPass, room.getChatList());

        ip = "192.168.43.235";
        port = "3000";

        // setting up recycler view
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new MessageAdapter(room.getChatList());
        LinearLayoutManager recyLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(recyLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        prepareData();
        controller.chat(username, roomName, ip, port);


        // Click Listeners
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSend.setEnabled(false);
                controller.sendMessage(roomName, roomPass, room.getChatList(), username, inputMsg.getText().toString());
                inputMsg.setText("");
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        controller.disconnect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        active = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        active = true;
        prepareData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.active_people, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_active_people) {
            Intent i = new Intent(this, ActivePeople.class);
            i.putExtra("room_name", getIntent().getStringExtra("room_name"));
            i.putExtra("username", username);
            i.putExtra("ip", ip);
            i.putExtra("port", port);
            startActivity(i);
            showToast("Active People", MessageType.INFO);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void prepareData() {
        messages = room.getChatList();
        adapter.notifyDataSetChanged();
    }


    public void printArrayList() {
        System.out.println(room.getChatList());
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
