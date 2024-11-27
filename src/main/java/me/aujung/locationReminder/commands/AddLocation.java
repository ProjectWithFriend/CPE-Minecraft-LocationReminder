package me.aujung.locationReminder.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static org.bukkit.Bukkit.getServer;

public class AddLocation implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        getServer().broadcastMessage("Hello, world!");
        return true;
    }
}
