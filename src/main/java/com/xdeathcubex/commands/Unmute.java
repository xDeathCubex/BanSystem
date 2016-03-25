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

public class Unmute extends Command {

    public Unmute() {
        super("unmute");
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        if (args.length == 0) {
            if (cs instanceof ProxiedPlayer) {
                ProxiedPlayer p = (ProxiedPlayer) cs;
                if(RankSystem.hasMod(p.getUniqueId().toString().replaceAll("-",""))){
                    p.sendMessage(new TextComponent(BanSystem.prefix + "§cVerwendung: §e/unmute <user>"));
                } else {
                    p.sendMessage(new TextComponent(BanSystem.prefix + "§cKeine Rechte!"));
                }
            } else {
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "§cVerwendung: §e/unmute <user>"));
            }
        } else {
            String uuid = UUIDFetcher.getUUID(args[0]);
            if (uuid == null) {
                if (cs instanceof ProxiedPlayer) {
                    ProxiedPlayer p = (ProxiedPlayer) cs;
                    if(RankSystem.hasMod(p.getUniqueId().toString().replaceAll("-",""))){
                        p.sendMessage(new TextComponent(BanSystem.prefix + "§7Dieser Spieler existiert nicht."));
                    } else {
                        p.sendMessage(new TextComponent(BanSystem.prefix + "§cKeine Rechte!"));
                    }
                } else {
                    ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "§7Dieser Spieler existiert nicht."));
                }
            } else {
                if (!MySQL.isCurrentlyMuted(uuid)) {
                    if (cs instanceof ProxiedPlayer) {
                        ProxiedPlayer p = (ProxiedPlayer) cs;
                        if(RankSystem.hasMod(p.getUniqueId().toString().replaceAll("-",""))){
                            p.sendMessage(new TextComponent(BanSystem.prefix + "§a" + args[0] + " §7ist nicht gemutet."));
                        } else {
                            p.sendMessage(new TextComponent(BanSystem.prefix + "§cKeine Rechte!"));
                        }
                    } else {
                        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "§a"  + RankSystem.getPrefix(uuid) + " §7ist nicht gemutet."));
                    }
                } else {
                    String name = "§4BungeeConsole";
                    if(cs instanceof ProxiedPlayer){
                        ProxiedPlayer p = (ProxiedPlayer) cs;
                        if(RankSystem.hasMod(p.getUniqueId().toString().replaceAll("-",""))){
                            name = p.getDisplayName();
                        } else {
                            p.sendMessage(new TextComponent(BanSystem.prefix + "§cKeine Rechte!"));
                            return;
                        }
                    }
                    MySQL.unmuteUser(uuid);
                    for (ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                        if(RankSystem.hasMod(all.getUniqueId().toString().replaceAll("-",""))){
                            all.sendMessage(new TextComponent(BanSystem.prefix + "§a" + RankSystem.getPrefix(uuid) + " §7wurde von §c" + name + " §7entmutet§c!"));
                        }
                    }
                }
            }
        }
    }
}
