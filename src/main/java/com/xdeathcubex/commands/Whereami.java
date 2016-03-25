package com.xdeathcubex.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Whereami extends Command{
    public Whereami() {
        super("whereami");
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer)cs;
        p.sendMessage(new TextComponent("§7Du befindest dich momentan auf Server §e" + p.getServer().getInfo().getName()));
    }
}
