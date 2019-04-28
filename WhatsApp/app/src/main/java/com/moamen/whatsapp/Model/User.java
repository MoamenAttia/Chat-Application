package com.moamen.whatsapp.Model;

public class User {
    private String name, email, password;

    public User(String name, String email, String password) {
        setEmail(email);
        setName(name);
        setPassword(password);
    }

    public User() {
        // For Only Document Snapshot Creating
    }

    public User(User user) {
        if (user == null) throw new Error("Error while instantiating the object user");
        setEmail(user.email);
        setName(user.name);
        setPassword(user.password);
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


    private void setPassword(String password) {
        if (password.length() <= 8) {
            throw new Error("Password is too weak");
        }
        this.password = password;
    }

    private void setName(String name) {
        if (name.equals("")) {
            throw new Error("No Name Entered");
        }
        this.name = name;
    }

    private void setEmail(String email) {
        if (email.equals("")) {
            throw new Error("No Email Entered");
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw new Error("Not Valid Email");
        }
        this.email = email;
    }

}
