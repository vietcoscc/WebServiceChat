package com.example.vaio.webservicechat;

import android.net.Uri;

/**
 * Created by vaio on 11/30/2016.
 */

public class Config {
    public static final String SERVER_ADDRESS = "http://10.255.148.59:8080/chatt3h/";

    public static final String chat(String name, String content) {
        name = Uri.encode(name);
        content = Uri.encode(content);
        return SERVER_ADDRESS + "chat.php?name=" + name + "&content=" + content;
    }

    public static String getData() {
        return SERVER_ADDRESS + "getdata.php";
    }
}
