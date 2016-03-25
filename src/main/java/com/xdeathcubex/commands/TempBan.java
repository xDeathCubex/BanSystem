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

public class TempBan extends Command{

    public TempBan(String cmd) {
        super(cmd);
    }

    String bannedUser;
    String bannedBy;
    String reason;
    String banTime;
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
                    p.sendMessage(new TextComponent(BanSystem.prefix + "§cVerwendung: §e/tempban <player> <time> <reason>"));
            } else {
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "§cVerwendung: §e/tempban <player> <time> <reason>"));
            }
        } else if(args.length >= 3){
            StringBuilder sb = new StringBuilder();
            for(int i = 2; i < args.length; i++){
                sb.append(args[i]).append(" ");
            }
            reason = sb.toString().trim();
            if(cs instanceof ProxiedPlayer){
                ProxiedPlayer p = (ProxiedPlayer)cs;
                bannedBy = p.getUniqueId().toString().replaceAll("-","");
            } else {
                bannedBy = "§4BungeeConsole";
            }
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
                banTime = args[1].toLowerCase();
                bannedUser = args[0];
                date = System.currentTimeMillis();
                String uuid = UUIDFetcher.getUUID(bannedUser);
                if(RankSystem.hasMod(uuid)){
                    if(cs instanceof ProxiedPlayer){
                        ProxiedPlayer p = (ProxiedPlayer)cs;
                        if(!RankSystem.hasAdmin(p.getUniqueId().toString().replaceAll("-",""))){
                            p.sendMessage(new TextComponent(BanSystem.prefix + "Du darfst keine anderen Teammitglieder bannen!"));
                            return;
                        }
                    }
                }
                if(!MySQL.isCurrentlyBanned(uuid)){
                    MySQL.tempbanUser(uuid, bannedBy, reason, date, banTime);
                    for (ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                        if (RankSystem.hasMod(all.getUniqueId().toString().replaceAll("-",""))){
                            all.sendMessage(new TextComponent(BanSystem.prefix + "§a" + RankSystem.getPrefix(uuid) + " §7wurde von §c" + RankSystem.getPrefix(bannedBy) + " §7gebannt§c!"));
                            all.sendMessage(new TextComponent(BanSystem.prefix + "§7" + reason + " §a["+banTime+"]"));
                        }
                    }
                    ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "§a" + RankSystem.getPrefix(uuid) + " §7wurde von §c" + RankSystem.getPrefix(bannedBy) + " §7gebannt§c!"));
                    ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "§7" + reason + " §a["+banTime+"]"));
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
                        p.sendMessage(new TextComponent(BanSystem.prefix + "§cDieser Spieler ist bereits gebannt."));
                    } else {
                        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "§cDieser Spieler ist beretis gebannt."));
                    }
                }
            }
        }
    }
}
