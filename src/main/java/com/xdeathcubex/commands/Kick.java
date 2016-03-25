package com.xdeathcubex.commands;

import com.xdeathcubex.BanSystem;
import com.xdeathcubex.mysql.MySQL;
import com.xdeathcubex.utils.RankSystem;
import com.xdeathcubex.utils.TimeUnit;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Kick extends Command {
    public Kick() {
        super("kick");
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        if(args.length < 2){
            if(cs instanceof ProxiedPlayer){
                ProxiedPlayer p = (ProxiedPlayer)cs;
                if (RankSystem.hasMod(p.getUniqueId().toString().replaceAll("-", ""))) {
                    p.sendMessage(new TextComponent(BanSystem.prefix + "Verwendung: §6/kick <Spieler>"));
                } else {
                    p.sendMessage(new TextComponent(BanSystem.prefix + "§cKeine Rechte!"));
                }
            } else {
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "Verwendung: §6/kick <Spieler>"));
            }
        } else {
            String kickedBy;
            if(cs instanceof ProxiedPlayer){
                ProxiedPlayer p = (ProxiedPlayer)cs;
                if(!RankSystem.hasMod(p.getUniqueId().toString().replaceAll("-",""))){
                    return;
                } else {
                    kickedBy = p.getDisplayName();
                }
            } else {
                kickedBy = "§4BungeeConsole";
            }
            ProxiedPlayer p1 = ProxyServer.getInstance().getPlayer(args[0]);
            if(p1 == null){
                if(cs instanceof ProxiedPlayer){
                    ProxiedPlayer p = (ProxiedPlayer)cs;
                    p.sendMessage(new TextComponent(BanSystem.prefix + "Dieser Spieler ist momentan nicht online."));
                } else {
                    ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "Dieser SPieler ist momentan nicht online."));
                }
            } else {
                StringBuilder sb = new StringBuilder();
                for(int i = 1; i < args.length; i++){
                    sb.append(args[i]).append(" ");
                }
                String reason = sb.toString().trim();
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "§a" + RankSystem.getPrefix(p1.getUniqueId().toString().replaceAll("-","")) + " §7wurde von §c" + kickedBy + " §7gekickt§c!"));
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + reason));
                    p1.disconnect(new TextComponent("§cDu wurdest vom LogMC Netzwerk gekickt.\n\n§7Grund§8: §c" + reason));
            }
        }
    }
}
