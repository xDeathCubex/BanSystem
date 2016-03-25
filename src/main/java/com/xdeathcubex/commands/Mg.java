package com.xdeathcubex.commands;

import com.xdeathcubex.utils.RankSystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Mg extends Command {
    public Mg() {
        super("mg");
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer)cs;
        if(!RankSystem.hasMod(p.getUniqueId().toString().replaceAll("-",""))){
            return;
        }
        if(args.length < 1){
            p.sendMessage(new TextComponent("§cVerwendung: §e/mg <Nachricht>"));
        } else {
            StringBuilder sb = new StringBuilder();
            for (String arg : args) {
                sb.append(arg).append(" §7");
            }
            String msg = sb.toString().trim();
            for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()){
                if(RankSystem.hasMod(all.getUniqueId().toString().replaceAll("-",""))){
                    all.sendMessage(new TextComponent("§7[§cMG§7] [§e" + p.getServer().getInfo().getName() +"§7] " + p.getDisplayName() + "§7: " + msg));
                }
            }
        }
    }
}
