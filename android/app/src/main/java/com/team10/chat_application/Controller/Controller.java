package com.team10.chat_application.Controller;


import com.team10.chat_application.Model.MessageType;
import com.team10.chat_application.Model.RoomUser;
import com.team10.chat_application.View.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Controller {
    private Socket socket;
    public static HashMap<String, ArrayList<RoomUser>> roomData;
    // Singleton implementation
    private static Controller single_instance = null;

    private Controller() {
        roomData = new HashMap<>();
    }

    public static Controller getInstance() {
        if (single_instance == null)
            single_instance = new Controller();
        return single_instance;
    }

    /**
     * @param username : string
     * @param room     : string
     * @param ip       : ip of pc
     * @param port     : port of the server
     * @return : true if success else false.
     */
    public Boolean chat(final String username, String room, String ip, String port) {
        try {
            socket = IO.socket("http://" + ip + ":" + port);
            socket.connect();
            JSONObject person = new JSONObject();
            person.put("username", username);
            person.put("room", room);
            socket.emit("join", person);
            socket.on("message", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject message = (JSONObject) args[0];
                    try {
                        final String username = message.getString("username");
                        final String text = message.getString("text");
                        MainActivity.mainActivity.runOnUiThread(new Runnable() {
                            public void run() {
                                MainActivity.mainActivity.showToast("Username:" + username + " ,text: " + text, MessageType.INFO);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            socket.on("roomData", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject message = (JSONObject) args[0];
                    try {
                        final String room = message.getString("room");
                        final ArrayList<RoomUser> current_room_data = new ArrayList<>();
                        final JSONArray data = message.getJSONArray("users");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonUserRoom = data.getJSONObject(i);
                            RoomUser roomUser = new RoomUser(
                                    jsonUserRoom.getString("username"),
                                    jsonUserRoom.getString("room"),
                                    jsonUserRoom.getInt("id")
                            );
                            current_room_data.add(roomUser);
                        }
                        roomData.put(room, current_room_data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void sendMessage(final String text) {
        socket.emit("sendMessage", text, new Ack() {
            @Override
            public void call(final Object... args) {
                MainActivity.mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Boolean callback = (Boolean) args[0];
                        if (callback) {
                            MainActivity.mainActivity.showToast("Delivered Ya Ma3lm", MessageType.SUCCESS);
                        } else {
                            MainActivity.mainActivity.showToast("Not Delivered batal 4tayem", MessageType.ERROR);
                        }
                        MainActivity.mainActivity.enableSendButton();
                    }
                });
            }
        });
    }
}