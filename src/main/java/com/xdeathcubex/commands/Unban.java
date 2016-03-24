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

public class Unban extends Command {

    public Unban() {
        super("unban");
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        if (args.length == 0) {
            if (cs instanceof ProxiedPlayer) {
                ProxiedPlayer p = (ProxiedPlayer) cs;
                if (RankSystem.hasMod(UUIDFetcher.getUUID(p.getName()))) {
                    p.sendMessage(new TextComponent(BanSystem.prefix + "Verwendung: §6/unban <user>"));
                } else {
                    p.sendMessage(new TextComponent(BanSystem.prefix + "§cKeine Rechte!"));
                }
            } else {
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "Verwendung: §6/unban <user>"));
            }
        } else {
            String uuid = UUIDFetcher.getUUID(args[0]);
            if (uuid == null) {
                if (cs instanceof ProxiedPlayer) {
                    ProxiedPlayer p = (ProxiedPlayer) cs;
                    if (RankSystem.hasMod(UUIDFetcher.getUUID(p.getName()))) {
                        p.sendMessage(new TextComponent(BanSystem.prefix + "Der Spieler §a" + args[0] + " §7existiert nicht."));
                    } else {
                        p.sendMessage(new TextComponent(BanSystem.prefix + "§cKeine Rechte!"));
                    }
                } else {
                    ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "Der Spieler §a" + args[0] + " §7existiert nicht."));
                }
            } else {
                if (!MySQL.isCurrentlyBanned(uuid)) {
                    if (cs instanceof ProxiedPlayer) {
                        ProxiedPlayer p = (ProxiedPlayer) cs;
                        if (RankSystem.hasMod(UUIDFetcher.getUUID(p.getName()))) {
                            p.sendMessage(new TextComponent(BanSystem.prefix + args[0] + " ist nicht gebannt."));
                        } else {
                            p.sendMessage(new TextComponent(BanSystem.prefix + "§cKeine Rechte!"));
                        }
                    } else {
                        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + args[0] + " ist nicht gebannt."));
                    }
                } else {
                    String name = "§4BungeeConsole";
                    if (cs instanceof ProxiedPlayer) {
                        ProxiedPlayer p = (ProxiedPlayer) cs;
                        if (RankSystem.hasMod(UUIDFetcher.getUUID(p.getName()))) {
                            name = p.getDisplayName();
                        } else {
                            p.sendMessage(new TextComponent(BanSystem.prefix + "§cKeine Rechte!"));
                            return;
                        }
                    }
                    MySQL.unbanUser(uuid);
                    for (ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                        if (RankSystem.hasMod(UUIDFetcher.getUUID(all.getName()))) {
                            all.sendMessage(new TextComponent(BanSystem.prefix + "§a" + args[0] + " §7wurde von §c" + name + " §7entbannt§c!"));
                        }
                    }
                    ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "§a" + args[0] + " §7wurde von §c" + name + " §7entbannt§c!"));
                }
            }
        }
    }
}
