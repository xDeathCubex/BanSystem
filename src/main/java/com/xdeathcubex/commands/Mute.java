package com.xdeathcubex.commands;

import com.xdeathcubex.BanSystem;
import com.xdeathcubex.mysql.MySQL;
import com.xdeathcubex.utils.RankSystem;
import com.xdeathcubex.utils.TimeUnit;
import com.xdeathcubex.utils.UUIDFetcher;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Mute extends Command {

    public Mute() {
        super("Mute");
    }

    String mutedUser;
    String mutedBy;
    String reason;
    long date;

    @Override
    public void execute(CommandSender cs, String[] args) {
        if(args.length == 0){
            if(cs instanceof ProxiedPlayer){
                ProxiedPlayer p = (ProxiedPlayer)cs;
                if (RankSystem.hasMod(UUIDFetcher.getUUID(p.getName()))) {
                    p.sendMessage(new TextComponent(BanSystem.prefix + "Verwendung: /mute <player> <reason>"));
                } else {
                    p.sendMessage(new TextComponent(BanSystem.prefix + "Keine Rechte!"));
                }
            } else {
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "Verwendung: /mute <player> <reason>"));
            }
        } else if(args.length >= 2){
            if(cs instanceof ProxiedPlayer){
                ProxiedPlayer p = (ProxiedPlayer)cs;
                if(!RankSystem.hasMod(UUIDFetcher.getUUID(p.getName()))){
                    return;
                }
            }
            StringBuilder sb = new StringBuilder();
            for(int i = 1; i < args.length; i++){
                sb.append(args[i]).append(" ");
            }
            reason = sb.toString().trim();
            mutedUser = args[0];
            date = System.currentTimeMillis();
            if(cs instanceof ProxiedPlayer){
                ProxiedPlayer p = (ProxiedPlayer)cs;
                mutedBy = p.getName();
            } else {
                mutedBy = "§4BungeeConsole";
            }
            String uuid = UUIDFetcher.getUUID(mutedUser);
            if(RankSystem.hasMod(uuid)){
                if(cs instanceof ProxiedPlayer){
                    ProxiedPlayer p = (ProxiedPlayer)cs;
                    if(!RankSystem.hasAdmin(UUIDFetcher.getUUID(p.getName()))){
                        p.sendMessage(new TextComponent(BanSystem.prefix + "Du darfst keine anderen Teammitglieder bannen / muten!"));
                        return;
                    }
                }
            }
            if(!MySQL.isCurrentlyMuted(uuid)){
                MySQL.muteUser(uuid, mutedBy, reason, date);
                for (ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                    if(RankSystem.hasMod(UUIDFetcher.getUUID(all.getName()))) {
                        all.sendMessage(new TextComponent(BanSystem.prefix + "§a" + mutedUser + " §7wurde von §c" + mutedBy + " §7gemutet§c!"));
                        all.sendMessage(new TextComponent(BanSystem.prefix + "§e" + reason + " §7| §a[permanent]"));
                    }
                }
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "§a" + mutedUser + " §7wurde von §c" + mutedBy + " §7gemutet§c!"));
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + reason + " §7| §a[permanent]"));
                ProxiedPlayer p1 = ProxyServer.getInstance().getPlayer(args[0]);
                if (p1 != null){
                    p1.sendMessage(new TextComponent(BanSystem.prefix + "Herzlichen Glückwunsch! Du wurdest soeben permanent gemutet!"));
                }
            } else {
                if(cs instanceof ProxiedPlayer){
                    ProxiedPlayer p = (ProxiedPlayer)cs;
                    p.sendMessage(new TextComponent(BanSystem.prefix + "Dieser Spieler ist bereits gemutet."));
                } else {
                    ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "Dieser Spieler ist beretis gemutet."));
                }
            }
        }
    }
}
