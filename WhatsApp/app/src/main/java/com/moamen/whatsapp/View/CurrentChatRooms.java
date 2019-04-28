package com.moamen.whatsapp.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.moamen.whatsapp.Controller.Controller;
import com.moamen.whatsapp.R;

import java.util.ArrayList;

public class CurrentChatRooms extends AppCompatActivity {

    public Controller controller;
    public ArrayList<String> arrayList;
    public static CurrentChatRooms currentChatRoomsActivity;
    ListView topicsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_chat_rooms);

        // Activity, Controller
        currentChatRoomsActivity = this;
        controller = Controller.getInstance();

        // Bind Components
        topicsListView = findViewById(R.id.list_view_messages);


        // Preparation
        arrayList = new ArrayList<>();
        controller.loadChatRooms(arrayList);


        // Click Listeners
        topicsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                enterRoom(arrayList.get(position));
            }
        });
    }


    public void enterRoom(String s) {
        Intent i = new Intent(this, EnterChatRoom.class);
        i.putExtra("ROOM_NAME", s);
        i.putExtra("username", getIntent().getStringExtra("username"));
        startActivity(i);
    }

    public void update() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.room_item, R.id.room_id, arrayList);
        topicsListView.setAdapter(adapter);
    }

}
