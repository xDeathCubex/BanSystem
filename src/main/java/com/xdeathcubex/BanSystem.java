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
        getProxy().getPluginManager().registerCommand(this, new TempBan("tempban"));
        getProxy().getPluginManager().registerCommand(this, new TempBan("tban"));
        getProxy().getPluginManager().registerCommand(this, new Unban());
        getProxy().getPluginManager().registerCommand(this, new Mute());
        getProxy().getPluginManager().registerCommand(this, new TempMute("tempmute"));
        getProxy().getPluginManager().registerCommand(this, new TempMute("tmute"));
        getProxy().getPluginManager().registerCommand(this, new Unmute());
        getProxy().getPluginManager().registerCommand(this, new Banlog());
        getProxy().getPluginManager().registerCommand(this, new Baninfo("baninfo"));
        getProxy().getPluginManager().registerCommand(this, new Baninfo("bans"));
        getProxy().getPluginManager().registerCommand(this, new Editban("editban"));
        getProxy().getPluginManager().registerCommand(this, new Editban("eb"));
        getProxy().getPluginManager().registerCommand(this, new Editmute("editmute"));
        getProxy().getPluginManager().registerCommand(this, new Editmute("em"));
        getProxy().getPluginManager().registerCommand(this, new Kick());
        getProxy().getPluginManager().registerCommand(this, new Mg());
        getProxy().getPluginManager().registerCommand(this, new Ml());
        getProxy().getPluginManager().registerCommand(this, new Whereami());
        getProxy().getPluginManager().registerCommand(this, new Whereis());
        getProxy().getPluginManager().registerCommand(this, new Goto());
        getProxy().getPluginManager().registerListener(this, new Chat());
        getProxy().getPluginManager().registerListener(this, new PreLogin());

        new MySQL("localhost", "LogMC", "PW :D", "LogMC");

    }
}
