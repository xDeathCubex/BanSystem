package com.xdeathcubex.commands;


import com.mysql.jdbc.TimeUtil;
import com.sun.xml.internal.txw2.TXW;
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

public class TempMute extends Command{

    public TempMute(String cmd) {
        super(cmd);
    }

    String mutedUser;
    String mutedBy;
    String reason;
    String muteTime;
    long date;

    @Override
    public void execute(CommandSender cs, String[] args) {
        if(cs instanceof ProxiedPlayer){
            ProxiedPlayer p = (ProxiedPlayer)cs;
            if(!RankSystem.hasMod(p.getUniqueId().toString().replaceAll("-",""))){
                p.sendMessage(new TextComponent(BanSystem.prefix + "§cKeine Rechte!"));
                return;
            }
        }
        if(args.length == 0){
            if(cs instanceof ProxiedPlayer){
                ProxiedPlayer p = (ProxiedPlayer)cs;
                    p.sendMessage(new TextComponent(BanSystem.prefix + "§cVerwendung: §e/tempmute <player> <time> <reason>"));
            } else {
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "§cVerwendung: §e/tempmute <player> <time> <reason>"));
            }
        } else if(args.length >= 3){
            if(cs instanceof ProxiedPlayer){
                ProxiedPlayer p = (ProxiedPlayer)cs;
                if(!RankSystem.hasMod(p.getUniqueId().toString().replaceAll("-",""))){
                    return;
                }
            }
            StringBuilder sb = new StringBuilder();
            for(int i = 2; i < args.length; i++){
                sb.append(args[i]).append(" ");
            }
            reason = sb.toString().trim();

            String timeVariable = args[1].replaceAll("[0-9]", "");
            if ((!timeVariable.equalsIgnoreCase("s")) && (!timeVariable.equalsIgnoreCase("m")) &&
                    (!timeVariable.equalsIgnoreCase("h")) && (!timeVariable.equalsIgnoreCase("d")) &&
                    (!timeVariable.equalsIgnoreCase("y"))) {
                if ((cs instanceof ProxiedPlayer)) {
                    ProxiedPlayer p = (ProxiedPlayer) cs;
                    p.sendMessage(new TextComponent(BanSystem.prefix + "§cInvalide Zeitangabe! Möglichkeiten§8: §a s, m, h, d, y"));
                } else {
                    ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "§cInvalide Zeitangabe! Möglichkeiten§8: §a s, m, h, d, y"));
                }
            } else {
                muteTime = args[1].toLowerCase();
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
                            p.sendMessage(new TextComponent(BanSystem.prefix + "§cDu darfst keine Teammitglieder muten"));
                            return;
                        }
                    }
                }
                if(!MySQL.isCurrentlyMuted(uuid)){
                    MySQL.tempmuteUser(uuid, mutedBy, reason, date, muteTime);
                    for (ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                        if(RankSystem.hasMod(all.getUniqueId().toString().replaceAll("-",""))){
                            all.sendMessage(new TextComponent(BanSystem.prefix + "§a" + RankSystem.getPrefix(uuid) + " §7wurde von §c" + RankSystem.getPrefix(mutedBy) + " §7gemutet§c!"));
                            all.sendMessage(new TextComponent(BanSystem.prefix + "§7" + reason + " §a[" + muteTime + "]"));
                        }
                    }
                    ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "§a" + RankSystem.getPrefix(uuid) + " §7wurde von §c" + RankSystem.getPrefix(mutedBy) + " §7gemutet§c!"));
                    ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "§7" + reason + " §a["+muteTime+"]"));
                    ProxiedPlayer p1 = ProxyServer.getInstance().getPlayer(args[0]);
                } else {
                    if(cs instanceof ProxiedPlayer){
                        ProxiedPlayer p = (ProxiedPlayer)cs;
                        p.sendMessage(new TextComponent(BanSystem.prefix + "§cDieser Spieler ist bereits gemutet."));
                    } else {
                        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix +"§cDieser Spieler ist beretis gemutet."));
                    }
                }
            }
        }
    }
}
