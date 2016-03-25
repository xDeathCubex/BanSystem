package com.xdeathcubex;

import com.xdeathcubex.commands.*;
import com.xdeathcubex.events.Chat;
import com.xdeathcubex.events.PreLogin;
import com.xdeathcubex.mysql.MySQL;
import net.md_5.bungee.api.plugin.Plugin;

public class BanSystem extends Plugin {

    public static BanSystem instance;
    public static String prefix = "§7[§c✦§7] ";
    public void onEnable(){
        instance = this;

        getProxy().getPluginManager().registerCommand(this, new Ban());
        getProxy().getPluginManager().registerCommand(this, new TempBan());
        getProxy().getPluginManager().registerCommand(this, new Unban());
        getProxy().getPluginManager().registerCommand(this, new Mute());
        getProxy().getPluginManager().registerCommand(this, new TempMute());
        getProxy().getPluginManager().registerCommand(this, new Unmute());
        getProxy().getPluginManager().registerCommand(this, new Banlog());
        getProxy().getPluginManager().registerCommand(this, new Baninfo());
        getProxy().getPluginManager().registerCommand(this, new Editban());
        getProxy().getPluginManager().registerCommand(this, new Editmute());
        getProxy().getPluginManager().registerCommand(this, new Kick());
        getProxy().getPluginManager().registerListener(this, new Chat());
        getProxy().getPluginManager().registerListener(this, new PreLogin());

        new MySQL("localhost", "root", "pw", "LogMC");

    }
}
