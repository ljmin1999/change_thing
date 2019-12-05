package com.example.studyroom;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class IdPost {
    public long id;
    public String name;
    public String psw;
    public String email;

    public IdPost() {

    }

    public IdPost(long id, String name, String psw, String email) {
        this.id = id;
        this.name = name;
        this.psw = psw;
        this.email = email;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("psw", psw);
        result.put("email", email);

        return result;
    }
}
