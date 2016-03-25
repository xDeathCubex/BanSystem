package com.xdeathcubex.utils;

import com.xdeathcubex.mysql.MySQL;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class RankSystem {

    public static boolean hasAdmin(String uuid){
        String rank = MySQL.getRank(uuid);
        return rank.equals("admin") || rank.equals("dev");
    }
    public static boolean hasMod(String uuid) {
        String rank = MySQL.getRank(uuid);
        return rank != null && (rank.equals("srmod") || rank.equals("mod") || rank.equals("admin") || rank.equals("dev"));
    }
    public static String getPrefix(String uuid){

        if(uuid.equals("§4BungeeConsole")){
            return "§4BungeeConsole";
        }

        String rank = MySQL.getRank(uuid);
        String name = UUIDFetcher.getName(uuid);

        if(rank == null) {
            return "§a" + name;
        }

        if(rank.equalsIgnoreCase("admin")){
            return "§4" + name;
        }
        if(rank.equalsIgnoreCase("dev")){
            return "§b" + name;
        }
        if(rank.equalsIgnoreCase("srmod")){
            return "§c" + name;
        }
        if(rank.equalsIgnoreCase("mod")){
            return "§c" + name;
        }
        if(rank.equalsIgnoreCase("builder")){
            return "§2" + name;
        }
        if(rank.equalsIgnoreCase("youtuber")){
            return "§5" + name;
        }
        if(rank.equalsIgnoreCase("premium")){
            return "§6" + name;
        }
        return "§a" + name;
    }
}
