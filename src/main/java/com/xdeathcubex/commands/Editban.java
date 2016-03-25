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

public class Editban extends Command{

    public Editban() {
        super("editban");
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        if(args.length < 2){
            if(cs instanceof ProxiedPlayer){
                ProxiedPlayer p = (ProxiedPlayer)cs;
                if(RankSystem.hasMod(p.getUniqueId().toString().replaceAll("-",""))){
                    p.sendMessage(new TextComponent(BanSystem.prefix + "§cVerwendung: §e/editmute <Spieler> <Grund>"));
                } else {
                    p.sendMessage(new TextComponent(BanSystem.prefix + "§cKeine Rechte!"));
                }
            } else {
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "§cVerwendung: §e/editmute <Spieler> <Grund>"));
            }
        } else {
            if(cs instanceof ProxiedPlayer){
                ProxiedPlayer p = (ProxiedPlayer)cs;
                if (RankSystem.hasMod(p.getUniqueId().toString().replaceAll("-",""))){
                    String uuid = UUIDFetcher.getUUID(args[0]);
                    if(uuid != null){
                        if(MySQL.isCurrentlyBanned(uuid)){
                            StringBuilder sb = new StringBuilder();
                            for(int i = 1; i < args.length; i++){
                                sb.append(args[i]).append(" ");
                            }
                            String reason = sb.toString().trim();
                            MySQL.changeBanReason(uuid, reason);
                            for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()){
                                if(RankSystem.hasMod(p.getUniqueId().toString().replaceAll("-",""))){
                                    all.sendMessage(new TextComponent(BanSystem.prefix + "§7Mutegrund bei §a" + RankSystem.getPrefix(uuid) + " §7wurde von " + p.getDisplayName() + " §7geändert:"));
                                    all.sendMessage(new TextComponent(BanSystem.prefix + "§7" + reason));
                                }
                            }
                        } else {
                            p.sendMessage(new TextComponent(BanSystem.prefix + "§cDieser Spieler ist momentan nicht gebannt."));
                        }
                    } else {
                        p.sendMessage(new TextComponent(BanSystem.prefix + "§cDieser Spieler existiert nicht."));
                    }
                } else {
                    p.sendMessage(new TextComponent(BanSystem.prefix + "§cKeine Rechte!"));
                }
            } else {
                String uuid = UUIDFetcher.getUUID(args[0]);
                if(uuid != null){
                    if(MySQL.isCurrentlyBanned(uuid)){
                        StringBuilder sb = new StringBuilder();
                        for(int i = 1; i < args.length; i++){
                            sb.append(args[i]).append(" ");
                        }
                        String reason = sb.toString().trim();
                        MySQL.changeBanReason(uuid, reason);
                        for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()){
                            if(RankSystem.hasMod(all.getUniqueId().toString().replaceAll("-",""))){
                                all.sendMessage(new TextComponent(BanSystem.prefix + "§7Mutegrund bei §a" + RankSystem.getPrefix(uuid) + " §7wurde von §4BungeeConsole §7geändert:"));
                                all.sendMessage(new TextComponent(BanSystem.prefix + "§7" + reason));
                            }
                        }
                    } else {
                        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "§cDieser Spieler ist momentan nicht gebannt."));
                    }
                } else {
                    ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "§cDieser Spieler existiert nicht."));
                }
            }
        }
    }
}
