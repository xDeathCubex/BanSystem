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
        if(cs instanceof ProxiedPlayer){
            ProxiedPlayer p = (ProxiedPlayer)cs;
            if(!RankSystem.hasMod(p.getUniqueId().toString().replaceAll("-",""))){
                p.sendMessage(new TextComponent(BanSystem.prefix + "§cKeine Rechte!"));
                return;
            }
        }
        if (args.length == 0) {
            if (cs instanceof ProxiedPlayer) {
                ProxiedPlayer p = (ProxiedPlayer) cs;
                    p.sendMessage(new TextComponent(BanSystem.prefix + "§cVerwendung: §e/unmute <Spieler> <Grund>"));
            } else {
                ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "§cVerwendung: §e/unmute <Spieler> <Grund>"));
            }
        } else {
            String uuid = UUIDFetcher.getUUID(args[0]);
            if (uuid == null) {
                if (cs instanceof ProxiedPlayer) {
                    ProxiedPlayer p = (ProxiedPlayer) cs;
                        p.sendMessage(new TextComponent(BanSystem.prefix + "§7Dieser Spieler existiert nicht."));
                } else {
                    ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "§7Dieser Spieler existiert nicht."));
                }
            } else {
                if (!MySQL.isCurrentlyMuted(uuid)) {
                    if (cs instanceof ProxiedPlayer) {
                        ProxiedPlayer p = (ProxiedPlayer) cs;
                            p.sendMessage(new TextComponent(BanSystem.prefix + "§a" + args[0] + " §7ist nicht gemutet."));
                    } else {
                        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(BanSystem.prefix + "§a"  + RankSystem.getPrefix(uuid) + " §7ist nicht gemutet."));
                    }
                } else {
                    if(MySQL.getCurrentMute("endTime", uuid).equals("permanent")){
                        if(cs instanceof ProxiedPlayer){
                            ProxiedPlayer p = (ProxiedPlayer)cs;
                            if(!RankSystem.hasSrMod(p.getUniqueId().toString().replaceAll("-",""))){
                                p.sendMessage(new TextComponent(BanSystem.prefix + "§cDu darfst keine permanent gemutete Spieler entmute!"));
                                return;
                            }
                        }
                    }
                    StringBuilder sb = new StringBuilder();
                    for(int i = 1; i < args.length; i++){
                        sb.append(args[i]).append(" ");
                    }
                    String reason = sb.toString().trim();
                    String name = "§4BungeeConsole";
                    if(cs instanceof ProxiedPlayer){
                        ProxiedPlayer p = (ProxiedPlayer) cs;
                            name = p.getDisplayName();
                    }
                    MySQL.unmuteUser(uuid);
                    for (ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                        if(RankSystem.hasMod(all.getUniqueId().toString().replaceAll("-",""))){
                            all.sendMessage(new TextComponent(BanSystem.prefix + "§a" + RankSystem.getPrefix(uuid) + " §7wurde von §c" + name + " §7entmutet§c!"));
                            all.sendMessage(new TextComponent(BanSystem.prefix + reason));
                        }
                    }
                }
            }
        }
    }
}
