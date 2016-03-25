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
import java.util.ArrayList;
import java.util.Date;

public class Banlog extends Command{

    public Banlog() {
        super("banlog");
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        if(args.length == 0){
            if(cs instanceof ProxiedPlayer){
                ProxiedPlayer p = (ProxiedPlayer)cs;
                p.sendMessage(new TextComponent(BanSystem.prefix + "§cVerwendung: §e/banlog <user>"));
            } else {
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "§cVerwendung: §e/banlog <Name>"));
            }
        } else {
            String uuid = UUIDFetcher.getUUID(args[0]);
            if(uuid == null){
                if(cs instanceof ProxiedPlayer){
                    ProxiedPlayer p = (ProxiedPlayer)cs;
                    p.sendMessage(new TextComponent(BanSystem.prefix + "Der Spieler §a" + args[0] + " §7existiert nicht."));
                } else {
                    ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "Der Spieler §a" + args[0] + " §7existiert nicht."));
                }
            } else {
                if(cs instanceof ProxiedPlayer){

                    ProxiedPlayer p = (ProxiedPlayer)cs;
                    p.sendMessage(new TextComponent("§eBanlog von §a" + RankSystem.getPrefix(uuid)));
                    SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

                    /** BANS **/
                    ArrayList<String> bannedBy = MySQL.getPastBans("bannedBy", uuid);
                    ArrayList<String> startTime = MySQL.getPastBans("startTime", uuid);
                    ArrayList<String> reason = MySQL.getPastBans("Reason", uuid);
                    ArrayList<String> endTime = MySQL.getPastBans("endTime", uuid);

                    for(int i = 0; i < bannedBy.size(); i++){
                            p.sendMessage(new TextComponent("§7Typ: §cban §7Erstellt§8: §c" + format1.format(new Date(Long.parseLong(startTime.get(i)))) + " §7Grund: §c" + reason.get(i) + " §7Von: §a" + RankSystem.getPrefix(bannedBy.get(i)) + " §7Zeit: " + endTime.get(i)));
                    }

                    /** MUTES **/
                    ArrayList<String> bannedBy1 = MySQL.getPastMutes("mutedBy", uuid);
                    ArrayList<String> startTime1 = MySQL.getPastMutes("startTime", uuid);
                    ArrayList<String> reason1 = MySQL.getPastMutes("Reason", uuid);
                    ArrayList<String> endTime1 = MySQL.getPastMutes("endTime", uuid);

                    for(int i = 0; i < bannedBy1.size(); i++){
                        p.sendMessage(new TextComponent("§7Typ: §cmute §7Erstellt§8: §c" + format1.format(new Date(Long.parseLong(startTime1.get(i)))) + " §7Grund: §c" + reason1.get(i) + " §7Von: §a" + RankSystem.getPrefix(bannedBy1.get(i)) + " §7Zeit: " + endTime1.get(i)));
                    }

                } else {
                    ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + ""));
                }
            }
        }
    }
}
