package com.example.vaio.webservicechat;

/**
 * Created by vaio on 11/30/2016.
 */

public class ItemMessage {
    private String name;
    private String date;
    private String content;

    public ItemMessage(String name, String date, String content) {
        this.name = name;
        this.date = date;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }
}
