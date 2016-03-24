package com.xdeathcubex.events;

import com.xdeathcubex.mysql.MySQL;
import com.xdeathcubex.utils.TimeUnit;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Chat implements Listener{

    @EventHandler
    public void onChat(ChatEvent e){
        ProxiedPlayer p = (ProxiedPlayer) e.getSender();
        if(!e.getMessage().startsWith("/")){
            if(TimeUnit.checkMute(p.getUniqueId().toString().replaceAll("-", ""))){
                e.setCancelled(true);
                p.sendMessage(new TextComponent("§cDu wurdest vom Chat gemutet." +
                        "\n§cVerbleibende Zeit: §e" + TimeUnit.getTime() +
                        "\n§cGrund: §e" + MySQL.getCurrentMute("Reason", p.getUniqueId().toString().replaceAll("-",""))));
            }
        }
    }
}
