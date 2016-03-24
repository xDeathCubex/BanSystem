package com.xdeathcubex.commands;

import com.xdeathcubex.BanSystem;
import com.xdeathcubex.mysql.MySQL;
import com.xdeathcubex.utils.RankSystem;
import com.xdeathcubex.utils.UUIDFetcher;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Baninfo extends Command {

    public Baninfo() {
        super("baninfo");
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        if(args.length != 1){
            if(cs instanceof ProxiedPlayer){
                ProxiedPlayer p = (ProxiedPlayer)cs;
                if(RankSystem.hasMod(UUIDFetcher.getUUID(p.getName()))){
                    p.sendMessage(new TextComponent(BanSystem.prefix + "Verwendung: §6/baninfo <Name>"));
                } else {
                    p.sendMessage(new TextComponent(BanSystem.prefix + "§cKeine Rechte!"));
                }
            } else {
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "Verwendung: §6/baninfo <Name>"));
            }
        } else {
            String uuid = UUIDFetcher.getUUID(args[0]);
            if(cs instanceof ProxiedPlayer){
                ProxiedPlayer p = (ProxiedPlayer)cs;
                if(RankSystem.hasMod(UUIDFetcher.getUUID(p.getName()))){
                    if(uuid == null){
                        p.sendMessage(new TextComponent(BanSystem.prefix + "Dieser Spieler existiert nicht."));
                    } else {
                        if(!MySQL.isCurrentlyBanned(uuid) && !MySQL.isCurrentlyMuted(uuid)){
                            p.sendMessage(new TextComponent(BanSystem.prefix + "§a" + args[0] + " ist momentan nicht gemutet / gebannt."));
                        } else {
                            if (MySQL.isCurrentlyBanned(uuid)) {
                                SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                                p.sendMessage(new TextComponent("§cTyp: §6Ban"));
                                p.sendMessage(new TextComponent("§cErstellt von: §e" + MySQL.getCurrentBan("bannedBy", uuid)));
                                p.sendMessage(new TextComponent("§cErstellt am: §e" + format1.format(new Date(Long.parseLong(MySQL.getCurrentBan("startTime", uuid))))));
                                p.sendMessage(new TextComponent("§cGrund: §e" + MySQL.getCurrentBan("Reason", uuid)));
                                p.sendMessage(new TextComponent("§cZeit: §e" + MySQL.getCurrentBan("endTime", uuid)));
                            }
                            if(MySQL.isCurrentlyMuted(uuid)){
                                SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                                p.sendMessage(new TextComponent("§cTyp: §6Mute"));
                                p.sendMessage(new TextComponent("§cErstellt von: §e" + MySQL.getCurrentMute("mutedBy", uuid)));
                                p.sendMessage(new TextComponent("§cErstellt am: §e" + format1.format(new Date(Long.parseLong(MySQL.getCurrentMute("startTime", uuid))))));
                                p.sendMessage(new TextComponent("§cGrund: §e" + MySQL.getCurrentMute("Reason", uuid)));
                                p.sendMessage(new TextComponent("§cZeit: §e" + MySQL.getCurrentMute("endTime", uuid)));
                            }
                        }
                    }
                } else {
                    p.sendMessage(new TextComponent(BanSystem.prefix + "§cKeine Rechte!"));
                }
            } else {
                if(uuid == null){
                    ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "Dieser Spieler existiert nicht."));
                } else {
                    if(!MySQL.isCurrentlyBanned(uuid) && !MySQL.isCurrentlyMuted(uuid)){
                        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + args[0] + "ist momentan nicht gemutet / gebannt."));
                    } else {
                        if (MySQL.isCurrentlyBanned(uuid)) {
                            SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                            ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§cTyp: §6Ban"));
                            ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§cErstellt von: " + MySQL.getCurrentBan("bannedBy", uuid)));
                            ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§cErstellt am: " + format1.format(new Date(Long.parseLong(MySQL.getCurrentBan("startTime", uuid))))));
                            ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§cGrund: " + MySQL.getCurrentBan("Reason", uuid)));
                            ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§cZeit: " + MySQL.getCurrentBan("endTime", uuid)));
                        }
                        if(MySQL.isCurrentlyMuted(uuid)){
                            SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                            ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§cTyp: §6Mute"));
                            ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§cErstellt von: " + MySQL.getCurrentMute("mutedBy", uuid)));
                            ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§cErstellt am: " + format1.format(new Date(Long.parseLong(MySQL.getCurrentMute("startTime", uuid))))));
                            ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§cGrund: " + MySQL.getCurrentMute("Reason", uuid)));
                            ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§cZeit: " + MySQL.getCurrentMute("endTime", uuid)));
                        }
                    }
                }
            }
        }
    }
}
