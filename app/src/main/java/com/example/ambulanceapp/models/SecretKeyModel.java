package com.example.ambulanceapp.models;

public class SecretKeyModel {
    private String id;
    private String user_id;
    private String secret_key;

    public SecretKeyModel(String id, String userId, String secret_key){
        this.id = id;
        this.user_id = userId;
        this.secret_key = secret_key;
    }

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getSecret_key() {
        return secret_key;
    }
}
