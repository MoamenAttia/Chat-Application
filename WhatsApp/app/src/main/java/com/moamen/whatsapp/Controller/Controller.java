package com.moamen.whatsapp.Controller;

import android.service.autofill.CharSequenceTransformation;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.moamen.whatsapp.Model.Message.Message;
import com.moamen.whatsapp.Model.MessageType;
import com.moamen.whatsapp.Model.Room;
import com.moamen.whatsapp.Model.RoomUser;
import com.moamen.whatsapp.Model.User;
import com.moamen.whatsapp.View.ActivePeople;
import com.moamen.whatsapp.View.ChatRoom;
import com.moamen.whatsapp.View.CreateRoom;
import com.moamen.whatsapp.View.CurrentChatRooms;
import com.moamen.whatsapp.View.EnterChatRoom;
import com.moamen.whatsapp.View.SignIn;
import com.moamen.whatsapp.View.SignUp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Controller {
    private FirebaseFirestore db;
    private Socket socket;
    public static HashMap<String, ArrayList<RoomUser>> roomData;

    public Controller() {
        db = FirebaseFirestore.getInstance();
        roomData = new HashMap<>();

    }

    // Singleton implementation
    private static Controller single_instance = null;


    public static Controller getInstance() {
        if (single_instance == null)
            single_instance = new Controller();
        return single_instance;
    }

    public void addUser(String name, String email, String password) {
        try {
            User user = new User(name, email, password);
            db.collection("user").document(user.getName())
                    .set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    SignUp.signUpActivity.showToast("sign up successfully ^_^", MessageType.SUCCESS);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    SignUp.signUpActivity.showToast("sign up failed :D", MessageType.ERROR);
                }
            });
        } catch (Error error) {
            SignUp.signUpActivity.showToast(error.getMessage(), MessageType.ERROR);
        }
    }

    public void signIn(String username, final String password) {
        db.collection("user").document(username).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        if (document.exists()) {
                            System.out.println(document);
                            User user = document.toObject(User.class);
                            if (user != null) {
                                if (user.getPassword().equals(password)) {
                                    SignIn.signInActivity.showToast("Sign In Successfully", MessageType.SUCCESS);
                                    SignIn.signInActivity.goToHomeActivity();
                                } else {
                                    SignIn.signInActivity.showToast("email or password is wrong", MessageType.ERROR);
                                }
                            }

                        } else {
                            SignIn.signInActivity.showToast("email or password is wrong", MessageType.ERROR);
                        }
                    }
                } else {
                    SignIn.signInActivity.showToast("Error due to network", MessageType.ERROR);
                }
            }
        });
    }

    public void addRoom(String chatRoomID, String password) {
        try {
            Room chatRoom = new Room(chatRoomID, password, new ArrayList<Message>());

            db.collection("room").document(chatRoomID)
                    .set(chatRoom).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    CreateRoom.createRoomActivity.showToast("Chat Room Created Successfully ^_^", MessageType.SUCCESS);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    CreateRoom.createRoomActivity.showToast("Chat Room Cannot be Created :D", MessageType.ERROR);
                }
            });
        } catch (Error error) {
            CreateRoom.createRoomActivity.showToast(error.getMessage(), MessageType.ERROR);
        }
    }

    public void joinRoom(String chatRoomID, final String password) {
        db.collection("room").document(chatRoomID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        if (document.exists()) {
                            Room room = document.toObject(Room.class);
                            if (room != null) {
                                if (room.getPassword().equals(password)) {
                                    EnterChatRoom.enterChatRoomActivity.loadRoom();
                                } else {
                                    EnterChatRoom.enterChatRoomActivity.showToast("chat room or password is wrong", MessageType.ERROR);
                                }
                            }
                        } else {
                            EnterChatRoom.enterChatRoomActivity.showToast("chat room or password is wrong", MessageType.ERROR);
                        }
                    }
                } else {
                    EnterChatRoom.enterChatRoomActivity.showToast("chat room or password is wrong", MessageType.ERROR);
                }
            }
        });
    }

    public void loadRoom(String chatRoomID, final String password, final ArrayList<Message> messagesArrayList) {
        db.collection("room").document(chatRoomID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        if (document.exists()) {
                            Room room = document.toObject(Room.class);
                            if (room != null) {
                                if (room.getPassword().equals(password)) {
                                    ArrayList<Message> dbList = room.getChatList();
                                    messagesArrayList.clear();
                                    messagesArrayList.addAll(dbList);
                                    ChatRoom.chatRoomActivity.prepareData();
                                } else {
                                    ChatRoom.chatRoomActivity.showToast("Error while Loading", MessageType.ERROR);
                                }
                            }

                        } else {
                            ChatRoom.chatRoomActivity.showToast("Error while Loading", MessageType.ERROR);
                        }
                    }
                } else {
                    ChatRoom.chatRoomActivity.showToast("Error while Loading", MessageType.ERROR);
                }
            }
        });
    }

    public void loadChatRooms(final ArrayList<String> arrayList) {
        db.collection("room")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            arrayList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String st = document.getId();
                                arrayList.add(st);
                                System.out.println(document.getId() + " => " + document.getData());
                            }
                            CurrentChatRooms.currentChatRoomsActivity.update();
                        } else {
                            System.out.println("Error getting documents: " + task.getException());
                        }
                    }
                });
    }

    public void addChatMessage(String chatRoomID, String password, ArrayList<Message> messageArrayList) {
        try {
            Room chatRoom = new Room(chatRoomID, password, messageArrayList);
            db.collection("room").document(chatRoomID)
                    .set(chatRoom).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    ChatRoom.chatRoomActivity.prepareData();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    ChatRoom.chatRoomActivity.showToast("Your Message Cannot be Added Check Internet Connection :D", MessageType.ERROR);
                }
            });
        } catch (Error error) {
            ChatRoom.chatRoomActivity.showToast("Your Message Cannot be Added Check Internet Connection :D", MessageType.ERROR);
        }
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
                        ChatRoom.chatRoomActivity.runOnUiThread(new Runnable() {
                            public void run() {
                                if (ChatRoom.active) {
                                    if (!username.equals("Admin")) {
                                        ChatRoom.room.getChatList().add(new Message(username, text));
                                        if (ChatRoom.active) {
                                            ChatRoom.chatRoomActivity.prepareData();
                                        }
                                    }
                                }
                                ChatRoom.chatRoomActivity.showToast("Username:" + username + "text: " + text, MessageType.INFO);
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
                                    jsonUserRoom.getString("id")
                            );
                            current_room_data.add(roomUser);
                        }
                        roomData.put(room, current_room_data);


                        if (ActivePeople.active) {
                            ActivePeople.activePeopleActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loadActivePeople(ActivePeople.activePeopleActivity.roomName, ActivePeople.activePeopleActivity.arrayList);
                                }
                            });
                        }
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

    public void sendMessage(final String chatRoomID, final String password, final ArrayList<Message> arrayList, final String from, final String text) {
        socket.emit("sendMessage", text, new Ack() {
            @Override
            public void call(final Object... args) {
                ChatRoom.chatRoomActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Boolean callback = (Boolean) args[0];
                        if (callback) {
                            arrayList.add(new Message(from, text));
                            Controller.getInstance().addChatMessage(chatRoomID, password, arrayList);
                        } else {
                            ChatRoom.chatRoomActivity.showToast("Not Delivered batal 4tayem", MessageType.ERROR);
                        }
                        ChatRoom.chatRoomActivity.btnSend.setEnabled(true);
                    }
                });
            }
        });
    }

    public void loadActivePeople(String chatRoomID, final ArrayList<String> arrayList) {
        for (Map.Entry<String, ArrayList<RoomUser>> entry : roomData.entrySet()) {
            if (entry.getKey().equals(chatRoomID)) {
                arrayList.clear();
                for (int i = 0; i < entry.getValue().size(); ++i) {
                    arrayList.add(entry.getValue().get(i).getUsername());
                }
            }
        }
        ActivePeople.activePeopleActivity.update();
    }

    public void disconnect() {
        socket.disconnect();
    }
}