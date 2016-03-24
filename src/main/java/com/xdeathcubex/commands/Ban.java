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

public class Ban extends Command {

    public Ban() {
        super("Ban");
    }

    String bannedUser;
    String bannedBy;
    String reason;
    long date;

    @Override
    public void execute(CommandSender cs, String[] args) {
        if(args.length == 0){
            if(cs instanceof ProxiedPlayer){
                ProxiedPlayer p = (ProxiedPlayer)cs;
                if(RankSystem.hasMod(UUIDFetcher.getUUID(p.getName()))){
                    p.sendMessage(new TextComponent(BanSystem.prefix + "Verwendung: /ban <player> <reason>"));
                } else {
                    p.sendMessage(new TextComponent(BanSystem.prefix + "§cKeine Rechte!"));
                }
            } else {
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "Verwendung1: /ban <player> <reason>"));
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
            bannedUser = args[0];
            date = System.currentTimeMillis();
            if(cs instanceof ProxiedPlayer){
                ProxiedPlayer p = (ProxiedPlayer)cs;
                if(RankSystem.hasMod(UUIDFetcher.getUUID(p.getName()))){
                    bannedBy = p.getName();
                }
            } else {
                bannedBy = "§4BungeeConsole";
            }
            String uuid = UUIDFetcher.getUUID(bannedUser);
            if(RankSystem.hasMod(uuid)){
                if(cs instanceof ProxiedPlayer){
                    ProxiedPlayer p = (ProxiedPlayer)cs;
                    if(!RankSystem.hasAdmin(UUIDFetcher.getUUID(p.getName()))){
                        p.sendMessage(new TextComponent(BanSystem.prefix + "Du darfst keine anderen Teammitglieder bannen / muten!"));
                        return;
                    }
                }
            }
            if(!MySQL.isCurrentlyBanned(uuid)){
                MySQL.banUser(uuid, bannedBy, reason, date);
                for (ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                    if(RankSystem.hasMod(UUIDFetcher.getUUID(all.getName()))) {
                        all.sendMessage(new TextComponent(BanSystem.prefix + "§a" + bannedUser + " §7wurde von §c" + bannedBy + " §7gebannt§c!"));
                        all.sendMessage(new TextComponent(BanSystem.prefix + "§e" + reason + " §7| §a[permanent]"));
                    }
                }
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "§a" + bannedUser + " §7wurde von §c" + bannedBy + " §7gebannt§c!"));
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + reason + " §7| §a[permanent]"));
                ProxiedPlayer p1 = ProxyServer.getInstance().getPlayer(args[0]);
                if (p1 != null && TimeUnit.checkBan(uuid)){
                    p1.disconnect(new TextComponent(
                            "§cDu wurdest vom LogMC Netzwerk gebannt." +
                            "\n" +
                            "\n§7Grund§8: §e" + MySQL.getCurrentBan("Reason", uuid) +
                            "\n§7Verbleibende Zeit: §e" + TimeUnit.getTime()));
                }
            } else {
                if(cs instanceof ProxiedPlayer){
                    ProxiedPlayer p = (ProxiedPlayer)cs;
                    p.sendMessage(new TextComponent(BanSystem.prefix + "Dieser Spieler ist bereits gebannt."));
                } else {
                    ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "Dieser Spieler ist beretis gebannt."));
                }
            }
        }
    }
}
