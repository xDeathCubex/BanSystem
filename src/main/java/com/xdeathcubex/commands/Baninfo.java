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

import java.text.SimpleDateFormat;
import java.util.Date;

public class Baninfo extends Command {

    public Baninfo(String cmd) {
        super(cmd);
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        if(cs instanceof ProxiedPlayer){
            ProxiedPlayer p = (ProxiedPlayer)cs;
            if(!RankSystem.hasMod(p.getUniqueId().toString().replaceAll("-",""))){
                p.sendMessage(new TextComponent(BanSystem.prefix + "§cKeine Rechte!"));
                return;
            }
        }
        if(args.length != 1){
            if(cs instanceof ProxiedPlayer){
                ProxiedPlayer p = (ProxiedPlayer)cs;
                    p.sendMessage(new TextComponent(BanSystem.prefix + "§cVerwendung: §e/baninfo <Name>"));
            } else {
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "§cVerwendung: §e/baninfo <Name>"));
            }
        } else {
            String uuid = UUIDFetcher.getUUID(args[0]);
            if(cs instanceof ProxiedPlayer){
                ProxiedPlayer p = (ProxiedPlayer)cs;
                    if(uuid == null){
                        p.sendMessage(new TextComponent(BanSystem.prefix + "§cDieser Spieler existiert nicht."));
                    } else {
                        String prefix = RankSystem.getPrefix(uuid);
                        if(!MySQL.isCurrentlyBanned(uuid) && !MySQL.isCurrentlyMuted(uuid)){
                            p.sendMessage(new TextComponent("§7[§cBAN/MUTE§7] §eÜbersicht für §a" + prefix));
                            p.sendMessage(new TextComponent("§7[§cBAN§7] §a" + prefix + " §7besitzt aktuell keinen Ban"));
                            p.sendMessage(new TextComponent("§7[§cMUTE§7] §a" + prefix + " §7besitzt aktuell keinen Mute"));
                        } else {
                            if (MySQL.isCurrentlyBanned(uuid)) {
                                SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                                p.sendMessage(new TextComponent("§7[§cBAN§7] §eÜbersicht für §a" + prefix));
                                p.sendMessage(new TextComponent("§7[§cBAN§7] §7Gebannt von§8: §c" + RankSystem.getPrefix(MySQL.getCurrentBan("bannedBy", uuid))));
                                p.sendMessage(new TextComponent("§7[§cBAN§7] §7Grund§8: §c" + MySQL.getCurrentBan("Reason", uuid)));
                                p.sendMessage(new TextComponent("§7[§cBAN§7] §7Gebannt am§8: §c" + format1.format(new Date(Long.parseLong(MySQL.getCurrentBan("startTime", uuid))))));
                                p.sendMessage(new TextComponent("§7[§cBAN§7] §7Dauer§8: §c" + MySQL.getCurrentBan("endTime", uuid)));
                                p.sendMessage(new TextComponent("§7[§cBAN§7] §7Verbleibende Zeit: §c" + TimeUnit.getTime()));
                            }
                            if(MySQL.isCurrentlyMuted(uuid)){
                                SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                                p.sendMessage(new TextComponent("§7[§cMUTE§7] §eÜbersicht für §a" + prefix));
                                p.sendMessage(new TextComponent("§7[§cMUTE§7] §7Gemuted von§8: §c" + RankSystem.getPrefix(MySQL.getCurrentMute("mutedBy", uuid))));
                                p.sendMessage(new TextComponent("§7[§cMUTE§7] §7Grund§8: §c" + MySQL.getCurrentMute("Reason", uuid)));
                                p.sendMessage(new TextComponent("§7[§cMUTE§7] §7Gebannt am§8: §c" + format1.format(new Date(Long.parseLong(MySQL.getCurrentMute("startTime", uuid))))));
                                p.sendMessage(new TextComponent("§7[§cMUTE§7] §7Dauer§8: §c" + MySQL.getCurrentMute("endTime", uuid)));
                                p.sendMessage(new TextComponent("§7[§cMUTE§7] §7Verbleibende Zeit: §c" + TimeUnit.getTime()));

                            }
                        }
                    }
            } else {
                if(uuid == null){
                    ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "§cDieser Spieler existiert nicht."));
                } else {
                    String prefix = RankSystem.getPrefix(uuid);
                    if(!MySQL.isCurrentlyBanned(uuid) && !MySQL.isCurrentlyMuted(uuid)){
                        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§7[§cBAN/MUTE§7] §eÜbersicht für §a" + prefix));
                        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§7[§cBAN§7] §a" + prefix + " §7besitzt aktuell keinen Ban"));
                        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§7[§cMUTE§7] §a" + prefix + " §7besitzt aktuell keinen Mute"));
                    } else {
                        if (MySQL.isCurrentlyBanned(uuid)) {
                            ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§7[§cBAN§7] §eÜbersicht für §a" + prefix));
                            ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§7[§cBAN§7] §7Gebannt von§8: §c" + RankSystem.getPrefix(MySQL.getCurrentBan("bannedBy", uuid))));
                            ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§7[§cBAN§7] §7Grund§8: §c" + MySQL.getCurrentBan("Reason", uuid)));
                            ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§7[§cBAN§7] §7Dauer§8: §c" + MySQL.getCurrentBan("endTime", uuid)));
                        }
                        if(MySQL.isCurrentlyMuted(uuid)){
                            ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§7[§cMUTE§7] §eÜbersicht für §a" + prefix));
                            ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§7[§cMUTE§7] §7Gemuted von§8: §c" + RankSystem.getPrefix(MySQL.getCurrentMute("mutedBy", uuid))));
                            ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§7[§cMUTE§7] §7Grund§8: §c" + MySQL.getCurrentMute("Reason", uuid)));
                            ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§7[§cMUTE§7] §7Dauer§8: §c" + MySQL.getCurrentMute("endTime", uuid)));
                        }
                    }
                }
            }
        }
    }
}
