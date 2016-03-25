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
                if (RankSystem.hasMod(p.getUniqueId().toString().replaceAll("-",""))){
                    p.sendMessage(new TextComponent(BanSystem.prefix + "§cVerwendung: §e/mute <player> <reason>"));
                } else {
                    p.sendMessage(new TextComponent(BanSystem.prefix + "§cKeine Rechte!"));
                }
            } else {
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "§cVerwendung: §e/mute <player> <reason>"));
            }
        } else if(args.length >= 2){
            if(cs instanceof ProxiedPlayer){
                ProxiedPlayer p = (ProxiedPlayer)cs;
                if(!RankSystem.hasMod(p.getUniqueId().toString().replaceAll("-",""))){
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
                mutedBy = p.getUniqueId().toString().replaceAll("-","");
            } else {
                mutedBy = "§4BungeeConsole";
            }
            String uuid = UUIDFetcher.getUUID(mutedUser);
            if(RankSystem.hasMod(uuid)){
                if(cs instanceof ProxiedPlayer){
                    ProxiedPlayer p = (ProxiedPlayer)cs;
                    if(!RankSystem.hasAdmin(p.getUniqueId().toString().replaceAll("-",""))){
                        p.sendMessage(new TextComponent(BanSystem.prefix + "§cDu darfst keine anderen Teammitglieder muten!"));
                        return;
                    }
                }
            }
            if(!MySQL.isCurrentlyMuted(uuid)){
                MySQL.muteUser(uuid, mutedBy, reason, date);
                for (ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                    if(RankSystem.hasMod(all.getUniqueId().toString().replaceAll("-",""))){
                        all.sendMessage(new TextComponent(BanSystem.prefix + "§a" + RankSystem.getPrefix(uuid) + " §7wurde von §c" + RankSystem.getPrefix(mutedBy) + " §7gemutet§c!"));
                        all.sendMessage(new TextComponent(BanSystem.prefix + "§7" + reason + " §c| §7Dauer: §cpermanent"));
                    }
                }
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "§a" + RankSystem.getPrefix(uuid) + " §7wurde von §c" + RankSystem.getPrefix(mutedBy) + " §7gemutet§c!"));
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "§7" + reason + " §c| §7Dauer: §cpermanent"));
                ProxiedPlayer p1 = ProxyServer.getInstance().getPlayer(args[0]);
            } else {
                if(cs instanceof ProxiedPlayer){
                    ProxiedPlayer p = (ProxiedPlayer)cs;
                    p.sendMessage(new TextComponent(BanSystem.prefix + "§cDieser Spieler ist bereits gemutet."));
                } else {
                    ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "§cDieser Spieler ist beretis gemutet."));
                }
            }
        }
    }
}
