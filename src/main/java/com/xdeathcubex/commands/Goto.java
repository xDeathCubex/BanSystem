package com.xdeathcubex.commands;

import com.xdeathcubex.BanSystem;
import com.xdeathcubex.utils.RankSystem;
import com.xdeathcubex.utils.UUIDFetcher;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Goto extends Command {
    public Goto() {
        super("goto");
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer)cs;
        if(!RankSystem.hasMod(p.getUniqueId().toString().replaceAll("-",""))){
            p.sendMessage(new TextComponent(BanSystem.prefix + "§cKeine Rechte!"));
            return;
        }
        if(args.length != 1){
            p.sendMessage(new TextComponent(BanSystem.prefix + "§cVerwendung: §e/goto <Spieler>"));
        } else {
            String uuid = UUIDFetcher.getUUID(args[0]);
            if(uuid == null){
                p.sendMessage(new TextComponent("§cDieser Spieler existiert nicht."));
            } else {
                ProxiedPlayer p1 = ProxyServer.getInstance().getPlayer(args[0]);
                if(p1 == null){
                    p.sendMessage(new TextComponent(RankSystem.getPrefix(uuid) +" §cist momentan nicht online."));
                } else {
                    p.connect(p1.getServer().getInfo());
                    p.sendMessage(new TextComponent(BanSystem.prefix + "§7Du bist nun auf dem Server von §a" + RankSystem.getPrefix(uuid)));
                }
            }
        }
    }
}
