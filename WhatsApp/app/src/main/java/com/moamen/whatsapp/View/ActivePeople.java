package com.moamen.whatsapp.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.moamen.whatsapp.Controller.Controller;
import com.moamen.whatsapp.R;

import java.util.ArrayList;

public class ActivePeople extends AppCompatActivity {

    public Controller controller;
    public ArrayList<String> arrayList;
    public static ActivePeople activePeopleActivity;
    ListView topicsListView;
    public String roomName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_people);

        // Activity, Controller
        activePeopleActivity = this;
        controller = Controller.getInstance();
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Active People");
        }

        // Bind Components
        topicsListView = findViewById(R.id.active_people_list_id);

        // Preparation
        arrayList = new ArrayList<>();
        roomName = getIntent().getStringExtra("room_name");
        controller.loadActivePeople(roomName, arrayList);
    }

    public void update() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.active_people_item, R.id.username_id, arrayList);
        topicsListView.setAdapter(adapter);
    }
}
