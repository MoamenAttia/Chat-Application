package com.moamen.whatsapp.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.moamen.whatsapp.R;

public class Home extends AppCompatActivity {

    public static Home homeActivity;
    TextView createRoom;
    TextView joinRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Activity
        homeActivity = this;
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("WhatsApp");
        }

        // bind components
        createRoom = findViewById(R.id.BT_create_room);
        joinRoom = findViewById(R.id.BT_join);

        // Click Listeners
        createRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRoomInfoActivity();
            }
        });
        joinRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToJoinForActivity();
            }
        });

    }

    void goToRoomInfoActivity() {
        Intent i = new Intent(Home.this, CreateRoom.class);
        startActivity(i);
    }

    void goToJoinForActivity() {
        Intent i = new Intent(Home.this, CurrentChatRooms.class);
        i.putExtra("username", getIntent().getStringExtra("username"));
        startActivity(i);
    }
}
