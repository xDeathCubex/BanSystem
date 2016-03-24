package com.xdeathcubex.utils;

import com.xdeathcubex.mysql.MySQL;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;

public class RankSystem {

    public static boolean hasAdmin(String uuid){
        String rank = MySQL.getRank(uuid);
        ProxyServer.getInstance().broadcast(new TextComponent("RANK: " + rank));
        return rank.equals("admin") || rank.equals("dev");
    }
    public static boolean hasMod(String uuid) {
        String rank = MySQL.getRank(uuid);
        return rank != null && (rank.equals("srmod") || rank.equals("mod") || rank.equals("admin") || rank.equals("dev"));
    }
}
