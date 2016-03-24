package com.xdeathcubex.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

public class UUIDFetcher {

    private static HashMap<String, String> uuidCache = new HashMap<String, String>();

    public static String getUUID(String username) {
        if (uuidCache.containsKey(username)) {
            return uuidCache.get(username);
        }
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
            InputStream stream = url.openStream();
            InputStreamReader inr = new InputStreamReader(stream);
            BufferedReader reader = new BufferedReader(inr);
            String s;
            StringBuilder sb = new StringBuilder();
            while ((s = reader.readLine()) != null) {
                sb.append(s);
            }
            String result = sb.toString();
            JsonElement element = new JsonParser().parse(result);
            JsonObject obj = element.getAsJsonObject();
            String uuid = obj.get("id").toString();
            uuid = uuid.substring(1);
            uuid = uuid.substring(0, uuid.length() - 1);
            uuidCache.put(username, uuid);
            return uuid;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

