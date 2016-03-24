package com.xdeathcubex.events;

import com.xdeathcubex.mysql.MySQL;
import com.xdeathcubex.utils.TimeUnit;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PreLogin implements Listener {

    @EventHandler
    public void onLogin(LoginEvent e){
        e.setCancelReason("XD");
        if(TimeUnit.checkBan(e.getConnection().getUniqueId().toString().replaceAll("-", ""))){
            e.setCancelled(true);
            e.getConnection().disconnect(new TextComponent(
                                    "§cDu wurdest vom LogMC Netzwerk gebannt." +
                                    "\n" +
                                    "\n§eBangrund§8: §c" + MySQL.getCurrentBan("Reason", e.getConnection().getUniqueId().toString().replaceAll("-", "")) +
                                    "\n§aVerbleibende Zeit§8: §e" + TimeUnit.getTime()));
        }
    }
}
